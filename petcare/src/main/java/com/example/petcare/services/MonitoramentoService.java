package com.example.petcare.services;

import com.example.petcare.domains.Alerta;
import com.example.petcare.domains.Dispositivo;
import com.example.petcare.domains.Monitoramento;
import com.example.petcare.domains.Pet;
import com.example.petcare.gateways.DispositivoRepository;
import com.example.petcare.gateways.MonitoramentoRepository;
import com.example.petcare.gateways.PetRepository;
import com.example.petcare.dtos.MonitoramentoRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MonitoramentoService {

    private final MonitoramentoRepository monitoramentoRepository;
    private final PetRepository petRepository;
    private final DispositivoRepository dispositivoRepository;
    private final AlertaService alertaService;

    private static final Double TEMPERATURA_ALTA = 39.5;
    private static final Double TEMPERATURA_BAIXA = 37.0;
    private static final Integer FREQUENCIA_ALTA = 180;
    private static final Integer FREQUENCIA_BAIXA = 60;
    private static final Integer ATIVIDADE_BAIXA = 100;

    @Transactional
    public MonitoramentoRequestDTO create(MonitoramentoRequestDTO monitoramentoRequestDTO) {
        Pet pet = petRepository.findById(monitoramentoRequestDTO.getPetId())
                .orElseThrow(() -> new RuntimeException("Pet não encontrado"));

        Dispositivo dispositivo = dispositivoRepository.findById(monitoramentoRequestDTO.getDispositivoId())
                .orElseThrow(() -> new RuntimeException("Dispositivo não encontrado"));

        Monitoramento monitoramento = toEntity(monitoramentoRequestDTO);
        monitoramento.setPet(pet);
        monitoramento.setDispositivo(dispositivo);

        Monitoramento savedMonitoramento = monitoramentoRepository.save(monitoramento);


        verificarAlertas(savedMonitoramento);

        return toDTO(savedMonitoramento);
    }

    private void verificarAlertas(Monitoramento monitoramento) {
        if (monitoramento.getTemperaturaCorporal() > TEMPERATURA_ALTA) {
            alertaService.criarAlerta(
                    monitoramento.getPet(),
                    "TEMPERATURA_ALTA",
                    String.format("Temperatura corporal elevada: %.1f°C", monitoramento.getTemperaturaCorporal()),
                    Alerta.GravidadeAlerta.ALTA
            );
        }

        // Alerta de temperatura baixa
        if (monitoramento.getTemperaturaCorporal() < TEMPERATURA_BAIXA) {
            alertaService.criarAlerta(
                    monitoramento.getPet(),
                    "TEMPERATURA_BAIXA",
                    String.format("Temperatura corporal baixa: %.1f°C", monitoramento.getTemperaturaCorporal()),
                    Alerta.GravidadeAlerta.MEDIA
            );
        }

        // Alerta de frequência cardíaca alta
        if (monitoramento.getFrequenciaCardiaca() > FREQUENCIA_ALTA) {
            alertaService.criarAlerta(
                    monitoramento.getPet(),
                    "FREQUENCIA_CARDIACA_ALTA",
                    String.format("Frequência cardíaca elevada: %d bpm", monitoramento.getFrequenciaCardiaca()),
                    Alerta.GravidadeAlerta.ALTA
            );
        }

        // Alerta de baixa atividade
        if (monitoramento.getNivelAtividade() < ATIVIDADE_BAIXA) {
            alertaService.criarAlerta(
                    monitoramento.getPet(),
                    "BAIXA_ATIVIDADE",
                    String.format("Baixo nível de atividade: %d", monitoramento.getNivelAtividade()),
                    Alerta.GravidadeAlerta.BAIXA
            );
        }
    }

    @Transactional(readOnly = true)
    public List<MonitoramentoRequestDTO> findByPetId(String petId) {
        return monitoramentoRepository.findByPetIdOrderByDataHoraDesc(petId).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<MonitoramentoRequestDTO> findUltimosMonitoramentosByPetId(String petId, int limite) {
        return monitoramentoRepository.findByPetIdOrderByDataHoraDesc(petId).stream()
                .limit(limite)
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    // Métodos de conversão
    private MonitoramentoRequestDTO toDTO(Monitoramento monitoramento) {
        String statusTemperatura = calcularStatusTemperatura(monitoramento.getTemperaturaCorporal());
        String statusFrequenciaCardiaca = calcularStatusFrequenciaCardiaca(monitoramento.getFrequenciaCardiaca());
        String statusAtividade = calcularStatusAtividade(monitoramento.getNivelAtividade());

        return MonitoramentoRequestDTO.builder()
                .id(monitoramento.getId())
                .dataHora(monitoramento.getDataHora())
                .temperaturaCorporal(monitoramento.getTemperaturaCorporal())
                .frequenciaCardiaca(monitoramento.getFrequenciaCardiaca())
                .nivelAtividade(monitoramento.getNivelAtividade())
                .latitude(monitoramento.getLatitude())
                .longitude(monitoramento.getLongitude())
                .batimentosPorMinuto(monitoramento.getBatimentosPorMinuto())
                .dispositivoId(monitoramento.getDispositivo().getId())
                .petId(monitoramento.getPet().getId())
                .statusTemperatura(statusTemperatura)
                .statusFrequenciaCardiaca(statusFrequenciaCardiaca)
                .statusAtividade(statusAtividade)
                .build();
    }

    private Monitoramento toEntity(MonitoramentoRequestDTO dto) {
        return Monitoramento.builder()
                .dataHora(dto.getDataHora() != null ? dto.getDataHora() : LocalDateTime.now())
                .temperaturaCorporal(dto.getTemperaturaCorporal())
                .frequenciaCardiaca(dto.getFrequenciaCardiaca())
                .nivelAtividade(dto.getNivelAtividade())
                .latitude(dto.getLatitude())
                .longitude(dto.getLongitude())
                .batimentosPorMinuto(dto.getBatimentosPorMinuto())
                .build();
    }

    private String calcularStatusTemperatura(Double temperatura) {
        if (temperatura > TEMPERATURA_ALTA) return "ALTA";
        if (temperatura < TEMPERATURA_BAIXA) return "BAIXA";
        return "NORMAL";
    }

    private String calcularStatusFrequenciaCardiaca(Integer frequencia) {
        if (frequencia > FREQUENCIA_ALTA) return "ALTA";
        if (frequencia < FREQUENCIA_BAIXA) return "BAIXA";
        return "NORMAL";
    }

    private String calcularStatusAtividade(Integer atividade) {
        if (atividade < ATIVIDADE_BAIXA) return "BAIXA";
        if (atividade > 1000) return "ALTA";
        return "NORMAL";
    }
}