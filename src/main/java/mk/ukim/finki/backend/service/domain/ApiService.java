package mk.ukim.finki.backend.service.domain;

import mk.ukim.finki.backend.model.domain.Api;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ApiService {
    Api create(Api api);

    Page<Api> findAll(String username, Pageable pageable);

    Api update(Api api);

    Api deleteById(Long apiId);

    Api findById(Long apiId);
}
