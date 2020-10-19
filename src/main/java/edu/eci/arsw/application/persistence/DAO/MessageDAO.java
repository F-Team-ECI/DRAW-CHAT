package edu.eci.arsw.application.persistence.DAO;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import edu.eci.arsw.application.entities.Message;

@Repository
@Service
@Transactional
public interface MessageDAO extends JpaRepository<Message, Integer> {
    @Query(value = "select * from mensaje m2 where m2.chat = :chatid ",nativeQuery = true)
    public List<Message> getMessagesByChat(int chatid);

    @Query(value = "select * from mensaje m where m.grupo = :groupid ",nativeQuery = true)
    public List<Message> getMessagesByGroup(int groupid);
}
