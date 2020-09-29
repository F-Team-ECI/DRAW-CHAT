package edu.eci.arsw.application.persistence.DAO;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.eci.arsw.application.entities.User;

@Repository
@Service
@Transactional
public interface UserDAO extends JpaRepository<User, Long>{

    @Query(value = "select * from (select c.dirigido from usuario u join contacto c on u.telefono = c.propietario where u.telefono = :telefono) as conid join usuario u on u.telefono = conid.dirigido",nativeQuery = true)
    public List<User> getContacts(long telefono);

    @Modifying
    @Query(value = "insert into contacto (propietario, dirigido) values (:tUsuario1, :tUsuario2);", nativeQuery = true)
    public void setContact(long tUsuario1, long tUsuario2);
    
}
