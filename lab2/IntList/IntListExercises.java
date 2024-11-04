package IntList;

public class IntListExercises {

    /**
     * Part A: mutative method that adds a constant C to each
     * element of an IntList
     *
     * @param lst IntList from Lecture
     */
    public static void addConstant(IntList lst, int c) {
        IntList head = lst;
        while (head.rest != null) {
            head.first += c;
            head = head.rest;
        }
        head.first += c;
    }

    /**
     * Part B: Buggy method that sets node.first to zero if
     * the max value in the list starting at node has the same
     * first and last digit, for every node in L
     *
     * @param L IntList from Lecture
     */
    public static void setToZeroIfMaxFEL(IntList L) {
        IntList p = L;
        while (p != null) {
            int currMax = max(p);
            boolean firstEqualsLast = firstDigitEqualsLastDigit(currMax);
            if (firstEqualsLast) {
                p.first = 0;
            }
            p = p.rest;
        }
    }

    /** Returns the max value in the IntList starting at L. */
    public static int max(IntList L) {
        int max = L.first;
        IntList p = L.rest;
        while (p != null) {
            if (p.first > max) {
                max = p.first;
            }
            p = p.rest;
        }
        return max;
    }

    /** Returns true if the last digit of x is equal to
     *  the first digit of x.
     */
    public static boolean firstDigitEqualsLastDigit(int x) {
        int lastDigit = x % 10;
        while (x >= 10) {
            x = x / 10;
        }
        int firstDigit = x % 10;
        return firstDigit == lastDigit;
    }

    /**
     * Part C: (Buggy) mutative method that squares each prime
     * element of the IntList.
     *
     * @param lst IntList from Lecture
     * @return True if there was an update to the list
     */
    public static boolean squarePrimes(IntList lst) {
        // Base Case: we have reached the end of the list
        if (lst == null) {
            return false;
        }

        boolean updated = false;

        // Check if the current element is prime
        if (Primes.isPrime(lst.first)) {
            lst.first *= lst.first;
            updated = true;
        }

        // Recursively process the rest of the list
        boolean restUpdated = squarePrimes(lst.rest);

        // Return true if there was any update in the current element or the rest of the list
        return updated || restUpdated;
    }
}
