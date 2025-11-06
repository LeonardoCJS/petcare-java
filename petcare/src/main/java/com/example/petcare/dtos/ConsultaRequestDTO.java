package com.example.petcare.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ConsultaRequestDTO {
    private String id;

    @NotNull(message = "Data é obrigatoria")
    private LocalDate dataHora;

    @NotBlank(message = "Diaguinostico é obrigatorio")
    private String diaguinostico;

    @NotBlank(message = "Prescricao não pode estar em branco")
    private String prescricao;

    @NotBlank(message = "Observacoes não pode estar em branco")
    private String observacoes;

    @NotNull(message = "ID do veterinário é obrigatorio")
    private String veterinarioId;

    @NotNull(message = "ID do pet é obrigatorio")
    private String petId;
}
