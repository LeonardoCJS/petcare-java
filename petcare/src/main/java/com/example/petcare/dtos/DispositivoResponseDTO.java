package com.example.petcare.dtos;

import com.example.petcare.domains.Dispositivo;
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
public class DispositivoResponseDTO extends RepresentationModel<DispositivoResponseDTO> {
    private String id;
    private String numeroSerie;
    private String modelo;
    private LocalDateTime dataAtivacao;
    private Dispositivo.StatusDispositivo status;
    private String petId;
    private String nomePet;
    private String nomeTutor;
    private Integer quantidadeMonitoramentos;
}
