package org.softuni.mobilele.init;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DBInit implements CommandLineRunner {


    private final String defaultAdminPass;

    @Autowired
    public DBInit(@Value("${mobilele.default.admin.pass}") String defaultAdminPass) {
        this.defaultAdminPass = defaultAdminPass;
    }

    @Override
    public void run(String... args) throws Exception {
        //TODO
    }
}
