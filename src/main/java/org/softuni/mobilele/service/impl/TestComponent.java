package org.softuni.mobilele.service.impl;

import org.softuni.mobilele.model.entity.BrandEntity;
import org.softuni.mobilele.repository.BrandRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TestComponent implements CommandLineRunner {

    private final BrandRepository brandRepository;

    public TestComponent(BrandRepository brandRepository) {
        this.brandRepository = brandRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("---------------");

        List<BrandEntity> brands = brandRepository.getAllBrands();

        System.out.println("B:---------------");

        brands.forEach(b -> {
            System.out.println("M:----------------");
            b.getModels().forEach(System.out::println);
            System.out.println("M:----------------");
        });

        System.out.println("B:---------------");
    }

}
