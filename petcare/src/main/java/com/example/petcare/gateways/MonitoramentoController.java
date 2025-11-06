package com.example.petcare.gateways;

import com.example.petcare.dtos.MonitoramentoRequestDTO;
import com.example.petcare.services.MonitoramentoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/monitoramentos")
@RequiredArgsConstructor
public class MonitoramentoController {

    private final MonitoramentoService monitoramentoService;

    @PostMapping
    public ResponseEntity<MonitoramentoRequestDTO> create(@Valid @RequestBody MonitoramentoRequestDTO monitoramentoRequestDTO) {
        MonitoramentoRequestDTO createdMonitoramento = monitoramentoService.create(monitoramentoRequestDTO);
        return ResponseEntity.created(URI.create("/api/monitoramentos/" + createdMonitoramento.getId()))
                .body(createdMonitoramento);
    }

    @GetMapping("/pet/{petId}")
    public ResponseEntity<List<MonitoramentoRequestDTO>> findByPetId(@PathVariable String petId) {
        List<MonitoramentoRequestDTO> monitoramentos = monitoramentoService.findByPetId(petId);
        return ResponseEntity.ok(monitoramentos);
    }

    @GetMapping("/pet/{petId}/ultimos")
    public ResponseEntity<List<MonitoramentoRequestDTO>> findUltimosMonitoramentos(
            @PathVariable String petId,
            @RequestParam(defaultValue = "10") int limite) {
        List<MonitoramentoRequestDTO> monitoramentos = monitoramentoService.findUltimosMonitoramentosByPetId(petId, limite);
        return ResponseEntity.ok(monitoramentos);
    }
}
