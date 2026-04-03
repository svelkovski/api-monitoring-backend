package mk.ukim.finki.backend.repository;

import mk.ukim.finki.backend.model.domain.Api;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApiRepository extends JpaRepository<Api, Long> {
    Page<Api> findByUserUsername(String username, Pageable pageable);
}
