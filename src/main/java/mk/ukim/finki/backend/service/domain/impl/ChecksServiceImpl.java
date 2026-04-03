package mk.ukim.finki.backend.service.domain.impl;

import lombok.RequiredArgsConstructor;
import mk.ukim.finki.backend.model.domain.Api;
import mk.ukim.finki.backend.model.domain.Checks;
import mk.ukim.finki.backend.repository.ChecksRepository;
import mk.ukim.finki.backend.service.domain.ChecksService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChecksServiceImpl implements ChecksService {
    private final ChecksRepository checksRepository;

    @Override
    public Checks getLatestCheckForApi(Api api) {
        return checksRepository.findTopByApiIdOrderByCheckedAtDesc(api.getId());
    }

    @Override
    public Page<Checks> getAllChecksByApi(Api api, Pageable pageable) {
        return checksRepository.findAllByApiOrderByCheckedAtDesc(api, pageable);
    }
}
