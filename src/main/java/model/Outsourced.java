package model;

/** Subclass for Instantiating Outsourced Part objects. */
public class Outsourced extends Part {

    private String companyName;

    /** Constructor for Outsourced parts.
     Calls the Part constructor while also assigning a company name.
     */
    public Outsourced(int id, String name, double price, int stock, int min, int max, String companyName) {
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
    }

    /** Method for getting an Outsourced Part's company name.
     @return the companyName.
     */
    public String getCompanyName() {
        return companyName;
    }


    /** Method for setting an Outsourced Part's company name.
     @param companyName the companyName to set.
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}
