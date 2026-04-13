package com.gamefestival.demo.mapper;

import com.gamefestival.demo.dto.RegistroDtoRequest;
import com.gamefestival.demo.dto.RegistroDtoResponse;
import com.gamefestival.demo.entity.RegistroEntity;

import java.time.Instant;

public class MapToRegistroDto {
    //entidad-dto
    public static RegistroDtoResponse toDto(RegistroEntity registroEntity) {
        return new RegistroDtoResponse(
                registroEntity.getNombre(),
                registroEntity.getApellidos(),
                registroEntity.getEmail(),
                registroEntity.getCiudad(),
                registroEntity.getEdad(),
                registroEntity.getFechaRegistro()
        );
    }

    //dto -entidad
    public static RegistroEntity toEntity(RegistroDtoRequest registroDtoRequest) {
        RegistroEntity registroEntity = new RegistroEntity();
        registroEntity.setNombre(registroDtoRequest.nombre());
        registroEntity.setApellidos(registroDtoRequest.apellidos());
        registroEntity.setCiudad(registroDtoRequest.ciudad());
        registroEntity.setEdad(registroDtoRequest.edad());
        registroEntity.setEmail(registroDtoRequest.email());
        registroEntity.setFechaRegistro(Instant.now());
        return registroEntity;
    }
}
