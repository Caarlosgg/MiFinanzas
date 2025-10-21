package com.example.MiFinanzas.controller;

import com.example.MiFinanzas.model.Movimiento;
import com.example.MiFinanzas.model.Usuario;
import com.example.MiFinanzas.service.MovimientoService;
import com.example.MiFinanzas.service.UsuarioService;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Controller
public class ExportController {

    private final MovimientoService movimientoService;
    private final UsuarioService usuarioService;

    public ExportController(MovimientoService movimientoService,
                            UsuarioService usuarioService) {
        this.movimientoService = movimientoService;
        this.usuarioService    = usuarioService;
    }

    /** Export CSV con separador punto y coma y BOM UTF-8 */
    @GetMapping("/exportar")
    public void exportarMovimientos(HttpServletResponse response,
                                    Authentication auth) throws IOException {
        // Cabeceras
        response.setContentType("text/csv; charset=UTF-8");
        response.setHeader("Content-Disposition",
                "attachment; filename=\"mis_movimientos.csv\"");

        // Obtenemos el usuario y sus movimientos
        Usuario u = usuarioService
                .buscarPorEmail(auth.getName())
                .orElseThrow(() ->
                        new IllegalArgumentException("Usuario no encontrado"));
        List<Movimiento> movimientos =
                movimientoService.listarPorUsuario(u.getId());

        // Preparar writer con BOM
        PrintWriter writer = new PrintWriter(
                new OutputStreamWriter(response.getOutputStream(), StandardCharsets.UTF_8));
        writer.write('\ufeff');

        // Cabecera CSV
        writer.println("\"Tipo\";\"Cantidad\";\"Categoría\";\"Fecha\"");

        // Filas
        for (Movimiento m : movimientos) {
            String fecha = m.getFecha() != null ? m.getFecha().toString() : "";
            writer.printf("\"%s\";\"%.2f\";\"%s\";\"%s\"%n",
                    m.getTipo(),
                    m.getCantidad(),
                    m.getCategoria(),
                    fecha);
        }

        writer.flush();
    }

    /** Exportar Excel con Apache POI (.xlsx) */
    @GetMapping("/exportar/excel")
    public void exportarExcel(HttpServletResponse response,
                              Authentication auth) throws IOException {
        // Cabeceras para descarga de Excel
        response.setContentType(
                "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition",
                "attachment; filename=\"mis_movimientos.xlsx\"");

        // Obtenemos el usuario y sus movimientos
        Usuario u = usuarioService
                .buscarPorEmail(auth.getName())
                .orElseThrow(() ->
                        new IllegalArgumentException("Usuario no encontrado"));
        List<Movimiento> movimientos =
                movimientoService.listarPorUsuario(u.getId());

        // Creamos el libro y la hoja
        try (XSSFWorkbook wb = new XSSFWorkbook()) {
            Sheet sheet = wb.createSheet("Movimientos");

            // Estilo de header (negrita)
            CellStyle headerStyle = wb.createCellStyle();
            Font font = wb.createFont();
            font.setBold(true);
            headerStyle.setFont(font);

            // Fila de encabezado
            Row header = sheet.createRow(0);
            String[] cols = {"Tipo", "Cantidad", "Categoría", "Fecha"};
            for (int i = 0; i < cols.length; i++) {
                Cell cell = header.createCell(i);
                cell.setCellValue(cols[i]);
                cell.setCellStyle(headerStyle);
            }

            // Estilo de fecha
            CreationHelper createHelper = wb.getCreationHelper();
            CellStyle dateStyle = wb.createCellStyle();
            dateStyle.setDataFormat(
                    createHelper.createDataFormat().getFormat("yyyy-mm-dd"));

            // Relleno de datos
            int rowIdx = 1;
            for (Movimiento m : movimientos) {
                Row row = sheet.createRow(rowIdx++);
                row.createCell(0).setCellValue(m.getTipo());
                row.createCell(1).setCellValue(m.getCantidad());
                row.createCell(2).setCellValue(m.getCategoria());
                Cell dateCell = row.createCell(3);
                if (m.getFecha() != null) {
                    dateCell.setCellValue(java.sql.Date.valueOf(m.getFecha()));
                    dateCell.setCellStyle(dateStyle);
                }
            }

            // Auto‐size columnas
            for (int i = 0; i < cols.length; i++) {
                sheet.autoSizeColumn(i);
            }

            // Enviar el libro al output
            wb.write(response.getOutputStream());
        }
    }

}
