package com.example.petcare.services;

import com.example.petcare.domains.Dispositivo;
import com.example.petcare.domains.Pet;
import com.example.petcare.gateways.DispositivoRepository;
import com.example.petcare.gateways.PetRepository;
import com.example.petcare.gateways.dtos.DispositivoDTO;
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
    public List<DispositivoDTO> findAll() {
        return dispositivoRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public DispositivoDTO findById(String id) {
        Dispositivo dispositivo = dispositivoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Dispositivo não encontrado"));
        return toDTO(dispositivo);
    }

    @Transactional(readOnly = true)
    public DispositivoDTO findByNumeroSerie(String numeroSerie) {
        Dispositivo dispositivo = dispositivoRepository.findByNumeroSerie(numeroSerie)
                .orElseThrow(() -> new RuntimeException("Dispositivo não encontrado"));
        return toDTO(dispositivo);
    }

    @Transactional
    public DispositivoDTO create(DispositivoDTO dispositivoDTO) {
        Pet pet = petRepository.findById(dispositivoDTO.getPetId())
                .orElseThrow(() -> new RuntimeException("Pet não encontrado"));

        if (dispositivoRepository.findByNumeroSerie(dispositivoDTO.getNumeroSerie()).isPresent()) {
            throw new RuntimeException("Já existe um dispositivo com este número de série");
        }

        Dispositivo dispositivo = toEntity(dispositivoDTO);
        dispositivo.setPet(pet);

        Dispositivo savedDispositivo = dispositivoRepository.save(dispositivo);
        return toDTO(savedDispositivo);
    }

    @Transactional
    public DispositivoDTO updateStatus(String id, Dispositivo.StatusDispositivo status) {
        Dispositivo dispositivo = dispositivoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Dispositivo não encontrado"));

        dispositivo.setStatus(status);
        Dispositivo updatedDispositivo = dispositivoRepository.save(dispositivo);
        return toDTO(updatedDispositivo);
    }

    @Transactional(readOnly = true)
    public List<DispositivoDTO> findByPetId(String petId) {
        return dispositivoRepository.findByPetId(petId).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<DispositivoDTO> findByTutorId(String tutorId) {
        return dispositivoRepository.findByTutorId(tutorId).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public long countDispositivosAtivos() {
        return dispositivoRepository.countDispositivosAtivos();
    }

    // Métodos de conversão
    private DispositivoDTO toDTO(Dispositivo dispositivo) {
        return DispositivoDTO.builder()
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

    private Dispositivo toEntity(DispositivoDTO dispositivoDTO) {
        return Dispositivo.builder()
                .numeroSerie(dispositivoDTO.getNumeroSerie())
                .modelo(dispositivoDTO.getModelo())
                .status(dispositivoDTO.getStatus() != null ?
                        dispositivoDTO.getStatus() : Dispositivo.StatusDispositivo.ATIVO)
                .build();
    }
}
