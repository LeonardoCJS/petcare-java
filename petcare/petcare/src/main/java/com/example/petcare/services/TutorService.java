package com.example.petcare.services;

import com.example.petcare.domains.Tutor;
import com.example.petcare.gateways.TutorRepository;
import com.example.petcare.gateways.dtos.TutorDTO;
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
    public List<TutorDTO> findAll() {
        return tutorRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public TutorDTO findById(String id) {
        Tutor tutor = tutorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tutor não encontrado"));
        return toDTO(tutor);
    }

    @Transactional(readOnly = true)
    public TutorDTO findByEmail(String email) {
        Tutor tutor = tutorRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Tutor não encontrado"));
        return toDTO(tutor);
    }

    @Transactional
    public TutorDTO create(TutorDTO tutorDTO) {
        if (tutorRepository.findByEmail(tutorDTO.getEmail()).isPresent()) {
            throw new RuntimeException("Já existe um tutor com este email");
        }

        Tutor tutor = toEntity(tutorDTO);
        Tutor savedTutor = tutorRepository.save(tutor);
        return toDTO(savedTutor);
    }

    @Transactional
    public TutorDTO update(String id, TutorDTO tutorDTO) {
        Tutor existingTutor = tutorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tutor não encontrado"));

        if (!existingTutor.getEmail().equals(tutorDTO.getEmail()) &&
                tutorRepository.findByEmail(tutorDTO.getEmail()).isPresent()) {
            throw new RuntimeException("Já existe um tutor com este email");
        }

        updateEntityFromDTO(existingTutor, tutorDTO);
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
    private TutorDTO toDTO(Tutor tutor) {
        return TutorDTO.builder()
                .id(tutor.getId())
                .nome(tutor.getNome())
                .email(tutor.getEmail())
                .telefone(tutor.getTelefone())
                .endereco(tutor.getEndereco())
                .dataCadastro(tutor.getDataCadastro())
                .quantidadePets(tutor.getPets().size())
                .build();
    }

    private Tutor toEntity(TutorDTO tutorDTO) {
        return Tutor.builder()
                .nome(tutorDTO.getNome())
                .email(tutorDTO.getEmail())
                .telefone(tutorDTO.getTelefone())
                .endereco(tutorDTO.getEndereco())
                .build();
    }

    private void updateEntityFromDTO(Tutor tutor, TutorDTO tutorDTO) {
        tutor.setNome(tutorDTO.getNome());
        tutor.setEmail(tutorDTO.getEmail());
        tutor.setTelefone(tutorDTO.getTelefone());
        tutor.setEndereco(tutorDTO.getEndereco());
    }
}
