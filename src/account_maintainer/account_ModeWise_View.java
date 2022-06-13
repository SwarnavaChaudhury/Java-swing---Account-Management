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
public class account_ModeWise_View extends javax.swing.JFrame {

    /////  create border
    Border textFile_border = BorderFactory.createMatteBorder(0, 0, 2, 0, Color.red);
    
    /**
     * Creates new form account_DashBoard
     */
    public account_ModeWise_View() {
        initComponents();
        
        //  center form
        this.setLocationRelativeTo(null);
        
        //////  set icon
        jLabel_profile.setIcon(new ImageIcon(getClass().getResource("IMAGES/useron.png")));
        
        /////  set border
        jLabel_grandTtl.setBorder(textFile_border);
        
        
        
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
            
            double total_price = 00;
            
            String Sess = jComboBox_slct_Sess.getSelectedItem().toString();
            String Mnths = jComboBox_slct_nmths.getSelectedItem().toString();
            String mode = jComboBox_slct_mode.getSelectedItem().toString();
            
            if(Mnths.equalsIgnoreCase("ALL")){
                /////////////////////////////////////////////////////////////////////////////////////////
                /////////////////////////////////////////////////////////////////////////////////////////
                /////////////////////////////////////////////////////////////////////////////////////////
                
                PreparedStatement ps = con.prepareStatement("SELECT * FROM SELL_TBL WHERE SELL_SESSION = ? AND SELL_MODE = ?");
                ps.setString(1, Sess);
                ps.setString(2, mode);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {

                    String sell_id = rs.getString("SELL_ID");
                    String sell_Mode = rs.getString("SELL_MODE");
                    String sell_AlTotal_price = rs.getString("SELL_ALTTL_PRICE");
                    String sell_Dttm = rs.getString("SELL_DTTM");

                    total_price += Double.valueOf(sell_AlTotal_price);
                    
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
                
                /////////////////////////////////////////////////////////////////////////////////////////
                /////////////////////////////////////////////////////////////////////////////////////////
                /////////////////////////////////////////////////////////////////////////////////////////
            }else{
                /////////////////////////////////////////////////////////////////////////////////////////
                /////////////////////////////////////////////////////////////////////////////////////////
                /////////////////////////////////////////////////////////////////////////////////////////
                
                PreparedStatement ps = con.prepareStatement("SELECT * FROM SELL_TBL WHERE SELL_SESSION = ? AND SELL_MONTH = ? AND SELL_MODE = ?");
                ps.setString(1, Sess);
                ps.setString(2, Mnths);
                ps.setString(3, mode);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {

                    String sell_id = rs.getString("SELL_ID");
                    String sell_Mode = rs.getString("SELL_MODE");
                    String sell_AlTotal_price = rs.getString("SELL_ALTTL_PRICE");
                    String sell_Dttm = rs.getString("SELL_DTTM");

                    total_price += Double.valueOf(sell_AlTotal_price);
                    
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
                
                /////////////////////////////////////////////////////////////////////////////////////////
                /////////////////////////////////////////////////////////////////////////////////////////
                /////////////////////////////////////////////////////////////////////////////////////////
            }
            
            
            jLabel_grandTtl.setText(String.valueOf(total_price)+" /-");
            
            
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
        DefaultTableModel model = (DefaultTableModel) jTable_sellTbl_Mode.getModel();
        
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
        jLabel_search = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel_profile = new javax.swing.JLabel();
        jLabel_close = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jComboBox_slct_mode = new javax.swing.JComboBox<>();
        jButton_Srch_btnn = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jComboBox_slct_Sess = new javax.swing.JComboBox<>();
        jComboBox_slct_nmths = new javax.swing.JComboBox<>();
        jLabel6 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable_sellTbl_Mode = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel_grandTtl = new javax.swing.JLabel();
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

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Times New Roman", 3, 26)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 204, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Mode Wise View");

        jLabel2.setFont(new java.awt.Font("Traditional Arabic", 3, 24)); // NOI18N
        jLabel2.setText("Select Mode");

        jComboBox_slct_mode.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jComboBox_slct_mode.setForeground(new java.awt.Color(51, 102, 255));
        jComboBox_slct_mode.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select Mode From Below", "Whole Sell", "Retail" }));

        jButton_Srch_btnn.setBackground(new java.awt.Color(0, 255, 0));
        jButton_Srch_btnn.setFont(new java.awt.Font("Tahoma", 0, 22)); // NOI18N
        jButton_Srch_btnn.setForeground(new java.awt.Color(255, 255, 255));
        jButton_Srch_btnn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/account_maintainer/IMAGES/searchOn.png"))); // NOI18N
        jButton_Srch_btnn.setText("Get Data");
        jButton_Srch_btnn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton_Srch_btnn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_Srch_btnnActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Traditional Arabic", 3, 24)); // NOI18N
        jLabel5.setText("Select Year");

        jComboBox_slct_Sess.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jComboBox_slct_Sess.setForeground(new java.awt.Color(51, 102, 255));
        jComboBox_slct_Sess.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select Year From Below", "2020", "2021", "2022", "2023", "2024", "2025", "2026", "2027", "2028", "2029", "2030", "2031", "2032", "2033", "2034", "2035", "2036", "2037", "2038", "2039", "2040", "2041", "2042", "2043", "2044", "2045", "2046", "2047", "2048", "2049", "2050", "2051", "2052", "2053", "2054", "2055", "2056", "2057", "2058", "2059", "2060", "2061", "2062", "2063", "2064", "2065", "2066", "2067", "2068", "2069", "2070", "2071", "2072", "2073", "2074", "2075", "2076", "2077", "2078", "2079", "2080", "2081", "2082", "2083", "2084", "2085", "2086", "2087", "2088", "2089", "2090", "2091", "2092", "2093", "2094", "2095", "2096", "2097", "2098", "2099", "2100", "2101", "2102", "2103", "2104", "2105", "2106", "2107", "2108", "2109", "2110", "2111", "2112", "2113", "2114", "2115", "2116", "2117", "2118", "2119", "2120", "2121", "2122", "2123", "2124", "2125", "2126", "2127", "2128", "2129", "2130", "2131", "2132", "2133", "2134", "2135", "2136", "2137", "2138", "2139", "2140", "2141", "2142", "2143", "2144", "2145", "2146", "2147", "2148", "2149", "2150", "2151", "2152", "2153", "2154", "2155", "2156", "2157", "2158", "2159", "2160", "2161", "2162", "2163", "2164", "2165", "2166", "2167", "2168", "2169", "2170", "2171", "2172", "2173", "2174", "2175", "2176", "2177", "2178", "2179", "2180", "2181", "2182", "2183", "2184", "2185", "2186", "2187", "2188", "2189", "2190", "2191", "2192", "2193", "2194", "2195", "2196", "2197", "2198", "2199", "2200", "2201", "2202", "2203", "2204", "2205", "2206", "2207", "2208", "2209", "2210", "2211", "2212", "2213", "2214", "2215", "2216", "2217", "2218", "2219", "2220", "2221", "2222", "2223", "2224", "2225", "2226", "2227", "2228", "2229", "2230", "2231", "2232", "2233", "2234", "2235", "2236", "2237", "2238", "2239", "2240", "2241", "2242", "2243", "2244", "2245", "2246", "2247", "2248", "2249", "2250", "2251", "2252", "2253", "2254", "2255", "2256", "2257", "2258", "2259", "2260", "2261", "2262", "2263", "2264", "2265", "2266", "2267", "2268", "2269", "2270", "2271", "2272", "2273", "2274", "2275", "2276", "2277", "2278", "2279", "2280", "2281", "2282", "2283", "2284", "2285", "2286", "2287", "2288", "2289", "2290", "2291", "2292", "2293", "2294", "2295", "2296", "2297", "2298", "2299", "2300", "2301", "2302", "2303", "2304", "2305", "2306", "2307", "2308", "2309", "2310", "2311", "2312", "2313", "2314", "2315", "2316", "2317", "2318", "2319", "2320", "2321", "2322", "2323", "2324", "2325", "2326", "2327", "2328", "2329", "2330", "2331", "2332", "2333", "2334", "2335", "2336", "2337", "2338", "2339", "2340", "2341", "2342", "2343", "2344", "2345", "2346", "2347", "2348", "2349", "2350", "2351", "2352", "2353", "2354", "2355", "2356", "2357", "2358", "2359", "2360", "2361", "2362", "2363", "2364", "2365", "2366", "2367", "2368", "2369", "2370", "2371", "2372", "2373", "2374", "2375", "2376", "2377", "2378", "2379", "2380", "2381", "2382", "2383", "2384", "2385", "2386", "2387", "2388", "2389", "2390", "2391", "2392", "2393", "2394", "2395", "2396", "2397", "2398", "2399", "2400", "2401", "2402", "2403", "2404", "2405", "2406", "2407", "2408", "2409", "2410", "2411", "2412", "2413", "2414", "2415", "2416", "2417", "2418", "2419", "2420", "2421", "2422", "2423", "2424", "2425", "2426", "2427", "2428", "2429", "2430", "2431", "2432", "2433", "2434", "2435", "2436", "2437", "2438", "2439", "2440", "2441", "2442", "2443", "2444", "2445", "2446", "2447", "2448", "2449", "2450", "2451", "2452", "2453", "2454", "2455", "2456", "2457", "2458", "2459", "2460", "2461", "2462", "2463", "2464", "2465", "2466", "2467", "2468", "2469", "2470", "2471", "2472", "2473", "2474", "2475", "2476", "2477", "2478", "2479", "2480", "2481", "2482", "2483", "2484", "2485", "2486", "2487", "2488", "2489", "2490", "2491", "2492", "2493", "2494", "2495", "2496", "2497", "2498", "2499", "2500", "2501", "2502", "2503", "2504", "2505", "2506", "2507", "2508", "2509", "2510", "2511", "2512", "2513", "2514", "2515", "2516", "2517", "2518", "2519", "2520", "2521", "2522", "2523", "2524", "2525", "2526", "2527", "2528", "2529", "2530", "2531", "2532", "2533", "2534", "2535", "2536", "2537", "2538", "2539", "2540", "2541", "2542", "2543", "2544", "2545", "2546", "2547", "2548", "2549", "2550", "2551", "2552", "2553", "2554", "2555", "2556", "2557", "2558", "2559", "2560", "2561", "2562", "2563", "2564", "2565", "2566", "2567", "2568", "2569", "2570", "2571", "2572", "2573", "2574", "2575", "2576", "2577", "2578", "2579", "2580", "2581", "2582", "2583", "2584", "2585", "2586", "2587", "2588", "2589", "2590", "2591", "2592", "2593", "2594", "2595", "2596", "2597", "2598", "2599", "2600", "2601", "2602", "2603", "2604", "2605", "2606", "2607", "2608", "2609", "2610", "2611", "2612", "2613", "2614", "2615", "2616", "2617", "2618", "2619", "2620", "2621", "2622", "2623", "2624", "2625", "2626", "2627", "2628", "2629", "2630", "2631", "2632", "2633", "2634", "2635", "2636", "2637", "2638", "2639", "2640", "2641", "2642", "2643", "2644", "2645", "2646", "2647", "2648", "2649", "2650", "2651", "2652", "2653", "2654", "2655", "2656", "2657", "2658", "2659", "2660", "2661", "2662", "2663", "2664", "2665", "2666", "2667", "2668", "2669", "2670", "2671", "2672", "2673", "2674", "2675", "2676", "2677", "2678", "2679", "2680", "2681", "2682", "2683", "2684", "2685", "2686", "2687", "2688", "2689", "2690", "2691", "2692", "2693", "2694", "2695", "2696", "2697", "2698", "2699", "2700", "2701", "2702", "2703", "2704", "2705", "2706", "2707", "2708", "2709", "2710", "2711", "2712", "2713", "2714", "2715", "2716", "2717", "2718", "2719", "2720", "2721", "2722", "2723", "2724", "2725", "2726", "2727", "2728", "2729", "2730", "2731", "2732", "2733", "2734", "2735", "2736", "2737", "2738", "2739", "2740", "2741", "2742", "2743", "2744", "2745", "2746", "2747", "2748", "2749", "2750", "2751", "2752", "2753", "2754", "2755", "2756", "2757", "2758", "2759", "2760", "2761", "2762", "2763", "2764", "2765", "2766", "2767", "2768", "2769", "2770", "2771", "2772", "2773", "2774", "2775", "2776", "2777", "2778", "2779", "2780", "2781", "2782", "2783", "2784", "2785", "2786", "2787", "2788", "2789", "2790", "2791", "2792", "2793", "2794", "2795", "2796", "2797", "2798", "2799", "2800", "2801", "2802", "2803", "2804", "2805", "2806", "2807", "2808", "2809", "2810", "2811", "2812", "2813", "2814", "2815", "2816", "2817", "2818", "2819", "2820", "2821", "2822", "2823", "2824", "2825", "2826", "2827", "2828", "2829", "2830", "2831", "2832", "2833", "2834", "2835", "2836", "2837", "2838", "2839", "2840", "2841", "2842", "2843", "2844", "2845", "2846", "2847", "2848", "2849", "2850", "2851", "2852", "2853", "2854", "2855", "2856", "2857", "2858", "2859", "2860", "2861", "2862", "2863", "2864", "2865", "2866", "2867", "2868", "2869", "2870", "2871", "2872", "2873", "2874", "2875", "2876", "2877", "2878", "2879", "2880", "2881", "2882", "2883", "2884", "2885", "2886", "2887", "2888", "2889", "2890", "2891", "2892", "2893", "2894", "2895", "2896", "2897", "2898", "2899", "2900", "2901", "2902", "2903", "2904", "2905", "2906", "2907", "2908", "2909", "2910", "2911", "2912", "2913", "2914", "2915", "2916", "2917", "2918", "2919", "2920", "2921", "2922", "2923", "2924", "2925", "2926", "2927", "2928", "2929", "2930", "2931", "2932", "2933", "2934", "2935", "2936", "2937", "2938", "2939", "2940", "2941", "2942", "2943", "2944", "2945", "2946", "2947", "2948", "2949", "2950", "2951", "2952", "2953", "2954", "2955", "2956", "2957", "2958", "2959", "2960", "2961", "2962", "2963", "2964", "2965", "2966", "2967", "2968", "2969", "2970", "2971", "2972", "2973", "2974", "2975", "2976", "2977", "2978", "2979", "2980", "2981", "2982", "2983", "2984", "2985", "2986", "2987", "2988", "2989", "2990", "2991", "2992", "2993", "2994", "2995", "2996", "2997", "2998", "2999", "3000", "3001", "3002", "3003", "3004", "3005", "3006", "3007", "3008", "3009", "3010", "3011", "3012", "3013", "3014", "3015", "3016", "3017", "3018", "3019", "3020" }));

        jComboBox_slct_nmths.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jComboBox_slct_nmths.setForeground(new java.awt.Color(51, 102, 255));
        jComboBox_slct_nmths.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select Month From Below", "ALL", "JANUARY", "FEBRUARY", "MARCH", "APRIL", "MAY", "JUNE", "JULY", "AUGUST", "SEPTEMBER", "OCTOBER", "NOVEMBER", "DECEMBER" }));

        jLabel6.setFont(new java.awt.Font("Traditional Arabic", 3, 24)); // NOI18N
        jLabel6.setText("Select Month");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton_Srch_btnn)
                            .addComponent(jComboBox_slct_mode, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jComboBox_slct_Sess, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jComboBox_slct_nmths, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(26, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(71, 71, 71)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox_slct_Sess, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox_slct_nmths, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox_slct_mode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jButton_Srch_btnn)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTable_sellTbl_Mode.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Sell Bill ID", "Mode", "Description", "Total Price", "Date Time"
            }
        ));
        jTable_sellTbl_Mode.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable_sellTbl_ModeMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTable_sellTbl_Mode);

        jLabel3.setBackground(new java.awt.Color(255, 255, 255));
        jLabel3.setFont(new java.awt.Font("Times New Roman", 3, 26)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Mode Wise View");

        jLabel4.setBackground(new java.awt.Color(255, 255, 255));
        jLabel4.setFont(new java.awt.Font("Traditional Arabic", 3, 24)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 102, 255));
        jLabel4.setText("GRAND TOTAL");

        jLabel_grandTtl.setBackground(new java.awt.Color(255, 255, 255));
        jLabel_grandTtl.setFont(new java.awt.Font("Traditional Arabic", 3, 24)); // NOI18N
        jLabel_grandTtl.setForeground(new java.awt.Color(0, 102, 255));
        jLabel_grandTtl.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel_grandTtl.setText(".....");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 494, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel_profile, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel_close, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 615, Short.MAX_VALUE)))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 221, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(56, 56, 56)
                        .addComponent(jLabel_grandTtl, javax.swing.GroupLayout.PREFERRED_SIZE, 248, javax.swing.GroupLayout.PREFERRED_SIZE)))
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
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 360, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jLabel_grandTtl))
                .addContainerGap(20, Short.MAX_VALUE))
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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
                .addGap(13, 13, 13))
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
        
    }//GEN-LAST:event_jLabel_dashboardMouseExited

    private void jLabel_productMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel_productMouseEntered
        
        jLabel_product.setBackground(new Color(255, 255, 255));
        jLabel_product.setForeground(new Color(0, 204, 204));
        jLabel_product.setBorder(textFile_border);
        
    }//GEN-LAST:event_jLabel_productMouseEntered

    private void jLabel_productMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel_productMouseExited
        
        jLabel_product.setBackground(new Color(0, 204, 204));
        jLabel_product.setForeground(new Color(255, 255, 255));
        
    }//GEN-LAST:event_jLabel_productMouseExited

    private void jLabel_productionMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel_productionMouseEntered
        
        jLabel_production.setBackground(new Color(255, 255, 255));
        jLabel_production.setForeground(new Color(0, 204, 204));
        jLabel_production.setBorder(textFile_border);
        
    }//GEN-LAST:event_jLabel_productionMouseEntered

    private void jLabel_productionMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel_productionMouseExited
        
        jLabel_production.setBackground(new Color(0, 204, 204));
        jLabel_production.setForeground(new Color(255, 255, 255));
        
    }//GEN-LAST:event_jLabel_productionMouseExited

    private void jLabel_sellMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel_sellMouseEntered
        
        jLabel_sell.setBackground(new Color(255, 255, 255));
        jLabel_sell.setForeground(new Color(0, 204, 204));
        jLabel_sell.setBorder(textFile_border);
        
    }//GEN-LAST:event_jLabel_sellMouseEntered

    private void jLabel_sellMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel_sellMouseExited
        
        jLabel_sell.setBackground(new Color(0, 204, 204));
        jLabel_sell.setForeground(new Color(255, 255, 255));
        
    }//GEN-LAST:event_jLabel_sellMouseExited

    private void jLabel_searchMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel_searchMouseEntered
        
        jLabel_search.setBackground(new Color(255, 255, 255));
        jLabel_search.setForeground(new Color(0, 204, 204));
        jLabel_search.setBorder(textFile_border);
        
    }//GEN-LAST:event_jLabel_searchMouseEntered

    private void jLabel_searchMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel_searchMouseExited
        
        jLabel_search.setBackground(new Color(0, 204, 204));
        jLabel_search.setForeground(new Color(255, 255, 255));
        
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

        jLabel_search1.setBackground(new Color(0, 204, 204));
        jLabel_search1.setForeground(new Color(255, 255, 255));
        
    }//GEN-LAST:event_jLabel_search1MouseExited

    private void jLabel_searchMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel_searchMouseClicked
        
        account_Expenditure form = new account_Expenditure();
        form.setVisible(true);
        form.pack();
        form.setLocationRelativeTo(null);
        //close the current form (login form)
        this.dispose();
        
    }//GEN-LAST:event_jLabel_searchMouseClicked

    private void jLabel_search1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel_search1MouseClicked
        
        account_Search form = new account_Search();
        form.setVisible(true);
        form.pack();
        form.setLocationRelativeTo(null);
        //close the current form (login form)
        this.dispose();
        
    }//GEN-LAST:event_jLabel_search1MouseClicked

    private void jTable_sellTbl_ModeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable_sellTbl_ModeMouseClicked

        int il = jTable_sellTbl_Mode.getSelectedRow();
        TableModel model = jTable_sellTbl_Mode.getModel();

        String Mode = model.getValueAt(il, 0).toString();
        String Product_Nm = model.getValueAt(il, 1).toString();
        String Unit = model.getValueAt(il, 2).toString();
        String Quantity = model.getValueAt(il, 3).toString();
        String Selling_Price = model.getValueAt(il, 4).toString();
        String ttl_Selling_Price = model.getValueAt(il, 5).toString();
        String Dttm = model.getValueAt(il, 6).toString();
        
        JOptionPane.showMessageDialog(this, "Mode Use : "+Mode+"\nProduct Name :"+Product_Nm+"\nTotal Quantity : "+Quantity+" "+Unit+"\nSelling Price: "+Selling_Price+"/-"+"\nTotal Selling Price : "+ttl_Selling_Price+"/-"+"\nAdd At : "+Dttm, "Details", JOptionPane.INFORMATION_MESSAGE);

    }//GEN-LAST:event_jTable_sellTbl_ModeMouseClicked

    private void jButton_Srch_btnnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_Srch_btnnActionPerformed
        
        String Sess = jComboBox_slct_Sess.getSelectedItem().toString();
        String Mnths = jComboBox_slct_nmths.getSelectedItem().toString();
        String mode = jComboBox_slct_mode.getSelectedItem().toString();
        
        if(Sess.isEmpty() || Sess.equals("Select Year From Below") || Mnths.isEmpty() || Mnths.equals("Select Month From Below") || mode.isEmpty() || mode.equals("Select Mode From Below")){
            ////////////////////////////////////////////////////////////////////
            JOptionPane.showMessageDialog(this, "Some Field is Missing!!", "Warning", JOptionPane.WARNING_MESSAGE);
            ////////////////////////////////////////////////////////////////////
        }
        else{
            ////////////////////////////////////////////////////////////////////
            
            ///////   set data to table
            show_sell();
            
            ////////////////////////////////////////////////////////////////////
        }
        
    }//GEN-LAST:event_jButton_Srch_btnnActionPerformed

    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
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
            java.util.logging.Logger.getLogger(account_ModeWise_View.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(account_ModeWise_View.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(account_ModeWise_View.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(account_ModeWise_View.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new account_ModeWise_View().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton_Srch_btnn;
    private javax.swing.JComboBox<String> jComboBox_slct_Sess;
    private javax.swing.JComboBox<String> jComboBox_slct_mode;
    private javax.swing.JComboBox<String> jComboBox_slct_nmths;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel_close;
    private javax.swing.JLabel jLabel_dashboard;
    private javax.swing.JLabel jLabel_grandTtl;
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
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable_sellTbl_Mode;
    // End of variables declaration//GEN-END:variables
}
