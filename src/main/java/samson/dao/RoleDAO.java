package samson.dao;

import samson.model.Role;

import java.util.List;

public interface RoleDAO {
    void addRole(Role role);
    List<Role> findAll();
    Role findByName(String name);
}
