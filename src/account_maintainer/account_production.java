/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package account_maintainer;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

/**
 *
 * @author Welcome
 */
public class account_production extends javax.swing.JFrame {

    /////  create border
    Border textFile_border = BorderFactory.createMatteBorder(0, 0, 2, 0, Color.red);
    Border textFile_borderNo = BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(0, 204, 204));

    /**
     * Creates new form account_DashBoard
     */
    public account_production() {
        initComponents();

        //  center form
        this.setLocationRelativeTo(null);

        //////  set icon
        jLabel_profile.setIcon(new ImageIcon(getClass().getResource("IMAGES/useron.png")));

        //// make dashboard selected from sidebar
        jLabel_productionMouseEntered(null);

        ////////  Set Border
        jTextField_RawQty_From.setBorder(textFile_border);
        jTextField_RawQty_to.setBorder(textFile_border);
        jTextField_mang_search.setBorder(textFile_border);

        //////////   generate production bill id
        generatte_production_id();

        //////////  set values of jComboBox
        set_value_JcomboBox();

        /////////   show manufactur table details
        show_production();

    }

    /////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////////
    ////  ~~~~~~~~~   Show data of jTable manufactur
    public ArrayList<production> productionList() {
        ArrayList<production> productionList = new ArrayList<>();

        ///////////////////////////
        Connection con;
        try {
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

            production production;

            PreparedStatement ps = con.prepareStatement("SELECT * FROM PRODUCTION_DESTINATION_TBL");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String prodctnIdno_destrination = rs.getString("PRODCTN_IDNO");
                
                String prodctn_Nm_destrination = "";
                
                String proiddds = rs.getString("PROIDNO");
                
                if( proiddds.isEmpty() || proiddds.equals("none") ){
                    
                    prodctn_Nm_destrination = proiddds ;
                    
                }else{
                    
                    PreparedStatement pss = con.prepareStatement("SELECT * FROM PRODUCT_TBL WHERE PROIDNO = ?");
                    pss.setString(1, proiddds);
                    ResultSet rss = pss.executeQuery();
                    while(rss.next()){
                        prodctn_Nm_destrination = rss.getString("PRODUCT_NM");
                    }
                    
                }
                
                String prodctn_Qty_destrination = rs.getString("USED_QTY");
                String prodctn_dtls_destrination_fnl = prodctn_Nm_destrination + "->" + prodctn_Qty_destrination;
                String prodctn_Dttm_destrination = rs.getString("DATE_TIME_PRDCTN");

                String prodctn_dtls_source = "";

                PreparedStatement ps1 = con.prepareStatement("SELECT PRODUCTION_SOURCE_TBL.PRODCTN_IDNO, PRODUCT_TBL.PRODUCT_NM, PRODUCTION_SOURCE_TBL.USED_QTY FROM PRODUCT_TBL,PRODUCTION_SOURCE_TBL WHERE PRODUCT_TBL.PROIDNO=PRODUCTION_SOURCE_TBL.PROIDNO AND PRODCTN_IDNO = ?");
                ps1.setString(1, prodctnIdno_destrination);
                ResultSet rs1 = ps1.executeQuery();
                while (rs1.next()) {
                    prodctn_dtls_source += rs1.getString("PRODUCT_NM") + "->" + rs1.getString("USED_QTY") + ",";
                }
                String prodctn_dtls_source_fnl = prodctn_dtls_source.substring(0, prodctn_dtls_source.length() - 1);

                production = new production(prodctnIdno_destrination, prodctn_dtls_source_fnl, prodctn_dtls_destrination_fnl, prodctn_Dttm_destrination);
                productionList.add(production);
            }
            /////////////////////////////////////////////
            /////////////////////////////////////////////
            /////////////////////////////////////////////
            con.close();
        } catch (Exception ea) {
            ea.printStackTrace();
        }
        ////////////////////////////////////////////////////////////////////////
        return productionList;
        ////////////////////////////////////////////////////////////////////////
    }

    public void show_production() {
        ArrayList<production> productionLst = productionList();
        DefaultTableModel model = (DefaultTableModel) jTable_stored_PurchaseBill.getModel();
        Object[] row = new Object[4];
        for (int i = 0; i < productionLst.size(); i++) {
            row[0] = productionLst.get(i).getproductionId();
            row[1] = productionLst.get(i).getrawMeti();
            row[2] = productionLst.get(i).getmfgMeti();
            row[3] = productionLst.get(i).getmfgdttm();
            model.addRow(row);
        }
    }

    ////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////
    ////  ~~~~~~~~~   Show data of jTable manufactur     ->>>>>   get search result
    public ArrayList<production> productionList_Search() {
        ArrayList<production> productionList = new ArrayList<>();

        ///////////////////////////
        Connection con;
        try {
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

            String Search_item = "%" + jTextField_mang_search.getText() + "%";

            production production;

            PreparedStatement ps = con.prepareStatement("SELECT * FROM PRODUCTION_DESTINATION_TBL WHERE (PRODCTN_IDNO LIKE ? OR DATE_TIME_PRDCTN LIKE ?)");
            ps.setString(1, Search_item);
            ps.setString(2, Search_item);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String prodctnIdno_destrination = rs.getString("PRODCTN_IDNO");
                String prodctn_Nm_destrination = "";
                
                String proiddds = rs.getString("PROIDNO");
                
                if( proiddds.isEmpty() || proiddds.equals("none") ){
                    
                    prodctn_Nm_destrination = proiddds ;
                    
                }else{
                    
                    PreparedStatement pss = con.prepareStatement("SELECT * FROM PRODUCT_TBL WHERE PROIDNO = ?");
                    pss.setString(1, proiddds);
                    ResultSet rss = pss.executeQuery();
                    while(rss.next()){
                        prodctn_Nm_destrination = rss.getString("PRODUCT_NM");
                    }
                    
                }
                
                String prodctn_Qty_destrination = rs.getString("USED_QTY");
                String prodctn_dtls_destrination_fnl = prodctn_Nm_destrination + "->" + prodctn_Qty_destrination;
                String prodctn_Dttm_destrination = rs.getString("DATE_TIME_PRDCTN");

                String prodctn_dtls_source = "";

                PreparedStatement ps1 = con.prepareStatement("SELECT PRODUCTION_SOURCE_TBL.PRODCTN_IDNO, PRODUCT_TBL.PRODUCT_NM, PRODUCTION_SOURCE_TBL.USED_QTY FROM PRODUCT_TBL,PRODUCTION_SOURCE_TBL WHERE PRODUCT_TBL.PROIDNO=PRODUCTION_SOURCE_TBL.PROIDNO AND PRODCTN_IDNO = ?");
                ps1.setString(1, prodctnIdno_destrination);
                ResultSet rs1 = ps1.executeQuery();
                while (rs1.next()) {
                    prodctn_dtls_source += rs1.getString("PRODUCT_NM") + "->" + rs1.getString("USED_QTY") + ",";
                }
                String prodctn_dtls_source_fnl = prodctn_dtls_source.substring(0, prodctn_dtls_source.length() - 1);

                production = new production(prodctnIdno_destrination, prodctn_dtls_source_fnl, prodctn_dtls_destrination_fnl, prodctn_Dttm_destrination);
                productionList.add(production);
            }
            /////////////////////////////////////////////
            /////////////////////////////////////////////
            /////////////////////////////////////////////
            con.close();
        } catch (Exception ea) {
            ea.printStackTrace();
        }
        ////////////////////////////////////////////////////////////////////////
        return productionList;
        ////////////////////////////////////////////////////////////////////////
    }

    public void show_production_Search() {
        ArrayList<production> productionLst = productionList_Search();
        DefaultTableModel model = (DefaultTableModel) jTable_stored_PurchaseBill.getModel();

        model.setRowCount(0);

        Object[] row = new Object[4];
        for (int i = 0; i < productionLst.size(); i++) {
            row[0] = productionLst.get(i).getproductionId();
            row[1] = productionLst.get(i).getrawMeti();
            row[2] = productionLst.get(i).getmfgMeti();
            row[3] = productionLst.get(i).getmfgdttm();
            model.addRow(row);
        }
    }

    ////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel_dashboard = new javax.swing.JLabel();
        jLabel_product = new javax.swing.JLabel();
        jLabel_sell = new javax.swing.JLabel();
        jLabel_production = new javax.swing.JLabel();
        jLabel_Expenditure = new javax.swing.JLabel();
        jLabel_bill = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jComboBox_prodtNm_From = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        jTextField_RawQty_From = new javax.swing.JTextField();
        jLabel_unitSymbl_from = new javax.swing.JLabel();
        jButton_storeRaw_Matirial = new javax.swing.JButton();
        jButton_removeRow_tempTbl = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable_tempStr_val = new javax.swing.JTable();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jComboBox_prodtNm_to = new javax.swing.JComboBox<>();
        jLabel7 = new javax.swing.JLabel();
        jTextField_RawQty_to = new javax.swing.JTextField();
        jLabel_unitSymbl_to = new javax.swing.JLabel();
        jLabel_productionBill_idno = new javax.swing.JLabel();
        jButton_ProductionDetails = new javax.swing.JButton();
        jButton_resetAll = new javax.swing.JButton();
        jLabel_from_presnt_qty = new javax.swing.JLabel();
        jLabel_to_presnt_qty = new javax.swing.JLabel();
        jButton_Update_Production = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jLabel_close = new javax.swing.JLabel();
        jLabel_profile = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        Btn_Download_productn = new javax.swing.JButton();
        jButton_Remove_prodctn_Bill = new javax.swing.JButton();
        jTextField_mang_search = new javax.swing.JTextField();
        jButton_search = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable_stored_PurchaseBill = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        jPanel1.setBackground(new java.awt.Color(204, 204, 204));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jLabel_dashboard.setBackground(new java.awt.Color(0, 204, 204));
        jLabel_dashboard.setFont(new java.awt.Font("Shonar Bangla", 3, 36)); // NOI18N
        jLabel_dashboard.setForeground(new java.awt.Color(255, 255, 255));
        jLabel_dashboard.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel_dashboard.setIcon(new javax.swing.ImageIcon(getClass().getResource("/account_maintainer/IMAGES/dashboard.png"))); // NOI18N
        jLabel_dashboard.setText("Dashboard");
        jLabel_dashboard.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel_dashboard.setOpaque(true);
        jLabel_dashboard.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel_dashboardMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel_dashboardMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel_dashboardMouseExited(evt);
            }
        });

        jLabel_product.setBackground(new java.awt.Color(0, 204, 204));
        jLabel_product.setFont(new java.awt.Font("Shonar Bangla", 3, 36)); // NOI18N
        jLabel_product.setForeground(new java.awt.Color(255, 255, 255));
        jLabel_product.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel_product.setIcon(new javax.swing.ImageIcon(getClass().getResource("/account_maintainer/IMAGES/product.png"))); // NOI18N
        jLabel_product.setText("Product");
        jLabel_product.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel_product.setOpaque(true);
        jLabel_product.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel_productMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel_productMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel_productMouseExited(evt);
            }
        });

        jLabel_sell.setBackground(new java.awt.Color(0, 204, 204));
        jLabel_sell.setFont(new java.awt.Font("Shonar Bangla", 3, 36)); // NOI18N
        jLabel_sell.setForeground(new java.awt.Color(255, 255, 255));
        jLabel_sell.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel_sell.setIcon(new javax.swing.ImageIcon(getClass().getResource("/account_maintainer/IMAGES/sell.png"))); // NOI18N
        jLabel_sell.setText("Sell");
        jLabel_sell.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel_sell.setOpaque(true);
        jLabel_sell.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel_sellMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel_sellMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel_sellMouseExited(evt);
            }
        });

        jLabel_production.setBackground(new java.awt.Color(0, 204, 204));
        jLabel_production.setFont(new java.awt.Font("Shonar Bangla", 3, 36)); // NOI18N
        jLabel_production.setForeground(new java.awt.Color(255, 255, 255));
        jLabel_production.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel_production.setIcon(new javax.swing.ImageIcon(getClass().getResource("/account_maintainer/IMAGES/production.png"))); // NOI18N
        jLabel_production.setText("Production");
        jLabel_production.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel_production.setOpaque(true);
        jLabel_production.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel_productionMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel_productionMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel_productionMouseExited(evt);
            }
        });

        jLabel_Expenditure.setBackground(new java.awt.Color(0, 204, 204));
        jLabel_Expenditure.setFont(new java.awt.Font("Shonar Bangla", 3, 36)); // NOI18N
        jLabel_Expenditure.setForeground(new java.awt.Color(255, 255, 255));
        jLabel_Expenditure.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel_Expenditure.setIcon(new javax.swing.ImageIcon(getClass().getResource("/account_maintainer/IMAGES/expenditure.png"))); // NOI18N
        jLabel_Expenditure.setText("Expenditure");
        jLabel_Expenditure.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel_Expenditure.setOpaque(true);
        jLabel_Expenditure.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel_ExpenditureMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel_ExpenditureMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel_ExpenditureMouseExited(evt);
            }
        });

        jLabel_bill.setBackground(new java.awt.Color(0, 204, 204));
        jLabel_bill.setFont(new java.awt.Font("Shonar Bangla", 3, 36)); // NOI18N
        jLabel_bill.setForeground(new java.awt.Color(255, 255, 255));
        jLabel_bill.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel_bill.setIcon(new javax.swing.ImageIcon(getClass().getResource("/account_maintainer/IMAGES/searchOn.png"))); // NOI18N
        jLabel_bill.setText("Search");
        jLabel_bill.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel_bill.setOpaque(true);
        jLabel_bill.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel_billMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel_billMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel_billMouseExited(evt);
            }
        });

        jPanel3.setBackground(new java.awt.Color(204, 204, 255));

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Tempus Sans ITC", 3, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(26, 198, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Production Entry");

        jLabel4.setBackground(new java.awt.Color(255, 255, 255));
        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(172, 115, 57));
        jLabel4.setText("Raw Material");

        jLabel2.setBackground(new java.awt.Color(255, 255, 255));
        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 204, 255));
        jLabel2.setText("Select Product");

        jComboBox_prodtNm_From.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jComboBox_prodtNm_From.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBox_prodtNm_FromItemStateChanged(evt);
            }
        });

        jLabel3.setBackground(new java.awt.Color(255, 255, 255));
        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 204, 255));
        jLabel3.setText("Quantity");

        jTextField_RawQty_From.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jTextField_RawQty_From.setForeground(new java.awt.Color(0, 138, 230));
        jTextField_RawQty_From.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextField_RawQty_FromKeyPressed(evt);
            }
        });

        jLabel_unitSymbl_from.setBackground(new java.awt.Color(255, 255, 255));
        jLabel_unitSymbl_from.setFont(new java.awt.Font("Tempus Sans ITC", 1, 21)); // NOI18N
        jLabel_unitSymbl_from.setForeground(new java.awt.Color(51, 204, 255));
        jLabel_unitSymbl_from.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel_unitSymbl_from.setText(".....");

        jButton_storeRaw_Matirial.setBackground(new java.awt.Color(64, 255, 0));
        jButton_storeRaw_Matirial.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jButton_storeRaw_Matirial.setForeground(new java.awt.Color(255, 255, 255));
        jButton_storeRaw_Matirial.setIcon(new javax.swing.ImageIcon(getClass().getResource("/account_maintainer/IMAGES/plusIcn.png"))); // NOI18N
        jButton_storeRaw_Matirial.setText("Add");
        jButton_storeRaw_Matirial.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton_storeRaw_Matirial.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_storeRaw_MatirialActionPerformed(evt);
            }
        });

        jButton_removeRow_tempTbl.setBackground(new java.awt.Color(255, 71, 26));
        jButton_removeRow_tempTbl.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jButton_removeRow_tempTbl.setForeground(new java.awt.Color(255, 255, 255));
        jButton_removeRow_tempTbl.setText("Remove");
        jButton_removeRow_tempTbl.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton_removeRow_tempTbl.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_removeRow_tempTblActionPerformed(evt);
            }
        });

        jTable_tempStr_val.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        jTable_tempStr_val.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Product Name", "Unit", "Quantity Used"
            }
        )
        {
            public boolean isCellEditable(int row, int column){
                return false;
            }
        }
    );
    jTable_tempStr_val.addMouseListener(new java.awt.event.MouseAdapter() {
        public void mouseClicked(java.awt.event.MouseEvent evt) {
            jTable_tempStr_valMouseClicked(evt);
        }
    });
    jScrollPane1.setViewportView(jTable_tempStr_val);

    jLabel5.setBackground(new java.awt.Color(255, 255, 255));
    jLabel5.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
    jLabel5.setForeground(new java.awt.Color(153, 204, 0));
    jLabel5.setText("Product Material");

    jLabel6.setBackground(new java.awt.Color(255, 255, 255));
    jLabel6.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
    jLabel6.setForeground(new java.awt.Color(0, 204, 255));
    jLabel6.setText("Select Product");

    jComboBox_prodtNm_to.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
    jComboBox_prodtNm_to.addItemListener(new java.awt.event.ItemListener() {
        public void itemStateChanged(java.awt.event.ItemEvent evt) {
            jComboBox_prodtNm_toItemStateChanged(evt);
        }
    });

    jLabel7.setBackground(new java.awt.Color(255, 255, 255));
    jLabel7.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
    jLabel7.setForeground(new java.awt.Color(0, 204, 255));
    jLabel7.setText("Quantity");

    jTextField_RawQty_to.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
    jTextField_RawQty_to.setForeground(new java.awt.Color(0, 138, 230));
    jTextField_RawQty_to.addKeyListener(new java.awt.event.KeyAdapter() {
        public void keyPressed(java.awt.event.KeyEvent evt) {
            jTextField_RawQty_toKeyPressed(evt);
        }
    });

    jLabel_unitSymbl_to.setBackground(new java.awt.Color(255, 255, 255));
    jLabel_unitSymbl_to.setFont(new java.awt.Font("Tempus Sans ITC", 1, 21)); // NOI18N
    jLabel_unitSymbl_to.setForeground(new java.awt.Color(51, 204, 255));
    jLabel_unitSymbl_to.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
    jLabel_unitSymbl_to.setText(".....");

    jLabel_productionBill_idno.setFont(new java.awt.Font("Tempus Sans ITC", 0, 18)); // NOI18N
    jLabel_productionBill_idno.setForeground(new java.awt.Color(0, 204, 255));
    jLabel_productionBill_idno.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

    jButton_ProductionDetails.setBackground(new java.awt.Color(51, 204, 51));
    jButton_ProductionDetails.setFont(new java.awt.Font("Sylfaen", 1, 24)); // NOI18N
    jButton_ProductionDetails.setForeground(new java.awt.Color(255, 255, 255));
    jButton_ProductionDetails.setIcon(new javax.swing.ImageIcon(getClass().getResource("/account_maintainer/IMAGES/plusIcn.png"))); // NOI18N
    jButton_ProductionDetails.setText("Mfg.");
    jButton_ProductionDetails.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
    jButton_ProductionDetails.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            jButton_ProductionDetailsActionPerformed(evt);
        }
    });

    jButton_resetAll.setBackground(new java.awt.Color(51, 51, 255));
    jButton_resetAll.setFont(new java.awt.Font("Sylfaen", 1, 24)); // NOI18N
    jButton_resetAll.setForeground(new java.awt.Color(255, 255, 255));
    jButton_resetAll.setIcon(new javax.swing.ImageIcon(getClass().getResource("/account_maintainer/IMAGES/reset.png"))); // NOI18N
    jButton_resetAll.setText("Reset");
    jButton_resetAll.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
    jButton_resetAll.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            jButton_resetAllActionPerformed(evt);
        }
    });

    jLabel_from_presnt_qty.setBackground(new java.awt.Color(255, 255, 255));
    jLabel_from_presnt_qty.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
    jLabel_from_presnt_qty.setForeground(new java.awt.Color(0, 204, 255));
    jLabel_from_presnt_qty.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
    jLabel_from_presnt_qty.setText(".....");

    jLabel_to_presnt_qty.setBackground(new java.awt.Color(255, 255, 255));
    jLabel_to_presnt_qty.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
    jLabel_to_presnt_qty.setForeground(new java.awt.Color(0, 204, 255));
    jLabel_to_presnt_qty.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
    jLabel_to_presnt_qty.setText(".....");

    jButton_Update_Production.setBackground(new java.awt.Color(255, 204, 0));
    jButton_Update_Production.setFont(new java.awt.Font("Sylfaen", 1, 20)); // NOI18N
    jButton_Update_Production.setForeground(new java.awt.Color(255, 255, 255));
    jButton_Update_Production.setIcon(new javax.swing.ImageIcon(getClass().getResource("/account_maintainer/IMAGES/update.png"))); // NOI18N
    jButton_Update_Production.setText("Update");
    jButton_Update_Production.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
    jButton_Update_Production.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            jButton_Update_ProductionActionPerformed(evt);
        }
    });

    javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
    jPanel4.setLayout(jPanel4Layout);
    jPanel4Layout.setHorizontalGroup(
        jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        .addGroup(jPanel4Layout.createSequentialGroup()
            .addContainerGap()
            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel4Layout.createSequentialGroup()
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel2)
                        .addComponent(jLabel3)
                        .addComponent(jLabel4))
                    .addGap(18, 18, 18)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(jPanel4Layout.createSequentialGroup()
                            .addComponent(jButton_removeRow_tempTbl, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton_storeRaw_Matirial, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel4Layout.createSequentialGroup()
                            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jComboBox_prodtNm_From, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jTextField_RawQty_From))
                            .addGap(18, 18, 18)
                            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jLabel_from_presnt_qty, javax.swing.GroupLayout.DEFAULT_SIZE, 74, Short.MAX_VALUE)
                                .addComponent(jLabel_unitSymbl_from, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addComponent(jLabel_productionBill_idno, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGap(31, 31, 31))
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 465, Short.MAX_VALUE)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                    .addComponent(jButton_Update_Production, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                    .addComponent(jButton_resetAll, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                    .addComponent(jButton_ProductionDetails, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel4Layout.createSequentialGroup()
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel5)
                        .addGroup(jPanel4Layout.createSequentialGroup()
                            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel6)
                                .addComponent(jLabel7))
                            .addGap(18, 18, 18)
                            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jTextField_RawQty_to, javax.swing.GroupLayout.DEFAULT_SIZE, 177, Short.MAX_VALUE)
                                .addComponent(jComboBox_prodtNm_to, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGap(18, 18, 18)
                            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jLabel_to_presnt_qty, javax.swing.GroupLayout.DEFAULT_SIZE, 74, Short.MAX_VALUE)
                                .addComponent(jLabel_unitSymbl_to, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                    .addGap(0, 0, Short.MAX_VALUE)))
            .addContainerGap())
    );
    jPanel4Layout.setVerticalGroup(
        jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(jPanel4Layout.createSequentialGroup()
            .addContainerGap()
            .addComponent(jLabel1)
            .addGap(18, 18, 18)
            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel4Layout.createSequentialGroup()
                    .addComponent(jLabel_productionBill_idno, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGap(2, 2, 2)))
            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(jComboBox_prodtNm_From)
                .addComponent(jLabel_from_presnt_qty, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(jTextField_RawQty_From, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(jLabel_unitSymbl_from, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jButton_storeRaw_Matirial, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(jButton_removeRow_tempTbl, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(jComboBox_prodtNm_to)
                .addComponent(jLabel_to_presnt_qty, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(jTextField_RawQty_to, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(jLabel_unitSymbl_to, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGap(18, 18, 18)
            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton_ProductionDetails, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton_resetAll, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addComponent(jButton_Update_Production, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGap(26, 26, 26))
    );

    jPanel5.setBackground(new java.awt.Color(153, 204, 255));

    jLabel_close.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
    jLabel_close.setForeground(new java.awt.Color(153, 153, 153));
    jLabel_close.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
    jLabel_close.setText("X");
    jLabel_close.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
    jLabel_close.addMouseListener(new java.awt.event.MouseAdapter() {
        public void mouseClicked(java.awt.event.MouseEvent evt) {
            jLabel_closeMouseClicked(evt);
        }
    });

    jLabel_profile.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
    jLabel_profile.addMouseListener(new java.awt.event.MouseAdapter() {
        public void mouseClicked(java.awt.event.MouseEvent evt) {
            jLabel_profileMouseClicked(evt);
        }
    });

    jLabel8.setFont(new java.awt.Font("Tempus Sans ITC", 3, 24)); // NOI18N
    jLabel8.setForeground(new java.awt.Color(255, 255, 255));
    jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
    jLabel8.setText("Production Entry");

    Btn_Download_productn.setBackground(new java.awt.Color(0, 255, 0));
    Btn_Download_productn.setFont(new java.awt.Font("Traditional Arabic", 3, 20)); // NOI18N
    Btn_Download_productn.setForeground(new java.awt.Color(255, 255, 255));
    Btn_Download_productn.setText("Download");
    Btn_Download_productn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
    Btn_Download_productn.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            Btn_Download_productnActionPerformed(evt);
        }
    });

    jButton_Remove_prodctn_Bill.setBackground(new java.awt.Color(255, 51, 0));
    jButton_Remove_prodctn_Bill.setFont(new java.awt.Font("Traditional Arabic", 3, 20)); // NOI18N
    jButton_Remove_prodctn_Bill.setForeground(new java.awt.Color(255, 255, 255));
    jButton_Remove_prodctn_Bill.setIcon(new javax.swing.ImageIcon(getClass().getResource("/account_maintainer/IMAGES/delete.png"))); // NOI18N
    jButton_Remove_prodctn_Bill.setText("Remove");
    jButton_Remove_prodctn_Bill.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
    jButton_Remove_prodctn_Bill.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            jButton_Remove_prodctn_BillActionPerformed(evt);
        }
    });

    jTextField_mang_search.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
    jTextField_mang_search.setForeground(new java.awt.Color(166, 166, 166));
    jTextField_mang_search.setText("Search by Manufacture Bill No. or Date-Time...");
    jTextField_mang_search.addFocusListener(new java.awt.event.FocusAdapter() {
        public void focusGained(java.awt.event.FocusEvent evt) {
            jTextField_mang_searchFocusGained(evt);
        }
        public void focusLost(java.awt.event.FocusEvent evt) {
            jTextField_mang_searchFocusLost(evt);
        }
    });
    jTextField_mang_search.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            jTextField_mang_searchActionPerformed(evt);
        }
    });

    jButton_search.setBackground(new java.awt.Color(51, 255, 0));
    jButton_search.setIcon(new javax.swing.ImageIcon(getClass().getResource("/account_maintainer/IMAGES/searchOn.png"))); // NOI18N
    jButton_search.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
    jButton_search.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            jButton_searchActionPerformed(evt);
        }
    });

    jTable_stored_PurchaseBill.setModel(new javax.swing.table.DefaultTableModel(
        new Object [][] {

        },
        new String [] {
            "Production ID", "Production Raw Material", "Production Mfg Material", "Date-Time"
        }
    ));
    jTable_stored_PurchaseBill.addMouseListener(new java.awt.event.MouseAdapter() {
        public void mouseClicked(java.awt.event.MouseEvent evt) {
            jTable_stored_PurchaseBillMouseClicked(evt);
        }
    });
    jScrollPane3.setViewportView(jTable_stored_PurchaseBill);

    javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
    jPanel5.setLayout(jPanel5Layout);
    jPanel5Layout.setHorizontalGroup(
        jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
            .addGap(46, 46, 46)
            .addComponent(jButton_Remove_prodctn_Bill, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(Btn_Download_productn, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGap(54, 54, 54))
        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
            .addContainerGap()
            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                .addComponent(jTextField_mang_search, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 394, Short.MAX_VALUE)
                .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                .addGroup(jPanel5Layout.createSequentialGroup()
                    .addComponent(jLabel_profile, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(jLabel_close, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addComponent(jButton_search, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addContainerGap())
        .addGroup(jPanel5Layout.createSequentialGroup()
            .addContainerGap()
            .addComponent(jScrollPane3)
            .addContainerGap())
    );
    jPanel5Layout.setVerticalGroup(
        jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(jPanel5Layout.createSequentialGroup()
            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                .addComponent(jLabel_profile, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel_close, javax.swing.GroupLayout.DEFAULT_SIZE, 43, Short.MAX_VALUE)
                .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.TRAILING))
            .addGap(18, 18, 18)
            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                .addComponent(jTextField_mang_search, javax.swing.GroupLayout.DEFAULT_SIZE, 36, Short.MAX_VALUE)
                .addComponent(jButton_search, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                .addComponent(jButton_Remove_prodctn_Bill, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(Btn_Download_productn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addContainerGap())
    );

    javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
    jPanel3.setLayout(jPanel3Layout);
    jPanel3Layout.setHorizontalGroup(
        jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(jPanel3Layout.createSequentialGroup()
            .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
    );
    jPanel3Layout.setVerticalGroup(
        jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        .addComponent(jPanel5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
    );

    javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
    jPanel2.setLayout(jPanel2Layout);
    jPanel2Layout.setHorizontalGroup(
        jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(jPanel2Layout.createSequentialGroup()
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                .addComponent(jLabel_Expenditure, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel_production, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel_product, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel_dashboard, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 197, Short.MAX_VALUE)
                .addComponent(jLabel_sell, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel_bill, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
    );
    jPanel2Layout.setVerticalGroup(
        jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        .addGroup(jPanel2Layout.createSequentialGroup()
            .addContainerGap()
            .addComponent(jLabel_dashboard, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(jLabel_product, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(jLabel_production, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(jLabel_sell, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGap(7, 7, 7)
            .addComponent(jLabel_Expenditure, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGap(7, 7, 7)
            .addComponent(jLabel_bill, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
    );

    javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
    jPanel1.setLayout(jPanel1Layout);
    jPanel1Layout.setHorizontalGroup(
        jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(jPanel1Layout.createSequentialGroup()
            .addContainerGap()
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addContainerGap())
    );
    jPanel1Layout.setVerticalGroup(
        jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
            .addContainerGap()
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addContainerGap())
    );

    javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
    getContentPane().setLayout(layout);
    layout.setHorizontalGroup(
        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
    );
    layout.setVerticalGroup(
        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
    );

    pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jLabel_closeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel_closeMouseClicked

        // Close window button
        this.dispose();

    }//GEN-LAST:event_jLabel_closeMouseClicked

    private void jLabel_dashboardMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel_dashboardMouseEntered

        jLabel_dashboard.setBackground(new Color(255, 255, 255));
        jLabel_dashboard.setForeground(new Color(0, 204, 204));
        jLabel_dashboard.setBorder(textFile_border);

    }//GEN-LAST:event_jLabel_dashboardMouseEntered

    private void jLabel_dashboardMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel_dashboardMouseExited

        jLabel_dashboard.setBackground(new Color(0, 204, 204));
        jLabel_dashboard.setForeground(new Color(255, 255, 255));
        jLabel_dashboard.setBorder(textFile_borderNo);

    }//GEN-LAST:event_jLabel_dashboardMouseExited

    private void jLabel_productMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel_productMouseEntered

        jLabel_product.setBackground(new Color(255, 255, 255));
        jLabel_product.setForeground(new Color(0, 204, 204));
        jLabel_product.setBorder(textFile_border);

    }//GEN-LAST:event_jLabel_productMouseEntered

    private void jLabel_productMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel_productMouseExited

        jLabel_product.setBackground(new Color(0, 204, 204));
        jLabel_product.setForeground(new Color(255, 255, 255));
        jLabel_product.setBorder(textFile_borderNo);

    }//GEN-LAST:event_jLabel_productMouseExited

    private void jLabel_productionMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel_productionMouseEntered

        jLabel_production.setBackground(new Color(255, 255, 255));
        jLabel_production.setForeground(new Color(0, 204, 204));
        jLabel_production.setBorder(textFile_border);

    }//GEN-LAST:event_jLabel_productionMouseEntered

    private void jLabel_productionMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel_productionMouseExited


    }//GEN-LAST:event_jLabel_productionMouseExited

    private void jLabel_sellMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel_sellMouseEntered

        jLabel_sell.setBackground(new Color(255, 255, 255));
        jLabel_sell.setForeground(new Color(0, 204, 204));
        jLabel_sell.setBorder(textFile_border);

    }//GEN-LAST:event_jLabel_sellMouseEntered

    private void jLabel_sellMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel_sellMouseExited

        jLabel_sell.setBackground(new Color(0, 204, 204));
        jLabel_sell.setForeground(new Color(255, 255, 255));
        jLabel_sell.setBorder(textFile_borderNo);

    }//GEN-LAST:event_jLabel_sellMouseExited

    private void jLabel_ExpenditureMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel_ExpenditureMouseEntered

        jLabel_Expenditure.setBackground(new Color(255, 255, 255));
        jLabel_Expenditure.setForeground(new Color(0, 204, 204));
        jLabel_Expenditure.setBorder(textFile_border);

    }//GEN-LAST:event_jLabel_ExpenditureMouseEntered

    private void jLabel_ExpenditureMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel_ExpenditureMouseExited

        jLabel_Expenditure.setBackground(new Color(0, 204, 204));
        jLabel_Expenditure.setForeground(new Color(255, 255, 255));
        jLabel_Expenditure.setBorder(textFile_borderNo);

    }//GEN-LAST:event_jLabel_ExpenditureMouseExited

    private void jLabel_billMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel_billMouseEntered

        jLabel_bill.setBackground(new Color(255, 255, 255));
        jLabel_bill.setForeground(new Color(0, 204, 204));
        jLabel_bill.setBorder(textFile_border);

    }//GEN-LAST:event_jLabel_billMouseEntered

    private void jLabel_billMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel_billMouseExited

        jLabel_bill.setBackground(new Color(0, 204, 204));
        jLabel_bill.setForeground(new Color(255, 255, 255));
        jLabel_bill.setBorder(textFile_borderNo);

    }//GEN-LAST:event_jLabel_billMouseExited

    private void jLabel_dashboardMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel_dashboardMouseClicked

        account_DashBoard form = new account_DashBoard();
        form.setVisible(true);
        form.pack();
        form.setLocationRelativeTo(null);
        //close the current form (login form)
        this.dispose();

    }//GEN-LAST:event_jLabel_dashboardMouseClicked

    private void jLabel_productMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel_productMouseClicked

        account_AddProduct form = new account_AddProduct();
        form.setVisible(true);
        form.pack();
        form.setLocationRelativeTo(null);
        //close the current form (login form)
        this.dispose();

    }//GEN-LAST:event_jLabel_productMouseClicked

    private void jLabel_productionMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel_productionMouseClicked

        account_production form = new account_production();
        form.setVisible(true);
        form.pack();
        form.setLocationRelativeTo(null);
        //close the current form (login form)
        this.dispose();

    }//GEN-LAST:event_jLabel_productionMouseClicked

    private void jComboBox_prodtNm_FromItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox_prodtNm_FromItemStateChanged

        Connection con;
        try {
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

            String ProdtNm = jComboBox_prodtNm_From.getSelectedItem().toString();
            PreparedStatement ps = con.prepareStatement("SELECT UNIT_TBL.UNIT_NAME,UNIT_SYMBOL,PRODUCT_TBL.PRODUCT_QTY FROM UNIT_TBL,PRODUCT_TBL WHERE UNIT_TBL.UNIT_IDNO=PRODUCT_TBL.UNIT_IDNO AND PRODUCT_TBL.PRODUCT_NM = ?");
            ps.setString(1, ProdtNm);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String unitNm = rs.getString("UNIT_SYMBOL");
                String present_qty = rs.getString("PRODUCT_QTY");
                jLabel_unitSymbl_from.setText(unitNm);
                jLabel_from_presnt_qty.setText("[" + present_qty + "]");
            }

            ////////////////////////////////////////////////////////////////////
            ////////////////////////////////////////////////////////////////////
            ////////////////////////////////////////////////////////////////////
            con.close();
        } catch (Exception ea) {
            ea.printStackTrace();
        }

    }//GEN-LAST:event_jComboBox_prodtNm_FromItemStateChanged

    private void jButton_removeRow_tempTblActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_removeRow_tempTblActionPerformed

        DefaultTableModel tblModel = (DefaultTableModel) jTable_tempStr_val.getModel();

        try {
            ////////////////////////////////////////////////////////////////////

            //////   set product name which will be removed from temp table and add to jCombo box
            int il = jTable_tempStr_val.getSelectedRow();
            TableModel model = jTable_tempStr_val.getModel();
            String product_Nm = model.getValueAt(il, 0).toString();
            // JOptionPane.showMessageDialog(null, product_Nm, " Product Name ", JOptionPane.INFORMATION_MESSAGE);
            jComboBox_prodtNm_From.addItem(product_Nm);
            jComboBox_prodtNm_to.addItem(product_Nm);

            //////////////    remove product item from temp Table
            int SelectedRowIndex = jTable_tempStr_val.getSelectedRow();
            tblModel.removeRow(SelectedRowIndex);

            ////////////////////////////////////////////////////////////////////
        } catch (Exception ea) {
            JOptionPane.showMessageDialog(this, " Configuration Failure!! ", "Error", JOptionPane.ERROR_MESSAGE);
        }

    }//GEN-LAST:event_jButton_removeRow_tempTblActionPerformed

    private void jComboBox_prodtNm_toItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox_prodtNm_toItemStateChanged

        Connection con;
        try {
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

            String ProdtNm = jComboBox_prodtNm_to.getSelectedItem().toString();
            PreparedStatement ps = con.prepareStatement("SELECT UNIT_TBL.UNIT_NAME,UNIT_SYMBOL,PRODUCT_TBL.PRODUCT_QTY FROM UNIT_TBL,PRODUCT_TBL WHERE UNIT_TBL.UNIT_IDNO=PRODUCT_TBL.UNIT_IDNO AND PRODUCT_TBL.PRODUCT_NM = ?");
            ps.setString(1, ProdtNm);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String unitNm = rs.getString("UNIT_SYMBOL");
                String present_qty = rs.getString("PRODUCT_QTY");
                jLabel_unitSymbl_to.setText(unitNm);
                jLabel_to_presnt_qty.setText("[" + present_qty + "]");
            }

            ////////////////////////////////////////////////////////////////////
            ////////////////////////////////////////////////////////////////////
            ////////////////////////////////////////////////////////////////////
            con.close();
        } catch (Exception ea) {
            ea.printStackTrace();
        }

    }//GEN-LAST:event_jComboBox_prodtNm_toItemStateChanged

    private void jTextField_mang_searchFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField_mang_searchFocusGained

        String srchBr = jTextField_mang_search.getText();
        if (srchBr.equals("Search by Manufacture Bill No. or Date-Time...")) {
            jTextField_mang_search.setText("");
            jTextField_mang_search.setForeground(new Color(153, 153, 153));
        }

    }//GEN-LAST:event_jTextField_mang_searchFocusGained

    private void jTextField_mang_searchFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField_mang_searchFocusLost

        String srchBr = jTextField_mang_search.getText();

        if (srchBr.equals("") || srchBr.equals("Search by Manufacture Bill No. or Date-Time...")) {
            jTextField_mang_search.setText("Search by Manufacture Bill No. or Date-Time...");
            jTextField_mang_search.setForeground(new Color(153, 153, 153));
        }

    }//GEN-LAST:event_jTextField_mang_searchFocusLost

    private void jButton_storeRaw_MatirialActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_storeRaw_MatirialActionPerformed

        String source_product = jComboBox_prodtNm_From.getSelectedItem().toString();
        String source_quantity = jTextField_RawQty_From.getText();
        String source_unit = jLabel_unitSymbl_from.getText();

        if (source_product.isEmpty() || source_product.equals("Select Product from Below") || source_quantity.isEmpty() || source_unit.isEmpty()) {
            ///////////////////////////////
            JOptionPane.showMessageDialog(null, "Some Field is Empty!!", "Error", JOptionPane.ERROR_MESSAGE);
            ///////////////////////////////
        } else {
            ///////////////////////////////
            ///////////////////////////////

            /////////////////////////
            /////////////////////////
            Connection con;
            try {
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

                String present_qty = "";
                PreparedStatement ps = con.prepareStatement("SELECT * FROM PRODUCT_TBL WHERE PRODUCT_NM = ?");
                ps.setString(1, source_product);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    present_qty = rs.getString("PRODUCT_QTY");
                }

                double Present_Qty = Double.valueOf(present_qty);
                double Select_source_quantity = Double.valueOf(source_quantity);

                if (Present_Qty > Select_source_quantity) {
                    ////////////////////////////////////////////////////////////
                    DefaultTableModel model = (DefaultTableModel) jTable_tempStr_val.getModel();

                    model.addRow(new Object[]{
                        source_product,
                        source_unit,
                        source_quantity,});

                    jComboBox_prodtNm_From.removeItem(source_product);
                    jComboBox_prodtNm_From.setSelectedIndex(0);
                    jComboBox_prodtNm_to.removeItem(source_product);
                    jComboBox_prodtNm_to.setSelectedIndex(0);
                    jTextField_RawQty_From.setText("");
                    jLabel_from_presnt_qty.setText(".....");
                    jLabel_unitSymbl_from.setText(".....");
                    ////////////////////////////////////////////////////////////
                } else {
                    ////////////////////////////////////////////////////////////
                    JOptionPane.showMessageDialog(this, "Low Quantity!!", "Warning", JOptionPane.WARNING_MESSAGE);
                    ////////////////////////////////////////////////////////////
                }

                ////////////////////////////////////////////////////////////////
                ////////////////////////////////////////////////////////////////
                ////////////////////////////////////////////////////////////////
                con.close();

            } catch (Exception ea) {
                ea.printStackTrace();
            }

            ///////////////////////////////
            ///////////////////////////////
        }

    }//GEN-LAST:event_jButton_storeRaw_MatirialActionPerformed

    private void jTextField_RawQty_FromKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField_RawQty_FromKeyPressed

        if (evt.getKeyChar() >= '0' && evt.getKeyChar() <= '9' || evt.getKeyCode() == KeyEvent.VK_BACK_SPACE || evt.getKeyChar() == KeyEvent.VK_DECIMAL || evt.getKeyChar() == KeyEvent.VK_PERIOD) {
            jTextField_RawQty_From.setEditable(true);
        } else {
            jTextField_RawQty_From.setEditable(false);
        }

    }//GEN-LAST:event_jTextField_RawQty_FromKeyPressed

    private void jTextField_RawQty_toKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField_RawQty_toKeyPressed

        if (evt.getKeyChar() >= '0' && evt.getKeyChar() <= '9' || evt.getKeyCode() == KeyEvent.VK_BACK_SPACE || evt.getKeyChar() == KeyEvent.VK_DECIMAL || evt.getKeyChar() == KeyEvent.VK_PERIOD) {
            jTextField_RawQty_to.setEditable(true);
        } else {
            jTextField_RawQty_to.setEditable(false);
        }

    }//GEN-LAST:event_jTextField_RawQty_toKeyPressed

    private void jTable_tempStr_valMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable_tempStr_valMouseClicked


    }//GEN-LAST:event_jTable_tempStr_valMouseClicked

    private void jButton_resetAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_resetAllActionPerformed

        try {
            //////////////////////////////////////////////////////////
            //////////////////////////////////////////////////////////

            //////////   generate production bill id
            generatte_production_id();

            ////////////////
            ////////////////
            DefaultTableModel tblmodel = (DefaultTableModel) jTable_stored_PurchaseBill.getModel();
            tblmodel.setRowCount(0);
            show_production();         ///////////////  for refresh table
            ////////////////
            ////////////////

            jComboBox_prodtNm_From.removeAllItems();
            jComboBox_prodtNm_to.removeAllItems();
            //////////  set value of jComboBox
            set_value_JcomboBox();

            jTextField_RawQty_From.setText("");
            jTextField_RawQty_to.setText("");
            jTextField_mang_search.setText("Search by Manufacture Bill No. or Date-Time...");

            //////////////////////   make table empty
            DefaultTableModel model = (DefaultTableModel) jTable_tempStr_val.getModel();
            model.setRowCount(0);

            jLabel_unitSymbl_from.setText(".....");
            jLabel_unitSymbl_to.setText(".....");
            jLabel_from_presnt_qty.setText(".....");
            jLabel_to_presnt_qty.setText(".....");

            //////////////////////////////////////////////////////////
            //////////////////////////////////////////////////////////
        } catch (Exception ea) {
            //////////////////////////////////////////////////////////
            JOptionPane.showMessageDialog(null, ea, "Error!", JOptionPane.ERROR_MESSAGE);
            //////////////////////////////////////////////////////////
        }

    }//GEN-LAST:event_jButton_resetAllActionPerformed

    private void jButton_ProductionDetailsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_ProductionDetailsActionPerformed

        int response = JOptionPane.showConfirmDialog(this, " Complete This Action ?? ", "Confirmation", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (response == JOptionPane.YES_OPTION) {
            // JOptionPane.showMessageDialog(this, "Yes Operation!!", "Status", JOptionPane.INFORMATION_MESSAGE);

            String production_idNo = jLabel_productionBill_idno.getText();
            DefaultTableModel model = (DefaultTableModel) jTable_tempStr_val.getModel();

            String distination_item = jComboBox_prodtNm_to.getSelectedItem().toString();
            String RawQty = jTextField_RawQty_to.getText();
            String unitSymbl_OfmugItem = jLabel_unitSymbl_to.getText();

            if (model.getRowCount() == 0 /*|| distination_item.isEmpty() || 
                    distination_item.equals("Select Product from Below") || RawQty.isEmpty() || 
                    unitSymbl_OfmugItem.isEmpty() || unitSymbl_OfmugItem.equals(".....") || production_idNo.isEmpty()*/) {
                ////////////////////////////////////////////////////////////////
                ////////////////////////////////////////////////////////////////
                JOptionPane.showMessageDialog(this, " Some field is Empty or incorrect!! ", "Warning", JOptionPane.WARNING_MESSAGE);
                ////////////////////////////////////////////////////////////////
                ////////////////////////////////////////////////////////////////
            } else {
                ////////////////////////////////////////////////////////////////
                ////////////////////////////////////////////////////////////////

                /////////////////////////
                /////////////////////////
                Connection con;
                try {
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

                    
                    ///  check this bill is already present or not!!
                    PreparedStatement src_key = con.prepareStatement("SELECT * FROM PRODUCTION_SOURCE_TBL WHERE PRODCTN_IDNO = ?");
                    src_key.setString(1, production_idNo);
                    int ii = src_key.executeUpdate();
                    if (ii > 0) {

                        JOptionPane.showMessageDialog(this, " Bill Already Present !! ", "Warning", JOptionPane.WARNING_MESSAGE);

                    } else {

                        
                        
                        
                        
                        
                        
                        
                        
                        //////////////////////////////////////////
                        /////   found present Date-Time
                        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                        LocalDateTime now = LocalDateTime.now();///fetch localDateTime from here
                        String presentDateTime = dtf.format(now);/////convert localtime to String with format 
                        //////////////////////////////////////////
                        
                        
                        
                        
                        
                        
                        
                        
                        
                        
                        //////////////////////////////////////////////////////////////////////////////////////
                        //////////////////////////////////////////////////////////////////////////////////////
                        // for new bill accept
                        //////////  get distrination product id_no
                        String disti_pro_Idno = "";
                        PreparedStatement dist_Entry_idno = con.prepareStatement("SELECT * FROM PRODUCT_TBL WHERE PRODUCT_NM = ?");
                        dist_Entry_idno.setString(1, distination_item);
                        ResultSet Res_dist_Entry_idno = dist_Entry_idno.executeQuery();
                        while (Res_dist_Entry_idno.next()) {
                            disti_pro_Idno = Res_dist_Entry_idno.getString("PROIDNO");
                        }
                        //////////////////////////////////////////
                        
                        
                        
                        
                        
                        
                        
                        
                        
                        
                        
                        
                        
                        
                        int i = 0 , j = 0;
                        
                        
                        if( distination_item.equals("Select Product from Below") || distination_item.isEmpty() ){
                            
                            ///////////////////////////////////////////////////////////////////
                            ///////////////////////////////////////////////////////////////////
                            ///////////////////////////////////////////////////////////////////
                            
                            ////// destrination product is not choosen so in this place make it ==> "None"
                            
                            disti_pro_Idno = "none";
                            RawQty = "00";
                            
                            ////    Insert values into Database
                            
                            //////////////////////////////////////////
                            //////    insert into Distrination table
                            PreparedStatement distrination_Entry = con.prepareStatement("INSERT INTO PRODUCTION_DESTINATION_TBL( PRODCTN_IDNO, PROIDNO, USED_QTY, DATE_TIME_PRDCTN) VALUES(?, ?, ?, ?)");
                            distrination_Entry.setString(1, production_idNo);
                            distrination_Entry.setString(2, disti_pro_Idno);
                            distrination_Entry.setString(3, RawQty);
                            distrination_Entry.setString(4, presentDateTime);
                            i = distrination_Entry.executeUpdate();
                            
                            
                            ///////////////////////////////////////////////////////////////////
                            ///////////////////////////////////////////////////////////////////
                            ///////////////////////////////////////////////////////////////////
                            
                        }
                        else{
                            
                            ///////////////////////////////////////////////////////////////////
                            ///////////////////////////////////////////////////////////////////
                            ///////////////////////////////////////////////////////////////////
                            
                            ////// destrination product is Selected


                            
                            ////////   maintain stock first
                            
                            
                            
                            ////////////////////////////////////////////////////////////
                            /////   update balance of distrination product quantity
                            double raw_Qty = Double.valueOf(RawQty);
                            double old_Qty = 0;
                            double update_Qty = 0;
                            //////////   get old quantity from DB
                            PreparedStatement ps = con.prepareStatement("SELECT * FROM PRODUCT_TBL WHERE PROIDNO = ?");
                            ps.setString(1, disti_pro_Idno);
                            ResultSet rs = ps.executeQuery();
                            while (rs.next()) {
                                old_Qty = Double.valueOf(rs.getString("PRODUCT_QTY"));
                            }
                            update_Qty = old_Qty + raw_Qty;
                            String Update_Qty = String.valueOf(String.format("%.2f", update_Qty));
                            //////////   update old quantity to DB
                            PreparedStatement ps1 = con.prepareStatement("UPDATE PRODUCT_TBL SET PRODUCT_QTY = ? WHERE PROIDNO = ?");
                            ps1.setString(1, Update_Qty);
                            ps1.setString(2, disti_pro_Idno);
                            j = ps1.executeUpdate();

                            if( j > 0 ){
                                
                                //////////////////////////////////////////
                                //////    insert into Distrination table
                                PreparedStatement distrination_Entry = con.prepareStatement("INSERT INTO PRODUCTION_DESTINATION_TBL( PRODCTN_IDNO, PROIDNO, USED_QTY, DATE_TIME_PRDCTN) VALUES(?, ?, ?, ?)");
                                distrination_Entry.setString(1, production_idNo);
                                distrination_Entry.setString(2, disti_pro_Idno);
                                distrination_Entry.setString(3, RawQty);
                                distrination_Entry.setString(4, presentDateTime);
                                i = distrination_Entry.executeUpdate();

                                
                            }else{
                                
                                JOptionPane.showMessageDialog(this, " Error Occurred While Updating Stock ", "Error", JOptionPane.ERROR_MESSAGE);
                                
                            }
                            
                            
                            
                            
                            
                            ///////////////////////////////////////////////////////////////////
                            ///////////////////////////////////////////////////////////////////
                            ///////////////////////////////////////////////////////////////////
                            
                        }
                        
                        
                        
                        
                        //////////////////////////////////////////////////////////////////////////////////////
                        //////////////////////////////////////////////////////////////////////////////////////
                        //////////////////////////////////////////////////////////////////////////////////////
                        //////////////////////////////////////////////////////////////////////////////////////
                        

                        
                        
                        
                        
                        
                        
                        
                        
                        
                        
                        
                        int l = 0, m = 0;

                        for (int k = 0; k < model.getRowCount(); k++) {

                            ////////////////////////////////////////////////////////
                            ////////////////////////////////////////////////////////
                            ////////////////////////////////////////////////////////
                            String source_product_Nm = (String) model.getValueAt(k, 0);
                            String source_product_Unit = (String) model.getValueAt(k, 1);
                            String source_product_Qty = (String) model.getValueAt(k, 2);
                            double Source_product_Qty = Double.valueOf(source_product_Qty);

                            String source_product_Idno = "";
                            double Source_product_Old_Qty = 0;

                            ////////////   get product idNo and Old Quantity
                            PreparedStatement ps2 = con.prepareStatement("SELECT * FROM PRODUCT_TBL WHERE PRODUCT_NM = ?");
                            ps2.setString(1, source_product_Nm);
                            ResultSet rs2 = ps2.executeQuery();
                            while (rs2.next()) {
                                source_product_Idno = rs2.getString("PROIDNO");
                                Source_product_Old_Qty = Double.valueOf(rs2.getString("PRODUCT_QTY"));
                            }

                            //////////////////////////////////////
                            //////   Insert into Source Table 
                            PreparedStatement source_Entry = con.prepareStatement("INSERT INTO PRODUCTION_SOURCE_TBL(PRODCTN_IDNO, PROIDNO, USED_QTY) VALUES(?, ?, ?)");
                            source_Entry.setString(1, production_idNo);
                            source_Entry.setString(2, source_product_Idno);
                            source_Entry.setString(3, source_product_Qty);
                            l = source_Entry.executeUpdate();

                            //////////////////////////////////////
                            ////// update table of product quantity
                            double latest_Qty_ofSource = Source_product_Old_Qty - Source_product_Qty;
                            PreparedStatement ps3 = con.prepareStatement("UPDATE PRODUCT_TBL SET PRODUCT_QTY = ? WHERE PROIDNO = ?");
                            ps3.setString(1, String.valueOf(String.format("%.2f", latest_Qty_ofSource)));
                            ps3.setString(2, source_product_Idno);
                            m = ps3.executeUpdate();

                            ////////////////////////////////////////////////////////
                            ////////////////////////////////////////////////////////
                            ////////////////////////////////////////////////////////
                        }

                        if (i > 0 && l > 0 && m > 0) {
                            ////////////////////////////////////////////////////////
                            JOptionPane.showMessageDialog(this, " Production Bill Entered Successfully Against Bill No. " + production_idNo, "Success", JOptionPane.INFORMATION_MESSAGE);
                            model.setRowCount(0);
                            jComboBox_prodtNm_From.removeAllItems();
                            jComboBox_prodtNm_to.removeAllItems();
                            //////////  set value of jComboBox
                            set_value_JcomboBox();
                            jTextField_RawQty_to.setText("");
                            jLabel_unitSymbl_to.setText(".....");
                            jLabel_to_presnt_qty.setText(".....");

                            ////////////////
                            ////////////////
                            DefaultTableModel tblmodel = (DefaultTableModel) jTable_stored_PurchaseBill.getModel();
                            tblmodel.setRowCount(0);
                            show_production();         ///////////////  for refresh table
                            ////////////////
                            ////////////////

                            ////////////////////////////////////////////////////////
                        } else {
                            JOptionPane.showMessageDialog(this, " Error Occurred ", "Error", JOptionPane.ERROR_MESSAGE);
                        }

                        //////////////////////////////////////////////////////////////////////////////////////
                        //////////////////////////////////////////////////////////////////////////////////////
                        //////////////////////////////////////////////////////////////////////////////////////
                        //////////////////////////////////////////////////////////////////////////////////////
                        //////////////////////////////////////////////////////////////////////////////////////
                        //////////////////////////////////////////////////////////////////////////////////////
                    }

                    ////////////////////////////////////////////////////////////
                    ////////////////////////////////////////////////////////////
                    ////////////////////////////////////////////////////////////
                    con.close();

                } catch (Exception ea) {
                    ea.printStackTrace();
                }

                ////////////////////////////////////////////////////////////////
                ////////////////////////////////////////////////////////////////
            }

        } else if (response == JOptionPane.NO_OPTION) {
            JOptionPane.showMessageDialog(this, " Operation Calcelled!!", "Status", JOptionPane.ERROR_MESSAGE);
        } else {
            System.out.println("No Operation Held");
        }

    }//GEN-LAST:event_jButton_ProductionDetailsActionPerformed

    private void jTable_stored_PurchaseBillMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable_stored_PurchaseBillMouseClicked

        int il = jTable_stored_PurchaseBill.getSelectedRow();
        TableModel model = jTable_stored_PurchaseBill.getModel();
        String Production_id = model.getValueAt(il, 0).toString();
        String Production_Raw = model.getValueAt(il, 1).toString();
        String Production_Mfg = model.getValueAt(il, 2).toString();
        String Production_Dttm = model.getValueAt(il, 3).toString();

        JOptionPane.showMessageDialog(this, "Production ID : " + Production_id + "\nRaw Material : " + Production_Raw + "\nMfg. Product : " + Production_Mfg + "\nDate-Time : " + Production_Dttm, "Description", JOptionPane.INFORMATION_MESSAGE);

        jLabel_productionBill_idno.setText(Production_id);

        ////// make raw product into table 'jTable_tempStr_val'    
        DefaultTableModel mdls = (DefaultTableModel) jTable_tempStr_val.getModel();
        mdls.setRowCount(0);

        String Prod_Nm = "", Prod_Qty = "", Prod_Unit = "", Product_Dtls = "";

        String[] rawGrp = Production_Raw.split(",");
        for (int i = 0; i < rawGrp.length; i++) {

            Product_Dtls = rawGrp[i];
            String[] prod_dt = Product_Dtls.split("->");

            Prod_Nm = prod_dt[0];
            Prod_Qty = prod_dt[1];

//            System.out.println(Product_Dtls+"=="+Prod_Nm+"=="+Prod_Qty);
            Connection con;
            try {
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

                PreparedStatement ps = con.prepareStatement("SELECT UNIT_TBL.UNIT_SYMBOL FROM UNIT_TBL,PRODUCT_TBL WHERE UNIT_TBL.UNIT_IDNO=PRODUCT_TBL.UNIT_IDNO AND PRODUCT_TBL.PRODUCT_NM = ?");
                ps.setString(1, Prod_Nm);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    Prod_Unit = rs.getString("UNIT_SYMBOL");
                }

                ///////////////////////
                ///////////////////////
                ///////////////////////
                con.close();

            } catch (Exception ea) {
                ea.printStackTrace();
            }

            mdls.addRow(new Object[]{
                Prod_Nm,
                Prod_Unit,
                Prod_Qty,});

            jComboBox_prodtNm_From.removeItem(Prod_Nm);
            jComboBox_prodtNm_to.removeItem(Prod_Nm);

        }

        ////// make product Material into Jcombo Box
        String[] Prod_Materl = Production_Mfg.split("->");

        if(Prod_Materl[0].equals("none")){
            jComboBox_prodtNm_to.setSelectedIndex(0);
        }else{
            jComboBox_prodtNm_to.setSelectedItem(Prod_Materl[0]);
        }
        
        jTextField_RawQty_to.setText(Prod_Materl[1]);


    }//GEN-LAST:event_jTable_stored_PurchaseBillMouseClicked

    private void jButton_Remove_prodctn_BillActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_Remove_prodctn_BillActionPerformed

        ////////////////////////////////////////////////////////////////////////
        ////////////////////////////////////////////////////////////////////////
        ////////////////////////////////////////////////////////////////////////
        int il = jTable_stored_PurchaseBill.getSelectedRow();
        TableModel model = jTable_stored_PurchaseBill.getModel();
        String Production_id = model.getValueAt(il, 0).toString();
        // String Production_Raw = model.getValueAt(il, 1).toString();
        // String Production_Mfg = model.getValueAt(il, 2).toString();
        // String Production_Dttm = model.getValueAt(il, 3).toString();

        /////////////////////////
        /////////////////////////
        Connection con;
        try {
            ///////////////////////////////
            ///////////////////////////////
            ///////////////////////////////
            try {
                Class.forName("oracle.jdbc.driver.OracleDriver");
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(Register_Frm.class.getName()).log(Level.SEVERE, null, ex);
            }
            con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "ACCOUNTMAINTAINER", "SWARNAVA");
            ////////////////////////////////////////////////////////////////////
            ////////////////////////////////////////////////////////////////////
            ////////////////////////////////////////////////////////////////////

