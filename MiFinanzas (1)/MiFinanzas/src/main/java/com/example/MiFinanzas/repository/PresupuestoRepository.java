package com.example.MiFinanzas.repository;

import com.example.MiFinanzas.model.Presupuesto;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface PresupuestoRepository extends MongoRepository<Presupuesto, String> {
    List<Presupuesto> findByUsuarioId(String usuarioId);
}
