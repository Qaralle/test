import java.io.*;
import java.net.*;

public class Client
{
    public static void main(String args[]) throws Exception
    {
        byte[] bytes;
        BufferedReader inFromUser =
                new BufferedReader(new InputStreamReader(System.in));
        DatagramSocket clientSocket = new DatagramSocket();
        InetAddress IPAddress = InetAddress.getByName(null);
        byte[] sendData;
        byte[] receiveData = new byte[1024];
        while (true) {
            String sentence = inFromUser.readLine();
            sendData = sentence.getBytes();
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, 9999);
            clientSocket.send(sendPacket);
            DatagramPacket hui = new DatagramPacket(receiveData,receiveData.length);
            clientSocket.receive(hui);
            int size = hui.getLength();
            bytes = new byte[size];

            for (int i = 0; i < size; ++i){
                bytes[i] = receiveData[i];
            }
            System.out.print(new String(bytes));


        }
    }
}