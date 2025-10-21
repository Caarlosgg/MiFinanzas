package com.example.MiFinanzas.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "metas")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MetaAhorro {

    @Id
    private String id;

    @NotBlank(message = "El nombre de la meta es obligatorio")
    private String nombre;

    @NotNull(message = "El objetivo debe estar definido")
    private double objetivo;

    @NotNull(message = "El progreso actual debe estar definido")
    private double actual;

    @NotBlank(message = "Debe estar asociado a un usuario")
    private String usuarioId;
}
