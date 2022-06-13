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
public class sell {
    
    private String SellId, mode, descriptn, Ttl_price, date_time;
    
    public sell( String SellId, String mode, String descriptn, String Ttl_price, String date_time){
        this.SellId=SellId;
        this.mode=mode;
        this.descriptn=descriptn;
        this.Ttl_price=Ttl_price;
        this.date_time=date_time;
    }
    
    public String getSellId(){
        return SellId;
    }
    public String getmode(){
        return mode;
    }
    public String getdescriptn(){
        return descriptn;
    }
    public String getTtl_price(){
        return Ttl_price;
    }
    public String getdate_time(){
        return date_time;
    }
    
}
