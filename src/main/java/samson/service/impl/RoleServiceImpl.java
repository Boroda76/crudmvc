package samson.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import samson.dao.RoleDAO;
import samson.model.Role;
import samson.service.RoleService;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleDAO dao;

    @Transactional
    @Override
    public void addRole(Role role) {
        dao.addRole(role);
    }

    @Override
    public List<Role> findAll() {
        return dao.findAll();
    }

    @Override
    public Role findByName(String name) {
        return dao.findByName(name);
    }
}
