package org.example;

import com.fazecast.jSerialComm.SerialPort;

public class StudentApp {
    public static void main(String[] args) {
        SerialPort comPort = SerialPort.getCommPort("COM7");
        comPort.openPort();

        // Установка параметров порта
        int baudRate = 115200; // скорость передачи данных
        int dataBits = 8;     // биты данных
        int stopBits = 1;     // стоп-биты
        int parity = SerialPort.NO_PARITY;  // четность
        comPort.setComPortParameters(baudRate, dataBits, stopBits, parity);

        comPort.setComPortTimeouts(SerialPort.TIMEOUT_READ_SEMI_BLOCKING, 0, 0);

        // Отправка сообщения
        String message = "Hi, I want to pass the lab";
        comPort.writeBytes(message.getBytes(), message.length());

        byte[] readBuffer = new byte[100];
        int numRead = comPort.readBytes(readBuffer, readBuffer.length);
        System.out.println("Прочитано " + numRead + " байтов.");
        String response = new String(readBuffer, 0, numRead);
        System.out.println("Ответ от сервера: " + response);

        comPort.closePort();
    }
}