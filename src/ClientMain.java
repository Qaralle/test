import ClientPackage.Validator3000;

/**
 * Класс для запуска приложения клиента
 */
public class ClientMain
{
    public static void main(String args[]) throws Exception
    {

        Validator3000 terminal = new Validator3000();
        terminal.interactiveMod("$");

    }
}