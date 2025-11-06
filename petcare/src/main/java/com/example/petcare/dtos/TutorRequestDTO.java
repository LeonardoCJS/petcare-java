package com.example.petcare.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TutorRequestDTO {

    private String id;

    @NotBlank(message = "Nome é obrigatorio")
    private String nome;

    @NotBlank(message = "Email é obrigatorio")
    @Email(message = "Email deve ser valido")
    private String email;

    @NotBlank(message = "Telefone é obrigatorio")
    @Pattern(regexp = "^\\(\\d{2}\\)\\s\\d{4,5}-\\d{4}$", message = "Telefone deve estar no formato (XX) XXXXX-XXXX")
    private String telefone;

    @NotBlank(message = "Endereço é obrigatorio")
    private String endereco;

    private LocalDateTime dataCadastro;


    private Integer quantidadePets;
}
