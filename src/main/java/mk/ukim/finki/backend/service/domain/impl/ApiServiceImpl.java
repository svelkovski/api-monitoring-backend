package mk.ukim.finki.backend.service.domain.impl;

import lombok.RequiredArgsConstructor;
import mk.ukim.finki.backend.event.ApiCreatedEvent;
import mk.ukim.finki.backend.model.domain.Api;
import mk.ukim.finki.backend.model.exception.ApiNotFoundException;
import mk.ukim.finki.backend.repository.ApiRepository;
import mk.ukim.finki.backend.service.domain.ApiService;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ApiServiceImpl implements ApiService {

    private final ApiRepository apiRepository;
    private final ApplicationEventPublisher applicationEventPublisher;

    @Transactional
    @Override
    public Api create(Api api) {
        Api saved = apiRepository.save(api);
        applicationEventPublisher.publishEvent(new ApiCreatedEvent(saved));
        return saved;
    }

    @Override
    public Page<Api> findAll(String username, Pageable pageable) {
        return apiRepository.findByUserUsername(username, pageable);
    }

    @Override
    public Api update(Api api) {
        return apiRepository.save(api);
    }

    @Override
    public Api deleteById(Long apiId) {
        Api api = apiRepository.findById(apiId)
                .orElseThrow(() -> new ApiNotFoundException(apiId));

        apiRepository.delete(api);

        return api;
    }

    @Override
    public Api findById(Long apiId) {
        return apiRepository.findById(apiId)
                .orElseThrow(() -> new ApiNotFoundException(apiId));
    }
}
