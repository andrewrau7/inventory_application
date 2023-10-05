package model;
/** Subclass for Instantiating InHouse Part objects. */
public class InHouse extends Part{

    private int machineId;

    /** Constructor for InHouse parts.
     Calls the Part constructor while also assigning a machine ID.
     */
    public InHouse(int id, String name, double price, int stock, int min, int max, int machineId) {
        super(id, name, price, stock, min, max);
        this.machineId = machineId;
    }


    /** Method for getting an InHouse Part's machine ID.
     @return the machineID.
     */
    public int getMachineId() {
        return machineId;
    }


    /** Method for setting an InHouse Part's machine ID.
     @param machineId the machine ID to set.
     */
    public void setMachineId(int machineId) {
        this.machineId = machineId;
    }
}
