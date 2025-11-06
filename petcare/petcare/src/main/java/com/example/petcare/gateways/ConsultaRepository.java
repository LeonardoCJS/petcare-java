package com.example.petcare.gateways;

import com.example.petcare.domains.Consulta;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ConsultaRepository extends GenericRepository<Consulta, String> {

    List<Consulta> findByPetIdOrderByDataHoraDesc(String petId);


    @Query("SELECT c FROM Consulta c WHERE c.dataHora BETWEEN :start AND :end")
    List<Consulta> findConsultasPorPeriodo(
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end);

    @Query("SELECT c FROM Consulta c WHERE c.pet.tutor.id = :tutorId")
    List<Consulta> findConsultasByTutorId(@Param("tutorId") String tutorId);

    @Query("SELECT c FROM Consulta c WHERE c.veterinario.idVeterinario = :veterinarioId AND c.dataHora > :dataAtual")
    List<Consulta> findProximasConsultasByVeterinarioId(
            @Param("veterinarioId") String veterinarioId,
            @Param("dataAtual") LocalDateTime dataAtual);

    default List<Consulta> findAllAtivos() {
        return findConsultasPorPeriodo(LocalDateTime.now(), LocalDateTime.now().plusYears(1));
    }
}