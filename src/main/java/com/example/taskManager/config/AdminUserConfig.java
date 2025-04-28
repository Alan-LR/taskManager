package com.example.taskManager.config;

import com.example.taskManager.entities.role.Role;
import com.example.taskManager.entities.users.User;
import com.example.taskManager.repository.RoleRepository;
import com.example.taskManager.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Set;

@Configuration
public class AdminUserConfig implements CommandLineRunner {

    private RoleRepository roleRepository;

    private UserRepository userRepository;

    private BCryptPasswordEncoder passwordEncoder;

    //A recomendação atual é utilizar injeção de dependencia com construtor, mas o @Autowired não está errado e ainda é utilizado
    public AdminUserConfig(RoleRepository roleRepository,
                    UserRepository userRepository,
                    BCryptPasswordEncoder passwordEncoder) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        Role roleAdmin = roleRepository.findByName(Role.Values.ADMIN.name());
        var userAdmin = userRepository.findByName("admin");

        userAdmin.ifPresentOrElse(user -> {
            System.out.println("admin created");
                },
                () ->{
                    User user = new User();
                    user.setName("admin");
                    user.setPassword(passwordEncoder.encode("123"));
                    user.setRoles(Set.of(roleAdmin));
                    user.setEmail("admin@gmail.com");
                    userRepository.save(user);
                }
        );
    }
}
