package com.example.note.controller;

import com.example.note.entity.Parametres;
import com.example.note.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/parametres")
public class ParametreController {

    @Autowired
    private ParametresRepository parametresRepository;
    @Autowired
    private MatiereRepository matiereRepository;
    @Autowired
    private OperateurRepository operateurRepository;
    @Autowired
    private ResolutionRepository resolutionRepository;

    @GetMapping
    public String list(Model model) {
        model.addAttribute("parametres", parametresRepository.findAll());
        return "parametre/list";
    }

    @GetMapping("/new")
    public String showForm(Model model) {
        model.addAttribute("parametre", new Parametres());
        model.addAttribute("matieres", matiereRepository.findAll());
        model.addAttribute("operateurs", operateurRepository.findAll());
        model.addAttribute("resolutions", resolutionRepository.findAll());
        return "parametre/form";
    }

    @PostMapping
    public String save(@ModelAttribute Parametres parametre) {
        parametresRepository.save(parametre);
        return "redirect:/parametres";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Long id, Model model) {
        model.addAttribute("parametre", parametresRepository.findById(id).orElseThrow());
        model.addAttribute("matieres", matiereRepository.findAll());
        model.addAttribute("operateurs", operateurRepository.findAll());
        model.addAttribute("resolutions", resolutionRepository.findAll());
        return "parametre/form";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        parametresRepository.deleteById(id);
        return "redirect:/parametres";
    }
}
