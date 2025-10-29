-- fichier : banque_db.sql

\c postgres
DROP DATABASE IF EXISTS banque_db;
CREATE DATABASE banque_db;
\c banque_db


CREATE TABLE client (
    id SERIAL PRIMARY KEY,
    nom VARCHAR(50) NOT NULL,
    prenom VARCHAR(50) NOT NULL,
    email VARCHAR(100),
    telephone VARCHAR(20)
);

CREATE TABLE direction (
    id SERIAL PRIMARY KEY,
    libelle VARCHAR(100) NOT NULL,
    niveau INT NOT NULL
);

CREATE TABLE utilisateur (
    id SERIAL PRIMARY KEY,
    email VARCHAR(100) NOT NULL UNIQUE,
    mot_de_passe VARCHAR(255) NOT NULL,
    direction_id INT NOT NULL REFERENCES direction(id),
    role INT NOT NULL, -- 1 = CONSULTANT, 2 = EMPLOYE, 3 = MANAGER, 4 = ADMIN
    date_creation DATE NOT NULL DEFAULT CURRENT_DATE
);

CREATE TABLE action_role (
    id SERIAL PRIMARY KEY,
    nom_table VARCHAR(50) NOT NULL,
    action VARCHAR(50) NOT NULL,
    role INT NOT NULL
);

-- 3. Types de mouvements et prêts

CREATE TABLE type_mouvement (
    id SERIAL PRIMARY KEY,
    libelle VARCHAR(30) NOT NULL,
    description VARCHAR(100)
);

CREATE TABLE type_pret (
    id SERIAL PRIMARY KEY,
    libelle VARCHAR(50) NOT NULL,
    nb_mois_remboursement INTEGER NOT NULL,
    interet NUMERIC(5,2) NOT NULL,
    montant NUMERIC(15,2) NOT NULL
);

-- 4. Comptes courants et mouvements

CREATE TABLE compte_courant (
    id SERIAL PRIMARY KEY,
    client_id INT NOT NULL REFERENCES client(id),
    solde NUMERIC(12,2) NOT NULL DEFAULT 0,
    date_maj DATE NOT NULL DEFAULT CURRENT_DATE
);

CREATE TABLE mouvement_courant (
    id SERIAL PRIMARY KEY,
    compte_id INT NOT NULL REFERENCES compte_courant(id),
    type_mouvement_id INT NOT NULL REFERENCES type_mouvement(id),
    montant NUMERIC(12,2) NOT NULL CHECK (montant > 0),
    date_mouvement DATE NOT NULL DEFAULT CURRENT_DATE,
    statut INT NOT NULL CHECK(statut IN (0, 1, 2)) DEFAULT 0 -- 0: EN_ATTENTE, 1: VALIDE, 2: REFUSE
);

-- 5. Comptes dépôt et mouvements

CREATE TABLE compte_depot (
    id SERIAL PRIMARY KEY,
    client_id INT NOT NULL REFERENCES client(id),
    solde NUMERIC(12,2) NOT NULL DEFAULT 0,
    taux_interet NUMERIC(5,2) NULL,
    plafond_retrait NUMERIC(12,2),
    date_dernier_interet DATE NOT NULL DEFAULT CURRENT_DATE,
    actif BOOLEAN NOT NULL DEFAULT FALSE
);

