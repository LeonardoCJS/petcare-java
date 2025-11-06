package com.example.petcare.domains;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@With
@Getter
@Entity
@Table(name = "tutor")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
public class Tutor {
    @Id
    @GeneratedValue (strategy = GenerationType.UUID)
    public String id;

    @NotBlank(message = "Nome é obrigatório")
    @Size(max = 100, message = "Nome deve ter no máximo 100 caracteres")
    @Column
    private String nome;

    @NotBlank(message = "Email é obrigatório")
    @Email(message = "Email deve ser válido")
    @Column(unique = true)
    private String email;

    @NotBlank(message = "Telefone é obrigatório")
    @Pattern(regexp = "^\\(\\d{2}\\)\\s\\d{4,5}-\\d{4}$", message = "Telefone deve estar no formato (XX) XXXXX-XXXX")
    @Column(unique = true)
    private String telefone;

    @NotBlank(message = "Endereço é obrigatorio")
    @Size(max = 200, message = "Endereço deve ter no maximo 200 caracteres")
    @Column
    private String endereco;

    @Column(name = "data_cadastro")
    private LocalDateTime dataCadastro;

    @OneToMany(mappedBy = "tutor",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY, orphanRemoval = true)
    @Builder.Default
    @ToString.Exclude
    private List<Pet> pets = new ArrayList<>();

    @PrePersist
    protected void onCreate() {
        dataCadastro = LocalDateTime.now();
    }
}
