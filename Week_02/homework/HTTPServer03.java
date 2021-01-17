package io.github;

import java.io.*;
import java.net.*;
import java.util.concurrent.*;

public class HTTPServer03 {
    public static void main(String[]args) throws Exception{
        ExecutorService es = Executors.newFixedThreadPool(100);
        ServerSocket ss = new ServerSocket(8003);
        while (true) {
            try {
                Socket socket =ss.accept();
                es.execute(()-> { process(socket);});
            }catch (Exception e ) {
                e.printStackTrace();
            }
        }
    }
    private static void process(Socket socket) {
        try {
            PrintWriter pw = new PrintWriter(socket.getOutputStream(), true);
            pw.println("HTTP/1.1 200 OK");
            pw.println("Content-Type:text/html;character=utf-8");
            String body = "hello,nio";
            pw.println("Content-Length:" + body.getBytes().length);
            pw.println();
            pw.write(body);
            pw.close();
            socket.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    
}