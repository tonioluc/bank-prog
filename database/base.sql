\c postgres;
drop database if exists ejb_app;
CREATE DATABASE ejb_app;
\c ejb_app;

CREATE TABLE direction(
   idDirection SERIAL,
   type VARCHAR(50) ,
   PRIMARY KEY(idDirection)
);

CREATE TABLE role(
   idRole SERIAL,
   niveau INTEGER,
   PRIMARY KEY(idRole)
);

CREATE TABLE client(
   idClient SERIAL,
   nom VARCHAR(50)  NOT NULL,
   PRIMARY KEY(idClient)
);

CREATE TABLE type_mouvement(
   idTypeMouvement SERIAL,
   description VARCHAR(50) ,
   PRIMARY KEY(idTypeMouvement)
);

CREATE TABLE statut_mouvement_courant(
   idStatut SERIAL,
   description VARCHAR(50) ,
   PRIMARY KEY(idStatut)
);

CREATE TABLE utilisateur(
   idUtilisateur SERIAL,
   nom VARCHAR(50) ,
   password VARCHAR(50) ,
   idRole INTEGER NOT NULL,
   idDirection INTEGER NOT NULL,
   PRIMARY KEY(idUtilisateur),
   FOREIGN KEY(idRole) REFERENCES role(idRole),
   FOREIGN KEY(idDirection) REFERENCES direction(idDirection)
);

CREATE TABLE compte_courant(
   idCompte SERIAL,
   solde NUMERIC(15,2)  ,
   idClient INTEGER NOT NULL,
   PRIMARY KEY(idCompte),
   UNIQUE(idClient),
   FOREIGN KEY(idClient) REFERENCES client(idClient)
);

CREATE TABLE mouvement_courant(
   idMouvement SERIAL,
   montant NUMERIC(15,2)  ,
   idTypeMouvement INTEGER NOT NULL,
   idCompte INTEGER NOT NULL,
   PRIMARY KEY(idMouvement),
   FOREIGN KEY(idTypeMouvement) REFERENCES type_mouvement(idTypeMouvement),
   FOREIGN KEY(idCompte) REFERENCES compte_courant(idCompte)
);

CREATE TABLE historique_statut(
   idMouvement INTEGER,
   idStatut INTEGER,
   dateDeChangement DATE,
   PRIMARY KEY(idMouvement, idStatut),
   FOREIGN KEY(idMouvement) REFERENCES mouvement_courant(idMouvement),
   FOREIGN KEY(idStatut) REFERENCES statut_mouvement_courant(idStatut)
);
