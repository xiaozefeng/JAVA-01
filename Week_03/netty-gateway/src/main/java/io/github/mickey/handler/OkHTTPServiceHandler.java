package io.github.mickey.handler;

import okhttp3.*;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.function.Consumer;

public class OkHTTPServiceHandler implements ServiceHandler {

    private final Logger logger = LoggerFactory.getLogger(OkHTTPServiceHandler.class);
    private final OkHttpClient client = new OkHttpClient();

    @Override
    public byte[] handle(String url, Consumer<byte[]> callback) {
        Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                logger.error(e.getMessage(), e);
                call.cancel();
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
                try(final ResponseBody body = response.body();) {
                    final String content = body.string();
                    logger.info("response string:${}", content);
                    callback.accept(content.getBytes(StandardCharsets.UTF_8));
                }
            }
        });
        return null;
    }
}
