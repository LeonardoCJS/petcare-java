package com.example.petcare.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class MonitoramentoResponseDTO extends RepresentationModel<MonitoramentoResponseDTO> {
    private String id;
    private LocalDateTime dataHora;
    private Double temperaturaCorporal;
    private Integer frequenciaCardiaca;
    private Integer nivelAtividade;
    private Double latitude;
    private Double longitude;
    private Integer batimentosPorMinuto;
    private String dispositivoId;
    private String petId;
    private String statusTemperatura;
    private String statusFrequenciaCardiaca;
    private String statusAtividade;
}
