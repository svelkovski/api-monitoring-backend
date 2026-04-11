package mk.ukim.finki.backend.repository;

import mk.ukim.finki.backend.model.domain.Api;
import mk.ukim.finki.backend.model.domain.Checks;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChecksRepository extends JpaRepository<Checks, Long> {
    Checks findTopByApiIdOrderByCheckedAtDesc(Long apiId);

    Page<Checks> findAllByApiOrderByCheckedAtDesc(Api api, Pageable pageable);
}
