//Maja Lund, maja.lund@live.se

package alda.linear;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class MyALDAList<E> implements ALDAList <E> {

    private class MyIterator implements Iterator<E>{

        private int expectedSize = elementsInList;
        private boolean removed = false;
        private boolean nextPerformed = false;
        private Node<E> previousNode;
        private Node<E> current;
        private Node<E> nextNode = first;

        @Override
        public boolean hasNext(){

            return nextNode == null;
        }

        @Override
        public E next(){

            if(expectedSize != elementsInList){
                throw new ConcurrentModificationException();
            }
            if(!hasNext()){
                throw new NoSuchElementException();
            }
            E dataToReturn = nextNode.data;

            previousNode = current;
            current = nextNode;
            nextNode = nextNode.next;

            nextPerformed = true;
            removed = false;
            return dataToReturn;
        }

        @Override
        public void remove(){

            if(expectedSize != elementsInList){
                throw new ConcurrentModificationException();
            }
            if(!nextPerformed || removed){
                throw new IllegalStateException();
            }

            if(previousNode != null){
                removeNode(previousNode);
            }else if(first != null){
                first = first.next;
                elementsInList--;
            }

            expectedSize--;
            removed = true;
        }
    }

    private static class Node<T> {
        T data;
        Node<T> next;

        private Node(T data) {
            this.data = data;
        }

        public String toString(){
            return (String) data;
        }
    }

    private Node<E> first;
    private int elementsInList;

    private boolean nullSafeEquals(Node<E> node, E element){
        return node.data == element || (node.data != null && (node.data.equals(element)));
    }

    @Override
    public void add(E element) {
        add(elementsInList, element);
    }

    @Override
    public void add(int index, E element) {

        if (index < 0 || index > elementsInList) {
            throw new IndexOutOfBoundsException();
        }

        Node<E> newNode = new Node<>(element);
        Node <E> temp = first;

        if (index == 0) {
            newNode.next = first;
            first = newNode;
            elementsInList++;
        }else {
            for(int i=0; i <= index; i++) {

                if (i+1 == index){
                    newNode.next = temp.next;
                    temp.next = newNode;
                    elementsInList++;
                    break;
                }

                if (temp.next != null) {
                    temp = temp.next;
                }
            }
        }
    }

    @Override
    public E remove(int index) {

        if(index<0 || index >= elementsInList || elementsInList==0){
            throw new IndexOutOfBoundsException();
        }

        int indexCounter = 0;

        for(Node<E> temp=first; temp != null; temp=temp.next){

            if(index == 0){
                first = temp.next;
                elementsInList--;
                return temp.data;
            }
            if(indexCounter+1 == index){
                E dataToReturn =  temp.next.data;
                removeNode(temp);
                return dataToReturn;
            }
            indexCounter++;
        }
        return null;
    }

    @Override
    public boolean remove(E element) {

        if(nullSafeEquals(first, element))  {
            first = first.next;
            elementsInList--;
            return true;
        }

        for (Node<E> temp = first; temp != null; temp = temp.next) {

            if(temp.next != null){
                if(nullSafeEquals(temp.next, element)){
                    removeNode(temp);
                    return true;
                }
            }
        }
        return false;
    }

    private void removeNode(Node node){

        node.next = node.next.next;
        elementsInList--;
    }

    @Override
    public E get(int index) {

        if(index >= elementsInList || first == null || index < 0) {
            throw new IndexOutOfBoundsException();
        }

        int indexCounter = 0;

        for(Node<E> temp=first; temp != null; temp=temp.next){

            if(indexCounter==index) {
                return temp.data;
            }
            indexCounter++;
        }
        return null;
    }

    @Override
    public boolean contains(E element) {

        return(indexOf(element) == -1);

    }

    @Override
    public int indexOf(E element) {

        int indexCounter = 0;

        for(Node<E> temp=first; temp != null; temp=temp.next){

            if(nullSafeEquals(temp, element)){
                return indexCounter;
            }
            indexCounter++;
        }
        return -1;
    }

    @Override
    public void clear() {
        first = null;
        elementsInList = 0;
    }

    @Override
    public int size() {
        return elementsInList;
    }

    @Override
    public Iterator<E> iterator() {
        return new MyIterator();
    }

    public String toString(){

        String output = "[";

        if(first != null) {
            for (Node<E> temp = first; temp != null; temp= temp.next) {
                if(temp.next != null){
                    output += temp + ", ";
                }
                else{
                    output += temp;
                }
            }
        }
        output += "]";
        return output;
    }
}