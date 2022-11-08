package ru.nitestalker;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class SimpleWebServer {
    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(8080)) {
            System.out.println("Server started!");

            while (true) {
                // ожидаем подключения
                Socket socket = serverSocket.accept();
                System.out.println("Client connected!");

                // для подключившегося клиента открываем потоки
                // чтения и записи
                try (BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
                     PrintWriter output = new PrintWriter(socket.getOutputStream())) {

                    // ждем первой строки запроса
                    while (!input.ready()) ;

                    // считываем и печатаем все что было отправлено клиентом
                    System.out.println();
                    while (input.ready()) {
                        System.out.println(input.readLine());
                    }

                    double rand = Math.random();

                    if (rand < 0.3) {
                        // отправляем ответ RESPONSE
                        output.println("HTTP/1.1 203 OK"); // стартовая строка
                        output.println("Content-Type: text/html; charset=utf-8"); // строка заголовков
                        output.println(); // разделитель
                        output.println("<p>Привет всем!</p>" + rand); // сообщение
                        output.flush();
                        // по окончанию выполнения блока try-with-resources потоки,
                        // а вместе с ними и соединение будут закрыты
                        System.out.println("Client disconnected!");
                    } else if (rand >= 0.3 && rand < 0.6) {
                        // отправляем ответ RESPONSE
                        output.println("HTTP/1.1 404 Not Found"); // стартовая строка
                        output.println("Content-Type: text/html; charset=utf-8"); // строка заголовков
                        output.println(); // разделитель
                        output.println("<p>Hiding in shadows!</p>" + rand); // сообщение
                        output.flush();
                        // по окончанию выполнения блока try-with-resources потоки,
                        // а вместе с ними и соединение будут закрыты
                        System.out.println("Client disconnected!");
                    } else {
                        // отправляем ответ RESPONSE
                        output.println("HTTP/1.1 500 Server is bad!"); // стартовая строка
                        output.println("Content-Type: text/html; charset=utf-8"); // строка заголовков
                        output.println(); // разделитель
                        output.println("<p>Rest time :-)</p>" + rand); // сообщение
                        output.flush();
                        // по окончанию выполнения блока try-with-resources потоки,
                        // а вместе с ними и соединение будут закрыты
                        System.out.println("Client disconnected!");
                    }

                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
