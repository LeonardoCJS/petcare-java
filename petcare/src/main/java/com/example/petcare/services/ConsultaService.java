package com.example.petcare.services;

import com.example.petcare.domains.Consulta;
import com.example.petcare.gateways.ConsultaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ConsultaService {

    private final ConsultaRepository consultaRepository;

    public List<Consulta> findAll() {
        return consultaRepository.findAll();
    }

    public Optional<Consulta> findById(String id) {
        return consultaRepository.findById(id);
    }

    public Consulta save(Consulta consulta) {
        return consultaRepository.save(consulta);
    }

    public void delete(String id) {
        consultaRepository.deleteById(id);
    }

}
