package id.rekaestudigital.mumu.util;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

public class ZohoApiClient {

    private static final String BASE_URL = "https://www.zohoapis.com";
    private static final String GET_PATH = "/crm/v7/functions/ekonid_rest_api/actions/execute";

    private final HttpClient httpClient;

    public ZohoApiClient() {
        this.httpClient = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_1_1)
                .build();
    }

    public String getData(
            String apiKey,
            String moduleName,
            String startDate,
            int pageNum,
            int pageCount) throws IOException, InterruptedException {

        String url = BASE_URL + GET_PATH
                + "?auth_type=" + encode("apikey")
                + "&zapikey=" + encode(apiKey)
                + "&modulename=" + encode(moduleName)
                + "&startdate=" + encode(startDate)
                + "&pagenum=" + pageNum
                + "&pagecount=" + pageCount;

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .header("Accept", "application/json")
                .build();

        HttpResponse<String> response = httpClient.send(
                request,
                HttpResponse.BodyHandlers.ofString()
        );

        if (response.statusCode() < 200 || response.statusCode() >= 300) {
            throw new IOException("Zoho API error. HTTP Status=" + response.statusCode()
                    + ", Body=" + response.body());
        }

        return response.body();
    }

    private String encode(String value) {
        if (value == null) {
            return "";
        }

        return URLEncoder.encode(value, StandardCharsets.UTF_8);
    }
}