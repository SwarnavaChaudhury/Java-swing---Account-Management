/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package account_maintainer;

import java.awt.Color;
import java.io.File;
import java.net.InetAddress;
import java.net.NetworkInterface;
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
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

/**
 *
 * @author Welcome
 */
public class account_Search extends javax.swing.JFrame {

    /////  create border
    Border textFile_border = BorderFactory.createMatteBorder(0, 0, 2, 0, Color.red);
    Border textFile_borderNo = BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(0,204,204));
    
    
    /**
     * Creates new form account_DashBoard
     */
    public account_Search() {
        initComponents();
        
        //  center form
        this.setLocationRelativeTo(null);
        
        //////  set icon
        jLabel_profile.setIcon(new ImageIcon(getClass().getResource("IMAGES/useron.png")));
        
        //// make dashboard selected from sidebar
        jLabel_search1MouseEntered(null);
        
        
        /////  set data to table
        show_sell();
        
        
        
        //////   make shop Name and propitor checkbox true
        jCheckBox_shopNm.setSelected(true);
        jCheckBox_propNm.setSelected(true);
        
        
        
        //////  make Jtextarea non-editable
        jTextArea_BillArea.setEditable(false);
        
        
        
        
        
        
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
        jLabel_search = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel_profile = new javax.swing.JLabel();
        jLabel_close = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jTextField_srchBr = new javax.swing.JTextField();
        jButton_Search_btn = new javax.swing.JButton();
        jCheckBox_shopNm = new javax.swing.JCheckBox();
        jCheckBox_propNm = new javax.swing.JCheckBox();
        jCheckBox_Phno = new javax.swing.JCheckBox();
        jCheckBox_Eml = new javax.swing.JCheckBox();
        jCheckBox_gst = new javax.swing.JCheckBox();
        jCheckBox_Addr = new javax.swing.JCheckBox();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable_sellTbl = new javax.swing.JTable();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextArea_BillArea = new javax.swing.JTextArea();
        jButton1 = new javax.swing.JButton();
        jButton_clear_textField = new javax.swing.JButton();
        jLabel_search1 = new javax.swing.JLabel();

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

        jLabel_search.setBackground(new java.awt.Color(0, 204, 204));
        jLabel_search.setFont(new java.awt.Font("Shonar Bangla", 3, 36)); // NOI18N
        jLabel_search.setForeground(new java.awt.Color(255, 255, 255));
        jLabel_search.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel_search.setIcon(new javax.swing.ImageIcon(getClass().getResource("/account_maintainer/IMAGES/expenditure.png"))); // NOI18N
        jLabel_search.setText("Expenditure");
        jLabel_search.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel_search.setOpaque(true);
        jLabel_search.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel_searchMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel_searchMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel_searchMouseExited(evt);
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

        jLabel5.setFont(new java.awt.Font("Tahoma", 3, 33)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(51, 153, 255));
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("Search & E-Bill");

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

        jCheckBox_shopNm.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jCheckBox_shopNm.setText("Shop Name");

        jCheckBox_propNm.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jCheckBox_propNm.setText("Propitor Name");

        jCheckBox_Phno.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jCheckBox_Phno.setText("Phone Number");

        jCheckBox_Eml.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jCheckBox_Eml.setText("Email");

        jCheckBox_gst.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jCheckBox_gst.setText("GST Number");

        jCheckBox_Addr.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jCheckBox_Addr.setText("Address");

        jLabel1.setFont(new java.awt.Font("Times New Roman", 3, 25)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 204, 0));
        jLabel1.setText("Include Item in Bill");

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

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(jPanel4Layout.createSequentialGroup()
                            .addComponent(jTextField_srchBr)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(jButton_Search_btn, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 546, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jCheckBox_shopNm)
                            .addComponent(jCheckBox_Eml))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jCheckBox_gst)
                            .addComponent(jCheckBox_propNm))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jCheckBox_Addr)
                            .addComponent(jCheckBox_Phno))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jTextField_srchBr)
                    .addComponent(jButton_Search_btn, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 266, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jCheckBox_shopNm)
                    .addComponent(jCheckBox_propNm)
                    .addComponent(jCheckBox_Phno))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jCheckBox_Eml)
                    .addComponent(jCheckBox_gst)
                    .addComponent(jCheckBox_Addr))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Print Preview", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 20), new java.awt.Color(51, 204, 255))); // NOI18N

        jTextArea_BillArea.setColumns(20);
        jTextArea_BillArea.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        jTextArea_BillArea.setRows(5);
        jScrollPane2.setViewportView(jTextArea_BillArea);

        jButton1.setBackground(new java.awt.Color(51, 153, 255));
        jButton1.setFont(new java.awt.Font("Tempus Sans ITC", 3, 20)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/account_maintainer/IMAGES/bill.png"))); // NOI18N
        jButton1.setText("Print");
        jButton1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton_clear_textField.setBackground(new java.awt.Color(51, 153, 255));
        jButton_clear_textField.setFont(new java.awt.Font("Tempus Sans ITC", 3, 20)); // NOI18N
        jButton_clear_textField.setForeground(new java.awt.Color(255, 255, 255));
        jButton_clear_textField.setIcon(new javax.swing.ImageIcon(getClass().getResource("/account_maintainer/IMAGES/reset.png"))); // NOI18N
        jButton_clear_textField.setText("Clear");
        jButton_clear_textField.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton_clear_textField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_clear_textFieldActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton_clear_textField, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(44, 44, 44)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 439, Short.MAX_VALUE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 337, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton_clear_textField)
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 8, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel_profile, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel_close, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel_close, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel_profile, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 8, Short.MAX_VALUE))
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        jLabel_search1.setBackground(new java.awt.Color(0, 204, 204));
        jLabel_search1.setFont(new java.awt.Font("Shonar Bangla", 3, 36)); // NOI18N
        jLabel_search1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel_search1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel_search1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/account_maintainer/IMAGES/searchOn.png"))); // NOI18N
        jLabel_search1.setText("Search");
        jLabel_search1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel_search1.setOpaque(true);
        jLabel_search1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel_search1MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel_search1MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel_search1MouseExited(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jLabel_search, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel_production, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel_product, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel_dashboard, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 197, Short.MAX_VALUE)
                    .addComponent(jLabel_sell, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel_search1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
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
                .addComponent(jLabel_search, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel_search1, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
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

    private void jLabel_searchMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel_searchMouseEntered
        
        jLabel_search.setBackground(new Color(255, 255, 255));
        jLabel_search.setForeground(new Color(0, 204, 204));
        jLabel_search.setBorder(textFile_border);
        
    }//GEN-LAST:event_jLabel_searchMouseEntered

    private void jLabel_searchMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel_searchMouseExited
        
        jLabel_search.setBackground(new Color(0, 204, 204));
        jLabel_search.setForeground(new Color(255, 255, 255));
        jLabel_search.setBorder(textFile_borderNo);
        
    }//GEN-LAST:event_jLabel_searchMouseExited

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

    private void jLabel_profileMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel_profileMouseClicked
        
        account_Profile form = new account_Profile();
        form.setVisible(true);
        form.pack();
        form.setLocationRelativeTo(null);
        //close the current form (login form)
        this.dispose();
        
    }//GEN-LAST:event_jLabel_profileMouseClicked

    private void jLabel_search1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel_search1MouseEntered
        
        jLabel_search1.setBackground(new Color(255, 255, 255));
        jLabel_search1.setForeground(new Color(0, 204, 204));
        jLabel_search1.setBorder(textFile_border);
        
    }//GEN-LAST:event_jLabel_search1MouseEntered

    private void jLabel_search1MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel_search1MouseExited
        
        
        
    }//GEN-LAST:event_jLabel_search1MouseExited

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
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        
        try{
            
            jTextArea_BillArea.print();
            
        }catch(Exception ea){
            ea.printStackTrace();
        }
        
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jLabel_search1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel_search1MouseClicked
        
        account_Search form = new account_Search();
        form.setVisible(true);
        form.pack();
        form.setLocationRelativeTo(null);
        //close the current form (login form)
        this.dispose();
        
    }//GEN-LAST:event_jLabel_search1MouseClicked

    private void jButton_clear_textFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_clear_textFieldActionPerformed
        
        jTextField_srchBr.setText("Search for Sell Bill or Date/Time...");
        
        ////////////////
        ////////////////
        DefaultTableModel model = (DefaultTableModel) jTable_sellTbl.getModel();
        model.setRowCount(0);
        show_sell();         ///////////////  for refresh table
        ////////////////
        ////////////////
        
        jTextArea_BillArea.setText("");
        
    }//GEN-LAST:event_jButton_clear_textFieldActionPerformed

    private void jTable_sellTblMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable_sellTblMouseClicked

        int il = jTable_sellTbl.getSelectedRow();
        TableModel model = jTable_sellTbl.getModel();

        String Sell_Bill_id = model.getValueAt(il, 0).toString();
        String Mode_use = model.getValueAt(il, 1).toString();
        String Description = model.getValueAt(il, 2).toString();
        String Total_priceToPay = model.getValueAt(il, 3).toString();
        String Date_time = model.getValueAt(il, 4).toString();
        
        
        
        
        String Shop_Nm = "";
        String Prop_Nm = "";
        String Phno = "";
        String Email = "";
        String GstIn = "";
        String Addr = "";
        
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

            //////////////////////////////////////////////////////////////////////////
            //////////////////////////////////////////////////////////////////////////

            PreparedStatement ps = con.prepareStatement("SELECT * FROM USERS");
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                Shop_Nm = rs.getString("SHOPNAME");
                Prop_Nm = rs.getString("PROP_NAME");
                Phno = rs.getString("PROP_PHNO");
                Email = rs.getString("PROP_EMAIL");
                GstIn = rs.getString("PROP_GST");
                Addr = rs.getString("SHOP_ADDR");
            }

            /////////////////////////////////////////////////////////
            /////////////////////////////////////////////////////////
            /////////////////////////////////////////////////////////
            con.close();
            
        }
        catch(Exception ea){
            ea.printStackTrace();
        }
        
        ////////////////////////////////////////////////////////////////////////
        ////////////////////////////////////////////////////////////////////////
        ////////////////////////////////////////////////////////////////////////
        
        
        
        
        
        
        
        jTextArea_BillArea.setText("***********************************************************************************************\n");
        
        if(jCheckBox_shopNm.isSelected() == true){
            jTextArea_BillArea.append("*\t Shop Name : "+Shop_Nm+" \t*\n");
        }
        
        if(jCheckBox_propNm.isSelected() == true){
            jTextArea_BillArea.append("*\t Propitor Name : "+Prop_Nm+" \t*\n");
        }
        
        if(jCheckBox_Phno.isSelected() == true){
            jTextArea_BillArea.append("*\t Phone : "+Phno+" \t*\n");
        }
        
        if(jCheckBox_Eml.isSelected() == true){
            jTextArea_BillArea.append("*\t Email ID : "+Email+" \t*\n");
        }
        
        if(jCheckBox_gst.isSelected() == true){
            jTextArea_BillArea.append("*\t GSTIN : "+GstIn+" \t*\n");
        }
        
        if(jCheckBox_Addr.isSelected() == true){
            jTextArea_BillArea.append("*\t Address :"+Addr+" \t*\n");
        }
        
        jTextArea_BillArea.append("***********************************************************************************************\n\n");
        
        
        
        jTextArea_BillArea.append("\t Bill ID : "+Sell_Bill_id+"\n");
        jTextArea_BillArea.append("\t Mode : "+Mode_use+"\n");
        jTextArea_BillArea.append("\t Date : "+Date_time+"\n");
        
