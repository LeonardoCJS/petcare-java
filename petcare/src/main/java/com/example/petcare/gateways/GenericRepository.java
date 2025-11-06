package com.example.petcare.gateways;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;
import java.util.Optional;

@NoRepositoryBean
public interface GenericRepository<T, ID> extends JpaRepository<T, ID>, JpaSpecificationExecutor<T> {

    default List<T> findAllAtivos() {
        return findAll();
    }

    default long countAtivos() {
        return count();
    }
}
