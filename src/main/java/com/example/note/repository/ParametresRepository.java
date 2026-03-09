package com.example.note.repository;

import com.example.note.entity.Parametres;
import com.example.note.entity.Matiere;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ParametresRepository extends JpaRepository<Parametres, Long> {
    List<Parametres> findByMatiere(Matiere matiere);
}
