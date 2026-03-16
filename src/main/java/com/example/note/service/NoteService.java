package com.example.note.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.note.entity.Matiere;
import com.example.note.entity.Note;
import com.example.note.entity.Parametres;
import com.example.note.repository.NoteRepository;
import com.example.note.repository.ParametresRepository;

@Service
public class NoteService {

    @Autowired
    private NoteRepository noteRepository;

    @Autowired
    private ParametresRepository parametresRepository;

    public static class FinalNoteResult {
        private Matiere matiere;
        private Double finalNote;

        public FinalNoteResult(Matiere matiere, Double finalNote) {
            this.matiere = matiere;
            this.finalNote = finalNote;
        }

        public Matiere getMatiere() { return matiere; }
        public Double getFinalNote() { return finalNote; }
    }

    public List<FinalNoteResult> calculateFinalNotes(Long candidatId) {
        List<Note> notes = noteRepository.findByCandidatId(candidatId);
        Map<Matiere, List<Note>> notesByMatiere = notes.stream()
                .collect(Collectors.groupingBy(Note::getMatiere));

        List<FinalNoteResult> results = new ArrayList<>();

        for (Map.Entry<Matiere, List<Note>> entry : notesByMatiere.entrySet()) {
            Matiere matiere = entry.getKey();
            List<Note> matiereNotes = entry.getValue();

            if (matiereNotes.isEmpty()) continue;

            double max = matiereNotes.stream().mapToDouble(Note::getNote).max().orElse(0.0);
            double min = matiereNotes.stream().mapToDouble(Note::getNote).min().orElse(0.0);
            double moyenne = matiereNotes.stream().mapToDouble(Note::getNote).average().orElse(0.0);
            
            // Calcul de la somme des différences entre toutes les paires uniques
            double sommeDifferences = 0.0;
            for (int i = 0; i < matiereNotes.size(); i++) {
                for (int j = i + 1; j < matiereNotes.size(); j++) {
                    sommeDifferences += Math.abs(matiereNotes.get(i).getNote() - matiereNotes.get(j).getNote());
                }
            }

            List<Parametres> params = parametresRepository.findByMatiere(matiere);
            Double finalValue = 0.0;

            for (Parametres p : params) {
                boolean match = false;
                String op = p.getOperateur().getNom();
                double seuil = p.getSeuil();

                if (op.equals("<") && sommeDifferences < seuil) match = true;
                else if (op.equals(">") && sommeDifferences > seuil) match = true;
                else if (op.equals("<=") && sommeDifferences <= seuil) match = true;
                else if (op.equals(">=") && sommeDifferences >= seuil) match = true;

                if (match) {
                    String res = p.getResolution().getNom();
                    if (res.equalsIgnoreCase("plus petit")) finalValue = min;
                    else if (res.equalsIgnoreCase("plus grand")) finalValue = max;
                    else if (res.equalsIgnoreCase("moyenne")) finalValue = moyenne;
                    break; // On s'arrête dès qu'une condition est remplie pour cette matière
                }
            }
            results.add(new FinalNoteResult(matiere, finalValue));
        }

        return results;
    }
}
