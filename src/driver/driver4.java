package driver;
import adapter.*;
import exception.AutoException;
import util.FileIO;
import scale.EditOptions;

public class driver4 {
    public static void main(String[] args) throws AutoException{
        try {
            String file = "C:\\Users\\Arteh\\IdeaProjects\\CIS35B_HW4\\textfiles\\ffw.txt";
            String autoName = "Ford Focus Wagon ZTW 2012";
            BuildAuto auto = new BuildAuto();
            auto.buildAuto(file);
            System.out.println("Printing Auto after building...");
            auto.printAuto(autoName);
            ScaleAuto sa1 = new BuildAuto();
            System.out.println("Started Thread 1...");
            ScaleAuto sa2 = new BuildAuto();
            System.out.println("Started Thread 2...");
            sa1.updateOptionSetName(1, autoName, "Color", "Colour");
            System.out.println("Editing Option Set name in thread 1...");
            sa2.updateOptionSetName(2, autoName, "Colour", "Color");
            System.out.println("Editing Option Set name in thread 2...");
            System.out.println("Printing Auto after editing...");
            auto.printAuto(autoName);
        } catch (AutoException ae) {

        }
    }
}
