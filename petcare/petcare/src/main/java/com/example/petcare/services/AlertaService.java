package com.example.petcare.services;

import com.example.petcare.domains.Alerta;
import com.example.petcare.domains.Pet;
import com.example.petcare.gateways.AlertaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AlertaService {

    private final AlertaRepository alertaRepository;

    @Transactional
    public Alerta criarAlerta(Pet pet, String tipoAlerta, String descricao, Alerta.GravidadeAlerta gravidade) {
        Alerta alerta = Alerta.builder()
                .pet(pet)
                .tipoAlerta(tipoAlerta)
                .descricao(descricao)
                .dataHora(LocalDateTime.now())
                .gravidade(gravidade)
                .status(Alerta.StatusAlerta.PENDENTE)
                .build();

        return alertaRepository.save(alerta);
    }


    @Transactional
    public Alerta resolverAlerta(String id) {
        Alerta alerta = alertaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Alerta não encontrado"));

        alerta.setStatus(Alerta.StatusAlerta.RESOLVIDO);
        return alertaRepository.save(alerta);
    }

    @Transactional(readOnly = true)
    public List<Alerta> findAlertasPendentesByPetId(String petId) {
        return alertaRepository.findAlertasPendentesByPetId(petId);
    }

    @Transactional(readOnly = true)
    public List<Alerta> findAlertasByPetId(String petId) {
        return alertaRepository.findByPetIdOrderByDataHoraDesc(petId);
    }

    @Transactional(readOnly = true)
    public List<Alerta> findAlertasByTutorId(String tutorId) {
        return alertaRepository.findAlertasByTutorId(tutorId);
    }

    @Transactional(readOnly = true)
    public long countAlertasPendentes() {
        return alertaRepository.countAlertasPendentes();
    }

    @Transactional(readOnly = true)
        public List<Alerta> findAlertasRecentes() {
        LocalDateTime umDiaAtras = LocalDateTime.now().minusDays(1);
        return alertaRepository.findAlertasPorPeriodo(umDiaAtras, LocalDateTime.now());
    }
}