CREATE TABLE mouvement_depot (
    id SERIAL PRIMARY KEY,
    compte_id INT NOT NULL REFERENCES compte_depot(id),
    type_mouvement_id INT NOT NULL REFERENCES type_mouvement(id),
    montant NUMERIC(12,2) NOT NULL CHECK (montant > 0),
    date_mouvement TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE parametre (
    id SERIAL PRIMARY KEY,
    plafond_retrait_global NUMERIC(12,2) NOT NULL,
    taux_interet_depot NUMERIC(5,2) NOT NULL
);

-- 6. Prêts et remboursements

CREATE TABLE pret (
    id SERIAL PRIMARY KEY,
    client_id INT NOT NULL REFERENCES client(id),
    type_pret_id INT NOT NULL REFERENCES type_pret(id),
    taux_interet NUMERIC(5,2) NOT NULL,
    montant NUMERIC(15,2) NOT NULL,
    date_debut DATE NOT NULL,
    date_fin DATE NOT NULL,
    etat VARCHAR(20) NOT NULL CHECK (etat IN ('ENCOURS','REMBOURSE'))
);

CREATE TABLE remboursement (
    id SERIAL PRIMARY KEY,
    pret_id INT NOT NULL REFERENCES pret(id),
    montant NUMERIC(12,2) NOT NULL CHECK (montant > 0),
    interet_payes NUMERIC(12,2) DEFAULT 0,
    capital_rembourse NUMERIC(12,2) DEFAULT 0,
    date_remboursement DATE NOT NULL DEFAULT CURRENT_DATE
);

-- 7. Données initiales (exemples)

INSERT INTO direction (libelle, niveau) VALUES
('Direction Générale Comptes', 10),
('Direction Comptes Courants', 😎,
('Direction Dépôts', 😎,
('Service Gestion Comptes', 5),
('Service Mouvements', 5),
('Équipe Saisie', 3),
('Consultants Comptes', 1);

INSERT INTO utilisateur (email, mot_de_passe, direction_id, role) VALUES
('admin.comptes@banque.com', 'test', 1, 4),
('manager.comptes@banque.com', 'test', 2, 3),
('employe.comptes@banque.com', 'test', 4, 2),
('consultant.comptes@banque.com', 'test', 7, 1);

INSERT INTO action_role (nom_table, action, role) VALUES
('compte_courant', 'CREATE', 4),
('compte_courant', 'READ', 4),
('compte_courant', 'UPDATE', 4),
('compte_courant', 'DELETE', 4),
('compte_courant', 'VALIDATE', 4),
('mouvement_courant', 'CREATE', 4),
('mouvement_courant', 'READ', 4),
('mouvement_courant', 'UPDATE', 4),
('mouvement_courant', 'DELETE', 4),
('mouvement_courant', 'VALIDATE', 4),
('compte_courant', 'CREATE', 3),
('compte_courant', 'READ', 3),
('compte_courant', 'UPDATE', 3),
('mouvement_courant', 'CREATE', 3),
('mouvement_courant', 'READ', 3),
('mouvement_courant', 'UPDATE', 3),
('compte_courant', 'CREATE', 2),
('compte_courant', 'READ', 2),
('compte_courant', 'UPDATE', 2),
('mouvement_courant', 'CREATE', 2),
('mouvement_courant', 'READ', 2),
('compte_courant', 'READ', 1),
('mouvement_courant', 'READ', 1);

INSERT INTO type_mouvement (id, libelle, description) VALUES
(1, 'DEPOT', 'Dépôt d''argent sur un compte'),
(2, 'RETRAIT', 'Retrait d''argent d''un compte'),
(3, 'VIREMENT_SORTANT', 'Envoi vers un autre compte'),
(4, 'VIREMENT_ENTRANT', 'Réception depuis un autre compte'),
(5, 'INTERET', 'Application d''intérêts sur dépôt'),
(6, 'FRAIS', 'Prélèvement de frais bancaires');

INSERT INTO parametre (plafond_retrait_global, taux_interet_depot) VALUES
(1000000.00, 2.0);

INSERT INTO type_pret (libelle, nb_mois_remboursement, interet, montant) VALUES
('Prêt Etudiant', 36, 24.00, 5000000),
('Prêt Immobilier', 240, 18.00, 200000000),
('Prêt Court Terme', 12, 30.00, 2000000),
('Prêt Long Terme', 60, 20.00, 30000000);

-- Ajoute ici d'autres données de test si besoin