# gitapi

## Pré requisitos: 
- Docker
- Docker Compose

## Comandos:
```
git clone https://github.com/liryanne/gitapi.git
cd gitapi
DB_PASSWORD=postgres docker-compose up --build
```

## Rotas:
- /repositories?username=`username`&from_local=`false`
- /repositories/`username`/`repository`?save_data=`true`

## Coverage report: 
https://liryanne.github.io/gitapi
