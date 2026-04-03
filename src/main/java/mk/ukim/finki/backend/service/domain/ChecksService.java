package mk.ukim.finki.backend.service.domain;

import mk.ukim.finki.backend.model.domain.Api;
import mk.ukim.finki.backend.model.domain.Checks;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ChecksService {
    Page<Checks> getAllChecksByApi(Api api, Pageable pageable);

    Checks getLatestCheckForApi(Api api);
}
