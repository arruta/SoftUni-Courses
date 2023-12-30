package org.softuni.mobilele.service;

import org.softuni.mobilele.model.dto.ReCaptchaResponseDTO;

import java.util.Optional;

public interface ReCaptchaService {

    Optional<ReCaptchaResponseDTO> verify(String token);
}
