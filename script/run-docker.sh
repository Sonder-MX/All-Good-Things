# core container
docker run -d --name agtcore -v $PWD/.env:/.env -v $PWD/settings.py:/settings.py -v $PWD/db.sqlite3:/db.sqlite3 -v $PWD/logs:/logs agt_core

# springboot container
docker run --name scrbkend -p 8080:8080 -v $PWD/application.yml:/server/application.yml -d scrbkend
