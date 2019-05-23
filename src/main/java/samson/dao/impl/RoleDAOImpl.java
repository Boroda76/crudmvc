package samson.dao.impl;

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

    @Override
    public void addRole(Role role) {
        em.persist(role);
    }

    @Override
    public List<Role> findAll() {
        return em.createQuery("SELECT r FROM Role r", Role.class).getResultList();
    }

    @Override
    public Role findByName(String name) {
        return (Role)em.createQuery("select r from Role r where r.authority=:authority").setParameter("authority", name).getSingleResult();
    }
}
