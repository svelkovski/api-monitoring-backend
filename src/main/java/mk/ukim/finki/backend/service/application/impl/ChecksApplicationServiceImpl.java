package mk.ukim.finki.backend.service.application.impl;

import lombok.RequiredArgsConstructor;
import mk.ukim.finki.backend.model.domain.Api;
import mk.ukim.finki.backend.model.domain.Checks;
import mk.ukim.finki.backend.model.dto.DisplayCheckDto;
import mk.ukim.finki.backend.model.exception.AccessDeniedException;
import mk.ukim.finki.backend.service.application.ChecksApplicationService;
import mk.ukim.finki.backend.service.domain.ApiService;
import mk.ukim.finki.backend.service.domain.ChecksService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChecksApplicationServiceImpl implements ChecksApplicationService {

    private final ApiService apiService;
    private final ChecksService checksService;

    @Override
    public Page<DisplayCheckDto> findAllChecksForApi(Long apiId, int page, int size) {
        Api api = apiService.findById(apiId);

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        if (!api.getUser().getUsername().equals(username)) {
            throw new AccessDeniedException("Cannot view another user's API checks");
        }

        Pageable pageable = PageRequest.of(page, size);
        Page<Checks> checks = checksService.getAllChecksByApi(api, pageable);

        return checks.map(DisplayCheckDto::from);
    }
}
