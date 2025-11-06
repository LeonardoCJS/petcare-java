package com.example.petcare.services;

import com.example.petcare.domains.Alerta;
import com.example.petcare.domains.Pet;
import com.example.petcare.domains.Tutor;
import com.example.petcare.gateways.PetRepository;
import com.example.petcare.gateways.TutorRepository;
import com.example.petcare.dtos.PetRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PetService {

    private final PetRepository petRepository;
    private final TutorRepository tutorRepository;
    private final AlertaService alertaService;

    @Transactional(readOnly = true)
    public List<PetRequestDTO> findAll() {
        return petRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public PetRequestDTO findById(String id) {
        Pet pet = petRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pet não encontrado"));
        return toDTO(pet);
    }

    @Transactional
    public PetRequestDTO create(PetRequestDTO petRequestDTO) {
        Tutor tutor = tutorRepository.findById(petRequestDTO.getTutorId())
                .orElseThrow(() -> new RuntimeException("Tutor não encontrado"));

        Pet pet = toEntity(petRequestDTO);
        pet.setTutor(tutor);

        Pet savedPet = petRepository.save(pet);
        return toDTO(savedPet);
    }

    @Transactional
    public PetRequestDTO update(String id, PetRequestDTO petRequestDTO) {
        Pet existingPet = petRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pet não encontrado"));

        Tutor tutor = tutorRepository.findById(petRequestDTO.getTutorId())
                .orElseThrow(() -> new RuntimeException("Tutor não encontrado"));

        updateEntityFromDTO(existingPet, petRequestDTO);
        existingPet.setTutor(tutor);

        Pet updatedPet = petRepository.save(existingPet);
        return toDTO(updatedPet);
    }

    @Transactional
    public void delete(String id) {
        Pet pet = petRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pet não encontrado"));
        petRepository.delete(pet);
    }

    @Transactional(readOnly = true)
    public List<PetRequestDTO> findByTutorId(String tutorId) {
        return petRepository.findByTutorId(tutorId).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Integer calcularIdade(LocalDate dataNascimento) {
        return Period.between(dataNascimento, LocalDate.now()).getYears();
    }

    // Métodos de conversão
    private PetRequestDTO toDTO(Pet pet) {
        return PetRequestDTO.builder()
                .id(pet.getId())
                .nome(pet.getNome())
                .especie(pet.getEspecie())
                .raca(pet.getRaca())
                .dataNascimento(pet.getDataNascimento())
                .peso(pet.getPeso())
                .sexo(pet.getSexo())
                .dataCadastro(pet.getDataCadastro())
                .tutorId(pet.getTutor().getId())
                .nomeTutor(pet.getTutor().getNome())
                .quantidadeDispositivos(pet.getDispositivos().size())
                .quantidadeAlertasAtivos((int) pet.getAlertas().stream()
                        .filter(alerta -> alerta.getStatus() == Alerta.StatusAlerta.PENDENTE).count())
                .idade(calcularIdade(pet.getDataNascimento()))
                .build();
    }

    private Pet toEntity(PetRequestDTO petRequestDTO) {
        return Pet.builder()
                .nome(petRequestDTO.getNome())
                .especie(petRequestDTO.getEspecie())
                .raca(petRequestDTO.getRaca())
                .dataNascimento(petRequestDTO.getDataNascimento())
                .peso(petRequestDTO.getPeso())
                .sexo(petRequestDTO.getSexo())
                .build();
    }

    private void updateEntityFromDTO(Pet pet, PetRequestDTO petRequestDTO) {
        pet.setNome(petRequestDTO.getNome());
        pet.setEspecie(petRequestDTO.getEspecie());
        pet.setRaca(petRequestDTO.getRaca());
        pet.setDataNascimento(petRequestDTO.getDataNascimento());
        pet.setPeso(petRequestDTO.getPeso());
        pet.setSexo(petRequestDTO.getSexo());
    }
}