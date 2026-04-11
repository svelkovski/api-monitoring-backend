package mk.ukim.finki.backend.service.domain.impl;

import mk.ukim.finki.backend.event.ApiCreatedEvent;
import mk.ukim.finki.backend.model.domain.Api;
import mk.ukim.finki.backend.model.domain.User;
import mk.ukim.finki.backend.model.enums.HttpMethod;
import mk.ukim.finki.backend.model.exception.ApiNotFoundException;
import mk.ukim.finki.backend.repository.ApiRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ApiServiceImplTest {

    @Mock
    private ApiRepository apiRepository;

    @Mock
    private ApplicationEventPublisher applicationEventPublisher;

    @InjectMocks
    private ApiServiceImpl apiService;

    private User user;

    @BeforeEach
    void setUp() {
        user = User.builder()
                .username("test")
                .email("test@test.com")
                .hashed_password("hashed")
                .build();
    }

    @Test
    void shouldCreateApi() {
        Api api = new Api("test", "www.test.com", HttpMethod.GET, user);

        when(apiRepository.save(any(Api.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Api result = apiService.create(api);

        assertNotNull(result);
        assertEquals(api.getName(), result.getName());
        assertEquals(api.getUrl(), result.getUrl());
        assertEquals(api.getMethod(), result.getMethod());
        assertEquals(api.getUser(), result.getUser());

        verify(apiRepository).save(any(Api.class));
        verify(applicationEventPublisher).publishEvent(any(ApiCreatedEvent.class));
    }

    @Test
    void shouldFindAllApisByUsername() {
        Page<Api> page = new PageImpl<>(List.of(
                new Api("ex1", "ex1.com", HttpMethod.GET, user),
                new Api("ex2", "ex2.com", HttpMethod.POST, user)
        ));
        Pageable pageable = Pageable.unpaged();

        when(apiRepository.findByUserUsername("test", pageable))
                .thenReturn(page);

        Page<Api> result = apiService.findAll("test", pageable);

        assertEquals(2, result.getContent().size());
        verify(apiRepository).findByUserUsername("test", pageable);
    }

    @Test
    void shouldUpdateApi() {
        Api updatedApi = new Api("New Name", "new.com", HttpMethod.POST, user);

        when(apiRepository.save(any(Api.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Api result = apiService.update(updatedApi);

        assertEquals("New Name", result.getName());
        assertEquals("new.com", result.getUrl());
        assertEquals(HttpMethod.POST, result.getMethod());

        verify(apiRepository).save(any(Api.class));
    }

    @Test
    void shouldDeleteApiById() {
        Api api = new Api("deleteApi", "delete.com", HttpMethod.GET, user);

        when(apiRepository.findById(api.getId())).thenReturn(Optional.of(api));

        Api result = apiService.deleteById(api.getId());

        assertEquals("deleteApi", result.getName());
        verify(apiRepository).delete(api);
    }

    @Test
    void shouldThrowWhenDeletingNonExistingApi() {
        when(apiRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(ApiNotFoundException.class, () -> apiService.deleteById(1L));
    }

    @Test
    void shouldFindApiById() {
        Api api = new Api("test", "test.com", HttpMethod.GET, user);
        when(apiRepository.findById(1L)).thenReturn(Optional.of(api));
        Api result = apiService.findById(1L);
        assertEquals("test", result.getName());
        verify(apiRepository).findById(1L);
    }

    @Test
    void shouldThrowWhenApiNotFoundById() {
        when(apiRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(ApiNotFoundException.class, () -> apiService.findById(1L));
    }
}
