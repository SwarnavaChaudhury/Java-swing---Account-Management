/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package account_maintainer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Welcome
 */
public class insert_VAls extends javax.swing.JFrame {

    /**
     * Creates new form insert_VAls
     */
    public insert_VAls() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(0, 0, 204));
        jPanel1.setForeground(new java.awt.Color(255, 255, 255));

        jButton1.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jButton1.setText("START OPERATION");
        jButton1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(56, 56, 56)
                .addComponent(jButton1)
                .addContainerGap(61, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(103, 103, 103)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(111, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        
        
        
        
        ////////////////////////////////////////////////////////////////////////
        ////////////////////////////////////////////////////////////////////////
        Connection con;
        try{
            ///////////////////////////////
            ///////////////////////////////
            ///////////////////////////////
            try {
                Class.forName("oracle.jdbc.driver.OracleDriver");
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(Register_Frm.class.getName()).log(Level.SEVERE, null, ex);
            }
            con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "ACCOUNTMAINTAINER", "SWARNAVA");
            ///////////////////////////////
            ///////////////////////////////
            ///////////////////////////////

            
            
            
            
            PreparedStatement sq = con.prepareStatement("CREATE TABLE USERS(" +
                                                        "REGISTERID VARCHAR2(200) PRIMARY KEY," +
                                                        "PROP_NAME VARCHAR2(300)," +
                                                        "PROP_PHNO VARCHAR2(50)," +
                                                        "PROP_EMAIL VARCHAR2(500)," +
                                                        "SHOPNAME VARCHAR2(500)," +
                                                        "SHOP_ADDR VARCHAR2(1000)," +
                                                        "PROP_GST VARCHAR2(100)," +
                                                        "PROP_PASS VARCHAR2(100)," +
                                                        "LICENSE_START VARCHAR2(100)," +
                                                        "LICENSE_END VARCHAR2(100)," +
                                                        "IP_SYSTEM VARCHAR2(200)" +
                                                        ")");
            sq.executeQuery();
            
            PreparedStatement sq1 = con.prepareStatement("CREATE TABLE UNIT_TBL(" +
                                                        "UNIT_IDNO VARCHAR2(100) PRIMARY KEY," +
                                                        "UNIT_NAME VARCHAR2(200)," +
                                                        "UNIT_SYMBOL VARCHAR2(50)" +
                                                        ")");
            sq1.executeQuery();
            
            PreparedStatement sq2 = con.prepareStatement("CREATE TABLE PRODUCT_TBL(" +
                                                        "PROIDNO VARCHAR2(100) PRIMARY KEY," +
                                                        "PRODUCT_NM VARCHAR2(200)," +
                                                        "PRODUCT_QTY VARCHAR2(1000)," +
                                                        "UNIT_IDNO VARCHAR2(100)" +
                                                        ")");
            sq2.executeQuery();
            
            PreparedStatement sq3 = con.prepareStatement("CREATE TABLE PURCHASE_TBL(" +
                                                        "PURIDNO VARCHAR2(100) PRIMARY KEY," +
                                                        "PROIDNO VARCHAR2(100)," +
                                                        "PRODUCT_PRICE VARCHAR2(1000)," +
                                                        "TTL_PRICE VARCHAR2(1000)," +
                                                        "PUR_SESSION VARCHAR2(100)," +
                                                        "PUR_MONTH VARCHAR2(100)," +
                                                        "PUR_DTTM VARCHAR2(100)," +
                                                        "PUR_QTY VARCHAR2(500)" +
                                                        ")");
            sq3.executeQuery();
            
            PreparedStatement sq4 = con.prepareStatement("CREATE TABLE PRODUCTION_SOURCE_TBL(" +
                                                        "PRODCTN_IDNO VARCHAR2(200)," +
                                                        "PROIDNO VARCHAR2(100)," +
                                                        "USED_QTY VARCHAR2(100)" +
                                                        ")");
            sq4.executeQuery();
            
            PreparedStatement sq5 = con.prepareStatement("CREATE TABLE PRODUCTION_DESTINATION_TBL(" +
                                                        "PRODCTN_IDNO VARCHAR2(200) PRIMARY KEY," +
                                                        "PROIDNO VARCHAR2(100)," +
                                                        "USED_QTY VARCHAR2(100)," +
                                                        "DATE_TIME_PRDCTN VARCHAR2(100)" +
                                                        ")");
            sq5.executeQuery();
            
            PreparedStatement sq6 = con.prepareStatement("CREATE TABLE SELL_TBL(" +
                                                        "SELL_ID VARCHAR2(100) PRIMARY KEY," +
                                                        "SELL_MODE VARCHAR2(100)," +
                                                        "SELL_ALTTL_PRICE VARCHAR2(200)," +
                                                        "SELL_SESSION VARCHAR2(100)," +
                                                        "SELL_MONTH VARCHAR2(100)," +
                                                        "SELL_DTTM VARCHAR2(100)" +
                                                        ")");
            sq6.executeQuery();
            
            PreparedStatement sq7 = con.prepareStatement("CREATE TABLE SELL_QTY_TBL(" +
                                                        "SELL_ID VARCHAR2(100)," +
                                                        "PROIDNO VARCHAR2(100)," +
                                                        "PRODUCT_QTY_SELL VARCHAR2(1000)," +
                                                        "PRODUCT_SELLING_PRICE VARCHAR2(1000)," +
                                                        "SELLING_TTL_PRICE VARCHAR2(1000)" +
                                                        ")");
            sq7.executeQuery();
            
            PreparedStatement sq8 = con.prepareStatement("CREATE TABLE EXPENDITURE(" +
                                                        "EXPENDITURE_TYPE VARCHAR2(200)" +
                                                        ")");
            sq8.executeQuery();
            
            PreparedStatement sq9 = con.prepareStatement("CREATE TABLE OTHER_EXPENSES_TBL(" +
                                                        "EXPID VARCHAR2(100) PRIMARY KEY," +
                                                        "EXP_SESSION VARCHAR2(100)," +
                                                        "EXP_MONTH VARCHAR2(100)," +
                                                        "EXP_ADD_DTTM VARCHAR2(100)" +
                                                        ")");
            sq9.executeQuery();
            
            PreparedStatement sq10 = con.prepareStatement("CREATE TABLE OTHER_EXPENSES_TBL_DETAILS(" +
                                                        "EXPID VARCHAR2(100)," +
                                                        "EXPENDITURE_TYPE VARCHAR2(1000)," +
                                                        "EXPENDITURE_PRICE VARCHAR2(1000)" +
                                                        ")");
            sq10.executeQuery();
            
            PreparedStatement sq11 = con.prepareStatement("CREATE TABLE OTHER_NECEARY_TABLE(" +
                                                        "DATABS_LINK_STORE VARCHAR2(4000)," +
                                                        "STORE_TABLE_DATA_PATH VARCHAR2(4000)," +
                                                        "ROWIDD VARCHAR2(100)" +
                                                        ")");
            sq11.executeQuery();
            
            PreparedStatement sq12 = con.prepareStatement("INSERT INTO OTHER_NECEARY_TABLE(ROWIDD) VALUES(?)");
            sq12.setString(1, "r1");
            int io = sq12.executeUpdate();
            
            
            
            
            
            
            
            
            
            
            
            PreparedStatement st = con.prepareStatement("CREATE TABLE ISSUE_TBL(" +
                                                        "KEYID VARCHAR2(1000) PRIMARY KEY," +
                                                        "DURATN VARCHAR2(200)" +
                                                        ")");
            st.executeUpdate();
            
            PreparedStatement st1 = con.prepareStatement("CREATE TABLE USERD_KEY(" +
                                                        "DATETODAY VARCHAR2(100)," +
                                                        "KEYID VARCHAR2(1000)" +
                                                        ")");
            st1.executeQuery();
            
            ArrayList<String> str = new ArrayList<String>();
            
            str.add("ypDt4Xey-vatR8Jvo-4kNuETAk-VuuiUWrL-unyL0AW1-1oJfcsAJ");
            str.add("AhrhI8l9-cHSM0lg8-X10AU7kL-tcFWBdMr-oLxELTcJ-LFEYqQUn");
            str.add("9P1DXcgT-nUknxLet-OdHfVn66-vEc7E1j2-gVA9hpRa-JD8N36nZ");
            str.add("gSTRUWoO-BpFYbL06-XKmCj5Nl-roHmG6ni-2ml5C1lu-0BZfrLv7");
            str.add("3VVCHA5S-jD6K75Uh-3PNv6uTF-vkddvskB-yo4X9GTQ-TPhIoR2M");
            str.add("V9XREcGM-N16rqaYD-RSpeQfGb-QzJed1xh-qsnE8LmA-UpP3EkgI");
            str.add("dokhQ8F6-uFmApjd8-ZXgRLz7F-U19TFe0X-tg0eTpoJ-EDPUVvKz");
            str.add("ERT1Pcnc-MzEg3OIp-4bQDoKJ6-uqMRIgBi-0HWvWPV9-BUiIBMOI");
            str.add("eF4OL3Tl-7kIDeqcl-LPm4l85r-Y5g7JYSU-rCBzLuZ9-OdK7SvrT");
            str.add("bdjyDgjC-FH02g1iF-ei86qAxl-0ZXtRopD-1sr0udYz-xmtlm75B");
            str.add("15rB81DqX-igFKhohe-y0kV0jiI-IFP2FaUR-HCFkcq6i-HlbRsoB7");
            str.add("0IdOT0Jd-Xb5hLbx7-rTIWIaTx-apOStnIq-7B6Ug0pz-nPfTWRjA");
            str.add("9KSJYzGd-r0Rq0csj-1RShprXq-oqDM2n5q-2aq7anQm-5Ietnvco");
            str.add("i8T4ZAxH-LylTKHfr-S4rCu0ly-J31eY2Wy-m3C3lr1J-5zcgVF4P");
            str.add("CXDrHqan-BRspD0RR-3WKngZE2-t1HprRin-ZMpsKnZc-qx7GGXZW");
            str.add("mZynFmaB-ZxTai3WD-KnJgQyeh-rrhcnXcA-r2oXrjSN-S7r7CvgY");
            str.add("tGHxPnXK-g3Y5HERu-ijGepnrb-Klfe4XKs-vIR8DJSN-uS1fB9UT");
            str.add("7d3vpMld-qUQS0ix3-AsmLO3EP-OTHdH7mM-tmFc411A-VVkcn75U");
            str.add("ZYWfRD9p-C6XHY1UG-NMnB4xBG-ILADjG5l-2FcBCau2-AvK4huUF");
            str.add("HqdROjRu-n5NdDion-L0eZXJ0U-seG7bGAX-304ke3yz-z7fDRSq7");
            str.add("PjudUTEd-3Y0TXZnA-VNIWVhMh-2do7mODK-LWPjK5Fr-sHDVV1Ln");
            str.add("TYU1uZQq-7E0SMZnx-mzQpvho7-CvDGUP0b-F7Yh9kdX-gEPzCaXf");
            str.add("Y1mOkZzd-GcAVPmEf-XSOmHcUv-xHbatPQV-WvSOygSv-TinzJP2O");
            str.add("fFmDuccj-8g1k5o4y-cOM3VlPD-cgDNn8S5-dxxpKPgt-lHHcIFB2");
            str.add("sFvTUYym-FNJzm6sR-gGK9J9gs-BrxePk43-WWjlJr4b-eW2iAlol");
            str.add("B8mSASnQ-Blx0eh9T-5BvxulAG-CLiKnC0h-6Q7zAegV-eiP9BY0N");
            str.add("tU8ZMD6m-8YgVieYg-p0ONJ9LJ-C9ttU84k-4VMveIlJ-25xxC7hg");
            str.add("6JfzBxWZ-uSsqxMa9-vcx4CaLq-0CKquCoA-nLtoqYfH-TWVLggJS");
            str.add("YNt7ZmUU-QFXug0DL-NHdMrB2C-VZIpOSmn-Wn718q70-uGdzTXP4");
            str.add("lgvDsahf-WM5MmmGc-sTiOlDga-jszRMkjY-KXIkHxx0-UhPZy1ed");
            str.add("777Dsahf-WM5MMMGc-sTiOlDga-jszRMKjY-KXiiHxx0-UhP551ed");
            str.add("788DsaRf-WM5xxMGc-sTi568ga-555RMKjY-KXii3450-UFF551eX");
            str.add("j9BYlnhI-8CLqtZEb-RuvYALnD-044EAS9V-PZFtVROm-rp1Hq70B");
            str.add("ENDIBML0-62tRiPAn-msIbMlmy-HjWj47qX-MXLttIqy-snSvC0Xx");
            str.add("Hc54BXdq-5DVx1vgf-62CnHdWy-vZvV3CEo-H5iHRNjN-8BnSHzlS");
            str.add("PfDG4Xt2-JdajvkEN-pkJcG5rX-s92qrBOz-nfiHBsLv-SYkaovB0");
            
            str.add("3qjUqtPV-LD998hya-XPE8homk-4xgA5uJ8-GZaQHxxR-SNEtu3kj");
            str.add("bU4L75go-4Dp1naSY-3QtqthbR-lUzaVKYa-fQkcqhJ3-RIVhySVS");
            str.add("OqFABeT4-5eUjvIQA-5PUcsddi-uyAFhsq1-PpOWlonV-kKuJdSCq");
            str.add("UHnT3RT7-cZNk9CtT-SyiksNyE-QWbukije-1UouQo2d-F22d9qQZ");
            str.add("uMJAd249-n9qKiRyZ-NKzdo6VV-C9nKEhhR-cE3uWflY-nSUODdWi");
            str.add("UTNiVhTj-GQm9mVhe-NT08DgHB-Wvyta0ZH-W6NtWzCI-sn5WSLPB");
            str.add("1hNlRVP2-XiHzAjYf-5eXCprJf-QXzv8tAP-uOTr0MsA-HrFO3oir");
            str.add("9sdv03zQ-zWh7jUfg-QYliG3kO-mCmPlFTf-TNu9Cbim-5r6713dR");
            str.add("1s4yhGmY-axlyWO1t-sO9Zbxa1-E6ioyroI-61y85L3p-t1XIBmjp");
            str.add("cEMDOh93-BaW1xNpJ-YuJ3hdfu-f6TI4mVE-GfcGXOGC-XMrdPHSI");
            str.add("FhbkuG0t-MrNG4j8b-VTSW1MKZ-hAxBzQ6q-BVxloEPY-F4mNxp2L");
            str.add("XPeKalcY-Ip8yBiYg-kW6jDQ5n-VdtR4Zxu-znGBlMnt-jxUX181P");
            str.add("n2N00VJG-zDF4KeCJ-hbq3B5NB-HJLb59PV-nkfMVm9x-cTUc1bU8");
            str.add("JAWRAciD-J1Gbg8GT-x8iT3Atv-37vQ1Uh1-2sjnq8oz-LqS3I3mZ");
            str.add("9FDVdkWG-8YnW0U2Z-Hfd8JAJJ-P04DJGp6-BhzCBpBm-HKtJ4ZZN");
            str.add("u3NMREYH-l88uJunO-rOnMavdc-YPzHe23m-Th09ySQt-P8V9kGtz");
            str.add("R9FFjlzn-3n6Xzm1g-pi9MBxrT-31ltIxv9-d8SpQ8Ki-diyRI9td");
            str.add("uuGm3aQh-lBMrbYXq-0JnP39Ve-flMHC37Y-sp9LniOo-9IyZ6sll");
            str.add("ZfzkeIjp-dDPSAkeq-SoB65xYz-URSiW2U7-z9dkjfgm-qeTcnTqZ");
            str.add("Ta08uM55-4XWcGHpI-qLdEMMOo-BastPUHa-cW6WOMAY-uqcSpSkB");
            str.add("stqfQtfm-lBRfvoAm-VEpJmUpK-0u1QcrbH-T6QoSHbP-TaWZJhJ4");
            str.add("WmvZed6J-9Iv29FdE-Bs1I7WFa-WN5jKxFc-n8CV1B3l-oOApQBae");
            str.add("UhIRurzh-YxlDlFJC-oVg4Qs7T-mHlySEzb-jkNbVHqk-HM2Trqm6");
            str.add("cQq8Gopl-tRGyMqU4-LIpNCoeI-HHjKaRBk-fFN9XeoD-JO5n4Rrz");
            str.add("WzpMQAHo-oRhM3iRm-UCeTfk7W-OXMdFqYV-ZSWJO3Ex-jg68x5R7");
            str.add("3gGtp5Tt-oEZ6sT0K-lGoxO0L6-usvjH4Xi-eR4gQIZm-NsF0d26S");
            str.add("WhBgJUu4-8XzheJBu-OKGY1eOI-GNbXELvH-u2aEDHeF-HWzLIf4u");
            str.add("AcC4lQ6b-eDzuKs5A-g59lI9LO-WdgK17yV-sjEq9iCv-uCJkvAmC");
            str.add("TA3RuxX5-ulqq7Ih5-AlcaYRPx-Rl9pFsXB-uXOueuGC-x8sg5j94");
            str.add("s7GQa84i-YXFXQpae-aEmDAg3r-Ifnaeol5-COWMGuPs-OVz0X6SE");
            str.add("xQebuXP4-ma6cIVu4-gY8H4yg1-W2Y8O2H4-FJpruk49-0pSo8cLB");
            str.add("nJFGPS79-dRpjTygS-dKB1WC8I-XUXLGOrR-UfAcX8Fr-u8H9xIYK");
            str.add("Qgv8QFQe-oHFDdIJ8-lCZBFg7f-LGVnjPNb-PtE7KoTm-uSxfSUBl");
            str.add("tBUDz37o-spUsCXke-Wr39OoCC-RLPAJXeV-m9joPTC5-7FNCpHns");
            str.add("CxDjnSQo-ikcnN3mS-HIlvQNS4-2IMYAmmb-nXsOAJ7o-KUKvhc79");
            str.add("b1VpVFR3-yEm13hkU-nJttc190-r2ay4HYh-r7t3pTHk-cjAmAm18");
            str.add("pKlzYDtb-qMdyqN2d-VAYJ02zL-qMCyrYfg-X7GLS2rs-d0v82Ivo");
            str.add("f0fgbSLU-kIImKZBR-BRpnVGmh-MdpSaQu7-GKupaJWr-LlafCa98");
            str.add("JHrWY6Ws-GkhLcaX9-SW4nOTNO-HpL0RHND-jcAT1PX2-EZW6p3WC");
            str.add("ZItQ1vL9-JxkIT2qg-ccFDu6gk-bVpzR8bY-PHL0ey6t-ZIzWL4Jt");
            str.add("kjYL2aX8-xDRuEzKz-qLT0f2i5-aa9czkWO-xTUCnyKn-VkQvuq30");
            str.add("Q0FvhHxa-Tq7F1H1u-j7VYuKIO-YaLhmAcc-61FHazuP-dAp2j91B");
            str.add("2lA73be8-qxzEtqon-vmBg5bx9-yPKRjTJ7-mL437i9b-8UJTCjHr");
            str.add("bhNVcLSG-HjIQMIuu-LdO2ZWeC-aExnsP2B-LMYVLIe2-ac3E4vrm");
            str.add("ymlmr4Ry-nfpjyEx8-rKk3ptDS-MWpN2aKy-W2zj9BVG-0MPOZqSF");
            str.add("jALGLxp3-tPZS0Gkc-rP2rkz6A-Js4hX0aj-NPpb6ADY-qPLZemHo");
            str.add("AYH8EAbc-MLDWZVSt-ncuAzdzk-kDPpvC6K-FvLfrnSI-qqzFO46y");
            str.add("MUioiCu0-ZVUC1CUt-0CTe7OCt-L0f0iH55-2YqP7zTb-a0gnoq8A");
            str.add("lczmRUSn-troDJAX9-a8p4fJzQ-EyQk9svl-nOMdrnyn-iabJeyIW");
            str.add("QOq0odol-AFrveSp2-LJUrybDg-b3N0A7vy-rfm41kZq-2aysXymU");
            str.add("alVN4saJ-g25D5x9c-k85oWBRj-SgWk16Cr-LOIlUFqD-EJDzYUMt");
            str.add("mgYa1ZH9-LbXmWhZM-MPR3KYpy-EhSfHJX0-BAJflyVD-xmNzCu6t");
            str.add("pQotkWqz-hICfPzUi-qkk9r5vY-PG2EeHDt-1n8OSo8j-YbIvVZXN");
            str.add("LJp4Se34-P3Bgkq0m-qFU5HCEh-rO8vQSkh-vvMiWI4S-lfKscchN");
            str.add("EtlMaD1j-Zu4XdLct-u14UTQC4-eSuhr2de-JjKuTZXp-S9sx7DNT");
            str.add("itYv2mZ0-TK8PUmJY-TJaZ7DIO-4aJ08Q4T-EonAroUR-7rblJ2qj");
            str.add("ZUXqpXff-dYHzAli4-SpXEHML2-a5mPJIB4-2DjWAjLs-O8PiUaeT");
            
            System.out.println(str);
            int lengths = str.size();
            System.out.println(lengths);
            
            int i = 0 ;
            
            for ( int ji = 0 ; ji < lengths ; ji ++){
                
                PreparedStatement ps = con.prepareStatement("INSERT INTO ISSUE_TBL(KEYID, DURATN) VALUES(?, ?)");
                ps.setString(1, str.get(ji));
                ps.setString(2, "12 MONTHS");
                i = ps.executeUpdate();
                
            }
            
            ///////    for 6 month duration
            
            ArrayList<String> str1 = new ArrayList<String>();
            
            str1.add("GqA4Pz-USMoFU-eJyq44-xuhquD-1EDIZ2");
            str1.add("Hn8PJo-if2fse-nMb8Fd-qaTbMj-jCARK4");
            str1.add("jW0MKQ-jGpc6W-RHNdVj-oO1E2T-XUjjNC");
            str1.add("eaROk1-tiZali-Mop2AI-MBt0rN-p5ZxDd");
            str1.add("C7MGDF-7rioRf-PVKyxv-0c92t2-jjJ1C7");
            str1.add("f49Uto-ohpT10-EoqhFv-Wspb2R-l6MJtx");
            str1.add("40iWLA-Hgrn2Z-LMzaZD-Eyynf2-JWSFrQ");
            str1.add("F2CDx9-ZIVi1M-agJABd-1gxIdg-AZ4kSQ");
            str1.add("zxH1lE-pad8Oo-RsSA9R-ZMaIf7-SSGE4e");
            str1.add("O5c1x9-8qiXJB-90Dg2T-S33Eua-q9TL2F");
            str1.add("7vcRFM-18sroe-jYvQi4-fMEWaO-LMv2D3");
            str1.add("k6pvaL-8NHK3v-BhEj9v-nYHChl-3JOHdM");
            
            int jm = 0 ;
            
            for ( int jk = 0 ; jk < str1.size() ; jk ++){
                
                PreparedStatement ps = con.prepareStatement("INSERT INTO ISSUE_TBL(KEYID, DURATN) VALUES(?, ?)");
                ps.setString(1, str1.get(jk));
                ps.setString(2, "6 MONTHS");
                jm = ps.executeUpdate();
                
            }
            
            
            
            
            if(i > 0 && jm > 0 && io > 0){
                JOptionPane.showMessageDialog(this, "Setup Successfully...", "Success", JOptionPane.INFORMATION_MESSAGE);
            }else{
                JOptionPane.showMessageDialog(this, "Error...", "Error", JOptionPane.ERROR_MESSAGE);
            }
            
            
            ////////////////////////////////
            ////////////////////////////////
            ////////////////////////////////
            con.close();
            
        }
        catch(Exception ea){
            ea.printStackTrace();
            JOptionPane.showMessageDialog(this, ea, "Notice", JOptionPane.WARNING_MESSAGE);
        }
        
        
        
        
        
        
        
        
        
    }//GEN-LAST:event_jButton1ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(insert_VAls.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(insert_VAls.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(insert_VAls.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(insert_VAls.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new insert_VAls().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables
}
