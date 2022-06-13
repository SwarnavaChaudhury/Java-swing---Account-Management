/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package account_maintainer;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
public class account_AddProduct_Sctn extends javax.swing.JFrame {

    /////  create border
    Border textFile_border = BorderFactory.createMatteBorder(0, 0, 2, 0, Color.red);
    Border textFile_borderNo = BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(0,204,204));
    

    /**
     * Creates new form account_DashBoard
     */
    public account_AddProduct_Sctn() {
        initComponents();

        //  center form
        this.setLocationRelativeTo(null);

        //////  set icon
        jLabel_profile.setIcon(new ImageIcon(getClass().getResource("IMAGES/useron.png")));

        ///////   set border to jTextField
        jTextField_prod_nm.setBorder(textFile_border);
        jTextField_opening_balnce.setBorder(textFile_border);
        jTextField_searchFr_product.setBorder(textFile_border);

        //////   generate product id 
        generateProductIds();

        ////////   show content of product in jTable
        show_product();

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

            PreparedStatement ps = con.prepareStatement("SELECT * FROM UNIT_TBL");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String UnitNm = rs.getString("UNIT_NAME");
                jComboBox_unitShow.addItem(UnitNm);
            }
            
            ////////////////////////////////////////////////////////////////////
            ////////////////////////////////////////////////////////////////////
            ////////////////////////////////////////////////////////////////////
            con.close();
            
            
        } catch (Exception ea) {
            ea.printStackTrace();
        }

    }

    /////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////////
    ////  ~~~~~~~~~   Show data of jTable
    public ArrayList<product> productList() {
        ArrayList<product> productList = new ArrayList<>();

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

            product product;

            PreparedStatement ps = con.prepareStatement("SELECT PRODUCT_TBL.PROIDNO,PRODUCT_NM,PRODUCT_QTY, UNIT_TBL.UNIT_NAME FROM PRODUCT_TBL,UNIT_TBL WHERE PRODUCT_TBL.UNIT_IDNO=UNIT_TBL.UNIT_IDNO");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                product = new product(rs.getString("PROIDNO"), rs.getString("PRODUCT_NM"), rs.getString("PRODUCT_QTY"), rs.getString("UNIT_NAME"));
                productList.add(product);
            }
            ////////////////////////////////////////////////////////////////////
            ////////////////////////////////////////////////////////////////////
            ////////////////////////////////////////////////////////////////////
            con.close();
            
            
        } catch (Exception ea) {
            ea.printStackTrace();
        }
        ////////////////////////////////////////////////////////////////////////
        return productList;
        ////////////////////////////////////////////////////////////////////////
    }

    public void show_product() {
        ArrayList<product> productLst = productList();
        DefaultTableModel model = (DefaultTableModel) jTable_show_product_data.getModel();
        Object[] row = new Object[4];
        for (int i = 0; i < productLst.size(); i++) {
            row[0] = productLst.get(i).getproductid();
            row[1] = productLst.get(i).getproductname();
            row[2] = productLst.get(i).getunit_id();
            row[3] = productLst.get(i).getproduct_qty();
            model.addRow(row);
        }
    }

    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    /////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////////
    ////  ~~~~~~~~~   Show data of jTable
    public ArrayList<product> productList_Search() {
        ArrayList<product> productList = new ArrayList<>();

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

            product product;

            String search_br = "%" + jTextField_searchFr_product.getText() + "%";

            PreparedStatement ps = con.prepareStatement("SELECT PRODUCT_TBL.PROIDNO,PRODUCT_NM,PRODUCT_QTY, UNIT_TBL.UNIT_NAME FROM PRODUCT_TBL,UNIT_TBL WHERE PRODUCT_TBL.UNIT_IDNO=UNIT_TBL.UNIT_IDNO AND PRODUCT_TBL.PRODUCT_NM LIKE ?");
            ps.setString(1, search_br);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                product = new product(rs.getString("PROIDNO"), rs.getString("PRODUCT_NM"), rs.getString("PRODUCT_QTY"), rs.getString("UNIT_NAME"));
                productList.add(product);
            }
            ////////////////////////////////////////////////////////////////////
            ////////////////////////////////////////////////////////////////////
            ////////////////////////////////////////////////////////////////////
            con.close();
            
            
        } catch (Exception ea) {
            ea.printStackTrace();
        }
        ////////////////////////////////////////////////////////////////////////
        return productList;
        ////////////////////////////////////////////////////////////////////////
    }

    public void show_product_Search() {
        ArrayList<product> productLst = productList_Search();
        DefaultTableModel model = (DefaultTableModel) jTable_show_product_data.getModel();

        model.setRowCount(0);

        Object[] row = new Object[4];
        for (int i = 0; i < productLst.size(); i++) {
            row[0] = productLst.get(i).getproductid();
            row[1] = productLst.get(i).getproductname();
            row[2] = productLst.get(i).getunit_id();
            row[3] = productLst.get(i).getproduct_qty();
            model.addRow(row);
        }
    }

    /////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////////
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
        jPanel4 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel_Product_idd = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jTextField_prod_nm = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jComboBox_unitShow = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        jTextField_opening_balnce = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jButton_updateProductDtls = new javax.swing.JButton();
        jButton_DeleteProduct = new javax.swing.JButton();
        jButton_clearBtn = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable_show_product_data = new javax.swing.JTable();
        jTextField_searchFr_product = new javax.swing.JTextField();
        jButton_searchBtn = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();

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

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setBackground(new java.awt.Color(255, 255, 255));
        jLabel1.setFont(new java.awt.Font("Tempus Sans ITC", 3, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 153, 204));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Add Product");

        jLabel2.setBackground(new java.awt.Color(255, 255, 255));
        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel2.setText("Product ID");

        jLabel_Product_idd.setFont(new java.awt.Font("Tempus Sans ITC", 3, 18)); // NOI18N
        jLabel_Product_idd.setForeground(new java.awt.Color(0, 204, 255));
        jLabel_Product_idd.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        jLabel3.setBackground(new java.awt.Color(255, 255, 255));
        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel3.setText("Product Name");

        jTextField_prod_nm.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        jLabel4.setBackground(new java.awt.Color(255, 255, 255));
        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel4.setText("Unit");

        jComboBox_unitShow.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        jLabel5.setBackground(new java.awt.Color(255, 255, 255));
        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel5.setText("Opening Balance");

        jTextField_opening_balnce.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jTextField_opening_balnce.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextField_opening_balnceKeyPressed(evt);
            }
        });

        jButton1.setBackground(new java.awt.Color(102, 255, 0));
        jButton1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/account_maintainer/IMAGES/plusIcn.png"))); // NOI18N
        jButton1.setText("Add");
        jButton1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton_updateProductDtls.setBackground(new java.awt.Color(255, 204, 0));
        jButton_updateProductDtls.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jButton_updateProductDtls.setForeground(new java.awt.Color(255, 255, 255));
        jButton_updateProductDtls.setIcon(new javax.swing.ImageIcon(getClass().getResource("/account_maintainer/IMAGES/update.png"))); // NOI18N
        jButton_updateProductDtls.setText("Update");
        jButton_updateProductDtls.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton_updateProductDtls.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_updateProductDtlsActionPerformed(evt);
            }
        });

        jButton_DeleteProduct.setBackground(new java.awt.Color(255, 0, 51));
        jButton_DeleteProduct.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jButton_DeleteProduct.setForeground(new java.awt.Color(255, 255, 255));
        jButton_DeleteProduct.setIcon(new javax.swing.ImageIcon(getClass().getResource("/account_maintainer/IMAGES/delete.png"))); // NOI18N
        jButton_DeleteProduct.setText("Remove");
        jButton_DeleteProduct.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton_DeleteProduct.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_DeleteProductActionPerformed(evt);
            }
        });

        jButton_clearBtn.setBackground(new java.awt.Color(0, 102, 255));
        jButton_clearBtn.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jButton_clearBtn.setForeground(new java.awt.Color(255, 255, 255));
        jButton_clearBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/account_maintainer/IMAGES/reset.png"))); // NOI18N
        jButton_clearBtn.setText("Reset");
        jButton_clearBtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton_clearBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_clearBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 46, Short.MAX_VALUE)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jComboBox_unitShow, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField_prod_nm, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel_Product_idd, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton_DeleteProduct, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton_clearBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextField_opening_balnce)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jButton1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jButton_updateProductDtls, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 32, Short.MAX_VALUE)
                    .addComponent(jLabel_Product_idd, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 47, Short.MAX_VALUE)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jTextField_prod_nm, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox_unitShow))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jTextField_opening_balnce, javax.swing.GroupLayout.DEFAULT_SIZE, 34, Short.MAX_VALUE))
                .addGap(53, 53, 53)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton_clearBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(34, 34, 34)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton_updateProductDtls, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton_DeleteProduct, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(36, 36, 36))
        );

        jTable_show_product_data.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Product ID", "Product Name", "Unit", "Present Quantity"
            }
        ));
        jTable_show_product_data.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable_show_product_dataMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTable_show_product_data);

        jTextField_searchFr_product.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jTextField_searchFr_product.setForeground(new java.awt.Color(204, 204, 204));
        jTextField_searchFr_product.setText(" Search For Product Name...");
        jTextField_searchFr_product.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTextField_searchFr_productFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTextField_searchFr_productFocusLost(evt);
            }
        });
        jTextField_searchFr_product.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField_searchFr_productActionPerformed(evt);
            }
        });

        jButton_searchBtn.setBackground(new java.awt.Color(0, 255, 0));
        jButton_searchBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/account_maintainer/IMAGES/searchOn.png"))); // NOI18N
        jButton_searchBtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton_searchBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_searchBtnActionPerformed(evt);
            }
        });

        jLabel6.setBackground(new java.awt.Color(255, 255, 255));
        jLabel6.setFont(new java.awt.Font("Tempus Sans ITC", 3, 24)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 153, 204));
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("Product");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addComponent(jTextField_searchFr_product)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton_searchBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 464, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel_profile, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel_close, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jLabel_close, javax.swing.GroupLayout.DEFAULT_SIZE, 43, Short.MAX_VALUE)
                        .addComponent(jLabel_profile, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jTextField_searchFr_product)
                    .addComponent(jButton_searchBtn))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
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
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 900, Short.MAX_VALUE)
                .addGap(2, 2, 2))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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

    private void jButton_clearBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_clearBtnActionPerformed

        generateProductIds();
        jTextField_prod_nm.setText("");
        jTextField_opening_balnce.setText("");
        jComboBox_unitShow.setSelectedIndex(0);
        jTextField_searchFr_product.setText(" Search For Product Name...");
        
        ////////////////
        ////////////////
        DefaultTableModel model = (DefaultTableModel) jTable_show_product_data.getModel();
        model.setRowCount(0);
        show_product();         ///////////////  for refresh table
        ////////////////
        ////////////////

    }//GEN-LAST:event_jButton_clearBtnActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

        String product_id = jLabel_Product_idd.getText();
        String product_nm = jTextField_prod_nm.getText();
        String unit_nm = jComboBox_unitShow.getSelectedItem().toString();
        String Opening_balance = jTextField_opening_balnce.getText();

        if (product_id.isEmpty() || product_nm.isEmpty() || unit_nm.isEmpty() || Opening_balance.isEmpty()) {
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

                PreparedStatement iq = con.prepareStatement("SELECT * FROM PRODUCT_TBL WHERE PROIDNO = ? OR PRODUCT_NM = ?");
                iq.setString(1, product_id);
                iq.setString(2, product_nm);

                int iqq = iq.executeUpdate();

                if (iqq > 0) {
                    JOptionPane.showMessageDialog(null, " Duplicate Value Not Allowed ", "Warning!", JOptionPane.WARNING_MESSAGE);
                } else {

                    ////////////////////////////////////////////////////////////
                    ////////////////////////////////////////////////////////////
                    PreparedStatement im = con.prepareStatement("SELECT * FROM UNIT_TBL WHERE UNIT_NAME = ?");
                    im.setString(1, unit_nm);
                    ResultSet rs = im.executeQuery();
                    while (rs.next()) {
                        String unit_symbol = rs.getString("UNIT_IDNO");

                        /////////////////////////////////////////////////////////////////////////////////
                        PreparedStatement iff = con.prepareStatement("INSERT INTO PRODUCT_TBL(PROIDNO, PRODUCT_NM, PRODUCT_QTY, UNIT_IDNO) VALUES(?, ?, ?, ?)");
                        iff.setString(1, product_id);
                        iff.setString(2, product_nm);
                        iff.setString(3, Opening_balance);
                        iff.setString(4, unit_symbol);

                        int ifd = iff.executeUpdate();

                        if (ifd > 0) {

                            JOptionPane.showMessageDialog(null, " Product Added Successfully... ", "Success!", JOptionPane.INFORMATION_MESSAGE);
                            generateProductIds();
                            jTextField_prod_nm.setText("");
                            jTextField_opening_balnce.setText("");
                            jComboBox_unitShow.setSelectedIndex(0);

                            ////////////////
                            ////////////////
                            DefaultTableModel model = (DefaultTableModel) jTable_show_product_data.getModel();
                            model.setRowCount(0);
                            show_product();         ///////////////  for refresh table
                            ////////////////
                            ////////////////

                        } else {

                            JOptionPane.showMessageDialog(null, " Error Occurred!! Please Try Again... ", "Error!", JOptionPane.ERROR_MESSAGE);

                        }

                        /////////////////////////////////////////////////////////////////////////////////
                    }

                    ////////////////////////////////////////////////////////////
                    ////////////////////////////////////////////////////////////
                }
                
                ////////////////////////////////////////////////////////////////
                ////////////////////////////////////////////////////////////////
                ////////////////////////////////////////////////////////////////
                con.close();
                
            } catch (Exception ea) {
                ea.printStackTrace();
            }

            /////////////////////////
            /////////////////////////
        }


    }//GEN-LAST:event_jButton1ActionPerformed

    private void jTable_show_product_dataMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable_show_product_dataMouseClicked

        int il = jTable_show_product_data.getSelectedRow();
        TableModel model = jTable_show_product_data.getModel();
        jLabel_Product_idd.setText(model.getValueAt(il, 0).toString());
        jTextField_prod_nm.setText(model.getValueAt(il, 1).toString());
        jComboBox_unitShow.setSelectedItem(model.getValueAt(il, 2).toString());
        jTextField_opening_balnce.setText(model.getValueAt(il, 3).toString());

    }//GEN-LAST:event_jTable_show_product_dataMouseClicked

    private void jButton_updateProductDtlsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_updateProductDtlsActionPerformed

        String product_id = jLabel_Product_idd.getText();
        String product_nm = jTextField_prod_nm.getText();
        String unit_nm = jComboBox_unitShow.getSelectedItem().toString();
        String Opening_balance = jTextField_opening_balnce.getText();

        if (product_id.isEmpty() || product_nm.isEmpty() || unit_nm.isEmpty() || Opening_balance.isEmpty()) {
            /////////////////////////
            /////////////////////////
            JOptionPane.showMessageDialog(null, " Some Field is Empty!! ", "Warning", JOptionPane.WARNING_MESSAGE);
            /////////////////////////
            /////////////////////////
        } else {
            ////////////////////////////////////////////////////////////////////

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

                PreparedStatement im = con.prepareStatement("SELECT * FROM UNIT_TBL WHERE UNIT_NAME = ?");
                im.setString(1, unit_nm);
                ResultSet rs = im.executeQuery();
                while (rs.next()) {
                    String unit_symbol = rs.getString("UNIT_IDNO");

                    ////////////////////////////////////////////////////////
                    PreparedStatement iy = con.prepareStatement("UPDATE PRODUCT_TBL SET PRODUCT_NM = ?, PRODUCT_QTY = ?, UNIT_IDNO = ? WHERE PROIDNO = ?");
                    iy.setString(1, product_nm);
                    iy.setString(2, Opening_balance);
                    iy.setString(3, unit_symbol);
                    iy.setString(4, product_id);

                    int iz = iy.executeUpdate();

                    if (iz > 0) {

                        JOptionPane.showMessageDialog(null, " Product Update Successfully... ", "Success!", JOptionPane.INFORMATION_MESSAGE);
                        generateProductIds();
                        jTextField_prod_nm.setText("");
                        jTextField_opening_balnce.setText("");

                        ////////////////
                        ////////////////
                        DefaultTableModel model = (DefaultTableModel) jTable_show_product_data.getModel();
                        model.setRowCount(0);
                        show_product();         ///////////////  for refresh table
                        ////////////////
                        ////////////////

                    } else {

                        JOptionPane.showMessageDialog(null, " Error Occurred!! Please Try Again... ", "Error!", JOptionPane.ERROR_MESSAGE);

                    }

                    ////////////////////////////////////////////////////////
                }
                ////////////////////////////////////////////////////////////////
                ////////////////////////////////////////////////////////////////
                ////////////////////////////////////////////////////////////////
                con.close();
                
                
            } catch (Exception ea) {
                ea.printStackTrace();
            }

            ////////////////////////////////////////////////////////////////////
        }

    }//GEN-LAST:event_jButton_updateProductDtlsActionPerformed

    private void jButton_DeleteProductActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_DeleteProductActionPerformed

        String product_id = jLabel_Product_idd.getText();

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

            ////  this product is already enter in purchase table or not...
            PreparedStatement sp = con.prepareStatement("SELECT * FROM PURCHASE_TBL WHERE PROIDNO = ?");
            sp.setString(1, product_id);
            int jk = sp.executeUpdate();
            if(jk > 0){
                ////////////////////////////////////////////////////////////////
                JOptionPane.showMessageDialog(this, "Product is Already Used!! Can't be Removed!!", "Warning", JOptionPane.WARNING_MESSAGE);
                ////////////////////////////////////////////////////////////////
            }else{
                ////////////////////////////////////////////////////////////////
                ////////////////////////////////////////////////////////////////
                
                    //////   product id present or not
                    PreparedStatement pa = con.prepareStatement("SELECT * FROM PRODUCT_TBL WHERE PROIDNO = ?");
                    pa.setString(1, product_id);
                    int i = pa.executeUpdate();
                    if (i > 0) {
                        ////////////////////////////////////////////////////////////////
                        PreparedStatement ps = con.prepareStatement("DELETE FROM PRODUCT_TBL WHERE PROIDNO = ?");
                        ps.setString(1, product_id);
                        int j = ps.executeUpdate();
                        if (j > 0) {

                            JOptionPane.showMessageDialog(null, " Removed Successfully... ", "Notice!", JOptionPane.INFORMATION_MESSAGE);
                            generateProductIds();
                            jTextField_prod_nm.setText("");
                            jTextField_opening_balnce.setText("");
                            jComboBox_unitShow.setSelectedIndex(0);

                            ////////////////
                            ////////////////
                            DefaultTableModel model = (DefaultTableModel) jTable_show_product_data.getModel();
                            model.setRowCount(0);
                            show_product();         ///////////////  for refresh table
                            ////////////////
                            ////////////////

                        } else {
                            JOptionPane.showMessageDialog(null, " Error Occurred!! Please Try Again... ", "Error!", JOptionPane.ERROR_MESSAGE);
                        }
                        ////////////////////////////////////////////////////////////////
                    } else {
                        JOptionPane.showMessageDialog(null, " Error Occurred!! No Product Found... ", "Error!", JOptionPane.ERROR_MESSAGE);
                    }
                
                ////////////////////////////////////////////////////////////////
                ////////////////////////////////////////////////////////////////
            }
            
            ////////////////////////////////////////////////////////////////////
            ////////////////////////////////////////////////////////////////////
            ////////////////////////////////////////////////////////////////////
            con.close();
            
            
        } catch (Exception ea) {
            ea.printStackTrace();
        }

    }//GEN-LAST:event_jButton_DeleteProductActionPerformed

    private void jTextField_searchFr_productFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField_searchFr_productFocusGained

        String srch = jTextField_searchFr_product.getText();
        if (srch.equalsIgnoreCase(" Search For Product Name...")) {
            jTextField_searchFr_product.setText("");
            jTextField_searchFr_product.setForeground(new Color(26, 198, 255));
        }

    }//GEN-LAST:event_jTextField_searchFr_productFocusGained

    private void jTextField_searchFr_productFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField_searchFr_productFocusLost

        if (jTextField_searchFr_product.getText().trim().equals("")) {
            jTextField_searchFr_product.setText(" Search For Product Name...");
            jTextField_searchFr_product.setForeground(new Color(153, 153, 153));
        }

    }//GEN-LAST:event_jTextField_searchFr_productFocusLost

    private void jTextField_opening_balnceKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField_opening_balnceKeyPressed

        if (evt.getKeyChar() >= '0' && evt.getKeyChar() <= '9' || evt.getKeyCode() == KeyEvent.VK_BACK_SPACE || evt.getKeyChar() == KeyEvent.VK_DECIMAL || evt.getKeyChar() == KeyEvent.VK_PERIOD) {
            jTextField_opening_balnce.setEditable(true);
        } else {
            jTextField_opening_balnce.setEditable(false);
        }

    }//GEN-LAST:event_jTextField_opening_balnceKeyPressed

    private void jButton_searchBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_searchBtnActionPerformed

        String srchRslt = jTextField_searchFr_product.getText();

        if (srchRslt.equalsIgnoreCase(" Search For Product Name...") || srchRslt.trim().equals("")) {
            JOptionPane.showMessageDialog(null, " Please Search Some Thing... ", "Warning", JOptionPane.WARNING_MESSAGE);
        } else {
            ////////////////
            ////////////////
            DefaultTableModel model = (DefaultTableModel) jTable_show_product_data.getModel();
            model.setRowCount(0);
            show_product_Search();         ///////////////  for refresh table
            ////////////////
            ////////////////
        }

    }//GEN-LAST:event_jButton_searchBtnActionPerformed

    private void jTextField_searchFr_productActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField_searchFr_productActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField_searchFr_productActionPerformed

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

    public void generateProductIds() {

        ///////   set Generated value of Product ID
        java.util.Random r = new java.util.Random();
        int x = r.nextInt(99999);
        String ProId = "Product-" + x + "IDNO";
        jLabel_Product_idd.setText(ProId);

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
            java.util.logging.Logger.getLogger(account_AddProduct_Sctn.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(account_AddProduct_Sctn.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(account_AddProduct_Sctn.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(account_AddProduct_Sctn.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new account_AddProduct_Sctn().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton_DeleteProduct;
    private javax.swing.JButton jButton_clearBtn;
    private javax.swing.JButton jButton_searchBtn;
    private javax.swing.JButton jButton_updateProductDtls;
    private javax.swing.JComboBox<String> jComboBox_unitShow;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel_Expenditure;
    private javax.swing.JLabel jLabel_Product_idd;
    private javax.swing.JLabel jLabel_bill;
    private javax.swing.JLabel jLabel_close;
    private javax.swing.JLabel jLabel_dashboard;
    private javax.swing.JLabel jLabel_product;
    private javax.swing.JLabel jLabel_production;
    private javax.swing.JLabel jLabel_profile;
    private javax.swing.JLabel jLabel_sell;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable_show_product_data;
    private javax.swing.JTextField jTextField_opening_balnce;
    private javax.swing.JTextField jTextField_prod_nm;
    private javax.swing.JTextField jTextField_searchFr_product;
    // End of variables declaration//GEN-END:variables
}
