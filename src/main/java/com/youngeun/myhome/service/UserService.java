package com.youngeun.myhome.service;

import com.youngeun.myhome.model.Role;
import com.youngeun.myhome.model.User;
import com.youngeun.myhome.model.UserList;
import com.youngeun.myhome.repository.UserListRepository;
import com.youngeun.myhome.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserListRepository getUserRepository;

    public User save(User user,int auth) {
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        user.setEnabled(true);
        Role role = new Role();
        //user
        if (auth == 1) {
            role.setId(1l);
        }

        //hospital
        if (auth == 2)
            role.setId(2l);

        //institution
        else if (auth == 3) {
            role.setId(3l);
        }
        user.getRoles().add(role);


        //if user, then add to user_list
        if (auth == 1) {
            UserList getUser = new UserList();
            getUser.setUsername(user.getUsername());
            getUserRepository.save(getUser);
        }

        return userRepository.save(user);
    }

}
