package com.example.petcare.domains;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@With
@Getter
@Entity
@Table(name = "pet")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
public class Pet {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id_pet")
    private String id;

    @NotBlank(message = "Nome é obrigatorio")
    @Size (max = 100, message = "Nome deve ter no maximo 100 caracteres")
    @Column(name = "nome")
    private String nome;

    @NotBlank(message = "Espécie é obrigatoria")
    @Column(name = "especie")
    private String especie;

    @NotBlank(message = "Raça é obrigatoria")
    @Column(name = "raca")
    private String raca;

    @NotNull (message = "Data de nascimento é obrigatoria")
    @Past (message = "Data de nascimento deve ser no passado")
    @Column(name = "data_nascimento")
    private LocalDate dataNascimento;

    @NotNull(message = "Peso é obrigatorio")
    @DecimalMin(value = "0.1", message = "Peso deve ser maior que zero")//para validar o peso
    @Column(name = "peso")
    private Double peso;

    @Enumerated(EnumType.STRING)
    @Column(name = "sexo")
    private Sexo sexo;

    @Column(name = "data_cadastro")
    private LocalDateTime dataCadastro;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tutor_id")
    @ToString.Exclude // para nao incluir tutor ao ToString
    private Tutor tutor;

    @OneToMany(mappedBy = "pet",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY, orphanRemoval = true)
    @Builder.Default
    @ToString.Exclude
    private List<Dispositivo> dispositivos = new ArrayList<>();

    @OneToMany(mappedBy = "pet",
    cascade = CascadeType.ALL,
    fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Monitoramento> monitoramentos = new ArrayList<>();

    @OneToMany(mappedBy = "pet",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY, orphanRemoval = true)
    @Builder.Default
    @ToString.Exclude
    private List<Alerta> alertas = new ArrayList<>();

    @PrePersist //faz o metodo ser executado automaticamente antes de uma entidade ser persistida no banco de dados
    protected void onCreate() {
        dataCadastro = LocalDateTime.now();
    }

    public enum Sexo {
        MACHO, FEMEA
    }


}
