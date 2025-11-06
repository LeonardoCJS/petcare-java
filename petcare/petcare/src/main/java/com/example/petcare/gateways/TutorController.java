package com.example.petcare.gateways;

import com.example.petcare.gateways.dtos.TutorDTO;
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
    public ResponseEntity<List<TutorDTO>> findAll() {
        List<TutorDTO> tutores = tutorService.findAll();
        return ResponseEntity.ok(tutores);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TutorDTO> findById(@PathVariable String id) {
        TutorDTO tutor = tutorService.findById(id);
        return ResponseEntity.ok(tutor);
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<TutorDTO> findByEmail(@PathVariable String email) {
        TutorDTO tutor = tutorService.findByEmail(email);
        return ResponseEntity.ok(tutor);
    }

    @PostMapping
    public ResponseEntity<TutorDTO> create(@Valid @RequestBody TutorDTO tutorDTO) {
        TutorDTO createdTutor = tutorService.create(tutorDTO);
        return ResponseEntity.created(URI.create("/api/tutores/" + createdTutor.getId()))
                .body(createdTutor);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TutorDTO> update(@PathVariable String id, @Valid @RequestBody TutorDTO tutorDTO) {
        TutorDTO updatedTutor = tutorService.update(id, tutorDTO);
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