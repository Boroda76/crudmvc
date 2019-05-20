package samson.dao.impl;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import samson.dao.RoleDAO;
import samson.model.Role;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class RoleDAOImpl implements RoleDAO {
    @PersistenceContext
    private EntityManager em;
    @Autowired
    SessionFactory factory;

    private Session session;

    @Override
    public void addRole(Role role) {
        session = factory.openSession();
        session.save(role);
        Transaction tx = session.beginTransaction();
        session.flush();
        tx.commit();
        session.close();
    }

    @Override
    public List<Role> findAll() {
        return em.createQuery("SELECT r FROM Role r", Role.class).getResultList();
    }

    @Override
    public Role findByName(String name) {
        session = factory.openSession();
        Role role;
        Query query = session.createQuery("from Role where authority=:name");
        query.setParameter("name", name);
        role = (Role) query.uniqueResult();
        session.close();
        return role;
    }
}
