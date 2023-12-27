package org.softuni.mobilele.service.impl;

import jakarta.transaction.Transactional;
import org.softuni.mobilele.model.dto.CreateOfferDTO;
import org.softuni.mobilele.model.dto.OfferDetailDTO;
import org.softuni.mobilele.model.dto.OfferSummaryDTO;
import org.softuni.mobilele.model.entity.*;
import org.softuni.mobilele.model.enums.UserRoleEnum;
import org.softuni.mobilele.repository.ModelRepository;
import org.softuni.mobilele.repository.OfferRepository;
import org.softuni.mobilele.repository.UserRepository;
import org.softuni.mobilele.service.MonitoringService;
import org.softuni.mobilele.service.OfferService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
public class OfferServiceImpl implements OfferService {

    private final OfferRepository offerRepository;
    private final ModelRepository modelRepository;

    private final MonitoringService monitoringService;

    private final UserRepository userRepository;

    public OfferServiceImpl(OfferRepository offerRepository, ModelRepository modelRepository, MonitoringService monitoringService, UserRepository userRepository) {
        this.offerRepository = offerRepository;
        this.modelRepository = modelRepository;
        this.monitoringService = monitoringService;
        this.userRepository = userRepository;
    }

    @Override
    public UUID createOffer(CreateOfferDTO createOfferDTO, UserDetails seller) {

        OfferEntity newOffer = map(createOfferDTO);

        ModelEntity modelEntity = modelRepository.findById(createOfferDTO.getModelId()).orElseThrow(() ->
                new IllegalArgumentException("Model with id " + createOfferDTO.getModelId() + " not found!"));

        UserEntity sellerEntity = userRepository.findByEmail(seller.getUsername()).orElseThrow(() ->
                new IllegalArgumentException("User with email " + seller.getUsername() + " not found!"));

        newOffer.setModel(modelEntity);
        newOffer.setSeller(sellerEntity);

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
    public Optional<OfferDetailDTO> getOfferDetail(UUID offerUUID, UserDetails viewer) {
        return offerRepository.findByUuid(offerUUID)
                .map(o -> this.mapAsDetails(o, viewer));
    }

    @Override
    @Transactional
    public void deleteOffer(UUID offerUUID) {
        offerRepository.deleteByUuid(offerUUID);
    }

    private OfferDetailDTO mapAsDetails(OfferEntity offerEntity, UserDetails viewer) {
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
                offerEntity.getImageURL(),
                offerEntity.getSeller().getFirstName() + " " + offerEntity.getSeller().getLastName(),
                isOwner(offerEntity, viewer != null ? viewer.getUsername() : null));
    }

    @Override
    public boolean isOwner(UUID uuid, String userName) {
        return isOwner(offerRepository.findByUuid(uuid).orElse(null), userName);
    }

    private boolean isOwner(OfferEntity offerEntity, String userName) {
        if (offerEntity == null || userName == null) {
            // anonymous users own no offers
            // missing offers are meaningless
            return false;
        }

        UserEntity viewerEntity = userRepository.findByEmail(userName)
                .orElseThrow(() -> new IllegalArgumentException("Unknown user..."));

        if (isAdmin(viewerEntity)) {
            // all admins own all offers
            return true;
        }

        return Objects.equals(offerEntity.getSeller().getId(), viewerEntity.getId());
    }

    private boolean isAdmin(UserEntity userEntity) {
        return userEntity.getRoles()
                .stream()
                .map(UserRoleEntity::getRole)
                .anyMatch(r -> UserRoleEnum.ADMIN == r);
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
