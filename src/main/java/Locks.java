import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Locks<Number>
{
    private final Lock lock = new ReentrantLock();
    private final List<Number> list = new ArrayList<Number>();
    private static int i = 0;


    public void set(Number o){
        try {
            lock.tryLock(10, TimeUnit.SECONDS);
            i++;
            list.add(o);
            System.out.println("As "+i+" spoke thread"+Thread.currentThread().getName());
            Thread.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {

            lock.unlock();
        }
    }


    /*

     public void set(Number o) {
         try {
             i++;
             list.add(o);
             System.out.println("As " + i + " spoke thread" + Thread.currentThread().getName());
             Thread.sleep(50);
         } catch (InterruptedException e) {
             e.printStackTrace();
         }
     }
*/
    public static void main(String[] args) {
        final Locks<String> lockExample = new Locks<String>();
        Runnable speakerThread = new Runnable() {
            @Override
            public void run() {
                while (i < 7) {
                    lockExample.set(String.valueOf(i));

                    try {
                        Thread.sleep(100);
                    }
                    catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        Thread t1 = new Thread(speakerThread, "Speaker1");
        Thread t2 = new Thread(speakerThread, "Speaker2");
        Thread t3 = new Thread(speakerThread, "Speaker3");
        Thread t4 = new Thread(speakerThread, "Speaker4");

        t1.start();
        t2.start();
        t3.start();
        t4.start();
    }
}