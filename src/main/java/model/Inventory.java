package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/** Class for holding a list of Parts and a list of Products. */
public class Inventory {

    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();
    private static int partIdCounter = 1;
    private static int productIdCounter = 1;


    /** Method for adding a part to the allParts list.
     @param part The part to be added.
     */
    public static void addPart(Part part){
        allParts.add(part);
        partIdCounter++;
    }


    /** Method for adding a product to the allProducts list.
     @param product The product to be added.
     */
    public static void addProduct(Product product){
        allProducts.add(product);
        productIdCounter++;
    }


    /** Method for specifying a specific part within the allParts list using ID number.
     @param partId The unique identifier for the part
     @return part The specified part.
     */
    public static Part lookUpPart(int partId){
        for(Part part : allParts){
            if (part.getId() == partId){
                return part;
            }
        }
        return null;
    }


    /** Method for specifying a specific product within the allProducts list using ID number.
     @param productId The unique identifier for the part
     @return product The specified product.
     */
    public static Product lookUpProduct(int productId){
        for(Product product : allProducts){
            if (product.getId() == productId){
                return product;
            }
        }
        return null;
    }


    /** Method for specifying a specific part within the allParts list using the part name.
     @param partName The name for the part.
     @return part The specified part.
     */
    public static ObservableList<Part> lookUpPart(String partName){
        ObservableList<Part> results = FXCollections.observableArrayList();
        for(Part part : allParts ){
            if (part.getName().contains(partName)){
                results.add(part);
            }
        }
        return results;
    }


    /** Method for specifying a specific product within the allProducts list using the product name.
     @param productName The name for the product.
     @return product The specified product.
     */
    public static ObservableList<Product> lookUpProduct(String productName){
        ObservableList<Product> results = FXCollections.observableArrayList();
        for(Product product : allProducts ){
            if (product.getName().contains(productName)){
                results.add(product);
            }
        }
        return results;
    }


    /** Method for updating a part within the allParts list.
     @param index The specific location of the part.
     @param selectedPart The new part.
     */
    public static void updatePart(int index, Part selectedPart){
        allParts.set(index, selectedPart);
    }


    /** Method for updating a product within the allProducts list.
     @param index The specific location of the product.
     @param selectedProduct The new part.
     */
    public static void updateProduct(int index, Product selectedProduct){
        allProducts.set(index, selectedProduct);
    }


    /** Method for deleting a part within the allParts list.
     @param selectedPart The specific part to be deleted.
     @return boolean depending on if Part exists.
     */
    public static boolean deletePart(Part selectedPart){
        if (allParts.contains(selectedPart)){
            allParts.remove(selectedPart);
            return true;
        }
        return false;
    }


    /** Method for deleting a product within the allProducts list.
     @param selectedProduct The specific part to be deleted.
     @return boolean depending on if Product exists.
     */
    public static boolean deleteProduct(Product selectedProduct){
        if (allProducts.contains(selectedProduct)){
            allProducts.remove(selectedProduct);
            return true;
        }
        return false;
    }


    /** Method for getting the allParts list.
     @return allParts.
     */
    public static ObservableList<Part> getAllParts(){
        return allParts;
    }


    /** Method for getting the allProducts list.
     @return allProducts.
     */
    public static ObservableList<Product> getAllProducts(){
        return allProducts;
    }


    /** Method for getting the unique variable partIdCounter.
     @return partIdCounter.
     */
    public static int getPartIdCounter() {
        return partIdCounter;
    }


    /** Method for getting the unique variable productIdCounter.
     @return productIdCounter.
     */
    public static int getProductIdCounter() {
        return productIdCounter;
    }


    /** Method for de-incrementing the partIdCounter variable.
     Used in part modify situations. Keeps unique ID generation for new parts consistent.
     */
    public static void retainPartIdCounter(){
        partIdCounter--;
    }

}
