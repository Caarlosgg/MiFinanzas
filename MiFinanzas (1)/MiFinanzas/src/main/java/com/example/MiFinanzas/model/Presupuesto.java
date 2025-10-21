package com.example.MiFinanzas.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "presupuestos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Presupuesto {

    @Id
    private String id;

    @NotBlank(message = "La categoría es obligatoria")
    private String categoria;

    @NotNull(message = "El límite debe estar definido")
    private double limite;

    @NotBlank(message = "Debe estar asociado a un usuario")
    private String usuarioId;
}
