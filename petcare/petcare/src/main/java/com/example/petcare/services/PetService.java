package com.example.petcare.services;

import com.example.petcare.domains.Alerta;
import com.example.petcare.domains.Pet;
import com.example.petcare.domains.Tutor;
import com.example.petcare.gateways.PetRepository;
import com.example.petcare.gateways.TutorRepository;
import com.example.petcare.gateways.dtos.PetDTO;
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
    public List<PetDTO> findAll() {
        return petRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public PetDTO findById(String id) {
        Pet pet = petRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pet não encontrado"));
        return toDTO(pet);
    }

    @Transactional
    public PetDTO create(PetDTO petDTO) {
        Tutor tutor = tutorRepository.findById(petDTO.getTutorId())
                .orElseThrow(() -> new RuntimeException("Tutor não encontrado"));

        Pet pet = toEntity(petDTO);
        pet.setTutor(tutor);

        Pet savedPet = petRepository.save(pet);
        return toDTO(savedPet);
    }

    @Transactional
    public PetDTO update(String id, PetDTO petDTO) {
        Pet existingPet = petRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pet não encontrado"));

        Tutor tutor = tutorRepository.findById(petDTO.getTutorId())
                .orElseThrow(() -> new RuntimeException("Tutor não encontrado"));

        updateEntityFromDTO(existingPet, petDTO);
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
    public List<PetDTO> findByTutorId(String tutorId) {
        return petRepository.findByTutorId(tutorId).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Integer calcularIdade(LocalDate dataNascimento) {
        return Period.between(dataNascimento, LocalDate.now()).getYears();
    }

    // Métodos de conversão
    private PetDTO toDTO(Pet pet) {
        return PetDTO.builder()
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

    private Pet toEntity(PetDTO petDTO) {
        return Pet.builder()
                .nome(petDTO.getNome())
                .especie(petDTO.getEspecie())
                .raca(petDTO.getRaca())
                .dataNascimento(petDTO.getDataNascimento())
                .peso(petDTO.getPeso())
                .sexo(petDTO.getSexo())
                .build();
    }

    private void updateEntityFromDTO(Pet pet, PetDTO petDTO) {
        pet.setNome(petDTO.getNome());
        pet.setEspecie(petDTO.getEspecie());
        pet.setRaca(petDTO.getRaca());
        pet.setDataNascimento(petDTO.getDataNascimento());
        pet.setPeso(petDTO.getPeso());
        pet.setSexo(petDTO.getSexo());
    }
}