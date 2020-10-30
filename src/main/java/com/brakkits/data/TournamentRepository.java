package com.brakkits.data;

import com.brakkits.models.Attendee;
import com.brakkits.models.Tournament;
import com.brakkits.models.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

/**
 * Ali Cooper
 * brakkits
 * CST-451
 **/
public interface TournamentRepository extends CrudRepository<Tournament, Long> {

    /**
     * find tournaments with name
     * @param user User
     * @return List of Tournaments
     */
    List<Tournament> findTournamentsByOwner(User user);

    /**
     * return Optional of Tournaments
     * @param title String
     * @return Optional of Tournaments
     */
     Optional<Tournament> findTournamentByTitle(String title);

    /**
     * used for searching tourneys
     * @param title String
     * @return String
     */
     List<Tournament> findTournamentsByTitleContaining(String title);

    /**
     * returns subset of listings
     * @param max
     * @return Iterable tourney
     */
    List<Tournament> findAll(Pageable max);

    /**
     * deletes a tournament by its title
     * @param title String
     * @return bool
     */
    Boolean deleteByTitle(String title);

}
