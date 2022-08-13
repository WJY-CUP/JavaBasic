package JUC;

import java.util.OptionalLong;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;
import java.util.stream.LongStream;

/**
 * @Author: Wan Jiangyuan
 * @Description:
 * @Date: Created in 9:58 2021/7/31
 * @E-mail: 15611562852@163.com
 */

public class ForkJoinTest extends RecursiveTask<Long> {

    // ------------------ADJUST_VALUE的取值决定着并行速度------------------
    // 1,10并行比串行慢，100之后并行比串行快
    private static final Integer ADJUST_VALUE = 100;
    private long begin;
    private long end;
    private long result;

    public ForkJoinTest(long begin, long end) {
        this.begin = begin;
        this.end = end;
    }

    @Override
    protected Long compute() {
        if ((end - begin) <= ADJUST_VALUE) {
            for (long i = begin; i <= end; i++) {
                result = result + i;
            }
        } else {
            long mid = (end - begin) / 2 + begin;
            ForkJoinTest task1 = new ForkJoinTest(begin, mid);
            ForkJoinTest task2 = new ForkJoinTest(mid+1, end);
            // task1.fork();
            // task2.fork();
            invokeAll(task1,task2);
            result = task1.join() + task2.join();
        }
        // System.out.println(result);
        return result;
    }


    public static void main(String[] args) {

        long max = 10_0000_0000;

        // 1.parallel accumulate
        ForkJoinTest test = new ForkJoinTest(0, max);
        ForkJoinPool threadPool = new ForkJoinPool();
        ForkJoinTask<Long> forkJoinTask = threadPool.submit(test);
        long parallelSum = 0L;
        long start1 = System.currentTimeMillis();
        try {
            parallelSum = forkJoinTask.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        long end1 = System.currentTimeMillis();

        System.out.println("parallel sum = " + parallelSum);
        System.out.println("parallel cost: " + (end1 - start1));
        threadPool.shutdown();

        // 2.Stream parallel accumulate
        long start3 = System.currentTimeMillis();
        // rangeClosed 闭区间
        OptionalLong streamParallelSum = LongStream.rangeClosed(0L, max).parallel().reduce(Long::sum);
        long end3 = System.currentTimeMillis();
        System.out.println("stream parallel sum = " + streamParallelSum);
        System.out.println("stream parallel cost: " + (end3 - start3));


        // 3.serial accumulate
        Long serialSum = 0L;
        long start2 = System.currentTimeMillis();
        for (int i = 0; i <= max; i++) {
            serialSum += i;
        }
        long end2 = System.currentTimeMillis();

        System.out.println("serial sum = " + serialSum);
        System.out.println("serial cost: " + (end2 - start2));


    }

}
