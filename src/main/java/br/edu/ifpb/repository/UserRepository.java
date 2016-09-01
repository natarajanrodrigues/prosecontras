package br.edu.ifpb.repository;

import br.edu.ifpb.entity.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by susanneferraz on 01/09/16.
 */
public interface UserRepository extends JpaRepository<UserProfile, Integer> {

    List<UserProfile> findByEmailAndPassword(String email, String password);

    UserProfile findByEmail(String email);

    UserProfile save(UserProfile user);
}
