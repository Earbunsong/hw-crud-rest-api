package com.example.mbanking.api.notification.web;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import org.springframework.beans.factory.annotation.Value;

import java.util.Map;
@Builder
public record NotificationDto(
        @JsonProperty("included_segments")
        String[] includedSegments,
        ContentDto contents,
        @Value("${oneSignal.app-id}")
        @JsonProperty("app_id")
        String appId
) {}