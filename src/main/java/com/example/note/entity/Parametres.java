package com.example.note.entity;

import jakarta.persistence.*;

@Entity
@Table(name="parametres")
public class Parametres {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_matiere")
    private Matiere matiere;

    private Double seuil;

    @ManyToOne
    @JoinColumn(name = "id_operateur")
    private Operateur operateur;

    @ManyToOne
    @JoinColumn(name = "id_resolution")
    private Resolution resolution;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Matiere getMatiere() { return matiere; }
    public void setMatiere(Matiere matiere) { this.matiere = matiere; }

    public Double getSeuil() { return seuil; }
    public void setSeuil(Double seuil) { this.seuil = seuil; }

    public Operateur getOperateur() { return operateur; }
    public void setOperateur(Operateur operateur) { this.operateur = operateur; }

    public Resolution getResolution() { return resolution; }
    public void setResolution(Resolution resolution) { this.resolution = resolution; }
}
