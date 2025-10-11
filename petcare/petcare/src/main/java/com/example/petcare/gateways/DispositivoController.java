package com.example.petcare.gateways;

import com.example.petcare.domains.Dispositivo;
import com.example.petcare.gateways.dtos.DispositivoDTO;
import com.example.petcare.services.DispositivoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/dispositivos")
@RequiredArgsConstructor
public class DispositivoController {

    private final DispositivoService dispositivoService;

    @GetMapping
    public ResponseEntity<List<DispositivoDTO>> findAll() {
        List<DispositivoDTO> dispositivos = dispositivoService.findAll();
        return ResponseEntity.ok(dispositivos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DispositivoDTO> findById(@PathVariable String id) {
        DispositivoDTO dispositivo = dispositivoService.findById(id);
        return ResponseEntity.ok(dispositivo);
    }

    @GetMapping("/serial/{numeroSerie}")
    public ResponseEntity<DispositivoDTO> findByNumeroSerie(@PathVariable String numeroSerie) {
        DispositivoDTO dispositivo = dispositivoService.findByNumeroSerie(numeroSerie);
        return ResponseEntity.ok(dispositivo);
    }

    @PostMapping
    public ResponseEntity<DispositivoDTO> create(@Valid @RequestBody DispositivoDTO dispositivoDTO) {
        DispositivoDTO createdDispositivo = dispositivoService.create(dispositivoDTO);
        return ResponseEntity.created(URI.create("/api/dispositivos/" + createdDispositivo.getId()))
                .body(createdDispositivo);
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<DispositivoDTO> updateStatus(
            @PathVariable String id,
            @RequestParam Dispositivo.StatusDispositivo status) {
        DispositivoDTO updatedDispositivo = dispositivoService.updateStatus(id, status);
        return ResponseEntity.ok(updatedDispositivo);
    }

    @GetMapping("/pet/{petId}")
    public ResponseEntity<List<DispositivoDTO>> findByPetId(@PathVariable String petId) {
        List<DispositivoDTO> dispositivos = dispositivoService.findByPetId(petId);
        return ResponseEntity.ok(dispositivos);
    }

    @GetMapping("/tutor/{tutorId}")
    public ResponseEntity<List<DispositivoDTO>> findByTutorId(@PathVariable String tutorId) {
        List<DispositivoDTO> dispositivos = dispositivoService.findByTutorId(tutorId);
        return ResponseEntity.ok(dispositivos);
    }

    @GetMapping("/estatisticas/ativos")
    public ResponseEntity<Long> countDispositivosAtivos() {
        long count = dispositivoService.countDispositivosAtivos();
        return ResponseEntity.ok(count);
    }
}