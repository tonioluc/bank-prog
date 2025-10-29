# Reconstruire l'image Docker
cd /home/antonio/Documents/GitHub/bank-prog/DevisesEJB
mvn clean package
docker build -t devises-ejb:2.0 .

# Arrêter et supprimer l'ancien conteneur
docker stop devises-container
docker rm devises-container

# Lancer le nouveau conteneur
docker run --name devises-container \
  -p 8081:8080 \
  -p 4849:4848 \
  -p 3701:3700 \
  devises-ejb:2.0