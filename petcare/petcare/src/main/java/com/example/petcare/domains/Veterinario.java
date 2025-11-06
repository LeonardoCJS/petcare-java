package com.example.petcare.domains;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@With
@Getter
@Entity
@Table(name = "veterinario")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
public class Veterinario {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id_veterinario" )
    private String idVeterinario;

    @NotBlank(message = "Nome é obrigatorio")
    @Column(name = "nome")
    private String nome;

    @NotBlank(message = "Especialidade é obrigatoria")
    @Column(name = "especialidade")
    private String especialidade;

    @NotBlank(message = "Telefone é obrigatorio")
    @Pattern(regexp = "^\\(\\d{2}\\)\\s\\d{4,5}-\\d{4}$", message = "Telefone deve estar no formato (XX) XXXXX-XXXX")
    @Column(name = "telefone", unique = true)
    private String telefone;

    @NotBlank(message = "Email é obrigatorio")
    @Email(message = "Email deve ser válido")
    @Column(name = "email", unique = true)
    private String email;

    @NotBlank(message = "CRMV é obrigatorio")
    @Column(name = "crmv", unique = true)
    private String crmv;

    @OneToMany(mappedBy = "veterinario", cascade = CascadeType.ALL,
            fetch = FetchType.LAZY, orphanRemoval = true)
    @Builder.Default
    @ToString.Exclude
    private List<Consulta> consultas = new ArrayList<>();

}
