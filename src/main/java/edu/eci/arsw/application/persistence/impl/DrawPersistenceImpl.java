package edu.eci.arsw.application.persistence.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.eci.arsw.application.entities.User;
import edu.eci.arsw.application.exceptions.AppException;
import edu.eci.arsw.application.persistence.DrawPersistenceService;
import edu.eci.arsw.application.persistence.DAO.UserDAO;

@Service
public class DrawPersistenceImpl implements DrawPersistenceService {

    @Autowired
    private UserDAO userDAO;
    

    @Override
    public void addUser(User user) throws  AppException{
        User current = getUser(user.getTelefono());
        System.out.println(user);
        if(current!=null){
            throw new AppException("User already registered");
        } else if(!correctUser(user)){
            throw new AppException("Incorrect credentials");
        }
        userDAO.save(user);
    }


    /**
     * Verificar que el usuario sea valido
     * @param user el usuario a verificar
     * @return true si es valido, false si no lo es
     */
    private boolean correctUser(User user) {
        boolean ans = true;
        if (user.getContraseña() == null || !user.validTelefono() || !user.validPass()) {
            ans = false;
        }
        if (user.getNombre() == null || !user.validName()) {
            ans = false;
        }
        if (user.getApellido() == null || !user.validApellido()) {
            ans = false;
        }
        return ans;
    }

    @Override
    public List<User> getUsers() {
        return userDAO.findAll();
    }

    @Override
    public User getUser(long telefono){
        List<User> user = userDAO.findAll();
        User usuario = null;

        for (User us : user) {
            if(us.getTelefono()==telefono){
                usuario = us;
                System.out.println(usuario.toString());
                break;
            }
        }
        return usuario;
    }

    public List<User> getContacts(long telefono) {
        List<User> contacts = userDAO.getContacts(telefono);
        return contacts;
    }

    @Override
    public void addContact(long tUsuario1, long tUsuario2) throws AppException {
        boolean check = checkPhone(tUsuario1) && checkPhone(tUsuario2);
        if(!check){
            throw new AppException(AppException.USER_NOT_REGISTERED);
        }
        userDAO.setContact(tUsuario1,tUsuario2);
        System.out.println("Contacto Registrado");
        
    }

    /**
     * Validar el telefono del usuario este en la base de datos
     * @param telefono el numero de telefono del usuario
     * @return true si es valido, false si no lo es
     */
    private boolean checkPhone(long telefono) {
        Optional<User> user = userDAO.findById(telefono);
        return user.isPresent();
    }

	@Override
	public void deleteUser(long telefono) {
		userDAO.delete(getUser(telefono));
		
	}

    @Override
    public void updateUser(User user) {
        User loaded = getUser(user.getTelefono());

        if(user.getNombre() != null && user.validName()){loaded.setNombre(user.getNombre());}
        if(user.getApellido() != null && user.validApellido()){loaded.setApellido(user.getApellido());}
        if(user.getContraseña() != null && user.validPass()){loaded.setContraseña(user.getContraseña());}
        if(user.getEstado() != null){loaded.setEstado(user.getEstado());}
        userDAO.save(loaded);
    }
}
