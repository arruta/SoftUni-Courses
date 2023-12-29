package org.softuni.mobilele.testutils;

import org.softuni.mobilele.model.entity.*;
import org.softuni.mobilele.model.enums.EngineEnum;
import org.softuni.mobilele.model.enums.TransmissionEnum;
import org.softuni.mobilele.repository.BrandRepository;
import org.softuni.mobilele.repository.ExchangeRateRepository;
import org.softuni.mobilele.repository.ModelRepository;
import org.softuni.mobilele.repository.OfferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Component
public class TestDataUtil {

    @Autowired
    private ExchangeRateRepository exchangeRateRepository;

    @Autowired
    private OfferRepository offerRepository;

    @Autowired
    private BrandRepository brandRepository;

    @Autowired
    private ModelRepository modelRepository;


    public void createExchangeRate(String currency, BigDecimal rate) {
        ExchangeRateEntity exchangeRateEntity = new ExchangeRateEntity();
        exchangeRateEntity.setCurrency(currency);
        exchangeRateEntity.setRate(rate);

        exchangeRateRepository.save(exchangeRateEntity);
    }


    public OfferEntity createTestOffer(UserEntity owner) {
        // create test model
        ModelEntity modelEntity = new ModelEntity();
        modelEntity.setName("Test Model");


        ModelEntity modelEntity1 = new ModelEntity();
        modelEntity1.setName("Test Model1");

        modelRepository.save(modelEntity);
        modelRepository.save(modelEntity1);

        // create test brand
        BrandEntity brandEntity = new BrandEntity();
        brandEntity.setName("Test Brand");
        brandEntity.setModels(modelRepository.findAll());
        brandRepository.save(brandEntity);

        // create test offer
        OfferEntity offer = new OfferEntity();
        offer.setModel(brandEntity.getModels().get(0));
        offer.setImageUrl("https://www.google.com");
        offer.setPrice(BigDecimal.valueOf(1000));
        offer.setYear(2020);
        offer.setUuid(UUID.randomUUID());
        offer.setDescription("Test Description");
        offer.setEngine(EngineEnum.GASOLINE);
        offer.setMileage(10000L);
        offer.setTransmission(TransmissionEnum.MANUAL);
        offer.setSeller(owner);

        return offerRepository.save(offer);
    }

    public void cleanUp() {
        exchangeRateRepository.deleteAll();
        offerRepository.deleteAll();
        modelRepository.deleteAll();
        brandRepository.deleteAll();

    }
}
