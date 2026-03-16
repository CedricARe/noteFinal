-- Candidats
INSERT INTO Candidat (nom, prenom, matricule) VALUES
('Candidat1', 'c1', 'MAT001'),
('Candidat2', 'c2', 'MAT002');

-- Correcteurs
INSERT INTO Correcteur (nom) VALUES
('Correcteur1'),
('Correcteur2'),
('Correcteur3');

-- Matières
INSERT INTO Matiere (nom) VALUES
('Java'),
('Php');

-- Résolutions
INSERT INTO Resolution (nom) VALUES
('plus petit'),
('plus grand'),
('moyenne');

-- Opérateurs
INSERT INTO Operateur (nom) VALUES
('<'),
('<='),
('>'),
('>=');

-- Paramètres
INSERT INTO Parametres (id_matiere, seuil, id_operateur, id_resolution) VALUES
(1,5,4,2),
(1,9,1,3);

INSERT INTO Note (id_candidat, id_matiere, id_correcteur, note) VALUES
(1,1,1,10),
(1,1,2,18);