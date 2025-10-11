package com.example.petcare.gateways;

import com.example.petcare.gateways.dtos.MonitoramentoDTO;
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
    public ResponseEntity<MonitoramentoDTO> create(@Valid @RequestBody MonitoramentoDTO monitoramentoDTO) {
        MonitoramentoDTO createdMonitoramento = monitoramentoService.create(monitoramentoDTO);
        return ResponseEntity.created(URI.create("/api/monitoramentos/" + createdMonitoramento.getId()))
                .body(createdMonitoramento);
    }

    @GetMapping("/pet/{petId}")
    public ResponseEntity<List<MonitoramentoDTO>> findByPetId(@PathVariable String petId) {
        List<MonitoramentoDTO> monitoramentos = monitoramentoService.findByPetId(petId);
        return ResponseEntity.ok(monitoramentos);
    }

    @GetMapping("/pet/{petId}/ultimos")
    public ResponseEntity<List<MonitoramentoDTO>> findUltimosMonitoramentos(
            @PathVariable String petId,
            @RequestParam(defaultValue = "10") int limite) {
        List<MonitoramentoDTO> monitoramentos = monitoramentoService.findUltimosMonitoramentosByPetId(petId, limite);
        return ResponseEntity.ok(monitoramentos);
    }
}
