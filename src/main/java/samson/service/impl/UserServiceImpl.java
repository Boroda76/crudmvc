package samson.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import samson.dao.UserDAO;
import samson.exceptions.UserException;
import samson.model.User;
import samson.service.UserService;

import java.util.List;

@Service("userService")
public class UserServiceImpl implements UserService, UserDetailsService {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserDAO dao;

    @Override
    public String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }

    @Transactional
    @Override
    public void delete(Long id) throws UserException {
        dao.delete(id);
    }

    @Override
    public List<User> getAll() {
        return dao.getAll();
    }

    @Override
    public User getById(Long id) throws UserException {
        return dao.getById(id);
    }

    @Override
    public User getByLogin(String login) throws UserException {
        return dao.getByLogin(login);
    }

    @Transactional
    @Override
    public void updateUser(User user) throws UserException {
        dao.updateUser(user);
    }

    @Transactional
    @Override
    public void createUser(User user) throws UserException {
        String pass=encodePassword(user.getPassword());
        user.setPassword(pass);
        dao.createUser(user);
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = null;
        try {
            user = dao.getByLogin(s);
        } catch (UserException e) {
            e.printStackTrace();
            throw new UsernameNotFoundException(e.getMessage());
        }
        return user;
    }
}
