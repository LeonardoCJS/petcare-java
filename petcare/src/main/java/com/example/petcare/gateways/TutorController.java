package com.example.petcare.gateways;

import com.example.petcare.dtos.TutorRequestDTO;
import com.example.petcare.services.TutorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/tutores")
@RequiredArgsConstructor
public class TutorController {

    private final TutorService tutorService;

    @GetMapping
    public ResponseEntity<List<TutorRequestDTO>> findAll() {
        List<TutorRequestDTO> tutores = tutorService.findAll();
        return ResponseEntity.ok(tutores);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TutorRequestDTO> findById(@PathVariable String id) {
        TutorRequestDTO tutor = tutorService.findById(id);
        return ResponseEntity.ok(tutor);
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<TutorRequestDTO> findByEmail(@PathVariable String email) {
        TutorRequestDTO tutor = tutorService.findByEmail(email);
        return ResponseEntity.ok(tutor);
    }

    @PostMapping
    public ResponseEntity<TutorRequestDTO> create(@Valid @RequestBody TutorRequestDTO tutorRequestDTO) {
        TutorRequestDTO createdTutor = tutorService.create(tutorRequestDTO);
        return ResponseEntity.created(URI.create("/api/tutores/" + createdTutor.getId()))
                .body(createdTutor);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TutorRequestDTO> update(@PathVariable String id, @Valid @RequestBody TutorRequestDTO tutorRequestDTO) {
        TutorRequestDTO updatedTutor = tutorService.update(id, tutorRequestDTO);
        return ResponseEntity.ok(updatedTutor);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        tutorService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/estatisticas/total")
    public ResponseEntity<Long> countTotalTutores() {
        long count = tutorService.countTotalTutores();
        return ResponseEntity.ok(count);
    }
}