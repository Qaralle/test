package ServerPackage;

import java.io.PrintStream;
import java.util.LinkedList;
import java.util.List;

public class StorePrintStream extends PrintStream {

    public static List<String> printList = new LinkedList<String>();

    public StorePrintStream(PrintStream org) {
        super(org);
    }

    @Override
    public void println(String line) {
        printList.add(line);
        super.println(line);
    }

    public String getLast() {
        return printList.get(printList.size()-1);
    }

    // And so on for double etc..
}