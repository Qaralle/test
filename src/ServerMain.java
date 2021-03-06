import ClassCollection.CollectionTask;
import ColClass.Person;
import ServerPackage.CollectionUnit;
import ServerPackage.IWillNameItLater.WrongTypeOfFieldException;
import ServerPackage.IWillNameItLater.receiver;
import ServerPackage.Log4J2;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.*;
import java.util.stream.Stream;

/**
 * Класс для запуская приложения сервера
 */
public class ServerMain
{
    private static Log4J2 SustemOut = new Log4J2(System.out);
    private static final String hui = "hui";
    private static CollectionTask collectionTask;
    private static receiver CU;
    private static String str;
    private static List<String> sendText= new ArrayList<>();

    public static void main(String args[]) throws Exception
    {

        collectionTask = new CollectionTask();
        try {
            collectionTask.load(args[0]);
            CU = new CollectionUnit(collectionTask, args[0]);
        }catch (Exception ex){
            /*collectionTask.load("C:\\Users\\proge\\IdeaProjects\\test\\src\\PersonClassTest.json");
            CU = new CollectionUnit(collectionTask, "C:\\Users\\proge\\IdeaProjects\\test\\src\\PersonClassTest.json");*/
            collectionTask.load("C:\\Users\\user\\Documents\\test\\src\\PersonClassTest.json");
            CU = new CollectionUnit(collectionTask,"C:\\Users\\user\\Documents\\test\\src\\PersonClassTest.json");
        }


        DatagramChannel chan = DatagramChannel.open();
        chan.socket().bind( new InetSocketAddress( 9999 ) );
        chan.configureBlocking(false);

        Selector selector = Selector.open();
        chan.register(selector,SelectionKey.OP_READ);
        ByteBuffer buffer = ByteBuffer.allocate(4*1024);

        while (true) {
            selector.select();
            Set<SelectionKey> selectedKeys = selector.selectedKeys();
            Iterator<SelectionKey> iter = selectedKeys.iterator();
            while (iter.hasNext()) {

                SelectionKey key = iter.next();

                if (key.isReadable()) {
                    buffer.clear();
                    buffer.put(new byte[4*1024]);
                    buffer.clear();
                    action(buffer, key);
                }
                iter.remove();
            }
        }

    }

    /**
     * Метод, реализующий взаимодействие сервера с запросом
     * @param buffer буфер типа ByteBuffer
     * @param key SelectionKey для работы с несколькими клиентами
     * @throws IOException
     * @throws InterruptedException
     */
    private static void action(ByteBuffer buffer, SelectionKey key) throws IOException, InterruptedException {

        DatagramChannel channel = (DatagramChannel)key.channel();

        buffer.clear();
        System.out.println("патключаус к нон блок аналу");
        SocketAddress from = channel.receive(buffer);
        System.out.println("внатуре успех");

        ByteBuffer finalBuffer = ByteBuffer.allocate(buffer.position());

        for (int i = 0; i < buffer.position(); ++i){
            finalBuffer.put(i, buffer.get(i));
        }

        if (from != null) {
            buffer.flip();
            String val = new String(finalBuffer.array());
            System.out.println(from);
            System.out.println(channel);
            System.out.println("Сервер получил по ебалу: "+val);

            try {
                String userCommand[] = val.split("=");
                HashMap<String, String> fields = new HashMap<>();
                if((userCommand[0].equals("update")) && (userCommand.length == 2)){
                    Stream<Person> personStream = CU.getCT().GetCollection().stream();
                    if(personStream.anyMatch(person -> person.getId() == Long.parseLong(userCommand[1]))){
                        SustemOut.print("Объект с таким id найден");
                    }else SustemOut.print("Объект с таким id не найден");
                }else {
                    for (int i = 1; i < userCommand.length; i += 2) {
                        fields.put(userCommand[i], userCommand[i + 1]);
                        collectionTask.getCommandMap().get(userCommand[0]).getTransporter().SetParams(fields); //костыль чтоб работал, потом переделать нормально (добавить всем тарнспортер)
                    }
                    collectionTask.getCommandMap().get(userCommand[0]).execute(CU);
                }
            } catch (WrongTypeOfFieldException e) {
                e.printStackTrace();
                
            }
             if(SustemOut.getLast().equals("Объект с таким id найден")) str = SustemOut.sendTxt()+"\n";
                else str = SustemOut.sendTxt()+"$";
                SustemOut.clear();
                ByteBuffer lol = ByteBuffer.wrap(str.getBytes());
                channel.send(lol, from);
                CU.setResponse("");

        }
        System.out.println( "отрубаюсь" );
    }

}