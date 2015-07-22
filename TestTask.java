package testTasks;
import java.util.Random;

/* Gavrilov Stanislav 
*  Тестовое задание
*  22.07.2015, 11:05
*/

public class TestTask {
    
    final int threadSize = 4;
    Thread threads[] = new Thread[threadSize];
    
    public TestTask() {
        int[] inputArr = new int[5];
        for (int i = 0; i < inputArr.length; i++) {
            inputArr[i] = new Random().nextInt(10);
        }
        int power = 2;
        print(inputArr);
        
        int[] resultArr = evaluate(inputArr, power);
        print(resultArr);
    }
    
    void print(int[] array) {
        for(int i = 0; i < array.length; i++)
            System.out.print(String.valueOf(array[i]) + " ");
        System.out.println();
    }
    
    synchronized int[] evaluate(int[] numbers, int power) {

        for (int i = 0; i < numbers.length; i += threadSize) {
            CoreThread[] coreThread = new CoreThread[threadSize];
            for (int j = 0; j < threadSize; j++) {
                if(i + j < numbers.length) {
                    coreThread[j] = new CoreThread(numbers[i + j], power);
                    threads[j] = new Thread(coreThread[j]);
                    threads[j].start();
                }
            }
            for (int j = 0; j < threadSize; j++) {
                if(i + j < numbers.length) {
                    try {
                        threads[j].join();
                        numbers[i + j] = coreThread[j].getResult();
                    } catch (Exception e) {}
                }
            }
            
        }
        
        return numbers;
    }
    
    public static void main(String[] args) {
        TestTask task = new TestTask();
    }
}


class CoreThread implements Runnable {

    private int number = 0;
    private int power = 0;
    private int result = 0;
    
    public CoreThread(int number, int power) {
        this.number = number;
        this.power = power;
    }
    
    int getResult() {
        return result;
    }
    
    public void run() {
        result = (int) Math.pow(number, power);
    }
}