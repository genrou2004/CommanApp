package byaj.services;

import byaj.models.Camp;
import byaj.models.User;
import byaj.repositories.RoleRepository;
import byaj.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
    public int countByEmail(String email) {
        return userRepository.countByEmail(email);
    }
    Principal principal;

    public User findByUsername(String username){
        return userRepository.findByUsername(username);
    }

    public void saveUser(User user) {
        user.setRoles(Arrays.asList(roleRepository.findByRoleName("USER")));
        user.setEnabled(true);
        userRepository.save(user);
    }

    public void saveDirector(User user) {
        user.setRoles(Arrays.asList(roleRepository.findByRoleName("USER")));
        user.setRoles(Arrays.asList(roleRepository.findByRoleName("DIRECTOR")));
        user.setEnabled(true);
        userRepository.save(user);
    }

    public void saveAdmin(User user) {
        user.setRoles(Arrays.asList(roleRepository.findByRoleName("USER")));
        user.setRoles(Arrays.asList(roleRepository.findByRoleName("DIRECTOR")));
        user.setRoles(Arrays.asList(roleRepository.findByRoleName("ADMIN")));
        user.setEnabled(true);
        userRepository.save(user);
    }

    public void saveSuperAdmin(User user) {
        user.setRoles(Arrays.asList(roleRepository.findByRoleName("USER")));
        user.setRoles(Arrays.asList(roleRepository.findByRoleName("DIRECTOR")));
        user.setRoles(Arrays.asList(roleRepository.findByRoleName("ADMIN")));
        user.setRoles(Arrays.asList(roleRepository.findByRoleName("SUPERADMIN")));
        user.setEnabled(true);
        userRepository.save(user);
    }



    
}
