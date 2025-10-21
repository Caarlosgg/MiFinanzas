package com.example.MiFinanzas.service;

import com.example.MiFinanzas.model.Movimiento;
import com.example.MiFinanzas.repository.MovimientoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MovimientoService {

    @Autowired
    private MovimientoRepository movimientoRepository;

    public List<Movimiento> listarPorUsuario(String usuarioId) {
        return movimientoRepository.findByUsuarioId(usuarioId);
    }

    public List<Movimiento> listarPorUsuarioYCategoria(String usuarioId, String categoria) {
        return movimientoRepository.findByUsuarioIdAndCategoriaIgnoreCase(usuarioId, categoria);
    }

    public Movimiento guardar(Movimiento movimiento) {
        return movimientoRepository.save(movimiento);
    }

    public Optional<Movimiento> buscarPorId(String id) {
        return movimientoRepository.findById(id);
    }

    public void eliminar(String id) {
        movimientoRepository.deleteById(id);
    }

    public void eliminarSiEsDelUsuario(String id, String usuarioId) {
        movimientoRepository.findById(id)
                .filter(m -> m.getUsuarioId().equals(usuarioId))
                .ifPresent(m -> movimientoRepository.deleteById(id));
    }

    public double saldoNetoEnCategoria(String usuarioId, String categoria) {
        double ingresos = movimientoRepository
                .findByUsuarioIdAndCategoriaAndTipoIgnoreCase(usuarioId, categoria, "ingreso")
                .stream().mapToDouble(Movimiento::getCantidad).sum();
        double gastos = movimientoRepository
                .findByUsuarioIdAndCategoriaAndTipoIgnoreCase(usuarioId, categoria, "gasto")
                .stream().mapToDouble(Movimiento::getCantidad).sum();
        return ingresos - gastos;
    }


    public double totalPorTipo(String usuarioId, String tipo) {
        return movimientoRepository
                .findByUsuarioIdAndTipoIgnoreCase(usuarioId, tipo)
                .stream()
                .mapToDouble(Movimiento::getCantidad)
                .sum();
    }

    public double totalGastosPorCategoria(String usuarioId, String categoria) {
        return movimientoRepository
                .findByUsuarioIdAndCategoriaAndTipoIgnoreCase(usuarioId, categoria, "gasto")
                .stream()
                .mapToDouble(Movimiento::getCantidad)
                .sum();
    }
}
