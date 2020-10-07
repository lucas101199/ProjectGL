package Operative;
import CC.CommandControl;


public interface InterfaceMaterielle {
    void Up();
    void Down();
    void emergencyStop();
    void stopNextFloor();
    void setCommandControl(CommandControl cc);
}
