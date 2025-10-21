package com.example.MiFinanzas.service;

import com.example.MiFinanzas.model.Presupuesto;
import com.example.MiFinanzas.repository.PresupuestoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class PresupuestoService {

    @Autowired
    private PresupuestoRepository presupuestoRepository;

    public List<Presupuesto> listarPorUsuario(String usuarioId) {
        return presupuestoRepository.findByUsuarioId(usuarioId);
    }

    public Optional<Presupuesto> buscarPorId(String id) {
        return presupuestoRepository.findById(id);
    }

    public Presupuesto guardar(Presupuesto presupuesto) {
        return presupuestoRepository.save(presupuesto);
    }

    public void eliminar(String id) {
        presupuestoRepository.deleteById(id);
    }
}
