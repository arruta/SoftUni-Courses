package org.softuni.mobilele.testutils;

import org.softuni.mobilele.model.entity.UserRoleEntity;
import org.softuni.mobilele.model.enums.UserRoleEnum;
import org.softuni.mobilele.repository.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DBInit implements CommandLineRunner {

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Override
    public void run(String... args) throws Exception {
        if (userRoleRepository.count() == 0) {
            UserRoleEntity user = new UserRoleEntity();
            user.setRole(UserRoleEnum.USER);

            UserRoleEntity admin = new UserRoleEntity();
            admin.setRole(UserRoleEnum.ADMIN);

            userRoleRepository.saveAll(List.of(user, admin));
        }
    }
}
