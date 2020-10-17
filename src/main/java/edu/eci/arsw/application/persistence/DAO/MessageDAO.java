package edu.eci.arsw.application.persistence.DAO;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import edu.eci.arsw.application.entities.Message;

@Repository
@Service
@Transactional
public interface MessageDAO extends JpaRepository<Message, Integer> {

}
