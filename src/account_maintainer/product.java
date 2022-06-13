/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package account_maintainer;

/**
 *
 * @author Welcome
 */
public class product {
    
    private String productid, productname, product_qty, unit_id;
    
    public product(String productid, String productname, String product_qty, String unit_id){
        this.productid=productid;
        this.productname=productname;
        this.product_qty=product_qty;
        this.unit_id=unit_id;
    }
    
    public String getproductid(){
        return productid;
    }
    public String getproductname(){
        return productname;
    }
    public String getproduct_qty(){
        return product_qty;
    }
    public String getunit_id(){
        return unit_id;
    }
    
}
