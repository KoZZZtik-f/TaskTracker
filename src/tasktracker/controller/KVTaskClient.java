package tasktracker.controller;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class KVTaskClient {
    private String uriStr;
    private String apiKey;
    private HttpClient client;
    private HttpRequest request;

    public KVTaskClient(String uriStr) {
        this.uriStr = uriStr;
        client = HttpClient.newHttpClient();
        System.out.println(uriStr + "/register"); //debug
        request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(uriStr + "/register"))
                .header("Accept", "application/json")
                .build();

        HttpResponse.BodyHandler<String> handler = HttpResponse.BodyHandlers.ofString();
        try {
            HttpResponse<String> response = client.send(request, handler);
            if (response.statusCode() == 200) {
                apiKey = response.body();
                System.out.println(apiKey);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void put(String key, String json) {
        request = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .uri(URI.create(uriStr + "/save/" + key + "?API_TOKEN=" + apiKey))
                .version(HttpClient.Version.HTTP_1_1)
                .header("Content-type", "application/json")
                .build();

        HttpResponse<String> response;
        try {
            HttpResponse.BodyHandler<String> handler = HttpResponse.BodyHandlers.ofString();
            response = client.send(request, handler);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        if (response.statusCode() == 200) {
            System.out.println("Запрос на добавление " + key + "/" + json + " от клиента завершился успешно");
        } else {
            System.out.println("Ошибка при добавлении " + key + "/" + json + " от клиента");
        }
    }

   public String load(String key) {
        request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(uriStr + "/load/" + key + "?API_TOKEN=" + apiKey))
                .version(HttpClient.Version.HTTP_1_1)
                .header("Accept", "application/json")
                .build();

        HttpResponse<String> response;
        try {
            HttpResponse.BodyHandler<String> handler = HttpResponse.BodyHandlers.ofString();
            response = client.send(request, handler);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        if (response.statusCode() == 200) {
            System.out.println("Получение объекта по ключу от клиента завершилось успешно. Объект=" + response.body());
            return response.body();
        } else {
            return null;
        }
    }

}
