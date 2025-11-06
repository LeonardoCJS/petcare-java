package com.example.petcare.domains;

import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@With
@Getter
@Entity
@Table(name = "consulta")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
public class Consulta  {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id_consulta")
    private String id;

    @NotNull(message = "Data e hora são obrigatorias")
    @Future(message = "Data da consulta deve ser no futuro")
    @Column(name = "data_hora")
    private LocalDate dataHora;

    @Column(name = "diaguinostico")
    private String diaguinostico;

    @Column(name = "prescricao")
    private String prescricao;

    @Column(name = "observacoes")
    private String observacoes;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_pet", nullable = false)
    @ToString.Exclude
    private Pet pet;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_veterinario", nullable = false)
    @ToString.Exclude
    private Veterinario veterinario;

}
