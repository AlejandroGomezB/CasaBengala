package com.gamefestival.demo.exception;

import com.gamefestival.demo.dto.RegistroDtoRequest;

public class RegistroException extends RuntimeException{
        public RegistroException(RegistroDtoRequest registroDtoRequest){
            super("El correo: " +registroDtoRequest.email() + " ya se encuentra registrado");
        }
}
