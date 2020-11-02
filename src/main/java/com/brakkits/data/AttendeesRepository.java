package com.brakkits.data;

import com.brakkits.models.Attendee;
import com.brakkits.models.Tournament;
import com.brakkits.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Ali Cooper
 * brakkits
 * CST-452
 * 10/18/2019
 * Jpa model repository for attendees
 **/
public interface AttendeesRepository extends JpaRepository<Attendee, Long> {
}
