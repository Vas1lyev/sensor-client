package ru.vasilyev;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import ru.vasilyev.dto.MeasurementDTO;

public class Client {
    public static void main(String[] args) {
        String testClientSensorName = "testClientSensor";
        registerSensor(testClientSensorName);

        Random random = new Random();

        double maxTemp = 37.0;
        for (int i = 0; i < 1000; i++) {
            addMeasurement(random.nextDouble() * maxTemp,
                    random.nextBoolean(),
                    testClientSensorName);
        }
    }

    public static void sendPostRequest(String url, Map<String, Object> jsonData) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Object> request = new HttpEntity<>(jsonData, headers);

        try {
            ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);
            System.out.println("OK");
        } catch (HttpClientErrorException e) {
            System.out.println("ERROR");
            System.out.println(e.getMessage());
        }
    }

    public static void registerSensor(String name) {
        String url = "http://localhost:8080/sensors/registration";
        Map<String, Object> jsonData = new HashMap<>();
        jsonData.put("name", name);

        sendPostRequest(url, jsonData);
    }

    public static void addMeasurement(double value, boolean isRaining, String sensorName) {
        String url = "http://localhost:8080/measurements/add";
        Map<String, Object> jsonData = new HashMap<>();
        jsonData.put("value", value);
        jsonData.put("raining", isRaining);
        jsonData.put("sensor", Map.of("name", sensorName));

        sendPostRequest(url, jsonData);
    }

//    // POST: Add a new measurement
//    public void addMeasurement(MeasurementDTO measurementDTO) throws Exception {
//        String url = baseUrl + "/measurement/add";
//
//        // Преобразование объекта в JSON
//        String json = objectMapper.writeValueAsString(measurementDTO);
//
//        HttpRequest request = HttpRequest.newBuilder()
//                .uri(URI.create(url))
//                .header("Content-Type", "application/json")
//                .POST(HttpRequest.BodyPublishers.ofString(json))
//                .build();
//
//        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
//
//        if (response.statusCode() == 201) {
//            System.out.println("Measurement added successfully!");
//        } else {
//            System.out.println("Failed to add measurement. Status code: " + response.statusCode());
//            System.out.println("Response body: " + response.body());
//        }
//    }
//
//
//
//    // GET: Get measurement by ID
//    public MeasurementDTO getMeasurementById(int id) throws Exception {
//        String url = baseUrl + "/measurement/" + id;
//
//        HttpRequest request = HttpRequest.newBuilder()
//                .uri(URI.create(url))
//                .GET()
//                .build();
//
//        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
//
//        if (response.statusCode() == 200) {
//            // Преобразование JSON ответа в объект
//            return objectMapper.readValue(response.body(), MeasurementDTO.class);
//        } else {
//            System.out.println("Failed to get measurement. Status code: " + response.statusCode());
//            System.out.println("Response body: " + response.body());
//            return null;
//        }
//    }

}

