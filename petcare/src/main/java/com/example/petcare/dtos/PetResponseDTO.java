package com.example.petcare.dtos;

import com.example.petcare.domains.Pet;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class PetResponseDTO extends RepresentationModel<PetResponseDTO> {
    private String id;
    private String nome;
    private String especie;
    private String raca;
    private LocalDate dataNascimento;
    private Double peso;
    private Pet.Sexo sexo;
    private LocalDateTime dataCadastro;
    private String tutorId;
    private String nomeTutor;
    private Integer quantidadeDispositivos;
    private Integer quantidadeAlertasAtivos;
    private Integer idade;
}
