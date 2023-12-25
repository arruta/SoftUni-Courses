package org.softuni.mobilele.service.impl;

import io.micrometer.core.instrument.Counter;
import org.softuni.mobilele.service.MonitoringService;
import org.springframework.stereotype.Service;

@Service
public class MonitoringServiceImpl implements MonitoringService {

    private Counter offerSearches;

    public MonitoringServiceImpl() {


    }

    @Override
    public void logOfferSearch() {

    }
}
