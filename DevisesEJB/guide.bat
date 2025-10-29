@REM -t devises-ejb → nom de l’image que tu vas créer.

@REM Docker va utiliser ton Dockerfile et créer l’image avec ton .war.
docker build -t devises-ejb:1.0 .

@REM -d → détaché (en arrière-plan).
@REM -p 8080:8080 → mappe le port 8080 du conteneur vers ton PC.
@REM --name → nom de ton conteneur.
docker stop devises-container
docker rm devises-container

sudo docker run --name devises-container \
  -p 8080:8080 \
  -p 4848:4848 \
  -p 3700:3700 \
  devises-ejb:1.0
@REM Rehefa manova zavatra 