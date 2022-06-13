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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
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
public class account_Sell extends javax.swing.JFrame {

    /////  create border
    Border textFile_border = BorderFactory.createMatteBorder(0, 0, 2, 0, Color.red);
    Border textFile_borderNo = BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(0,204,204));
    
    
    
    /**
     * Creates new form account_DashBoard
     */
    public account_Sell() {
        initComponents();
        
        //  center form
        this.setLocationRelativeTo(null);
        
        //////  set icon
        jLabel_profile.setIcon(new ImageIcon(getClass().getResource("IMAGES/useron.png")));
        
        //// make dashboard selected from sidebar
        jLabel_sellMouseEntered(null);
        
        ////////   set border 
        jTextField_prodt_Qty.setBorder(textFile_border);
        jTextField_SellingPrice.setBorder(textFile_border);
        jTextField_ttl_Price.setBorder(textFile_border);
        jTextField_srchBr.setBorder(textFile_border);
        jLabel_Ttl_toPay.setBorder(textFile_border);
                
        
        /////////    make total price non-editable
        jTextField_ttl_Price.setEditable(false);
        
        
        ///////////////  set val of JcomboBox ---->  choose Product
        setVal_product();
        
        ////////////////   generate sell Bill ID
        generate_SellBillId();
        
        
        
        /////////   add table data
        show_sell();
        
        
        
        
        
    }

    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    /////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////////
    ////  ~~~~~~~~~   Show data of jTable
    public ArrayList<sell> sellList() {
        ArrayList<sell> sellList = new ArrayList<>();

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

            sell sell;

            PreparedStatement ps = con.prepareStatement("SELECT * FROM SELL_TBL");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                
                String sell_id = rs.getString("SELL_ID");
                String sell_Mode = rs.getString("SELL_MODE");
                String sell_AlTotal_price = rs.getString("SELL_ALTTL_PRICE");
                String sell_Dttm = rs.getString("SELL_DTTM");
                
                String Descriptn = "";
                
                PreparedStatement ps1 = con.prepareStatement("SELECT PRODUCT_TBL.PRODUCT_NM, UNIT_TBL.UNIT_SYMBOL, SELL_QTY_TBL.PRODUCT_QTY_SELL,PRODUCT_SELLING_PRICE,SELLING_TTL_PRICE FROM SELL_QTY_TBL,PRODUCT_TBL,UNIT_TBL WHERE SELL_QTY_TBL.PROIDNO=PRODUCT_TBL.PROIDNO AND PRODUCT_TBL.UNIT_IDNO=UNIT_TBL.UNIT_IDNO AND SELL_QTY_TBL.SELL_ID = ?");
                ps1.setString(1, sell_id);
                ResultSet rs1 = ps1.executeQuery();
                while(rs1.next()){
                    Descriptn +=  rs1.getString("PRODUCT_NM")+"-> ("+rs1.getString("PRODUCT_QTY_SELL")+"-"+rs1.getString("UNIT_SYMBOL")+") - Per :"+rs1.getString("PRODUCT_SELLING_PRICE")+"/- Total :"+rs1.getString("SELLING_TTL_PRICE")+"/-\n";
                }
                
                // String final_description = Descriptn.substring(0, Descriptn.length()-4);
                
                sell = new sell(sell_id, sell_Mode, Descriptn, sell_AlTotal_price, sell_Dttm);
                sellList.add(sell);
            }
            /////////////////////////////////////////////
            /////////////////////////////////////////////
            /////////////////////////////////////////////
            con.close();
            
        } catch (Exception ea) {
            ea.printStackTrace();
        }
        ////////////////////////////////////////////////////////////////////////
        return sellList;
        ////////////////////////////////////////////////////////////////////////
    }

    public void show_sell() {
        ArrayList<sell> sellLst = sellList();
        DefaultTableModel model = (DefaultTableModel) jTable_sellTbl.getModel();
        Object[] row = new Object[5];
        for (int i = 0; i < sellLst.size(); i++) {
            row[0] = sellLst.get(i).getSellId();
            row[1] = sellLst.get(i).getmode();
            row[2] = sellLst.get(i).getdescriptn();
            row[3] = sellLst.get(i).getTtl_price();
            row[4] = sellLst.get(i).getdate_time();
            model.addRow(row);
        }
    }

    ////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    /////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////////
    ////  ~~~~~~~~~   Show -------- Searched ----------- data of jTable
    public ArrayList<sell> sellList_search() {
        ArrayList<sell> sellList = new ArrayList<>();

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

            String search_item = "%"+jTextField_srchBr.getText()+"%";
            
            sell sell;

            PreparedStatement ps = con.prepareStatement("SELECT * FROM SELL_TBL WHERE (SELL_ID LIKE ? OR SELL_DTTM LIKE ?)");
            ps.setString(1, search_item);
            ps.setString(2, search_item);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                
                String sell_id = rs.getString("SELL_ID");
                String sell_Mode = rs.getString("SELL_MODE");
                String sell_AlTotal_price = rs.getString("SELL_ALTTL_PRICE");
                String sell_Dttm = rs.getString("SELL_DTTM");
                
                String Descriptn = "";
                
                PreparedStatement ps1 = con.prepareStatement("SELECT PRODUCT_TBL.PRODUCT_NM, UNIT_TBL.UNIT_SYMBOL, SELL_QTY_TBL.PRODUCT_QTY_SELL,PRODUCT_SELLING_PRICE,SELLING_TTL_PRICE FROM SELL_QTY_TBL,PRODUCT_TBL,UNIT_TBL WHERE SELL_QTY_TBL.PROIDNO=PRODUCT_TBL.PROIDNO AND PRODUCT_TBL.UNIT_IDNO=UNIT_TBL.UNIT_IDNO AND SELL_QTY_TBL.SELL_ID = ?");
                ps1.setString(1, sell_id);
                ResultSet rs1 = ps1.executeQuery();
                while(rs1.next()){
                    Descriptn +=  rs1.getString("PRODUCT_NM")+"-> ("+rs1.getString("PRODUCT_QTY_SELL")+"-"+rs1.getString("UNIT_SYMBOL")+") - Per :"+rs1.getString("PRODUCT_SELLING_PRICE")+"/- Total :"+rs1.getString("SELLING_TTL_PRICE")+"/-\n";
                }
                
                // String final_description = Descriptn.substring(0, Descriptn.length()-4);
                
                sell = new sell(sell_id, sell_Mode, Descriptn, sell_AlTotal_price, sell_Dttm);
                sellList.add(sell);
            }
            /////////////////////////////////////////////
            /////////////////////////////////////////////
            /////////////////////////////////////////////
            con.close();
            
        } catch (Exception ea) {
            ea.printStackTrace();
        }
        ////////////////////////////////////////////////////////////////////////
        return sellList;
        ////////////////////////////////////////////////////////////////////////
    }

    public void show_sell_search() {
        ArrayList<sell> sellLst = sellList_search();
        DefaultTableModel model = (DefaultTableModel) jTable_sellTbl.getModel();
        
        model.setRowCount(0);
        
        Object[] row = new Object[5];
        for (int i = 0; i < sellLst.size(); i++) {
            row[0] = sellLst.get(i).getSellId();
            row[1] = sellLst.get(i).getmode();
            row[2] = sellLst.get(i).getdescriptn();
            row[3] = sellLst.get(i).getTtl_price();
            row[4] = sellLst.get(i).getdate_time();
            model.addRow(row);
        }
    }

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
        jPanel_sellEntry_panel = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel_sellID = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jComboBox_prodt_Nm = new javax.swing.JComboBox<>();
        jLabel_Present_qty = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jTextField_prodt_Qty = new javax.swing.JTextField();
        jLabel_Unit = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jComboBox_modeUse = new javax.swing.JComboBox<>();
        jLabel8 = new javax.swing.JLabel();
        jTextField_SellingPrice = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jTextField_ttl_Price = new javax.swing.JTextField();
        jLabel_perUnit = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jButton_For_Sell = new javax.swing.JButton();
        jButton_fr_Reset = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable_tempStr_val = new javax.swing.JTable();
        jButton_Add_Tmp_tbl = new javax.swing.JButton();
        jButton_Remove_Tmp_tbl = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jLabel_Ttl_toPay = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLabel_profile = new javax.swing.JLabel();
        jLabel_close = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable_sellTbl = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        jTextField_srchBr = new javax.swing.JTextField();
        jButton_Search_btn = new javax.swing.JButton();
        jButton_download = new javax.swing.JButton();
        jButton_ModeWise = new javax.swing.JButton();
        jButton_fr_Remove = new javax.swing.JButton();

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

        jPanel_sellEntry_panel.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Tempus Sans ITC", 3, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(26, 198, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Sell Entry");

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel3.setText("Bill ID");

        jLabel_sellID.setBackground(new java.awt.Color(255, 255, 255));
        jLabel_sellID.setFont(new java.awt.Font("Traditional Arabic", 2, 20)); // NOI18N
        jLabel_sellID.setForeground(new java.awt.Color(51, 153, 255));
        jLabel_sellID.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel_sellID.setText(".....");

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel5.setText("Product");

        jComboBox_prodt_Nm.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jComboBox_prodt_Nm.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBox_prodt_NmItemStateChanged(evt);
            }
        });

        jLabel_Present_qty.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jLabel_Present_qty.setForeground(new java.awt.Color(51, 204, 255));
        jLabel_Present_qty.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel_Present_qty.setText(".....");

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel6.setText("Quantity");

        jTextField_prodt_Qty.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jTextField_prodt_Qty.setText("00");
        jTextField_prodt_Qty.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextField_prodt_QtyKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField_prodt_QtyKeyReleased(evt);
            }
        });

        jLabel_Unit.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jLabel_Unit.setForeground(new java.awt.Color(51, 204, 255));
        jLabel_Unit.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel_Unit.setText(".....");

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel7.setText("Mode Use");

        jComboBox_modeUse.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jComboBox_modeUse.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Whole Sell", "Retail" }));

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel8.setText("Price");

        jTextField_SellingPrice.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jTextField_SellingPrice.setText("00");
        jTextField_SellingPrice.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextField_SellingPriceKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField_SellingPriceKeyReleased(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel9.setText("Total");

        jTextField_ttl_Price.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jTextField_ttl_Price.setText("00");

        jLabel_perUnit.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel_perUnit.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel_perUnit.setText(".....");

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel10.setText("/-");

        jButton_For_Sell.setBackground(new java.awt.Color(71, 209, 71));
        jButton_For_Sell.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jButton_For_Sell.setForeground(new java.awt.Color(255, 255, 255));
        jButton_For_Sell.setIcon(new javax.swing.ImageIcon(getClass().getResource("/account_maintainer/IMAGES/plusIcn.png"))); // NOI18N
        jButton_For_Sell.setText("Sell");
        jButton_For_Sell.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton_For_Sell.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_For_SellActionPerformed(evt);
            }
        });

        jButton_fr_Reset.setBackground(new java.awt.Color(0, 128, 255));
        jButton_fr_Reset.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jButton_fr_Reset.setForeground(new java.awt.Color(255, 255, 255));
        jButton_fr_Reset.setIcon(new javax.swing.ImageIcon(getClass().getResource("/account_maintainer/IMAGES/reset.png"))); // NOI18N
        jButton_fr_Reset.setText("Reset");
        jButton_fr_Reset.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton_fr_Reset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_fr_ResetActionPerformed(evt);
            }
        });

        jTable_tempStr_val.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Product", "Quantity", "Selling Price", "Total Price"
            }
        ));
        jScrollPane2.setViewportView(jTable_tempStr_val);

        jButton_Add_Tmp_tbl.setBackground(new java.awt.Color(51, 255, 0));
        jButton_Add_Tmp_tbl.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jButton_Add_Tmp_tbl.setForeground(new java.awt.Color(255, 255, 255));
        jButton_Add_Tmp_tbl.setText("Add");
        jButton_Add_Tmp_tbl.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton_Add_Tmp_tbl.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_Add_Tmp_tblActionPerformed(evt);
            }
        });

        jButton_Remove_Tmp_tbl.setBackground(new java.awt.Color(255, 0, 0));
        jButton_Remove_Tmp_tbl.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jButton_Remove_Tmp_tbl.setForeground(new java.awt.Color(255, 255, 255));
        jButton_Remove_Tmp_tbl.setText("Remove");
        jButton_Remove_Tmp_tbl.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton_Remove_Tmp_tbl.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_Remove_Tmp_tblActionPerformed(evt);
            }
        });

        jLabel4.setBackground(new java.awt.Color(255, 255, 255));
        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 153, 255));
        jLabel4.setText("Total To Pay");

        jLabel_Ttl_toPay.setBackground(new java.awt.Color(255, 255, 255));
        jLabel_Ttl_toPay.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        jLabel_Ttl_toPay.setForeground(new java.awt.Color(0, 153, 255));
        jLabel_Ttl_toPay.setText(".....");

        javax.swing.GroupLayout jPanel_sellEntry_panelLayout = new javax.swing.GroupLayout(jPanel_sellEntry_panel);
        jPanel_sellEntry_panel.setLayout(jPanel_sellEntry_panelLayout);
        jPanel_sellEntry_panelLayout.setHorizontalGroup(
            jPanel_sellEntry_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_sellEntry_panelLayout.createSequentialGroup()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 517, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(jPanel_sellEntry_panelLayout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addGroup(jPanel_sellEntry_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel_sellEntry_panelLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButton_Add_Tmp_tbl, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(55, 55, 55))
                    .addGroup(jPanel_sellEntry_panelLayout.createSequentialGroup()
                        .addGroup(jPanel_sellEntry_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel_sellEntry_panelLayout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel_Ttl_toPay, javax.swing.GroupLayout.PREFERRED_SIZE, 234, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel_sellEntry_panelLayout.createSequentialGroup()
                                .addComponent(jButton_fr_Reset, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jButton_For_Sell, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel_sellEntry_panelLayout.createSequentialGroup()
                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel_sellID, javax.swing.GroupLayout.PREFERRED_SIZE, 318, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel_sellEntry_panelLayout.createSequentialGroup()
                                .addComponent(jLabel7)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jComboBox_modeUse, javax.swing.GroupLayout.PREFERRED_SIZE, 222, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel_sellEntry_panelLayout.createSequentialGroup()
                                .addGroup(jPanel_sellEntry_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel_sellEntry_panelLayout.createSequentialGroup()
                                        .addComponent(jLabel8)
                                        .addGap(51, 51, 51))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel_sellEntry_panelLayout.createSequentialGroup()
                                        .addComponent(jLabel9)
                                        .addGap(52, 52, 52)))
                                .addGroup(jPanel_sellEntry_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jTextField_ttl_Price, javax.swing.GroupLayout.PREFERRED_SIZE, 221, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jTextField_SellingPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 221, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jButton_Remove_Tmp_tbl, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanel_sellEntry_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel_sellEntry_panelLayout.createSequentialGroup()
                                        .addGap(25, 25, 25)
                                        .addComponent(jLabel10))
                                    .addGroup(jPanel_sellEntry_panelLayout.createSequentialGroup()
                                        .addGap(11, 11, 11)
                                        .addComponent(jLabel_perUnit, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel_sellEntry_panelLayout.createSequentialGroup()
                                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jComboBox_prodt_Nm, javax.swing.GroupLayout.PREFERRED_SIZE, 221, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel_Present_qty, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel_sellEntry_panelLayout.createSequentialGroup()
                                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jTextField_prodt_Qty, javax.swing.GroupLayout.PREFERRED_SIZE, 221, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel_Unit, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel_sellEntry_panelLayout.setVerticalGroup(
            jPanel_sellEntry_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_sellEntry_panelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel_sellEntry_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel_sellID, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel_sellEntry_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jComboBox_modeUse, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel_sellEntry_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jComboBox_prodt_Nm, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel_Present_qty, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel_sellEntry_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jTextField_prodt_Qty, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel_Unit, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel_sellEntry_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jTextField_SellingPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel_perUnit, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel_sellEntry_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jTextField_ttl_Price, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel_sellEntry_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton_Add_Tmp_tbl, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton_Remove_Tmp_tbl, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel_sellEntry_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jLabel_Ttl_toPay))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel_sellEntry_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton_For_Sell)
                    .addComponent(jButton_fr_Reset)))
        );

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel_sellEntry_panel, javax.swing.GroupLayout.PREFERRED_SIZE, 466, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addComponent(jPanel_sellEntry_panel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jLabel_profile.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel_profile.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel_profile.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel_profileMouseClicked(evt);
            }
        });

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

        jTable_sellTbl.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Sell Bill ID", "Mode", "Description", "Total Price", "Date-Time"
            }
        ));
        jTable_sellTbl.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable_sellTblMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTable_sellTbl);

        jLabel2.setFont(new java.awt.Font("Tempus Sans ITC", 3, 24)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(26, 198, 255));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Sell Details");

        jTextField_srchBr.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jTextField_srchBr.setForeground(new java.awt.Color(153, 153, 153));
        jTextField_srchBr.setText("Search for Sell Bill or Date/Time...");
        jTextField_srchBr.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTextField_srchBrFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTextField_srchBrFocusLost(evt);
            }
        });

        jButton_Search_btn.setBackground(new java.awt.Color(51, 255, 0));
        jButton_Search_btn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/account_maintainer/IMAGES/searchOn.png"))); // NOI18N
        jButton_Search_btn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton_Search_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_Search_btnActionPerformed(evt);
            }
        });

        jButton_download.setBackground(new java.awt.Color(51, 255, 0));
        jButton_download.setFont(new java.awt.Font("Tempus Sans ITC", 1, 24)); // NOI18N
        jButton_download.setForeground(new java.awt.Color(255, 255, 255));
        jButton_download.setText("Download");
        jButton_download.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton_download.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_downloadActionPerformed(evt);
            }
        });

        jButton_ModeWise.setBackground(new java.awt.Color(204, 102, 255));
        jButton_ModeWise.setFont(new java.awt.Font("Tempus Sans ITC", 1, 24)); // NOI18N
        jButton_ModeWise.setForeground(new java.awt.Color(255, 255, 255));
        jButton_ModeWise.setText("Mode Wise");
        jButton_ModeWise.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton_ModeWise.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_ModeWiseActionPerformed(evt);
            }
        });

        jButton_fr_Remove.setBackground(new java.awt.Color(255, 51, 0));
        jButton_fr_Remove.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jButton_fr_Remove.setForeground(new java.awt.Color(255, 255, 255));
        jButton_fr_Remove.setIcon(new javax.swing.ImageIcon(getClass().getResource("/account_maintainer/IMAGES/delete.png"))); // NOI18N
        jButton_fr_Remove.setText("Remove");
        jButton_fr_Remove.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton_fr_Remove.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_fr_RemoveActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jTextField_srchBr)
                                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                                        .addComponent(jLabel_profile, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jLabel_close, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jButton_Search_btn, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                        .addGap(0, 57, Short.MAX_VALUE)
                        .addComponent(jButton_ModeWise)
                        .addGap(18, 18, 18)
                        .addComponent(jButton_fr_Remove)
                        .addGap(18, 18, 18)
                        .addComponent(jButton_download)
                        .addGap(60, 60, 60))))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel_close, javax.swing.GroupLayout.DEFAULT_SIZE, 43, Short.MAX_VALUE)
                    .addComponent(jLabel_profile, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextField_srchBr, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton_Search_btn, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton_ModeWise)
                    .addComponent(jButton_fr_Remove)
                    .addComponent(jButton_download))
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
            .addComponent(jPanel4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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
        
        jLabel_production.setBackground(new Color(0, 204, 204));
        jLabel_production.setForeground(new Color(255, 255, 255));
        jLabel_production.setBorder(textFile_borderNo);
        
    }//GEN-LAST:event_jLabel_productionMouseExited

    private void jLabel_sellMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel_sellMouseEntered
        
        jLabel_sell.setBackground(new Color(255, 255, 255));
        jLabel_sell.setForeground(new Color(0, 204, 204));
        jLabel_sell.setBorder(textFile_border);
        
    }//GEN-LAST:event_jLabel_sellMouseEntered

    private void jLabel_sellMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel_sellMouseExited
        
        
        
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

    private void jLabel_sellMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel_sellMouseClicked
        
        account_Sell form = new account_Sell();
        form.setVisible(true);
        form.pack();
        form.setLocationRelativeTo(null);
        //close the current form (login form)
        this.dispose();
        
    }//GEN-LAST:event_jLabel_sellMouseClicked

    private void jTextField_srchBrFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField_srchBrFocusGained
        
        String srchBr = jTextField_srchBr.getText();
        
        if (srchBr.equals("Search for Sell Bill or Date/Time...")) {
            jTextField_srchBr.setText("");
            jTextField_srchBr.setForeground(new Color(51, 153, 255));
        }
        
    }//GEN-LAST:event_jTextField_srchBrFocusGained

    private void jTextField_srchBrFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField_srchBrFocusLost
        
        String srchBr = jTextField_srchBr.getText();
        
        if (srchBr.equals("") || srchBr.equals("Search for Sell Bill or Date/Time...")) {
            jTextField_srchBr.setText("Search for Sell Bill or Date/Time...");
            jTextField_srchBr.setForeground(new Color(153, 153, 153));
        }
        
    }//GEN-LAST:event_jTextField_srchBrFocusLost

    private void jComboBox_prodt_NmItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox_prodt_NmItemStateChanged
        
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

            String prodt_nm = jComboBox_prodt_Nm.getSelectedItem().toString();
            PreparedStatement ps = con.prepareStatement("SELECT PRODUCT_TBL.PRODUCT_QTY, UNIT_TBL.UNIT_NAME,UNIT_SYMBOL FROM PRODUCT_TBL,UNIT_TBL WHERE PRODUCT_TBL.UNIT_IDNO=UNIT_TBL.UNIT_IDNO AND PRODUCT_TBL.PRODUCT_NM = ?");
            ps.setString(1, prodt_nm);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                String prsnt_qty = "["+rs.getString("PRODUCT_QTY")+"]";
                String unit_symbl = rs.getString("UNIT_SYMBOL");
                
                jLabel_Present_qty.setText(prsnt_qty);
                jLabel_Unit.setText(unit_symbl);
                jLabel_perUnit.setText("/- / "+unit_symbl);
            }
            
            //////////////////////////////////////////////////////
            //////////////////////////////////////////////////////
            //////////////////////////////////////////////////////
            con.close();
        }
        catch(Exception ea){
            ea.printStackTrace();
        }
        
    }//GEN-LAST:event_jComboBox_prodt_NmItemStateChanged

    private void jButton_For_SellActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_For_SellActionPerformed
        
        try{
            
            String BillId = jLabel_sellID.getText();
            String mode_use = jComboBox_modeUse.getSelectedItem().toString();
            String total_to_Pay = jLabel_Ttl_toPay.getText();
            
            
            
            if(jTable_tempStr_val.getRowCount() <= 0 || BillId.isEmpty() || mode_use.isEmpty() || total_to_Pay.isEmpty() || total_to_Pay.equals(".....")){
                
                JOptionPane.showMessageDialog(this, "No Item is Choosen!!", "Notice", JOptionPane.WARNING_MESSAGE);
                
            }else{
                ////////////////////////////////////////////////////////////////
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


                        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                        LocalDateTime now = LocalDateTime.now();///fetch localDateTime from here
                        String presentDateTime = dtf.format(now);/////convert localtime to String with format 

                        Date d = new Date();
                        int year = d.getYear();
                        int currentYear = year + 1900;
                        String Current_Year = String.valueOf(currentYear);

                        LocalDate currentdate = LocalDate.now();
                        Month currentMonth = currentdate.getMonth();
                        String Current_Month = String.valueOf(currentMonth);

                        //////////////////////////////////////
                        
                        //////////////////////////////////////////////////////////////////////////
                        //////////////////////////////////////////////////////////////////////////
                        
                        PreparedStatement ps = con.prepareStatement("INSERT INTO SELL_TBL(SELL_ID, SELL_MODE, SELL_ALTTL_PRICE, SELL_SESSION, SELL_MONTH, SELL_DTTM) VALUES(?, ?, ?, ?, ?, ?)");
                        ps.setString(1, BillId);
                        ps.setString(2, mode_use);
                        ps.setString(3, total_to_Pay);
                        ps.setString(4, Current_Year);
                        ps.setString(5, Current_Month);
                        ps.setString(6, presentDateTime);
                        
                        int i = ps.executeUpdate();
                        if( i > 0 ){
                            
                            ////////////////////////////////////////////////////
                            ////////////////////////////////////////////////////
                            ////     fetch each Product and Deduct Quantity from table and add quantity to Sell_Qty_table
                            
                            DefaultTableModel model = (DefaultTableModel) jTable_tempStr_val.getModel();
                            
                            int k = 0, l = 0;
                            
                            for ( int j = 0 ; j < model.getRowCount() ; j++ ){
                                
                                /////////  get first product details from temp table
                                String prodt_name = (String)model.getValueAt(j, 0);
                                String prodt_Quantity = (String)model.getValueAt(j, 1);
                                String prodt_Selling_Price = (String)model.getValueAt(j, 2);
                                String prodt_Ttl_Price = (String)model.getValueAt(j, 3);
                                
                                /////////   get quantity from product table  -->  product id and product present quantity
                                double present_quantity = 00;
                                String product_id = "";
                                PreparedStatement ps1 = con.prepareStatement("SELECT * FROM PRODUCT_TBL WHERE PRODUCT_NM = ?");
                                ps1.setString(1, prodt_name);
                                ResultSet rs1 = ps1.executeQuery();
                                while(rs1.next()){
                                    ////////////////////////////////////////////
                                    product_id = rs1.getString("PROIDNO");
                                    present_quantity = Double.valueOf(rs1.getString("PRODUCT_QTY"));
                                    ////////////////////////////////////////////
                                }
                                
                                
                                /////////////  deduct from present quantity to selling quantity
                                double rest_Qty = present_quantity - Double.valueOf(prodt_Quantity);
                                /////////////   update to product table
                                PreparedStatement ps2 = con.prepareStatement("UPDATE PRODUCT_TBL SET PRODUCT_QTY = ? WHERE PROIDNO = ?");
                                ps2.setString(1, String.valueOf(rest_Qty));
                                ps2.setString(2, product_id);
                                k = ps2.executeUpdate();
                                
                                
                                //////////////   set to Sell_Qty_table
                                PreparedStatement ps3 = con.prepareStatement("INSERT INTO SELL_QTY_TBL(SELL_ID, PROIDNO, PRODUCT_QTY_SELL, PRODUCT_SELLING_PRICE, SELLING_TTL_PRICE) VALUES(?, ?, ?, ?, ?)");
                                ps3.setString(1, BillId);
                                ps3.setString(2, product_id);
                                ps3.setString(3, prodt_Quantity);
                                ps3.setString(4, prodt_Selling_Price);
                                ps3.setString(5, prodt_Ttl_Price);
                                l = ps3.executeUpdate();
                                
                                
                            }
                            
                            
                            if(k > 0 && l > 0){
                                ////////////////////////////////////////////////
                                ////////////////////////////////////////////////
                                
                                JOptionPane.showMessageDialog(this, "Sell Bill Accepted...", "Success", JOptionPane.INFORMATION_MESSAGE);
                                

                                
                                        
                                generate_SellBillId();
                                jComboBox_prodt_Nm.removeAllItems();
                                setVal_product();
                                
                                
                                
                                
                                ////   set all field empty
                                jComboBox_prodt_Nm.setSelectedIndex(0);
                                jTextField_prodt_Qty.setText("00");
                                jComboBox_modeUse.setSelectedIndex(0);
                                jTextField_SellingPrice.setText("00");
                                jTextField_ttl_Price.setText("00");
                                jLabel_Present_qty.setText(".....");
                                jLabel_Unit.setText(".....");
                                jLabel_perUnit.setText(".....");
                                jLabel_Ttl_toPay.setText(".....");

                                ////////////////
                                ////////////////
                                DefaultTableModel modelOri = (DefaultTableModel) jTable_sellTbl.getModel();
                                modelOri.setRowCount(0);
                                show_sell();         ///////////////  for refresh table



                                ///////  make temp table null and stored item into jcombo box
                                DefaultTableModel Temp_model = (DefaultTableModel) jTable_tempStr_val.getModel();
                                Temp_model.setRowCount(0);
                                
                                
                                ////////////////////////////////////////////////
                                ////////////////////////////////////////////////
                            }
                            else{
                                ////////////////////////////////////////////////////////
                                ////////////////////////////////////////////////////////
                                JOptionPane.showMessageDialog(this, "Error Occurred !! Please Try Again!!22!!", "Error", JOptionPane.ERROR_MESSAGE);
                                ////////////////////////////////////////////////////////
                                ////////////////////////////////////////////////////////
                            }
                            
                            
                            
                            ////////////////////////////////////////////////////
                            ////////////////////////////////////////////////////
                            
                        }else{
                            ////////////////////////////////////////////////////////
                            ////////////////////////////////////////////////////////
                            JOptionPane.showMessageDialog(this, "Error Occurred !! Please Try Again!!11!!", "Error", JOptionPane.ERROR_MESSAGE);
                            ////////////////////////////////////////////////////////
                            ////////////////////////////////////////////////////////
                        }


                        ////////////////////////////////////////////////////////////
                        ////////////////////////////////////////////////////////////
                        ////////////////////////////////////////////////////////////
                        con.close();
                    
                    }
                    catch(Exception ea){
                        ea.printStackTrace();
                    }

                
                ////////////////////////////////////////////////////////////////
                ////////////////////////////////////////////////////////////////
                ////////////////////////////////////////////////////////////////
                
            }
            
            
        }catch(Exception ea){
            ea.printStackTrace();
        }
        
    }//GEN-LAST:event_jButton_For_SellActionPerformed

    private void jTextField_prodt_QtyKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField_prodt_QtyKeyPressed
        
        if (evt.getKeyChar() >= '0' && evt.getKeyChar() <= '9' || evt.getKeyCode() == KeyEvent.VK_BACK_SPACE || evt.getKeyChar() == KeyEvent.VK_DECIMAL || evt.getKeyChar() == KeyEvent.VK_PERIOD) {
            jTextField_prodt_Qty.setEditable(true);
        } else {
            jTextField_prodt_Qty.setEditable(false);
        }
        
    }//GEN-LAST:event_jTextField_prodt_QtyKeyPressed

    private void jTextField_SellingPriceKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField_SellingPriceKeyPressed
        
        if (evt.getKeyChar() >= '0' && evt.getKeyChar() <= '9' || evt.getKeyCode() == KeyEvent.VK_BACK_SPACE || evt.getKeyChar() == KeyEvent.VK_DECIMAL || evt.getKeyChar() == KeyEvent.VK_PERIOD) {
            jTextField_SellingPrice.setEditable(true);
        } else {
            jTextField_SellingPrice.setEditable(false);
        }
        
    }//GEN-LAST:event_jTextField_SellingPriceKeyPressed

    private void jTextField_prodt_QtyKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField_prodt_QtyKeyReleased
        
        double qty = Double.valueOf(jTextField_prodt_Qty.getText());
        double price = Double.valueOf(jTextField_SellingPrice.getText());
        
        double total_price = qty * price ;
        
        jTextField_ttl_Price.setText(String.valueOf(String.format("%.2f", total_price)));
        
    }//GEN-LAST:event_jTextField_prodt_QtyKeyReleased

    private void jTextField_SellingPriceKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField_SellingPriceKeyReleased
        
        double qty = Double.valueOf(jTextField_prodt_Qty.getText());
        double price = Double.valueOf(jTextField_SellingPrice.getText());
        
        double total_price = qty * price ;
        
        jTextField_ttl_Price.setText(String.valueOf(String.format("%.2f", total_price)));
        
    }//GEN-LAST:event_jTextField_SellingPriceKeyReleased

    private void jButton_Search_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_Search_btnActionPerformed
        
        String searchBar = jTextField_srchBr.getText();
        
        if(searchBar.equalsIgnoreCase("Search for Sell Bill or Date/Time...") || searchBar.trim().equals("")){
            JOptionPane.showMessageDialog(null, " Please Search Some Thing... ", "Warning", JOptionPane.WARNING_MESSAGE);
        }
        else{
            ////////////////
            ////////////////
            DefaultTableModel model = (DefaultTableModel) jTable_sellTbl.getModel();
            model.setRowCount(0);
            show_sell_search();         ///////////////  for refresh table
            ////////////////
            ////////////////
        }
        
        
    }//GEN-LAST:event_jButton_Search_btnActionPerformed

    private void jButton_fr_ResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_fr_ResetActionPerformed
        
        /////   generate sell bill number
        generate_SellBillId();

        ////   set all field empty
        jComboBox_prodt_Nm.setSelectedIndex(0);
        jTextField_prodt_Qty.setText("00");
        jComboBox_modeUse.setSelectedIndex(0);
        jTextField_SellingPrice.setText("00");
        jTextField_ttl_Price.setText("00");
        jLabel_Present_qty.setText(".....");
        jLabel_Unit.setText(".....");
        jLabel_perUnit.setText(".....");
        
        jTextField_srchBr.setText("Search for Sell Bill or Date/Time...");

        jLabel_Ttl_toPay.setText(".....");
        
        ////////////////
        ////////////////
        DefaultTableModel model = (DefaultTableModel) jTable_sellTbl.getModel();
        model.setRowCount(0);
        show_sell();         ///////////////  for refresh table
        
        
        
        ///////  make temp table null and stored item into jcombo box
        DefaultTableModel Temp_model = (DefaultTableModel) jTable_tempStr_val.getModel();
        
        ////////  get product list from jTable and add to jcombo_box
        if(Temp_model.getRowCount() > 0){
            for(int i = 0; i < Temp_model.getRowCount(); i++)
            {
                String prodT_name = (String)Temp_model.getValueAt(i, 0);
                jComboBox_prodt_Nm.addItem(prodT_name);
            }
        }
        ////  make temp table empty
        Temp_model.setRowCount(0);
        ////////////////
        ////////////////
        
    }//GEN-LAST:event_jButton_fr_ResetActionPerformed

    private void jTable_sellTblMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable_sellTblMouseClicked
        
        int il = jTable_sellTbl.getSelectedRow();
        TableModel model = jTable_sellTbl.getModel();
        
        String Sell_ID = model.getValueAt(il, 0).toString();
        String Mode_Use = model.getValueAt(il, 1).toString();
        String DescripTn = model.getValueAt(il, 2).toString();
        String Ttl_Price = model.getValueAt(il, 3).toString();
        String Dttm = model.getValueAt(il, 4).toString();
        
        String Full_Descriptn = "Sell Bill ID : "+Sell_ID+"\nMode Use : "+Mode_Use+"\nProduct Used : \n"+DescripTn+"\nTotal To Pay : "+Ttl_Price+"/-\nDate-Time : "+Dttm;
        
        JOptionPane.showMessageDialog(this, Full_Descriptn, "Description", JOptionPane.INFORMATION_MESSAGE);
        
        
    }//GEN-LAST:event_jTable_sellTblMouseClicked

    private void jButton_fr_RemoveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_fr_RemoveActionPerformed
        
        
        try{
            

                    DefaultTableModel tblModel = (DefaultTableModel) jTable_sellTbl.getModel();

                    int il = jTable_sellTbl.getSelectedRow();

                    if(il > 0){

                            String Sell_Id = tblModel.getValueAt(il, 0).toString();

                            ////////////////////////////////////////////////////////////////////////
                            ////////////////////////////////////////////////////////////////////////
                            ////////////////////////////////////////////////////////////////////////
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
                                ////////////////////////////////////////////////
                                ////////////////////////////////////////////////
                                ////////////////////////////////////////////////
                                
                                int i = 0, j = 0, k = 0;
                                
                                PreparedStatement ps = con.prepareStatement("SELECT * FROM SELL_QTY_TBL WHERE SELL_ID = ?");
                                ps.setString(1, Sell_Id);
                                ResultSet rs = ps.executeQuery();
                                while(rs.next()){
                                    ////////////////////////////////////////////
                                    
                                    //////    GET PRODUCT DETAILS
                                    String Selling_Prodt_Id = rs.getString("PROIDNO");
                                    double Selling_Prodt_Qty = Double.valueOf(rs.getString("PRODUCT_QTY_SELL"));
                                    
                                    ///////    GET STORED VALUE OF PRODUCT DETAILS
                                    PreparedStatement ps1 = con.prepareStatement("SELECT * FROM PRODUCT_TBL WHERE PROIDNO = ?");
                                    ps1.setString(1, Selling_Prodt_Id);
                                    ResultSet rs1 = ps1.executeQuery();
                                    while(rs1.next()){
                                        
                                        double prodt_stored_Qty = Double.valueOf(rs1.getString("PRODUCT_QTY"));
                                        
                                        double final_Qty = prodt_stored_Qty + Selling_Prodt_Qty ;
                                        
                                        PreparedStatement ps2 = con.prepareStatement("UPDATE PRODUCT_TBL SET PRODUCT_QTY = ? WHERE PROIDNO = ?");
                                        ps2.setString(1, String.valueOf(final_Qty));
                                        ps2.setString(2, Selling_Prodt_Id);
                                        i = ps2.executeUpdate();
                                        if(i > 0){
                                            
                                            PreparedStatement ps3 = con.prepareStatement("DELETE FROM SELL_QTY_TBL WHERE SELL_ID = ? AND PROIDNO = ?");
                                            ps3.setString(1, Sell_Id);
                                            ps3.setString(2, Selling_Prodt_Id);
                                            j = ps3.executeUpdate();
                                            
                                        }
                                        else{
                                            /////////////////////////////////////////////////////////
                                            JOptionPane.showMessageDialog(this, "Error Updating Store!!", "Error", JOptionPane.ERROR_MESSAGE);
                                            /////////////////////////////////////////////////////////
                                        }
                                        
                                    }
                                    
                                    ////////////////////////////////////////////
                                }
                                
                                
                                if( j > 0 ){
                                    
                                    
                                    
                                    PreparedStatement ps4 = con.prepareStatement("DELETE FROM SELL_TBL WHERE SELL_ID = ?");
                                    ps4.setString(1, Sell_Id);
                                    k = ps4.executeUpdate();
                                    if(k > 0){
                                        ////////////////////////////////////////////////////////////////////////
                                        JOptionPane.showMessageDialog(this, "Sold Bill Returned...", "Success", JOptionPane.INFORMATION_MESSAGE);
                                        // Refresh table
                                        tblModel.setRowCount(0);
                                        show_sell();
                                        ////////////////////////////////////////////////////////////////////////
                                    }
                                    else{
                                        JOptionPane.showMessageDialog(this, "Error Occurred!! Please Try Again...!!22!!", "Error", JOptionPane.ERROR_MESSAGE);
                                    }
                                    
                                    
                                    
                                    
                                }
                                else{
                                    JOptionPane.showMessageDialog(this, "Error Occurred!! Please Try Again...!!11!!", "Error", JOptionPane.ERROR_MESSAGE);
                                }

                                /////////////////////////////////////////////////////////////////////////////////////////
                                /////////////////////////////////////////////////////////////////////////////////////////
                                /////////////////////////////////////////////////////////////////////////////////////////
                                con.close();

                            }
                            catch(Exception ea){
                                ea.printStackTrace();
                            }
                            ////////////////////////////////////////////////////////////////////////
                            ////////////////////////////////////////////////////////////////////////
                            ////////////////////////////////////////////////////////////////////////




                    }
                    else{
                        JOptionPane.showMessageDialog(this, "No Row Selected!!", "Warning", JOptionPane.WARNING_MESSAGE);
                    }
            
        }catch(Exception ea){
            ea.printStackTrace();
            JOptionPane.showMessageDialog(this, "Configaration Failed!!", "Notice", JOptionPane.ERROR_MESSAGE);
        }
        
        
        
    }//GEN-LAST:event_jButton_fr_RemoveActionPerformed

    private void jButton_downloadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_downloadActionPerformed
        
        
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
            while(rs.next()){
                
                getPath = rs.getString("STORE_TABLE_DATA_PATH");
                
            }
            
            ///////////////////////////////
            ///////////////////////////////
            ///////////////////////////////
            con.close();
            
        }
        catch(Exception ea){
            ea.printStackTrace();
        }
        
        ////////////////////////////////////////////////////////////////////////
        ////////////////////////////////////////////////////////////////////////
        
        
        
        
        
        
        
        ///////////     check the file path is valid or not!!!
        String pathsss = getPath + "\\";
        Path FullPath = Paths.get(pathsss);
        if(Files.exists(FullPath) == true){
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
                    String Sell_ID = "Sell_Table-" + x + "ID-at-" + onlyDt+ "DT__" + modifyTm +"TM.xls";         //////// for excel file
                    /////////////////////////////////
                    /////////////////////////////////

                    ////////////////////////
                    ///////  url path + file name
                    ////////////////////////
                    String pathWithFileName = getPath + "\\" + Sell_ID;

                    //File PathWithFileName = new File(pathWithFileName);
                    File file = new File(pathWithFileName);




                    //////////////////////// export file into .xls format

                    try{
                        TableModel model = jTable_sellTbl.getModel();
                        FileWriter excel = new FileWriter(file);

                        for(int i = 0; i < model.getColumnCount(); i++){
                            excel.write(model.getColumnName(i) + "\t");
                        }

                        excel.write("\n");

                        for(int i=0; i< model.getRowCount(); i++) {
                            for(int j=0; j < model.getColumnCount(); j++) {
                                excel.write(model.getValueAt(i,j).toString()+"\t");
                            }
                            excel.write("\n");
                        }

                        excel.close();
                        JOptionPane.showMessageDialog(this, "File Saved Successfully...", "Status", JOptionPane.INFORMATION_MESSAGE);

                    }
                    catch(IOException e)
                    {
                        System.out.println(e); 
                    }
            
            
            
                    
            ////////////////////////////////////////////////////////////////////
            ////////////////////////////////////////////////////////////////////
            ////////////////////////////////////////////////////////////////////
            ////////////////////////////////////////////////////////////////////
                    
            
        }else{
            System.out.println("Invalid");
            JOptionPane.showMessageDialog(this, "Invalid Path or Path not Choosen Yet!!", "Error", JOptionPane.ERROR_MESSAGE);
        }
        
        
    }//GEN-LAST:event_jButton_downloadActionPerformed

    private void jLabel_profileMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel_profileMouseClicked
        
        account_Profile form = new account_Profile();
        form.setVisible(true);
        form.pack();
        form.setLocationRelativeTo(null);
        //close the current form (login form)
        this.dispose();
        
    }//GEN-LAST:event_jLabel_profileMouseClicked

    private void jButton_ModeWiseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_ModeWiseActionPerformed
        
        account_ModeWise_View form = new account_ModeWise_View();
        form.setVisible(true);
        form.pack();
        form.setLocationRelativeTo(null);
        //close the current form (login form)
        this.dispose();
        
    }//GEN-LAST:event_jButton_ModeWiseActionPerformed

    private void jButton_Add_Tmp_tblActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_Add_Tmp_tblActionPerformed
        
        String prodt_nm = jComboBox_prodt_Nm.getSelectedItem().toString();
        String Prodt_qty = jTextField_prodt_Qty.getText();
        String selling_price = jTextField_SellingPrice.getText();
        String ttl_price = jTextField_ttl_Price.getText();
        
        
        if(prodt_nm.isEmpty() || Prodt_qty.isEmpty() || Prodt_qty.equals("00") || selling_price.isEmpty() || selling_price.equals("00") || ttl_price.isEmpty() || ttl_price.equals("00")){
            ///////////////////////////////
            JOptionPane.showMessageDialog(null, "Some Field is Empty!!", "Error", JOptionPane.ERROR_MESSAGE);
            ///////////////////////////////
        }else{
            ////////////////////////////////////////////////////////////////////
            ////////////////////////////////////////////////////////////////////
            
            
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
                
                
                
                double present_qty = 00;
                PreparedStatement ps = con.prepareStatement("SELECT * FROM PRODUCT_TBL WHERE PRODUCT_NM = ?");
                ps.setString(1, prodt_nm);
                ResultSet rs = ps.executeQuery();
                while(rs.next()){
                    present_qty = Double.valueOf(rs.getString("PRODUCT_QTY"));
                }
                
                
                double prodt_qty = Double.valueOf(Prodt_qty);
                
                if(prodt_qty > present_qty){
                    
                    ////////////////////////////////////////////////////////////
                    JOptionPane.showMessageDialog(this, "Low Quantity!!", "Warning", JOptionPane.WARNING_MESSAGE);
                    ////////////////////////////////////////////////////////////
                    
                }else{
                    
                    DefaultTableModel model = (DefaultTableModel) jTable_tempStr_val.getModel();

                    model.addRow(new Object[]{
                        prodt_nm,
                        Prodt_qty,
                        selling_price,
                        ttl_price,
                        });
                    
                    
                    
                    
                    
                    String total_to_Pay = jLabel_Ttl_toPay.getText();
                    double Total_to_Pay = 00;
                    if(total_to_Pay.equals(".....")){
                        Total_to_Pay = 00;
                    }else{
                        Total_to_Pay = Double.valueOf(total_to_Pay);
                    }
                    
                    double final_total_to_pay = Total_to_Pay + Double.valueOf(ttl_price) ;
                    jLabel_Ttl_toPay.setText(String.valueOf(final_total_to_pay));
                    
                    
                    
                    
                    
                    
                    jComboBox_prodt_Nm.removeItem(prodt_nm);
                    jComboBox_prodt_Nm.setSelectedIndex(0);
                    
                    jTextField_prodt_Qty.setText("00");
                    jTextField_SellingPrice.setText("00");
                    jTextField_ttl_Price.setText("00");
                    
                    jLabel_Present_qty.setText(".....");
                    jLabel_Unit.setText(".....");
                    jLabel_perUnit.setText(".....");
                    
                    
                    
                }
                
                
                
                
                
                
                ///////////////////////////////////////////////////
                ///////////////////////////////////////////////////
                ///////////////////////////////////////////////////
                con.close();
            }
            catch(Exception ea){
                ea.printStackTrace();
            }
            
            
            ////////////////////////////////////////////////////////////////////
            ////////////////////////////////////////////////////////////////////
        }
        
        
    }//GEN-LAST:event_jButton_Add_Tmp_tblActionPerformed

    private void jButton_Remove_Tmp_tblActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_Remove_Tmp_tblActionPerformed
        
        DefaultTableModel tblModel = (DefaultTableModel) jTable_tempStr_val.getModel();

        try {
            ////////////////////////////////////////////////////////////////////

            //////   set product name which will be removed from temp table and add to jCombo box
            int il = jTable_tempStr_val.getSelectedRow();
            TableModel model = jTable_tempStr_val.getModel();
            String product_Nm = model.getValueAt(il, 0).toString();
            
            jComboBox_prodt_Nm.addItem(product_Nm);
            
            
            String ttl_Selling_price = model.getValueAt(il, 3).toString();
            double Ttl_Selling_price = Double.valueOf(ttl_Selling_price);
            String allTotal_to_pay = jLabel_Ttl_toPay.getText();
            double AllTotal_to_pay = Double.valueOf(allTotal_to_pay);
            
            double final_to_payNow = AllTotal_to_pay - Ttl_Selling_price ;
            String Final_to_payNow = String.valueOf(final_to_payNow);
            if(Final_to_payNow.equals("0.0")){
                jLabel_Ttl_toPay.setText(".....");
            }else{
                jLabel_Ttl_toPay.setText(Final_to_payNow);
            }
            

            //////////////    remove product item from temp Table
            int SelectedRowIndex = jTable_tempStr_val.getSelectedRow();
            tblModel.removeRow(SelectedRowIndex);

            ////////////////////////////////////////////////////////////////////
        } catch (Exception ea) {
            JOptionPane.showMessageDialog(this, " Configuration Failure!! ", "Error", JOptionPane.ERROR_MESSAGE);
        }
        
    }//GEN-LAST:event_jButton_Remove_Tmp_tblActionPerformed

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

    
    
    
    
    
    
    public void setVal_product(){
        ////////////////////////////////////////////////////////////////////////
        
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

            PreparedStatement ps = con.prepareStatement("SELECT * FROM PRODUCT_TBL");
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                String prod_Nm = rs.getString("PRODUCT_NM");
                jComboBox_prodt_Nm.addItem(prod_Nm);
            }
            
            ///////////////////////////////
            ///////////////////////////////
            ///////////////////////////////
            con.close();
        }
        catch(Exception ea){
            ea.printStackTrace();
        }
        
        ////////////////////////////////////////////////////////////////////////
    }
    
    
    public void generate_SellBillId() {
        ///////   set Generated value of Product ID
        java.util.Random r = new java.util.Random();
        int x = r.nextInt(99999);
        String SellId = "Sell-" + x + "IDNO";
        jLabel_sellID.setText(SellId);
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
            java.util.logging.Logger.getLogger(account_Sell.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(account_Sell.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(account_Sell.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(account_Sell.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new account_Sell().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton_Add_Tmp_tbl;
    private javax.swing.JButton jButton_For_Sell;
    private javax.swing.JButton jButton_ModeWise;
    private javax.swing.JButton jButton_Remove_Tmp_tbl;
    private javax.swing.JButton jButton_Search_btn;
    private javax.swing.JButton jButton_download;
    private javax.swing.JButton jButton_fr_Remove;
    private javax.swing.JButton jButton_fr_Reset;
    private javax.swing.JComboBox<String> jComboBox_modeUse;
    private javax.swing.JComboBox<String> jComboBox_prodt_Nm;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabel_Expenditure;
    private javax.swing.JLabel jLabel_Present_qty;
    private javax.swing.JLabel jLabel_Ttl_toPay;
    private javax.swing.JLabel jLabel_Unit;
    private javax.swing.JLabel jLabel_bill;
    private javax.swing.JLabel jLabel_close;
    private javax.swing.JLabel jLabel_dashboard;
    private javax.swing.JLabel jLabel_perUnit;
    private javax.swing.JLabel jLabel_product;
    private javax.swing.JLabel jLabel_production;
    private javax.swing.JLabel jLabel_profile;
    private javax.swing.JLabel jLabel_sell;
    private javax.swing.JLabel jLabel_sellID;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel_sellEntry_panel;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable_sellTbl;
    private javax.swing.JTable jTable_tempStr_val;
    private javax.swing.JTextField jTextField_SellingPrice;
    private javax.swing.JTextField jTextField_prodt_Qty;
    private javax.swing.JTextField jTextField_srchBr;
    private javax.swing.JTextField jTextField_ttl_Price;
    // End of variables declaration//GEN-END:variables
}
