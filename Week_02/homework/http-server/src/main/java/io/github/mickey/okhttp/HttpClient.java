package io.github.mickey.okhttp;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

public class HttpClient {

    public static void main(String[] args) throws IOException {
        OkHttpClient client = new OkHttpClient();
        String url = "http://localhost:8801";
        String res = doGet(client, url);
        System.out.println(res);
    }

    private static String doGet(OkHttpClient client,
                                String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();
        try (Response res = client.newCall(request).execute()) {
            return res.body().string();
        }
    }


}
