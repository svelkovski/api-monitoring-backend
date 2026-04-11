package mk.ukim.finki.backend.service.application;

import mk.ukim.finki.backend.model.dto.DisplayCheckDto;
import org.springframework.data.domain.Page;

public interface ChecksApplicationService {
    Page<DisplayCheckDto> findAllChecksForApi(Long apiId, int page, int size);
}
