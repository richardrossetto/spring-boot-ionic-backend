package com.richard.cursomc.services;

import com.richard.cursomc.domain.Cidade;
import com.richard.cursomc.domain.Cliente;
import com.richard.cursomc.domain.Endereco;
import com.richard.cursomc.domain.enums.Perfil;
import com.richard.cursomc.domain.enums.TipoCliente;
import com.richard.cursomc.dto.ClienteDTO;
import com.richard.cursomc.dto.ClienteNewDTO;
import com.richard.cursomc.repositories.ClienteRepository;
import com.richard.cursomc.repositories.EnderecoRepository;
import com.richard.cursomc.security.UserSS;
import com.richard.cursomc.services.exceptions.AuthorizationException;
import com.richard.cursomc.services.exceptions.DataIntegrityException;
import com.richard.cursomc.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.awt.image.BufferedImage;
import java.net.URI;
import java.util.List;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository repo;

    @Autowired
    private EnderecoRepository enderecoRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private S3Service s3Service;
    
    @Autowired private ImageService imageService;
    
    @Value("${img.prefix.client.profile}")
    private String prefix;
    
    @Value("${img.profile.size}")
    private Integer size;
    
    public Cliente find(Integer id) {
        UserSS user = UserService.authenticated();
        if (user == null && !user.hasRole(Perfil.ADMIN) && !id.equals(user.getId())) {
            throw new AuthorizationException("Acesso negado!");
        }

        Cliente obj = repo.findOne(id);
        if (obj == null) {
            throw new ObjectNotFoundException("Objeto não encontrado! Id: " + id
                    + ", Tipo: " + Cliente.class.getName());
        }
        return obj;
    }

    @Transactional
    public Cliente insert(Cliente obj) {
        obj.setId(null);
        repo.save(obj);
        enderecoRepository.save(obj.getEnderecos());
        return obj;
    }

    public Cliente update(Cliente obj) {
        Cliente newObj = find(obj.getId());
        updateData(newObj, obj);
        return repo.save(obj);
    }

    private void updateData(Cliente newObj, Cliente obj) {
        newObj.setNome(obj.getNome());
        newObj.setEmail(obj.getEmail());
    }

    public void delete(Integer id) {
        find(id);
        try {
            repo.delete(id);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityException("Não é possível excluir porque há pedidos relacionados");
        }
    }

    public List<Cliente> findAll() {
        return repo.findAll();
    }

    public Cliente findByEmail(String email){
        UserSS user = UserService.authenticated();
        if(user == null || !user.hasRole(Perfil.ADMIN) && !email.equals(user.getUsername())){
            throw new AuthorizationException("Acesso negado");
        }
        Cliente cliente = repo.findByEmail(email);
        if(cliente == null){
            throw new ObjectNotFoundException("Objeto não encontrado! ID: " + user.getId() +
                ", " +
                    ", Tipo: " + Cliente.class.getName());
        }
        return cliente;
    }

    public Page<Cliente> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
        PageRequest pageRequest = new PageRequest(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);
        return repo.findAll(pageRequest);
    }

    public Cliente fromDTO(ClienteDTO objDTO) {
        return new Cliente(objDTO.getId(), objDTO.getNome(), objDTO.getEmail(), null, null, null);
    }

    public Cliente fromDTO(ClienteNewDTO objDto) {
        Cliente cli = new Cliente(null, objDto.getNome(), objDto.getEmail(), objDto.getCpfOuCnpj(), TipoCliente.toEnum(objDto.getTipo()), bCryptPasswordEncoder.encode(objDto.getSenha()));
        Cidade cid = new Cidade(objDto.getCidadeId(), null, null);
        Endereco end = new Endereco(null, objDto.getLogradouro(), objDto.getNumero(), objDto.getComplemento(), objDto.getBairro(), objDto.getCep(), cli, cid);
        cli.getEnderecos().add(end);
        cli.getTelefones().add(objDto.getTelefone1());
        if (objDto.getTelefone2() != null) {
            cli.getTelefones().add(objDto.getTelefone2());
        }
        if (objDto.getTelefone3() != null) {
            cli.getTelefones().add(objDto.getTelefone3());
        }
        return cli;
    }

    public URI uploadProfilePicture(MultipartFile multipartFile) {
        UserSS user = UserService.authenticated();
        if(user == null){
            throw new AuthorizationException("Acesso negado!");
        }
        
        BufferedImage jpgImage = imageService.getJpgImageFromFile(multipartFile);
        jpgImage = imageService.cropSquare(jpgImage);
        jpgImage = imageService.resize(jpgImage, size);
        
        String fileName = prefix + user.getId() + ".jpg";
        
        return s3Service.uploadFile(imageService.getInputStream(jpgImage, "jpg"), fileName, "image");
        
    }

}
