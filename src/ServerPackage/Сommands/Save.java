package ServerPackage.Сommands;

import java.io.IOException;
import ServerPackage.IWillNameItLater.*;

/**
 * Класс, предоставляющий реализацию команды save (сохранить коллекцию в файл)
 * @author Maxim Antonov and Andrey Lyubkin
 */
public class Save implements Command {
    /**
     * выполнить команду
     * @param res Receiver (объект класса CollectionUnit)
     */
    @Override
    public void execute(receiver res) {
        try {
            res.save();
            res.addCommandToHistory("save");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
