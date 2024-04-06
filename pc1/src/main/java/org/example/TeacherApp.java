package org.example;

import com.fazecast.jSerialComm.SerialPort;

public class TeacherApp {
    public static void main(String[] args) {
        SerialPort comPort = SerialPort.getCommPort("COM6");
        comPort.openPort();
        System.out.println("Слушаем "+ comPort.getDescriptivePortName());

        // Установка параметров порта
        int baudRate = 115200; // скорость передачи данных
        int dataBits = 8;     // биты данных
        int stopBits = 1;     // стоп-биты
        int parity = SerialPort.NO_PARITY;  // четность
        comPort.setComPortParameters(baudRate, dataBits, stopBits, parity);

        comPort.setComPortTimeouts(SerialPort.TIMEOUT_READ_SEMI_BLOCKING, 0, 0);

        byte[] readBuffer = new byte[100];
        int numRead;
        String ms = "Lab work credited";
        while (true) {
            numRead = comPort.readBytes(readBuffer, readBuffer.length);
            if (numRead > 0) {
                System.out.println("Прочитано " + numRead + " байтов.");
                String received = new String(readBuffer, 0, numRead);
                System.out.println("Получено: " + received);
                if(received.equals("Hi, I want to pass the lab")){
                    System.out.println("Отправитель хочет сдать лабу!");
                    comPort.writeBytes(ms.getBytes(), ms.length());
                } else if(received.equals("Exit")){
                    System.out.println("Получено сообщение 'Exit'. Завершение работы...");
                    break;
                }
            }
        }

        comPort.closePort();
    }
}