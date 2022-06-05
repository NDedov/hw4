package hw4;

import hw4.Main;

public interface Iterator {

    void reset(); //- перемещение в начало списка
    void next(); //-  перемещение итератора к следующему элементу
    Main.Dog getCurrent(); //- получение элемента на который указывает итератор
    boolean atEnd(); //- возвращает true если итератор находится в конце списка
    void insertAfter(Main.Dog dog); //- вставка элемента после итератора
    void insertBefore(Main.Dog dog); //- вставка элемента до итератора
    Main.Dog deleteCurrent(); //- удаление элемента в текущей позиции итератора
}
