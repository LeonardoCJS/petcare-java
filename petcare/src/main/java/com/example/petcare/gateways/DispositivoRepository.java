package com.example.petcare.gateways;

import com.example.petcare.domains.Dispositivo;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DispositivoRepository extends GenericRepository<Dispositivo, String> {

    Optional<Dispositivo> findByNumeroSerie(String numeroSerie);

    List<Dispositivo> findByPetId(String petId);

    List<Dispositivo> findByStatus(Dispositivo.StatusDispositivo status);

    @Query("select d from Dispositivo d where d.pet.tutor.id = :tutorId")
    List<Dispositivo> findByTutorId(@Param("tutorId") String tutorId);

    @Query("select count (d) from Dispositivo d where d.status = 'ATIVO'")
    long countDispositivosAtivos();

    @Query("select d from Dispositivo d where d.pet.id = :petId and d.status = 'ATIVO'")
    Optional<Dispositivo> findDispositivoAtivoByPetId(@Param("petId") String petId);

    default List<Dispositivo> findAllAtivos() {
        return findByStatus(Dispositivo.StatusDispositivo.ATIVO);
    }
}
