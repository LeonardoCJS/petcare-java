package com.example.petcare.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class VeterinarioResponseDTO extends RepresentationModel<VeterinarioResponseDTO> {
    private String idVeterinario;
    private String nome;
    private String especialidade;
    private String telefone;
    private String email;
    private String crmv;
}
