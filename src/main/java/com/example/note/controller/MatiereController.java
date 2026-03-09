package com.example.note.controller;

import com.example.note.entity.Matiere;
import com.example.note.repository.MatiereRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/matieres")
public class MatiereController {

    @Autowired
    private MatiereRepository matiereRepository;

    @GetMapping
    public String list(Model model) {
        model.addAttribute("matieres", matiereRepository.findAll());
        return "matiere/list";
    }

    @GetMapping("/new")
    public String showForm(Model model) {
        model.addAttribute("matiere", new Matiere());
        return "matiere/form";
    }

    @PostMapping
    public String save(@ModelAttribute Matiere matiere) {
        matiereRepository.save(matiere);
        return "redirect:/matieres";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Long id, Model model) {
        model.addAttribute("matiere", matiereRepository.findById(id).orElseThrow());
        return "matiere/form";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        matiereRepository.deleteById(id);
        return "redirect:/matieres";
    }
}
