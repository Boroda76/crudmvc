package samson.dao.impl;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import samson.dao.UserDAO;
import samson.exceptions.UserException;
import samson.model.User;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class UserDAOImpl implements UserDAO {
    @PersistenceContext
    EntityManager em;
    @Autowired
    private SessionFactory factory;
    private Session session;

    @Override
    public void delete(Long id) throws UserException {

        javax.persistence.Query query = em.createQuery("delete User where id=:id");
        query.setParameter("id", id);
        int result = query.executeUpdate();
        if (result != 1) throw new UserException("No user with provided ID found");
    }

    @Override
    public List<User> getAll() {
        session = factory.openSession();
        List<User> users;
        Criteria criteria = session.createCriteria(User.class);
        users = criteria.list();
        session.close();
        return users;
    }

    @Override
    public User getByLogin(String login) throws UserException {
        session = factory.openSession();
        User user;
        Query query = session.createQuery("from User where login=:login");
        query.setParameter("login", login);
        user = (User) query.uniqueResult();
        session.close();
        if (user == null) throw new UserException("No user with provided LOGIN found");
        return user;
    }

    @Override
    public User getById(Long id) throws UserException {
        session = factory.openSession();
        User user;
        user = (User) session.get(User.class, id);
        session.close();
        if (user == null) throw new UserException("No user with provided ID found");
        return user;
    }

    @Override
    public void updateUser(User user) throws UserException {
        try {
            em.merge(user);
            /*em.refresh(user, LockModeType.OPTIMISTIC_FORCE_INCREMENT);*/
        } catch (Exception e) {
            throw new UserException(e.getCause().getCause().getMessage());
        }
    }

    @Override
    public void createUser(User user) throws UserException {
        try {
            em.merge(user);
        } catch (Exception e) {
            throw new UserException(e.getCause().getCause().getMessage());
        }

    }
}
