package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/** Class for instantiating Product Objects. */
public class Product {

    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;

    private static int idCounter = 1;

    /** Constructor for new Product objects. */
    public Product(int id, String name, double price, int stock, int min, int max) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }


    /** Method for getting a Product's ID.
     @return the id.
     */
    public int getId() {
        return id;
    }


    /** Method for setting a Product's ID.
     @param id the id.
     */
    public void setId(int id) {
        this.id = id;
    }


    /** Method for getting a Product's name.
     @return the name.
     */
    public String getName() {
        return name;
    }


    /** Method for setting a Product's name.
     @param name the name.
     */
    public void setName(String name) {
        this.name = name;
    }


    /** Method for getting a Product's price.
     @return the price.
     */
    public double getPrice() {
        return price;
    }


    /** Method for setting a Product's price.
     @param price the price.
     */
    public void setPrice(double price) {
        this.price = price;
    }


    /** Method for getting a Product's stock.
     @return the stock.
     */
    public int getStock() {
        return stock;
    }


    /** Method for setting a Product's stock.
     @param stock the stock.
     */
    public void setStock(int stock) {
        this.stock = stock;
    }


    /** Method for getting a Product's minimum stock level.
     @return the min.
     */
    public int getMin() {
        return min;
    }


    /** Method for setting a Product's minimum stock level.
     @param min the min.
     */
    public void setMin(int min) {
        this.min = min;
    }


    /** Method for getting a Product's maximum stock level.
     @return the max.
     */
    public int getMax() {
        return max;
    }


    /** Method for setting a Product's maximum stock level.
     @param max the max.
     */
    public void setMax(int max) {
        this.max = max;
    }


    /** Method for adding part's to a product's associatedParts list.
     @param part the part to add.
     */
    public void addAssociatedPart(Part part){
        associatedParts.add(part);
    }


    /** Method for deleting a specific part from a product's associatedParts list.
     @param selectedAssociatedPart the part to be deleted.
     @return boolean.
     */
    public boolean deleteAssociatedPart(Part selectedAssociatedPart){
        if (associatedParts.contains(selectedAssociatedPart)){
            associatedParts.remove(selectedAssociatedPart);
            return true;
        }
        return false;
    }


    /** Method to return a product's associatedParts list.
     @return associatedParts.
     */
    public ObservableList<Part> getAllAssociatedParts(){
        return associatedParts;
    }
}
