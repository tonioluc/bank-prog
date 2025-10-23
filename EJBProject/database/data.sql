
INSERT INTO utilisateur (nom, password, idRole, idDirection) 
VALUES ('admin', 'admin123', 1, 1);
INSERT INTO utilisateur (nom, password, idRole, idDirection) 
VALUES  ('user', 'user123', 2, 2);


-- Données de test
INSERT INTO direction (type) VALUES ('courant'), ('depot'), ('pret');
INSERT INTO role (niveau) VALUES (1), (2), (3);

-- Insérer des types de mouvement
INSERT INTO type_mouvement (description) VALUES 
('Credit'),
('Debit');

-- Insérer des clients
INSERT INTO client (nom) VALUES 
('Jean Dupont'),
('Marie Martin'),
('Pierre Dubois'),
('Sophie Bernard');

-- Insérer des comptes courants
INSERT INTO compte_courant (solde, idclient) VALUES 
(100000.00, 1),
(250000.00, 2),
(50000.00, 3),
(180000.00, 4);

-- Insérer des statuts
INSERT INTO statut_mouvement_courant (description) VALUES 
('En attente'),
('Validé'),
('Refusé');