package com.pedido.model.dao;

import com.pedido.model.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IUsuarioDAO extends JpaRepository<Usuario,Integer> {
  Optional<Usuario> findByNickName(String username);

}
