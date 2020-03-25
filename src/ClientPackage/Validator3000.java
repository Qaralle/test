package ClientPackage;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.*;
import java.util.Map;
import java.util.Scanner;

import ServerPackage.IWillNameItLater.WrongTypeOfFieldException;
import ServerPackage.IWillNameItLater.receiver;
import ServerPackage.Сommands.*;

/**
 * Абстрактный класс, выполняющий функци инвокера
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

    @Override
    public void interactiveMod(String del) throws IOException, SocketException, UnknownHostException {



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
                                System.out.println("Неверный синтаксис команды. Используйте help.");
                            }
                            break;

                        case ("show"):
                            if (userCommand_.length==1) {
                                key = "show";
                                sendSmth(key);
                                //show.execute(res);
                            }else {
                                System.out.println("Неверный синтаксис команды. Используйте help.");
                            }
                            break;

                        case ("info"):
                            if (userCommand_.length==1) {
                                key = "info";
                                sendSmth(key);
                                //info.execute(res);
                            }else {
                                System.out.println("Неверный синтаксис команды. Используйте help.");
                            }
                            break;

                        /*case ("update"):
                            if (userCommand_.length == 2) {
                                if (res.getCT().GetCollection().size()==0){
                                    System.out.println("Коллекция пуста!");
                                }else {
                                    bufferMap.put("id", userCommand_[1].trim());
                                    for (int i = 0; i < res.getCT().GetCollection().size(); ++i) {
                                        if (Long.parseLong(bufferMap.get("id")) == res.getCT().GetCollection().get(i).getId()) {
                                            bufferMap.put("index", String.valueOf(i));
                                            update.getTransporter().SetParams(bufferMap);
                                            update.execute(res);
                                            break;
                                        }
                                        if (i == res.getCT().GetCollection().size() - 1) {
                                            System.out.println("Объекта с таким id нет");
                                        }
                                    }
                                }
                            }else {
                                System.out.println("Неверный синтаксис команды. Используйте help.");
                            }
                            break;*/

                        case ("clear"):
                            if (userCommand_.length==1) {
                                //clear.execute(res);
                                key = "clear";
                                sendSmth(key);
                            }else {
                                System.out.println("Неверный синтаксис команды. Используйте help.");
                            }
                            break;


                        case ("remove_by_id"):
                            if(userCommand_.length == 2) {
                                bufferMap.put("file_name", userCommand_[1].trim());
                                //remove_by_id.getTransporter().SetParams(bufferMap);
                                //remove_by_id.execute(res);
                                key = "remove_by_id";
                                sendSmth(key+"="+"id"+"="+userCommand_[1]);
                            }else {
                                System.out.println("Неверный синтаксис команды. Используйте help.");
                            }
                            break;


                        case ("remove_head"):
                            if (userCommand_.length==1) {
                                //removeHead.execute(res);
                                key = "remove_head";
                                sendSmth(key);
                            }else {
                                System.out.println("Неверный синтаксис команды. Используйте help.");
                            }
                            break;


                        case ("remove_any_by_nationality"):
                            if(userCommand_.length == 2) {
                                bufferMap.put("nationality", userCommand_[1].trim());
                                //removeAnyByNationality.getTransporter().SetParams(bufferMap);
                                //removeAnyByNationality.execute(res);
                                key = "remove_any_by_nationality";
                                sendSmth(key+"="+"nationality"+"="+userCommand_[1]);
                            }else {
                                System.out.println("Неверный синтаксис команды. Используйте help.");
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
                                System.out.println("Неверный синтаксис команды. Используйте help.");
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
                                System.out.println("Неверный синтаксис команды. Используйте help.");
                            }
                            break;


                        case ("save"):
                            if (userCommand_.length==1) {
                                //save.execute(res);
                                key = "save";
                                sendSmth(key);
                            }else {
                                System.out.println("Неверный синтаксис команды. Используйте help.");
                            }
                            break;


                        case ("execute_script"):
                            if(userCommand_.length == 2) {
                                //executeScript.getTransporter().SetParams(bufferMap);
                                //executeScript.execute(res);
                                key = "execute_script";
                                sendSmth(key+"="+"file_name"+"="+userCommand_[1]);
                            }else {
                                System.out.println("Неверный синтаксис команды. Используйте help.");
                            }
                            break;

                        case ("exit"):
                            if (userCommand_.length==1) {
                                //exit.execute(res);
                                key = "exit";
                                sendSmth(key);
                            }else {
                                System.out.println("Неверный синтаксис команды. Используйте help.");
                            }
                        case ("history"):
                            if (userCommand_.length==1) {
                                //history.execute(res);
                                key = "history";
                                sendSmth(key);
                            }else {
                                System.out.println("Неверный синтаксис команды. Используйте help.");
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
                                System.out.println("Неверный синтаксис команды. Используйте help.");
                            }
                            break;


                        case ("help"):
                            if (userCommand_.length==1) {
                                //help.execute(res);
                                key = "help";
                                sendSmth(key);
                            }else {
                                System.out.println("Неверный синтаксис команды. Используйте help.");
                            }
                            break;


                        default:
                            if (!userCommand_[0].equals("")) {
                                System.out.println("Кажется, что-то пошло не так. Чтобы посмотреть доступные команды, используйте 'help'");
                            }
                            break;


                    }


                } catch (ArrayIndexOutOfBoundsException ex) {
                    System.out.println("Wrong syntax. Please use 'help'");

                } /*catch (FileNotFoundException ex) {
                    System.out.println("Файл не найден");

                }*/ catch (WrongTypeOfFieldException e) {
                    e.printStackTrace();
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

        DatagramPacket hui = new DatagramPacket(receiveData,receiveData.length);
        clientSocket.receive(hui);
        int size = hui.getLength();
        finalReceiveData = new byte[size];
        for (int i = 0; i < size; ++i){
            finalReceiveData[i] = receiveData[i];
        }
        System.out.println(new String(finalReceiveData));
    }
}
