package byaj.configs;


import byaj.models.Role;
import byaj.models.User;
import byaj.repositories.RoleRepository;
import byaj.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class DataLoader implements CommandLineRunner{


    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;


    @Override
    public void run(String... strings) throws Exception {
        System.out.println("Loading data . . .");
        if(roleRepository.findByRoleName("USER")==null) {

            roleRepository.save(new Role("USER"));
            roleRepository.save(new Role("DIRECTOR"));
            roleRepository.save(new Role("ADMIN"));
            roleRepository.save(new Role("SUPERADMIN"));

            Role superadminRole = roleRepository.findByRoleName("SUPERADMIN");
            Role adminRole = roleRepository.findByRoleName("ADMIN");
            Role directorRole = roleRepository.findByRoleName("DIRECTOR");
            Role userRole = roleRepository.findByRoleName("USER");

            User user = new User("user@secure.com", "user", "User", "Userrson", true, "user");
            user.setRoles(Arrays.asList(userRole));
            userRepository.save(user);

            user = new User("director@secure.com", "director", "Director", "Directorson", true, "director");
            user.setRoles(Arrays.asList(directorRole));
            userRepository.save(user);

            user = new User("superadmin@secure.com", "TowerOfGod", "Viole", "Grace", true, "FloorAdministrator");
            user.setRoles(Arrays.asList(userRole));
            user.setRoles(Arrays.asList(directorRole));
            user.setRoles(Arrays.asList(adminRole));
            user.setRoles(Arrays.asList(superadminRole));
            userRepository.save(user);

            user = new User("admin@secure.com", "TowerOfGod", "Urek", "Mazino", true, "Irregular");
            user.setRoles(Arrays.asList(userRole, adminRole));
            user.setRoles(Arrays.asList(directorRole));
            user.setRoles(Arrays.asList(adminRole));
            userRepository.save(user);
        }
    }
}
