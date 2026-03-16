package com.example.note.controller;

import com.example.note.entity.Candidat;
import com.example.note.repository.CandidatRepository;
import com.example.note.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class MainController {

    @Autowired
    private CandidatRepository candidatRepository;

    @Autowired
    private NoteService noteService;

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("candidats", candidatRepository.findAll());
        return "index";
    }

    @PostMapping("/calculate")
    public String calculate(@RequestParam("candidatId") Long candidatId, Model model) {
        Candidat candidat = candidatRepository.findById(candidatId).orElseThrow();
        List<NoteService.FinalNoteResult> results = noteService.calculateFinalNotes(candidatId);
        
        model.addAttribute("candidat", candidat);
        model.addAttribute("results", results);
        return "results";
    }
}
