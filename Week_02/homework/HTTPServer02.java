package io.github;

import java.io.*;
import java.net.*;

public class HTTPServer02 {
    public static void main(String[]args) throws Exception{
        ServerSocket ss = new ServerSocket(8002);
        while (true) {
            try {
                Socket socket =ss.accept();
                new Thread(()-> {
                    process(socket);
                }).start();
            }catch (Exception e ) {
                e.printStackTrace();
            }
        }
    }
    private static void process(Socket socket) {
        try {
            PrintWriter pw = new PrintWriter(socket.getOutputStream(), true);
            pw.println("HTTP/1.1 200 OK");
            pw.println("Content-Type:text/html;charset=utf-8");
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
