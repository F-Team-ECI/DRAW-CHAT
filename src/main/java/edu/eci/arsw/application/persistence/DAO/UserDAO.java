package edu.eci.arsw.application.persistence.DAO;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.eci.arsw.application.entities.User;

/**
 * Mapper Usuario
 */
@Repository
@Service
@Transactional
public interface UserDAO extends JpaRepository<User, Long>{

    @Query(value = "select * from (select c.dirigido from usuario u join contacto c on u.telefono = c.propietario where u.telefono = :telefono) as conid join usuario u on u.telefono = conid.dirigido",nativeQuery = true)
    public List<User> getContacts(long telefono);

    @Query(value = "select * from (select c.dirigido from usuario u join contacto c on u.telefono = c.propietario where u.telefono = :telefono) as conid join usuario u on u.telefono = conid.dirigido where conid.dirigido not in (select g2.usuario from usuario u join gruposusuario g2 on u.telefono = g2.usuario where g2.grupo=:idgrupo)",nativeQuery = true)
    public List<User> getContactsExGroup(long telefono, int idgrupo);

    @Modifying
    @Query(value = "insert into contacto (propietario, dirigido) values (:tUsuario1, :tUsuario2);", nativeQuery = true)
    public void setContact(long tUsuario1, long tUsuario2);
}
