/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package account_maintainer;

import java.awt.Color;
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
public class account_AddUnit_Sctn extends javax.swing.JFrame {

    /////  create border
    Border textFile_border = BorderFactory.createMatteBorder(0, 0, 2, 0, Color.red);
    Border textFile_borderNo = BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(0,204,204));

    /**
     * Creates new form account_DashBoard
     */
    public account_AddUnit_Sctn() {
        initComponents();

        //  center form
        this.setLocationRelativeTo(null);

        //////  set icon
        jLabel_profile.setIcon(new ImageIcon(getClass().getResource("IMAGES/useron.png")));

        //////  set value of jTextField
        jTextField_unitNm.setBorder(textFile_border);
        jTextField_Symbol.setBorder(textFile_border);
        jTextField_SearchUnit.setBorder(textFile_border);

        ///////   set Generated value of Product ID
        generateUnitIds();

        ////////  show unit data to jTable 
        show_unit();

    }

    /////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////////
    ////  ~~~~~~~~~   Show data of jTable
    public ArrayList<unit> unitList() {
        ArrayList<unit> unitList = new ArrayList<>();

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

            unit unit;

            PreparedStatement ps = con.prepareStatement("SELECT * FROM UNIT_TBL");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                unit = new unit(rs.getString("UNIT_IDNO"), rs.getString("UNIT_NAME"), rs.getString("UNIT_SYMBOL"));
                unitList.add(unit);
            }
            ////////////////////////////////////////////////////////////////////
            ////////////////////////////////////////////////////////////////////
            ////////////////////////////////////////////////////////////////////
            con.close();
            
        } catch (Exception ea) {
            ea.printStackTrace();
        }
        ///////////////////////////
        return unitList;
    }

    public void show_unit() {
        ArrayList<unit> unitLst = unitList();
        DefaultTableModel model = (DefaultTableModel) jTable_UnitData.getModel();
        Object[] row = new Object[3];
        for (int i = 0; i < unitLst.size(); i++) {
            row[0] = unitLst.get(i).getunitid();
            row[1] = unitLst.get(i).getunitnm();
            row[2] = unitLst.get(i).getunitsymbol();
            model.addRow(row);
        }
    }

    /////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////////

    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    /////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////////
    
    ////  ~~~~~~~~~   Show data of jTable
    public ArrayList<unit> unitListSearched() {
        ArrayList<unit> unitList = new ArrayList<>();

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

            unit unit;

            String srchUnit = "%"+jTextField_SearchUnit.getText()+"%";
            
            PreparedStatement ps = con.prepareStatement("SELECT * FROM UNIT_TBL WHERE UNIT_NAME LIKE '"+srchUnit+"'");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                unit = new unit(rs.getString("UNIT_IDNO"), rs.getString("UNIT_NAME"), rs.getString("UNIT_SYMBOL"));
                unitList.add(unit);
            }
            
            ////////////////////////////////////////////////////////////////////
            ////////////////////////////////////////////////////////////////////
            ////////////////////////////////////////////////////////////////////
            con.close();
            
        } catch (Exception ea) {
            ea.printStackTrace();
        }
        ///////////////////////////
        return unitList;
    }
    
    public void show_unit_searched() {
        ArrayList<unit> unitLst = unitListSearched();
        DefaultTableModel model = (DefaultTableModel) jTable_UnitData.getModel();
        
        model.setRowCount(0);
        
        Object[] row = new Object[3];
        for (int i = 0; i < unitLst.size(); i++) {
            row[0] = unitLst.get(i).getunitid();
            row[1] = unitLst.get(i).getunitnm();
            row[2] = unitLst.get(i).getunitsymbol();
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
        jLabel_unit_id = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jTextField_unitNm = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jTextField_Symbol = new javax.swing.JTextField();
        jButton_addUnitBtn = new javax.swing.JButton();
        jButton_clear = new javax.swing.JButton();
        jButton_updateUnit = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable_UnitData = new javax.swing.JTable();
        jTextField_SearchUnit = new javax.swing.JTextField();
        jButton_SearchUnit = new javax.swing.JButton();

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
        jLabel1.setText("Add Unit");

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 22)); // NOI18N
        jLabel2.setText("Unit ID");

        jLabel_unit_id.setFont(new java.awt.Font("Times New Roman", 3, 18)); // NOI18N
        jLabel_unit_id.setForeground(new java.awt.Color(0, 153, 255));
        jLabel_unit_id.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 22)); // NOI18N
        jLabel3.setText("Unit Name");

        jTextField_unitNm.setFont(new java.awt.Font("Tahoma", 0, 22)); // NOI18N

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 22)); // NOI18N
        jLabel4.setText("Symbol");

        jTextField_Symbol.setFont(new java.awt.Font("Tahoma", 0, 22)); // NOI18N

        jButton_addUnitBtn.setBackground(new java.awt.Color(0, 255, 51));
        jButton_addUnitBtn.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jButton_addUnitBtn.setForeground(new java.awt.Color(255, 255, 255));
        jButton_addUnitBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/account_maintainer/IMAGES/plusIcn.png"))); // NOI18N
        jButton_addUnitBtn.setText("Add Unit");
        jButton_addUnitBtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton_addUnitBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jButton_addUnitBtnMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jButton_addUnitBtnMouseExited(evt);
            }
        });
        jButton_addUnitBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_addUnitBtnActionPerformed(evt);
            }
        });

        jButton_clear.setBackground(new java.awt.Color(0, 102, 255));
        jButton_clear.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jButton_clear.setForeground(new java.awt.Color(255, 255, 255));
        jButton_clear.setIcon(new javax.swing.ImageIcon(getClass().getResource("/account_maintainer/IMAGES/reset.png"))); // NOI18N
        jButton_clear.setText("Reset");
        jButton_clear.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton_clear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_clearActionPerformed(evt);
            }
        });

        jButton_updateUnit.setBackground(new java.awt.Color(255, 204, 0));
        jButton_updateUnit.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jButton_updateUnit.setForeground(new java.awt.Color(255, 255, 255));
        jButton_updateUnit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/account_maintainer/IMAGES/update.png"))); // NOI18N
        jButton_updateUnit.setText("Update");
        jButton_updateUnit.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton_updateUnit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_updateUnitActionPerformed(evt);
            }
        });

        jButton2.setBackground(new java.awt.Color(255, 0, 51));
        jButton2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jButton2.setForeground(new java.awt.Color(255, 255, 255));
        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/account_maintainer/IMAGES/delete.png"))); // NOI18N
        jButton2.setText("Remove");
        jButton2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
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
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(89, 89, 89)
                        .addComponent(jLabel_unit_id, javax.swing.GroupLayout.PREFERRED_SIZE, 227, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextField_Symbol)
                            .addComponent(jTextField_unitNm)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jButton_clear, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, 158, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jButton_updateUnit, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton_addUnitBtn, javax.swing.GroupLayout.DEFAULT_SIZE, 165, Short.MAX_VALUE))))
                .addContainerGap(36, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 44, Short.MAX_VALUE)
                    .addComponent(jLabel_unit_id, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jTextField_unitNm, javax.swing.GroupLayout.DEFAULT_SIZE, 41, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jTextField_Symbol, javax.swing.GroupLayout.DEFAULT_SIZE, 42, Short.MAX_VALUE))
                .addGap(40, 40, 40)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButton_addUnitBtn, javax.swing.GroupLayout.DEFAULT_SIZE, 57, Short.MAX_VALUE)
                    .addComponent(jButton_clear, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButton_updateUnit, javax.swing.GroupLayout.DEFAULT_SIZE, 57, Short.MAX_VALUE)
                    .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(74, 74, 74))
        );

        jLabel5.setBackground(new java.awt.Color(153, 204, 255));
        jLabel5.setFont(new java.awt.Font("Tempus Sans ITC", 3, 24)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 153, 255));
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("Unit");

        jTable_UnitData.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Unit ID", "Unit Name", "Symbol"
            }
        ));
        jTable_UnitData.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable_UnitDataMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTable_UnitData);

        jTextField_SearchUnit.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jTextField_SearchUnit.setForeground(new java.awt.Color(191, 191, 191));
        jTextField_SearchUnit.setText("  Search For Unit Name...");
        jTextField_SearchUnit.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTextField_SearchUnitFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTextField_SearchUnitFocusLost(evt);
            }
        });

        jButton_SearchUnit.setBackground(new java.awt.Color(51, 255, 0));
        jButton_SearchUnit.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jButton_SearchUnit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/account_maintainer/IMAGES/searchOn.png"))); // NOI18N
        jButton_SearchUnit.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton_SearchUnit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jButton_SearchUnitMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jButton_SearchUnitMouseExited(evt);
            }
        });
        jButton_SearchUnit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_SearchUnitActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jTextField_SearchUnit, javax.swing.GroupLayout.PREFERRED_SIZE, 374, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jButton_SearchUnit, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel_profile, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel_close, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jLabel_close, javax.swing.GroupLayout.DEFAULT_SIZE, 43, Short.MAX_VALUE)
                        .addComponent(jLabel_profile, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jButton_SearchUnit)
                    .addComponent(jTextField_SearchUnit, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
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
                .addContainerGap(14, Short.MAX_VALUE)
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

    private void jButton_addUnitBtnMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton_addUnitBtnMouseEntered

        jButton_addUnitBtn.setBackground(new Color(255, 255, 255));
        jButton_addUnitBtn.setForeground(new Color(0, 255, 51));

    }//GEN-LAST:event_jButton_addUnitBtnMouseEntered

    private void jButton_addUnitBtnMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton_addUnitBtnMouseExited

        jButton_addUnitBtn.setBackground(new Color(0, 255, 51));
        jButton_addUnitBtn.setForeground(new Color(255, 255, 255));

    }//GEN-LAST:event_jButton_addUnitBtnMouseExited

    private void jButton_SearchUnitMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton_SearchUnitMouseEntered

        jButton_SearchUnit.setBackground(new Color(128, 255, 128));

    }//GEN-LAST:event_jButton_SearchUnitMouseEntered

    private void jButton_SearchUnitMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton_SearchUnitMouseExited

        jButton_SearchUnit.setBackground(new Color(51, 255, 0));

    }//GEN-LAST:event_jButton_SearchUnitMouseExited

    private void jButton_addUnitBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_addUnitBtnActionPerformed

        String unit_id = jLabel_unit_id.getText();
        String unit_nm = jTextField_unitNm.getText();
        String symbl = jTextField_Symbol.getText();

        if (unit_id.isEmpty() || unit_nm.isEmpty() || symbl.isEmpty()) {

            JOptionPane.showMessageDialog(null, " Some Field is Empty!! ", "Warning!", JOptionPane.WARNING_MESSAGE);

        } else {

            /////////////////////////////////////////////////////////////////////////////////////
            /////////////////////////////////////////////////////////////////////////////////////
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

                PreparedStatement su = con.prepareStatement("SELECT * FROM UNIT_TBL WHERE UNIT_IDNO = ? OR UNIT_NAME = ? OR UNIT_SYMBOL = ?");
                su.setString(1, unit_id);
                su.setString(2, unit_nm);
                su.setString(3, symbl);
                int j = su.executeUpdate();
                if (j > 0) {
                    JOptionPane.showMessageDialog(null, " Duplicate Value Entered !! ", "Error!", JOptionPane.ERROR_MESSAGE);
                } else {
                    ///////////////////////////////////////
                    ///////////////////////////////////////

                    PreparedStatement ps = con.prepareStatement("INSERT INTO UNIT_TBL(UNIT_IDNO, UNIT_NAME, UNIT_SYMBOL) VALUES(?, ?, ?)");
                    ps.setString(1, unit_id);
                    ps.setString(2, unit_nm);
                    ps.setString(3, symbl);
                    int i = ps.executeUpdate();
                    if (i > 0) {
                        ///////////////
                        JOptionPane.showMessageDialog(null, " Unit Added Successfully!! ", "Success!", JOptionPane.INFORMATION_MESSAGE);
                        generateUnitIds();
                        jTextField_unitNm.setText("");
                        jTextField_Symbol.setText("");
                        ////////  show unit data to jTable 

                        ////////////////
                        ////////////////
                        DefaultTableModel model = (DefaultTableModel) jTable_UnitData.getModel();
                        model.setRowCount(0);
                        show_unit();         ///////////////  for refresh table
                        ////////////////
                        ////////////////
                    } else {
                        JOptionPane.showMessageDialog(null, " Error Occurred !! Please Try Again... ", "Error!", JOptionPane.ERROR_MESSAGE);
                    }

                    ///////////////////////////////////////
                    ///////////////////////////////////////
                }
                ////////////////////////////////////////////////////////////////
                ////////////////////////////////////////////////////////////////
                ////////////////////////////////////////////////////////////////
                con.close();
            } catch (Exception ea) {
                ea.printStackTrace();
            }

            /////////////////////////////////////////////////////////////////////////////////////
            /////////////////////////////////////////////////////////////////////////////////////
        }


    }//GEN-LAST:event_jButton_addUnitBtnActionPerformed

    private void jButton_clearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_clearActionPerformed

        generateUnitIds();
        jTextField_unitNm.setText("");
        jTextField_Symbol.setText("");
        
        jTextField_SearchUnit.setText("  Search For Unit Name...");
        
        ////////////////
        ////////////////
        DefaultTableModel model = (DefaultTableModel) jTable_UnitData.getModel();
        model.setRowCount(0);
        show_unit();         ///////////////  for refresh table
        ////////////////
        ////////////////

    }//GEN-LAST:event_jButton_clearActionPerformed

    private void jTable_UnitDataMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable_UnitDataMouseClicked

        int il = jTable_UnitData.getSelectedRow();
        TableModel model = jTable_UnitData.getModel();
        jLabel_unit_id.setText(model.getValueAt(il, 0).toString());
        jTextField_unitNm.setText(model.getValueAt(il, 1).toString());
        jTextField_Symbol.setText(model.getValueAt(il, 2).toString());

    }//GEN-LAST:event_jTable_UnitDataMouseClicked

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed

        String unitidd = jLabel_unit_id.getText();

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

            //////  SEARCH UNIT IS ALREADY USED OR NOT
            PreparedStatement su = con.prepareStatement("SELECT * FROM PRODUCT_TBL WHERE UNIT_IDNO = ?");
            su.setString(1, unitidd);
            int jk = su.executeUpdate();
            if(jk > 0){
                ////////////////////////////////////////////////////////////////
                JOptionPane.showMessageDialog(this, "Unit is Already Used!!", "Warning", JOptionPane.WARNING_MESSAGE);
                ////////////////////////////////////////////////////////////////
            }else{
                ////////////////////////////////////////////////////////////////
                ////////////////////////////////////////////////////////////////
                
                
                
                            /////// search for unit id present or not
                            PreparedStatement ps = con.prepareStatement("SELECT * FROM UNIT_TBL WHERE UNIT_IDNO = '" + unitidd + "'");
                            int req = ps.executeUpdate();
                            if (req > 0) {

                                PreparedStatement ps1 = con.prepareStatement("DELETE FROM UNIT_TBL WHERE UNIT_IDNO = '" + unitidd + "'");
                                int rem = ps1.executeUpdate();
                                if (rem > 0) {
                                    JOptionPane.showMessageDialog(null, " Unit Remove Successfullt... ", "Notice", JOptionPane.INFORMATION_MESSAGE);
                                    jTextField_unitNm.setText("");
                                    jTextField_Symbol.setText("");
                                    generateUnitIds();

                                    ////////////////
                                    ////////////////
                                    DefaultTableModel model = (DefaultTableModel) jTable_UnitData.getModel();
                                    model.setRowCount(0);
                                    show_unit();         ///////////////  for refresh table
                                    ////////////////
                                    ////////////////

                                } else {
                                    JOptionPane.showMessageDialog(null, " Error Occurred!! Please Try Again... ", "Error", JOptionPane.ERROR_MESSAGE);
                                }
                            } else {
                                JOptionPane.showMessageDialog(null, " Unit not Available!! ", "Error", JOptionPane.ERROR_MESSAGE);
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

    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton_updateUnitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_updateUnitActionPerformed

        String unitid = jLabel_unit_id.getText();
        String unitNm = jTextField_unitNm.getText();
        String unitSymbol = jTextField_Symbol.getText();

        if (unitid.isEmpty() || unitNm.isEmpty() || unitSymbol.isEmpty()) {
            JOptionPane.showMessageDialog(null, " Some Field is Empty!! ", "Notice", JOptionPane.WARNING_MESSAGE);
        } else {

            ///////////////////////////////////////////////////////////////////////////
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

                PreparedStatement ps = con.prepareStatement("UPDATE UNIT_TBL SET UNIT_NAME = ?, UNIT_SYMBOL = ? WHERE UNIT_IDNO = ?");
                ps.setString(1, unitNm);
                ps.setString(2, unitSymbol);
                ps.setString(3, unitid);
                
                int req = ps.executeUpdate();
                
                if(req > 0){
                    
                    JOptionPane.showMessageDialog(null, " Unit Updated Successfully!! ", "Success", JOptionPane.INFORMATION_MESSAGE);
                    jTextField_unitNm.setText("");
                    jTextField_Symbol.setText("");
                    generateUnitIds();

                    ////////////////
                    ////////////////
                    DefaultTableModel model = (DefaultTableModel) jTable_UnitData.getModel();
                    model.setRowCount(0);
                    show_unit();         ///////////////  for refresh table
                    ////////////////
                    ////////////////
                    
                }else{
                    JOptionPane.showMessageDialog(null, " Error Occurred!! Please Try Again... ", "Error", JOptionPane.ERROR_MESSAGE);
                }
                
                ////////////////////////////////////////////////////////////////
                ////////////////////////////////////////////////////////////////
                ////////////////////////////////////////////////////////////////
                con.close();
                
            }
            catch(Exception ea){
                ea.printStackTrace();
            }
            ///////////////////////////////////////////////////////////////////////////
        }

    }//GEN-LAST:event_jButton_updateUnitActionPerformed

    private void jTextField_SearchUnitFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField_SearchUnitFocusGained
        
        String srch = jTextField_SearchUnit.getText();
        if(srch.equalsIgnoreCase("  Search For Unit Name...")){
            jTextField_SearchUnit.setText("");
            jTextField_SearchUnit.setForeground(new Color(26, 198, 255));
        }
        
    }//GEN-LAST:event_jTextField_SearchUnitFocusGained

    private void jTextField_SearchUnitFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField_SearchUnitFocusLost
        
        if (jTextField_SearchUnit.getText().trim().equals("")) {
            jTextField_SearchUnit.setText("  Search For Unit Name...");
            jTextField_SearchUnit.setForeground(new Color(153, 153, 153));
        }
        
    }//GEN-LAST:event_jTextField_SearchUnitFocusLost

    private void jButton_SearchUnitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_SearchUnitActionPerformed
        
        String srchRslt = jTextField_SearchUnit.getText();
        
        if(srchRslt.equalsIgnoreCase("  Search For Unit Name...") || srchRslt.trim().equals("")){
            JOptionPane.showMessageDialog(null, " Please Search Some Thing... ", "Warning", JOptionPane.WARNING_MESSAGE);
        }
        else{
            ////////////////
            ////////////////
            DefaultTableModel model = (DefaultTableModel) jTable_UnitData.getModel();
            model.setRowCount(0);
            show_unit_searched();         ///////////////  for refresh table
            ////////////////
            ////////////////
        }
        
    }//GEN-LAST:event_jButton_SearchUnitActionPerformed

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

    public void generateUnitIds() {

        ///////   set Generated value of Product ID
        java.util.Random r = new java.util.Random();
        int x = r.nextInt(99999);
        String UnitId = "Unit-" + x + "IDNO";
        jLabel_unit_id.setText(UnitId);

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
            java.util.logging.Logger.getLogger(account_AddUnit_Sctn.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(account_AddUnit_Sctn.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(account_AddUnit_Sctn.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(account_AddUnit_Sctn.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new account_AddUnit_Sctn().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton_SearchUnit;
    private javax.swing.JButton jButton_addUnitBtn;
    private javax.swing.JButton jButton_clear;
    private javax.swing.JButton jButton_updateUnit;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel_Expenditure;
    private javax.swing.JLabel jLabel_bill;
    private javax.swing.JLabel jLabel_close;
    private javax.swing.JLabel jLabel_dashboard;
    private javax.swing.JLabel jLabel_product;
    private javax.swing.JLabel jLabel_production;
    private javax.swing.JLabel jLabel_profile;
    private javax.swing.JLabel jLabel_sell;
    private javax.swing.JLabel jLabel_unit_id;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable_UnitData;
    private javax.swing.JTextField jTextField_SearchUnit;
    private javax.swing.JTextField jTextField_Symbol;
    private javax.swing.JTextField jTextField_unitNm;
    // End of variables declaration//GEN-END:variables
}
