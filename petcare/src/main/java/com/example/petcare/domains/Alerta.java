package com.example.petcare.domains;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@With
@Getter
@Entity
@Table(name = "alerta")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
public class Alerta{

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id_alerta")
    private String idAlerta;

    @NotBlank(message = "Tipo do alerta é obrigatorio")
    @Column(name = "tipo_alerta")
    private String tipoAlerta;

    @NotBlank(message = "Descrição é obrigatoria")
    @Column(name = "descricao")
    private String descricao;

    @NotNull(message = "Data e hora são obrigatorias")
    @Column(name = "data_hora")
    private LocalDateTime dataHora;

    @Enumerated(EnumType.STRING)
    @Column(name = "gravidade")
    private GravidadeAlerta gravidade;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private StatusAlerta status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_pet")
    @ToString.Exclude
    private Pet pet;

    @PrePersist
    protected void onCreate() {
        if (dataHora == null) {
            dataHora = LocalDateTime.now();
        }
        if (status == null) {
            status = StatusAlerta.PENDENTE;
        }
    }

    public enum GravidadeAlerta {
        BAIXA, MEDIA, ALTA
    }

    public enum StatusAlerta {
        PENDENTE, RESOLVIDO
    }
}