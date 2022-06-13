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
public class unit {
    
    private String unitid, unitnm, unitsymbol;
    
    public unit(String unitid, String unitnm, String unitsymbol){
        
        this.unitid = unitid;
        this.unitnm = unitnm;
        this.unitsymbol = unitsymbol;
        
    }
    
    public String getunitid(){
        return unitid;
    }
    public String getunitnm(){
        return unitnm;
    }
    public String getunitsymbol(){
        return unitsymbol;
    }
    
}
