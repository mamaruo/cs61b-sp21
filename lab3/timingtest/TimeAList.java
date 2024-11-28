package timingtest;
import edu.princeton.cs.algs4.Stopwatch;

/**
 * Created by hug.
 * Ns: 列表中元素数的列表
 * times：运行时间的列表
 * opCounts：操作次数的列表
 */
public class TimeAList {
    private static void printTimingTable(AList<Integer> Ns, AList<Double> times, AList<Integer> opCounts) {
        System.out.printf("%12s %12s %12s %12s\n", "N", "time (s)", "# ops", "microsec/op");
        System.out.printf("------------------------------------------------------------\n");
        for (int i = 0; i < Ns.size(); i += 1) {
            int N = Ns.get(i);
            double time = times.get(i);
            int opCount = opCounts.get(i);
            double timePerOp = time / opCount * 1e6;
            System.out.printf("%12d %12.2f %12d %12.2f\n", N, time, opCount, timePerOp);
        }
    }

    public static void main(String[] args) {
        timeAListConstruction();
    }

    public static void timeAListConstruction() {
        AList<Integer> Ns = new AList<>();
        AList<Double> times = new AList<>();
        AList<Integer> opCounts = new AList<>();

        int[] testSizes = {1000, 2000, 4000, 8000, 16000, 32000, 64000, 128000};

        for (int size : testSizes) {
            AList<Integer> test = new AList<>();
            Stopwatch sw = new Stopwatch();

            for (int i = 0; i < size; i++) {
                test.addLast(i);
            }

            double runTime = sw.elapsedTime();

            Ns.addLast(size);
            times.addLast(runTime);
            opCounts.addLast(size);
        }

        printTimingTable(Ns, times, opCounts);
    }
}