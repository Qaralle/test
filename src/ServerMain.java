import ClassCollection.CollectionTask;
import ServerPackage.CollectionUnit;
import ServerPackage.IWillNameItLater.WrongTypeOfFieldException;
import ServerPackage.IWillNameItLater.receiver;
import ServerPackage.StorePrintStream;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.TimeUnit;
public class ServerMain
{
    private static StorePrintStream SustemOut = new StorePrintStream(System.out);
    private static final String hui = "hui";
    private static CollectionTask collectionTask;
    private static receiver CU;
    private static String str;

    public static void main(String args[]) throws Exception
    {

        collectionTask = new CollectionTask();
        try {
            collectionTask.load(args[0]);
            CU = new CollectionUnit(collectionTask, args[0]);
        }catch (Exception ex){
            collectionTask.load("C:\\Users\\proge\\IdeaProjects\\test\\src\\PersonClassTest.json");
            CU = new CollectionUnit(collectionTask, "C:\\Users\\proge\\IdeaProjects\\test\\src\\PersonClassTest.json");
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
                for (int i=1; i < userCommand.length; i+=2){
                    fields.put(userCommand[i], userCommand[i+1]);
                    collectionTask.getCommandMap().get(userCommand[0]).getTransporter().SetParams(fields); //костыль чтоб работал, потом переделать нормально (добавить всем тарнспортер)
                }
                collectionTask.getCommandMap().get(userCommand[0]).execute(CU);
            } catch (WrongTypeOfFieldException e) {
                e.printStackTrace();
            }

            //
            if (!CU.getResponse().equals("")){
                channel.send(ByteBuffer.wrap(CU.getResponse().getBytes()),from);
            }else {
                str = SustemOut.getLast();
                ByteBuffer lol = ByteBuffer.wrap(str.getBytes());
                channel.send(lol, from);
            }
        }
        System.out.println( "отрубаюсь" );
    }

}