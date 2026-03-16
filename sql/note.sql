CREATE DATABASE bacc_db;
\c bacc_db;

-- Table des correcteurs
CREATE TABLE IF NOT EXISTS Correcteur (
    id SERIAL PRIMARY KEY,
    nom VARCHAR(255) UNIQUE NOT NULL
);

-- Table des matières
CREATE TABLE IF NOT EXISTS Matiere (
    id SERIAL PRIMARY KEY,
    nom VARCHAR(255) UNIQUE NOT NULL
);

-- Table des candidats
CREATE TABLE IF NOT EXISTS Candidat (
    id SERIAL PRIMARY KEY,
    nom VARCHAR(255) UNIQUE NOT NULL,
    prenom VARCHAR(255) UNIQUE NOT NULL,
    matricule VARCHAR(255) UNIQUE NOT NULL 
);

-- Table des résolutions
CREATE TABLE IF NOT EXISTS Resolution (
    id SERIAL PRIMARY KEY,
    nom VARCHAR(255) NOT NULL -- 'plus petit', 'plus grand', 'moyen'1
);

-- Table des opérateurs
CREATE TABLE IF NOT EXISTS Operateur (
    id SERIAL PRIMARY KEY,
    nom VARCHAR(255) NOT NULL-- '>', '<', '>='
);

-- Table des paramètres
CREATE TABLE IF NOT EXISTS Parametres (
    id SERIAL PRIMARY KEY,
    id_matiere INT NOT NULL REFERENCES Matiere(id),
    seuil NUMERIC NOT NULL,
    id_operateur INT NOT NULL REFERENCES Operateur(id),
    id_resolution INT NOT NULL REFERENCES Resolution(id)
);

-- Table des notes
CREATE TABLE IF NOT EXISTS Note (
    id SERIAL PRIMARY KEY,
    id_candidat INT NOT NULL REFERENCES Candidat(id),
    id_matiere INT NOT NULL REFERENCES Matiere(id),
    id_correcteur INT NOT NULL REFERENCES Correcteur(id),
    note FLOAT NOT NULL 
);

CREATE TABLE Notefinal (
    id_notefinal SERIAL PRIMARY KEY,
    id_candidat INT REFERENCES Candidat(id),
    id_matiere INT REFERENCES Matiere(id),
    note_finale_valeur DECIMAL(5,2)
);

ALTER TABLE Note
ADD CONSTRAINT unique_note
UNIQUE(id_candidat, id_matiere, id_correcteur);