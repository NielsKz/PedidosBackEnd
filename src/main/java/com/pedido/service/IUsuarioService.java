package com.pedido.service;

import com.pedido.model.dto.UsuarioDTO;
import com.pedido.model.entity.Usuario;

import java.util.List;

public interface IUsuarioService {
  List<Usuario> getALL();
  Usuario save(UsuarioDTO usuarioDTO);
  Usuario findBydId(Integer id);
  Usuario findByNickName(String nickname);
  void delete(Usuario usuario);
  boolean existsById(Integer id);
}
