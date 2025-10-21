package com.example.MiFinanzas.service;

import com.example.MiFinanzas.model.MetaAhorro;
import com.example.MiFinanzas.repository.MetaAhorroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MetaAhorroService {

    @Autowired
    private MetaAhorroRepository metaAhorroRepository;

    public List<MetaAhorro> listarTodos() {
        return metaAhorroRepository.findAll();
    }

    public List<MetaAhorro> listarPorUsuario(String usuarioId) {
        return metaAhorroRepository.findByUsuarioId(usuarioId);
    }

    public Optional<MetaAhorro> buscarPorId(String id) {
        return metaAhorroRepository.findById(id);
    }

    public MetaAhorro guardar(MetaAhorro meta) {
        return metaAhorroRepository.save(meta);
    }

    public void eliminar(String id) {
        metaAhorroRepository.deleteById(id);
    }
    public void eliminarSiEsDelUsuario(String id, String usuarioId) {
        metaAhorroRepository.findById(id).ifPresent(meta -> {
            if (meta.getUsuarioId().equals(usuarioId)) {
                metaAhorroRepository.deleteById(id);
            }
        });
    }
    public void sumarAhorroAMeta(String metaId, double cantidad) {
        metaAhorroRepository.findById(metaId).ifPresent(meta -> {
            meta.setActual(meta.getActual() + cantidad);
            metaAhorroRepository.save(meta);
        });
    }

}
