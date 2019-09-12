package com.richard.cursomc.services;

import com.richard.cursomc.domain.Categoria;
import com.richard.cursomc.domain.Cliente;
import com.richard.cursomc.domain.ItemPedido;
import com.richard.cursomc.domain.PagamentoComBoleto;
import com.richard.cursomc.domain.Pedido;
import com.richard.cursomc.domain.enums.EstadoPagamento;
import com.richard.cursomc.repositories.*;
import com.richard.cursomc.security.UserSS;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.richard.cursomc.services.exceptions.AuthorizationException;
import com.richard.cursomc.services.exceptions.ObjectNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
public class PedidoService {
	
	@Autowired
	private PedidoRepository repo;

	@Autowired private BoletoService boletoService;

	@Autowired private PagamentoRepository pagamentoRepository;

	@Autowired private ProdutoRepository produtoRepository;

	@Autowired private ItemPedidoRepository itemPedidoRepository;

	@Autowired private ClienteRepository clienteRepository;

	@Autowired private EmailService emailService;
	
	public Pedido find(Integer id) {
		Pedido obj = repo.findOne(id);
		if (obj == null) {
			throw new ObjectNotFoundException("Objeto não encontrado! Id: " + id
					+ ", Tipo: " + Pedido.class.getName());
		}
		return obj;
	}

	@Transactional
	public Pedido insert(Pedido obj) {
		obj.setId(null);
		obj.setInstante(new Date());
		obj.setCliente(clienteRepository.findOne(obj.getCliente().getId()));
		obj.getPagamento().setEstado((EstadoPagamento.PENDENTE));
		obj.getPagamento().setPedido(obj);
		if(obj.getPagamento() instanceof PagamentoComBoleto) {
			PagamentoComBoleto pagto = (PagamentoComBoleto) obj.getPagamento();
			boletoService.preencherPagamentoComBoleto(pagto, obj.getInstante());
		}
		obj = repo.save(obj);
		pagamentoRepository.save(obj.getPagamento());
		for (ItemPedido ip : obj.getItens()) {
			ip.setDesconto(0.0);
			ip.setProduto(produtoRepository.findOne(ip.getProduto().getId()));
			ip.setPreco(ip.getProduto().getPreco());
			ip.setPedido(obj);
		}
		itemPedidoRepository.save(obj.getItens());
		emailService.sendOrderConfirmationHtmlEmail(obj);
		return obj;
	}
	
	public Page<Pedido> findPage(Integer page, Integer linesPerPage, String orderBy, String direction){
		UserSS user = UserService.authenticated();
		if(user == null) {
			throw new AuthorizationException("Acesso negado");
		}
		PageRequest pageRequest = new PageRequest(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);
		Cliente cliente = clienteRepository.findOne(user.getId());
		return repo.findByCliente(cliente, pageRequest);
	}
	
}
