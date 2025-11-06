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
public class TutorResponseDTO extends RepresentationModel<TutorResponseDTO> {
    private String id;
    private String nome;
    private String email;
    private String telefone;
    private String endereco;
    private LocalDateTime dataCadastro;
    private Integer quantidadePets;
}