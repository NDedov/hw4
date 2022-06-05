package hw4;

import java.util.Objects;
import java.util.Spliterator;
import java.util.function.Consumer;

public class Main {


    /**
     * Класс Собак, для хранения и обработки в Списках и очереди
     */
    static class Dog{
        private String name;
        private Float weight;

        @Override
        public String toString() {
            return String.format("%s - %.1f kg", name, weight);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Dog dog = (Dog) o;
            return Objects.equals(name, dog.name) && Objects.equals(weight, dog.weight);
        }

        @Override
        public int hashCode() {
            return Objects.hash(name, weight);
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setWeight(Float weight) {
            this.weight = weight;
        }

        public String getName() {
            return name;
        }

        public Float getWeight() {
            return weight;
        }

        public Dog(String name, Float weight) {
            this.name = name;
            this.weight = weight;
        }
    }

    /**
     * Двусвязный список
     */
    static class DoubleLinkedList implements Iterator {

        private Node head;//голова списка
        private Node tail; // хвост списка

        private Node currentNode; // указатель итератора

        /**
         * Узел для хранения в списке
         */
        private class Node{
            Dog dog; // данные - собачка
            Node next;// ссылка на следующий узел
            Node prev;// ссылка на предыдущий узел

            public Node(Dog dog) {
                this.dog = dog;
            }

            @Override
            public String toString() {
                return dog.toString();
            }
        }

        @Override
        public void reset() {
            this.currentNode = head;
        }

        @Override
        public void next() {
            if (currentNode != tail)
                currentNode = currentNode.next;
        }

        @Override
        public Dog getCurrent() {
            return currentNode.dog;
        }

        @Override
        public boolean atEnd() {
            return currentNode.equals(tail);

        }

        /**
         * Вставляем элемент после текущего значения, если список пусток, то вставляем в голову, если один элемент,
         * вставляем в хвост
         * @param dog текущий элемент
         */
        @Override
        public void insertAfter(Dog dog) {
            Node newNode = new Node(dog);
            if (head == null){
                pushHead(dog);
                return;
            }
            if (head.equals(tail)){
                pushTail(dog);
                return;
            }
            if (currentNode.next == null){
                pushTail(dog);
                return;
            }
            Node tmpNext = currentNode.next;
            tmpNext.prev = newNode;
            newNode.next = tmpNext;
            newNode.prev = currentNode;
            currentNode.next = newNode;
        }

        /**
         * Вставляем элемент до текущего значения, если список пусток, то вставляем в голову, если один элемент,
         * вставляем в голову
         * @param dog текущий элемент
         */

        @Override
        public void insertBefore(Dog dog) {
            Node newNode = new Node(dog);
            if (head == null){
                pushHead(dog);
                return;
            }
            if (head.equals(tail)){
                pushHead(dog);
                return;
            }
            if (currentNode.prev == null){
                pushHead(dog);
                return;
            }
            Node tmpPrev = currentNode.prev;
            tmpPrev.next = newNode;
            newNode.prev = tmpPrev;
            newNode.next = currentNode;
            currentNode.prev = newNode;

        }

        /**
         * Удаляем текущий элемент, путем переназначения ссылок от предыдущего и следующего элементов
         * проверяем на крайние случаи начало/конец, используем штатные методы вставки в таких случаях
         * @return текущий элемент
         */
        @Override
        public Dog deleteCurrent() {
            if (isEmpty())
                return null;

            Dog tmpDog = currentNode.dog;

            if (currentNode.prev != null && currentNode.next != null){//если в середине
                currentNode.prev.next = currentNode.next;
                currentNode.next.prev = currentNode.prev;
                currentNode = currentNode.prev;
            }else if (currentNode.prev == null){//если в голове
                popHead();
                currentNode = head;
            }
            else{//если в хвосте
                popTail();
                currentNode = tail;
            }
            return tmpDog;
        }

        public DoubleLinkedList() {
            head = null;
            tail = null;
        }

        public boolean isEmpty(){
            return head == null;
        }

        /**
         * Метод вставки элемент в голову списка.
         * @param dog элемент
         */
        public void pushHead(Dog dog){
            Node newNode = new Node(dog);
            newNode.next = head;
            newNode.prev = null;
            if (head != null)
                head.prev = newNode;
            else
                tail = newNode;
            head = newNode;
        }

