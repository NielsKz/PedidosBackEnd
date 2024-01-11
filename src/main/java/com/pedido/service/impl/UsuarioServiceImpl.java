package com.pedido.service.impl;

import com.pedido.model.dao.IUsuarioDAO;
import com.pedido.model.dto.UsuarioDTO;
import com.pedido.model.entity.Usuario;
import com.pedido.service.IUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class UsuarioServiceImpl implements IUsuarioService {
  @Autowired
  private IUsuarioDAO repository;

  @Override
  public List<Usuario> getALL() {
    return repository.findAll();
  }

  @Override
  public Usuario save(UsuarioDTO usuarioDTO) {
    Usuario usuario = Usuario.builder()
      .idUsuario(usuarioDTO.getIdUsuario())
      .nombre(usuarioDTO.getNombre())
      .apellido(usuarioDTO.getApellido())
      .fechaNacimiento(usuarioDTO.getFechaNacimiento())
      .numeroDocumento(usuarioDTO.getNumeroDocumento())
      .rol(usuarioDTO.getRol())
      .nickName(usuarioDTO.getNickName())
      .contrasena(usuarioDTO.getContrasena())
      .correo(usuarioDTO.getCorreo())
      .idUsuarioRegistro(usuarioDTO.getIdUsuarioRegistro())
      .fechaRegistro(new Date())
      .build();
    return repository.save(usuario);
  }

  @Override
  public Usuario findBydId(Integer id) {
    return repository.findById(id).orElse(null);
  }

  @Override
  public Usuario findByNickName(String nickname) {
    return repository.findByNickName(nickname).orElse(null);
  }

  @Override
  public void delete(Usuario usuario) {
    repository.delete(usuario);
  }

  @Override
  public boolean existsById(Integer id) {
    return repository.existsById(id);
  }
}
