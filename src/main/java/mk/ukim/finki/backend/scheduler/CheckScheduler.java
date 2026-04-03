package mk.ukim.finki.backend.scheduler;

import lombok.RequiredArgsConstructor;
import mk.ukim.finki.backend.model.domain.Api;
import mk.ukim.finki.backend.repository.ApiRepository;
import mk.ukim.finki.backend.service.domain.impl.ApiCheckWorker;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CheckScheduler {

    private final ApiRepository apiRepository;
    private final ApiCheckWorker worker;

    @Scheduled(fixedRate = 60000)
    public void scheduleChecks() {
        List<Api> apis = apiRepository.findAll();
        apis.forEach(worker::checkApiAsync);
    }
}
