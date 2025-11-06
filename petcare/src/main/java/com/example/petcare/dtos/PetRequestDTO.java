package com.example.petcare.dtos;

import com.example.petcare.domains.Pet;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PetRequestDTO {

    private String id;

    @NotBlank(message = "Nome é obrigatorio")

    private String nome;

    @NotBlank(message = "Espécie é obrigatoria")
    private String especie;

    @NotBlank(message = "Raça é obrigatoria")
    private String raca;

    @NotNull(message = "Data de nascimento é obrigatoria")
    @Past(message = "Data de nascimento deve ser no passado")
    private LocalDate dataNascimento;

    @NotNull(message = "Peso é obrigatorio")
    @DecimalMin(value = "0.1", message = "Peso deve ser maior que zero")
    private Double peso;

    @NotNull(message = "Sexo é obrigatorio")
    private Pet.Sexo sexo;

    private LocalDateTime dataCadastro;

    @NotNull(message = "Tutor ID é obrigatorio")
    private String tutorId;


    private String nomeTutor;
    private Integer quantidadeDispositivos;
    private Integer quantidadeAlertasAtivos;
    private Integer idade;
}
