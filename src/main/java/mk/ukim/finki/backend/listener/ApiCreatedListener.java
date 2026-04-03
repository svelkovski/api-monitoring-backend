package mk.ukim.finki.backend.listener;

import lombok.RequiredArgsConstructor;
import mk.ukim.finki.backend.event.ApiCreatedEvent;
import mk.ukim.finki.backend.service.domain.impl.ApiCheckWorker;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class ApiCreatedListener {

    private final ApiCheckWorker worker;

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleApiCreated(ApiCreatedEvent event) {
        worker.checkApiAsync(event.api());
    }
}
