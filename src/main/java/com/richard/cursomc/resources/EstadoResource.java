package com.richard.cursomc.resources;

import com.richard.cursomc.domain.Cidade;
import com.richard.cursomc.domain.Estado;
import com.richard.cursomc.dto.CidadeDTO;
import com.richard.cursomc.dto.EstadoDTO;
import com.richard.cursomc.services.CidadeService;
import com.richard.cursomc.services.EstadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Richard Rossetto on 9/21/18.
 */
@RestController
@RequestMapping("/estados")
public class EstadoResource {

    @Autowired
    private
    EstadoService estadoService;

    @Autowired
    private CidadeService cidadeService;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<EstadoDTO>> findAll() {
        List<Estado> estados = estadoService.findAll();
        List<EstadoDTO> listDto = estados.stream().map(obj -> new EstadoDTO(obj)).collect(Collectors.toList());
        return ResponseEntity.ok().body(listDto);
    }

    @RequestMapping(value = "/{estadoId}/cidades", method = RequestMethod.GET)
    public ResponseEntity<List<CidadeDTO>> findCidades(@PathVariable Integer estadoId) {
        List<Cidade> cidades = cidadeService.findByCidade(estadoId);
        List<CidadeDTO> listDto = cidades.stream().map(obj -> new CidadeDTO(obj)).collect(Collectors.toList());
        return ResponseEntity.ok().body(listDto);
    }
}
