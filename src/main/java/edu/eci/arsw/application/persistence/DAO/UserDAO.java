package edu.eci.arsw.application.persistence.DAO;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.eci.arsw.application.entities.User;

@Repository
@Service
@Transactional
<<<<<<< HEAD
public interface UserDAO extends JpaRepository<User, Long>{

    @Query(value = "select * from (select c.dirigido from usuario u join contacto c on u.telefono = c.propietario where u.telefono = :telefono) as conid join usuario u on u.telefono = conid.dirigido",nativeQuery = true)
    public List<User> getContacts(long telefono);
=======
public interface UserDAO extends JpaRepository<User, String>{
	
>>>>>>> 3a8d891ebdd6365a044575e6ebed438f390e55bf
}
