package adapter;
import exception.AutoException;
import model.AutoTemplate;
import model.Automotive;
import util.FileIO;
import scale.*;
import java.util.Scanner;

public abstract class ProxyAutomotive {
    private static AutoTemplate at1 = new AutoTemplate();
    private static Automotive a1;

    public ProxyAutomotive() {
        a1 = new Automotive();
    }

    public AutoTemplate getAutoTemplate() {
        return at1;
    }

    //implements CreateAuto interface methods via subclass BuildAuto
    public void buildAuto(String filename) throws AutoException {
        FileIO fileIO = new FileIO();
        Automotive toBuild = fileIO.buildAutoObject(filename);
        at1.addVehicle(toBuild.getName(), toBuild); //also add a1 to the hash map
    }

    public synchronized void printAuto(String modelName) {
        at1.getVehicle(modelName).print();
    }

    //UpdateAuto interface methods via subclass BuildAuto
    public synchronized void updateOptionSetName(String key, String opsetName, String newOpsetName) {
        at1.getVehicle(key).updateOpsetName(opsetName, newOpsetName);
    }

    public synchronized void updateOptionPrice(String key, String opsetName, String opName, float newPrice) {
        at1.getVehicle(key).updateOpPrice(opsetName, opName, newPrice);
    }

    //implements FixAuto interface method
    public void fix(int errno)  {
        //implementation already included in fileIO
    }

    //implements ConfigureAuto interface methods
    public void selectChoices(String key) {
        Automotive selectAuto = at1.getVehicle(key);
        System.out.println("\nSelecting options for: " + selectAuto.getName());
        selectAuto.selectChoices();
        //update hashmap entry to contain selected options
        at1.updateVehicle(selectAuto.getName(), selectAuto);
    }

    public void printChoices(String key) {
        at1.getVehicle(key).printChoices();
    }
    public float calculatePrice(String key) {
        return at1.getVehicle(key).getTotalPrice();
    }

    //implements ScaleAuto interface methods
    public synchronized void updateOptionSetName(int threadNum, String key, String opsetName, String newOpsetName) {
        //start thread with a number
        EditOptions thread = new EditOptions(threadNum, key, opsetName, newOpsetName);
        //run
        thread.start();
    }

    public synchronized void updateOptionName(int threadNum, String key, String opsetName, String opName, String newOpName) {
        //start thread with a number
        EditOptions thread = new EditOptions(threadNum, key, opsetName, opName, newOpName);
        //run
        thread.start();
    }

    public synchronized void updateOptionPrice(int threadNum, String key, String opsetName, String opName, float newPrice) {
        //start thread with a number
        EditOptions thread = new EditOptions(threadNum, key, opsetName, opName, newPrice);
        //run
        thread.start();
    }
}
