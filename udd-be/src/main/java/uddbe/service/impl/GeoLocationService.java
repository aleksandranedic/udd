package uddbe.service.impl;

import org.elasticsearch.common.geo.GeoPoint;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import org.json.JSONArray;
import org.json.JSONObject;
import org.elasticsearch.common.geo.GeoPoint;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
@Service
public class GeoLocationService {

    public GeoPoint getCoordinatesBasedOnAddress(String address) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .version(HttpClient.Version.HTTP_2)
                .uri(URI.create("https://positionstack.com/geo_api.php?query="+ URLEncoder.encode(address, StandardCharsets.UTF_8.toString())))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        JSONObject responseJSON = new JSONObject(response.body());
        JSONArray arrayData = responseJSON.getJSONArray("data");
        JSONObject object = arrayData.getJSONObject(0);
        System.out.println(object.getDouble("latitude"));
        return new GeoPoint(object.getDouble("latitude"),object.getDouble("longitude"));
    }

    public String getCityBasedOnIpAddress(String ipAddress) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .version(HttpClient.Version.HTTP_2)
                .uri(URI.create("https://api.ipgeolocation.io/ipgeo?apiKey=37780cebecf646769a73f726a0a2e3fa&ip="+ ipAddress))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        JSONObject responseJSON = new JSONObject(response.body());
        System.out.println(responseJSON.getString("city"));
        return responseJSON.getString("city");
    }

}