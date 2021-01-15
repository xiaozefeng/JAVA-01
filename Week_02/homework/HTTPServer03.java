package io.github;

import java.io.*;
import java.net.*;
import java.util.concurrent.*;

public class HTTPServer03 {
    public static void main(String[]args) throws Exception{
        ExecutorService es = Executors.newFixedThreadPool(40);
        ServerSocket ss = new ServerSocket(8801);
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
            Thread.sleep(20);
            PrintWriter pw = new PrintWriter(socket.getOutputStream(), true);
            pw.println("HTTP/1.1 200 OK");
            pw.println("Content-Type:text/html;character=utf-8");
            pw.println();
            pw.write("hello nio");
            pw.close();
            socket.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    
}