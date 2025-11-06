package com.example.petcare.gateways;

import com.example.petcare.domains.Dispositivo;
import com.example.petcare.dtos.DispositivoRequestDTO;
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
    public ResponseEntity<List<DispositivoRequestDTO>> findAll() {
        List<DispositivoRequestDTO> dispositivos = dispositivoService.findAll();
        return ResponseEntity.ok(dispositivos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DispositivoRequestDTO> findById(@PathVariable String id) {
        DispositivoRequestDTO dispositivo = dispositivoService.findById(id);
        return ResponseEntity.ok(dispositivo);
    }

    @GetMapping("/serial/{numeroSerie}")
    public ResponseEntity<DispositivoRequestDTO> findByNumeroSerie(@PathVariable String numeroSerie) {
        DispositivoRequestDTO dispositivo = dispositivoService.findByNumeroSerie(numeroSerie);
        return ResponseEntity.ok(dispositivo);
    }

    @PostMapping
    public ResponseEntity<DispositivoRequestDTO> create(@Valid @RequestBody DispositivoRequestDTO dispositivoRequestDTO) {
        DispositivoRequestDTO createdDispositivo = dispositivoService.create(dispositivoRequestDTO);
        return ResponseEntity.created(URI.create("/api/dispositivos/" + createdDispositivo.getId()))
                .body(createdDispositivo);
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<DispositivoRequestDTO> updateStatus(
            @PathVariable String id,
            @RequestParam Dispositivo.StatusDispositivo status) {
        DispositivoRequestDTO updatedDispositivo = dispositivoService.updateStatus(id, status);
        return ResponseEntity.ok(updatedDispositivo);
    }

    @GetMapping("/pet/{petId}")
    public ResponseEntity<List<DispositivoRequestDTO>> findByPetId(@PathVariable String petId) {
        List<DispositivoRequestDTO> dispositivos = dispositivoService.findByPetId(petId);
        return ResponseEntity.ok(dispositivos);
    }

    @GetMapping("/tutor/{tutorId}")
    public ResponseEntity<List<DispositivoRequestDTO>> findByTutorId(@PathVariable String tutorId) {
        List<DispositivoRequestDTO> dispositivos = dispositivoService.findByTutorId(tutorId);
        return ResponseEntity.ok(dispositivos);
    }

    @GetMapping("/estatisticas/ativos")
    public ResponseEntity<Long> countDispositivosAtivos() {
        long count = dispositivoService.countDispositivosAtivos();
        return ResponseEntity.ok(count);
    }
}