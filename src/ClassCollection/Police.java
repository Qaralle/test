package ClassCollection;

/**
 * Interface, реализуемый классами, проверяющими поля элементов коллекции
 * @author Maxim Antonov and Andrey Lyubkin
 * @param <T> Person
 * @param <P> Location
 * @param <V> Coordinates
 */
public interface Police<T,V> {
    void PersonReplace(T p);
    void CoordinatesReplace(V coo);
}