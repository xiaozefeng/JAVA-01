package io.github.mickey;



public class Application {

    private static final int PORT = Integer.parseInt(System.getProperty("proxyService", "backend"));

    public static void main(String[] args) {
        Application.run(PORT);
    }

    private static void run(int port) {
        new InnerServer(port).start();
    }

}
