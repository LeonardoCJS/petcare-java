package com.example.petcare.gateways;

import com.example.petcare.domains.Tutor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TutorRepository extends GenericRepository<Tutor, String> {

    Optional<Tutor> findByEmail(String email);

    List<Tutor> findByEnderecoContainingIgnoreCase(String endereco);

    @Query("SELECT t FROM Tutor t WHERE SIZE(t.pets) >= :minPets")
    List<Tutor> findTutoresComMinimoPets(@Param("minPets") int minPets);

    // Metodo padrão para tutores ativos
    default List<Tutor> findAllAtivos() {
        return findAll();
    }
}
