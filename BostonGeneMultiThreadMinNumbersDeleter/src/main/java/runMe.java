import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class runMe {
    public static void main(String... args){
        try {
            DataValues store = new DataValues();
            GetThread getter = new GetThread(store);
            PutThread putter = new PutThread(store);
            while(putter.currThread.getState() == Thread.State.RUNNABLE){
               //waiting...
            }
            putter.currThread.join();
            getter.endInput();
            getter.currThread.join();

        } catch (InterruptedException e) {
            System.out.println("Main Thread Interrupted!");
            e.printStackTrace();
        }
    }
}

///////////////////////////////////////////////////////////////////////////////////////////////////////////
class DataValues{
    final Map<String,Integer> digits = new HashMap<String, Integer>() {
            {
                put("zero",0);
                put("one",1);
                put("two",2);
                put("three",3);
                put("four",4);
                put("five",5);
                put("six",6);
                put("seven",7);
                put("eight",8);
                put("nine",9);
                put("ten",10);
                put("eleven",11);
                put("twelve",12);
                put("thirteen",13);
                put("fourteen",14);
                put("fifteen",15);
                put("sixteen",16);
                put("seventeen",17);
                put("eighteen",18);
                put("nineteen",19);
                put("twenty",20);
                put("thirty",30);
                put("forty",40);
                put("fifty",50);
                put("sixty",60);
                put("seventy",70);
                put("eighty",80);
                put("ninety",90);
                put("hundred",100);
                put("thousand",1000);
            }
    };
    orderedDoubleLinkedList data = new orderedDoubleLinkedList();
    int numValues = 0;

    void put(String number){
        synchronized (this){
            data.insert(numberToInteger(number),number);
            numValues++;
        }
    }

    void getMin(){
        synchronized (this){
            Link tmp;
            if(numValues == 0){
                //System.out.println("There is nothing to delete");
                return;
            }
            tmp = data.removeMinimum();
            numValues--;
            System.out.println("Было удалено : "+ tmp.getStrValue());
        }
    }

    Integer numberToInteger(String number){
        Integer value = 0;
        int prevValue = 0;
        Pattern worldPattern = Pattern.compile("\\s");
        String[] numbers = worldPattern.split(number);
        for (String s : numbers) {
            System.out.println(s);
            if(s.equals("thousand") || s.equals("hundred")){
                value -= prevValue;
                value += (prevValue * digits.get(s));
            }else{
                value += digits.get(s);
                prevValue = digits.get(s);
            }
        }
        return value;
    }
}

///////////////////////////////////////////////////////////////////////////////////////////////////////////
class GetThread implements  Runnable{
    Thread currThread;
    DataValues store;
    private boolean isInputOver = false;

    GetThread(DataValues store){
        this.store = store;
        currThread = new Thread(this,"Putter");
        currThread.start();
    }

    public void run(){
        try {
            while(!isInputOver) {
                currThread.sleep(5000);
                store.getMin();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    synchronized void endInput(){
        isInputOver = true;
    }
}

///////////////////////////////////////////////////////////////////////////////////////////////////////////
class PutThread implements  Runnable{
    Thread currThread;
    DataValues store;

    PutThread(DataValues store){
        this.store = store;
        currThread = new Thread(this,"Getter");
        currThread.start();
    }
    public void run() {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            String currNumber = "";
            while(true){
                currNumber = reader.readLine();
                if(currNumber.equals("exit")) break;
                store.put(currNumber);
            }
        }catch (IOException exc){
            System.out.println("input error");
            exc.printStackTrace();
        }
    }
}

