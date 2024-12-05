package deque;

import javax.print.DocFlavor;
import java.util.Comparator;

public class MaxArrayDeque<T> extends ArrayDeque<T>{
    private Comparator<T> comparator;

    public MaxArrayDeque(Comparator<T> c) {
        this.comparator = c;
    }

    public T max(){
        return max(comparator);
    }

    public T max(Comparator<T> c){
        if (isEmpty()){
            return null;
        }

        T maxItem = get(0);
        for (int i = 1; i < size(); i++){
            T compareItem = get(i);
            if (c.compare(compareItem, maxItem) > 0){
                maxItem = compareItem;
            }
        }

        return maxItem;
    }

    public static void main(String[] args) {
        Comparator<Integer> comparator = Comparator.naturalOrder();
        MaxArrayDeque<Integer> AD = new MaxArrayDeque<>(comparator);

        for (int i = 0; i < 10; i++){
            AD.addLast(i);
        }

        System.out.println(AD.max());
        comparator = Comparator.reverseOrder();

        System.out.println(AD.max(comparator));

        Comparator<String> strComparator = new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o1.length() - o2.length();
            }   
        };
        MaxArrayDeque<String> MAD = new MaxArrayDeque<>(strComparator);

        MAD.addLast("a");
        MAD.addLast("ab");
        MAD.addLast("abc");
        MAD.addLast("abcd");
        MAD.addLast("a123");

        System.out.println(MAD.max());
    }
}
