package com.example.note.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.note.entity.Candidat;
import com.example.note.repository.CandidatRepository;

@Controller
@RequestMapping("/candidats")
public class CandidatController {

    @Autowired
    private CandidatRepository candidatRepository;

    @GetMapping
    public String list(Model model) {
        model.addAttribute("candidats", candidatRepository.findAll());
        return "candidat/list";
    }

    @GetMapping("/new")
    public String showForm(Model model) {
        model.addAttribute("candidat", new Candidat());
        return "candidat/form";
    }

    @PostMapping
    public String save(@ModelAttribute Candidat candidat) {
        candidatRepository.save(candidat);
        return "redirect:/candidats";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Long id, Model model) {
        model.addAttribute("candidat", candidatRepository.findById(id).orElseThrow());
        return "candidat/form";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {   
        candidatRepository.deleteById(id);
        return "redirect:/candidats";
    }
}