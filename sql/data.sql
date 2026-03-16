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
(1,7,1,2),
(1,7,4,3),
(2,2,2,1),
(2,2,3,2);

INSERT INTO Note (id_candidat, id_matiere, id_correcteur, note) VALUES
(1,1,1,15),
(1,1,2,10),
(1,1,3,12),
(1,2,1,10),
(1,2,2,10),

(2,1,1,9),
(2,1,2,8),
(2,1,3,11),
(2,2,1,13),
(2,2,2,11);
