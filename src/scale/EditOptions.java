package scale;
import adapter.ProxyAutomotive;
import model.*;

public class EditOptions extends ProxyAutomotive implements Runnable {
    private String key;
    private String opsetName;
    private String newOpsetName;
    private String optionName;
    private String newOptionName;
    private float newOptionPrice;

    //thread-related private variables
    private static int threadCount = 1;
    private Thread thread;
    public int threadNum;
    private boolean isBusy;
    private String editOp;

    public EditOptions(int threadNum) {
        thread = new Thread(this);
        this.isBusy = false;
        this.threadNum = threadNum;
        threadCount ++;
    }

    //constructor for thread editing option set name
    public EditOptions(int threadNum, String key, String opsetName, String newOpsetName) {
        thread = new Thread(this);
        this.isBusy = false;
        this.threadNum = threadNum;
        threadCount ++;
        this.key = key;
        this.opsetName = opsetName;
        this.newOpsetName = newOpsetName;
    }

    //constructor for thread editing option name
    public EditOptions(int threadNum, String key, String opsetName, String optionName, String newOptionName) {
        thread = new Thread(this);
        this.isBusy = false;
        this.threadNum = threadNum;
        threadCount ++;
        this.key = key;
        this.opsetName = opsetName;
        this.optionName = optionName;
        this.newOptionName = newOptionName;
    }

    public EditOptions(int threadNum, String key, String opsetName, String optionName, float newOptionPrice) {
        thread = new Thread(this);
        this.isBusy = false;
        this.threadNum = threadNum;
        threadCount ++;
        this.key = key;
        this.opsetName = opsetName;
        this.optionName = optionName;
        this.newOptionPrice = newOptionPrice;
    }

    public void syncUpdateOptionSetName(String key, String opsetName, String newOpsetName) {
        editOp = "syncUpdateOptionSetName";
        OptionSet optionSet = getAutoTemplate().getVehicle(key).findOpset(opsetName);
        synchronized (optionSet) {
            //if there is already an edit option running, wait
            while (isBusy) {
                try {
                    wait();
                } catch (InterruptedException ie) {
                    System.out.println("Thread " + Integer.toString(threadNum) + "was interrupted");
                }
            }
            isBusy = true;
            updateOptionSetName(key, opsetName, newOpsetName);
            isBusy = false;
            notifyAll();
        }

    }

    public void syncUpdateOptionName(String key, String opsetName, String opName, String newOpName) {
        editOp = "syncUpdateOptionName";
        Option option = getAutoTemplate().getVehicle(key).findOption(opsetName, opName);
        synchronized (option) {
            while(isBusy) {
                try {
                    wait();

                } catch (InterruptedException ie) {
                    System.out.println("Thread " + Integer.toString(threadNum) + "was interrupted");
                }
            }
            isBusy = true;
            getAutoTemplate().getVehicle(key).updateOpname(opsetName, opName, newOpName);
            isBusy = false;
            notifyAll();
        }
    }

    public void syncUpdateOptionPrice(String key, String opsetName, String opName, float newPrice) {
        editOp = "syncUpdateOptionPrice";
        Option option = getAutoTemplate().getVehicle(key).findOption(opsetName, opName);
        synchronized (option) {
            while(isBusy) {
                try {
                    wait();

                } catch (InterruptedException ie) {
                    System.out.println("Thread " + Integer.toString(threadNum) + "was interrupted");
                }
            }
            isBusy = true;
            getAutoTemplate().getVehicle(key).updateOpPrice(opsetName, opName, newPrice);
            isBusy = false;
            notifyAll();
        }
    }

    public void run() {
        switch(editOp) {
            case "syncUpdateOptionSetName":
                syncUpdateOptionSetName(key, opsetName, newOpsetName);
                break;
            case "syncUpdateOptionName":
                syncUpdateOptionName(key, opsetName, optionName, newOptionName);
                break;
            case "syncUpdateOptionPrice":
                syncUpdateOptionPrice(key, opsetName, optionName, newOptionPrice);
                break;
        }
    }

    public void start() {
        thread.start();
    }
}
