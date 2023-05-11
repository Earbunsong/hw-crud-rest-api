package com.example.mbanking.api.notification.web;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import org.springframework.beans.factory.annotation.Value;

public record CreateNotificationDto(
        @JsonProperty("included_segments")
        String[] includedSegments,
        ContentDto contents
) { }