package com.example.petcare.services;

import com.example.petcare.domains.Veterinario;
import com.example.petcare.gateways.VeterinarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class VeterinarioService {

    private final VeterinarioRepository veterinarioRepository;

    public List<Veterinario> findAll() {
        return veterinarioRepository.findAll();
    }

    public Optional<Veterinario> findById(String id) {
        return veterinarioRepository.findById(id);
    }

    public Veterinario save(Veterinario veterinario) {
        return veterinarioRepository.save(veterinario);
    }

    public void delete(String id) {
        veterinarioRepository.deleteById(id);
    }

    public Optional<Veterinario> findByEmail(String email) {
        return veterinarioRepository.findByEmail(email);
    }
}
