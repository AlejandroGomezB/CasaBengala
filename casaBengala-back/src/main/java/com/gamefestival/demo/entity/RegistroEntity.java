package com.gamefestival.demo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Entity
@Table(name="registro_participantes")
@Setter
@Getter
@NoArgsConstructor
public class RegistroEntity {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String nombre;
    @Column(nullable = false)
    private String apellidos;
    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private String ciudad;
    @Column(nullable = false)
    private int edad;
    @Column(nullable = false)
    private Instant fechaRegistro;
}
