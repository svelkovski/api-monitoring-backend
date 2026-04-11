package mk.ukim.finki.backend.service.domain.impl;

import mk.ukim.finki.backend.model.domain.Api;
import mk.ukim.finki.backend.model.domain.Checks;
import mk.ukim.finki.backend.model.domain.User;
import mk.ukim.finki.backend.model.enums.HttpMethod;
import mk.ukim.finki.backend.repository.ChecksRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ChecksServiceImplTest {

    @Mock
    private ChecksRepository checksRepository;

    @InjectMocks
    private ChecksServiceImpl checksService;

    private User user;
    private Api api;
    private Checks check1;
    private Checks check2;

    @BeforeEach
    void setUp() {
        user = User.builder()
                .username("testUser")
                .email("test@test.com")
                .hashed_password("hashed")
                .build();

        api = new Api("test", "www.test.com", HttpMethod.GET, user);

        check1 = new Checks();
        check1.setApi(api);
        check1.setResponseCode(200);
        check1.setResponseTime(100L);
        check1.setCheckedAt(LocalDateTime.now().minusMinutes(5));

        check2 = new Checks();
        check2.setApi(api);
        check2.setResponseCode(500);
        check2.setResponseTime(300L);
        check2.setCheckedAt(LocalDateTime.now());
    }

    @Test
    void shouldGetLatestCheckForApi() {
        when(checksRepository.findTopByApiIdOrderByCheckedAtDesc(api.getId()))
                .thenReturn(check2);

        Checks latest = checksService.getLatestCheckForApi(api);

        assertNotNull(latest);
        assertEquals(check2.getId(), latest.getId());
        assertEquals(500, latest.getResponseCode());
        verify(checksRepository).findTopByApiIdOrderByCheckedAtDesc(api.getId());
    }

    @Test
    void shouldGetAllChecksByApi() {
        Pageable pageable = Pageable.unpaged();
        Page<Checks> page = new PageImpl<>(List.of(check1, check2));

        when(checksRepository.findAllByApiOrderByCheckedAtDesc(api, pageable))
                .thenReturn(page);

        Page<Checks> result = checksService.getAllChecksByApi(api, pageable);

        assertNotNull(result);
        assertEquals(2, result.getContent().size());
        assertEquals(check1.getId(), result.getContent().get(0).getId());
        assertEquals(check2.getId(), result.getContent().get(1).getId());
        verify(checksRepository).findAllByApiOrderByCheckedAtDesc(api, pageable);
    }
}
