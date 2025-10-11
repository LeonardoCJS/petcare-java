package com.example.petcare.gateways;

import com.example.petcare.domains.Alerta;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AlertaRepository extends GenericRepository<Alerta, String> {

    List<Alerta> findByPetIdOrderByDataHoraDesc(String petId);

    List<Alerta> findByStatus(Alerta.StatusAlerta status);

    List<Alerta> findByGravidade(Alerta.GravidadeAlerta gravidade);

    @Query("select a from Alerta a where a.pet.id = :petId and a.status = 'PENDENTE'")
    List<Alerta> findAlertasPendentesByPetId(@Param("petId") String petId);

    @Query("select a from Alerta a where a.dataHora between :start and :end")
    List<Alerta> findAlertasPorPeriodo(
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end);

    @Query("SELECT COUNT(a) FROM Alerta a WHERE a.status = 'PENDENTE'")
    long countAlertasPendentes();

    @Query("SELECT a FROM Alerta a WHERE a.pet.tutor.id = :tutorId")
    List<Alerta> findAlertasByTutorId(@Param("tutorId") String tutorId);

    // Método para alertas ativos (consideramos pendentes como ativos)
    default List<Alerta> findAllAtivos() {
        return findByStatus(Alerta.StatusAlerta.PENDENTE);
    }
}
