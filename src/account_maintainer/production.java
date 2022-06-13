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
public class production {
    
    private String productionId, rawMeti, mfgMeti, mfgdttm;
    
    public production(String productionId, String rawMeti, String mfgMeti, String mfgdttm){
        this.productionId=productionId;
        this.rawMeti=rawMeti;
        this.mfgMeti=mfgMeti;
        this.mfgdttm=mfgdttm;
    }
    
    public String getproductionId(){
        return productionId;
    }
    public String getrawMeti(){
        return rawMeti;
    }
    public String getmfgMeti(){
        return mfgMeti;
    }
    public String getmfgdttm(){
        return mfgdttm;
    }
    
}
