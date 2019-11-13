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
        editOp = "syncUpdateOptionSetName";
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
        editOp = "syncUpdateOptionName";
        thread = new Thread(this);
        this.isBusy = false;
        this.threadNum = threadNum;
        threadCount ++;
        this.key = key;
        this.opsetName = opsetName;
        this.optionName = optionName;
        this.newOptionName = newOptionName;
    }

    //constructor for thread editing option price
    public EditOptions(int threadNum, String key, String opsetName, String optionName, float newOptionPrice) {
        editOp = "syncUpdateOptionPrice";
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
        synchronized (getAutoTemplate().getVehicle(key)) {
            while (isBusy) {
                try {
                    getAutoTemplate().getVehicle(key).wait();
                } catch (InterruptedException ie) {
                    System.out.println("Thread " + Integer.toString(threadNum) + "was interrupted");
                }
            }
            isBusy = true;
            updateOptionSetName(key, opsetName, newOpsetName);
            isBusy = false;
            getAutoTemplate().getVehicle(key).notifyAll();
        }

    }

    public void syncUpdateOptionName(String key, String opsetName, String opName, String newOpName) {
        Option option = getAutoTemplate().getVehicle(key).findOption(opsetName, opName);
        synchronized (getAutoTemplate().getVehicle(key)) {
            while(isBusy) {
                try {
                    getAutoTemplate().getVehicle(key).wait();

                } catch (InterruptedException ie) {
                    System.out.println("Thread " + Integer.toString(threadNum) + "was interrupted");
                }
            }
            isBusy = true;
            getAutoTemplate().getVehicle(key).updateOpname(opsetName, opName, newOpName);
            isBusy = false;
            getAutoTemplate().getVehicle(key).notifyAll();
        }
    }

    public void syncUpdateOptionPrice(String key, String opsetName, String opName, float newPrice) {
        synchronized (getAutoTemplate().getVehicle(key)) {
            while(isBusy) {
                try {
                    getAutoTemplate().getVehicle(key).wait();

                } catch (InterruptedException ie) {
                    System.out.println("Thread " + Integer.toString(threadNum) + "was interrupted");
                }
            }
            isBusy = true;
            getAutoTemplate().getVehicle(key).updateOpPrice(opsetName, opName, newPrice);
            isBusy = false;
            getAutoTemplate().getVehicle(key).notifyAll();
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
