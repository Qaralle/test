package ServerPackage;

import java.io.PrintStream;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;

public class StorePrintStream extends PrintStream {

    public static List<String> printList = new LinkedList<>();

    public StorePrintStream(PrintStream org) {
        super(org);
    }

    @Override
    public void println(String line) {
        printList.add(line);
        super.println(line);
    }

    public String getLast() {
        return printList.get(printList.size() - 1);
    }


    @Override
    public void print(String line) {
        printList.add(line);
    }

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

    public void clear(){
        printList.clear();
    }

    // And so on for double etc..
}