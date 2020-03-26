package ServerPackage;

import ClassCollection.CollectionTask;
import ClassCollection.CompareCenter;
import ClassCollection.FieldPolice;
import ClassCollection.NullPolice;
import ColClass.*;
import ServerPackage.IWillNameItLater.FileTerminal;
import ServerPackage.IWillNameItLater.receiver;
import ServerPackage.FactoryPackage.CoordinatesMaker;
import ServerPackage.FactoryPackage.LocationMaker;
import ServerPackage.FactoryPackage.ObjectClassMaker;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.util.Iterator;
import java.util.Scanner;
import java.util.stream.Stream;

/**
 * Класс, реазилующий обработку коллекции
 * @author Maxim Antonov and Andrey Lyubkin
 */
public class CollectionUnit implements receiver {

    private static StorePrintStream SystemOut = new StorePrintStream(System.out);
    private CollectionTask ct;
    private CompareCenter compareCenter;
    private CoordinatesMaker cm;
    private LocationMaker lm;
    private ObjectClassMaker om;
    private FieldPolice fp;
    private NullPolice np;

    private Coordinates coo;
    private Location loc;
    private Person per;

    private String file_name;
    private String response = "";


    /**
     * @param CT Объект класса CollectionTask
     * @param file_name_ имя файла
     */
    public CollectionUnit(CollectionTask CT, String file_name_){
        this.ct=CT;
        this.file_name=file_name_;

    }

    {
        compareCenter=new CompareCenter();
        cm=new CoordinatesMaker();
        lm=new LocationMaker();
        om=new ObjectClassMaker();
        fp=new FieldPolice();
        np=new NullPolice();
    }

    /**
     * реализация команды add
     */
    @Override
    public void add(String name_, Double height_, Color eyeColor_, Color hairColor_, Country nationality_, Float x_, Double y_, Float x1_, double y1_, String name1_){
        coo=cm.create();
        coo.SetX(x_);
        coo.SetY(y_);
        np.CoordinatesReplace(coo);
        fp.CoordinatesReplace(coo);

        loc=lm.create();
        loc.SetX(x1_);
        loc.SetY(y1_);
        loc.SetName(name1_);
        //np.LocationReplace(loc);
        fp.LocationReplace(loc);

        per=om.create();

        per.setEverything(name_, coo, height_, eyeColor_, hairColor_, nationality_, loc);

        np.PersonReplace(per);
        fp.PersonReplace(per);
        ct.add(per);
        response = "Element added";
        SystemOut.print(response);
    }


    /**
     * реализация команды show
     */
    @Override
    public void show() {
        if (ct.GetCollection().size() > 0) {
            for (int i=0; i<ct.GetCollection().size(); ++i){
                Person s = ct.GetCollection().get(i);
                if(i==0){
                    response = "name: " + s.getName() + " id: " + s.getId() + " date: " + s.getData() + " hair color: " + s.getHairColor() + " location: " + s.location.getName() + " Х " + s.coordinates.getX();
                }else{
                    response += "\n" + "name: " + s.getName() + " id: " + s.getId() + " date: " + s.getData() + " hair color: " + s.getHairColor() + " location: " + s.location.getName() + " Х " + s.coordinates.getX();
                }

            }
        }else {
            response = "Коллекция пуста";
        }
        SystemOut.print(response);
    }

    /**
     * реализация команды info
     */
    @Override
    public void info() {
        ParameterizedType parameterizedType =(ParameterizedType)ct.GetCollection().getClass().getGenericSuperclass();
        response = "Тип коллекции: "+parameterizedType+
                " Дата иницализации: "+ct.getDateInit()+
                " Количество элементов: "+ct.GetCollection().size();
        /*System.out.println("Тип коллекции: "+parameterizedType+
                            " Дата иницализации: "+ct.getDateInit()+
                            " Количество элементов: "+ct.GetCollection().size());*/
        SystemOut.print(response);
    }

    /**
     * реализация команды update
     */
    @Override
    public void update(long id, String nameP_, Double height_, Color eyeColor_, Color hairColor_, Country nationality_, Float x_, Double y_, Float x1_, double y1_, String nameL_, int index) {
        coo=cm.create();
        coo.SetX(x_);
        coo.SetY(y_);
        np.CoordinatesReplace(coo);
        fp.CoordinatesReplace(coo);

        loc=lm.create();
        loc.SetX(x1_);
        loc.SetY(y1_);
        loc.SetName(nameL_);
        //np.LocationReplace(loc);
        fp.LocationReplace(loc);

        ct.GetCollection().get(index).setEverything(nameP_, coo, height_, eyeColor_, hairColor_, nationality_, loc);
        System.out.println("Обновлен объект с id = "+id);
        SystemOut.print(response);

        //this.show();
    }

