# to run mysql in docker
docker compose up -d

# check running containers
docker ps

# ssh to containers
docker exec -it mysql_db bash

# open mysql under root
mysql -u root -p

# check available databases in mysql
> SHOW DATABASES;

# https://stackoverflow.com/questions/40149880/docker-mysql-root-password-do-not-work
## remove volumes
docker volume ls
docker volume rm topic07-task01_db
