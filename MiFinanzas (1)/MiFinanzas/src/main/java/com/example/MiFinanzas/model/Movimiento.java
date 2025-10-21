package com.example.MiFinanzas.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Document(collection = "movimientos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Movimiento {

    @Id
    private String id;

    @NotBlank(message = "El tipo debe ser 'ingreso' o 'gasto'")
    private String tipo;

    @NotNull(message = "La cantidad es obligatoria")
    private double cantidad;

    @NotBlank(message = "La categoría es obligatoria")
    private String categoria;

    @NotNull(message = "La fecha es obligatoria")
    private LocalDate fecha;

    @NotBlank(message = "Debe estar asociado a un usuario")
    private String usuarioId;

    private String metaId;

    /**
     * Nos aseguramos de guardar siempre el tipo en minúsculas,
     * de modo que luego no falle la búsqueda exacta.
     */
    public void setTipo(String tipo) {
        this.tipo = (tipo != null) ? tipo.toLowerCase() : null;
    }
}
