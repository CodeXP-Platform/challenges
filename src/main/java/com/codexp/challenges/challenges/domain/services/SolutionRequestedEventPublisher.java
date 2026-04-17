package com.codexp.challenges.challenges.domain.services;

import com.codexp.challenges.challenges.domain.model.events.SolutionRequestedEvent;

public interface SolutionRequestedEventPublisher {
    void publish(SolutionRequestedEvent event);
}