    /**
     * реализация команды clear
     */
    @Override
    public void clear() {
        ct.GetCollection().clear();
        response = "Коллекция очищена.";
        SystemOut.print(response);
        //this.show();
    }

    /**
     * реализация команды remove_by_id
     */
    @Override
    public void remove_by_id(long id) {
        int size = ct.GetCollection().size();
        Iterator<Person> it = ct.GetCollection().iterator();
        while (it.hasNext()){
            Person p = it.next();
            if(p.getId() == id){
                it.remove();
                response = "Удален объект с айди = "+id; break;
            }
        }if (size==ct.GetCollection().size()){
            response = "Объекта с таким id нет";
        }
        SystemOut.print(response);
    }

    /**
     * реализация команды remove_head
     */
    @Override
    public void removeHead() {
        if (ct.GetCollection().size()>0) {
            response = "Name: " + ct.GetCollection().get(0).getName() +
                    " id: " + ct.GetCollection().get(0).getId() +
                    " date: " + ct.GetCollection().get(0).getData() +
                    " hair color: " + ct.GetCollection().get(0).getHairColor() +
                    " location: " + ct.GetCollection().get(0).location.getName();
            ct.GetCollection().remove(0);
        }else{
            response = "Коллекция уже пуста";
        }
        SystemOut.print(response);
    }

    /**
     * реализация команды remove_ane_by_nationality
     */
    @Override
    public void removeAnyByNationality(Country nationality) {
        int size = ct.GetCollection().size();
        Iterator<Person> it = ct.GetCollection().iterator();
        while (it.hasNext()){
            Person p = it.next();
            if(p.getNationality().equals( nationality)){
                it.remove();
                response = "Удален объект по национальности = "+nationality; break;
            }
        }if (size==ct.GetCollection().size()){
            response = "Объекта с такой национальностью нет";
        }
        SystemOut.print(response);
    }

    /**
     * реализация команды count_less_than_location
     */
    @Override
    public void countLessThanLocation(String namel) {

        loc = lm.create();
        loc.SetX(1f);
        loc.SetY(1);
        loc.SetName(namel);
        //np.LocationReplace(loc);
        fp.LocationReplace(loc);

        Stream<Person> personStream = ct.GetCollection().stream();
        response = String.valueOf(personStream.filter(person -> person.getLocation().compareTo(loc) > 0).count());
        SystemOut.print(response);
    }

    /**
     * реализация команды filter_starts_with_name
     */
    @Override
    public void filterStartsWithName(String name) {
        for (int i=0; i<ct.GetCollection().size(); ++i){
            if(ct.GetCollection().get(i).getName().startsWith(name)){
                if(i==0){
                    response = ct.GetCollection().get(i).getName();
                }else{
                    response += "\n" + ct.GetCollection().get(i).getName();
                }
            }
        }
        SystemOut.print(response);
        /*Iterator<Person> it = ct.GetCollection().iterator();
        while (it.hasNext()){
            Person p = it.next();
            if(p.getName().startsWith(name)){
               response += p.getName();
            }
        }*/
    }

