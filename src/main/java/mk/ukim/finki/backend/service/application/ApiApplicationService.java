package mk.ukim.finki.backend.service.application;

import mk.ukim.finki.backend.model.dto.CheckedApiDto;
import mk.ukim.finki.backend.model.dto.CreateApiDto;
import mk.ukim.finki.backend.model.dto.DisplayApiDto;
import org.springframework.data.domain.Page;

public interface ApiApplicationService {
    DisplayApiDto createApi(CreateApiDto createApiDto);

    Page<CheckedApiDto> getAllApisWithStatus(int page, int size);

    DisplayApiDto updateApi(Long id, CreateApiDto dto);

    DisplayApiDto deleteById(Long id);
}
