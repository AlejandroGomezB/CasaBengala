package com.gamefestival.demo.dto;

import java.time.Instant;

public record RegistroDtoResponse (
        String nombre,
        String apellidos,
        String email,
        String ciudad,
        int edad,
        Instant fechaRegistro
) {
}