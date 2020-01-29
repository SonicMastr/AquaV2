package com.jaylon.aqua.utils;

import com.google.gson.Gson;
import com.jaylon.aqua.objects.weeb.WeebObject;
import com.jaylon.aqua.objects.Response;
import org.json.JSONObject;

import java.io.*;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;


public class Request {

    public Response request(HttpRequest request) throws IOException, InterruptedException {
            HttpClient client = HttpClient.newHttpClient();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        int status = response.statusCode();
            if (status > 299) {
                System.out.println(response.body());
            }
        return new Response(response.statusCode(), response.body());
    }

    public static JSONObject parse(String responseBody) throws IOException {
        Gson gson = new Gson();
        WeebObject anime = gson.fromJson(responseBody, WeebObject.class);
        FileWriter writer = new FileWriter("c:\\Users\\jaylo\\test\\anime.json");
        String out = gson.toJson(anime);
        System.out.println(responseBody);
        System.out.println(out);
        return new JSONObject(responseBody);
    }
}
