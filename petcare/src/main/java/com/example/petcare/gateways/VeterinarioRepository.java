package com.example.petcare.gateways;

import com.example.petcare.domains.Veterinario;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VeterinarioRepository extends GenericRepository<Veterinario, String> {

    Optional<Veterinario> findByCrmv(String crmv);

    Optional<Veterinario> findByEmail(String email);

    List<Veterinario> findByEspecialidadeContainingIgnoreCase(String especialidade);

    @Query("SELECT v FROM Veterinario v WHERE LOWER(v.nome) LIKE LOWER(CONCAT('%', :nome, '%'))")
    List<Veterinario> findByNomeContainingIgnoreCase(@Param("nome") String nome);

    @Query("SELECT COUNT(v) FROM Veterinario v")
    long countTotalVeterinarios();

    // Método padrão para veterinários ativos
    default List<Veterinario> findAllAtivos() {
        return findAll();
    }
}
