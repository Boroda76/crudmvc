package samson.service;

import samson.model.Role;

import java.util.List;

public interface RoleService {
    void addRole(Role role);
    List<Role> findAll();
    Role findByName(String name);
}
