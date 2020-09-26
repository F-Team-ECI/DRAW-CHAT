package edu.eci.arsw.application.persistence.DAO;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.eci.arsw.application.entities.User;

@Repository
@Service
@Transactional
public interface UserDAO extends JpaRepository<User, String>{

    public User getUserByPhone(String phone);

    public void createUser(User user);
}
