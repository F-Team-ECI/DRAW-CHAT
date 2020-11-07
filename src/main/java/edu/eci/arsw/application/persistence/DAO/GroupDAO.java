package edu.eci.arsw.application.persistence.DAO;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import edu.eci.arsw.application.entities.Group;


@Repository
@Service
@Transactional
public interface GroupDAO extends JpaRepository<Group, Integer> {
    @Modifying
    @Query(value = "insert into gruposusuario (usuario, grupo, rol) values (:tUsuario,:groupId, :role);", nativeQuery = true)
    public void addUserToGroup(long tUsuario, int groupId, String role);

    @Query(value = "select rol from gruposusuario g where g.grupo =:groupId and g.usuario =:tUsuario ", nativeQuery = true)
    public String getRole(long tUsuario, int groupId);

}
