package mk.ukim.finki.backend.service.application.impl;

import lombok.RequiredArgsConstructor;
import mk.ukim.finki.backend.model.domain.Api;
import mk.ukim.finki.backend.model.domain.Checks;
import mk.ukim.finki.backend.model.domain.User;
import mk.ukim.finki.backend.model.dto.CheckedApiDto;
import mk.ukim.finki.backend.model.dto.CreateApiDto;
import mk.ukim.finki.backend.model.dto.DisplayApiDto;
import mk.ukim.finki.backend.model.exception.AccessDeniedException;
import mk.ukim.finki.backend.service.application.ApiApplicationService;
import mk.ukim.finki.backend.service.domain.ApiService;
import mk.ukim.finki.backend.service.domain.ChecksService;
import mk.ukim.finki.backend.service.domain.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ApiApplicationServiceImpl implements ApiApplicationService {

    private final UserService userService;
    private final ApiService apiService;
    private final ChecksService checksService;

    @Override
    public DisplayApiDto createApi(CreateApiDto dto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        Optional<User> user = userService.findByUsername(username);

        Api api = apiService.create(new Api(dto.name(), dto.url(), dto.method(), user.get()));
        return DisplayApiDto.from(api);
    }

    @Override
    public Page<CheckedApiDto> getAllApisWithStatus(int page, int size) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        Pageable pageable = PageRequest.of(page, size);
        Page<Api> apis = apiService.findAll(username, pageable);

        return apis.map(api -> {
            Checks latestCheck = checksService.getLatestCheckForApi(api);
            return CheckedApiDto.from(api, latestCheck);
        });
    }

    @Override
    public DisplayApiDto updateApi(Long id, CreateApiDto dto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        Api apiToUpdate = apiService.findById(id);

        if (!apiToUpdate.getUser().getUsername().equals(username)) {
            throw new AccessDeniedException("Cannot update another user's API");
        }

        apiToUpdate.setName(dto.name());
        apiToUpdate.setUrl(dto.url());
        apiToUpdate.setMethod(dto.method());

        return DisplayApiDto.from(apiService.update(apiToUpdate));
    }

    @Override
    public DisplayApiDto deleteById(Long id) {
        Api api = apiService.findById(id);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        if (!api.getUser().getUsername().equals(username)) {
            throw new AccessDeniedException("Cannot delete another user's API");
        }

        return DisplayApiDto.from(apiService.deleteById(id));
    }
}
