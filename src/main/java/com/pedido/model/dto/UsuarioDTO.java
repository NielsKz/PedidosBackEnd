package com.pedido.model.dto;

import com.pedido.model.entity.Role;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.util.Date;
@Data
@Builder
public class UsuarioDTO {
  private Integer idUsuario;
  private String nombre;
  private String apellido;
  private Date fechaNacimiento;
  private String numeroDocumento;
  private Role rol;
  private String nickName;
  private String contrasena;
  private String correo;
  private Integer idUsuarioRegistro;
}
