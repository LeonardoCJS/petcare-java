package com.example.petcare.gateways;

import com.example.petcare.dtos.PetRequestDTO;
import com.example.petcare.services.PetService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/pets")
@RequiredArgsConstructor
public class PetController {

    private final PetService petService;

    @GetMapping
    public ResponseEntity<List<PetRequestDTO>> findAll() {
        List<PetRequestDTO> pets = petService.findAll();
        return ResponseEntity.ok(pets);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PetRequestDTO> findById(@PathVariable String id) {
        PetRequestDTO pet = petService.findById(id);
        return ResponseEntity.ok(pet);
    }

    @PostMapping
    public ResponseEntity<PetRequestDTO> create(@Valid @RequestBody PetRequestDTO petRequestDTO) {
        PetRequestDTO createdPet = petService.create(petRequestDTO);
        return ResponseEntity.created(URI.create("/api/pets/" + createdPet.getId()))
                .body(createdPet);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PetRequestDTO> update(@PathVariable String id, @Valid @RequestBody PetRequestDTO petRequestDTO) {
        PetRequestDTO updatedPet = petService.update(id, petRequestDTO);
        return ResponseEntity.ok(updatedPet);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        petService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/tutor/{tutorId}")
    public ResponseEntity<List<PetRequestDTO>> findByTutorId(@PathVariable String tutorId) {
        List<PetRequestDTO> pets = petService.findByTutorId(tutorId);
        return ResponseEntity.ok(pets);
    }
}
