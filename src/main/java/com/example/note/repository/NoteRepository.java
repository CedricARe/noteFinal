package com.example.note.repository;

import com.example.note.entity.Note;
import com.example.note.entity.Candidat;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface NoteRepository extends JpaRepository<Note, Long> {
    List<Note> findByCandidatId(Long candidatId);
}
