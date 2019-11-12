package adapter;
import exception.AutoException;
import model.AutoTemplate;
import model.Automotive;
import util.FileIO;
import java.util.Scanner;

public abstract class ProxyAutomotive {
    private static AutoTemplate at1;
    private static Automotive a1;

    public ProxyAutomotive() {
        a1 = new Automotive();
        at1 = new AutoTemplate();
    }

    //implements CreateAuto interface methods via subclass BuildAuto
    public void buildAuto(String filename) throws AutoException {
        FileIO fileIO = new FileIO();
        Automotive toBuild = fileIO.buildAutoObject(filename);
        at1.addVehicle(toBuild.getName(), toBuild); //also add a1 to the hash map
    }

    public void printAuto(String modelName) {
        at1.getVehicle(modelName).print();
    }

    //UpdateAuto interface methods via subclass BuildAuto
    public void updateOptionSetName(String key, String opsetName, String newOpsetName) {
        at1.getVehicle(key).updateOpsetName(opsetName, newOpsetName);
    }

    public void updateOptionPrice(String key, String opsetName, String opName, float newPrice) {
        at1.getVehicle(key).updateOpPrice(opsetName, opName, newPrice);
    }

    //implements FixAuto interface method
    public void fix(int errno)  {
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
}
