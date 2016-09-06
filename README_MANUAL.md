## MANUAL DE IMPLANTAÇÃO

### Redis - Sistema de Cache

- Baixe e instale uma versão estável do Redis [aqui](http://redis.io/download).
- Para inicializar o Redis, no terminal, insira o seguinte comando:
```
redis-server
```

### MongoDB - Persistência de Opiniões e imagens usando GRIDFS

- Baixe e instale uma versão estável do MongoDB [aqui](https://www.mongodb.com/download-center).
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
- Baixe e instale uma versão estável do Neo4J [aqui](https://neo4j.com/download/).
- Quando utilizar o Neo4J, preste atenção e copie o caminho definido como seu Database Location.
- Abrir a classe br.edu.ifpb.repository.UserTopicRepositoryNeo4jImpl e informar endereço correto do caminho para seu banco neo4j na variável `path`.
- Não é preciso inicializar o aplicativo do Neo4J, antes de iniciar o Spring. O aplicativo do Neo4J deve estar fechado, caso contrário ocorre um conflito. 

### PostgreSQL - Persistência de Usuários e Tópicos

- Instale uma versão estável do PostgreSQL [aqui](https://www.postgresql.org/download/).
- Através do pgadmin ou por linha de comando, crie uma base de dados com o nome `prosecontras`.

### Rodando a aplicação

- Compilar o projeto e fazer run da classe br.edu.ifpb.ProsecontrasApplication

### That's it, you're ready to go


