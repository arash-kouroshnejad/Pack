import java.util.*;
import java.util.function.Predicate;

public class Driver {
    public static void main(String[] args) {
        Pack<Integer> intPack = new Pack<>();
        intPack.add(4); // empty pack add (63)
        intPack.add(5);
        intPack.add(10);
        for (Integer a : intPack) {
            System.out.println(a);
        }
        intPack.add(12, 1);
        for (Integer a : intPack) {
            System.out.println(a);
        }
        for (Integer a : intPack) {
            System.out.println(a);
        }
        for (int i=0;i < 10; i ++) {
            intPack.remove(new Integer(i));
        }
        for (Integer a : intPack) {
            System.out.println(a);
        }
        for (int i = 99; i >= 0; i--) {
            intPack.add(i);
        }
        Comparator<Integer> comp = new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o1 - o2;
            }
        };
        intPack.sort(comp);

        LinkedList<Integer> list = new LinkedList<>();
        for (int i = 100; i < 200; i++) {
            list.add(i);
        }
        intPack.addAll(list);
        printAll(intPack);
        for ( int  i=0;i<100; i++) {
            intPack.add(i);
        }
        Iterator<Integer> it = intPack.iterator();
        ListIterator<Integer> lit = intPack.listIterator();
        System.out.println(lit.nextIndex());
        System.out.println(lit.previousIndex());
        System.out.println(lit.next());
        System.out.println(lit.previous());
        System.out.println(lit.next());
        System.out.println(lit.next());
        System.out.println();
        Pack<Animal> animals = new Pack<>();
        Animal animal = new Animal();
        Bird bird = new Bird();
        Dog dog = new Dog();
        Mammal mammal = new Mammal();

        animals.add(animal);
        animals.add(mammal);
        animals.add(bird);
        animals.add(dog);
        LinkedList<Animal> animals1 = new LinkedList<>();
        animals1.add(animal);
        animals1.add(mammal);
        animals1.add(bird);
        animals1.add(dog);
        animals.addAll(animals1);
        animals.remove(bird);
        animals.remove(3);
        printAll(animals);
        System.out.println(animals.get(5));
        System.out.println(animals.indexOf(bird));
        System.out.println(animals.contains(mammal));
        animals.removeIf(new Predicate<Animal>() {
            @Override
            public boolean test(Animal animal) {
                return animal instanceof Dog;
            }
        });
        // animals.add(mammal, -4);
        // animals.add(mammal, 100);
        animals.remove(bird); // tail removal
        printAll(animals);
        ListIterator<Animal> listIterator = animals.listIterator();
        listIterator.remove();
        printAll(animals);
        listIterator.next();
        listIterator.remove();
        listIterator.remove();
        listIterator.add(animal);
        listIterator.remove();
        listIterator.previous();
        listIterator.remove();
        listIterator.next();
        listIterator.next();
        listIterator.add(animal);
        listIterator.set(mammal);
        System.out.println(listIterator.previousIndex());
        printAll(animals);
        // animals.remove(100);
        // animals.remove(-1);




        Pack<Integer> ints = new Pack<>();
        for (int i =0 ;i < 100; i++)
            ints.add(i);
        for (Iterator<Integer> itr = ints.iterator(50); itr.hasNext();)
            System.out.println(itr.next());
    }
    public static <T> void printAll(Pack<T> pack) {
        for (T t : pack) {
            System.out.println(t);
        }
    }
}

class Animal {}
class  Mammal extends Animal{}
class Dog extends Mammal{}
class Bird extends Animal{}
