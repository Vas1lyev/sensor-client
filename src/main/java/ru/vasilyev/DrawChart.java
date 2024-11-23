//package ru.vasilyev;
//
//import org.knowm.xchart.QuickChart;
//import org.springframework.web.client.RestTemplate;
//import ru.vasilyev.dto.MeasurementDTO;
//import ru.vasilyev.dto.MeasurementsResponse;
//
//import org.knowm.xchart.XYChart;
//import org.knowm.xchart.XYChartBuilder;
//import org.knowm.xchart.SwingWrapper;
//
//import java.util.Collections;
//import java.util.List;
//import java.util.stream.Collectors;
//import java.util.stream.DoubleStream;
//import java.util.stream.IntStream;
//
//public class DrawChart {
//
//    public static void main(String[] args) {
//        List<Double> temps = getTempFromServer();
//        draw(temps);
//
//    }
//
//    private static List<Double> getTempFromServer() {
//        RestTemplate restTemplate = new RestTemplate();
//        String url = "http://localhost:8080/measurements";
//        MeasurementsResponse response = restTemplate.getForObject(url, MeasurementsResponse.class);
//
//        if (response != null && response.getMeasurements() != null) {
//            return response.getMeasurements().stream().map(MeasurementDTO::getValue)
//                    .collect(Collectors.toList());
//        } return Collections.emptyList();
//
//    }
//
//    public static void draw(List<Double> temps) {
//        double[] xData = IntStream.range(0, temps.size()).asDoubleStream().toArray();
//        double[] yData = temps.stream().mapToDouble(x -> x).toArray();
//        XYChart chart = QuickChart.getChart("Temperatures", "X", "Y",
//                "f", xData, yData);
//
//        new SwingWrapper(chart).displayChart();
//    }
//}


package ru.vasilyev;

import org.knowm.xchart.QuickChart;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.SwingWrapper;
import org.springframework.web.client.RestTemplate;
import ru.vasilyev.dto.MeasurementDTO;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class DrawChart {

    public static void main(String[] args) {
        // Получение данных с сервера
        List<Double> temps = getTempFromServer();

        // Проверяем наличие данных перед визуализацией
        if (temps.isEmpty()) {
            System.out.println("No temperature data received from the server.");
        } else {
            draw(temps);
        }
    }

    /**
     * Получение списка температур с сервера
     */
    private static List<Double> getTempFromServer() {
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8080/measurements";

        try {
            // Получаем массив измерений с сервера
            MeasurementDTO[] response = restTemplate.getForObject(url, MeasurementDTO[].class);

            // Преобразуем в список температур
            if (response != null) {
                return List.of(response).stream()
                        .map(MeasurementDTO::getValue)
                        .collect(Collectors.toList());
            }
        } catch (Exception e) {
            System.err.println("Error while fetching data: " + e.getMessage());
        }

        // Возвращаем пустой список в случае ошибки
        return Collections.emptyList();
    }

    /**
     * Визуализация списка температур с помощью XYChart
     */
    public static void draw(List<Double> temps) {
        double[] xData = IntStream.range(0, temps.size()).asDoubleStream().toArray();
        double[] yData = temps.stream().mapToDouble(x -> x).toArray();

        // Построение графика
        XYChart chart = QuickChart.getChart(
                "Temperatures", // Заголовок графика
                "Measurement #", // Подпись оси X
                "Temperature (°C)", // Подпись оси Y
                "Temperature", // Легенда
                xData, yData
        );

        // Отображение графика
        new SwingWrapper<>(chart).displayChart();
    }
}
