package edu.eci.arsw.application.persistence.DAO;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import edu.eci.arsw.application.entities.Chat;

@Repository
@Service
@Transactional
public interface ChatDAO extends JpaRepository<Chat, Integer> {
    @Query(value = "select * from chat c where c.usuario1 = :telefono",nativeQuery = true)
    public List<Chat> getChatsByUser(long telefono);
}

