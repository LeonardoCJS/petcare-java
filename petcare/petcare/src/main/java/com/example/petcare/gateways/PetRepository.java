package com.example.petcare.gateways;

import com.example.petcare.domains.Pet;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
    public interface PetRepository extends GenericRepository<Pet, String> {

    List<Pet> findByTutorId(String tutorId);

    List<Pet> findByEspecie(String especie);

    Optional<Pet> findByIdAndTutorId(String id, String tutorId);

    @Query("select p from Pet p where p.tutor.email = :email")
    List<Pet> findByTutorEmail(@Param("email") String email);

    @Query("select count (p) from Pet p where p.especie = :especie")
    long countByEspecie(@Param("especie") String especie);

    default List<Pet> findAllAtivos() {
        return findAll();
    }
}
