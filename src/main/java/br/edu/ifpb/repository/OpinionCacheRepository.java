package br.edu.ifpb.repository;

import br.edu.ifpb.entity.Opinion;

/**
 * Created by kieckegard on 02/09/2016.
 */
public interface OpinionCacheRepository {

    void save(Opinion opinion);

    void remove(String userEmail);

    String getCachedOpinion(String userEmail);

}
