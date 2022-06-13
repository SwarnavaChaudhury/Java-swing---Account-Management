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
public class account_AddProduct extends javax.swing.JFrame {

    /////  create border
    Border textFile_border = BorderFactory.createMatteBorder(0, 0, 2, 0, Color.red);
    Border textFile_borderNo = BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(0,204,204));

    /**
     * Creates new form account_DashBoard
     */
    public account_AddProduct() {
        initComponents();

        //  center form
        this.setLocationRelativeTo(null);
        
        ////  set border
        jLabel_Purchase_Gross_TTL.setBorder(textFile_border);

        //////  set icon
        jLabel_profile.setIcon(new ImageIcon(getClass().getResource("IMAGES/useron.png")));

        //// make dashboard selected from sidebar
        jLabel_productMouseEntered(null);

        //////  make total price jTextField non-Editable
        jTextField_totalPrice.setEditable(false);

        ///////  set border to all jTextField
        jTextField_Purchase_price.setBorder(textFile_border);
        jTextField_purchase_quantity.setBorder(textFile_border);
        jTextField_totalPrice.setBorder(textFile_border);
        jTextField_searchPurchaseBill.setBorder(textFile_border);

        //////  generate purchase id
        generatePurchase();

        /////   Load data to jTable
        show_purchase();

        ///////  add value of jCombo Box
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
            while (rs.next()) {
                String proNm = rs.getString("PRODUCT_NM");
                jComboBox_selectProduct.addItem(proNm);
            }
            
            ////////////////////////////////////////////////
            ////////////////////////////////////////////////
            ////////////////////////////////////////////////
            con.close();
            
        } catch (Exception ea) {
            ea.printStackTrace();
        }

    }

    /////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////////
    ////  ~~~~~~~~~   Show data of jTable
    public ArrayList<purchase> purchaseList() {
        ArrayList<purchase> purchaseList = new ArrayList<>();

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

            purchase purchase;
            
            double gross_ttl = 00;

            PreparedStatement ps = con.prepareStatement("SELECT PURCHASE_TBL.PURIDNO, PRODUCT_TBL.PRODUCT_NM, UNIT_TBL.UNIT_SYMBOL, PURCHASE_TBL.PRODUCT_PRICE,TTL_PRICE,PUR_SESSION,PUR_MONTH,PUR_DTTM,PUR_QTY FROM UNIT_TBL,PRODUCT_TBL,PURCHASE_TBL WHERE UNIT_TBL.UNIT_IDNO=PRODUCT_TBL.UNIT_IDNO AND PRODUCT_TBL.PROIDNO=PURCHASE_TBL.PROIDNO");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                
                gross_ttl += Double.valueOf(rs.getString("TTL_PRICE"));
                
                purchase = new purchase(rs.getString("PURIDNO"), rs.getString("PRODUCT_NM"), rs.getString("UNIT_SYMBOL"), rs.getString("PUR_QTY"), rs.getString("PRODUCT_PRICE"), rs.getString("TTL_PRICE"), rs.getString("PUR_DTTM"));
                purchaseList.add(purchase);
            }
            
            jLabel_Purchase_Gross_TTL.setText(String.valueOf(gross_ttl)+" /-");
            
            /////////////////////////////////////////////
            /////////////////////////////////////////////
            /////////////////////////////////////////////
            con.close();
        } catch (Exception ea) {
            ea.printStackTrace();
        }
        ////////////////////////////////////////////////////////////////////////
        return purchaseList;
        ////////////////////////////////////////////////////////////////////////
    }

    public void show_purchase() {
        ArrayList<purchase> purchaseLst = purchaseList();
        DefaultTableModel model = (DefaultTableModel) jTable_all_Purchase_Bill.getModel();
        Object[] row = new Object[7];
        for (int i = 0; i < purchaseLst.size(); i++) {
            row[0] = purchaseLst.get(i).getpurchaseid();
            row[1] = purchaseLst.get(i).getproductname();
            row[2] = purchaseLst.get(i).getuniname();
            row[3] = purchaseLst.get(i).getquantity();
            row[4] = purchaseLst.get(i).getperPrice();
            row[5] = purchaseLst.get(i).getttlPrice();
            row[6] = purchaseLst.get(i).getpur_dttm();
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
    /////////    ~~~~~~~~~~~~~~~   Search Result jTable
    
    public ArrayList<purchase> purchaseList_search() {
        ArrayList<purchase> purchaseList = new ArrayList<>();

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

            purchase purchase;
            
            double gross_ttl = 00;

            String searchRslt = "%"+jTextField_searchPurchaseBill.getText()+"%";
            
            PreparedStatement ps = con.prepareStatement("SELECT PURCHASE_TBL.PURIDNO, PRODUCT_TBL.PRODUCT_NM, UNIT_TBL.UNIT_SYMBOL, PURCHASE_TBL.PRODUCT_PRICE,TTL_PRICE,PUR_SESSION,PUR_MONTH,PUR_DTTM,PUR_QTY FROM UNIT_TBL,PRODUCT_TBL,PURCHASE_TBL WHERE UNIT_TBL.UNIT_IDNO=PRODUCT_TBL.UNIT_IDNO AND PRODUCT_TBL.PROIDNO=PURCHASE_TBL.PROIDNO AND ( PURCHASE_TBL.PURIDNO LIKE ? OR PURCHASE_TBL.PUR_DTTM LIKE ? OR PRODUCT_TBL.PRODUCT_NM LIKE ? )");
            ps.setString(1, searchRslt);
            ps.setString(2, searchRslt);
            ps.setString(3, searchRslt);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                
                gross_ttl += Double.valueOf(rs.getString("TTL_PRICE"));
                
                purchase = new purchase(rs.getString("PURIDNO"), rs.getString("PRODUCT_NM"), rs.getString("UNIT_SYMBOL"), rs.getString("PUR_QTY"), rs.getString("PRODUCT_PRICE"), rs.getString("TTL_PRICE"), rs.getString("PUR_DTTM"));
                purchaseList.add(purchase);
            }
            
            jLabel_Purchase_Gross_TTL.setText(String.valueOf(gross_ttl)+" /-");
            
            ///////////////////////////////////////
            ///////////////////////////////////////
            ///////////////////////////////////////
            con.close();
            
        } catch (Exception ea) {
            ea.printStackTrace();
        }
        ////////////////////////////////////////////////////////////////////////
        return purchaseList;
        ////////////////////////////////////////////////////////////////////////
    }

    public void show_purchase_search() {
        ArrayList<purchase> purchaseLst = purchaseList_search();
        DefaultTableModel model = (DefaultTableModel) jTable_all_Purchase_Bill.getModel();
        
        model.setRowCount(0);
        
        Object[] row = new Object[7];
        for (int i = 0; i < purchaseLst.size(); i++) {
            row[0] = purchaseLst.get(i).getpurchaseid();
            row[1] = purchaseLst.get(i).getproductname();
            row[2] = purchaseLst.get(i).getuniname();
            row[3] = purchaseLst.get(i).getquantity();
            row[4] = purchaseLst.get(i).getperPrice();
            row[5] = purchaseLst.get(i).getttlPrice();
            row[6] = purchaseLst.get(i).getpur_dttm();
            model.addRow(row);
        }
    }

    
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
        jLabel_profile = new javax.swing.JLabel();
        jLabel_close = new javax.swing.JLabel();
        jButton_AddProduct_section = new javax.swing.JButton();
        jButton_AddUnit_Section = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jButton_Add_purchase_bill = new javax.swing.JButton();
        jComboBox_selectProduct = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        jLabel_showUnit = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jTextField_Purchase_price = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jTextField_purchase_quantity = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jTextField_totalPrice = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel_genProID = new javax.swing.JLabel();
        jButton_Clear_Purchase_AllField = new javax.swing.JButton();
        jButton_Delete_PurchaseBill = new javax.swing.JButton();
        jButton_update_Purchase = new javax.swing.JButton();
        jLabel_ShowUnit_su = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable_all_Purchase_Bill = new javax.swing.JTable();
        jTextField_searchPurchaseBill = new javax.swing.JTextField();
        jButton_SearchPurchaseBill = new javax.swing.JButton();
        jButton_Download_Purchase_Data = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel_Purchase_Gross_TTL = new javax.swing.JLabel();

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

        jLabel_profile.setBackground(new java.awt.Color(255, 255, 255));
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

        jButton_AddProduct_section.setBackground(new java.awt.Color(255, 204, 0));
        jButton_AddProduct_section.setFont(new java.awt.Font("Trebuchet MS", 3, 20)); // NOI18N
        jButton_AddProduct_section.setForeground(new java.awt.Color(255, 255, 255));
        jButton_AddProduct_section.setIcon(new javax.swing.ImageIcon(getClass().getResource("/account_maintainer/IMAGES/product.png"))); // NOI18N
        jButton_AddProduct_section.setText("Add Product Item");
        jButton_AddProduct_section.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton_AddProduct_section.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jButton_AddProduct_sectionMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jButton_AddProduct_sectionMouseExited(evt);
            }
        });
        jButton_AddProduct_section.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_AddProduct_sectionActionPerformed(evt);
            }
        });

        jButton_AddUnit_Section.setBackground(new java.awt.Color(255, 204, 0));
        jButton_AddUnit_Section.setFont(new java.awt.Font("Trebuchet MS", 3, 20)); // NOI18N
        jButton_AddUnit_Section.setForeground(new java.awt.Color(255, 255, 255));
        jButton_AddUnit_Section.setIcon(new javax.swing.ImageIcon(getClass().getResource("/account_maintainer/IMAGES/unit.png"))); // NOI18N
        jButton_AddUnit_Section.setText("Add Unit");
        jButton_AddUnit_Section.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton_AddUnit_Section.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jButton_AddUnit_SectionMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jButton_AddUnit_SectionMouseExited(evt);
            }
        });
        jButton_AddUnit_Section.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_AddUnit_SectionActionPerformed(evt);
            }
        });

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(26, 178, 255)));

        jLabel1.setFont(new java.awt.Font("Tempus Sans ITC", 3, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(26, 198, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Purchase Entry");

        jButton_Add_purchase_bill.setBackground(new java.awt.Color(64, 255, 0));
        jButton_Add_purchase_bill.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        jButton_Add_purchase_bill.setForeground(new java.awt.Color(255, 255, 255));
        jButton_Add_purchase_bill.setIcon(new javax.swing.ImageIcon(getClass().getResource("/account_maintainer/IMAGES/plusIcn.png"))); // NOI18N
        jButton_Add_purchase_bill.setText("Store");
        jButton_Add_purchase_bill.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton_Add_purchase_bill.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_Add_purchase_billActionPerformed(evt);
            }
        });

        jComboBox_selectProduct.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jComboBox_selectProduct.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBox_selectProductItemStateChanged(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(51, 102, 255));
        jLabel2.setText("Product");

        jLabel_showUnit.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jLabel_showUnit.setForeground(new java.awt.Color(51, 153, 255));
        jLabel_showUnit.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(51, 102, 255));
        jLabel3.setText("Price");

        jTextField_Purchase_price.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jTextField_Purchase_price.setText("00");
        jTextField_Purchase_price.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextField_Purchase_priceKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField_Purchase_priceKeyReleased(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(51, 102, 255));
        jLabel4.setText("Quantity");

        jTextField_purchase_quantity.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jTextField_purchase_quantity.setText("00");
        jTextField_purchase_quantity.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextField_purchase_quantityKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField_purchase_quantityKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField_purchase_quantityKeyTyped(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(51, 102, 255));
        jLabel5.setText("Total Price");

        jTextField_totalPrice.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jTextField_totalPrice.setText("00");
        jTextField_totalPrice.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextField_totalPriceKeyPressed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(51, 102, 255));
        jLabel6.setText("Purchase ID");

        jLabel_genProID.setFont(new java.awt.Font("Times New Roman", 2, 18)); // NOI18N
        jLabel_genProID.setForeground(new java.awt.Color(0, 153, 255));
        jLabel_genProID.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        jButton_Clear_Purchase_AllField.setBackground(new java.awt.Color(51, 102, 255));
        jButton_Clear_Purchase_AllField.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        jButton_Clear_Purchase_AllField.setForeground(new java.awt.Color(255, 255, 255));
        jButton_Clear_Purchase_AllField.setIcon(new javax.swing.ImageIcon(getClass().getResource("/account_maintainer/IMAGES/reset.png"))); // NOI18N
        jButton_Clear_Purchase_AllField.setText("Reset");
        jButton_Clear_Purchase_AllField.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton_Clear_Purchase_AllField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_Clear_Purchase_AllFieldActionPerformed(evt);
            }
        });

        jButton_Delete_PurchaseBill.setBackground(new java.awt.Color(255, 0, 0));
        jButton_Delete_PurchaseBill.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        jButton_Delete_PurchaseBill.setForeground(new java.awt.Color(255, 255, 255));
        jButton_Delete_PurchaseBill.setIcon(new javax.swing.ImageIcon(getClass().getResource("/account_maintainer/IMAGES/delete.png"))); // NOI18N
        jButton_Delete_PurchaseBill.setText("Remove");
        jButton_Delete_PurchaseBill.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton_Delete_PurchaseBill.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_Delete_PurchaseBillActionPerformed(evt);
            }
        });

        jButton_update_Purchase.setBackground(new java.awt.Color(255, 204, 0));
        jButton_update_Purchase.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        jButton_update_Purchase.setForeground(new java.awt.Color(255, 255, 255));
        jButton_update_Purchase.setIcon(new javax.swing.ImageIcon(getClass().getResource("/account_maintainer/IMAGES/update.png"))); // NOI18N
        jButton_update_Purchase.setText("Update");
        jButton_update_Purchase.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton_update_Purchase.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_update_PurchaseActionPerformed(evt);
            }
        });

        jLabel_ShowUnit_su.setBackground(new java.awt.Color(255, 255, 255));
        jLabel_ShowUnit_su.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jLabel_ShowUnit_su.setForeground(new java.awt.Color(51, 153, 255));
        jLabel_ShowUnit_su.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel4Layout.createSequentialGroup()
                                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(18, 18, 18)
                                    .addComponent(jTextField_totalPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 223, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(18, 18, 18)
                                    .addComponent(jComboBox_selectProduct, javax.swing.GroupLayout.PREFERRED_SIZE, 223, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanel4Layout.createSequentialGroup()
                                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(18, 18, 18)
                                    .addComponent(jLabel_genProID, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanel4Layout.createSequentialGroup()
                                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(18, 18, 18)
                                    .addComponent(jTextField_purchase_quantity, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jLabel_ShowUnit_su, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(18, 18, 18)
                                    .addComponent(jTextField_Purchase_price, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jLabel_showUnit, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jButton_Clear_Purchase_AllField, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jButton_Delete_PurchaseBill, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jButton_update_Purchase, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jButton_Add_purchase_bill, javax.swing.GroupLayout.DEFAULT_SIZE, 161, Short.MAX_VALUE))))
                        .addGap(0, 23, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel_genProID, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox_selectProduct, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(19, 19, 19)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel_showUnit, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jTextField_Purchase_price, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jTextField_purchase_quantity, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel_ShowUnit_su, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField_totalPrice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButton_Add_purchase_bill, javax.swing.GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE)
                    .addComponent(jButton_Clear_Purchase_AllField, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(30, 30, 30)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButton_update_Purchase, javax.swing.GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE)
                    .addComponent(jButton_Delete_PurchaseBill, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTable_all_Purchase_Bill.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Purchase ID", "Product Name", "Unit", "Quantity", "Price", "Total Price", "Add At"
            }
        ));
        jTable_all_Purchase_Bill.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable_all_Purchase_BillMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTable_all_Purchase_Bill);

        jTextField_searchPurchaseBill.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jTextField_searchPurchaseBill.setForeground(new java.awt.Color(204, 204, 204));
        jTextField_searchPurchaseBill.setText(" Search by Product Name or Purchase Bill No or Date/Time...");
        jTextField_searchPurchaseBill.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTextField_searchPurchaseBillFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTextField_searchPurchaseBillFocusLost(evt);
            }
        });

        jButton_SearchPurchaseBill.setBackground(new java.awt.Color(51, 255, 0));
        jButton_SearchPurchaseBill.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jButton_SearchPurchaseBill.setIcon(new javax.swing.ImageIcon(getClass().getResource("/account_maintainer/IMAGES/searchOn.png"))); // NOI18N
        jButton_SearchPurchaseBill.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton_SearchPurchaseBill.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jButton_SearchPurchaseBillMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jButton_SearchPurchaseBillMouseExited(evt);
            }
        });
        jButton_SearchPurchaseBill.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_SearchPurchaseBillActionPerformed(evt);
            }
        });

        jButton_Download_Purchase_Data.setBackground(new java.awt.Color(0, 204, 204));
        jButton_Download_Purchase_Data.setFont(new java.awt.Font("Sylfaen", 1, 22)); // NOI18N
        jButton_Download_Purchase_Data.setForeground(new java.awt.Color(255, 255, 255));
        jButton_Download_Purchase_Data.setText("Download");
        jButton_Download_Purchase_Data.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton_Download_Purchase_Data.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_Download_Purchase_DataActionPerformed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Tempus Sans ITC", 3, 24)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText("Purchase Bill");

        jLabel8.setBackground(new java.awt.Color(255, 255, 255));
        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 22)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(0, 153, 255));
        jLabel8.setText("Gross Total");

        jLabel_Purchase_Gross_TTL.setBackground(new java.awt.Color(255, 255, 255));
        jLabel_Purchase_Gross_TTL.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLabel_Purchase_Gross_TTL.setForeground(new java.awt.Color(0, 153, 255));
        jLabel_Purchase_Gross_TTL.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel_Purchase_Gross_TTL.setText(".....");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addComponent(jTextField_searchPurchaseBill, javax.swing.GroupLayout.DEFAULT_SIZE, 545, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton_SearchPurchaseBill, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButton_AddUnit_Section)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton_AddProduct_section)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel_profile, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel_close, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jButton_Download_Purchase_Data, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel8)
                        .addGap(44, 44, 44)
                        .addComponent(jLabel_Purchase_Gross_TTL, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel_close, javax.swing.GroupLayout.DEFAULT_SIZE, 43, Short.MAX_VALUE)
                    .addComponent(jLabel_profile, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jButton_AddProduct_section, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton_AddUnit_Section, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 18, Short.MAX_VALUE)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jTextField_searchPurchaseBill)
                    .addComponent(jButton_SearchPurchaseBill, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 278, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButton_Download_Purchase_Data, javax.swing.GroupLayout.DEFAULT_SIZE, 49, Short.MAX_VALUE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel_Purchase_Gross_TTL, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap(20, Short.MAX_VALUE)
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
                .addGap(12, 12, 12))
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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

    private void jButton_AddProduct_sectionMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton_AddProduct_sectionMouseEntered

        jButton_AddProduct_section.setForeground(new Color(255, 204, 0));
        jButton_AddProduct_section.setBackground(new Color(255, 255, 255));

    }//GEN-LAST:event_jButton_AddProduct_sectionMouseEntered

    private void jButton_AddProduct_sectionMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton_AddProduct_sectionMouseExited

        jButton_AddProduct_section.setForeground(new Color(255, 255, 255));
        jButton_AddProduct_section.setBackground(new Color(255, 204, 0));

    }//GEN-LAST:event_jButton_AddProduct_sectionMouseExited

    private void jButton_AddUnit_SectionMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton_AddUnit_SectionMouseEntered

        jButton_AddUnit_Section.setForeground(new Color(255, 204, 0));
        jButton_AddUnit_Section.setBackground(new Color(255, 255, 255));

    }//GEN-LAST:event_jButton_AddUnit_SectionMouseEntered

    private void jButton_AddUnit_SectionMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton_AddUnit_SectionMouseExited

        jButton_AddUnit_Section.setForeground(new Color(255, 255, 255));
        jButton_AddUnit_Section.setBackground(new Color(255, 204, 0));

    }//GEN-LAST:event_jButton_AddUnit_SectionMouseExited

    private void jButton_SearchPurchaseBillMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton_SearchPurchaseBillMouseEntered

        jButton_SearchPurchaseBill.setBackground(new Color(128, 255, 128));

    }//GEN-LAST:event_jButton_SearchPurchaseBillMouseEntered

    private void jButton_SearchPurchaseBillMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton_SearchPurchaseBillMouseExited

        jButton_SearchPurchaseBill.setBackground(new Color(51, 255, 0));

    }//GEN-LAST:event_jButton_SearchPurchaseBillMouseExited

    private void jComboBox_selectProductItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox_selectProductItemStateChanged

        /////////////////////  show unit of product
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

            String prodNm = jComboBox_selectProduct.getSelectedItem().toString();

            PreparedStatement ps = con.prepareStatement("SELECT * FROM PRODUCT_TBL WHERE PRODUCT_NM = ?");
            ps.setString(1, prodNm);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String UnitID = rs.getString("UNIT_IDNO");
                PreparedStatement ps1 = con.prepareStatement("SELECT * FROM UNIT_TBL WHERE UNIT_IDNO = ?");
                ps1.setString(1, UnitID);
                ResultSet rs1 = ps1.executeQuery();
                while (rs1.next()) {
                    String UnitSrtNm = rs1.getString("UNIT_SYMBOL");
                    jLabel_showUnit.setText(" Rs./" + UnitSrtNm);
                    jLabel_ShowUnit_su.setText(UnitSrtNm);
                }
            }

        } catch (Exception ea) {
            ea.printStackTrace();
        }


    }//GEN-LAST:event_jComboBox_selectProductItemStateChanged

    private void jLabel_dashboardMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel_dashboardMouseClicked

        account_DashBoard form = new account_DashBoard();
        form.setVisible(true);
        form.pack();
        form.setLocationRelativeTo(null);
        //close the current form (login form)
        this.dispose();

    }//GEN-LAST:event_jLabel_dashboardMouseClicked

    private void jTextField_purchase_quantityKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField_purchase_quantityKeyPressed

        if (evt.getKeyChar() >= '0' && evt.getKeyChar() <= '9' || evt.getKeyCode() == KeyEvent.VK_BACK_SPACE || evt.getKeyChar() == KeyEvent.VK_DECIMAL || evt.getKeyChar() == KeyEvent.VK_PERIOD) {
            jTextField_purchase_quantity.setEditable(true);
        } else {
            jTextField_purchase_quantity.setEditable(false);
        }

    }//GEN-LAST:event_jTextField_purchase_quantityKeyPressed

    private void jTextField_purchase_quantityKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField_purchase_quantityKeyTyped

        ////////////////////////////
        ////////////////////////////

    }//GEN-LAST:event_jTextField_purchase_quantityKeyTyped

    private void jTextField_purchase_quantityKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField_purchase_quantityKeyReleased

        String price = jTextField_Purchase_price.getText();
        double priceDbl = Double.parseDouble(price);
        String quantity = jTextField_purchase_quantity.getText();
        double quantityDbl = Double.parseDouble(quantity);

        double total_price = priceDbl * quantityDbl;
        String total_priceStr = String.valueOf(String.format("%.2f", total_price));

        jTextField_totalPrice.setText(total_priceStr);

    }//GEN-LAST:event_jTextField_purchase_quantityKeyReleased

    private void jTextField_Purchase_priceKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField_Purchase_priceKeyPressed

        if (evt.getKeyChar() >= '0' && evt.getKeyChar() <= '9' || evt.getKeyCode() == KeyEvent.VK_BACK_SPACE || evt.getKeyChar() == KeyEvent.VK_DECIMAL || evt.getKeyChar() == KeyEvent.VK_PERIOD) {
            jTextField_Purchase_price.setEditable(true);
        } else {
            jTextField_Purchase_price.setEditable(false);
        }

    }//GEN-LAST:event_jTextField_Purchase_priceKeyPressed

    private void jTextField_totalPriceKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField_totalPriceKeyPressed

        if (evt.getKeyChar() >= '0' && evt.getKeyChar() <= '9' || evt.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
            jTextField_totalPrice.setEditable(true);
        } else {
            jTextField_totalPrice.setEditable(false);
        }

    }//GEN-LAST:event_jTextField_totalPriceKeyPressed

    private void jButton_Add_purchase_billActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_Add_purchase_billActionPerformed

        String PurId = jLabel_genProID.getText();
        String productNm = jComboBox_selectProduct.getSelectedItem().toString();
        String per_purchase_price = jTextField_Purchase_price.getText();
        String Total_quantity = jTextField_purchase_quantity.getText();
        String total_price = jTextField_totalPrice.getText();

        if (
                PurId.isEmpty() || productNm.isEmpty() || 
                per_purchase_price.isEmpty() || Total_quantity.isEmpty() || 
                total_price.isEmpty() || per_purchase_price.equals("00") || 
                Total_quantity.equals("00") || total_price.equals("00")
                ) {
            /////////////////////////
            /////////////////////////
            JOptionPane.showMessageDialog(null, " Some Field is Empty!! ", "Warning", JOptionPane.WARNING_MESSAGE);
            /////////////////////////
            /////////////////////////
        } else {

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

                PreparedStatement srchPurID = con.prepareStatement("SELECT * FROM PURCHASE_TBL WHERE PURIDNO = ?");
                srchPurID.setString(1, PurId);
                int srch = srchPurID.executeUpdate();
                if (srch > 0) {

                    /////////////////////////////////////////
                    ////////   ~~~~~~~~~~~~~~~~~  ///////////
                    JOptionPane.showMessageDialog(null, " Duplicate Value Not Allow!! ", "Warning!", JOptionPane.WARNING_MESSAGE);

                    ////////   ~~~~~~~~~~~~~~~~~  ///////////
                    /////////////////////////////////////////
                } else {
                    ////////////////////////////////////////////////////////////////////
                    ////////////////////////////////////////////////////////////////////
                    ////////////////////////////////////////////////////////////////////

                    String productIdd = "";
                    PreparedStatement ps = con.prepareStatement("SELECT * FROM PRODUCT_TBL WHERE PRODUCT_NM = ?");
                    ps.setString(1, productNm);
                    ResultSet rs = ps.executeQuery();
                    while (rs.next()) {
                        productIdd = rs.getString("PROIDNO");
                    }

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
                    /////  Add purchase Bill
                    PreparedStatement ps1 = con.prepareStatement("INSERT INTO PURCHASE_TBL(PURIDNO, PROIDNO, PRODUCT_PRICE, TTL_PRICE, PUR_SESSION, PUR_MONTH, PUR_DTTM, PUR_QTY) VALUES(?, ?, ?, ?, ?, ?, ?, ?)");
                    ps1.setString(1, PurId);
                    ps1.setString(2, productIdd);
                    ps1.setString(3, per_purchase_price);
                    ps1.setString(4, total_price);
                    ps1.setString(5, Current_Year);
                    ps1.setString(6, Current_Month);
                    ps1.setString(7, presentDateTime);
                    ps1.setString(8, Total_quantity);

                    int i = ps1.executeUpdate();

                    if (i > 0) {
                        ////////////////////////////////////////////////////////////
                        //////   if purchase bill is added....
                        //////   update product table quantity also...
                        PreparedStatement ps2 = con.prepareStatement("SELECT * FROM PRODUCT_TBL WHERE PROIDNO = ?");
                        ps2.setString(1, productIdd);
                        ResultSet rs2 = ps2.executeQuery();
                        while (rs2.next()) {
                            /////  get old value of product quantity
                            String store_product_quantity = rs2.getString("PRODUCT_QTY");
                            double Store_Product_Quantity = Double.valueOf(store_product_quantity);

                            double total_quantity = Double.valueOf(Total_quantity);
                            /////  add old quantity with new quantity
                            double update_quantity = Store_Product_Quantity + total_quantity;
                            String Update_Quantity = String.valueOf(update_quantity);

                            ///////////   update product table set new value of product table
                            PreparedStatement ps3 = con.prepareStatement("UPDATE PRODUCT_TBL SET PRODUCT_QTY = ? WHERE PROIDNO = ?");
                            ps3.setString(1, Update_Quantity);
                            ps3.setString(2, productIdd);
                            int j = ps3.executeUpdate();
                            if (j > 0) {

                                JOptionPane.showMessageDialog(null, " Purchase Bill Added Successfully... Bill Number : " + PurId, "Success!", JOptionPane.INFORMATION_MESSAGE);
                                generatePurchase();
                                jComboBox_selectProduct.setSelectedIndex(0);
                                jTextField_Purchase_price.setText("");
                                jTextField_purchase_quantity.setText("");
                                jTextField_totalPrice.setText("");

                                ////////////////
                                ////////////////
                                DefaultTableModel model = (DefaultTableModel) jTable_all_Purchase_Bill.getModel();
                                model.setRowCount(0);
                                show_purchase();         ///////////////  for refresh table
                                ////////////////
                                ////////////////

                            } else {
                                JOptionPane.showMessageDialog(null, " Error Occurred!! Please Try Again... ", "Error!", JOptionPane.ERROR_MESSAGE);
                            }
                        }

                        ////////////////////////////////////////////////////////////
                    } else {
                        JOptionPane.showMessageDialog(null, " Error Occurred!! Please Try Again... ", "Error!", JOptionPane.ERROR_MESSAGE);
                    }

                    ////////////////////////////////////////////////////////////////////
                    ////////////////////////////////////////////////////////////////////
                    ////////////////////////////////////////////////////////////////////
                }
                
                con.close();
                
            } catch (Exception ea) {
                ea.printStackTrace();
            }

        }

    }//GEN-LAST:event_jButton_Add_purchase_billActionPerformed

    private void jButton_AddUnit_SectionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_AddUnit_SectionActionPerformed

        account_AddUnit_Sctn form = new account_AddUnit_Sctn();
        form.setVisible(true);
        form.pack();
        form.setLocationRelativeTo(null);
        //close the current form (login form)
        this.dispose();

    }//GEN-LAST:event_jButton_AddUnit_SectionActionPerformed

    private void jButton_AddProduct_sectionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_AddProduct_sectionActionPerformed

        account_AddProduct_Sctn form = new account_AddProduct_Sctn();
        form.setVisible(true);
        form.pack();
        form.setLocationRelativeTo(null);
        //close the current form (login form)
        this.dispose();

    }//GEN-LAST:event_jButton_AddProduct_sectionActionPerformed

    private void jButton_Clear_Purchase_AllFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_Clear_Purchase_AllFieldActionPerformed

        generatePurchase();
        jComboBox_selectProduct.setSelectedIndex(0);
        jTextField_Purchase_price.setText("");
        jTextField_purchase_quantity.setText("");
        jTextField_totalPrice.setText("");
        jTextField_searchPurchaseBill.setText(" Search by Product Name or Purchase Bill No or Date/Time...");

        ////////////////
        ////////////////
        DefaultTableModel model = (DefaultTableModel) jTable_all_Purchase_Bill.getModel();
        model.setRowCount(0);
        show_purchase();         ///////////////  for refresh table
        ////////////////
        ////////////////

    }//GEN-LAST:event_jButton_Clear_Purchase_AllFieldActionPerformed

    private void jTable_all_Purchase_BillMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable_all_Purchase_BillMouseClicked

        int il = jTable_all_Purchase_Bill.getSelectedRow();
        TableModel model = jTable_all_Purchase_Bill.getModel();
        jLabel_genProID.setText(model.getValueAt(il, 0).toString());
        jComboBox_selectProduct.setSelectedItem(model.getValueAt(il, 1).toString());
        jTextField_Purchase_price.setText(model.getValueAt(il, 4).toString());
        jTextField_purchase_quantity.setText(model.getValueAt(il, 3).toString());
        jTextField_totalPrice.setText(model.getValueAt(il, 5).toString());

    }//GEN-LAST:event_jTable_all_Purchase_BillMouseClicked

    private void jButton_update_PurchaseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_update_PurchaseActionPerformed

        String PurId = jLabel_genProID.getText();
        String productNm = jComboBox_selectProduct.getSelectedItem().toString();
        String per_purchase_price = jTextField_Purchase_price.getText();
        String Total_quantity = jTextField_purchase_quantity.getText();
        String total_price = jTextField_totalPrice.getText();

        if (
                PurId.isEmpty() || productNm.isEmpty() || 
                per_purchase_price.isEmpty() || Total_quantity.isEmpty() || 
                total_price.isEmpty() || per_purchase_price.equals("00") || 
                Total_quantity.equals("00") || total_price.equals("00")
                ) {
            /////////////////////////
            /////////////////////////
            JOptionPane.showMessageDialog(null, " Some Field is Empty!! ", "Warning", JOptionPane.WARNING_MESSAGE);
            /////////////////////////
            /////////////////////////
        } else {

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

                PreparedStatement cp = con.prepareStatement("SELECT * FROM PURCHASE_TBL WHERE PURIDNO = ?");
                cp.setString(1, PurId);
                int p = cp.executeUpdate();
                if (p > 0) {

                    ////////////////////////////////////////////////////////////////////////
                    ////////////////////////////////////////////////////////////////////////
                    ////////////////////////////////////////////////////////////////////////
                    ////////////////////////////////////////////////////////////////////////
                    ///////////////////////////////////////////
                    ////////  Variable Declaration
                    String oldpurQuantity = "";
                    double OldpurQuantity = 0;
                    double NewpurQuantity = 0;
                    double getDifference = 0;
                    ///////////////////////////////////////////

                    ///old value of purchase quantity which is already store in database
                    PreparedStatement ps = con.prepareStatement("SELECT * FROM PURCHASE_TBL WHERE PURIDNO = ?");
                    ps.setString(1, PurId);
                    ResultSet rs = ps.executeQuery();
                    while (rs.next()) {
                        oldpurQuantity = rs.getString("PUR_QTY");
                    }
                    OldpurQuantity = Double.valueOf(oldpurQuantity);
                    NewpurQuantity = Double.valueOf(Total_quantity);

                    if (OldpurQuantity > NewpurQuantity) {

                        getDifference = OldpurQuantity - NewpurQuantity;

                        PreparedStatement ps1 = con.prepareStatement("SELECT * FROM PRODUCT_TBL WHERE PRODUCT_NM = ?");
                        ps1.setString(1, productNm);
                        ResultSet rs1 = ps1.executeQuery();
                        while (rs1.next()) {
                            String proQuan = rs1.getString("PRODUCT_QTY");
                            double ProQuan = Double.valueOf(proQuan);
                            double updateProQuan = ProQuan - getDifference;
                            String UpdateProQuan = String.valueOf(updateProQuan);

                            ////////   update value of product __ table
                            PreparedStatement ps2 = con.prepareStatement("UPDATE PRODUCT_TBL SET PRODUCT_QTY = ? WHERE PRODUCT_NM = ?");
                            ps2.setString(1, UpdateProQuan);
                            ps2.setString(2, productNm);
                            int i = ps2.executeUpdate();
                            if (i > 0) {

                                //////  GET VALUE ID OF PRODUCT
                                PreparedStatement ps3 = con.prepareStatement("SELECT * FROM PRODUCT_TBL WHERE PRODUCT_NM = ?");
                                ps3.setString(1, productNm);
                                ResultSet rs3 = ps3.executeQuery();
                                while (rs3.next()) {
                                    String productID = rs3.getString("PROIDNO");

                                    //////  update values of purchase__table
                                    PreparedStatement ps4 = con.prepareStatement("UPDATE PURCHASE_TBL SET PROIDNO = ?,  PRODUCT_PRICE = ?, TTL_PRICE = ?, PUR_QTY = ? WHERE PURIDNO = ?");
                                    ps4.setString(1, productID);
                                    ps4.setString(2, per_purchase_price);
                                    ps4.setString(3, total_price);
                                    ps4.setString(4, Total_quantity);
                                    ps4.setString(5, PurId);

                                    int j = ps4.executeUpdate();
                                    if (j > 0) {
                                        JOptionPane.showMessageDialog(null, " Purchase Bill Update Complete... ", "Success", JOptionPane.INFORMATION_MESSAGE);

                                        generatePurchase();
                                        jComboBox_selectProduct.setSelectedIndex(0);
                                        jTextField_Purchase_price.setText("");
                                        jTextField_purchase_quantity.setText("");
                                        jTextField_totalPrice.setText("");

                                        ////////////////
                                        ////////////////
                                        DefaultTableModel model = (DefaultTableModel) jTable_all_Purchase_Bill.getModel();
                                        model.setRowCount(0);
                                        show_purchase();         ///////////////  for refresh table
                                        ////////////////
                                        ////////////////

                                    } else {
                                        JOptionPane.showMessageDialog(null, " Error Occurred!! Please Try Again... ", "Error", JOptionPane.ERROR_MESSAGE);
                                    }

                                }

                            } else {
                                JOptionPane.showMessageDialog(null, " Error Occurred!! Please Try Again... ", "Error", JOptionPane.ERROR_MESSAGE);
                            }

                        }

                    } else if (NewpurQuantity > OldpurQuantity) {

                        getDifference = NewpurQuantity - OldpurQuantity;

                        PreparedStatement ps1 = con.prepareStatement("SELECT * FROM PRODUCT_TBL WHERE PRODUCT_NM = ?");
                        ps1.setString(1, productNm);
                        ResultSet rs1 = ps1.executeQuery();
                        while (rs1.next()) {
                            String proQuan = rs1.getString("PRODUCT_QTY");
                            double ProQuan = Double.valueOf(proQuan);
                            double updateProQuan = ProQuan + getDifference;
                            String UpdateProQuan = String.valueOf(updateProQuan);

                            ////////   update value of product __ table
                            PreparedStatement ps2 = con.prepareStatement("UPDATE PRODUCT_TBL SET PRODUCT_QTY = ? WHERE PRODUCT_NM = ?");
                            ps2.setString(1, UpdateProQuan);
                            ps2.setString(2, productNm);
                            int i = ps2.executeUpdate();
                            if (i > 0) {

                                //////  GET VALUE ID OF PRODUCT
                                PreparedStatement ps3 = con.prepareStatement("SELECT * FROM PRODUCT_TBL WHERE PRODUCT_NM = ?");
                                ps3.setString(1, productNm);
                                ResultSet rs3 = ps3.executeQuery();
                                while (rs3.next()) {
                                    String productID = rs3.getString("PROIDNO");

                                    //////  update values of purchase__table
                                    PreparedStatement ps4 = con.prepareStatement("UPDATE PURCHASE_TBL SET PROIDNO = ?,  PRODUCT_PRICE = ?, TTL_PRICE = ?, PUR_QTY = ? WHERE PURIDNO = ?");
                                    ps4.setString(1, productID);
                                    ps4.setString(2, per_purchase_price);
                                    ps4.setString(3, total_price);
                                    ps4.setString(4, Total_quantity);
                                    ps4.setString(5, PurId);

                                    int j = ps4.executeUpdate();
                                    if (j > 0) {
                                        JOptionPane.showMessageDialog(null, " Purchase Bill Update Complete... ", "Success", JOptionPane.INFORMATION_MESSAGE);

                                        generatePurchase();
                                        jComboBox_selectProduct.setSelectedIndex(0);
                                        jTextField_Purchase_price.setText("");
                                        jTextField_purchase_quantity.setText("");
                                        jTextField_totalPrice.setText("");

                                        ////////////////
                                        ////////////////
                                        DefaultTableModel model = (DefaultTableModel) jTable_all_Purchase_Bill.getModel();
                                        model.setRowCount(0);
                                        show_purchase();         ///////////////  for refresh table
                                        ////////////////
                                        ////////////////

                                    } else {
                                        JOptionPane.showMessageDialog(null, " Error Occurred!! Please Try Again... ", "Error", JOptionPane.ERROR_MESSAGE);
                                    }

                                }

                            } else {
                                JOptionPane.showMessageDialog(null, " Error Occurred!! Please Try Again... ", "Error", JOptionPane.ERROR_MESSAGE);
                            }

                        }

                    } else if (NewpurQuantity == OldpurQuantity) {

                        //////  GET VALUE ID OF PRODUCT
                        PreparedStatement ps3 = con.prepareStatement("SELECT * FROM PRODUCT_TBL WHERE PRODUCT_NM = ?");
                        ps3.setString(1, productNm);
                        ResultSet rs3 = ps3.executeQuery();
                        while (rs3.next()) {
                            String productID = rs3.getString("PROIDNO");

                            //////  update values of purchase__table
                            PreparedStatement ps4 = con.prepareStatement("UPDATE PURCHASE_TBL SET PROIDNO = ?,  PRODUCT_PRICE = ?, TTL_PRICE = ?, PUR_QTY = ? WHERE PURIDNO = ?");
                            ps4.setString(1, productID);
                            ps4.setString(2, per_purchase_price);
                            ps4.setString(3, total_price);
                            ps4.setString(4, Total_quantity);
                            ps4.setString(5, PurId);

                            int j = ps4.executeUpdate();
                            if (j > 0) {
                                JOptionPane.showMessageDialog(null, " Purchase Bill Update Complete... ", "Success", JOptionPane.INFORMATION_MESSAGE);

                                generatePurchase();
                                jComboBox_selectProduct.setSelectedIndex(0);
                                jTextField_Purchase_price.setText("");
                                jTextField_purchase_quantity.setText("");
                                jTextField_totalPrice.setText("");

                                ////////////////
                                ////////////////
                                DefaultTableModel model = (DefaultTableModel) jTable_all_Purchase_Bill.getModel();
                                model.setRowCount(0);
                                show_purchase();         ///////////////  for refresh table
                                ////////////////
                                ////////////////

                            } else {
                                JOptionPane.showMessageDialog(null, " Error Occurred!! Please Try Again... ", "Error", JOptionPane.ERROR_MESSAGE);
                            }

                        }

                    }

                    ////////////////////////////////////////////////////////////////////////
                    ////////////////////////////////////////////////////////////////////////
                    ////////////////////////////////////////////////////////////////////////
                    ////////////////////////////////////////////////////////////////////////
                } else {
                    JOptionPane.showMessageDialog(null, " Purchase Bill Error!! ", "Error", JOptionPane.ERROR_MESSAGE);
                }

                ///////////////////////////////
                ///////////////////////////////
                
                con.close();
                
            } catch (Exception ea) {
                ea.printStackTrace();
            }
        }

    }//GEN-LAST:event_jButton_update_PurchaseActionPerformed

    private void jTextField_Purchase_priceKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField_Purchase_priceKeyReleased

        String price = jTextField_Purchase_price.getText();
        double priceDbl = Double.parseDouble(price);
        String quantity = jTextField_purchase_quantity.getText();
        double quantityDbl = Double.parseDouble(quantity);

        double total_price = priceDbl * quantityDbl;
        String total_priceStr = String.valueOf(String.format("%.2f", total_price));

        jTextField_totalPrice.setText(total_priceStr);

    }//GEN-LAST:event_jTextField_Purchase_priceKeyReleased

    private void jButton_Delete_PurchaseBillActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_Delete_PurchaseBillActionPerformed

        String PurId = jLabel_genProID.getText();
        String productNm = jComboBox_selectProduct.getSelectedItem().toString();
        String per_purchase_price = jTextField_Purchase_price.getText();
        String Total_quantity = jTextField_purchase_quantity.getText();
        String total_price = jTextField_totalPrice.getText();

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

            PreparedStatement ps = con.prepareStatement("SELECT * FROM PURCHASE_TBL WHERE PURIDNO = ?");
            ps.setString(1, PurId);
            int i = ps.executeUpdate();
            if (i > 0) {

                PreparedStatement ps1 = con.prepareStatement("SELECT * FROM PRODUCT_TBL WHERE PRODUCT_NM = ?");
                ps1.setString(1, productNm);
                ResultSet rs1 = ps1.executeQuery();
                while (rs1.next()) {

                    String product_quantity = rs1.getString("PRODUCT_QTY");
                    double Product_quantity = Double.valueOf(product_quantity);

                    double Purchase_quantity = Double.valueOf(Total_quantity);

                    if (Product_quantity > Purchase_quantity) {

                        double quantity_balance = Product_quantity - Purchase_quantity;
                        String Quantity_balance = String.valueOf(String.format("%.2f", quantity_balance));

                        PreparedStatement ps2 = con.prepareStatement("UPDATE PRODUCT_TBL SET PRODUCT_QTY = ? WHERE PRODUCT_NM = ?");
                        ps2.setString(1, Quantity_balance);
                        ps2.setString(2, productNm);
                        int j = ps2.executeUpdate();
                        if (j > 0) {

                            PreparedStatement ps3 = con.prepareStatement("DELETE FROM PURCHASE_TBL WHERE PURIDNO = ?");
                            ps3.setString(1, PurId);
                            int k = ps3.executeUpdate();
                            if (k > 0) {

                                JOptionPane.showMessageDialog(null, " Purchase Bill Removed Successfully!! ", "Warning", JOptionPane.WARNING_MESSAGE);

                                /////////////////////////////////////////////////////////////////
                                ////////////   ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~   ///////////////
                                generatePurchase();
                                jComboBox_selectProduct.setSelectedIndex(0);
                                jTextField_Purchase_price.setText("");
                                jTextField_purchase_quantity.setText("");
                                jTextField_totalPrice.setText("");

                                ////////////////
                                ////////////////
                                DefaultTableModel model = (DefaultTableModel) jTable_all_Purchase_Bill.getModel();
                                model.setRowCount(0);
                                show_purchase();         ///////////////  for refresh table
                                ////////////////
                                ////////////////

                                ////////////   ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~   ///////////////
                                /////////////////////////////////////////////////////////////////
                            } else {
                                JOptionPane.showMessageDialog(null, " Bill Error!! ", "Error", JOptionPane.ERROR_MESSAGE);
                            }

                        } else {
                            JOptionPane.showMessageDialog(null, " Stock Error!! ", "Error", JOptionPane.ERROR_MESSAGE);
                        }

                    } else {
                        JOptionPane.showMessageDialog(null, " Balance Error!! ", "Error", JOptionPane.ERROR_MESSAGE);
                    }

                }

            } else {
                JOptionPane.showMessageDialog(null, " Error Occurred!! ", "Error", JOptionPane.ERROR_MESSAGE);
            }

            /////////////////////////////////////////////////
            /////////////////////////////////////////////////
            /////////////////////////////////////////////////
            con.close();
            
        } catch (Exception ea) {
            ea.printStackTrace();
        }

    }//GEN-LAST:event_jButton_Delete_PurchaseBillActionPerformed

    private void jButton_SearchPurchaseBillActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_SearchPurchaseBillActionPerformed
        
        String searchBar = jTextField_searchPurchaseBill.getText();
        
        if(searchBar.equalsIgnoreCase(" Search by Product Name or Purchase Bill No or Date/Time...") || searchBar.equals("")){
            JOptionPane.showMessageDialog(null, " Please Search Some Thing... ", "Warning", JOptionPane.WARNING_MESSAGE);
        }
        else{
            ////////////////
            ////////////////
            DefaultTableModel model = (DefaultTableModel) jTable_all_Purchase_Bill.getModel();
            model.setRowCount(0);
            show_purchase_search();         ///////////////  for refresh table
            ////////////////
            ////////////////
        }
        
    }//GEN-LAST:event_jButton_SearchPurchaseBillActionPerformed

    private void jTextField_searchPurchaseBillFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField_searchPurchaseBillFocusGained

        String searchBar = jTextField_searchPurchaseBill.getText();
        
        if (searchBar.equalsIgnoreCase(" Search by Product Name or Purchase Bill No or Date/Time...")) {
            jTextField_searchPurchaseBill.setText("");
            jTextField_searchPurchaseBill.setForeground(new Color(153, 153, 153));
        }
        
    }//GEN-LAST:event_jTextField_searchPurchaseBillFocusGained

    private void jTextField_searchPurchaseBillFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField_searchPurchaseBillFocusLost
        
        if (jTextField_searchPurchaseBill.getText().trim().equals("") || jTextField_searchPurchaseBill.getText().trim().toLowerCase().equals(" Search by Product Name or Purchase Bill No or Date/Time...")) {
            jTextField_searchPurchaseBill.setText(" Search by Product Name or Purchase Bill No or Date/Time...");
            jTextField_searchPurchaseBill.setForeground(new Color(153, 153, 153));
        }
        
    }//GEN-LAST:event_jTextField_searchPurchaseBillFocusLost

    private void jLabel_productionMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel_productionMouseClicked
        
        account_production form = new account_production();
        form.setVisible(true);
        form.pack();
        form.setLocationRelativeTo(null);
        //close the current form (login form)
        this.dispose();
        
    }//GEN-LAST:event_jLabel_productionMouseClicked

    private void jLabel_productMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel_productMouseClicked
        
        account_AddProduct form = new account_AddProduct();
        form.setVisible(true);
        form.pack();
        form.setLocationRelativeTo(null);
        //close the current form (login form)
        this.dispose();
        
    }//GEN-LAST:event_jLabel_productMouseClicked

    private void jLabel_sellMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel_sellMouseClicked
        
        account_Sell form = new account_Sell();
        form.setVisible(true);
        form.pack();
        form.setLocationRelativeTo(null);
        //close the current form (login form)
        this.dispose();
        
    }//GEN-LAST:event_jLabel_sellMouseClicked

    private void jButton_Download_Purchase_DataActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_Download_Purchase_DataActionPerformed
        
        
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
                    String Product_ID = "Product_Table-" + x + "ID-at-" + onlyDt+ "DT__" + modifyTm +"TM.xls";         //////// for excel file
                    /////////////////////////////////
                    /////////////////////////////////

                    ////////////////////////
                    ///////  url path + file name
                    ////////////////////////
                    String pathWithFileName = getPath + "\\" + Product_ID;

                    //File PathWithFileName = new File(pathWithFileName);
                    File file = new File(pathWithFileName);




                    //////////////////////// export file into .xls format

                    try{
                        TableModel model = jTable_all_Purchase_Bill.getModel();
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
        
        
        
        
        
        
    }//GEN-LAST:event_jButton_Download_Purchase_DataActionPerformed

    private void jLabel_profileMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel_profileMouseClicked
        
        account_Profile form = new account_Profile();
        form.setVisible(true);
        form.pack();
        form.setLocationRelativeTo(null);
        //close the current form (login form)
        this.dispose();
        
    }//GEN-LAST:event_jLabel_profileMouseClicked

    private void jLabel_billMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel_billMouseExited

        jLabel_bill.setBackground(new Color(0, 204, 204));
        jLabel_bill.setForeground(new Color(255, 255, 255));
        jLabel_bill.setBorder(textFile_borderNo);
        
    }//GEN-LAST:event_jLabel_billMouseExited

    private void jLabel_billMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel_billMouseEntered

        jLabel_bill.setBackground(new Color(255, 255, 255));
        jLabel_bill.setForeground(new Color(0, 204, 204));
        jLabel_bill.setBorder(textFile_border);
        
    }//GEN-LAST:event_jLabel_billMouseEntered

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

    
    
    
    
    
    
    
    
    
    
    public void generatePurchase() {
        ///////   set Generated value of Product ID
        java.util.Random r = new java.util.Random();
        int x = r.nextInt(99999);
        String PurId = "Purchase-" + x + "IDNO";
        jLabel_genProID.setText(PurId);
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
            java.util.logging.Logger.getLogger(account_AddProduct.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(account_AddProduct.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(account_AddProduct.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(account_AddProduct.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new account_AddProduct().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton_AddProduct_section;
    private javax.swing.JButton jButton_AddUnit_Section;
    private javax.swing.JButton jButton_Add_purchase_bill;
    private javax.swing.JButton jButton_Clear_Purchase_AllField;
    private javax.swing.JButton jButton_Delete_PurchaseBill;
    private javax.swing.JButton jButton_Download_Purchase_Data;
    private javax.swing.JButton jButton_SearchPurchaseBill;
    private javax.swing.JButton jButton_update_Purchase;
    private javax.swing.JComboBox<String> jComboBox_selectProduct;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel_Expenditure;
    private javax.swing.JLabel jLabel_Purchase_Gross_TTL;
    private javax.swing.JLabel jLabel_ShowUnit_su;
    private javax.swing.JLabel jLabel_bill;
    private javax.swing.JLabel jLabel_close;
    private javax.swing.JLabel jLabel_dashboard;
    private javax.swing.JLabel jLabel_genProID;
    private javax.swing.JLabel jLabel_product;
    private javax.swing.JLabel jLabel_production;
    private javax.swing.JLabel jLabel_profile;
    private javax.swing.JLabel jLabel_sell;
    private javax.swing.JLabel jLabel_showUnit;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable_all_Purchase_Bill;
    private javax.swing.JTextField jTextField_Purchase_price;
    private javax.swing.JTextField jTextField_purchase_quantity;
    private javax.swing.JTextField jTextField_searchPurchaseBill;
    private javax.swing.JTextField jTextField_totalPrice;
    // End of variables declaration//GEN-END:variables
}
