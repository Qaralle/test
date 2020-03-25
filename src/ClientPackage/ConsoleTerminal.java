package ClientPackage;

import java.util.HashMap;
import java.util.Scanner;

/**
 * Класс, предоставляющий консоль
 * @author Maxim Antonov and Andrey Lyubkin
 */
public class ConsoleTerminal extends Terminal {

    /**
     * @param res_ Receiver (объект класса CollectionUnit)
     */
    /*public ConsoleTerminal(receiver res_){
        super(res_);

    }*/

    {
        userCommand = "";
        scan=new Scanner(System.in);
        /*add=new Add(new ConsoleTransporter());
        show=new Show();
        info=new Info();
        update=new Update(new ConsoleTransporter());
        clear=new Clear();
        remove_by_id=new RemoveById(new ConsoleTransporter());
        removeHead=new RemoveHead();
        removeAnyByNationality=new RemoveAnyByNationality(new ConsoleTransporter());
        countLessThanLocation=new CountLessThanLocation(new ConsoleTransporter());
        filterStartsWithName=new FilterStartsWithName(new ConsoleTransporter());
        save=new Save();*/
        bufferMap = new HashMap<>();
        /*executeScript = new ExecuteScript(new ConsoleTransporter());
        exit=new Exit();
        history=new History();
        addIfMin = new AddIfMin(new ConsoleTransporter());
        help=new Help();*/


    }

    /*private String ObjectsName;

    public void GetLine() {
        ObjectsName=scan.nextLine();
    }

    public void GetObjectsName(){
        System.out.println(ObjectsName);
    }*/

}