    /**
     * реализация команды save
     * @throws IOException файл не найден
     */
    @Override
    public void save() throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream(file_name);
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        fileOutputStream.write("[".getBytes());
        for (int i=0; i<ct.GetCollection().size(); ++i) {
            fileOutputStream.write(gson.toJson(ct.GetCollection().get(i)).getBytes());
            if(ct.GetCollection().size()-i!=1) {
                fileOutputStream.write(",".getBytes());
            }
        }
        fileOutputStream.write("]".getBytes());
        response = "Коллекция сохранена в файл";
        fileOutputStream.close();
        SystemOut.print(response);
    }

    /**
     * реализация команды execute_script
     * @throws FileNotFoundException файл не найден
     */
    @Override
    public void executeScript(String file_name) throws FileNotFoundException {

        FileTerminal ft = new FileTerminal(file_name, new Scanner(new File(file_name)), this);




    }

    /**
     * реализация команды exit
     */
    @Override
    public void exit() {
        System.exit(0);
    }

    /**
     * реализация команды history
     */
    @Override
    public void history() {
        for (int i = 0; i < ct.getHistoryOfCommands().length; ++i){
            if(ct.getHistoryOfCommands()!=null){
                if(i == 0){
                    response = ct.getHistoryOfCommands()[i];
                }else{
                    response += "\n"+ ct.getHistoryOfCommands()[i];
                }
            }
        }
        SystemOut.print(response);
    }

    /**
     *реализация команды add_if_min
     */
    @Override
    public void addIfMin(String name_, Double height_, Color eyeColor_, Color hairColor_, Country nationality_, Float x_, Double y_, Float x1_, double y1_, String name1_) {
        coo=cm.create();
        coo.SetX(x_);
        coo.SetY(y_);
        np.CoordinatesReplace(coo);
        fp.CoordinatesReplace(coo);

        loc=lm.create();
        loc.SetX(x1_);
        loc.SetY(y1_);
        loc.SetName(name1_);
        //np.LocationReplace(loc);
        fp.LocationReplace(loc);

        per=om.create();

        per.setEverything(name_, coo, height_, eyeColor_, hairColor_, nationality_, loc);

        if (ct.GetCollection().size() != 0) {
            ct.CollectionSort();
        }

            if((ct.GetCollection().size() == 0) || (per.compareTo(ct.GetCollection().get(0))> 0)){
                //System.out.println(per.compareTo(ct.GetCollection().get(0)));
                np.PersonReplace(per);
                fp.PersonReplace(per);
                ct.add(per);
                response = "Элемент добавлен!";
            }

        SystemOut.print(response);
    }

    /**
     * реализация команды help
     */
    @Override
    public void help() {
        response = "Доступные команды: help, info, show, add, update, remove_by_id, clear, save, execute_script," +
                " exit, remove_head, add_if_min, history, remove_any_by_nationality, count_less_than_location, filter_starts_with_name";
        response += "\n" + "help: Вывести информацию по доступным командам";
        response += "\n" + "info: Вывести информацию о коллекции";
        response += "\n" + "show: Вывести все элементы коллекции";
        response += "\n" + "add: Добавить элемент в коллекцию.";
        response += "\n" + "update: Обновить данные элемента с заданным id коллекции. Синтаксис: update id";
        response += "\n" + "remove_by_id: Удалить элемент с заданным id из коллекции. Синтаксис: remove_by_id id";
        response += "\n" + "clear: Очистить коллекцию";
        response += "\n" + "save: Сохранить коллекцию в файл";
        response += "\n" + "execute_script: Выполнить скрипт, записанный в файл. Синтаксис: execute_script filename";
        response += "\n" + "exit: Завершить программу";
        response += "\n" + "remove_head: Удалить первый элемент коллекции";
        response += "\n" + "add_if_min: Добавлить элемент в коллекцию, если он меньше, чем все имеющиеся элементы коллекции.";
        response += "\n" + "history: Вывести последние 8 команд (если их было меньше 8, то выведет меньше 8)";
        response += "\n" + "remove_any_by_nationality: Удалить один элемент коллекции, с заданной национальностью. Синтаксис: remove_any_by_nationality nationality";
        response += "\n" + "count_less_than_location: Вывести количество элементов коллекции, значения поля location которых меньше заданного. Синтаксис: count_less_than_location location";
        response += "\n" + "filter_starts_with_name: Вывести элементы коллекции, имя которых начинается с заданной подстроки. Синтаксис: filter_starts_with_name string";
        response += "\n" + "Все команды, синтаксис которых не обозначен в описании команды вводятся просто вводом названия команды без каких-либо символов после них.";
        SystemOut.print(response);
    }

    @Override
    public CollectionTask getCT() {
        return ct;
    }

    /**
     * метод, добавляющий команду в историю
     * @param userCommand команда, введенная пользователем
     */
    public void addCommandToHistory(String userCommand){
        for (int i = 0; i<ct.getHistoryOfCommands().length; ++i){
            if(i!=ct.getHistoryOfCommands().length-1){
                ct.getHistoryOfCommands()[i] = ct.getHistoryOfCommands()[i+1];
            }else{
                ct.getHistoryOfCommands()[i] = userCommand;
            }
        }
    }

    @Override
    public String getResponse() {
        return response;
    }


}
