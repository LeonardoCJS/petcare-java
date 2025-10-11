package com.example.petcare.gateways;

import com.example.petcare.gateways.dtos.PetDTO;
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
    public ResponseEntity<List<PetDTO>> findAll() {
        List<PetDTO> pets = petService.findAll();
        return ResponseEntity.ok(pets);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PetDTO> findById(@PathVariable String id) {
        PetDTO pet = petService.findById(id);
        return ResponseEntity.ok(pet);
    }

    @PostMapping
    public ResponseEntity<PetDTO> create(@Valid @RequestBody PetDTO petDTO) {
        PetDTO createdPet = petService.create(petDTO);
        return ResponseEntity.created(URI.create("/api/pets/" + createdPet.getId()))
                .body(createdPet);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PetDTO> update(@PathVariable String id, @Valid @RequestBody PetDTO petDTO) {
        PetDTO updatedPet = petService.update(id, petDTO);
        return ResponseEntity.ok(updatedPet);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        petService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/tutor/{tutorId}")
    public ResponseEntity<List<PetDTO>> findByTutorId(@PathVariable String tutorId) {
        List<PetDTO> pets = petService.findByTutorId(tutorId);
        return ResponseEntity.ok(pets);
    }
}
