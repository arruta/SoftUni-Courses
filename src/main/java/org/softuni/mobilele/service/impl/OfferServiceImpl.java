package org.softuni.mobilele.service.impl;

import jakarta.transaction.Transactional;
import org.softuni.mobilele.model.dto.CreateOfferDTO;
import org.softuni.mobilele.model.dto.OfferDetailDTO;
import org.softuni.mobilele.model.dto.OfferSummaryDTO;
import org.softuni.mobilele.model.entity.ModelEntity;
import org.softuni.mobilele.model.entity.OfferEntity;
import org.softuni.mobilele.repository.ModelRepository;
import org.softuni.mobilele.repository.OfferRepository;
import org.softuni.mobilele.service.MonitoringService;
import org.softuni.mobilele.service.OfferService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class OfferServiceImpl implements OfferService {

    private final OfferRepository offerRepository;
    private final ModelRepository modelRepository;

    private final MonitoringService monitoringService;

    public OfferServiceImpl(OfferRepository offerRepository, ModelRepository modelRepository, MonitoringService monitoringService) {
        this.offerRepository = offerRepository;
        this.modelRepository = modelRepository;
        this.monitoringService = monitoringService;
    }

    @Override
    public UUID createOffer(CreateOfferDTO createOfferDTO) {

        OfferEntity newOffer = map(createOfferDTO);

        ModelEntity modelEntity = modelRepository.findById(createOfferDTO.getModelId()).orElseThrow(() ->
                new IllegalArgumentException("Model with id " + createOfferDTO.getModelId() + " not found!"));

        newOffer.setModel(modelEntity);

        newOffer = offerRepository.save(newOffer);

        return newOffer.getUuid();
    }


    @Override
    public Page<OfferSummaryDTO> getAllOffers(Pageable pageable) {

        monitoringService.logOfferSearch();

        return offerRepository.findAll(pageable)
                .map(OfferServiceImpl::mapAsSummary);

    }

    @Override
    public Optional<OfferDetailDTO> getOfferDetail(UUID offerUUID) {
        return offerRepository.findByUuid(offerUUID)
                .map(OfferServiceImpl::mapAsDetails);
    }

    @Override
    @Transactional
    public void deleteOffer(UUID offerUUID) {
        offerRepository.deleteByUuid(offerUUID);
    }

    private static OfferDetailDTO mapAsDetails(OfferEntity offerEntity) {
        //TODO: reuse
        return new OfferDetailDTO(
                offerEntity.getUuid().toString(),
                offerEntity.getModel().getBrand().getName(),
                offerEntity.getModel().getName(),
                offerEntity.getYear(),
                offerEntity.getMileage(),
                offerEntity.getPrice(),
                offerEntity.getEngine(),
                offerEntity.getTransmission(),
                offerEntity.getImageURL());
    }
    private static OfferSummaryDTO mapAsSummary(OfferEntity offerEntity) {
        return new OfferSummaryDTO(
                offerEntity.getUuid().toString(),
                offerEntity.getModel().getBrand().getName(),
                offerEntity.getModel().getName(),
                offerEntity.getYear(),
                offerEntity.getMileage(),
                offerEntity.getPrice(),
                offerEntity.getEngine(),
                offerEntity.getTransmission(),
                offerEntity.getImageURL());
    }

    private OfferEntity map(CreateOfferDTO createOfferDTO) {
        OfferEntity offerEntity = new OfferEntity();

        offerEntity.setUuid(UUID.randomUUID());
        offerEntity.setDescription(createOfferDTO.getDescription());
        offerEntity.setEngine(createOfferDTO.getEngine());
        offerEntity.setImageUrl(createOfferDTO.getImageUrl());
        offerEntity.setMileage(createOfferDTO.getMileage());
        offerEntity.setPrice(createOfferDTO.getPrice());
        offerEntity.setTransmission(createOfferDTO.getTransmission());
        offerEntity.setYear(createOfferDTO.getYear());

        return offerEntity;
    }


}
