package com.example.demo.entities;

import jakarta.persistence.*;
import java.util.Set;

@Entity
@Table(name = "file_record")
public class FileRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private Long sizeBytes;

    @ManyToMany(mappedBy = "filesHosted")
    private Set<Peer> hosts;

    // getters & setters
    public Long getId() { return id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public Long getSizeBytes() { return sizeBytes; }
    public void setSizeBytes(Long sizeBytes) { this.sizeBytes = sizeBytes; }
    public Set<Peer> getHosts() { return hosts; }
    public void setHosts(Set<Peer> hosts) { this.hosts = hosts; }
}
