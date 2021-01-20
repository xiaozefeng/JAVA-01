package io.github.mickey;


import io.github.mickey.server.BootstrapServer;

public class Application {

    private static final int PORT = Integer.parseInt(System.getProperty("port", "8888"));

    public static void main(String[] args) throws Exception {
        Application.run();
    }

    private static void run() throws Exception {
        new BootstrapServer(Application.PORT).start();
    }

}
