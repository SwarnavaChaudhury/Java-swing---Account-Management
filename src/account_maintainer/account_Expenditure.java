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
import java.net.InetAddress;
import java.net.NetworkInterface;
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
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

/**
 *
 * @author Welcome
 */
public class account_Expenditure extends javax.swing.JFrame {

    /////  create border
    Border textFile_border = BorderFactory.createMatteBorder(0, 0, 2, 0, Color.red);
    Border textFile_borderNo = BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(0,204,204));
    
    
    
    /**
     * Creates new form account_DashBoard
     */
    public account_Expenditure() {
        initComponents();
        
        //  center form
        this.setLocationRelativeTo(null);
        
        //////  set icon
        jLabel_profile.setIcon(new ImageIcon(getClass().getResource("IMAGES/useron.png")));
        
        //// make dashboard selected from sidebar
        jLabel_expenditureMouseEntered(null);
        
        
        ///////////////  SET BORDER
        jTextField_Expenditure_Price.setBorder(textFile_border);
        
        
        /////////    set value of jCombo box
        Expn_tpe();
        
        
        ////////    set expenditure id
        generatte_Expn_id();
        
        //////////   set year
        Date d = new Date();
        int year = d.getYear();
        int currentYear = year + 1900;
        jLabel_Year.setText(String.valueOf(currentYear));
        
        /////////    set months
        LocalDate currentdate = LocalDate.now();
        Month currentMonth = currentdate.getMonth();
        jLabel_month.setText(String.valueOf(currentMonth));
        
        
        
        ///////////////////  show data in jTable from db
        show_expenditure();
        
        
    }

    
    
    
    
    
    
    
    
    
    
    /////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////////
    ////  ~~~~~~~~~   Show data of jTable manufactur
    public ArrayList<expenditure> expenditureList() {
        ArrayList<expenditure> expenditureList = new ArrayList<>();

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

            expenditure expenditure;

            PreparedStatement ps = con.prepareStatement("SELECT * FROM OTHER_EXPENSES_TBL");
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                ////////////////////////////////////////////////////////////////
                ////////////////////////////////////////////////////////////////
                
                String expn_id = rs.getString("EXPID");
                String expn_sess = rs.getString("EXP_SESSION");
                String expn_mnths = rs.getString("EXP_MONTH");
                String expn_Dttm = rs.getString("EXP_ADD_DTTM");
                
                String expn_dtls = "";
                
                PreparedStatement ps1 = con.prepareStatement("SELECT * FROM OTHER_EXPENSES_TBL_DETAILS WHERE EXPID = ?");
                ps1.setString(1, expn_id);
                ResultSet rs1 = ps1.executeQuery();
                while(rs1.next()){
                    String expn_type = rs1.getString("EXPENDITURE_TYPE");
                    String expn_Price = rs1.getString("EXPENDITURE_PRICE");
                    expn_dtls += expn_type +"->"+expn_Price+"/- , ";
                }
                
                String finalExpn_dtls = expn_dtls.substring(0, expn_dtls.length()-3);
                
                expenditure = new expenditure(expn_id, expn_sess, expn_mnths, expn_Dttm, finalExpn_dtls);
                expenditureList.add(expenditure);
                
                ////////////////////////////////////////////////////////////////
                ////////////////////////////////////////////////////////////////
            }
            
            /////////////////////////////////////////////
            /////////////////////////////////////////////
            /////////////////////////////////////////////
            con.close();
        } catch (Exception ea) {
            ea.printStackTrace();
        }
        ////////////////////////////////////////////////////////////////////////
        return expenditureList;
        ////////////////////////////////////////////////////////////////////////
    }
    
    
    
    public void show_expenditure() {
        ArrayList<expenditure> expenditureLst = expenditureList();
        DefaultTableModel model = (DefaultTableModel) jTable_contains_ExpenditureDtls.getModel();
        Object[] row = new Object[5];
        for (int i = 0; i < expenditureLst.size(); i++) {
            row[0] = expenditureLst.get(i).getexp_id();
            row[1] = expenditureLst.get(i).getexp_sess();
            row[2] = expenditureLst.get(i).getexp_mnts();
            row[3] = expenditureLst.get(i).getexp_dttm();
            row[4] = expenditureLst.get(i).getexp_dtls();
            model.addRow(row);
        }
    }
    
    
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
        jLabel_expenditure = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel_profile = new javax.swing.JLabel();
        jLabel_close = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jLabel_Year = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel_month = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jTextField_Expenditure_Price = new javax.swing.JTextField();
        jButton_str_tempTbl = new javax.swing.JButton();
        jButton_remove_item_tempTbl = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable_tempStr_val = new javax.swing.JTable();
        jButton_Store = new javax.swing.JButton();
        jButton_reset = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        jLabel_entryID = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jComboBox_ExpnDtr_type = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable_contains_ExpenditureDtls = new javax.swing.JTable();
        jButton_Exp_Download = new javax.swing.JButton();
        jButton_SearchBtn = new javax.swing.JButton();
        jComboBox_Session = new javax.swing.JComboBox<>();
        jComboBox_month = new javax.swing.JComboBox<>();
        jButton_remove = new javax.swing.JButton();
        jButton_categoryWise_ViewPg = new javax.swing.JButton();
        jLabel_search = new javax.swing.JLabel();

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

        jLabel_expenditure.setBackground(new java.awt.Color(0, 204, 204));
        jLabel_expenditure.setFont(new java.awt.Font("Shonar Bangla", 3, 36)); // NOI18N
        jLabel_expenditure.setForeground(new java.awt.Color(255, 255, 255));
        jLabel_expenditure.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel_expenditure.setIcon(new javax.swing.ImageIcon(getClass().getResource("/account_maintainer/IMAGES/expenditure.png"))); // NOI18N
        jLabel_expenditure.setText("Expenditure");
        jLabel_expenditure.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel_expenditure.setOpaque(true);
        jLabel_expenditure.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel_expenditureMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel_expenditureMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel_expenditureMouseExited(evt);
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
        jLabel5.setText("Other Expenditure");

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));

        jLabel6.setFont(new java.awt.Font("Tempus Sans ITC", 3, 18)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 204, 255));
        jLabel6.setText("Year :");

        jLabel_Year.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jLabel_Year.setText(".....");

        jLabel7.setFont(new java.awt.Font("Tempus Sans ITC", 3, 18)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(0, 204, 255));
        jLabel7.setText("Month :");

        jLabel_month.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jLabel_month.setText(".....");

        jLabel8.setFont(new java.awt.Font("Tempus Sans ITC", 3, 18)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(0, 204, 255));
        jLabel8.setText("Expenditure :");

        jLabel9.setFont(new java.awt.Font("Tempus Sans ITC", 3, 18)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(0, 204, 255));
        jLabel9.setText("Value :");

        jTextField_Expenditure_Price.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jTextField_Expenditure_Price.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextField_Expenditure_PriceKeyPressed(evt);
            }
        });

        jButton_str_tempTbl.setBackground(new java.awt.Color(51, 255, 0));
        jButton_str_tempTbl.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jButton_str_tempTbl.setForeground(new java.awt.Color(255, 255, 255));
        jButton_str_tempTbl.setText("Add");
        jButton_str_tempTbl.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton_str_tempTbl.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_str_tempTblActionPerformed(evt);
            }
        });

        jButton_remove_item_tempTbl.setBackground(new java.awt.Color(255, 0, 0));
        jButton_remove_item_tempTbl.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jButton_remove_item_tempTbl.setForeground(new java.awt.Color(255, 255, 255));
        jButton_remove_item_tempTbl.setText("Remove");
        jButton_remove_item_tempTbl.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton_remove_item_tempTbl.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_remove_item_tempTblActionPerformed(evt);
            }
        });

        jTable_tempStr_val.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Expenditure Type", "Price"
            }
        ));
        jScrollPane2.setViewportView(jTable_tempStr_val);

        jButton_Store.setBackground(new java.awt.Color(51, 255, 0));
        jButton_Store.setFont(new java.awt.Font("Tempus Sans ITC", 3, 20)); // NOI18N
        jButton_Store.setForeground(new java.awt.Color(255, 255, 255));
        jButton_Store.setIcon(new javax.swing.ImageIcon(getClass().getResource("/account_maintainer/IMAGES/plusIcn.png"))); // NOI18N
        jButton_Store.setText("Store");
        jButton_Store.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton_Store.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_StoreActionPerformed(evt);
            }
        });

        jButton_reset.setBackground(new java.awt.Color(51, 102, 255));
        jButton_reset.setFont(new java.awt.Font("Tempus Sans ITC", 3, 20)); // NOI18N
        jButton_reset.setForeground(new java.awt.Color(255, 255, 255));
        jButton_reset.setIcon(new javax.swing.ImageIcon(getClass().getResource("/account_maintainer/IMAGES/reset.png"))); // NOI18N
        jButton_reset.setText("Reset");
        jButton_reset.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton_reset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_resetActionPerformed(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Tempus Sans ITC", 3, 18)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(0, 204, 255));
        jLabel10.setText("Entry ID :");

        jLabel_entryID.setFont(new java.awt.Font("Tempus Sans ITC", 3, 16)); // NOI18N
        jLabel_entryID.setForeground(new java.awt.Color(51, 204, 255));
        jLabel_entryID.setText(".....");

        jButton1.setBackground(new java.awt.Color(153, 102, 255));
        jButton1.setFont(new java.awt.Font("Times New Roman", 3, 20)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Add Expenditure Type");
        jButton1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jComboBox_ExpnDtr_type.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addGap(0, 44, Short.MAX_VALUE)
                        .addComponent(jButton_reset, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(39, 39, 39)
                        .addComponent(jButton_Store, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(42, 42, 42))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jButton_remove_item_tempTbl, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jButton_str_tempTbl, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(29, 29, 29)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel_month, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel_Year, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jTextField_Expenditure_Price, javax.swing.GroupLayout.DEFAULT_SIZE, 225, Short.MAX_VALUE)
                                    .addComponent(jLabel_entryID, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jComboBox_ExpnDtr_type, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                        .addGap(19, 19, 19))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 287, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(76, 76, 76))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel_entryID, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel_Year, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel_month, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jComboBox_ExpnDtr_type, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jTextField_Expenditure_Price, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton_str_tempTbl, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton_remove_item_tempTbl, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton_Store, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton_reset, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jLabel1.setBackground(new java.awt.Color(255, 255, 255));
        jLabel1.setFont(new java.awt.Font("Tempus Sans ITC", 3, 22)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Expenditure List");

        jTable_contains_ExpenditureDtls.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jTable_contains_ExpenditureDtls.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Exp. Id", "Session", "Month", "Add At", "Expenditure Details"
            }
        ));
        jTable_contains_ExpenditureDtls.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable_contains_ExpenditureDtlsMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(jTable_contains_ExpenditureDtls);

        jButton_Exp_Download.setBackground(new java.awt.Color(51, 255, 0));
        jButton_Exp_Download.setFont(new java.awt.Font("Times New Roman", 3, 24)); // NOI18N
        jButton_Exp_Download.setForeground(new java.awt.Color(255, 255, 255));
        jButton_Exp_Download.setText("Download");
        jButton_Exp_Download.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton_Exp_Download.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_Exp_DownloadActionPerformed(evt);
            }
        });

        jButton_SearchBtn.setBackground(new java.awt.Color(51, 204, 0));
        jButton_SearchBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/account_maintainer/IMAGES/searchOn.png"))); // NOI18N
        jButton_SearchBtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton_SearchBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_SearchBtnActionPerformed(evt);
            }
        });

        jComboBox_Session.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        jComboBox_Session.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select Year From Below", "2020", "2021", "2022", "2023", "2024", "2025", "2026", "2027", "2028", "2029", "2030", "2031", "2032", "2033", "2034", "2035", "2036", "2037", "2038", "2039", "2040", "2041", "2042", "2043", "2044", "2045", "2046", "2047", "2048", "2049", "2050", "2051", "2052", "2053", "2054", "2055", "2056", "2057", "2058", "2059", "2060", "2061", "2062", "2063", "2064", "2065", "2066", "2067", "2068", "2069", "2070", "2071", "2072", "2073", "2074", "2075", "2076", "2077", "2078", "2079", "2080", "2081", "2082", "2083", "2084", "2085", "2086", "2087", "2088", "2089", "2090", "2091", "2092", "2093", "2094", "2095", "2096", "2097", "2098", "2099", "2100", "2101", "2102", "2103", "2104", "2105", "2106", "2107", "2108", "2109", "2110", "2111", "2112", "2113", "2114", "2115", "2116", "2117", "2118", "2119", "2120", "2121", "2122", "2123", "2124", "2125", "2126", "2127", "2128", "2129", "2130", "2131", "2132", "2133", "2134", "2135", "2136", "2137", "2138", "2139", "2140", "2141", "2142", "2143", "2144", "2145", "2146", "2147", "2148", "2149", "2150", "2151", "2152", "2153", "2154", "2155", "2156", "2157", "2158", "2159", "2160", "2161", "2162", "2163", "2164", "2165", "2166", "2167", "2168", "2169", "2170", "2171", "2172", "2173", "2174", "2175", "2176", "2177", "2178", "2179", "2180", "2181", "2182", "2183", "2184", "2185", "2186", "2187", "2188", "2189", "2190", "2191", "2192", "2193", "2194", "2195", "2196", "2197", "2198", "2199", "2200", "2201", "2202", "2203", "2204", "2205", "2206", "2207", "2208", "2209", "2210", "2211", "2212", "2213", "2214", "2215", "2216", "2217", "2218", "2219", "2220", "2221", "2222", "2223", "2224", "2225", "2226", "2227", "2228", "2229", "2230", "2231", "2232", "2233", "2234", "2235", "2236", "2237", "2238", "2239", "2240", "2241", "2242", "2243", "2244", "2245", "2246", "2247", "2248", "2249", "2250", "2251", "2252", "2253", "2254", "2255", "2256", "2257", "2258", "2259", "2260", "2261", "2262", "2263", "2264", "2265", "2266", "2267", "2268", "2269", "2270", "2271", "2272", "2273", "2274", "2275", "2276", "2277", "2278", "2279", "2280", "2281", "2282", "2283", "2284", "2285", "2286", "2287", "2288", "2289", "2290", "2291", "2292", "2293", "2294", "2295", "2296", "2297", "2298", "2299", "2300", "2301", "2302", "2303", "2304", "2305", "2306", "2307", "2308", "2309", "2310", "2311", "2312", "2313", "2314", "2315", "2316", "2317", "2318", "2319", "2320", "2321", "2322", "2323", "2324", "2325", "2326", "2327", "2328", "2329", "2330", "2331", "2332", "2333", "2334", "2335", "2336", "2337", "2338", "2339", "2340", "2341", "2342", "2343", "2344", "2345", "2346", "2347", "2348", "2349", "2350", "2351", "2352", "2353", "2354", "2355", "2356", "2357", "2358", "2359", "2360", "2361", "2362", "2363", "2364", "2365", "2366", "2367", "2368", "2369", "2370", "2371", "2372", "2373", "2374", "2375", "2376", "2377", "2378", "2379", "2380", "2381", "2382", "2383", "2384", "2385", "2386", "2387", "2388", "2389", "2390", "2391", "2392", "2393", "2394", "2395", "2396", "2397", "2398", "2399", "2400", "2401", "2402", "2403", "2404", "2405", "2406", "2407", "2408", "2409", "2410", "2411", "2412", "2413", "2414", "2415", "2416", "2417", "2418", "2419", "2420", "2421", "2422", "2423", "2424", "2425", "2426", "2427", "2428", "2429", "2430", "2431", "2432", "2433", "2434", "2435", "2436", "2437", "2438", "2439", "2440", "2441", "2442", "2443", "2444", "2445", "2446", "2447", "2448", "2449", "2450", "2451", "2452", "2453", "2454", "2455", "2456", "2457", "2458", "2459", "2460", "2461", "2462", "2463", "2464", "2465", "2466", "2467", "2468", "2469", "2470", "2471", "2472", "2473", "2474", "2475", "2476", "2477", "2478", "2479", "2480", "2481", "2482", "2483", "2484", "2485", "2486", "2487", "2488", "2489", "2490", "2491", "2492", "2493", "2494", "2495", "2496", "2497", "2498", "2499", "2500", "2501", "2502", "2503", "2504", "2505", "2506", "2507", "2508", "2509", "2510", "2511", "2512", "2513", "2514", "2515", "2516", "2517", "2518", "2519", "2520", "2521", "2522", "2523", "2524", "2525", "2526", "2527", "2528", "2529", "2530", "2531", "2532", "2533", "2534", "2535", "2536", "2537", "2538", "2539", "2540", "2541", "2542", "2543", "2544", "2545", "2546", "2547", "2548", "2549", "2550", "2551", "2552", "2553", "2554", "2555", "2556", "2557", "2558", "2559", "2560", "2561", "2562", "2563", "2564", "2565", "2566", "2567", "2568", "2569", "2570", "2571", "2572", "2573", "2574", "2575", "2576", "2577", "2578", "2579", "2580", "2581", "2582", "2583", "2584", "2585", "2586", "2587", "2588", "2589", "2590", "2591", "2592", "2593", "2594", "2595", "2596", "2597", "2598", "2599", "2600", "2601", "2602", "2603", "2604", "2605", "2606", "2607", "2608", "2609", "2610", "2611", "2612", "2613", "2614", "2615", "2616", "2617", "2618", "2619", "2620", "2621", "2622", "2623", "2624", "2625", "2626", "2627", "2628", "2629", "2630", "2631", "2632", "2633", "2634", "2635", "2636", "2637", "2638", "2639", "2640", "2641", "2642", "2643", "2644", "2645", "2646", "2647", "2648", "2649", "2650", "2651", "2652", "2653", "2654", "2655", "2656", "2657", "2658", "2659", "2660", "2661", "2662", "2663", "2664", "2665", "2666", "2667", "2668", "2669", "2670", "2671", "2672", "2673", "2674", "2675", "2676", "2677", "2678", "2679", "2680", "2681", "2682", "2683", "2684", "2685", "2686", "2687", "2688", "2689", "2690", "2691", "2692", "2693", "2694", "2695", "2696", "2697", "2698", "2699", "2700", "2701", "2702", "2703", "2704", "2705", "2706", "2707", "2708", "2709", "2710", "2711", "2712", "2713", "2714", "2715", "2716", "2717", "2718", "2719", "2720", "2721", "2722", "2723", "2724", "2725", "2726", "2727", "2728", "2729", "2730", "2731", "2732", "2733", "2734", "2735", "2736", "2737", "2738", "2739", "2740", "2741", "2742", "2743", "2744", "2745", "2746", "2747", "2748", "2749", "2750", "2751", "2752", "2753", "2754", "2755", "2756", "2757", "2758", "2759", "2760", "2761", "2762", "2763", "2764", "2765", "2766", "2767", "2768", "2769", "2770", "2771", "2772", "2773", "2774", "2775", "2776", "2777", "2778", "2779", "2780", "2781", "2782", "2783", "2784", "2785", "2786", "2787", "2788", "2789", "2790", "2791", "2792", "2793", "2794", "2795", "2796", "2797", "2798", "2799", "2800", "2801", "2802", "2803", "2804", "2805", "2806", "2807", "2808", "2809", "2810", "2811", "2812", "2813", "2814", "2815", "2816", "2817", "2818", "2819", "2820", "2821", "2822", "2823", "2824", "2825", "2826", "2827", "2828", "2829", "2830", "2831", "2832", "2833", "2834", "2835", "2836", "2837", "2838", "2839", "2840", "2841", "2842", "2843", "2844", "2845", "2846", "2847", "2848", "2849", "2850", "2851", "2852", "2853", "2854", "2855", "2856", "2857", "2858", "2859", "2860", "2861", "2862", "2863", "2864", "2865", "2866", "2867", "2868", "2869", "2870", "2871", "2872", "2873", "2874", "2875", "2876", "2877", "2878", "2879", "2880", "2881", "2882", "2883", "2884", "2885", "2886", "2887", "2888", "2889", "2890", "2891", "2892", "2893", "2894", "2895", "2896", "2897", "2898", "2899", "2900", "2901", "2902", "2903", "2904", "2905", "2906", "2907", "2908", "2909", "2910", "2911", "2912", "2913", "2914", "2915", "2916", "2917", "2918", "2919", "2920", "2921", "2922", "2923", "2924", "2925", "2926", "2927", "2928", "2929", "2930", "2931", "2932", "2933", "2934", "2935", "2936", "2937", "2938", "2939", "2940", "2941", "2942", "2943", "2944", "2945", "2946", "2947", "2948", "2949", "2950", "2951", "2952", "2953", "2954", "2955", "2956", "2957", "2958", "2959", "2960", "2961", "2962", "2963", "2964", "2965", "2966", "2967", "2968", "2969", "2970", "2971", "2972", "2973", "2974", "2975", "2976", "2977", "2978", "2979", "2980", "2981", "2982", "2983", "2984", "2985", "2986", "2987", "2988", "2989", "2990", "2991", "2992", "2993", "2994", "2995", "2996", "2997", "2998", "2999", "3000", "3001", "3002", "3003", "3004", "3005", "3006", "3007", "3008", "3009", "3010", "3011", "3012", "3013", "3014", "3015", "3016", "3017", "3018", "3019", "3020" }));

        jComboBox_month.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        jComboBox_month.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select Month From Below", "JANUARY", "FEBRUARY", "MARCH", "APRIL", "MAY", "JUNE", "JULY", "AUGUST", "SEPTEMBER", "OCTOBER", "NOVEMBER", "DECEMBER" }));

        jButton_remove.setBackground(new java.awt.Color(255, 0, 0));
        jButton_remove.setFont(new java.awt.Font("Tempus Sans ITC", 3, 20)); // NOI18N
        jButton_remove.setForeground(new java.awt.Color(255, 255, 255));
        jButton_remove.setIcon(new javax.swing.ImageIcon(getClass().getResource("/account_maintainer/IMAGES/delete.png"))); // NOI18N
        jButton_remove.setText("Remove");
        jButton_remove.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton_remove.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_removeActionPerformed(evt);
            }
        });

        jButton_categoryWise_ViewPg.setBackground(new java.awt.Color(102, 102, 255));
        jButton_categoryWise_ViewPg.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jButton_categoryWise_ViewPg.setForeground(new java.awt.Color(255, 255, 255));
        jButton_categoryWise_ViewPg.setText("Catagory Wise");
        jButton_categoryWise_ViewPg.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton_categoryWise_ViewPg.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jButton_categoryWise_ViewPgMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jButton_categoryWise_ViewPgMouseExited(evt);
            }
        });
        jButton_categoryWise_ViewPg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_categoryWise_ViewPgActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(0, 283, Short.MAX_VALUE)
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 338, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(206, 206, 206)
                        .addComponent(jLabel_profile, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel_close, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(67, 67, 67)
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 335, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addGap(12, 12, 12)
                                        .addComponent(jButton_categoryWise_ViewPg, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGap(18, 18, 18)
                                        .addComponent(jButton_remove)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jButton_Exp_Download))
                                    .addComponent(jScrollPane3)
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addComponent(jComboBox_Session, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jComboBox_month, 0, 0, Short.MAX_VALUE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jButton_SearchBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)))))))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel_close, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel_profile, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton_SearchBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jComboBox_Session)
                            .addComponent(jComboBox_month))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 292, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jButton_remove, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jButton_Exp_Download, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jButton_categoryWise_ViewPg, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(2, 2, 2))))
        );

        jLabel_search.setBackground(new java.awt.Color(0, 204, 204));
        jLabel_search.setFont(new java.awt.Font("Shonar Bangla", 3, 36)); // NOI18N
        jLabel_search.setForeground(new java.awt.Color(255, 255, 255));
        jLabel_search.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel_search.setIcon(new javax.swing.ImageIcon(getClass().getResource("/account_maintainer/IMAGES/searchOn.png"))); // NOI18N
        jLabel_search.setText("Search");
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

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jLabel_expenditure, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel_production, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel_product, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel_dashboard, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 197, Short.MAX_VALUE)
                    .addComponent(jLabel_sell, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel_search, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
                .addComponent(jLabel_expenditure, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel_search, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
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
        
        jLabel_sell.setBackground(new Color(0, 204, 204));
        jLabel_sell.setForeground(new Color(255, 255, 255));
        jLabel_sell.setBorder(textFile_borderNo);
        
    }//GEN-LAST:event_jLabel_sellMouseExited

    private void jLabel_expenditureMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel_expenditureMouseEntered
        
        jLabel_expenditure.setBackground(new Color(255, 255, 255));
        jLabel_expenditure.setForeground(new Color(0, 204, 204));
        jLabel_expenditure.setBorder(textFile_border);
        
    }//GEN-LAST:event_jLabel_expenditureMouseEntered

    private void jLabel_expenditureMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel_expenditureMouseExited
        
        
        
    }//GEN-LAST:event_jLabel_expenditureMouseExited

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

    private void jTextField_Expenditure_PriceKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField_Expenditure_PriceKeyPressed
        
        if (evt.getKeyChar() >= '0' && evt.getKeyChar() <= '9' || evt.getKeyCode() == KeyEvent.VK_BACK_SPACE || evt.getKeyChar() == KeyEvent.VK_DECIMAL || evt.getKeyChar() == KeyEvent.VK_PERIOD) {
            jTextField_Expenditure_Price.setEditable(true);
        } else {
            jTextField_Expenditure_Price.setEditable(false);
        }
        
    }//GEN-LAST:event_jTextField_Expenditure_PriceKeyPressed

    private void jLabel_expenditureMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel_expenditureMouseClicked
        
        account_Expenditure form = new account_Expenditure();
        form.setVisible(true);
        form.pack();
        form.setLocationRelativeTo(null);
        //close the current form (login form)
        this.dispose();
        
    }//GEN-LAST:event_jLabel_expenditureMouseClicked

    private void jButton_str_tempTblActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_str_tempTblActionPerformed
        
        String expn_type = jComboBox_ExpnDtr_type.getSelectedItem().toString();
        String expn_value = jTextField_Expenditure_Price.getText();
        
        if(expn_type.isEmpty() || expn_value.isEmpty()){
            ////////////////////////////////////////////////////////////////////
            JOptionPane.showMessageDialog(this, "Some Field is Empty!!", "Warning", JOptionPane.WARNING_MESSAGE);
            ////////////////////////////////////////////////////////////////////
        }else{
            ////////////////////////////////////////////////////////////////////
            ////////////////////////////////////////////////////////////////////
            ////////////////////////////////////////////////////////////////////
            
            DefaultTableModel model = (DefaultTableModel) jTable_tempStr_val.getModel();

            model.addRow(new Object[]{
                expn_type,
                expn_value,});
            
            jComboBox_ExpnDtr_type.setSelectedIndex(0);
            jComboBox_ExpnDtr_type.removeItem(expn_type);
            jTextField_Expenditure_Price.setText("");
            
            ////////////////////////////////////////////////////////////////////
            ////////////////////////////////////////////////////////////////////
            ////////////////////////////////////////////////////////////////////
        }
        
    }//GEN-LAST:event_jButton_str_tempTblActionPerformed

    private void jButton_remove_item_tempTblActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_remove_item_tempTblActionPerformed
        
        DefaultTableModel tblModel = (DefaultTableModel) jTable_tempStr_val.getModel();

        try {
            ////////////////////////////////////////////////////////////////////
            //////   set product name which will be removed from temp table and add to jCombo box
            int il = jTable_tempStr_val.getSelectedRow();
            TableModel model = jTable_tempStr_val.getModel();

            //////////////    remove product item from temp Table
            int SelectedRowIndex = jTable_tempStr_val.getSelectedRow();
            tblModel.removeRow(SelectedRowIndex);

            ////////////////////////////////////////////////////////////////////
        } catch (Exception ea) {
            JOptionPane.showMessageDialog(this, " Configuration Failure!! ", "Error", JOptionPane.ERROR_MESSAGE);
        }
        
    }//GEN-LAST:event_jButton_remove_item_tempTblActionPerformed

    private void jButton_StoreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_StoreActionPerformed
        
        ////////////////////////////////////////////////////////////////////////
        
        int response = JOptionPane.showConfirmDialog(this, " Complete This Action ?? ", "Confirmation", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if(response==JOptionPane.YES_OPTION){
            // JOptionPane.showMessageDialog(this, "Yes Operation!!", "Status", JOptionPane.INFORMATION_MESSAGE);
            
            
            DefaultTableModel model = (DefaultTableModel) jTable_tempStr_val.getModel();
            
            if(model.getRowCount()==0){
                ////////////////////////////////////////////////////////////////
                ////////////////////////////////////////////////////////////////
                JOptionPane.showMessageDialog(this, " Some field is Empty or incorrect!! ", "Warning", JOptionPane.WARNING_MESSAGE);
                ////////////////////////////////////////////////////////////////
                ////////////////////////////////////////////////////////////////
            }
            else{
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

                    String Expn_id = jLabel_entryID.getText();
                    String getYear = jLabel_Year.getText();
                    String getMonth = jLabel_month.getText();
                    
                    //////////////////////////////////////////
                    /////   found present Date-Time
                    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                    LocalDateTime now = LocalDateTime.now();///fetch localDateTime from here
                    String presentDateTime = dtf.format(now);/////convert localtime to String with format 
                    //////////////////////////////////////////
                    
                    PreparedStatement ps = con.prepareStatement("INSERT INTO OTHER_EXPENSES_TBL(EXPID, EXP_SESSION, EXP_MONTH, EXP_ADD_DTTM) VALUES(?, ?, ?, ?)");
                    ps.setString(1, Expn_id);
                    ps.setString(2, getYear);
                    ps.setString(3, getMonth);
                    ps.setString(4, presentDateTime);
                    
                    int io = ps.executeUpdate();
                    int im = 0;
                    if(io > 0){
                        ////////////////////////////////////////////////////////
                        ////////////////////////////////////////////////////////
                        
                        for(int k = 0; k < model.getRowCount(); k++){
                            ////////////////////////////////////////////////////////

                            String expenditure_type = (String)model.getValueAt(k, 0);
                            String expenditure_price = (String)model.getValueAt(k, 1);
                            
                            PreparedStatement ps1 = con.prepareStatement("INSERT INTO OTHER_EXPENSES_TBL_DETAILS(EXPID, EXPENDITURE_TYPE, EXPENDITURE_PRICE) VALUES(?, ?, ?)");
                            ps1.setString(1, Expn_id);
                            ps1.setString(2, expenditure_type);
                            ps1.setString(3, expenditure_price);
                            im = ps1.executeUpdate();

                            ////////////////////////////////////////////////////
                        }
                        
                        if(im > 0){
                            ////////////////////////////////////////////////
                            JOptionPane.showMessageDialog(this, "Expenditure Entered Successfully...", "Success", JOptionPane.INFORMATION_MESSAGE);
                            //////////////   make temp table empty
                            model.setRowCount(0);
                            //////////////   set new expn id
                            generatte_Expn_id();
                            
                            //////////////  remove and set value of jCombo box
                            jComboBox_ExpnDtr_type.removeAllItems();
                            Expn_tpe();
                            
                            ////////////////
                            ////////////////
                            DefaultTableModel tblmodel = (DefaultTableModel) jTable_contains_ExpenditureDtls.getModel();
                            tblmodel.setRowCount(0);
                            show_expenditure();         ///////////////  for refresh table
                            ////////////////
                            ////////////////
                            
                            ////////////////////////////////////////////////
                        }else{
                            ////////////////////////////////////////////////
                            JOptionPane.showMessageDialog(this, "Error Occurred!!", "Error", JOptionPane.ERROR_MESSAGE);
                            ////////////////////////////////////////////////
                        }
                        
                        ////////////////////////////////////////////////////////
                        ////////////////////////////////////////////////////////
                    }else{
                        ////////////////////////////////////////////////////////
                        ////////////////////////////////////////////////////////
                        
                        JOptionPane.showMessageDialog(this, "Error Occurred!! Please Try Again...", "Error", JOptionPane.ERROR_MESSAGE);
                        
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
            }
            
            
        }
        else if(response==JOptionPane.NO_OPTION){
            JOptionPane.showMessageDialog(this, " Operation Calcelled!!", "Status", JOptionPane.ERROR_MESSAGE);
        }
        else{
            System.out.println("No Operation Held");
        }
        
        ////////////////////////////////////////////////////////////////////////
    }//GEN-LAST:event_jButton_StoreActionPerformed

    private void jButton_SearchBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_SearchBtnActionPerformed
        
        String Sess = jComboBox_Session.getSelectedItem().toString();
        String Mnths = jComboBox_month.getSelectedItem().toString();
        
        if(Sess.isEmpty() || Sess.equals("Select Year From Below") || Mnths.isEmpty() || Mnths.equals("Select Month From Below")){
            ////////////////////////////////////////////////////////////////////
            JOptionPane.showMessageDialog(this, "Select Currect Value", "Warning", JOptionPane.WARNING_MESSAGE);
            ////////////////////////////////////////////////////////////////////
        }
        else{
            ////////////////////////////////////////////////////////////////////
            
            ///////////////    show search result in jtable
            show_expenditure_Search();
            
            ////////////////////////////////////////////////////////////////////
        }
        
        
    }//GEN-LAST:event_jButton_SearchBtnActionPerformed

    
    
    /////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////////
    ////  ~~~~~~~~~   Show data of jTable manufactur
    public ArrayList<expenditure> expenditureList_Search() {
        ArrayList<expenditure> expenditureList = new ArrayList<>();

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

            expenditure expenditure;
            
            String Sess = jComboBox_Session.getSelectedItem().toString();
            String Mnths = jComboBox_month.getSelectedItem().toString();

            PreparedStatement ps = con.prepareStatement("SELECT * FROM OTHER_EXPENSES_TBL WHERE EXP_SESSION = ? AND EXP_MONTH = ?");
            ps.setString(1, Sess);
            ps.setString(2, Mnths);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                ////////////////////////////////////////////////////////////////
                ////////////////////////////////////////////////////////////////
                
                String expn_id = rs.getString("EXPID");
                String expn_sess = rs.getString("EXP_SESSION");
                String expn_mnths = rs.getString("EXP_MONTH");
                String expn_Dttm = rs.getString("EXP_ADD_DTTM");
                
                String expn_dtls = "";
                
                PreparedStatement ps1 = con.prepareStatement("SELECT * FROM OTHER_EXPENSES_TBL_DETAILS WHERE EXPID = ?");
                ps1.setString(1, expn_id);
                ResultSet rs1 = ps1.executeQuery();
                while(rs1.next()){
                    String expn_type = rs1.getString("EXPENDITURE_TYPE");
                    String expn_Price = rs1.getString("EXPENDITURE_PRICE");
                    expn_dtls += expn_type +"->"+expn_Price+"/- , ";
                }
                
                String finalExpn_dtls = expn_dtls.substring(0, expn_dtls.length()-3);
                
                expenditure = new expenditure(expn_id, expn_sess, expn_mnths, expn_Dttm, finalExpn_dtls);
                expenditureList.add(expenditure);
                
                ////////////////////////////////////////////////////////////////
                ////////////////////////////////////////////////////////////////
            }
            
            /////////////////////////////////////////////
            /////////////////////////////////////////////
            /////////////////////////////////////////////
            con.close();
        } catch (Exception ea) {
            ea.printStackTrace();
        }
        ////////////////////////////////////////////////////////////////////////
        return expenditureList;
        ////////////////////////////////////////////////////////////////////////
    }
    
    
    
    public void show_expenditure_Search() {
        ArrayList<expenditure> expenditureLst = expenditureList_Search();
        DefaultTableModel model = (DefaultTableModel) jTable_contains_ExpenditureDtls.getModel();
        
        model.setRowCount(0);
        
        Object[] row = new Object[5];
        for (int i = 0; i < expenditureLst.size(); i++) {
            row[0] = expenditureLst.get(i).getexp_id();
            row[1] = expenditureLst.get(i).getexp_sess();
            row[2] = expenditureLst.get(i).getexp_mnts();
            row[3] = expenditureLst.get(i).getexp_dttm();
            row[4] = expenditureLst.get(i).getexp_dtls();
            model.addRow(row);
        }
    }
    
    
    ////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    private void jTable_contains_ExpenditureDtlsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable_contains_ExpenditureDtlsMouseClicked
        
        int il = jTable_contains_ExpenditureDtls.getSelectedRow();
        TableModel model = jTable_contains_ExpenditureDtls.getModel();
        String Expn_id = model.getValueAt(il, 0).toString();
        String Expn_sess = model.getValueAt(il, 1).toString();
        String Expn_Mnths = model.getValueAt(il, 2).toString();
        String Expn_dttm = model.getValueAt(il, 3).toString();
        
        String Expn_dtls = model.getValueAt(il, 4).toString();
        
        String modify_dtls = Expn_dtls.replace(" , ", "\n");        
        
        
        JOptionPane.showMessageDialog(this, "Expenditure ID : "+Expn_id+"\nSession : "+Expn_sess+"\nMonth : "+Expn_Mnths+"\nAdd at, "+Expn_dttm+"\n**Details : \n"+modify_dtls, "Information", JOptionPane.INFORMATION_MESSAGE);
        
        
        
    }//GEN-LAST:event_jTable_contains_ExpenditureDtlsMouseClicked

    private void jButton_resetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_resetActionPerformed
        
        
        ////////    set expenditure id
        generatte_Expn_id();
        
        //////////   set year
        Date d = new Date();
        int year = d.getYear();
        int currentYear = year + 1900;
        jLabel_Year.setText(String.valueOf(currentYear));
        
        /////////    set months
        LocalDate currentdate = LocalDate.now();
        Month currentMonth = currentdate.getMonth();
        jLabel_month.setText(String.valueOf(currentMonth));
        
        
        jComboBox_ExpnDtr_type.setSelectedIndex(0);
        jTextField_Expenditure_Price.setText("");
        
        
        /////////////////
        DefaultTableModel model = (DefaultTableModel) jTable_tempStr_val.getModel();
        model.setRowCount(0);
        /////////////////
        
        ////////////////
        ////////////////
        jComboBox_Session.setSelectedIndex(0);
        jComboBox_month.setSelectedIndex(0);
        
        DefaultTableModel tblmodel = (DefaultTableModel) jTable_contains_ExpenditureDtls.getModel();
        tblmodel.setRowCount(0);
        show_expenditure();         ///////////////  for refresh table
        ////////////////
        ////////////////
        
    }//GEN-LAST:event_jButton_resetActionPerformed

    private void jButton_removeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_removeActionPerformed
        
        int il = jTable_contains_ExpenditureDtls.getSelectedRow();
        TableModel model = jTable_contains_ExpenditureDtls.getModel();
        String Expn_id = model.getValueAt(il, 0).toString();
        
        
        if(Expn_id.isEmpty()){
            ////////////////////////////////////////////////////////////////////
            JOptionPane.showMessageDialog(this, "No Bill Selected!!!", "Notice", JOptionPane.WARNING_MESSAGE);
            ////////////////////////////////////////////////////////////////////
        }else{
            ////////////////////////////////////////////////////////////////////
            
            ////////////////////////////////////////////////////////////////////////
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

                PreparedStatement ps = con.prepareStatement("DELETE FROM OTHER_EXPENSES_TBL WHERE EXPID = ?");
                ps.setString(1, Expn_id);
                int i = ps.executeUpdate();
                if(i > 0){
                    
                    PreparedStatement ps1 = con.prepareStatement("DELETE FROM OTHER_EXPENSES_TBL_DETAILS WHERE EXPID = ?");
                    ps1.setString(1, Expn_id);
                    int j = ps1.executeUpdate();
                    if(j > 0){
                        
                        JOptionPane.showMessageDialog(this, "Expenditure Bill Removed Successfully...", "Notice", JOptionPane.INFORMATION_MESSAGE);
                        
                        //////////////   make temp table empty
                        DefaultTableModel tbmodel = (DefaultTableModel) jTable_tempStr_val.getModel();
                        tbmodel.setRowCount(0);
                        //////////////   set new expn id
                        generatte_Expn_id();

                        ////////////////
                        ////////////////
                        DefaultTableModel tblmodel = (DefaultTableModel) jTable_contains_ExpenditureDtls.getModel();
                        tblmodel.setRowCount(0);
                        show_expenditure();         ///////////////  for refresh table
                        ////////////////
                        ////////////////
                        
                    }else{
                        /////////////////////////////////////////////
                        /////////////////////////////////////////////
                        JOptionPane.showMessageDialog(this, "Error Occurred!! Please Try Again!!", "Error", JOptionPane.ERROR_MESSAGE);
                        /////////////////////////////////////////////
                        /////////////////////////////////////////////
                    }
                    
                }else{
                    /////////////////////////////////////////////
                    /////////////////////////////////////////////
                    JOptionPane.showMessageDialog(this, "Error Occurred!! Please Try Again!!", "Error", JOptionPane.ERROR_MESSAGE);
                    /////////////////////////////////////////////
                    /////////////////////////////////////////////
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
            ////////////////////////////////////////////////////////////////////////
            ////////////////////////////////////////////////////////////////////////
            
            ////////////////////////////////////////////////////////////////////
        }
        
        
    }//GEN-LAST:event_jButton_removeActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        
        account_AddExpn_Type form = new account_AddExpn_Type();
        form.setVisible(true);
        form.pack();
        form.setLocationRelativeTo(null);
        //close the current form (login form)
        this.dispose();
        
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton_categoryWise_ViewPgMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton_categoryWise_ViewPgMouseEntered
        
        jButton_categoryWise_ViewPg.setForeground(new Color(102,102,255));
        jButton_categoryWise_ViewPg.setBackground(Color.WHITE);
        
    }//GEN-LAST:event_jButton_categoryWise_ViewPgMouseEntered

    private void jButton_categoryWise_ViewPgMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton_categoryWise_ViewPgMouseExited
        
        jButton_categoryWise_ViewPg.setForeground(Color.WHITE);
        jButton_categoryWise_ViewPg.setBackground(new Color(102,102,255));
        
    }//GEN-LAST:event_jButton_categoryWise_ViewPgMouseExited

    private void jButton_categoryWise_ViewPgActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_categoryWise_ViewPgActionPerformed
        
        account_CategoryWise_View form = new account_CategoryWise_View();
        form.setVisible(true);
        form.pack();
        form.setLocationRelativeTo(null);
        //close the current form (login form)
        this.dispose();
        
    }//GEN-LAST:event_jButton_categoryWise_ViewPgActionPerformed

    private void jButton_Exp_DownloadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_Exp_DownloadActionPerformed
        
        
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
                    String Expenditure_Tbl = "Expenditure_Tbl-" + x + "ID-at-" + onlyDt+ "DT__" + modifyTm +"TM.xls";         //////// for excel file
                    /////////////////////////////////
                    /////////////////////////////////

                    ////////////////////////
                    ///////  url path + file name
                    ////////////////////////
                    String pathWithFileName = getPath + "\\" + Expenditure_Tbl;

                    //File PathWithFileName = new File(pathWithFileName);
                    File file = new File(pathWithFileName);




                    //////////////////////// export file into .xls format

                    try{
                        TableModel model = jTable_contains_ExpenditureDtls.getModel();
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
        
        
    }//GEN-LAST:event_jButton_Exp_DownloadActionPerformed

    private void jLabel_searchMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel_searchMouseClicked
        
        account_Search form = new account_Search();
        form.setVisible(true);
        form.pack();
        form.setLocationRelativeTo(null);
        //close the current form
        this.dispose();
        
    }//GEN-LAST:event_jLabel_searchMouseClicked

    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    public void generatte_Expn_id() {

        ///////   set Generated value of Product ID
        java.util.Random r = new java.util.Random();
        int x = r.nextInt(99999);
        String ExpnId = "Expenditure-" + x + "IDNO";
        jLabel_entryID.setText(ExpnId);

    }
    
    
    
    
    
    
    
    
    public void Expn_tpe(){
        ////////////////////////////////////////////////////////////////////////
        
        ////////////////////////////////////////////////////////////////////////
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

            PreparedStatement ps = con.prepareStatement("SELECT * FROM EXPENDITURE");
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                String expnd_tp = rs.getString("EXPENDITURE_TYPE");
                jComboBox_ExpnDtr_type.addItem(expnd_tp);
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
        ////////////////////////////////////////////////////////////////////////
        ////////////////////////////////////////////////////////////////////////
        
        ////////////////////////////////////////////////////////////////////////
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
            java.util.logging.Logger.getLogger(account_Expenditure.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(account_Expenditure.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(account_Expenditure.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(account_Expenditure.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new account_Expenditure().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton_Exp_Download;
    private javax.swing.JButton jButton_SearchBtn;
    private javax.swing.JButton jButton_Store;
    private javax.swing.JButton jButton_categoryWise_ViewPg;
    private javax.swing.JButton jButton_remove;
    private javax.swing.JButton jButton_remove_item_tempTbl;
    private javax.swing.JButton jButton_reset;
    private javax.swing.JButton jButton_str_tempTbl;
    private javax.swing.JComboBox<String> jComboBox_ExpnDtr_type;
    private javax.swing.JComboBox<String> jComboBox_Session;
    private javax.swing.JComboBox<String> jComboBox_month;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabel_Year;
    private javax.swing.JLabel jLabel_close;
    private javax.swing.JLabel jLabel_dashboard;
    private javax.swing.JLabel jLabel_entryID;
    private javax.swing.JLabel jLabel_expenditure;
    private javax.swing.JLabel jLabel_month;
    private javax.swing.JLabel jLabel_product;
    private javax.swing.JLabel jLabel_production;
    private javax.swing.JLabel jLabel_profile;
    private javax.swing.JLabel jLabel_search;
    private javax.swing.JLabel jLabel_sell;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable jTable_contains_ExpenditureDtls;
    private javax.swing.JTable jTable_tempStr_val;
    private javax.swing.JTextField jTextField_Expenditure_Price;
    // End of variables declaration//GEN-END:variables
}
