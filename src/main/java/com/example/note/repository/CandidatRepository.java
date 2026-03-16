package com.example.note.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.note.entity.Candidat;
public interface CandidatRepository extends JpaRepository<Candidat, Long> {
}