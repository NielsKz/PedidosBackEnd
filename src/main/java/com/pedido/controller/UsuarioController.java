package com.pedido.controller;

import com.pedido.model.dto.UsuarioDTO;
import com.pedido.model.entity.Role;
import com.pedido.model.entity.Usuario;
import com.pedido.model.payload.MensajeResponse;
import com.pedido.service.IUsuarioService;
import com.pedido.util.Constantes;
import com.pedido.util.MemoriaCache;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(Constantes.API_V + "usuario")
@Slf4j
@RequiredArgsConstructor
public class UsuarioController {
  @Autowired
  private IUsuarioService usuarioService;
  @Autowired
  private MemoriaCache memoriaCache;

  private final PasswordEncoder passwordEncoder;
  @GetMapping
  public ResponseEntity<?> showALl(){
    List<Usuario> getList = null;

    if(memoriaCache.existeCache()){
      getList = memoriaCache.recuperarDeCache("usuarioshowALl");
      log.info("Data desde [Memoria Cache]");
    }

    if(getList == null){
      getList = usuarioService.getALL();
      log.info("Data desde [BD]");
      if(getList.stream().count() == 0){
        return new ResponseEntity<>(
          MensajeResponse.builder()
            .mensaje("No existe registros de usuarios")
            .object(null)
            .build(),
          HttpStatus.OK);
      }

      memoriaCache.guardarEnCache("usuarioshowALl", getList);
    }

    return new ResponseEntity<>(
      MensajeResponse.builder()
        .mensaje("")
        .object(getList)
        .build(),
      HttpStatus.OK);
  }
  @GetMapping("/{name}")
  public ResponseEntity<?> findByNickName(@PathVariable String name){
    Usuario usuario = usuarioService.findByNickName(name);
    return new ResponseEntity<>(
      MensajeResponse.builder()
        .mensaje("")
        .object(usuario)
        .build(),
      HttpStatus.OK);
  }

  @PostMapping()
  public ResponseEntity<?> create(@RequestBody UsuarioDTO usuarioDTO){
    try {
      Usuario usuariosave = null;
      usuariosave = usuarioService.save(usuarioDTO);
      usuarioDTO = UsuarioDTO.builder()
        .idUsuario(usuarioDTO.getIdUsuario())
        .nombre(usuarioDTO.getNombre())
        .apellido(usuarioDTO.getApellido())
        .fechaNacimiento(usuarioDTO.getFechaNacimiento())
        .numeroDocumento(usuarioDTO.getNumeroDocumento())
        .rol(Role.USER)
        .nickName(usuarioDTO.getNickName())
        .contrasena(passwordEncoder.encode(usuarioDTO.getContrasena()))
        .correo(usuarioDTO.getCorreo())
        .idUsuarioRegistro(usuarioDTO.getIdUsuarioRegistro())
        .build();
      return new ResponseEntity<>(MensajeResponse.builder()
        .mensaje("Usuario: Guardado correctamente")
        .object(usuarioDTO)
        .build()
        ,HttpStatus.CREATED);
    }catch (DataAccessException ex){
      return new ResponseEntity<>(
        MensajeResponse.builder()
          .mensaje(ex.getMessage())
          .object(null)
          .build(),
        HttpStatus.METHOD_NOT_ALLOWED);
    }
  }
  @PutMapping("/{id}")
  public ResponseEntity<?> update(@RequestBody UsuarioDTO usuarioDTO, @PathVariable Integer id){
    try{
      Usuario usuarioUpd = null;
      if(!usuarioService.existsById(id)){
        return new ResponseEntity<>(
          MensajeResponse.builder()
            .mensaje("El usuario no existe")
            .object(null)
            .build(),
          HttpStatus.NOT_FOUND);
      }
      usuarioDTO.setIdUsuario(id);
      usuarioUpd = usuarioService.save(usuarioDTO);
      usuarioDTO = UsuarioDTO.builder()
        .idUsuario(usuarioDTO.getIdUsuario())
        .nombre(usuarioDTO.getNombre())
        .apellido(usuarioDTO.getApellido())
        .fechaNacimiento(usuarioDTO.getFechaNacimiento())
        .numeroDocumento(usuarioDTO.getNumeroDocumento())
        .rol(Role.USER)
        .nickName(usuarioDTO.getNickName())
        .contrasena(passwordEncoder.encode(usuarioDTO.getContrasena()))
        .correo(usuarioDTO.getCorreo())
        .idUsuarioRegistro(usuarioDTO.getIdUsuarioRegistro())
        .build();
      return new ResponseEntity<>(MensajeResponse.builder()
        .mensaje("Usuario: Guardado correctamente")
        .object(usuarioDTO)
        .build()
        ,HttpStatus.CREATED);
    }catch (DataAccessException ex){
      return new ResponseEntity<>(
        MensajeResponse.builder()
          .mensaje(ex.getMessage())
          .object(null)
          .build(),
        HttpStatus.METHOD_NOT_ALLOWED);
    }
  }


  @DeleteMapping("/{id}")
  public ResponseEntity<?> delete(@PathVariable Integer id){
    try {
      Usuario clientedelete = usuarioService.findBydId(id);
      usuarioService.delete(clientedelete);
      return new ResponseEntity<>(clientedelete, HttpStatus.NO_CONTENT);
    }catch ( DataAccessException ex){
      return new ResponseEntity<>(
        MensajeResponse.builder()
          .mensaje(ex.getMessage())
          .object(null)
          .build(),
        HttpStatus.METHOD_NOT_ALLOWED);
    }
  }


}
