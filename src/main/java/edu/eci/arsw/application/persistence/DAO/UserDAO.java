package edu.eci.arsw.application.persistence.DAO;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.eci.arsw.application.entities.User;

@Repository
public interface UserDAO extends CrudRepository<User, Integer> {

    public User getUserByPhone(String phone);

    public void createUser(User user);
}
