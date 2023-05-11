package com.example.mbanking.api.notification;
import com.example.mbanking.api.notification.web.CreateNotificationDto;
import com.example.mbanking.api.notification.web.NotificationDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class NotificationServiceImp implements NotificationService {

    @Value("${onesignal.rest-api-key}")
    private String restApiKey;

    @Value("${onesignal.app-id}")
    private String appId;

    private RestTemplate restTemplate;

    @Autowired
    public void setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public boolean pushNotification(CreateNotificationDto createNotificationDto) {
        System.out.println("createNotificationDto "+createNotificationDto);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("accept", "application/json");
        httpHeaders.set("Authorization", "Basic " + restApiKey);
        httpHeaders.set("content-type", "application/json");
        NotificationDto body = NotificationDto.builder()
                .includedSegments(createNotificationDto.includedSegments())
                .contents(createNotificationDto.contents())
                .appId(appId)
                .build();
        System.out.println("body "+body);
        HttpEntity<NotificationDto> requestBody = new HttpEntity<>(body, httpHeaders);
        ResponseEntity<?> response = restTemplate.postForEntity(
                "https://onesignal.com/api/v1/notifications",
                requestBody,
                Map.class
        );
        System.out.println(response);
        return false;
    }
}
//        OkHttpClient client = new OkHttpClient();
//
//        MediaType mediaType = MediaType.parse("application/json");
//        RequestBody body = RequestBody.create(mediaType, "{\"included_segments\":[\"Subscribed Users\"],\"contents\":{\"en\":\"English or Any Language Message\",\"es\":\"Spanish Message\"},\"name\":\"INTERNAL_CAMPAIGN_NAME\"}");
//        Request request = new Request.Builder()
//                .url("https://onesignal.com/api/v1/notifications")
//                .post(body)
//                .addHeader("accept", "application/json")
//                .addHeader("Authorization", "Basic YOUR_REST_API_KEY")
//                .addHeader("content-type", "application/json")
//                .build();
//
//        Response response = client.newCall(request).execute();