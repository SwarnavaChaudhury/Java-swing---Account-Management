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
public class expenditure {
    
    private String exp_id, exp_sess, exp_mnts, exp_dttm, exp_dtls;
    
    public expenditure(String exp_id, String exp_sess, String exp_mnts, String exp_dttm, String exp_dtls){
        this.exp_id=exp_id;
        this.exp_sess=exp_sess;
        this.exp_mnts=exp_mnts;
        this.exp_dttm=exp_dttm;
        this.exp_dtls=exp_dtls;
    }
    
    public String getexp_id(){
        return exp_id;
    }
    public String getexp_sess(){
        return exp_sess;
    }
    public String getexp_mnts(){
        return exp_mnts;
    }
    public String getexp_dttm(){
        return exp_dttm;
    }
    public String getexp_dtls(){
        return exp_dtls;
    }
    
}
