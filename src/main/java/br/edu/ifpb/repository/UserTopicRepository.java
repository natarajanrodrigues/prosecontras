package br.edu.ifpb.repository;

import br.edu.ifpb.entity.Topic;
import br.edu.ifpb.entity.UserProfile;
import br.edu.ifpb.enums.Status;
import org.neo4j.graphdb.Node;

import java.util.Map;
import java.util.Set;

/**
 * Created by kieckegard on 01/09/2016.
 */
public interface UserTopicRepository {

    /**
     * Cria um novo relacinamento entre Usuário e Tópico.
     * @param user - Instancia de UserProfile que representa o usuário que está se relacionando com o tópico.
     * @param topic - Instancia de Topic que representa o Tópico no qual o usuário irá se relacionar.
     * @param status - Tipo do relacionamento entre Usuário e Tópico. Pode ser FOR (A favor) e AGAINST (Contra).
     */
    void newRelationship(UserProfile user, Topic topic, Status status);

    /**
     * Busca os tópicos sugeridos de acordo com uma coleção de tópicos A FAVOR passada por parâmetro.
     * @param topics
     * @return
     */
    Set<Long> getSuggestedTopicsByForTopics(Set<Topic> topics);

    /**
     * Retorna a quantidade de usuários que votaram a favor em um determinado tópico.
     * @param topic - Instancia de Tópico
     * @return - Instancia de Integer representando a quantidade de usuários.
     */
    Integer getForQtde(Topic topic);
    /**
     * Retorna a quantidade de usuários que votaram contra em um determinado tópico.
     * @param topic - Instancia de Tópico
     * @return - Instancia de Integer representando a quantidade de usuários.
     */
    Integer getAgainstQtde(Topic topic);

    /**
     * Retorna a quantidade de usuários que votaram em pelo menos um tópico.
     * @return - Instancia de Integer representando a quantidade de usuários.
     */
    Integer getWhoVotedQtd();

    /**
     * Busca a relação de usuários que reagiram a um tópico específico e a um outro tópico específico.
     * Pega a quantidade de usuários que reagiram ao startTopic e startStatus, desses usuários, pegamos a quantidade
     * que reagiram também ao targetTopic e targetStatus e esses valores são retornados em um Map<String, Integer>.
     * keys = (total,found), total = representa usuarios que reagiram a startTopic e startStatus.
     * found = dos usuários que reagiram ao startTopic e startStatus, found representa os que também reagiram a targetTopic
     * e targetStatus.
     * @param startTopic
     * @param startStatus
     * @param targetTopic
     * @param targetStatus
     * @return
     */
    Map<String, Integer> getTrends(Topic startTopic, Status startStatus, Topic targetTopic, Status targetStatus);




}
