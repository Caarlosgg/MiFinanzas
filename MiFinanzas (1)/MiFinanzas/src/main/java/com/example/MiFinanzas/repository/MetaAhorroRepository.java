package com.example.MiFinanzas.repository;

import com.example.MiFinanzas.model.MetaAhorro;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface MetaAhorroRepository extends MongoRepository<MetaAhorro, String> {
    List<MetaAhorro> findByUsuarioId(String usuarioId);
}