//        String[] perDescrip = Description.split("\n");
//        for (int q = 0 ; q < perDescrip.length ; q++ ){
//            jTextArea_BillArea.append("\t Product : "+perDescrip[q]+"\n");
//        }

        
        ////////////////////////////////////////////////////////////////////////
        //////////////////////////////////////////////////////////////////////////
        //////////////////////////////////////////////////////////////////////////
//        Connection con;
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
            jTextArea_BillArea.append("\nProduct"+"\t"+"Quantity"+"\t"+"Selling Price(per Unit)"+"\t"+"Total Price"+"\n");
            jTextArea_BillArea.append("-------"+"\t"+"--------"+"\t"+"-----------------------"+"\t"+"-----------"+"\n");
            PreparedStatement ps = con.prepareStatement("SELECT PRODUCT_TBL.PRODUCT_NM, UNIT_TBL.UNIT_SYMBOL, SELL_QTY_TBL.PRODUCT_QTY_SELL,PRODUCT_SELLING_PRICE,SELLING_TTL_PRICE FROM UNIT_TBL,PRODUCT_TBL,SELL_QTY_TBL WHERE UNIT_TBL.UNIT_IDNO=PRODUCT_TBL.UNIT_IDNO AND PRODUCT_TBL.PROIDNO=SELL_QTY_TBL.PROIDNO AND SELL_QTY_TBL.SELL_ID = ?");            
            ps.setString(1, Sell_Bill_id);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                jTextArea_BillArea.append(rs.getString("PRODUCT_NM")+"\t"+rs.getString("PRODUCT_QTY_SELL")+" "+rs.getString("UNIT_SYMBOL")+"\t"+rs.getString("SELLING_TTL_PRICE")+"/-(per "+rs.getString("UNIT_SYMBOL")+")"+"\t"+rs.getString("SELLING_TTL_PRICE")+"\n");
            }
            
            
            ////////////////////////////////////////////////////////////////////
            ////////////////////////////////////////////////////////////////////
            ////////////////////////////////////////////////////////////////////
            con.close();
        }
        catch(Exception ea){
            ea.printStackTrace();
        }
        ////////////////////////////////////////////////////////////////////////

        
        jTextArea_BillArea.append("\n---------------------------------------------------------------------------------------------\n");
        jTextArea_BillArea.append("\t\t Total Amount : "+Total_priceToPay+"\n");
        
        
        
        jTextArea_BillArea.append("\n\t\t\t Signature\n");
        jTextArea_BillArea.append("***********************************************************************************************\n");
        jTextArea_BillArea.append("*\t\t Thanking Comming Here \t\t*\n");
        jTextArea_BillArea.append("***********************************************************************************************");
        
        
        
        
        
        
        ////////////////////////////////////////////////////////////////////////
        ////////////////////////////////////////////////////////////////////////
        ////////////////////////////////////////////////////////////////////////

        
    }//GEN-LAST:event_jTable_sellTblMouseClicked

    private void jLabel_searchMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel_searchMouseClicked
        
        account_Expenditure form = new account_Expenditure();
        form.setVisible(true);
        form.pack();
        form.setLocationRelativeTo(null);
        //close the current form
        this.dispose();
        
    }//GEN-LAST:event_jLabel_searchMouseClicked

    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
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
            java.util.logging.Logger.getLogger(account_Search.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(account_Search.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(account_Search.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(account_Search.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new account_Search().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton_Search_btn;
    private javax.swing.JButton jButton_clear_textField;
    private javax.swing.JCheckBox jCheckBox_Addr;
    private javax.swing.JCheckBox jCheckBox_Eml;
    private javax.swing.JCheckBox jCheckBox_Phno;
    private javax.swing.JCheckBox jCheckBox_gst;
    private javax.swing.JCheckBox jCheckBox_propNm;
    private javax.swing.JCheckBox jCheckBox_shopNm;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel_close;
    private javax.swing.JLabel jLabel_dashboard;
    private javax.swing.JLabel jLabel_product;
    private javax.swing.JLabel jLabel_production;
    private javax.swing.JLabel jLabel_profile;
    private javax.swing.JLabel jLabel_search;
    private javax.swing.JLabel jLabel_search1;
    private javax.swing.JLabel jLabel_sell;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable_sellTbl;
    private javax.swing.JTextArea jTextArea_BillArea;
    private javax.swing.JTextField jTextField_srchBr;
    // End of variables declaration//GEN-END:variables
}
