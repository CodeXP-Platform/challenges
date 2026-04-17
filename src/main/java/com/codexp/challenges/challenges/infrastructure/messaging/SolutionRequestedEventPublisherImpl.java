package com.codexp.challenges.challenges.infrastructure.messaging;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.codexp.challenges.challenges.domain.model.events.SolutionRequestedEvent;
import com.codexp.challenges.challenges.domain.services.SolutionRequestedEventPublisher;

@Component
public class SolutionRequestedEventPublisherImpl
    implements SolutionRequestedEventPublisher {

    private final RabbitTemplate rabbitTemplate;
    private final String challengesExchange;
    private final String solutionRequestedRoutingKey;

    public SolutionRequestedEventPublisherImpl(
        RabbitTemplate rabbitTemplate,
        @Value("${app.messaging.challenges.exchange}") String challengesExchange,
        @Value("${app.messaging.solutions.routing-keys.solution-requested}") String solutionRequestedRoutingKey
    ) {
        this.rabbitTemplate = rabbitTemplate;
        this.challengesExchange = challengesExchange;
        this.solutionRequestedRoutingKey = solutionRequestedRoutingKey;
    }

    @Override
    public void publish(SolutionRequestedEvent event) {
        rabbitTemplate.convertAndSend(
            challengesExchange,
            solutionRequestedRoutingKey,
            event
        );
    }
}
