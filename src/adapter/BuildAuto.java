/*
 * Yen Le
 * 20123455
 *
 * BuildAuto.java
 * Class which connects all the interfaces of the program together. Interface methods are implemented in ProxyAuto so
 * that implementations are hidden from the external environment.
 * */

package adapter;

public class BuildAuto extends ProxyAutomotive implements CreateAuto, UpdateAuto, FixAuto, ConfigureAuto, ScaleAuto {
    //remains empty
}

