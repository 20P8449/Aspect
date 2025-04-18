package com.example.demo.controllers;

import com.example.demo.entities.Peer;
import com.example.demo.repos.PeerRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/peers")
public class PeerController {
    private final PeerRepository repo;
    public PeerController(PeerRepository repo) { this.repo = repo; }

    @GetMapping
    public List<Peer> list() {
        return repo.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Peer> get(@PathVariable Long id) {
        return repo.findById(id)
                   .map(ResponseEntity::ok)
                   .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Peer create(@RequestBody Peer p) {
        return repo.save(p);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Peer> update(@PathVariable Long id, @RequestBody Peer p) {
        return repo.findById(id).map(existing -> {
            existing.setIpAddress(p.getIpAddress());
            existing.setPort(p.getPort());
            existing.setOnline(p.getOnline());
            existing.setFilesHosted(p.getFilesHosted());
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
