package com.example.petcare.domains;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@With
@Getter
@Entity
@Table(name = "dispositivo")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
public class Dispositivo {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @NotBlank(message = "Numero de serie é obrigatorio")
    @Column(name = "numero_serie", unique = true)
    private String numeroSerie;

    @NotBlank(message = "Modelo é obrigatorio")
    @Column(name = "modelo")
    private String modelo;

    @Column(name = "data_ativacao")
    private LocalDateTime dataAtivacao;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private StatusDispositivo status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_pet")
    @ToString.Exclude
    private Pet pet;

    @OneToMany(mappedBy = "dispositivo",
            cascade = CascadeType.ALL,
    fetch = FetchType.LAZY, orphanRemoval = true)
    @Builder.Default
    @ToString.Exclude
    private List<Monitoramento> monitoramentos = new ArrayList<>();

    @PrePersist
    protected void onCreate() {
        if (dataAtivacao == null) {
            dataAtivacao = LocalDateTime.now();
        }

        if (status == null) {
            status = StatusDispositivo.ATIVO;
        }
    }

    public enum StatusDispositivo {
        ATIVO, INATIVO
    }
}
