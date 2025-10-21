package com.example.MiFinanzas.repository;

import com.example.MiFinanzas.model.Movimiento;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface MovimientoRepository extends MongoRepository<Movimiento, String> {

    List<Movimiento> findByUsuarioId(String usuarioId);

    List<Movimiento> findByUsuarioIdAndTipoIgnoreCase(String usuarioId, String tipo);

    List<Movimiento> findByUsuarioIdAndCategoria(String usuarioId, String categoria);
    List<Movimiento> findByUsuarioIdAndCategoriaIgnoreCase(String usuarioId, String categoria);

    List<Movimiento> findByUsuarioIdAndCategoriaAndTipoIgnoreCase(String usuarioId, String categoria, String tipo);
}
