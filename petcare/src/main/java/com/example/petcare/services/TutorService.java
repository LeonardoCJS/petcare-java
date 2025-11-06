package com.example.petcare.services;

import com.example.petcare.domains.Tutor;
import com.example.petcare.gateways.TutorRepository;
import com.example.petcare.dtos.TutorRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TutorService {

    private final TutorRepository tutorRepository;

    @Transactional(readOnly = true)
    public List<TutorRequestDTO> findAll() {
        return tutorRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public TutorRequestDTO findById(String id) {
        Tutor tutor = tutorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tutor não encontrado"));
        return toDTO(tutor);
    }

    @Transactional(readOnly = true)
    public TutorRequestDTO findByEmail(String email) {
        Tutor tutor = tutorRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Tutor não encontrado"));
        return toDTO(tutor);
    }

    @Transactional
    public TutorRequestDTO create(TutorRequestDTO tutorRequestDTO) {
        if (tutorRepository.findByEmail(tutorRequestDTO.getEmail()).isPresent()) {
            throw new RuntimeException("Já existe um tutor com este email");
        }

        Tutor tutor = toEntity(tutorRequestDTO);
        Tutor savedTutor = tutorRepository.save(tutor);
        return toDTO(savedTutor);
    }

    @Transactional
    public TutorRequestDTO update(String id, TutorRequestDTO tutorRequestDTO) {
        Tutor existingTutor = tutorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tutor não encontrado"));

        if (!existingTutor.getEmail().equals(tutorRequestDTO.getEmail()) &&
                tutorRepository.findByEmail(tutorRequestDTO.getEmail()).isPresent()) {
            throw new RuntimeException("Já existe um tutor com este email");
        }

        updateEntityFromDTO(existingTutor, tutorRequestDTO);
        Tutor updatedTutor = tutorRepository.save(existingTutor);
        return toDTO(updatedTutor);
    }

    @Transactional
    public void delete(String id) {
        Tutor tutor = tutorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tutor não encontrado"));
        tutorRepository.delete(tutor);
    }

    @Transactional(readOnly = true)
    public long countTotalTutores() {
        return tutorRepository.count();
    }

    // Métodos de conversão
    private TutorRequestDTO toDTO(Tutor tutor) {
        return TutorRequestDTO.builder()
                .id(tutor.getId())
                .nome(tutor.getNome())
                .email(tutor.getEmail())
                .telefone(tutor.getTelefone())
                .endereco(tutor.getEndereco())
                .dataCadastro(tutor.getDataCadastro())
                .quantidadePets(tutor.getPets().size())
                .build();
    }

    private Tutor toEntity(TutorRequestDTO tutorRequestDTO) {
        return Tutor.builder()
                .nome(tutorRequestDTO.getNome())
                .email(tutorRequestDTO.getEmail())
                .telefone(tutorRequestDTO.getTelefone())
                .endereco(tutorRequestDTO.getEndereco())
                .build();
    }

    private void updateEntityFromDTO(Tutor tutor, TutorRequestDTO tutorRequestDTO) {
        tutor.setNome(tutorRequestDTO.getNome());
        tutor.setEmail(tutorRequestDTO.getEmail());
        tutor.setTelefone(tutorRequestDTO.getTelefone());
        tutor.setEndereco(tutorRequestDTO.getEndereco());
    }
}
