package samson.dao.impl;

import org.springframework.stereotype.Repository;
import samson.dao.UserDAO;
import samson.exceptions.UserException;
import samson.model.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class UserDAOImpl implements UserDAO {

    @PersistenceContext
    EntityManager em;

    @Override
    public void delete(Long id) throws UserException {
        User delete=em.find(User.class, id);
        if (delete != null) {
            em.remove(delete);
        }else{
            throw new UserException("No user with provided ID found");
        }
    }

    @Override
    public List<User> getAll() {
        return em.createQuery("Select u from User u", User.class).getResultList();
    }

    @Override
    public User getByLogin(String login) throws UserException {
        User user=(User)em.createQuery("select u from User u where u.login=:login").setParameter("login", login).getSingleResult();
        if (user == null) throw new UserException("No user with provided LOGIN found");
        return user;
    }

    @Override
    public User getById(Long id) throws UserException {
        User user=em.find(User.class, id);
        if (user == null) throw new UserException("No user with provided ID found");
        return user;
    }

    @Override
    public void updateUser(User user) throws UserException {
        try {
            em.merge(user);
        } catch (Exception e) {
            throw new UserException(e.getCause().getCause().getMessage());
        }
    }

    @Override
    public void createUser(User user) throws UserException {
        try {
            em.merge(user); //TODO: why persist doesn't work?
        } catch (Exception e) {
            throw new UserException(e.getCause().getCause().getMessage());
        }

    }
}
