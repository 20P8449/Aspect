package com.example.demo.controllers;

import com.example.demo.entities.FileRecord;
import com.example.demo.repos.FileRecordRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/files")
public class FileRecordController {
    private final FileRecordRepository repo;
    public FileRecordController(FileRecordRepository repo) { this.repo = repo; }

    @GetMapping
    public List<FileRecord> list() {
        return repo.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<FileRecord> get(@PathVariable Long id) {
        return repo.findById(id)
                   .map(ResponseEntity::ok)
                   .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public FileRecord create(@RequestBody FileRecord f) {
        return repo.save(f);
    }

    @PutMapping("/{id}")
    public ResponseEntity<FileRecord> update(@PathVariable Long id, @RequestBody FileRecord f) {
        return repo.findById(id).map(existing -> {
            existing.setName(f.getName());
            existing.setSizeBytes(f.getSizeBytes());
            existing.setHosts(f.getHosts());
            return ResponseEntity.ok(repo.save(existing));
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!repo.existsById(id)) return ResponseEntity.notFound().build();
        repo.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
