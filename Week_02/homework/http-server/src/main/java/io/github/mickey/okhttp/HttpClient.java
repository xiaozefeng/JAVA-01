package io.github.mickey.okhttp;

import okhttp3.*;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class HttpClient {

    public static void main(String[] args) throws IOException {
        OkHttpClient client = new OkHttpClient();
        String url = "http://localhost:8080";
        String res = doGet(client, url);
        System.out.println(res);
    }

    private static String doGet(OkHttpClient client,
                                String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();
//        try (Response res = client.newCall(request).execute()) {
//            return res.body().string();
//        }
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                final ResponseBody body = response.body();
                System.out.println(body.string());
                body.close();
            }
        });

        return "";

    }


}
