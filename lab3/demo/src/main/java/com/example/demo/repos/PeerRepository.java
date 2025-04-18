package com.example.demo.repos;

import com.example.demo.entities.Peer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PeerRepository extends JpaRepository<Peer, Long> { }
