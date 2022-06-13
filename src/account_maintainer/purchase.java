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
public class purchase {
    
    
    private String purchaseid, productname, uniname, quantity, perPrice, ttlPrice, pur_dttm;
    
    public purchase(String purchaseid, String productname, String uniname, String quantity, String perPrice, String ttlPrice, String pur_dttm){
        
        this.purchaseid=purchaseid;
        this.productname=productname;
        this.uniname=uniname;
        this.quantity=quantity;
        this.perPrice=perPrice;
        this.ttlPrice=ttlPrice;
        this.pur_dttm=pur_dttm;
        
    }
    
    public String getpurchaseid(){
        return purchaseid;
    }
    public String getproductname(){
        return productname;
    }
    public String getuniname(){
        return uniname;
    }
    public String getquantity(){
        return quantity;
    }
    public String getperPrice(){
        return perPrice;
    }
    public String getttlPrice(){
        return ttlPrice;
    }
    public String getpur_dttm(){
        return pur_dttm;
    }
    
    
}
