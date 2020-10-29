package com.brakkits.data;

import com.brakkits.models.Tournament;
import com.brakkits.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

/**
 * Ali Cooper
 * brakkits
 * CST-451
 * 3/1/2020
 **/
public interface UserRepository extends CrudRepository<User, Long> {

    /**
     * find all users
     * @return List<User>
     */
    public List<User> findAll();

    /**
     * find a User By Email
     * @param email String
     * @return Optional<User>
     */
    public Optional<User> findUserByEmail(String email);

    /**
     * check to see if user exists with email as a param
     * @param email String
     * @return boolean
     */
    public boolean existsUserByEmail(String email);

    /**
     * find top 500 players on leaderboard
     * @return List<User>
     */
    public List<User> findTop500ByOrderByPlacementAsc();

    /**
     * tries to find user by tag
     * @param tag String
     * @return Optional<User>
     */
    public Optional<User> findByTag(String tag);

}
