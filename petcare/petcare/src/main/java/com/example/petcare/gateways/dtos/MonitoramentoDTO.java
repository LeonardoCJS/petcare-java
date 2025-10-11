package com.example.petcare.gateways.dtos;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MonitoramentoDTO {

    private String id;

    private LocalDateTime dataHora;

    @NotNull(message = "Temperatura corporal é obrigatoria")
    @DecimalMin("30.0") @DecimalMax("45.0")
    private Double temperaturaCorporal;

    @NotNull(message = "Frequência cardíaca é obrigatoria")
    private Integer frequenciaCardiaca;

    @NotNull(message = "Nível de atividade é obrigatorio")
    @Min(0)
    private Integer nivelAtividade;

    @NotNull(message = "Latitude é obrigatoria")
    private Double latitude;

    @NotNull(message = "Longitude é obrigatoria")
    private Double longitude;

    @NotNull(message = "Batimentos por minuto são obrigatorios")
    private Integer batimentosPorMinuto;

    @NotNull(message = "Dispositivo ID é obrigatorio")
    private String dispositivoId;

    @NotNull(message = "Pet ID é obrigatorio")
    private String petId;

    private String statusTemperatura;
    private String statusFrequenciaCardiaca;
    private String statusAtividade;
}
