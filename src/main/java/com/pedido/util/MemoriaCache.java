package com.pedido.util;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.concurrent.TimeUnit;
@Slf4j
@Repository
public class MemoriaCache {
  private Cache<String, Object> cache;

  public MemoriaCache() {
    cache = Caffeine.newBuilder()
      .maximumSize(100)
      .expireAfterWrite(2, TimeUnit.MINUTES) // Duración de expiración
      .build();
  }


  public boolean existeCache() {
    boolean exists = this.cache != null;
    log.info("[Memoria Cache]");
    if (exists) {
      log.info("[Memoria Cache] - Memoria cache creada anteriormente");
    } else {
      log.info("[Memoria Cache] - No existe memoria cache");
    }
    return exists;
  }

  public boolean guardarEnCache(String clave, Object valor) {
    if (this.existeCache()) {
      this.cache.put(clave, valor);
      log.info("[Memoria Cache]  [Guardar en Cache] con clave " + clave + " guardado correctamente");
      return this.cache.getIfPresent(clave) != null;
    } else {
      log.info("[Memoria Cache] [Guardar en Cache] - Error al guardar con clave : " + clave + ". Cache no inicializado");
      return false;
    }
  }

  public <T> T recuperarDeCache(String clave) {
    T valor = (T) this.cache.getIfPresent(clave);
    if (valor != null) {
      log.info("[Memoria Cache] [Recuperar de Cache] - con clave " + clave + " se recupera de la memoria cache");
      return valor;
    } else {
      log.info("[Memoria Cache] [Recuperar de Cache] - Catalogo con clave " + clave + " no se encontro en la memoria cache");
      return null;
    }
  }




}