//            String[] rawFirst = Production_Raw.split(",");
//            for(int i = 0; i < rawFirst.length; i++){
//                
//                String[] rawSecond = rawFirst[i].split("->");
//                
//                String material = rawSecond[0];
//                String rawQty = rawSecond[1];
//                
//                System.out.println(material+"-"+rawQty+"\n");
//                
//            }
            /////////   source table configaration
            PreparedStatement ps = con.prepareStatement("SELECT * FROM PRODUCTION_SOURCE_TBL WHERE PRODCTN_IDNO = ?");
            ps.setString(1, Production_id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String rawM_prodId = rs.getString("PROIDNO");
                double used_qty = Double.valueOf(rs.getString("USED_QTY"));

                PreparedStatement ps1 = con.prepareStatement("SELECT * FROM PRODUCT_TBL WHERE PROIDNO = ?");
                ps1.setString(1, rawM_prodId);
                ResultSet rs1 = ps1.executeQuery();
                while (rs1.next()) {
                    double old_qty = Double.valueOf(rs1.getString("PRODUCT_QTY"));

                    double new_qty = old_qty + used_qty;

                    PreparedStatement ps2 = con.prepareStatement("UPDATE PRODUCT_TBL SET PRODUCT_QTY = ? WHERE PROIDNO = ?");
                    ps2.setString(1, String.valueOf(String.format("%.2f", new_qty)));
                    ps2.setString(2, rawM_prodId);
                    int im = ps2.executeUpdate();
                    if (im > 0) {

                        PreparedStatement ps3 = con.prepareStatement("DELETE FROM PRODUCTION_SOURCE_TBL WHERE PRODCTN_IDNO = ? AND PROIDNO = ?");
                        ps3.setString(1, Production_id);
                        ps3.setString(2, rawM_prodId);
                        int ij = ps3.executeUpdate();
                        if (ij > 0) {
                            JOptionPane.showMessageDialog(this, "Successfully Removed!!", "Status", JOptionPane.INFORMATION_MESSAGE);
                        } else {
                            JOptionPane.showMessageDialog(this, "Error Occurred 11!!", "Status", JOptionPane.ERROR_MESSAGE);
                        }

                    } else {
                        JOptionPane.showMessageDialog(this, "Error Occurred 22!!", "Status", JOptionPane.ERROR_MESSAGE);
                    }

                }
            }

            /////////   Destrination table configaration
            PreparedStatement st = con.prepareStatement("SELECT * FROM PRODUCTION_DESTINATION_TBL WHERE PRODCTN_IDNO = ?");
            st.setString(1, Production_id);
            ResultSet rm = st.executeQuery();
            while (rm.next()) {

                String desti_prod_id = rm.getString("PROIDNO");
                double used_desti_qty = Double.valueOf(rm.getString("USED_QTY"));

                PreparedStatement st1 = con.prepareStatement("SELECT * FROM PRODUCT_TBL WHERE PROIDNO = ?");
                st1.setString(1, desti_prod_id);
                ResultSet rm1 = st1.executeQuery();
                while (rm1.next()) {

                    double old_desti_qty = Double.valueOf(rm1.getString("PRODUCT_QTY"));

                    double new_desti_qty = old_desti_qty - used_desti_qty;

                    PreparedStatement st2 = con.prepareStatement("UPDATE PRODUCT_TBL SET PRODUCT_QTY = ? WHERE PROIDNO = ?");
                    st2.setString(1, String.valueOf(String.format("%.2f", new_desti_qty)));
                    st2.setString(2, desti_prod_id);
                    int im = st2.executeUpdate();
                    if (im > 0) {

                        PreparedStatement st3 = con.prepareStatement("DELETE FROM PRODUCTION_DESTINATION_TBL WHERE PRODCTN_IDNO = ? AND PROIDNO = ?");
                        st3.setString(1, Production_id);
                        st3.setString(2, desti_prod_id);
                        int io = st3.executeUpdate();
                        if (io > 0) {
                            JOptionPane.showMessageDialog(this, "Product Removed !!", "Status", JOptionPane.INFORMATION_MESSAGE);
                        } else {
                            JOptionPane.showMessageDialog(this, "Error Ocurred 33 !!", "Status", JOptionPane.ERROR_MESSAGE);
                        }

                    } else {
                        JOptionPane.showMessageDialog(this, "Error Ocurred 44 !!", "Status", JOptionPane.ERROR_MESSAGE);
                    }

                }

            }

            JOptionPane.showMessageDialog(this, "All Items are Removed!!", "Status", JOptionPane.INFORMATION_MESSAGE);
            ////////////////
            ////////////////
            DefaultTableModel tblmodel = (DefaultTableModel) jTable_stored_PurchaseBill.getModel();
            tblmodel.setRowCount(0);
            show_production();         ///////////////  for refresh table
            ////////////////
            ////////////////

            con.close();
            ////////////////////////////////////////////////////////////////////
            ////////////////////////////////////////////////////////////////////
        } catch (Exception ea) {
            ea.printStackTrace();
        }

        ////////////////////////////////////////////////////////////////////////
        ////////////////////////////////////////////////////////////////////////
        ////////////////////////////////////////////////////////////////////////
        ////////////////////////////////////////////////////////////////////////
        ////////////////////////////////////////////////////////////////////////

    }//GEN-LAST:event_jButton_Remove_prodctn_BillActionPerformed

    private void jButton_searchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_searchActionPerformed

        String searchBar = jTextField_mang_search.getText();

        if (searchBar.equalsIgnoreCase(" Search by Product Name or Purchase Bill No...") || searchBar.trim().equals("")) {
            JOptionPane.showMessageDialog(null, " Please Search Some Thing... ", "Warning", JOptionPane.WARNING_MESSAGE);
        } else {
            ////////////////
            ////////////////
            DefaultTableModel model = (DefaultTableModel) jTable_stored_PurchaseBill.getModel();
            model.setRowCount(0);
            show_production_Search();         ///////////////  for refresh table
            ////////////////
            ////////////////
        }

    }//GEN-LAST:event_jButton_searchActionPerformed

    private void jLabel_sellMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel_sellMouseClicked

        account_Sell form = new account_Sell();
        form.setVisible(true);
        form.pack();
        form.setLocationRelativeTo(null);
        //close the current form (login form)
        this.dispose();

    }//GEN-LAST:event_jLabel_sellMouseClicked

    private void Btn_Download_productnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Btn_Download_productnActionPerformed

        String getPath = "";

        //////////////////////////////////////////////////////////////////////////
        //////////////////////////////////////////////////////////////////////////
        Connection con;
        try {

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

            PreparedStatement ps = con.prepareStatement("SELECT STORE_TABLE_DATA_PATH FROM OTHER_NECEARY_TABLE");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {

                getPath = rs.getString("STORE_TABLE_DATA_PATH");

            }

            ///////////////////////////////
            ///////////////////////////////
            ///////////////////////////////
            con.close();

        } catch (Exception ea) {
            ea.printStackTrace();
        }

        ////////////////////////////////////////////////////////////////////////
        ////////////////////////////////////////////////////////////////////////
        ///////////     check the file path is valid or not!!!
        String pathsss = getPath + "\\";
        Path FullPath = Paths.get(pathsss);
        if (Files.exists(FullPath) == true) {
            // System.out.println("Valid");

            ////////////////////////////////////////////////////////////////////
            ////////////////////////////////////////////////////////////////////
            ////////////////////////////////////////////////////////////////////
            ////////////////////////////////////////////////////////////////////
            /////////////////////////////////
            ///////   give name of file name
            /////////////////////////////////
            java.util.Random r = new java.util.Random();
            int x = r.nextInt(999444899);
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            LocalDateTime now = LocalDateTime.now();///fetch localDateTime from here
            String presentDateTime = dtf.format(now);/////convert localtime to String with format
            /////and formatted by dtf
            String[] dttm = presentDateTime.split(" ");
            String onlyDt = dttm[0];
            String onlyTm = dttm[1];
            String modifyTm = onlyTm.replace(":", "-");
            String Production_ID = "Production_Table-" + x + "ID-at-" + onlyDt + "DT__" + modifyTm + "TM.xls";         //////// for excel file
            /////////////////////////////////
            /////////////////////////////////

            ////////////////////////
            ///////  url path + file name
            ////////////////////////
            String pathWithFileName = getPath + "\\" + Production_ID;

            //File PathWithFileName = new File(pathWithFileName);
            File file = new File(pathWithFileName);

            //////////////////////// export file into .xls format
            try {
                TableModel model = jTable_stored_PurchaseBill.getModel();
                FileWriter excel = new FileWriter(file);

                for (int i = 0; i < model.getColumnCount(); i++) {
                    excel.write(model.getColumnName(i) + "\t");
                }

                excel.write("\n");

                for (int i = 0; i < model.getRowCount(); i++) {
                    for (int j = 0; j < model.getColumnCount(); j++) {
                        excel.write(model.getValueAt(i, j).toString() + "\t");
                    }
                    excel.write("\n");
                }

                excel.close();
                JOptionPane.showMessageDialog(this, "File Saved Successfully...", "Status", JOptionPane.INFORMATION_MESSAGE);

            } catch (IOException e) {
                System.out.println(e);
            }

            ////////////////////////////////////////////////////////////////////
            ////////////////////////////////////////////////////////////////////
            ////////////////////////////////////////////////////////////////////
            ////////////////////////////////////////////////////////////////////
        } else {
            System.out.println("Invalid");
            JOptionPane.showMessageDialog(this, "Invalid Path or Path not Choosen Yet!!", "Error", JOptionPane.ERROR_MESSAGE);
        }


    }//GEN-LAST:event_Btn_Download_productnActionPerformed

    private void jLabel_profileMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel_profileMouseClicked

        account_Profile form = new account_Profile();
        form.setVisible(true);
        form.pack();
        form.setLocationRelativeTo(null);
        //close the current form (login form)
        this.dispose();

    }//GEN-LAST:event_jLabel_profileMouseClicked

    private void jLabel_ExpenditureMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel_ExpenditureMouseClicked

        account_Expenditure form = new account_Expenditure();
        form.setVisible(true);
        form.pack();
        form.setLocationRelativeTo(null);
        //close the current form
        this.dispose();

    }//GEN-LAST:event_jLabel_ExpenditureMouseClicked

    private void jLabel_billMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel_billMouseClicked

        account_Search form = new account_Search();
        form.setVisible(true);
        form.pack();
        form.setLocationRelativeTo(null);
        //close the current form
        this.dispose();

    }//GEN-LAST:event_jLabel_billMouseClicked

    private void jButton_Update_ProductionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_Update_ProductionActionPerformed

        /////////////////////////
        /////////////////////////
        Connection con;
        try {
            ///////////////////////////////
            ///////////////////////////////

            String production_idNo = jLabel_productionBill_idno.getText();
            
            

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

            
            
            
            
            
            
            
            
            
            
            
            //////////  create arraylist for which items are already present in database against the bill no.
            ArrayList<String> product_items_old = new ArrayList<String>();
            PreparedStatement al_pi = con.prepareStatement("SELECT * FROM PRODUCTION_SOURCE_TBL WHERE PRODCTN_IDNO = ?");
            al_pi.setString(1, production_idNo);
            ResultSet rs_Pi = al_pi.executeQuery();
            while(rs_Pi.next()){
                
                product_items_old.add(rs_Pi.getString("PROIDNO"));
                
            }
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            PreparedStatement src_key = con.prepareStatement("SELECT * FROM PRODUCTION_SOURCE_TBL WHERE PRODCTN_IDNO = ?");
            src_key.setString(1, production_idNo);
            int ii = src_key.executeUpdate();
            if (ii > 0) {

                
                // JOptionPane.showMessageDialog(this, " Bill Already Present !! ", "info", JOptionPane.INFORMATION_MESSAGE);
                
                
                
                
                DefaultTableModel model = (DefaultTableModel) jTable_tempStr_val.getModel();

                String distination_item = jComboBox_prodtNm_to.getSelectedItem().toString();
                String RawQty = jTextField_RawQty_to.getText();
                String unitSymbl_OfmugItem = jLabel_unitSymbl_to.getText();
                
                    

                ///////////////////////////////////////////////////////////////////////////////////
                ///////////////////////////////////////////////////////////////////////////////////
                ///////////////////////////////////////////////////////////////////////////////////
                ///////////////////////////////////////////////////////////////////////////////////
                ///////////////////////////////////////////////////////////////////////////////////
                ///////////////////////////////////////////////////////////////////////////////////
                ///////////////////////////////////////////////////////////////////////////////////
                ///////////////////////////////////////////////////////////////////////////////////
                ///////////////////////////////////////////////////////////////////////////////////
                ///////////////////////////////////////////////////////////////////////////////////

                ///// *****************************************************************************
                ///// &&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&
                ///// #############################################################################
                ///// *****************************************************************************
                ///// &&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&
                ///// #############################################################################
                ///// *****************************************************************************
                ///// &&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&
                ///// #############################################################################
                ///// *****************************************************************************
                ///// &&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&
                ///// #############################################################################

                //////  Destrination Table update

                if( distination_item.isEmpty() || distination_item.equals("Select Product from Below") ){
                    
                    
                    ///////   no item is selected when update the bill
                    
                    ///////  check previously any product is selected or not???
                    PreparedStatement dt_updt = con.prepareStatement("SELECT * FROM PRODUCTION_DESTINATION_TBL WHERE PRODCTN_IDNO = ?");
                    dt_updt.setString(1, production_idNo);
                    ResultSet rs_dt_updt = dt_updt.executeQuery();
                    while(rs_dt_updt.next()){
                        
                        
                        
                        /////// get previous product idno. check if it is present or not
                        String destrinatn_product_id = rs_dt_updt.getString("PROIDNO");
                        double destrinatn_product_UsedQTY = Double.valueOf(rs_dt_updt.getString("USED_QTY"));
                        
                        if(destrinatn_product_id.equals("none")){
                            
                            ///////////////  previously also no product was setted....
                            /////   previous == present ==> No item is selected for Destrination Table
                            /////   so no update or operation is required...
                            
                        }
                        else{
                            
                            ///////////////  any product was Selected previously....
                            
                            ///  get quantity from stock
                            double old_desti_stock_productTbl = 00;
                            PreparedStatement pms = con.prepareStatement("SELECT * FROM PRODUCT_TBL WHERE PROIDNO = ?");
                            pms.setString(1, destrinatn_product_id);
                            ResultSet rs_pms = pms.executeQuery();
                            while(rs_pms.next()){
                                old_desti_stock_productTbl = Double.valueOf(rs_pms.getString("PRODUCT_QTY"));
                            }
                            
                            //////   add destrination with Stock quantity....
                            double new_updated_product_Qty = old_desti_stock_productTbl - destrinatn_product_UsedQTY ;
                            
                            ////  update, add to stock with unused quantity...
                            PreparedStatement pms1 = con.prepareStatement("UPDATE PRODUCT_TBL SET PRODUCT_QTY = ? WHERE PROIDNO = ?");
                            pms1.setString(1, String.valueOf(new_updated_product_Qty));
                            pms1.setString(2, destrinatn_product_id);
                            int imm = pms1.executeUpdate();
                            if( imm > 0 ){
                                
                                ////////   after stock update complete
                                
                                ////////   destrination product update and product is to be removed...
                                
                                PreparedStatement pms2 = con.prepareStatement("UPDATE PRODUCTION_DESTINATION_TBL SET PROIDNO = ?, USED_QTY = ? WHERE PRODCTN_IDNO = ?");
                                pms2.setString(1, "none");
                                pms2.setString(2, "00");
                                pms2.setString(3, production_idNo);
                                
                                int immm1 = pms2.executeUpdate();
                                
                                System.out.println(immm1);
                                
                                
                            }else{
                                
                                JOptionPane.showMessageDialog(this, "Error Occurred!! while updating Quantity of Stock...", "Error", JOptionPane.ERROR_MESSAGE);
                                
                            }
                            
                        }
                        
                    }
                    
                    
                }
                else
                {
                    
                    
                    
                    ////////   any product is selected For destrination product
                    
                    ////////  now find the id of "distination_item"
                    String distination_item_idno = "";
                    PreparedStatement ppe = con.prepareStatement("SELECT * FROM PRODUCT_TBL WHERE PRODUCT_NM = ?");
                    ppe.setString(1, distination_item);
                    ResultSet rs_ppe = ppe.executeQuery();
                    while(rs_ppe.next()){
                        
                        distination_item_idno = rs_ppe.getString("PROIDNO");
                        
                    }
                    
                    //////   get previous product details 
                    //////   if it is selected or not
                    //////   same or not 
                    /////    quantity same or not
                    
                    String old_Product_idno = "";
                    double old_Product_Qty = 00;
                    PreparedStatement ppe1 = con.prepareStatement("SELECT * FROM PRODUCTION_DESTINATION_TBL WHERE PRODCTN_IDNO = ?");
                    ppe1.setString(1, production_idNo);
                    ResultSet rs_ppe1 = ppe1.executeQuery();
                    while(rs_ppe1.next()){
                        
                        old_Product_idno = rs_ppe1.getString("PROIDNO");
                        old_Product_Qty = Double.valueOf(rs_ppe1.getString("USED_QTY"));
                        
                    }
                    
                    
                    
                    
                    
                    
                    
                    ////////   if no product is selected previously for destrination product
                    
                    if( old_Product_idno.equals("none") || old_Product_idno.isEmpty() ){
                        
                        
                        /////  now only add product to PRODUCTION_DESTINATION_TBL and maintain stock in PRODUCT_TBL
                        
                        ///  first add distrination product
                        PreparedStatement ppe2 = con.prepareStatement("UPDATE PRODUCTION_DESTINATION_TBL SET PROIDNO = ?, USED_QTY = ? WHERE PRODCTN_IDNO = ?");
                        ppe2.setString(1, distination_item_idno);
                        ppe2.setString(2, RawQty);
                        ppe2.setString(3, production_idNo);
                        int i_ppe2 = ppe2.executeUpdate();
                        
                        if( i_ppe2 > 0 ){
                            
                            
                            /////  get the quantity of the stock
                            double stored_qty = 00;
                            PreparedStatement ppe3 = con.prepareStatement("SELECT * FROM PRODUCT_TBL WHERE PROIDNO = ?");
                            ppe3.setString(1, distination_item_idno);
                            ResultSet rs_ppe3 = ppe3.executeQuery();
                            while(rs_ppe3.next()){
                                
                                stored_qty = Double.valueOf(rs_ppe3.getString("PRODUCT_QTY"));
                                
                            }
                            
                            double update_stored_desti_qty = stored_qty + Double.valueOf(RawQty) ;
                            
                            PreparedStatement ppe4 = con.prepareStatement("UPDATE PRODUCT_TBL SET PRODUCT_QTY = ? WHERE PROIDNO = ?");
                            ppe4.setString(1, String.valueOf(update_stored_desti_qty));
                            ppe4.setString(2, distination_item_idno);
                            int i_ppe4 = ppe4.executeUpdate();
                            
                            System.out.println(i_ppe4);
                            
                            System.out.println("Stock Updated...!!");
                            
                        }
                        else{
                            
                            JOptionPane.showMessageDialog(this, "Error Occurred!! Updating Destrination Product!!", "Error", JOptionPane.ERROR_MESSAGE);
                            
                        }
                        
                        
                        
                        
                    }
                    else{
                        
                        
                        /////  previously some product was choosen
                        
                        /////  if new product is same with stored product
                        if( old_Product_idno.equals(distination_item_idno) )
                        {
                            
                            
                            ////  check the quantity equal or increased or decreased...
                            
                            //  if Equal
                            if( old_Product_Qty == Double.valueOf(RawQty) ){
                                
                                //////  no operation is required....
                                
                            }
                            else if( old_Product_Qty > Double.valueOf(RawQty) ){
                                
                                
                                double get_diff = old_Product_Qty - Double.valueOf(RawQty) ;
                                
                                ////  get old product quantity to update stock
                                double old_pro_qty = 00;
                                PreparedStatement ppe5 = con.prepareStatement("SELECT * FROM PRODUCT_TBL WHERE PROIDNO = ?");
                                ppe5.setString(1, distination_item_idno);
                                ResultSet rs_ppe5 = ppe5.executeQuery();
                                while(rs_ppe5.next()){
                                    
                                    old_pro_qty = Double.valueOf(rs_ppe5.getString("PRODUCT_QTY"));
                                    
                                }
                                
                                double get_updated_qty = old_pro_qty + get_diff ;
                                
                                PreparedStatement ppe6 = con.prepareStatement("UPDATE PRODUCT_TBL SET PRODUCT_QTY = ? WHERE PROIDNO = ?");
                                ppe6.setString(1, String.valueOf(get_updated_qty));
                                ppe6.setString(2, distination_item_idno);
                                int i_ppe6 = ppe6.executeUpdate();
                                
                                System.out.println(i_ppe6);
                                
                                System.out.println("Stock Updated...!!!");
                                
                                
                            }
                            else if( old_Product_Qty < Double.valueOf(RawQty) ){
                                
                                
                                double get_diff = Double.valueOf(RawQty) - old_Product_Qty ;
                                
                                ////  get old product quantity to update stock
                                double old_pro_qty = 00;
                                PreparedStatement ppe5 = con.prepareStatement("SELECT * FROM PRODUCT_TBL WHERE PROIDNO = ?");
                                ppe5.setString(1, distination_item_idno);
                                ResultSet rs_ppe5 = ppe5.executeQuery();
                                while(rs_ppe5.next()){
                                    
                                    old_pro_qty = Double.valueOf(rs_ppe5.getString("PRODUCT_QTY"));
                                    
                                }
                                
                                double get_updated_qty = old_pro_qty - get_diff ;
                                
                                PreparedStatement ppe6 = con.prepareStatement("UPDATE PRODUCT_TBL SET PRODUCT_QTY = ? WHERE PROIDNO = ?");
                                ppe6.setString(1, String.valueOf(get_updated_qty));
                                ppe6.setString(2, distination_item_idno);
                                int i_ppe6 = ppe6.executeUpdate();
                                
                                System.out.println(i_ppe6);
                                
                                System.out.println("Stock Updated...!!!");
                                
                                
                            }
                            
                            
                            
                            
                            
                            
                        }
                        else{
                            
                            //////  different product is choosen when update
                            
                            //////  get details of Previous Product which is Set Before
                            String old_desti_proIdno = "";
                            double old_desti_proQty = 00;
                            PreparedStatement ppe7 = con.prepareStatement("SELECT * FROM PRODUCTION_DESTINATION_TBL WHERE PRODCTN_IDNO = ?");
                            ppe7.setString(1, production_idNo);
                            ResultSet rs_ppe7 = ppe7.executeQuery();
                            while(rs_ppe7.next()){
                                
                                old_desti_proIdno = rs_ppe7.getString("PROIDNO");
                                old_desti_proQty = Double.valueOf(rs_ppe7.getString("USED_QTY"));
                                
                            }
                            
                            
                            
                            if( old_desti_proIdno.equals("none") || old_desti_proIdno.isEmpty() ){
                                
                                
                                ///////  no product choose before
                                
                                
                            }else{
                                
                                //////   some product was choosen
                                
                                //////   stock maintain of old products
                                //////   get the stock quantity details
                                double old_qtyis = 00;
                                PreparedStatement ppe8 = con.prepareStatement("SELECT * FROM PRODUCT_TBL WHERE PROIDNO = ?");
                                ppe8.setString(1, old_desti_proIdno);
                                ResultSet rs_ppe8 = ppe8.executeQuery();
                                while(rs_ppe8.next()){
                                    
                                    old_qtyis = Double.valueOf(rs_ppe8.getString("PRODUCT_QTY"));
                                    
                                }
                                
                                double update_old_qtys = old_qtyis + old_desti_proQty ;
                                /////  update product table for stock update
                                PreparedStatement ppe9 = con.prepareStatement("UPDATE PRODUCT_TBL SET PRODUCT_QTY = ? WHERE PROIDNO = ?");
                                ppe9.setString(1, String.valueOf(update_old_qtys));
                                ppe9.setString(2, old_desti_proIdno);
                                int i_ppe9 = ppe9.executeUpdate();
                                
                                if( i_ppe9 > 0 ){
                                    
                                    /////// old product stock maintanance complete....
                                    
                                    /*
                                    
                                    String distination_item 
                                    String RawQty 
                                    
                                    */
                                    ///// get new product id
                                    String distination_item_iDnO = "";
                                    PreparedStatement ppe10 = con.prepareStatement("SELECT * FROM PRODUCT_TBL WHERE PRODUCT_NM = ?");
                                    ppe10.setString(1, distination_item);
                                    ResultSet rs_ppe10 = ppe10.executeQuery();
                                    while(rs_ppe10.next()){
                                        distination_item_iDnO = rs_ppe10.getString("PROIDNO");
                                    }
                                    
                                    
                                    ///////  now new product stock maintanance start....
                                    double new_product_qty = 00;
                                    PreparedStatement ppe11 = con.prepareStatement("SELECT * FROM PRODUCT_TBL WHERE PROIDNO = ?");
                                    ppe11.setString(1, distination_item_iDnO);
                                    ResultSet rs_ppe11 = ppe11.executeQuery();
                                    while(rs_ppe11.next()){
                                        new_product_qty = Double.valueOf(rs_ppe11.getString("PRODUCT_QTY"));
                                    }
                                    
                                    double update_new_product_qty = new_product_qty - Double.valueOf(RawQty);
                                    
                                    PreparedStatement ppe12 = con.prepareStatement("UPDATE PRODUCT_TBL SET PRODUCT_QTY = ? WHERE PROIDNO = ?");
                                    ppe12.setString(1, String.valueOf(update_new_product_qty));
                                    ppe12.setString(2, distination_item_iDnO);
                                    int i_ppe12 = ppe12.executeUpdate();
                                    
                                    if( i_ppe12 > 0 ){
                                        
                                        
                                        PreparedStatement ppe13 = con.prepareStatement("UPDATE PRODUCTION_DESTINATION_TBL SET PROIDNO = ?, USED_QTY = ? WHERE PRODCTN_IDNO = ?");
                                        ppe13.setString(1, distination_item_iDnO);
                                        ppe13.setString(2, RawQty);
                                        ppe13.setString(3, production_idNo);
                                        int i_ppe13 = ppe13.executeUpdate();
                                        
                                        System.out.println(i_ppe13);
                                        
                                        System.out.println(" New Updated Destrination Item Allocated!! ");
                                        
                                    }
                                    else{
                                        JOptionPane.showMessageDialog(this, "Error Occurred while Update Stock of new Destrination Product !!", "Error", JOptionPane.ERROR_MESSAGE);
                                    }
                                    
                                    
                                    
                                }
                                else{
                                    JOptionPane.showMessageDialog(this, "Error Occurred while Stock Maintanance of Destrination Product !!", "Error", JOptionPane.ERROR_MESSAGE);
                                }   
                                
                            }
                            
                        }
                        
                    }
                    
                }
                
                
                
                
                
                
                
                
                
                
                
                
                
                
                
                
                
                
                
                
                    
                    
                    ///////////////////////////////////////////////////////////////////////////////////
                    ///////////////////////////////////////////////////////////////////////////////////
                    ///////////////////////////////////////////////////////////////////////////////////
                    ///////////////////////////////////////////////////////////////////////////////////
                    ///////////////////////////////////////////////////////////////////////////////////
                    ///////////////////////////////////////////////////////////////////////////////////
                    ///////////////////////////////////////////////////////////////////////////////////
                    ///////////////////////////////////////////////////////////////////////////////////
                    ///////////////////////////////////////////////////////////////////////////////////
                    ///////////////////////////////////////////////////////////////////////////////////
                    
                    ///// *****************************************************************************
                    ///// &&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&
                    ///// #############################################################################
                    ///// *****************************************************************************
                    ///// &&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&
                    ///// #############################################################################
                    ///// *****************************************************************************
                    ///// &&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&
                    ///// #############################################################################
                    ///// *****************************************************************************
                    ///// &&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&
                    ///// #############################################################################
                    
                    //////  Source Table update
                    
                    
                
                if( model.getRowCount() == 0 ){
                    
                    JOptionPane.showMessageDialog(this, " Some Field is Empty !! ", "Warning", JOptionPane.WARNING_MESSAGE);
                    
                }
                else{
                    
                    ////////////////////////////////////////////////////////////
                    ////////////////////////////////////////////////////////////
                    ////////////////////////////////////////////////////////////
                    ////////////////////////////////////////////////////////////
                    
                    ////////     ***********************************************     ////////
                    ////////     ***********************************************     ////////
                    ////////     ***********************************************     ////////
                    ////////     ***********************************************     ////////
                    ////////     ***********************************************     ////////
                    
                    //////////  product_source_tbl      ############################
                    //////////  ####################################################
                    //////////  ####################################################
                    //////////  ####################################################
                    //////////  ####################################################
                    //////////  ####################################################
                    
                    for ( int i = 0 ; i < model.getRowCount() ; i ++ ) {
                        
                        
                        String source_product_Nm = (String)model.getValueAt(i, 0);
                        String source_product_Unit = (String)model.getValueAt(i, 1);
                        String source_product_Qty = (String)model.getValueAt(i, 2);
                        double Source_product_Qty = Double.valueOf(source_product_Qty);
                        
                        ///////  search product id no from tempurary table
                        String srch_product_idno = "";
                        PreparedStatement ps = con.prepareStatement("SELECT * FROM PRODUCT_TBL WHERE PRODUCT_NM = ?");
                        ps.setString(1, source_product_Nm);
                        ResultSet rs = ps.executeQuery();
                        while(rs.next()){
                            srch_product_idno = rs.getString("PROIDNO");
                        }
                        
                        
                        
                        ///////   Q> search this product is already present in old bill or not ??
                        PreparedStatement ps1 = con.prepareStatement("SELECT * FROM PRODUCTION_SOURCE_TBL WHERE PRODCTN_IDNO = ? AND PROIDNO = ?");
                        ps1.setString(1, production_idNo);
                        ps1.setString(2, srch_product_idno);
                        int srch_idin = ps1.executeUpdate();
                        if( srch_idin > 0 ){
                            
                            ///////////////////////////////////////////////////////////////
                            ///////////////////////////////////////////////////////////////
                            ///////////////////////////////////////////////////////////////
                            
                            ///////  Ans> this product was already present in old bill  
                            
                            //////  get old quantity from the bill
                            double old_quatity = 00;
                            PreparedStatement sts = con.prepareStatement("SELECT * FROM PRODUCTION_SOURCE_TBL WHERE PRODCTN_IDNO = ? AND PROIDNO = ?");
                            sts.setString(1, production_idNo);
                            sts.setString(2, srch_product_idno);
                            ResultSet rss = sts.executeQuery();
                            while(rss.next()){
                                
                                old_quatity = Double.valueOf(rss.getString("USED_QTY"));
                                
                            }
                            
                            
                            // Source_product_Qty    =====>    present quantity      ( [ { may be modified or be same } ] )
                            // old_quatity    =====>    Get from the Database
                            
                            /////  vary the quantity between old and newly setted
                            if( old_quatity > Source_product_Qty )
                            {
                                
                                double diff_between_old_new = old_quatity - Source_product_Qty ;
                                
                                /////  difference quantity must be add into stock   --->   "PRODUCT_TBL"
                                
                                /////  get value of present Quantity of product From stock
                                double qty_stock_old = 00;
                                PreparedStatement sts1 = con.prepareStatement("SELECT * FROM PRODUCT_TBL WHERE PROIDNO = ?");
                                sts1.setString(1, srch_product_idno);
                                ResultSet rss1 = sts1.executeQuery();
                                while(rss1.next()){
                                    
                                    qty_stock_old = Double.valueOf(rss1.getString("PRODUCT_QTY"));
                                    
                                }
                                
                                double update_quantity_in_stock = qty_stock_old + diff_between_old_new ;
                                
                                PreparedStatement sts2 = con.prepareStatement("UPDATE PRODUCT_TBL SET PRODUCT_QTY = ? WHERE PROIDNO = ?");
                                sts2.setString(1, String.valueOf(update_quantity_in_stock));
                                sts2.setString(2, srch_product_idno);
                                int imm = sts2.executeUpdate();
                                if( imm > 0 ){
                                    
                                    /////  here you reached means stock is updated for old product
                                    
                                    PreparedStatement sts3 = con.prepareStatement("UPDATE PRODUCTION_SOURCE_TBL SET USED_QTY = ? WHERE PRODCTN_IDNO = ? AND PROIDNO = ?");
                                    sts3.setString(1, source_product_Qty);
                                    sts3.setString(2, production_idNo);
                                    sts3.setString(3, srch_product_idno);
                                    int ic = sts3.executeUpdate();
                                    
                                    System.out.println(ic);
                                    
                                    
                                    
                                }else{
                                    
                                    JOptionPane.showMessageDialog(this, "Error Occurred Updating Stock for the Product "+source_product_Nm, "Error", JOptionPane.ERROR_MESSAGE);
                                    
                                }
                                
                                //////////// **************---------------************** /////////////////
                                //////////// **************---------------************** /////////////////
                                //////////// **************---------------************** /////////////////
                                //////////// **************---------------************** /////////////////
                                //////////// **************---------------************** /////////////////
                                
                                product_items_old.remove(srch_product_idno);
                                
                                //////////// **************---------------************** /////////////////
                                
                            }
                            else if( old_quatity < Source_product_Qty )
                            {
                                
                                
                                
                                double diff_between_old_new = Source_product_Qty - old_quatity ;
                                
                                /////  difference quantity must be add into stock   --->   "PRODUCT_TBL"
                                
                                /////  get value of present Quantity of product From stock
                                double qty_stock_old = 00;
                                PreparedStatement sts1 = con.prepareStatement("SELECT * FROM PRODUCT_TBL WHERE PROIDNO = ?");
                                sts1.setString(1, srch_product_idno);
                                ResultSet rss1 = sts1.executeQuery();
                                while(rss1.next()){
                                    
                                    qty_stock_old = Double.valueOf(rss1.getString("PRODUCT_QTY"));
                                    
                                }
                                
                                double update_quantity_in_stock = qty_stock_old - diff_between_old_new ;
                                
                                PreparedStatement sts2 = con.prepareStatement("UPDATE PRODUCT_TBL SET PRODUCT_QTY = ? WHERE PROIDNO = ?");
                                sts2.setString(1, String.valueOf(update_quantity_in_stock));
                                sts2.setString(2, srch_product_idno);
                                int imm = sts2.executeUpdate();
                                if( imm > 0 ){
                                    
                                    /////  here you reached means stock is updated for old product
                                    
                                    PreparedStatement sts3 = con.prepareStatement("UPDATE PRODUCTION_SOURCE_TBL SET USED_QTY = ? WHERE PRODCTN_IDNO = ? AND PROIDNO = ?");
                                    sts3.setString(1, source_product_Qty);
                                    sts3.setString(2, production_idNo);
                                    sts3.setString(3, srch_product_idno);
                                    int ic = sts3.executeUpdate();
                                    
                                    System.out.println(ic);
                                    
                                    
                                }else{
                                    
                                    JOptionPane.showMessageDialog(this, "Error Occurred Updating Stock for the Product "+source_product_Nm, "Error", JOptionPane.ERROR_MESSAGE);
                                    
                                }
                                
                                
                                //////////// **************---------------************** /////////////////
                                //////////// **************---------------************** /////////////////
                                //////////// **************---------------************** /////////////////
                                //////////// **************---------------************** /////////////////
                                //////////// **************---------------************** /////////////////
                                
                                product_items_old.remove(srch_product_idno);
                                
                                //////////// **************---------------************** /////////////////
                                
                            }
                            else if( old_quatity == Source_product_Qty )
                            {
                                
                                //////////////////////////////////////////////// ---------------------------------
                                //////////////////////////////////////////////// ---------------------------------
                                
                                ///// ***** no update or modified required !!!
                                
                                ///// ***** already data is present in database ... 
                                
                                //////////////////////////////////////////////// ---------------------------------
                                //////////////////////////////////////////////// ---------------------------------
                                
                                product_items_old.remove(srch_product_idno);
                                
                                //////////////////////////////////////////////// ---------------------------------
                                
                            }
                            
                            
                            
                            ///////////////////////////////////////////////////////////////
                            ///////////////////////////////////////////////////////////////
                            ///////////////////////////////////////////////////////////////
                            
                        }
                        else
                        {
                            
                            ///////////////////////////////////////////////////////////////
                            ///////////////////////////////////////////////////////////////
                            ///////////////////////////////////////////////////////////////
                            
                            ///////  Ans> this product is new to add this bill in update mode    "XXXXXXX"
                            
                            ///////  Decrease stock quantity
                            double present_quantity = 00;
                            PreparedStatement st = con.prepareStatement("SELECT * FROM PRODUCT_TBL WHERE PROIDNO = ?");
                            st.setString(1, srch_product_idno);
                            ResultSet rs_st = st.executeQuery();
                            while(rs_st.next()){
                                
                                present_quantity = Double.valueOf(rs_st.getString("PRODUCT_QTY"));
                                
                            }
                            
                            double update_quantity = present_quantity - Source_product_Qty ;
                            
                            PreparedStatement st1 = con.prepareStatement("UPDATE PRODUCT_TBL SET PRODUCT_QTY = ? WHERE PROIDNO = ?");
                            st1.setString(1, String.valueOf(update_quantity));
                            st1.setString(2, srch_product_idno);
                            int im = st1.executeUpdate();
                            if(im > 0){
                                
                                ///   product quantity decreased
                                
                                
                                PreparedStatement st2 = con.prepareStatement("INSERT INTO PRODUCTION_SOURCE_TBL(PRODCTN_IDNO, PROIDNO, USED_QTY) VALUES(?, ?, ?)");
                                st2.setString(1, production_idNo);
                                st2.setString(2, srch_product_idno);
                                st2.setString(3, String.valueOf(Source_product_Qty));
                                int ij = st2.executeUpdate();
                                
                                
                                
                            }else{
                                
                                JOptionPane.showMessageDialog(this, " Error Occurred !!11!! ", "Error", JOptionPane.ERROR_MESSAGE);
                                
                            }
                            
                            ///////////////////////////////////////////////////////////////
                            ///////////////////////////////////////////////////////////////
                            ///////////////////////////////////////////////////////////////
                            
                        }
                        
                    }
                    
                    
                    
                    
                    
                    
                    
                    /////////  check the present size of arrayList which are containing the product Id lists
                    
                    int al_pi_sz = product_items_old.size();
                    
                    if(al_pi_sz > 0){
                        
                        //////   some product which is removed from old bill when update it
                        
                        for( String pi_dtls : product_items_old ){
                            
                            
                            //////   *******   first maintain stock
                            
                            //////  get quantity from production table
                            double qty_alreadyUsed = 00;
                            PreparedStatement pq = con.prepareStatement("SELECT * FROM PRODUCTION_SOURCE_TBL WHERE PRODCTN_IDNO = ? AND PROIDNO = ?");
                            pq.setString(1, production_idNo);
                            pq.setString(2, pi_dtls);
                            ResultSet rs_Pqs = pq.executeQuery();
                            while(rs_Pqs.next()){
                                
                                qty_alreadyUsed = Double.valueOf(rs_Pqs.getString("USED_QTY"));
                                
                            }
                            
                            
                            //////  get quantity from stock
                            double stock_old_quantity = 00;
                            PreparedStatement pq1 = con.prepareStatement("SELECT * FROM PRODUCT_TBL WHERE PROIDNO = ?");
                            pq1.setString(1, pi_dtls);
                            ResultSet rs_pq1 = pq1.executeQuery();
                            while(rs_pq1.next()){
                                
                                stock_old_quantity = Double.valueOf(rs_pq1.getString("PRODUCT_QTY"));
                                
                            }
                            
                            
                            
                            double final_update_stock_value = stock_old_quantity + qty_alreadyUsed ;
                            
                            //////  update product table to add quantity which are substructed from old bill to update
                            PreparedStatement pq2 = con.prepareStatement("UPDATE PRODUCT_TBL SET PRODUCT_QTY = ? WHERE PROIDNO = ?");
                            pq2.setString(1, String.valueOf(final_update_stock_value));
                            pq2.setString(2, pi_dtls);
                            int pQ2 = pq2.executeUpdate();
                            
                            System.out.println(pQ2);
                            
                            if( pQ2 > 0 ){
                                
                                
                                //////  remove item from bill
                                
                                PreparedStatement pq3 = con.prepareStatement("DELETE FROM PRODUCTION_SOURCE_TBL WHERE PRODCTN_IDNO = ? AND PROIDNO = ?");
                                pq3.setString(1, production_idNo);
                                pq3.setString(2, pi_dtls);
                                int Pq3 = pq3.executeUpdate();
                                
                                System.out.println(Pq3);
                                
                                
                                
                            }
                            else{
                                
                                JOptionPane.showMessageDialog(this, " Error Occurred While Removing Items... ", " Error ", JOptionPane.ERROR_MESSAGE);
                                
                            }
                            
                        }
                        
                    }
                    else{
                        
                        /////  no product in array list
                        /////  all product are to be updated
                        
                    }
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    ////////////////////////////////////////////////////////////
                    ////////////////////////////////////////////////////////////
                    ////////////////////////////////////////////////////////////
                    ////////////////////////////////////////////////////////////
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                }
                
                
                
                ///////////////////////////////////////////////////////////////////////////////////
                /*********************************************************************************/
                /*********************************************************************************/
                /*********************************************************************************/
                /*********************************************************************************/
                /*********************************************************************************/
                ///////////////////////////////////////////////////////////////////////////////////
                
                //////   all operation hold Successfully....
                
                JOptionPane.showMessageDialog(this, "Production Bill Updated!!", "Success", JOptionPane.INFORMATION_MESSAGE);
                
                model.setRowCount(0);
                jComboBox_prodtNm_From.removeAllItems();
                jComboBox_prodtNm_to.removeAllItems();
                //////////  set value of jComboBox
                set_value_JcomboBox();
                jTextField_RawQty_to.setText("");
                jLabel_unitSymbl_to.setText(".....");
                jLabel_to_presnt_qty.setText(".....");

                ////////////////
                ////////////////
                DefaultTableModel tblmodel = (DefaultTableModel) jTable_stored_PurchaseBill.getModel();
                tblmodel.setRowCount(0);
                show_production();         ///////////////  for refresh table
                ////////////////
                ////////////////
                
                
                
                
                
            } else {

                JOptionPane.showMessageDialog(this, " Invalid Bill !! ", "Warning", JOptionPane.WARNING_MESSAGE);

            }

            ///////////////////////////////////////////
            ///////////////////////////////////////////
            ///////////////////////////////////////////
            con.close();

        } catch (Exception ea) {
            ea.printStackTrace();
        }

    }//GEN-LAST:event_jButton_Update_ProductionActionPerformed

    private void jTextField_mang_searchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField_mang_searchActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField_mang_searchActionPerformed

    public void set_value_JcomboBox() {
        ///////////////////////////////////////////////////////

        Connection con;
        try {
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

            jComboBox_prodtNm_From.addItem("Select Product from Below");
            jComboBox_prodtNm_to.addItem("Select Product from Below");

            PreparedStatement ps = con.prepareStatement("SELECT * FROM PRODUCT_TBL");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String productNm = rs.getString("PRODUCT_NM");
                jComboBox_prodtNm_From.addItem(productNm);
                jComboBox_prodtNm_to.addItem(productNm);
            }

            ////////////////////////////////////////////////////////////////////
            ////////////////////////////////////////////////////////////////////
            ////////////////////////////////////////////////////////////////////
            ////////////////////////////////////////////////////////////////////
            con.close();

        } catch (Exception ea) {
            ea.printStackTrace();
        }

        ///////////////////////////////////////////////////////
    }

    public void generatte_production_id() {

        ///////   set Generated value of Product ID
        java.util.Random r = new java.util.Random();
        int x = r.nextInt(99999);
        String ProdctnId = "Production-" + x + "IDNO";
        jLabel_productionBill_idno.setText(ProdctnId);

    }

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
            java.util.logging.Logger.getLogger(account_production.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(account_production.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(account_production.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(account_production.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new account_production().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Btn_Download_productn;
    private javax.swing.JButton jButton_ProductionDetails;
    private javax.swing.JButton jButton_Remove_prodctn_Bill;
    private javax.swing.JButton jButton_Update_Production;
    private javax.swing.JButton jButton_removeRow_tempTbl;
    private javax.swing.JButton jButton_resetAll;
    private javax.swing.JButton jButton_search;
    private javax.swing.JButton jButton_storeRaw_Matirial;
    private javax.swing.JComboBox<String> jComboBox_prodtNm_From;
    private javax.swing.JComboBox<String> jComboBox_prodtNm_to;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel_Expenditure;
    private javax.swing.JLabel jLabel_bill;
    private javax.swing.JLabel jLabel_close;
    private javax.swing.JLabel jLabel_dashboard;
    private javax.swing.JLabel jLabel_from_presnt_qty;
    private javax.swing.JLabel jLabel_product;
    private javax.swing.JLabel jLabel_production;
    private javax.swing.JLabel jLabel_productionBill_idno;
    private javax.swing.JLabel jLabel_profile;
    private javax.swing.JLabel jLabel_sell;
    private javax.swing.JLabel jLabel_to_presnt_qty;
    private javax.swing.JLabel jLabel_unitSymbl_from;
    private javax.swing.JLabel jLabel_unitSymbl_to;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable jTable_stored_PurchaseBill;
    private javax.swing.JTable jTable_tempStr_val;
    private javax.swing.JTextField jTextField_RawQty_From;
    private javax.swing.JTextField jTextField_RawQty_to;
    private javax.swing.JTextField jTextField_mang_search;
    // End of variables declaration//GEN-END:variables
}
