package ServerPackage;

import java.io.PrintStream;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;

/**
 * Класс для управления информацией для отправки на клиент
 */
public class Log4J2 extends PrintStream {

    public static List<String> printList = new LinkedList<>();

    /**
     * @param org объект класса PrintStream
     */
    public Log4J2(PrintStream org) {
        super(org);
    }

    @Override
    public void println(String line) {
        printList.add(line);
        super.println(line);
    }

    /**
     * ВОзвращает последний элемент списка
     * @return String
     */
    public String getLast() {
        return printList.get(printList.size() - 1);
    }


    @Override
    public void print(String line) {
        super.print(line+"\n");


    }
    public void addText(String line) {
        printList.add(line);
    }

    /**
     * Посылает содержимое списка
     * @return String
     */
    public String sendTxt(){
        int i=0;
        String res="";
        for (String s: printList){
            if (i != printList.size()-1) {
                res = res + s + "\n";
            }
            else {res = res + s;}
            i++;
        }
        return res;
    }


    /**
     * Очищает коллекцию
     */
    public void clear(){
        printList.clear();
    }
}