        /**
         * Метод вставки элемент в хвост списка
         * @param dog
         */
        public void pushTail(Dog dog){
            Node newNode = new Node(dog);
            newNode.prev = tail;
            newNode.next = null;
            if (tail != null)
                tail.next = newNode;
            else
                head = newNode;
            tail = newNode;
        }

        /**
         * Метод снятия элемента с головы списка.
         * @return элемент
         */
        public Dog popHead(){
            Dog tmpDog = head.dog;
            if (isEmpty()) return null;

            if (head.next != null){
                head = head.next;
                head.prev = null;
            }
            else{
                head = null;
                tail = null;
            }
            return tmpDog;
        }

        /**
         * Метод снятия элемента с хвоста списка.
         * @return элемент
         */
        public Dog popTail(){
            Dog tmpDog = tail.dog;
            if (isEmpty()) return null;

            if (tail.prev != null){
                tail = tail.prev;
                tail.next = null;
            }
            else{
                head = null;
                tail = null;
            }
            return tmpDog;
        }

        public Dog peekHead(){
            if (isEmpty()) return null;
            return head.dog;
        }

        @Override
        public String toString() {
            Node current = head;
            StringBuilder sb = new StringBuilder("[ ");
            while (current != null){
                sb.append(current.toString());
                current = current.next;
                sb.append((current == null) ? " ]" : "; ");
            }
            return sb.toString();
        }
    }

    /**
     * Класс Очередь, унаследованный от двусвязного списка
     */
    public static class Queue extends DoubleLinkedList{

        public Queue() {
        }

        public void push(Dog dog){
            super.pushTail(dog);
        }

        public Dog pop(){
            return super.popHead();
        }

    }

    public static void main(String[] args) {

        DoubleLinkedList doubleLinkedList = new DoubleLinkedList();
        doubleLinkedList.pushHead(new Dog ("Dog01", 1.5f));
        doubleLinkedList.pushHead(new Dog ("Dog02", 2.5f));
        doubleLinkedList.pushHead(new Dog ("Dog03", 3.5f));
        doubleLinkedList.pushHead(new Dog ("Dog04", 4.5f));

        doubleLinkedList.pushTail(new Dog ("Dog05", 1.5f));
        doubleLinkedList.pushTail(new Dog ("Dog06", 2.5f));
        doubleLinkedList.pushTail(new Dog ("Dog07", 3.5f));
        doubleLinkedList.pushTail(new Dog ("Dog08", 4.5f));

        System.out.println(doubleLinkedList);
        System.out.println(doubleLinkedList.popTail());
        System.out.println(doubleLinkedList.popTail());
        System.out.println(doubleLinkedList);

        System.out.println(doubleLinkedList.popHead());
        System.out.println(doubleLinkedList.popHead());
        System.out.println(doubleLinkedList);

        // Работа с "итератором"
        doubleLinkedList.reset();
        System.out.println(doubleLinkedList.getCurrent());

        doubleLinkedList.insertBefore(new Dog ("DogIterator01", 1.5f));
        System.out.println(doubleLinkedList);

        doubleLinkedList.next();
        System.out.println(doubleLinkedList.getCurrent());

        doubleLinkedList.insertAfter(new Dog ("DogIterator02", 1.5f));
        System.out.println(doubleLinkedList);

        doubleLinkedList.insertBefore(new Dog ("DogIterator03", 1.5f));
        System.out.println(doubleLinkedList);
        doubleLinkedList.next();
        doubleLinkedList.next();
        doubleLinkedList.next();
        doubleLinkedList.next();
        doubleLinkedList.insertAfter(new Dog ("DogIterator04", 1.5f));
        System.out.println(doubleLinkedList);
        doubleLinkedList.next();
        doubleLinkedList.deleteCurrent();
        System.out.println(doubleLinkedList);
        doubleLinkedList.reset();
        doubleLinkedList.deleteCurrent();
        System.out.println(doubleLinkedList);
        doubleLinkedList.next();
        doubleLinkedList.next();
        doubleLinkedList.deleteCurrent();
        System.out.println(doubleLinkedList);

        Queue q = new Queue();
        q.push(new Dog ("DogQ01", 1.5f));
        q.push(new Dog ("DogQ02", 1.5f));
        q.push(new Dog ("DogQ03", 1.5f));
        System.out.println(q);
        System.out.println(q.pop());


    }
}
