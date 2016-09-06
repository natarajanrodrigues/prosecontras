## MANUAL DE IMPLANTAÇÃO

### Redis - Sistema de Cache

- Baixe uma versão estável do Redis [aqui](http://redis.io/download).
- Para inicializar o Redis, no terminal, insira o seguinte comando:
```
redis-server
```

### MongoDB - Persistência de Opiniões e imagens usando GRIDFS

- Baixe uma versão estável do MongoDB [aqui](https://www.mongodb.com/download-center).
- Para inicializar o servidor do Mongo no terminal, insira o comando: 
```
mongod
```
- Para inicializar um client do Mongo no terminal, insira o comando: 
```
mongo
```
- Crie uma base de dados com o nome `prosecontras` e uma outra base de dados com o nome `topicimages` no client do mongo aberto utilizando os seguintes comandos:
```
use topicimages
use prosecontras
```
- Ainda no client do mongo, na base de dados `prosecontras`, crie uma coleção chamada `opinion` através do comando:
```
db.createCollection("opinion");
```

### Neo4J - Persistência dos relacionamentos entre Usuário e Tópicos votados.
- Baixe uma versão estável do Neo4J [aqui](https://neo4j.com/download/).
- Inicialize o neo4J.

### PostgreSQL - Persistência de Usuários e Tópicos

- Instale uma versão estável do PostgreSQL [aqui](https://www.postgresql.org/download/).
- Através do pgadmin ou por linha de comando, crie uma base de dados com o nome `prosecontras`.

### That's it, you're ready to go


