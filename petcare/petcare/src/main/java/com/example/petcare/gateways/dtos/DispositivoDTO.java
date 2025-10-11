package com.example.petcare.gateways.dtos;

import com.example.petcare.domains.Dispositivo;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DispositivoDTO {

    private String id;

    @NotBlank(message = "Número de série é obrigatório")
    private String numeroSerie;

    @NotBlank(message = "Modelo é obrigatório")
    private String modelo;

    private LocalDateTime dataAtivacao;

    private Dispositivo.StatusDispositivo status;

    @NotNull(message = "Pet ID é obrigatório")
    private String petId;

    private String nomePet;
    private String nomeTutor;
    private Integer quantidadeMonitoramentos;
}
