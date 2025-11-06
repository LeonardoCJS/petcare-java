package com.example.petcare.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class ConsultaResponseDTO extends RepresentationModel<ConsultaResponseDTO> {
    private String id;
    private LocalDate dataHora;
    private String diaguinostico;
    private String prescricao;
    private String observacoes;
    private String veterinarioId;
    private String petId;
}
