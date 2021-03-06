package ClientPackage;

import ServerPackage.IWillNameItLater.WrongTypeOfFieldException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.*;
import java.util.Map;
import java.util.Scanner;

/**
 * Класс, реализующий проверку команд на валидность и отправку их на сервер
 * @author Maxim Antonov and Andrey Lyubkin
 */
public class Validator3000 implements invoker {
    protected Scanner scan;
    protected FieldSetter fieldSetter = new FieldSetter();

    protected String userCommand;
    protected String[] userCommand_;
    //protected receiver res;

    String sentence = new String();
    String key = new String();
    byte[] finalReceiveData;
    byte[] sendData;
    byte[] receiveData = new byte[4*1024];
    DatagramSocket clientSocket;

    protected Map<String, String> bufferMap;
    protected String[] bufferStringForArgs;


    {
        userCommand = "";
        scan=new Scanner(System.in);
    }

    /**
     * Метод перехода в интерактивный режим
     * @param del Символ для приглашения к вводу
     * @throws IOException Ошибка файла
     * @throws SocketException Не найден сокет
     * @throws UnknownHostException Неизвестный хост
     */
    @Override
    public void interactiveMod(String del) throws IOException, SocketException, UnknownHostException {


        System.out.print(del);
        while (true) {


            if (scan.hasNextLine()) {

                try {


                    userCommand = scan.nextLine();
                    userCommand_ = userCommand.trim().split(" ", 2);
                    userCommand_[0] = userCommand_[0].toLowerCase();

                    switch (userCommand_[0]) {

                        case ("add"):
                            if (userCommand_.length==1) {
                                key = "add";
                                fieldSetter.setFields();
                                String data = key+fieldSetter.getStringToSend();
                                sendSmth(data);
                                //add.execute(res);
                            }else {
                                System.out.print("Неверный синтаксис команды. Используйте help."+"\n$");
                            }
                            break;

                        case ("show"):
                            if (userCommand_.length==1) {
                                key = "show";
                                sendSmth(key);
                                //show.execute(res);
                            }else {
                                System.out.print("Неверный синтаксис команды. Используйте help."+"\n$");
                            }
                            break;

                        case ("info"):
                            if (userCommand_.length==1) {
                                key = "info";
                                sendSmth(key);
                                //info.execute(res);
                            }else {
                                System.out.print("Неверный синтаксис команды. Используйте help."+"\n$");
                            }
                            break;

                        case ("update"):
                            if (userCommand_.length == 2) {
                                key = "update";
                                sendSmth(key+"="+userCommand_[1]);
                                String s = new String(finalReceiveData);
                                if(s.equals("Объект с таким id найден\n")){
                                    fieldSetter.setFields();
                                    sendSmth(key+"="+"id"+"="+userCommand_[1]+fieldSetter.getStringToSend());
                                }
                            }else {
                                System.out.print("Неверный синтаксис команды. Используйте help."+"\n$");
                            }
                            break;

                        case ("clear"):
                            if (userCommand_.length==1) {
                                //clear.execute(res);
                                key = "clear";
                                sendSmth(key);
                            }else {
                                System.out.print("Неверный синтаксис команды. Используйте help."+"\n$");
                            }
                            break;


                        case ("remove_by_id"):
                            if(userCommand_.length == 2) {
                                long checkType = Long.parseLong(userCommand_[1]);
                                //bufferMap.put("file_name", userCommand_[1].trim());
                                //remove_by_id.getTransporter().SetParams(bufferMap);
                                //remove_by_id.execute(res);
                                key = "remove_by_id";
                                sendSmth(key+"="+"id"+"="+userCommand_[1]);
                            }else {
                                System.out.print("Неверный синтаксис команды. Используйте help."+"\n$");
                            }
                            break;


                        case ("remove_head"):
                            if (userCommand_.length==1) {
                                //removeHead.execute(res);
                                key = "remove_head";
                                sendSmth(key);
                            }else {
                                System.out.print("Неверный синтаксис команды. Используйте help."+"\n$");
                            }
                            break;


                        case ("remove_any_by_nationality"):
                            if(userCommand_.length == 2) {
                                //bufferMap.put("nationality", userCommand_[1].trim());
                                //removeAnyByNationality.getTransporter().SetParams(bufferMap);
                                //removeAnyByNationality.execute(res);
                                userCommand_[1] = userCommand_[1].toUpperCase();
                                key = "remove_any_by_nationality";
                                sendSmth(key+"="+"nationality"+"="+userCommand_[1]);
                            }else {
                                System.out.print("Неверный синтаксис команды. Используйте help."+"\n$");
                            }
                            break;


                        case ("count_less_than_location"):
                            if(userCommand_.length == 2) {
                                bufferMap.put("location", userCommand_[1].trim());
                                //countLessThanLocation.getTransporter().SetParams(bufferMap);
                                //countLessThanLocation.execute(res);
                                key = "count_less_than_location";
                                sendSmth(key+"="+"location"+"="+userCommand_[1]);
                            }else {
                                System.out.print("Неверный синтаксис команды. Используйте help."+"\n$");
                            }
                            break;


                        case ("filter_starts_with_name"):
                            if(userCommand_.length == 2) {
                                //bufferMap.put("name", userCommand_[1].trim());
                                //filterStartsWithName.getTransporter().SetParams(bufferMap);
                                //filterStartsWithName.execute(res);
                                key = "filter_starts_with_name";
                                sendSmth(key+"="+"name"+"="+userCommand_[1]);
                            }else {
                                System.out.print("Неверный синтаксис команды. Используйте help."+"\n$");
                            }
                            break;


                        case ("save"):
                            if (userCommand_.length==1) {
                                //save.execute(res);
                                key = "save";
                                sendSmth(key);
                            }else {
                                System.out.print("Неверный синтаксис команды. Используйте help."+"\n$");
                            }
                            break;


                        case ("execute_script"):
                            if(userCommand_.length == 2) {
                                //executeScript.getTransporter().SetParams(bufferMap);
                                //executeScript.execute(res);
                                File file = new File(userCommand_[1]);
                                if (file.exists()==false){
                                    throw new FileNotFoundException();
                                }
                                key = "execute_script";
                                sendSmth(key+"="+"file_name"+"="+userCommand_[1]);
                            }else {
                                System.out.print("Неверный синтаксис команды. Используйте help."+"\n$");
                            }
                            break;

                        case ("exit"):
                            if (userCommand_.length==1) {
                                //exit.execute(res);
                                key = "exit";
                                try {
                                    sendSmth(key);
                                }catch (SocketTimeoutException ex){
                                    System.out.println("Закрываем клиент и сервер");
                                    System.exit(0);
                                }
                            }else {
                                System.out.print("Неверный синтаксис команды. Используйте help."+"\n$");
                            }
                        case ("history"):
                            if (userCommand_.length==1) {
                                //history.execute(res);
                                key = "history";
                                sendSmth(key);
                            }else {
                                System.out.print("Неверный синтаксис команды. Используйте help."+"\n$");
                            }
                            break;


                        case ("add_if_min"):
                            if (userCommand_.length==1) {
                                //addIfMin.execute(res);
                                key = "add_if_min";
                                fieldSetter.setFields();
                                String data = key+fieldSetter.getStringToSend();
                                sendSmth(data);
                            }else {
                                System.out.print("Неверный синтаксис команды. Используйте help."+"\n$");
                            }
                            break;


                        case ("help"):
                            if (userCommand_.length==1) {
                                //help.execute(res);
                                key = "help";
                                sendSmth(key);
                            }else {
                                System.out.print("Неверный синтаксис команды. Используйте help."+"\n$");
                            }
                            break;


                        default:
                            if (!userCommand_[0].equals("")) {
                                System.out.print("Кажется, что-то пошло не так. Чтобы посмотреть доступные команды, используйте 'help'"+"\n$");
                            }
                            break;


                    }
                } catch (NumberFormatException ex) {
                    System.out.println("Неподходящее значения для поля\n$");

                } catch (FileNotFoundException ex) {
                    System.out.println("Файл не найден\n$");

                } catch (WrongTypeOfFieldException e) {
                    e.printStackTrace();
                }catch (SocketTimeoutException ex){
                    System.out.println("Превышен интервал ожидания");
                    System.exit(0);
                }

            }else break;


        }


    }
    private void sendSmth(String data) throws IOException {
        clientSocket = new DatagramSocket();
        InetAddress IPAddress = InetAddress.getByName(null);
        sentence = data;
        sendData = sentence.getBytes();
        DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, 9999);
        clientSocket.send(sendPacket);
        clientSocket.setSoTimeout(5000);
        DatagramPacket hui = new DatagramPacket(receiveData,receiveData.length);
        clientSocket.receive(hui);
        int size = hui.getLength();
        finalReceiveData = new byte[size];
        for (int i = 0; i < size; ++i){
            finalReceiveData[i] = receiveData[i];
        }
        System.out.print(new String(finalReceiveData));

    }
}
