package io.github.mickey.handler;

import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.function.Consumer;

@Slf4j
public class OkHTTPServiceHandler implements ServiceHandler {

    private final OkHttpClient client = new OkHttpClient();

    @Override
    public void handle(String url, Consumer<byte[]> callback) {
        Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                log.error(e.getMessage(), e);
                call.cancel();
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
                try (final ResponseBody body = response.body();) {
                    final String content = body.string();
                    log.info("response string:${}", content);
                    callback.accept(content.getBytes(StandardCharsets.UTF_8));
                }
            }
        });
    }
}
