package mk.ukim.finki.backend.service.domain.impl;

import lombok.RequiredArgsConstructor;
import mk.ukim.finki.backend.model.domain.Api;
import mk.ukim.finki.backend.model.domain.Checks;
import mk.ukim.finki.backend.repository.ChecksRepository;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class ApiCheckWorker {

    private final ChecksRepository checksRepository;
    private final RestTemplate restTemplate;

    @Async("apiCheckExecutor")
    public void checkApiAsync(Api api) {
        long start = System.currentTimeMillis();

        try {
            ResponseEntity<String> response =
                    restTemplate.exchange(api.getUrl(),
                            HttpMethod.valueOf(api.getMethod().name()),
                            null,
                            String.class);

            long responseTime = System.currentTimeMillis() - start;

            saveCheck(responseTime, response.getStatusCode().value(), api);
        } catch (Exception e) {
            long responseTime = System.currentTimeMillis() - start;
            saveCheck(responseTime, 0, api);
        }
    }

    private void saveCheck(long responseTime, int responseCode, Api api) {
        Checks check = new Checks(responseTime, responseCode, api);
        checksRepository.save(check);
    }
}
