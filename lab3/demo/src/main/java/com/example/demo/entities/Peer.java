package com.example.demo.entities;

import jakarta.persistence.*;
import java.util.Set;

@Entity
@Table(name = "peer")
public class Peer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String ipAddress;
    private Integer port;
    private Boolean online;

    @ManyToMany
    @JoinTable(
        name = "peer_files",
        joinColumns = @JoinColumn(name = "peer_id"),
        inverseJoinColumns = @JoinColumn(name = "file_id")
    )
    private Set<FileRecord> filesHosted;

    // getters & setters
    public Long getId() { return id; }
    public String getIpAddress() { return ipAddress; }
    public void setIpAddress(String ipAddress) { this.ipAddress = ipAddress; }
    public Integer getPort() { return port; }
    public void setPort(Integer port) { this.port = port; }
    public Boolean getOnline() { return online; }
    public void setOnline(Boolean online) { this.online = online; }
    public Set<FileRecord> getFilesHosted() { return filesHosted; }
    public void setFilesHosted(Set<FileRecord> filesHosted) { this.filesHosted = filesHosted; }
}
