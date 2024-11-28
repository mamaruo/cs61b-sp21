package randomizedtest;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdRandom;
import org.junit.Assert;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by hug.
 */
public class TestBuggyAList {
    @Test
    public void testThreeAddThreeRemove(){
        AListNoResizing<Integer> perfectL = new AListNoResizing<>();
        BuggyAList<Integer> buggyL = new BuggyAList<>();

        for (int i = 0; i < 3; i++) {
            perfectL.addLast(i);
            buggyL.addLast(i);
        }

        Assert.assertEquals(perfectL.size(), buggyL.size());
        for (int i = 0; i < 3; i++) {
            Assert.assertEquals(perfectL.removeLast(), buggyL.removeLast());
        }
    }

    @Test
    public void randomizedTest(){
        AListNoResizing<Integer> L = new AListNoResizing<>();
        BuggyAList<Integer> BL = new BuggyAList<>();

        int N = 50000;
        for (int i = 0; i < N; i += 1) {
            int operationNumber = StdRandom.uniform(0, 4);
            if (operationNumber == 0) {
                // addLast
                int randVal = StdRandom.uniform(0, 100);
                L.addLast(randVal);
                BL.addLast(randVal);
//                System.out.println("addLast(" + randVal + ")");
            } else if (operationNumber == 1) {
                // size
                int size = L.size();
                int bSize = BL.size();
//                System.out.println("size: " + size);
                Assert.assertEquals(size, bSize);
            } else if (operationNumber == 2) {
                // removeLast
                if (L.size() > 0 && BL.size() > 0){
                    int removed = L.removeLast();
                    int bRemoved = BL.removeLast();
                    Assert.assertEquals(removed, bRemoved);
//                    System.out.println("removeLast(" + removed + ")");
                }
            } else if (operationNumber == 3){
                // getLast
                if (L.size() > 0 && BL.size() > 0){
                    Assert.assertEquals(L.getLast(), BL.getLast());
//                    System.out.println("getLast(" + L.getLast() + ")");
                }
            }
        }
    }
}
