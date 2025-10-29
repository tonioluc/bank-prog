# Image de base : OmniFish GlassFish 7
FROM omnifish/glassfish:7.0.15

# Variables d'environnement
ENV GLASSFISH_HOME=/opt/glassfish7

# Copier le WAR dans autodeploy
COPY target/DevisesEJB-1.0-SNAPSHOT.war ${GLASSFISH_HOME}/glassfish/domains/domain1/autodeploy/


# Exposer les ports
EXPOSE 8080 4848 8181

# Script de démarrage personnalisé
CMD ["asadmin", "start-domain", "--verbose"]