import java.util.Iterator;

import org.junit.Test;

public class RandomizedQueueTest {

    @Test
    public void addRemove() {
        RandomizedQueue<String> val = new RandomizedQueue<String>();
        val.enqueue("Test");
        val.enqueue("anotherTEst");
        val.enqueue("anotherTEst1");
        val.enqueue("anotherTEst2");
        val.enqueue("anotherTEst3");
        val.enqueue("anotherTEst4");
        val.enqueue("anotherTEst5");
        val.enqueue("anotherTEst6");
        val.enqueue("anotherTEst7");
        val.enqueue("anotherTEst8");
        val.enqueue("anotherTEst9");
        val.enqueue("anotherTEst10");
        val.enqueue("anotherTEst11");
        val.enqueue("anotherTEst12");
        val.enqueue("anotherTEst13");
        val.enqueue("anotherTEst14");
        val.enqueue("anotherTEst15");

        Iterator<String> i1 = val.iterator();
        Iterator<String> i2 = val.iterator();
        while (i1.hasNext() && i2.hasNext()) {
            System.out.println("i1 value: " + i1.next());
            System.out.println("i2 value: " + i2.next());
        }
    }
}
