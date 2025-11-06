package com.example.petcare.services;

import com.example.petcare.domains.Dispositivo;
import com.example.petcare.domains.Pet;
import com.example.petcare.gateways.DispositivoRepository;
import com.example.petcare.gateways.PetRepository;
import com.example.petcare.dtos.DispositivoRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DispositivoService {

    private final DispositivoRepository dispositivoRepository;
    private final PetRepository petRepository;

    @Transactional(readOnly = true)
    public List<DispositivoRequestDTO> findAll() {
        return dispositivoRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public DispositivoRequestDTO findById(String id) {
        Dispositivo dispositivo = dispositivoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Dispositivo não encontrado"));
        return toDTO(dispositivo);
    }

    @Transactional(readOnly = true)
    public DispositivoRequestDTO findByNumeroSerie(String numeroSerie) {
        Dispositivo dispositivo = dispositivoRepository.findByNumeroSerie(numeroSerie)
                .orElseThrow(() -> new RuntimeException("Dispositivo não encontrado"));
        return toDTO(dispositivo);
    }

    @Transactional
    public DispositivoRequestDTO create(DispositivoRequestDTO dispositivoRequestDTO) {
        Pet pet = petRepository.findById(dispositivoRequestDTO.getPetId())
                .orElseThrow(() -> new RuntimeException("Pet não encontrado"));

        if (dispositivoRepository.findByNumeroSerie(dispositivoRequestDTO.getNumeroSerie()).isPresent()) {
            throw new RuntimeException("Já existe um dispositivo com este número de série");
        }

        Dispositivo dispositivo = toEntity(dispositivoRequestDTO);
        dispositivo.setPet(pet);

        Dispositivo savedDispositivo = dispositivoRepository.save(dispositivo);
        return toDTO(savedDispositivo);
    }

    @Transactional
    public DispositivoRequestDTO updateStatus(String id, Dispositivo.StatusDispositivo status) {
        Dispositivo dispositivo = dispositivoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Dispositivo não encontrado"));

        dispositivo.setStatus(status);
        Dispositivo updatedDispositivo = dispositivoRepository.save(dispositivo);
        return toDTO(updatedDispositivo);
    }

    @Transactional(readOnly = true)
    public List<DispositivoRequestDTO> findByPetId(String petId) {
        return dispositivoRepository.findByPetId(petId).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<DispositivoRequestDTO> findByTutorId(String tutorId) {
        return dispositivoRepository.findByTutorId(tutorId).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public long countDispositivosAtivos() {
        return dispositivoRepository.countDispositivosAtivos();
    }

    // Métodos de conversão
    private DispositivoRequestDTO toDTO(Dispositivo dispositivo) {
        return DispositivoRequestDTO.builder()
                .id(dispositivo.getId())
                .numeroSerie(dispositivo.getNumeroSerie())
                .modelo(dispositivo.getModelo())
                .dataAtivacao(dispositivo.getDataAtivacao())
                .status(dispositivo.getStatus())
                .petId(dispositivo.getPet().getId())
                .nomePet(dispositivo.getPet().getNome())
                .nomeTutor(dispositivo.getPet().getTutor().getNome())
                .quantidadeMonitoramentos(dispositivo.getMonitoramentos().size())
                .build();
    }

    private Dispositivo toEntity(DispositivoRequestDTO dispositivoRequestDTO) {
        return Dispositivo.builder()
                .numeroSerie(dispositivoRequestDTO.getNumeroSerie())
                .modelo(dispositivoRequestDTO.getModelo())
                .status(dispositivoRequestDTO.getStatus() != null ?
                        dispositivoRequestDTO.getStatus() : Dispositivo.StatusDispositivo.ATIVO)
                .build();
    }
}
