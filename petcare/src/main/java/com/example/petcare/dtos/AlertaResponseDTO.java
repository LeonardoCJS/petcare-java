package com.example.petcare.dtos;

import com.example.petcare.domains.Alerta;
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
public class AlertaResponseDTO extends RepresentationModel<AlertaResponseDTO> {
    private String id;
    private String tipoAlerta;
    private String descricao;
    private LocalDateTime dataHora;
    private Alerta.GravidadeAlerta gravidade;
    private Alerta.StatusAlerta status;
    private String petId;
    private String nomePet;
    private String nomeTutor;
    private String emailTutor;
}
