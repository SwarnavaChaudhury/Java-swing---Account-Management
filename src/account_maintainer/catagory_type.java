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
public class catagory_type {
    
    private String sess, mnths, dttm, Expenditure_type, price;
    
    public catagory_type(String sess, String mnths, String dttm, String Expenditure_type, String price){
        
        this.sess=sess;
        this.mnths=mnths;
        this.dttm=dttm;
        this.Expenditure_type=Expenditure_type;
        this.price=price;
        
    }
    
    public String getsess(){
        return sess;
    }
    public String getmnths(){
        return mnths;
    }
    public String getdttm(){
        return dttm;
    }
    public String getExpenditure_type(){
        return Expenditure_type;
    }
    public String getprice(){
        return price;
    }
    
    
}
