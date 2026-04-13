package com.gamefestival.demo.controller;

import com.gamefestival.demo.dto.RegistroDtoRequest;
import com.gamefestival.demo.dto.RegistroDtoResponse;
import com.gamefestival.demo.service.RegistroService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Registro de participantes", description = "Conjunto de EndPoints para el registro y consulta de participantes")
@RestController
@RequestMapping("/registro/v1")
public class RegistroController {

    @Autowired
    public RegistroService registroService;

    @Operation(
            summary = "Registra los participantes",
            description = "Realiza el registro del participante")
    @PostMapping(value = "/")
    public ResponseEntity<RegistroDtoResponse> save(@Valid @RequestBody RegistroDtoRequest registroDtoRequest){
        return ResponseEntity.status(HttpStatus.CREATED).body(registroService.save(registroDtoRequest));
    }

    @Operation(
            summary = "Consulta los participantes registrados",
            description = "Devuelve la lista de todos los participantes registrados")
    @GetMapping(value = "/",produces = "application/json")
    public ResponseEntity<List<RegistroDtoResponse>> getAll(){
        return ResponseEntity.status(HttpStatus.OK).body(registroService.getAll());
    }
}
