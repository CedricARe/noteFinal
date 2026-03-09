-- Candidats
INSERT INTO Candidat (nom, prenom, matricule) VALUES
('Rakoto', 'Jean', 'MAT001'),
('Rabe', 'Marie', 'MAT002'),
('Andrianina', 'Paul', 'MAT003'),
('Razanaka', 'Luc', 'MAT004'),
('Rakotomalala', 'Sofia', 'MAT005');

-- Correcteurs
INSERT INTO Correcteur (nom) VALUES
('Rasoa Andry'),
('Rasoanaivo Hery'),
('Rajaonarison Lala'),
('Rakotondrazaka Mamy'),
('Ratsimbazafy Fidy'),
('Rabe Miary');

-- Matières
INSERT INTO Matiere (nom) VALUES
('Mathematiques'),
('Physique'),
('Chimie');

-- Résolutions
INSERT INTO Resolution (nom) VALUES
('plus petit'),
('plus grand'),
('moyenne');

-- Opérateurs
INSERT INTO Operateur (nom) VALUES
('<'),
('>'),
('=');

-- Paramètres
INSERT INTO Parametres (id_matiere, seuil, id_operateur, id_resolution) VALUES
(1,8,1,1),
(1,7,2,2),
(2,6,1,1),
(2,3,2,2),
(3,12,1,1),
(3,10,2,2);

INSERT INTO Note (id_candidat, id_matiere, id_correcteur, note) VALUES
(1,1,1,10.5),
(1,1,2,9),
(1,1,3,13),
(1,2,4,12),
(1,2,5,12),
(1,2,6,12),
(1,3,1,17),
(1,3,2,9),
(1,3,3,14),

(2,1,4,8),
(2,1,5,11),
(2,1,6,9),
(2,2,1,10),
(2,2,2,12),
(2,2,3,11),
(2,3,4,13),
(2,3,5,12),
(2,3,6,14),

(3,1,1,15),
(3,1,2,14),
(3,1,3,16),
(3,2,4,9),
(3,2,5,10),
(3,2,6,11),
(3,3,1,18),
(3,3,2,17),
(3,3,3,16);