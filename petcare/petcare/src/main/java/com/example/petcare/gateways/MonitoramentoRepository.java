package com.example.petcare.gateways;

import com.example.petcare.domains.Monitoramento;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface MonitoramentoRepository extends GenericRepository<Monitoramento, Long> {

    List<Monitoramento> findByPetIdOrderByDataHoraDesc(String petId);

    List<Monitoramento> findByDispositivoIdOrderByDataHoraDesc(String dispositivoId);

    @Query("select m from Monitoramento m where  m.pet.id = :petId and m.dataHora between :start and :end")
    List<Monitoramento> findByPetIdAndPeriodo(
            @Param("petId") String petId,
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end);

    @Query("select m from Monitoramento m where m.temperaturaCorporal > :tempLimite or m.frequenciaCardiaca > :freqLimite")
    List<Monitoramento> findMetricasCriticas(
            @Param("tempLimite") Double tempLimite,
            @Param("freqLimite") Integer freqLimite);

    Optional<Monitoramento> findFirstByPetIdOrderByDataHoraDesc(String petId);

    default List<Monitoramento> findAllAtivos() {
        return findAll();
    }
}
