package com.example.petcare.dtos;

import com.example.petcare.domains.Alerta;
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
public class AlertaRequestDTO {

    private String id;

    @NotBlank(message = "Tipo do alerta é obrigatório")
    private String tipoAlerta;

    @NotBlank(message = "Descrição é obrigatória")
    private String descricao;

    private LocalDateTime dataHora;

    @NotNull(message = "Gravidade é obrigatória")
    private Alerta.GravidadeAlerta gravidade;

    private Alerta.StatusAlerta status;

    @NotNull(message = "Pet ID é obrigatório")
    private String petId;

    // Campos calculados
    private String nomePet;
    private String nomeTutor;
    private String emailTutor;
}
