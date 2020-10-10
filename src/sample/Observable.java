package sample;

public interface Observable
{
    void registerObserver(Observer o);
    void notifyObservers(Object[] args);
}
