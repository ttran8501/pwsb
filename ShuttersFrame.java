
//import com.sun.xml.internal.ws.spi.db.BindingContextFactory;

import com.jniwrapper.win32.jexcel.ExcelException;
import com.jniwrapper.win32.jexcel.ui.JWorkbook;
import com.sun.javafx.scene.control.skin.TableHeaderRow;
//import com.sun.xml.internal.ws.api.addressing.WSEndpointReference;
//import com.sun.xml.internal.ws.spi.db.BindingContextFactory;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FocusTraversalPolicy;
import java.awt.Font;
import static java.awt.Font.BOLD;
import static java.awt.Font.PLAIN;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.TextArea;
import java.awt.font.TextAttribute;
import static java.awt.font.TextAttribute.FONT;
import java.awt.print.PrinterException;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.Format;
import java.text.MessageFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.geometry.Pos;
import javax.print.attribute.Attribute;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.MediaSize;
import javax.print.attribute.standard.MediaSizeName;
import javax.print.attribute.standard.OrientationRequested;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import static javax.swing.JOptionPane.CANCEL_OPTION;
import static javax.swing.JOptionPane.ERROR_MESSAGE;
import static javax.swing.JOptionPane.OK_CANCEL_OPTION;
import static javax.swing.JOptionPane.OK_OPTION;
import static javax.swing.JOptionPane.QUESTION_MESSAGE;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListModel;
import javax.swing.Renderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.UIManager;
import javax.swing.event.ListDataListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.text.MaskFormatter;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import sun.net.www.MessageHeader;
//import sun.swing.table.DefaultTableCellHeaderRenderer;
//import net.proteanit.sql.DbUtils;


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author ttran
 */
// java.awt.Frame
public class ShuttersFrame extends java.awt.Frame {
    
    //SQL settings
    static final String dbms="mysql";
    static final String serverName="localhost";
//    static final String serverName="MYPC3-PC";
   
    static final String portNumber="3306";
    static final String dbName="pwsb";
    static final String tableName="orderdetails";
    static final String customerTable="customer";
    static final String orderTable="orderdetails";
    
    //customer table columns
    static final String col_cust_id = "id";
    static final String col_cust_name = "name";
    
    //order table columns

    /* 2 */ static final String col_order_cust_id = "cust_id";
    /* 3 */ static final String col_order_mount = "mount";
    /* 4 */ static final String col_order_width = "width";
    /* 5 */ static final String col_order_length = "length";
    /* 6 */ static final String col_order_panel = "panel";
    /* 7 */ static final String col_order_rail = "rail";
    /* 8 */ static final String col_order_railcount = "railcount";
    /* 9 */ static final String col_order_louverlen = "louverlen";
    /*10 */ static final String col_order_louversize = "louversize";
    /*11 */ static final String col_order_louvercount = "louvercount";
    /*12 */ static final String col_order_stilelen = "stilelen";
    /*13 */ static final String col_order_bited = "bited";
    /*14 */ static final String col_order_rabited = "rabited";
    /*15 */ static final String col_order_hinge = "hinge";
    /*16 */ static final String col_order_framecount = "framecount";
    /*17 */ static final String col_order_instruction = "instruction";
    /* 1 */ static final String col_order_item_id = "itemid";    
    
    static Connection conn = null;
    static int tableColCount = 0;
    static final int stileWidth=2;
    
    // Table fields
    static final String ORDERID="orderid";
    static final String ITEMNBR="itemnbr";
    static final String WIDTH="width";
    static final String LENGTH="length";
    private String username;
    private String password;
    static DefaultTableModel tableModel;
    static final int ADD = 1;
    static final int UPDATE = 2;
    static final int INSERT = 3;
    
    // Measurement constants
    // static final double dStile = 4.0;
    static final double dStilePlus = 4.125;
    static final double dRails4 = 9.0;
    static final double dRails5 = 11;
    static final int iLouverSize2 = 2;
    static final int iLouverSize3 = 3;
    static final int iLouverSize4 = 4;
    
    static HashMap fractionMap = new HashMap();
    static HashMap fractionMap32 = new HashMap();
    
    static HashMap outFrameSizeMap = new HashMap();
    static HashMap inFrameSizeMap = new HashMap();
    static HashMap stringToDoubleMap = new HashMap();
    static HashMap decimalToFraction = new HashMap();
    
    private int custID;
    private String custName;
    private Frame parentFrame;
    private int windowStyle;
    private boolean bSaveRequired;
    
    public static final int WINDOW_STYLE_STANDARD = 1;
    public static final int WINDOW_STYLE_FRAME2FRAME = 2;

    
    /**
     * Creates new form ShuttersFrame
     */
    public ShuttersFrame() throws Exception {
        initComponents();
        startup();
    }

    public void setCustID(int custID){
        this.custID = custID;
        jCustIDFld.setText(String.valueOf(custID));
    }
    
    public int getCustID(){
        return custID;
    }
    
    public void setCustName(String name){
        this.custName = name;
        jNameFld.setText(name);
    }
    
    public Frame getParentFrame(){
        return parentFrame;
    }
    
    public void setParentFrame(Frame parentFrame){
        this.parentFrame = parentFrame;
    }
    
    public int getWindowStyle(){
        return windowStyle;
    }
    
    public void setWindowStyle(int style){
        windowStyle = style;
    }
    public String getCustName(){
        return custName;
    }
    
    public void initDataTable(){
        DefaultTableModel model = (DefaultTableModel) jDataTable.getModel();
        JTableHeader headerRow = jDataTable.getTableHeader();
        TableColumn col = new TableColumn();
        col.setHeaderValue("Finish Frame");
        model.addColumn(col);
    }
    
    private void initMap(){
        String sKey []   = {".0", ".125"   ,".25" ,".375"     ,".5"    ,".625"     ,".75"  ,".875",
                            ".1"    ,".2"   ,".3"       ,".4"    ,".5"       ,".6"   ,".7"};
        //String sValue [] = {"",   "1/8"   ,"1/4"  ,"3/8"      ,"1/2"   ,"5/8"      ,"3/4"  ,"7/8",
        //                    "1/8"   ,"1/4"  ,"3/8"      ,"1/2"   ,"5/8"      ,"3/4"  ,"7/8",};
//        for (int i=0; i < sKey.length; i++){
//            fractionMap.put(sKey[i], sValue[i]);
//        }
        // 0 1/16 1/8 3/16 1/4 5/16 3/8 7/16 1/2 9/16 5/8 11/16 3/4 13/16 7/8 15/16
        Double num[] = {.0000, .0625, .1250, .1875, .2500, .3125, .3750, .4375,
                        .5000, .56250, .6250, .6875, .7500, .8125, .875, .9375};
        String sValue[] = {"","1/16","1/8","3/16","1/4","5/16","3/8","7/16",
                        "1/2","9/16","5/8","11/16","3/4","13/16","7/8","15/16"};
        for (int i = 0; i < num.length; i++){
            fractionMap.put(num[i],sValue[i]);
        }
        
        Double num32[] = {.0000, 0.03125, .0625, 0.09375, .1250, 0.15625, .1875, 0.21875, .2500, 0.28125, .3125, 0.34375, .3750, 0.40625, .4375, 0.46875, 
                        0.5000, 0.53125, 0.56250, 0.59375, 0.6250, 0.65625, 0.6875, 0.71875, .7500, 0.78125, .8125, 0.84375, .875, 0.90625, .9375, 0.96875};
        String sValue32[] = {"","1/32","1/16","3/32","1/8","5/32","3/16","7/32","1/4","9/32","5/16","11/32","3/8","13/32","7/16","15/32",
                        "1/2","17/32","9/16","19/32","5/8","21/32","11/16","23/32","3/4","25/32","13/16","27/32","7/8","29/32","15/16","31/32"};
                
        for (int i = 0; i < num32.length; i++){
            fractionMap32.put(num32[i],sValue32[i]);
        }
         
       Double dec[] = {0.0, .1000, .2000, .3000, .4000, .5000, .600, .7000};
        String decKey[] = {"", "1/8","1/4","3/8","1/2","5/8","3/4","7/8"};
        for (int i = 0; i < dec.length; i++){
            decimalToFraction.put(dec[i],decKey[i]);
        }

        String frameOMKey [] = {"1 1/4\"","1\" Lframe","2\"","2 1/2\""};
        Double dFrameOMValues [] = {1.2, 1.0, 2.0, 2.4};
        for (int i = 0; i < frameOMKey.length; i++){
            outFrameSizeMap.put(frameOMKey[i],dFrameOMValues[i]);
        }
        
        String frameIMKey [] = {"3/4 Lframe","1\" Lframe","1\"Zframe","2\" Zframe","2 1/2\" Zframe","3\" Zframe"};
        Double dFrameIMValues [] = {0.6, 1.0, 1.0, 2.0, 2.4, 3.0};
        for (int i=0; i < frameIMKey.length; i++){
            inFrameSizeMap.put(frameIMKey[i],dFrameIMValues[i]);
        }
        
        String stringKey [] = {"","1/16","1/8","3/16","1/4","5/16","3/8","7/16",
                        "1/2","9/16","5/8","11/16","3/4","13/16","7/8","15/16"};
        Double doubleValue [] = {.0000, .0625, .1250, .1875, .2500, .3125, .3750, .4375,
                        .5000, .56250, .6250, .6875, .7500, .8125, .875, .9375};
        
        for (int i= 0; i < stringKey.length; i++){
            stringToDoubleMap.put(stringKey[i], doubleValue[i]);
        }
        
            
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jDialog1 = new javax.swing.JDialog();
        button1 = new java.awt.Button();
        buttonGrpMount = new javax.swing.ButtonGroup();
        buttonGroup2 = new javax.swing.ButtonGroup();
        buttonGrpLouverSize = new javax.swing.ButtonGroup();
        buttonGrpFrameSize = new javax.swing.ButtonGroup();
        buttonGrpPanel = new javax.swing.ButtonGroup();
        buttonGrpWindowType = new javax.swing.ButtonGroup();
        buttonGroup7 = new javax.swing.ButtonGroup();
        buttonGrpRailLength = new javax.swing.ButtonGroup();
        buttonGrpDivSplit = new javax.swing.ButtonGroup();
        buttonGrpMeasType = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jRadioShop = new javax.swing.JRadioButton();
        jRadioStandard = new javax.swing.JRadioButton();
        jPanel2 = new javax.swing.JPanel();
        jRadioDeadPan = new javax.swing.JRadioButton();
        jRadioOutside = new javax.swing.JRadioButton();
        jRadioInside = new javax.swing.JRadioButton();
        jPanel4 = new javax.swing.JPanel();
        jRadioLouverChoice2 = new javax.swing.JRadioButton();
        jRadioLouverChoice3 = new javax.swing.JRadioButton();
        jRadioLouverChoice4 = new javax.swing.JRadioButton();
        jPanelPanel = new javax.swing.JPanel();
        jRadioPanelOne = new javax.swing.JRadioButton();
        jRadioPanelTwo = new javax.swing.JRadioButton();
        jRadioPanelFour = new javax.swing.JRadioButton();
        jPanel8 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLouverLenFld = new javax.swing.JTextField();
        jRailWidthFld = new javax.swing.JTextField();
        jHingeFld = new javax.swing.JTextField();
        jLouverCountLbl = new javax.swing.JLabel();
        jLouverCountFld = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jStileLenFld = new javax.swing.JTextField();
        jBitedFld = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jRabitedFld = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        jDataTable = new javax.swing.JTable();
        jPanel6 = new javax.swing.JPanel();
        jNewBtn = new javax.swing.JButton();
        jAddBtn = new javax.swing.JButton();
        jDeleteBtn = new javax.swing.JButton();
        jIMoveUpBtn = new javax.swing.JButton();
        jMoveDownBtn = new javax.swing.JButton();
        jUpdatetBtn = new javax.swing.JButton();
        jSaveBtn = new javax.swing.JButton();
        jBtnExit = new javax.swing.JButton();
        jPrintInvoiceBtn = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        jSqrFtFld = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        jCustIDFld = new javax.swing.JTextField();
        jPanel9 = new javax.swing.JPanel();
        jRadioFrameSillBase = new javax.swing.JRadioButton();
        jRadioFrametoFrame = new javax.swing.JRadioButton();
        jRadioFrameNormal = new javax.swing.JRadioButton();
        jPanel10 = new javax.swing.JPanel();
        jRadioThreeSides = new javax.swing.JRadioButton();
        jRadioFourSides = new javax.swing.JRadioButton();
        jPanel11 = new javax.swing.JPanel();
        jRadioRailLen4 = new javax.swing.JRadioButton();
        jRadioRailLen5 = new javax.swing.JRadioButton();
        jLabel6 = new javax.swing.JLabel();
        jNameFld = new javax.swing.JTextField();
        jPanel12 = new javax.swing.JPanel();
        jRadioSplit = new javax.swing.JRadioButton();
        jRadioDivider = new javax.swing.JRadioButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jDivSplitList = new javax.swing.JList();
        jWidthFld = new javax.swing.JTextField();
        jWidthLabel = new javax.swing.JLabel();
        jLengthLabel = new javax.swing.JLabel();
        jLengthFld = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jInstructionFld = new javax.swing.JTextField();
        jItemNumber = new javax.swing.JTextField();
        jFrameSizePanel = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        jFrameSizeLst = new javax.swing.JList();
        jFinishedWidthLabel = new javax.swing.JLabel();
        jFinishedWidthFld = new javax.swing.JTextField();
        jFinishedLengthLabel = new javax.swing.JLabel();
        jFinishedLengthFld = new javax.swing.JTextField();
        jCalcBtn = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jColorComboBox = new javax.swing.JComboBox();
        jMeasTypePan = new javax.swing.JPanel();
        jRadioWindowStd = new javax.swing.JRadioButton();
        jRadioWindowFtoF = new javax.swing.JRadioButton();
        jPanel7 = new javax.swing.JPanel();
        jSillLabel = new javax.swing.JLabel();
        jSillWidthFld = new javax.swing.JTextField();

        button1.setLabel("button1");

        setTitle("Pacific Wholesale Shutters and Blinds");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                exitForm(evt);
            }
        });

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel1.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                jPanel1ComponentShown(evt);
            }
        });

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Logo.png"))); // NOI18N
        jLabel5.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        jLabel5.setOpaque(true);

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Choice", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 13))); // NOI18N

        buttonGroup2.add(jRadioShop);
        jRadioShop.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jRadioShop.setSelected(true);
        jRadioShop.setText("Shop");
        jRadioShop.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioShopActionPerformed(evt);
            }
        });

        buttonGroup2.add(jRadioStandard);
        jRadioStandard.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jRadioStandard.setText("Standard");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jRadioStandard)
                    .addComponent(jRadioShop))
                .addContainerGap(28, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jRadioShop)
                .addGap(11, 11, 11)
                .addComponent(jRadioStandard)
                .addGap(0, 16, Short.MAX_VALUE))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Mount", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 13))); // NOI18N

        buttonGrpMount.add(jRadioDeadPan);
        jRadioDeadPan.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jRadioDeadPan.setText("Dead Panels");

        buttonGrpMount.add(jRadioOutside);
        jRadioOutside.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jRadioOutside.setSelected(true);
        jRadioOutside.setText("Outside");
        jRadioOutside.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioOutsideActionPerformed(evt);
            }
        });

        buttonGrpMount.add(jRadioInside);
        jRadioInside.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jRadioInside.setText("Inside");
        jRadioInside.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioInsideActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jRadioDeadPan)
                    .addComponent(jRadioOutside)
                    .addComponent(jRadioInside))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jRadioInside)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jRadioOutside)
                .addGap(18, 18, 18)
                .addComponent(jRadioDeadPan)
                .addContainerGap())
        );

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Louver Size", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 13))); // NOI18N

        buttonGrpLouverSize.add(jRadioLouverChoice2);
        jRadioLouverChoice2.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jRadioLouverChoice2.setText("2 1/2");

        buttonGrpLouverSize.add(jRadioLouverChoice3);
        jRadioLouverChoice3.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jRadioLouverChoice3.setSelected(true);
        jRadioLouverChoice3.setText("3 1/2");

        buttonGrpLouverSize.add(jRadioLouverChoice4);
        jRadioLouverChoice4.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jRadioLouverChoice4.setText("4 1/2");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jRadioLouverChoice2)
                    .addComponent(jRadioLouverChoice3)
                    .addComponent(jRadioLouverChoice4))
                .addContainerGap(41, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jRadioLouverChoice2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jRadioLouverChoice3)
                .addGap(18, 18, 18)
                .addComponent(jRadioLouverChoice4)
                .addContainerGap())
        );

        jPanelPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Panel", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 13))); // NOI18N

        buttonGrpPanel.add(jRadioPanelOne);
        jRadioPanelOne.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jRadioPanelOne.setText("One Panel");

        buttonGrpPanel.add(jRadioPanelTwo);
        jRadioPanelTwo.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jRadioPanelTwo.setText("Two Panel");
        jRadioPanelTwo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioPanelTwoActionPerformed(evt);
            }
        });

        buttonGrpPanel.add(jRadioPanelFour);
        jRadioPanelFour.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jRadioPanelFour.setText("Four Panel");

        javax.swing.GroupLayout jPanelPanelLayout = new javax.swing.GroupLayout(jPanelPanel);
        jPanelPanel.setLayout(jPanelPanelLayout);
        jPanelPanelLayout.setHorizontalGroup(
            jPanelPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jRadioPanelOne)
                    .addComponent(jRadioPanelTwo)
                    .addComponent(jRadioPanelFour))
                .addContainerGap(12, Short.MAX_VALUE))
        );
        jPanelPanelLayout.setVerticalGroup(
            jPanelPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelPanelLayout.createSequentialGroup()
                .addComponent(jRadioPanelOne)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jRadioPanelTwo)
                .addGap(18, 18, 18)
                .addComponent(jRadioPanelFour)
                .addContainerGap())
        );

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel3.setText("Rail Width:");

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel2.setText("Louver Length:");

        jLouverLenFld.setEditable(false);
        jLouverLenFld.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        jRailWidthFld.setEditable(false);
        jRailWidthFld.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        jHingeFld.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jHingeFld.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jHingeFldFocusLost(evt);
            }
        });

        jLouverCountLbl.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLouverCountLbl.setText("Louvers Qty:");

        jLouverCountFld.setEditable(false);
        jLouverCountFld.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel1.setText("Hinge:");

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel11.setText("Stile Length:");

        jStileLenFld.setEditable(false);
        jStileLenFld.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N

        jBitedFld.setEditable(false);
        jBitedFld.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel12.setText("Bited:");

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel13.setText("Rabited:");

        jRabitedFld.setEditable(false);
        jRabitedFld.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        jDataTable.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        jDataTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Line", "Mount", "Width", "Length", "Panel", "Rail", "Rail Qty.", "Louver Len.", "Louver Sz.", "Louver Qty.", "Stile Len.", "Bited", "Rabited", "Hinge", "Color", "# of Frame", "Frame Size", "Special Instructions", "ID"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jDataTable.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        jDataTable.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jDataTable.setMinimumSize(new java.awt.Dimension(70, 0));
        jDataTable.setRowHeight(48);
        jDataTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jDataTableMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jDataTable);
        jDataTable.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);

        jNewBtn.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jNewBtn.setText("New");
        jNewBtn.setBorderPainted(false);
        jNewBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jNewBtnActionPerformed(evt);
            }
        });

        jAddBtn.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jAddBtn.setText("Add");
        jAddBtn.setBorderPainted(false);
        jAddBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jAddBtnActionPerformed(evt);
            }
        });

        jDeleteBtn.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jDeleteBtn.setText("Delete");
        jDeleteBtn.setBorderPainted(false);
        jDeleteBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jDeleteBtnActionPerformed(evt);
            }
        });

        jIMoveUpBtn.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jIMoveUpBtn.setText("Move Up");
        jIMoveUpBtn.setBorderPainted(false);
        jIMoveUpBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jIMoveUpBtnActionPerformed(evt);
            }
        });

        jMoveDownBtn.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jMoveDownBtn.setText("Move Down");
        jMoveDownBtn.setBorderPainted(false);
        jMoveDownBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMoveDownBtnActionPerformed(evt);
            }
        });

        jUpdatetBtn.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jUpdatetBtn.setText("Update");
        jUpdatetBtn.setBorderPainted(false);
        jUpdatetBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jUpdatetBtnActionPerformed(evt);
            }
        });

        jSaveBtn.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jSaveBtn.setForeground(java.awt.Color.blue);
        jSaveBtn.setText("Save");
        jSaveBtn.setBorderPainted(false);
        jSaveBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jSaveBtnActionPerformed(evt);
            }
        });

        jBtnExit.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jBtnExit.setForeground(java.awt.Color.blue);
        jBtnExit.setMnemonic('E');
        jBtnExit.setText("Exit");
        jBtnExit.setBorderPainted(false);
        jBtnExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnExitActionPerformed(evt);
            }
        });

        jPrintInvoiceBtn.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jPrintInvoiceBtn.setMnemonic('I');
        jPrintInvoiceBtn.setText("Print Invoice");
        jPrintInvoiceBtn.setBorderPainted(false);
        jPrintInvoiceBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jPrintInvoiceBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jNewBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jAddBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jUpdatetBtn)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jDeleteBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jIMoveUpBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jMoveDownBtn)
                .addGap(18, 18, 18)
                .addComponent(jSaveBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPrintInvoiceBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jBtnExit, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(126, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jNewBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jAddBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jUpdatetBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jDeleteBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jIMoveUpBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jMoveDownBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jSaveBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jPrintInvoiceBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jBtnExit, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(29, Short.MAX_VALUE))
        );

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel7.setText("Square Footage:");

        jSqrFtFld.setEditable(false);
        jSqrFtFld.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(17, 17, 17)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLouverLenFld)
                            .addComponent(jRailWidthFld, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel8Layout.createSequentialGroup()
                                .addGap(60, 60, 60)
                                .addComponent(jLabel1))
                            .addGroup(jPanel8Layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(jLouverCountLbl)))
                        .addGap(22, 22, 22)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jHingeFld, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLouverCountFld, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel8Layout.createSequentialGroup()
                                .addComponent(jLabel11)
                                .addGap(18, 18, 18)
                                .addComponent(jStileLenFld)
                                .addGap(46, 46, 46)
                                .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jBitedFld, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel8Layout.createSequentialGroup()
                                .addComponent(jLabel13)
                                .addGap(18, 18, 18)
                                .addComponent(jRabitedFld, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(402, 402, 402))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                        .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(101, 101, 101)
                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jSqrFtFld, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(75, 75, 75))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addComponent(jScrollPane1)
                .addContainerGap())
        );

        jPanel8Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jHingeFld, jStileLenFld});

        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 224, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jSqrFtFld, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jBitedFld, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jRailWidthFld, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel3)
                        .addComponent(jLabel1)
                        .addComponent(jHingeFld, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel11)
                        .addComponent(jStileLenFld, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLouverLenFld, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLouverCountFld, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLouverCountLbl)
                    .addComponent(jLabel13)
                    .addComponent(jRabitedFld, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel8Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jBitedFld, jHingeFld, jLouverCountFld, jLouverLenFld, jRabitedFld, jRailWidthFld, jStileLenFld});

        jLabel14.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel14.setText("Invoice Number:");

        jCustIDFld.setEditable(false);
        jCustIDFld.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jCustIDFld.setNextFocusableComponent(jNameFld);
        jCustIDFld.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jCustIDFldFocusGained(evt);
            }
        });
        jCustIDFld.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                jCustIDFldComponentShown(evt);
            }
        });

        jPanel9.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Frame", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 13))); // NOI18N

        buttonGrpWindowType.add(jRadioFrameSillBase);
        jRadioFrameSillBase.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jRadioFrameSillBase.setText("Sill Base");
        jRadioFrameSillBase.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioFrameSillBaseActionPerformed(evt);
            }
        });

        buttonGrpWindowType.add(jRadioFrametoFrame);
        jRadioFrametoFrame.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jRadioFrametoFrame.setText("Frame-to-Frame");
        jRadioFrametoFrame.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioFrametoFrameActionPerformed(evt);
            }
        });

        buttonGrpWindowType.add(jRadioFrameNormal);
        jRadioFrameNormal.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jRadioFrameNormal.setSelected(true);
        jRadioFrameNormal.setText("Normal");
        jRadioFrameNormal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioFrameNormalActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jRadioFrametoFrame))
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jRadioFrameSillBase)
                            .addComponent(jRadioFrameNormal))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                .addComponent(jRadioFrameNormal)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jRadioFrameSillBase)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jRadioFrametoFrame))
        );

        jPanel10.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Number of Frames", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 13))); // NOI18N

        buttonGroup7.add(jRadioThreeSides);
        jRadioThreeSides.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jRadioThreeSides.setText("3 Sides");

        buttonGroup7.add(jRadioFourSides);
        jRadioFourSides.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jRadioFourSides.setSelected(true);
        jRadioFourSides.setText("4 Sides");

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jRadioThreeSides)
                    .addComponent(jRadioFourSides))
                .addContainerGap(49, Short.MAX_VALUE))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jRadioThreeSides)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jRadioFourSides)
                .addContainerGap(22, Short.MAX_VALUE))
        );

        jPanel11.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Rail Length", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 13))); // NOI18N

        buttonGrpRailLength.add(jRadioRailLen4);
        jRadioRailLen4.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jRadioRailLen4.setSelected(true);
        jRadioRailLen4.setText("4.5 Inch");

        buttonGrpRailLength.add(jRadioRailLen5);
        jRadioRailLen5.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jRadioRailLen5.setText("5.5 Inch");

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jRadioRailLen4)
                    .addComponent(jRadioRailLen5))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jRadioRailLen4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jRadioRailLen5)
                .addContainerGap())
        );

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel6.setText("Customer:");

        jNameFld.setEditable(false);
        jNameFld.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jNameFld.setNextFocusableComponent(jWidthFld);

        jPanel12.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Divider/Split Option", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 13))); // NOI18N

        buttonGrpDivSplit.add(jRadioSplit);
        jRadioSplit.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jRadioSplit.setText("Split");

        buttonGrpDivSplit.add(jRadioDivider);
        jRadioDivider.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jRadioDivider.setText("Divider");

        jDivSplitList.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jDivSplitList.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "50/50", "30/70" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        jDivSplitList.setSelectedIndices(new int[] {0});
        jScrollPane2.setViewportView(jDivSplitList);

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jRadioDivider)
                    .addComponent(jRadioSplit))
                .addGap(26, 26, 26)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addComponent(jRadioDivider)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jRadioSplit))
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 4, Short.MAX_VALUE)))
                .addContainerGap())
        );

        jWidthFld.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jWidthFld.setHorizontalAlignment(javax.swing.JTextField.TRAILING);
        jWidthFld.setText("0.0");
        jWidthFld.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        jWidthFld.setNextFocusableComponent(jLengthFld);
        jWidthFld.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jWidthFldFocusLost(evt);
            }
        });

        jWidthLabel.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jWidthLabel.setText("Width:");

        jLengthLabel.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLengthLabel.setText("Length:");

        jLengthFld.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLengthFld.setHorizontalAlignment(javax.swing.JTextField.TRAILING);
        jLengthFld.setText("0.0");
        jLengthFld.setNextFocusableComponent(jInstructionFld);
        jLengthFld.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jLengthFldFocusLost(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel4.setText("Instructions:");

        jInstructionFld.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        jItemNumber.setEditable(false);
        jItemNumber.setEnabled(false);
        jItemNumber.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jItemNumberActionPerformed(evt);
            }
        });

        jFrameSizePanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Frame Size", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 13))); // NOI18N

        jFrameSizeLst.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jScrollPane4.setViewportView(jFrameSizeLst);

        javax.swing.GroupLayout jFrameSizePanelLayout = new javax.swing.GroupLayout(jFrameSizePanel);
        jFrameSizePanel.setLayout(jFrameSizePanelLayout);
        jFrameSizePanelLayout.setHorizontalGroup(
            jFrameSizePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jFrameSizePanelLayout.createSequentialGroup()
                .addGap(0, 1, Short.MAX_VALUE)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jFrameSizePanelLayout.setVerticalGroup(
            jFrameSizePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jFrameSizePanelLayout.createSequentialGroup()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jFinishedWidthLabel.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jFinishedWidthLabel.setText("Finished Width:");

        jFinishedWidthFld.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jFinishedWidthFld.setHorizontalAlignment(javax.swing.JTextField.TRAILING);
        jFinishedWidthFld.setText("0.0");
        jFinishedWidthFld.setNextFocusableComponent(jFinishedLengthFld);
        jFinishedWidthFld.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jFinishedWidthFldFocusLost(evt);
            }
        });

        jFinishedLengthLabel.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jFinishedLengthLabel.setText("Finished Length:");

        jFinishedLengthFld.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jFinishedLengthFld.setHorizontalAlignment(javax.swing.JTextField.TRAILING);
        jFinishedLengthFld.setText("0.0");
        jFinishedLengthFld.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jFinishedLengthFldFocusLost(evt);
            }
        });

        jCalcBtn.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jCalcBtn.setMnemonic('&');
        jCalcBtn.setText("Calculate");
        jCalcBtn.setBorderPainted(false);
        jCalcBtn.setDebugGraphicsOptions(javax.swing.DebugGraphics.NONE_OPTION);
        jCalcBtn.setOpaque(false);
        jCalcBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCalcBtnActionPerformed(evt);
            }
        });

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Color", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 13))); // NOI18N

        jColorComboBox.setEditable(true);
        jColorComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Blanco", "Pure White", "Vivid White", " " }));

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jColorComboBox, 0, 243, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(jColorComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 83, Short.MAX_VALUE))
        );

        jMeasTypePan.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Measurement Type", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 13))); // NOI18N
        jMeasTypePan.setNextFocusableComponent(jWidthFld);

        buttonGrpMeasType.add(jRadioWindowStd);
        jRadioWindowStd.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jRadioWindowStd.setSelected(true);
        jRadioWindowStd.setText("Standard");
        jRadioWindowStd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioWindowStdActionPerformed(evt);
            }
        });

        buttonGrpMeasType.add(jRadioWindowFtoF);
        jRadioWindowFtoF.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jRadioWindowFtoF.setText("Frame to Frame");
        jRadioWindowFtoF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioWindowFtoFActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jMeasTypePanLayout = new javax.swing.GroupLayout(jMeasTypePan);
        jMeasTypePan.setLayout(jMeasTypePanLayout);
        jMeasTypePanLayout.setHorizontalGroup(
            jMeasTypePanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jMeasTypePanLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jRadioWindowStd)
                .addGap(18, 18, 18)
                .addComponent(jRadioWindowFtoF)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jMeasTypePanLayout.setVerticalGroup(
            jMeasTypePanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jMeasTypePanLayout.createSequentialGroup()
                .addGroup(jMeasTypePanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jRadioWindowStd)
                    .addComponent(jRadioWindowFtoF))
                .addGap(0, 7, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 115, Short.MAX_VALUE)
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jSillLabel.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jSillLabel.setText("Sill Width:");

        jSillWidthFld.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jSillWidthFld.setHorizontalAlignment(javax.swing.JTextField.TRAILING);
        jSillWidthFld.setText("0.0");
        jSillWidthFld.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jSillWidthFldFocusLost(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(4, 4, 4)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addGap(96, 96, 96)
                                .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jCustIDFld, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(162, 162, 162)
                                        .addComponent(jSillWidthFld, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                                .addComponent(jWidthLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(18, 18, 18))
                                            .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addGap(21, 21, 21)
                                                .addComponent(jFinishedWidthLabel)
                                                .addGap(35, 35, 35)))
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jFinishedWidthFld, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jWidthFld, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addGap(51, 51, 51)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jFinishedLengthLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLengthLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLengthFld, javax.swing.GroupLayout.DEFAULT_SIZE, 93, Short.MAX_VALUE)
                                    .addComponent(jFinishedLengthFld))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel4)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jInstructionFld, javax.swing.GroupLayout.PREFERRED_SIZE, 494, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jCalcBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel6)
                                .addGap(18, 18, 18)
                                .addComponent(jNameFld, javax.swing.GroupLayout.PREFERRED_SIZE, 249, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jMeasTypePan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap(486, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jPanel11, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jPanelPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jFrameSizePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(27, 27, 27)
                                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jItemNumber, javax.swing.GroupLayout.PREFERRED_SIZE, 7, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())))))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(72, 72, 72)
                        .addComponent(jSillLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(7, 7, 7)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jMeasTypePan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jCustIDFld, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jNameFld, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel6)
                                    .addComponent(jLabel14))
                                .addComponent(jLabel5))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(54, 54, 54)
                        .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jWidthLabel)
                            .addComponent(jWidthFld, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jFinishedLengthFld, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jFinishedWidthLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jFinishedWidthFld, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jFinishedLengthLabel))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLengthFld, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLengthLabel)
                            .addComponent(jCalcBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jInstructionFld, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4))))
                .addGap(1, 1, 1)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSillLabel)
                    .addComponent(jSillWidthFld))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jFrameSizePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanelPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jPanel10, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel9, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGap(19, 19, 19))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jItemNumber, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jCustIDFld.getAccessibleContext().setAccessibleName("AccessCustID");

        add(jPanel1, java.awt.BorderLayout.CENTER);

        getAccessibleContext().setAccessibleParent(this);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Exit the Application
     */
    private void exitForm(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_exitForm
        System.exit(0);
    }//GEN-LAST:event_exitForm

    private void jPanel1ComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_jPanel1ComponentShown
        jCustIDFld.setText(String.valueOf(custID));
        jNameFld.setText(custName);
    }//GEN-LAST:event_jPanel1ComponentShown

    private void jRadioWindowFtoFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioWindowFtoFActionPerformed
        setWindowStyle(WINDOW_STYLE_FRAME2FRAME);
        enableFinishedFields();
        disableStandardFields();
    }//GEN-LAST:event_jRadioWindowFtoFActionPerformed

    private void jRadioWindowStdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioWindowStdActionPerformed
        setWindowStyle(WINDOW_STYLE_STANDARD);
        disableFinishedFields();
        enableStandardFields();
        // enableSillFields(true);
    }//GEN-LAST:event_jRadioWindowStdActionPerformed

    private void jCalcBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCalcBtnActionPerformed
        boolean bF2F = false;
        if (getWindowStyle() == CustomerInvoice.WINDOW_STYLE_FRAME2FRAME){
            if (jFrameSizeLst.getSelectedIndex() >= 0){
                setWidth();
                setLength();
                setFinishedSize();
                bF2F = true;
            }
            else{
                JOptionPane.showMessageDialog(null, "A frame size has not been selected.  Please select a frame size.","Error",ERROR_MESSAGE);
                return;
            }

        }
        else if (jRadioFrameSillBase.isSelected()) {
            String sSillWidth = jSillWidthFld.getText();
            double dSillWidth = Double.parseDouble(sSillWidth);
            String frameSizeKey = getFrameSizeKey();                        
            double dFrameSize = getFrameSize(frameSizeKey, 1) ; // need method to get frame size
            double dGapAndDiff = 1.2;
//                double dDecValue = 0;
//                dDecValue = dfinWidth/(int)dfinWidth;
            dFrameSize *= 2;
            dSillWidth = subtract(dSillWidth,dFrameSize);
            double dWidth = subtract(dSillWidth, dGapAndDiff);
            String sWidth = String.format("%.1f", dWidth);
            jWidthFld.setText(sWidth);           
        }

        String sPanel = getPanels();
        double dStileLen = getStileLength();
        jHingeFld.setText(sPanel);
        String sWidth = jWidthFld.getText();
        if (sWidth == null || sWidth.length() == 0){
            return;
        }
        double dWidth = Double.parseDouble(sWidth);
        //        if (bF2F)
        //          dWidth--;
        if (sPanel == "L" || sPanel == "R") {
            calcPanel(1, dWidth);
            if (buttonGrpPanel.isSelected(null))
            jRadioPanelOne.setSelected(true);
        }
        else if (sPanel == "LR"){
            calcPanel(2, dWidth);
            if (buttonGrpPanel.isSelected(null))
            jRadioPanelTwo.setSelected(true);
        }
        else if (sPanel == "2L2R"){
            calcPanel(4, dWidth);
            if (buttonGrpPanel.isSelected(null))
            jRadioPanelFour.setSelected(true);
        }

        String stileLen = decimalToFraction(dStileLen);

        jStileLenFld.setText(stileLen);
        jBitedFld.setText("2");
        jRabitedFld.setText(getRabited());
        jAddBtn.setEnabled(true);
        // jUpdatetBtn.setEnabled(true);

    }//GEN-LAST:event_jCalcBtnActionPerformed

    private void jItemNumberActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jItemNumberActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jItemNumberActionPerformed

    private void jRadioFrametoFrameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioFrametoFrameActionPerformed
        if (jRadioFrametoFrame.isSelected()){
            jRadioThreeSides.setEnabled(true);
            jRadioFourSides.setEnabled(true);

            enableSillFields(false);
            
            //jWidthFld.setEnabled(false);
            //jWidthLabel.setEnabled(false);
            
        }
    }//GEN-LAST:event_jRadioFrametoFrameActionPerformed

    private void jRadioFrameSillBaseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioFrameSillBaseActionPerformed
        if (jRadioFrameSillBase.isSelected()){
            jRadioThreeSides.setSelected(true);
            jRadioFourSides.setEnabled(false);
            enableSillFields(true);
            jSillWidthFld.requestFocus();
            enableFinishedFields();
            jFinishedLengthFld.setEnabled(false);
            jFinishedLengthLabel.setEnabled(false);
            jFinishedWidthFld.setEnabled(false);
            jFinishedWidthLabel.setEnabled(false);
            
            
            
            jWidthFld.setEnabled(false);
            jWidthLabel.setEnabled(false);
        }
    }//GEN-LAST:event_jRadioFrameSillBaseActionPerformed

    private void jCustIDFldComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_jCustIDFldComponentShown

    }//GEN-LAST:event_jCustIDFldComponentShown

    private void jCustIDFldFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jCustIDFldFocusGained
        jCustIDFld.setText(String.valueOf(getCustID()));
        jNameFld.setText(getCustName());
    }//GEN-LAST:event_jCustIDFldFocusGained

    private void jPrintInvoiceBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jPrintInvoiceBtnActionPerformed
        try {
            printInvoice();
        } catch (PrinterException ex) {
            Logger.getLogger(ShuttersFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jPrintInvoiceBtnActionPerformed

    private void jBtnExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnExitActionPerformed
        int iOption = OK_OPTION;
        if (bSaveRequired){
            iOption = JOptionPane.showConfirmDialog(null,"              Exit without saving?\n\nPress OK to continue or Cancel to return.\n"  , "Confirm Exit",OK_CANCEL_OPTION , QUESTION_MESSAGE, null);
        }
        if (iOption == CANCEL_OPTION)
        return;

        bSaveRequired = false ;
        getParentFrame().setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_jBtnExitActionPerformed

    private void jSaveBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jSaveBtnActionPerformed
        try {
            saveOrder();
        } catch (SQLException ex) {
            Logger.getLogger(ShuttersFrame.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null,"Unable to save records: " + ex.getMessage());
        }
    }//GEN-LAST:event_jSaveBtnActionPerformed

    private void jUpdatetBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jUpdatetBtnActionPerformed
        // insertItem();
        int index = jDataTable.getSelectedRow();
        setRowData(index, UPDATE);
        resetFields();
        String sqrFt = getSquareFootage();
        jSqrFtFld.setText(sqrFt);
    }//GEN-LAST:event_jUpdatetBtnActionPerformed

    private void jMoveDownBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMoveDownBtnActionPerformed
        moveDown();
    }//GEN-LAST:event_jMoveDownBtnActionPerformed

    private void jIMoveUpBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jIMoveUpBtnActionPerformed
        moveUp();
    }//GEN-LAST:event_jIMoveUpBtnActionPerformed

    private void jDeleteBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jDeleteBtnActionPerformed

        removeRow();
        resetFields();
    }//GEN-LAST:event_jDeleteBtnActionPerformed

    private void jAddBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jAddBtnActionPerformed
        setRowData(-1, ADD);
        resequence();
        resetFields();
        String sqrFt = getSquareFootage();
        jSqrFtFld.setText(sqrFt);
        int iCount = jDataTable.getRowCount();
        jDataTable.setEditingRow(iCount);
    }//GEN-LAST:event_jAddBtnActionPerformed

    private void jNewBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jNewBtnActionPerformed
        jAddBtn.setEnabled(true);
        resetFields();

        //        try {
            //            String orderid = jCustIDFld.getText();
            //            long count = getOrderCount(orderid) + 1;
            //            jItemNumber.setText(String.valueOf(count));
            //        } catch (SQLException ex) {
            //            Logger.getLogger(ShuttersFrame.class.getName()).log(Level.SEVERE, null, ex);
            //        }

    }//GEN-LAST:event_jNewBtnActionPerformed

    private void jDataTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jDataTableMouseClicked
        Object [] cols = getTableSelection();
        selectTableItem(cols);
    }//GEN-LAST:event_jDataTableMouseClicked

    private void jRadioPanelTwoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioPanelTwoActionPerformed

    }//GEN-LAST:event_jRadioPanelTwoActionPerformed

    private void jRadioInsideActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioInsideActionPerformed
        populateFrameSizeList("I");
    }//GEN-LAST:event_jRadioInsideActionPerformed

    private void jRadioOutsideActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioOutsideActionPerformed
        populateFrameSizeList("O");

    }//GEN-LAST:event_jRadioOutsideActionPerformed

    private void jRadioShopActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioShopActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jRadioShopActionPerformed

    private void jHingeFldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jHingeFldFocusLost
        String str = jHingeFld.getText();
        String strUpper = str.toUpperCase();
        jHingeFld.setText(strUpper);
        if (strUpper.compareToIgnoreCase("L")!=0 && strUpper.compareToIgnoreCase("R") != 0 && 
                strUpper.compareToIgnoreCase("LR") != 0 && strUpper.compareToIgnoreCase("2LR") != 0){
            JOptionPane.showMessageDialog(null, "Invalid hinge type.  Please enter R or L or LR or 2LR.");
            jHingeFld.requestFocus();
        }
    }//GEN-LAST:event_jHingeFldFocusLost

    private void jRadioFrameNormalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioFrameNormalActionPerformed
        if (jRadioFrameNormal.isSelected())
        {
            enableSillFields(false);
            
            jWidthFld.setEnabled(true);
            jWidthLabel.setEnabled(true);
            jWidthFld.requestFocus();
            jRadioFourSides.setEnabled(true);
            jRadioFourSides.setSelected(true);
            disableFinishedFields();
        }
        
    }//GEN-LAST:event_jRadioFrameNormalActionPerformed

    private void jLengthFldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jLengthFldFocusLost
        autoSetDecimal(jLengthFld);
    }//GEN-LAST:event_jLengthFldFocusLost

    private void jWidthFldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jWidthFldFocusLost
        autoSetDecimal(jWidthFld);
    }//GEN-LAST:event_jWidthFldFocusLost

    private void jFinishedWidthFldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jFinishedWidthFldFocusLost
        autoSetDecimal(jFinishedWidthFld);
    }//GEN-LAST:event_jFinishedWidthFldFocusLost

    private void jFinishedLengthFldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jFinishedLengthFldFocusLost
        autoSetDecimal(jFinishedLengthFld);
    }//GEN-LAST:event_jFinishedLengthFldFocusLost

    private void jSillWidthFldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jSillWidthFldFocusLost
        autoSetDecimal(jSillWidthFld);
    }//GEN-LAST:event_jSillWidthFldFocusLost

    String getRabited(){
        String rabited = "";
        String panel = getPanels();
        switch (panel){
            case "LR":
                rabited = "2";
                break;
            case "2L2R":
                rabited = "6";
                break;
            default:
        }
        return rabited;
    }
    
    private void launchExcel() throws ExcelException{
        JFrame frame= new JFrame("Test application");

        frame.setDefaultCloseOperation (
          javax.swing.WindowConstants.EXIT_ON_CLOSE
        );

        Container cp = frame.getContentPane();

        cp.setLayout( new BorderLayout());

        JWorkbook workbook = new JWorkbook();

        cp.add(workbook);

        frame.setBounds(100, 100, 500, 500 );

        frame.setVisible(true);       
        
    }
    
    public void validateSizeField(JTextField fldName){
        String sValue = fldName.getText();
        int index = sValue.indexOf(".");
        String sFraction = sValue.substring(index +1);
        double dFraction = Double.valueOf(sFraction);
        if (dFraction >= 8){
            JOptionPane.showMessageDialog(null, "The input decimal must be < .8.  Please re-enter.","Error",ERROR_MESSAGE);
            fldName.requestFocus();
         }
     }
    
    public int validateNumeric(JTextField fldName){
        String str = jWidthFld.getText();
        int len = str.length();
        for (int i=0; i < len; i++){
            String s = str.substring(i);
            
            if (Integer.getInteger(s) < 0 || Integer.getInteger(s) > 9){
                JOptionPane.showMessageDialog(null, "You've entered an invalid value."); 
                return -1;
            }
        }
        
        return 1;
    }
    public void printInvoice() throws PrinterException
    {
     
     String h1 ="Shutters";
     String h2 = "Test header";
     Date dt = new Date();
     Font ft = new Font("Courier",Font.PLAIN,16);
     String strObj = DateFormat.getDateInstance().format(dt);     // String squareFt = getSquareFootage();
     // **************
    StringBuilder builder = new StringBuilder();
    builder.append("PWSB - Work Order    ");
    builder.append(System.getProperty("line.separator"));
    builder.append("Name: ");
    builder.append(custName);
    builder.append(System.lineSeparator());
    builder.append("      ");
    builder.append(strObj);     
     
     //***************

     // String header = MessageFormat.format("PWSB - Work Order  ", strObj);

    Font font = new Font("Arial", Font.BOLD, 16);
    MessageFormat header = new MessageFormat(builder.toString());
    //header.setFormat(1, font);
    //MessageFormat msgFmt = new MessageFormat(header);
     MessageFormat footer = new MessageFormat("{0,number,integer}");
     //Message
    //footer.set("{0,number,integer}",null);
     JTable table = jDataTable;
    PrintRequestAttributeSet attr = new HashPrintRequestAttributeSet();
    attr.add(OrientationRequested.LANDSCAPE);
    attr.add(MediaSizeName.NA_LEGAL);
    
//    JTableHeader thdr = new JTableHeader(table.getColumnModel());
//    thdr.setFont(font);
//    table.setTableHeader(thdr);
    //Graphics g = table.getGraphics();
    // g.drawString(strObj, 10, 10);
    
    //table.printComponents(g);
    
    table.print(JTable.PrintMode.FIT_WIDTH, header, footer, true, attr, false);
    }
    

    
    public void setSquareFootage(){
        String sqrFt = getSquareFootage();
        jSqrFtFld.setText(sqrFt);        
    }
    
    public String getSquareFootage(){
        String sqrft = "";
        String sWidth = "";
        String sLength = "";
        double dTotal =0.0;
        int rowCount = jDataTable.getRowCount();
        DefaultTableModel model = (DefaultTableModel) jDataTable.getModel();
        for (int i=0; i < rowCount; i++){
            sWidth = (String) model.getValueAt(i, 2);
            sLength = (String) model.getValueAt(i, 3);
            int index = sWidth.indexOf(" ");
            if (index == -1)
                index = sWidth.length() - 1;
            String sWidthTemp = sWidth.substring(0,index);
            index = sLength.indexOf(" ");
            if (index == -1)
                index = sLength.length() - 1;
            String sLengthTemp = sLength.substring(0, index);
            dTotal += (Double.parseDouble(sWidthTemp) * Double.parseDouble(sLengthTemp))/144;       
        }

        sqrft = String.format("%.2f", dTotal);
        return sqrft;
    }
        

    private int getLouverQty(){
        double dLen = Double.valueOf(jLengthFld.getText());
        
        ///////Inside Mount Option/////////
        String mountOpt = getMountingOption();

        String sFrameSzKey = getFrameSizeKey();

        if (mountOpt == "IM"){
            dLen = getIMLength(dLen);
        }
        
       
        //////////////////////////////////
        int iLouverQty = 0;
        int iDiv = 0;
        double dRails = dRails4;
        int iLouverSize = iLouverSize3;
        
        if (jRadioRailLen5.isSelected())
            dRails = dRails5;
        
        if (jRadioLouverChoice4.isSelected())
            iLouverSize = iLouverSize4;
        else if (jRadioLouverChoice2.isSelected())
            iLouverSize = iLouverSize2;
        
        iLouverQty = (int)((dLen - dRails)/ iLouverSize);
        
        iLouverQty++; // why this was added
        return iLouverQty;
    }
    
    private double getStileLength(){
        double dStileLen = 0.0;
        int iDiv = 0;
        
        double dRails = dRails4;
        int iLouverSize = iLouverSize3;
        Double dLen = Double.valueOf(jLengthFld.getText());
        String mountOpt = getMountingOption();

        String sFrameSzKey = getFrameSizeKey();

        if (mountOpt == "IM"){
            dLen = getIMLength(dLen);
        }
        String sLen = String.valueOf(dLen);

        int index = sLen.indexOf(".");
        String sFraction = sLen.substring(index);
        double dFraction = Double.parseDouble(sFraction);
        String sWholeVal = sLen.substring(0, index);
        double dWholeVal = Double.parseDouble(sWholeVal);

        ////////////////////Inside Mount Option/////////////
//        else {
//            if (jRadioFrameNormal.isSelected()){
//                if (jRadioFourSides.isSelected())
//                    dWholeVal++;
//                else
//                    dLength += .4;
//            }
//            else if (jRadioFrameSillBase.isSelected()){
//                dLength += .4;
//            }
//        }
        ////////////////////////////////////////////////////
        
        if (jRadioRailLen5.isSelected())
            dRails = dRails5;
        
        if (jRadioLouverChoice4.isSelected())
            iLouverSize = iLouverSize4;
        else if (jRadioLouverChoice2.isSelected())
            iLouverSize = iLouverSize2;
        
        if (dFraction >  0.7)
            dWholeVal++;
        // double dLen = Double.valueOf(sLen);        
        iDiv = (int) (dWholeVal - dRails) % iLouverSize;
        if (iLouverSize == 2){
            if (iDiv == 0)
                dStileLen = add(dWholeVal,1.7); // 2.875
            else if (iDiv == 1)
                dStileLen = add(dWholeVal,.7); // 1.875
        }
        if (iLouverSize == 3){
            if (iDiv == 0)
                dStileLen = add(dWholeVal,2.7); // 2.875
            else if (iDiv == 1)
                dStileLen = add(dWholeVal,1.7); // 1.875
            else
                dStileLen = add(dWholeVal,.7); // .845
        }
        else if (iLouverSize == 4){
            if (iDiv == 0)
                dStileLen = add(dWholeVal, 3.7); // 3.875
            else if (iDiv == 1)
                dStileLen = add(dWholeVal, 2.7); // 2.875
            else if (iDiv == 2)
                dStileLen = add(dWholeVal, 1.7); // 1.875
            else
                dStileLen = add(dWholeVal, .7); // .875
        }
        return dStileLen;
    
    }
    
    public double getFrameSize(String frameSizeKey, int mountOpt){
        double frameSize = 0.0;
        // mountOpt { OM = 1; IM = 2}
        if (frameSizeKey == null || frameSizeKey.length() == 0){
            JOptionPane.showMessageDialog(null, "Please select a frame size before calculation.");
            jFrameSizeLst.setForeground(Color.red);
            return 0.0;
        }
      
        if (mountOpt == 1){ // OM
            frameSize = (double) outFrameSizeMap.get(frameSizeKey);
        }
        else if (mountOpt == 2){ // IM
            frameSize = (double) inFrameSizeMap.get(frameSizeKey);
        }
        
        return frameSize;
    }
    
    double add(double dOp1, double dOp2){
        double dRes = 0.0;
        double dValue = 0.0;
        double dFract = 0.0;
        String sOp1 = String.valueOf(dOp1);
        int index = sOp1.indexOf(".");
        String sValue1 = sOp1.substring(0, index);
        String sFract1 = sOp1.substring(index);
        String sOp2 = String.valueOf(dOp2);
        index = sOp2.indexOf(".");
        String sValue2 = sOp2.substring(0, index);
        String sFract2 = sOp2.substring(index);
        double dValue1 = Double.parseDouble(sValue1);
        double dValue2 = Double.parseDouble(sValue2);
        double dFract1 = Double.parseDouble(sFract1);
        double dFract2 = Double.parseDouble(sFract2);
        if (dFract1 >= 0.8){
            dValue1++;
            dFract1 -= 0.8;
        }
        if (dFract2 >= 0.8){
            dValue2++;
            dFract2 -= 0.8;
        }
        dValue = dValue1 + dValue2;
        
        dFract = (float)Double.sum(dFract1,dFract2);
        
        if (dFract >= 0.8){
            dValue++;
            dFract -= 0.8;
        }
        dRes = dValue + dFract;
        
        return dRes;
    }
    double subtract(double dOp1, double dOp2){
        double dRes = 0.0;
        double dValue = 0.0;
        double dFract = 0.0;
        String sOp1 = String.valueOf(dOp1);
        int index = sOp1.indexOf(".");
        String sValue1 = sOp1.substring(0, index);
        String sFract1 = sOp1.substring(index);
        String sOp2 = String.valueOf(dOp2);
        index = sOp2.indexOf(".");
        String sValue2 = sOp2.substring(0, index);
        String sFract2 = sOp2.substring(index);
        double dValue1 = Double.parseDouble(sValue1);
        double dValue2 = Double.parseDouble(sValue2);
        double dFract1 = Double.parseDouble(sFract1);
        double dFract2 = Double.parseDouble(sFract2);
        if (Double.parseDouble(sFract2) > Double.parseDouble(sFract1)){
            if (dFract2 >= 0.8)
                dFract = 1.0 - dFract2;
            else
                dFract = 0.8 - dFract2;
            dFract = add(dFract,dFract1);
            dValue1--;
        }
        else {
            dFract = dFract1 - dFract2;
        }
        
        
        dValue = dValue1 - dValue2;
        dRes = dValue + dFract;

        
        
        
        return dRes;
    }
    
    public void setWidth(){
        // subtract 3 1/4 or 3 3/4 based on frame size.
        int mountOption = 1;
        if (jRadioInside.isSelected())
            mountOption = 2;
        else if (jRadioOutside.isSelected())
            mountOption = 1;
        String frameSizeKey = getFrameSizeKey(); // (String) jFrameSizeLst.getSelectedValue();
        
        double dfinWidth = Double.parseDouble(jFinishedWidthFld.getText());
        double dWidth = 0.0;
        
        if (frameSizeKey != null){
            double dFrameSize = getFrameSize(frameSizeKey, mountOption) ; // need method to get frame size
            double dGapAndDiff = 1.2;
            double dDecValue = 0;
            dDecValue = dfinWidth/(int)dfinWidth;
            dFrameSize *= 2;
            dfinWidth = subtract(dfinWidth,dFrameSize);
            dWidth = subtract(dfinWidth, dGapAndDiff);
            //if (dDecValue >= 1.011764705882353) //  51.6 divide 51
            //    dWidth = dfinWidth - (dFrameSize*2) - dGapAndDiff;
            //else
            //    dWidth = dfinWidth - (dFrameSize*2) - (dGapAndDiff + 0.2);
            String sWidth = String.format("%.1f", dWidth);
            jWidthFld.setText(sWidth);           

        //double dResult = subtract(dfinWidth, dGapAndDiff);
            
        }

    }
    
    public double getIMWidth(int panelCount, double dWidth){
        double dInsideWidth = 0.0;
        // int panelOpt = getPanelOption();
        String sFrameSzKey = getFrameSizeKey();
        // Inside Mount
        if (sFrameSzKey == "3/4 Lframe"){
            if (panelCount == 2)
                dInsideWidth = subtract(dWidth,2.2);
            else if (panelCount == 4)
                dInsideWidth = subtract(dWidth,2.3);
            else
                dInsideWidth = subtract(dWidth,2.0);
        }
        else if (sFrameSzKey == "1\" Lframe"){
            if (panelCount == 4)
                dInsideWidth = subtract(dWidth,2.5);
            else
                dInsideWidth = subtract(dWidth,2.4);
        }
        else
            dInsideWidth = dWidth - 2.0;
                
        return dInsideWidth;
    }

    public double getIMLength(double dLen){
        double dInsideLen = 0.0;
        String sFrameSzKey = getFrameSizeKey();
        // Inside Mount
        if (sFrameSzKey == "1\" Lframe"){
            dInsideLen = subtract(dLen,2.4);
        }
        else { //(sFrameSzKey == "3/4 Lframe")
            dInsideLen = dLen - 2.0;
        }
       
        return dInsideLen;
    }
    public void setLength(){
        String sFinLength = jFinishedLengthFld.getText();
        int mountOption = 1;
        if (jRadioInside.isSelected())
            mountOption = 2;
        else if (jRadioOutside.isSelected())
            mountOption = 1;
        String frameSizeKey = getFrameSizeKey();// (String) jFrameSizeLst.getSelectedValue();

        
        if (frameSizeKey != null){
            double dFrameSize = getFrameSize(frameSizeKey, mountOption) ; // need method to get frame size
            double dGap = 0.2;
            if (sFinLength == null || sFinLength.length() == 0){
                return;
            }
            double dFinLength = Double.parseDouble(sFinLength);
            dFrameSize *= 2;
            double dLength = subtract(dFinLength,dFrameSize);
            dLength = subtract(dLength,dGap);
            String sLength = String.format("%.1f",dLength);
            jLengthFld.setText(String.valueOf(sLength));
        }
    }
    
    public void setFinishedSize(){
        String sFinWidth = jWidthFld.getText();
        String sFinLength = jLengthFld.getText();
        double dWidth = Double.parseDouble(sFinWidth);
        double dLength = Double.parseDouble(sFinLength);
        sFinWidth = String.valueOf(dWidth);
        sFinLength = String.valueOf(dLength);
                
        String sInstruction = "Typing size=" + sFinWidth + " x " + sFinLength;
        jInstructionFld.setText(sInstruction);
    }
    private void calcPanel(int panelCount, double dWidth){
        String mountOpt = getMountingOption();
        if (mountOpt == "IM"){
            dWidth = getIMWidth(panelCount, dWidth);
        }
        
        String sWidth = String.valueOf(dWidth);
        int index = sWidth.indexOf(".");
        String sFraction = sWidth.substring(index);
        double dFraction = Double.parseDouble(sFraction);
        boolean highPrecision = false;
        
        Double dBitedWidth = 3.125;
        Double dStile = 4.0;
        String sRailWidth = "";
        double dWidthDiff = 0.03125;
        //double dHinge = 0.0625;
        double dGap = .0625;
        //int dExtra = (panelCount == 4) ? 2:1 ;
        double dExtra = 1.0;
        if (panelCount == 4){
            //dExtra = 2.0;
 //           dHinge = 0.0;
            // dStile = 4.0625;
            // dWidthDiff = 0.0625;
            highPrecision = true;
            dStile = 2.8375;
            if (dFraction > 0.1 && dFraction <= 0.6) {
                dStile -= 0.037;
            }
            else if (dFraction > 0.6){
                dStile -= 0.0625;
                dGap = .0625;
            }
            else if (dFraction == 0.0)
                dStile -= 0.02;
            
        }
        else if (panelCount == 2){
            dExtra = 1.0;
//            dHinge = 0.125;
            dStile = 1.8625;
            if (dFraction > 0.5){
                dStile = 1.8475;
                dGap = .0625;
            }
            else if (dFraction == 0.0)
                dStile = 1.875;
        }
        else if (panelCount == 1){
            dBitedWidth = 0.0;
            dStile = 3.062;
            dGap = .0625;
            if ((dFraction > 0.2 && dFraction < 0.5)){
                dStile = 3.00;
            }
            else if (dFraction >= 0.5){
                dStile = 2.9375;
            }
            if (dFraction == 0.0)
                dStile = 3.0625;
        }
        // Frame to frame

        double dStileExtra = (panelCount == 4) ? 4.125: 4.06;
        // double dWidth = Double.valueOf(jWidthFld.getText()) + dExtra;
        // 5.30.15 dWidth += dExtra;
        if(jRadioInside.isSelected()){
            dWidth = subtract(dWidth,1.0);
        }
        
        dWidth = dWidth - dBitedWidth;
        double dWidth_div_panel = dWidth/panelCount; // Added 05.27.15
        double dLouverWidth = dWidth_div_panel - dStile;//  - dStilePlus;
        // dLouverWidth += dHinge;
        double dRailWidth = dLouverWidth + dGap; // subtract(dWidth_div_panel,dStile);
        sRailWidth = numToStr(dRailWidth,highPrecision);
        String fmtLouverWidth;
        fmtLouverWidth =numToStr(dLouverWidth,highPrecision);
        
        jLouverLenFld.setText(fmtLouverWidth);
        jRailWidthFld.setText(sRailWidth);
        int iLouverQty = getLouverQty();
        String fmtLouverQty = getLouverCount(iLouverQty, panelCount);
        jLouverCountFld.setText(fmtLouverQty);
        
    }
    
    private void calcOnePanel(){
        double dStile = 4.125;
        double dWidth = Double.valueOf(jWidthFld.getText()) + 1;
        double dLouverWidth = dWidth - dStilePlus;
        double dRailWidth = dWidth - dStile;
        String fmtLouverWidth = String.format("%.3f",dLouverWidth );
        //converts back to double
        dLouverWidth = Double.parseDouble(fmtLouverWidth);
        fmtLouverWidth =numToStr(dLouverWidth,true);
        
        // jLouverLenFld.setText(String.valueOf(dLouverWidth));
        jLouverLenFld.setText(fmtLouverWidth);
        jRailWidthFld.setText(String.valueOf(dRailWidth));
        int iLouverQty = getLouverQty();
        String fmtLouverQty = getLouverCount(iLouverQty, 1);
        jLouverCountFld.setText(fmtLouverQty);
        
    }
    
    
    private void calcTwoPanel(){
         double dStile = 4.0;
        double dWidth = Double.valueOf(jWidthFld.getText()) + 1;
        double dLouverWidth = (dWidth/2 - dStilePlus);
        double dRailWidth = (dWidth/2) - dStile;
        jLouverLenFld.setText(String.valueOf(dLouverWidth));
        jRailWidthFld.setText(String.valueOf(dRailWidth));
        
        int iLouverQty = getLouverQty();
        String fmtLouverQty = getLouverCount(iLouverQty, 2);
        jLouverCountFld.setText(fmtLouverQty);
   }
    
    private void calcFourPanel(){
         double dStile = 4.0;
         double dWidth = Double.valueOf(jWidthFld.getText()) + 2;
        double dLouverWidth = (dWidth/4 - dStilePlus);
        double dRailWidth = (dWidth/4) - dStile;
        jLouverLenFld.setText(String.valueOf(dLouverWidth));
        jRailWidthFld.setText(String.valueOf(dRailWidth));
        
        int iLouverQty = getLouverQty();
        String fmtLouverQty = getLouverCount(iLouverQty, 4);
        jLouverCountFld.setText(fmtLouverQty);       
    }
    double roundDouble(double num){
        double dResult = 0.0;
        if (num == 0.0)
            dResult = 0.0;
        else if (num > 0.0 && num < 0.10)
            dResult = 0.0625;
        else if (num >= 0.10 && num <= 0.125)
            dResult = 0.125;
        else if (num > 0.125 && num <= 0.1875)
            dResult = 0.1875;
        else if (num > 0.1875 && num <= 0.250)
            dResult = 0.250;
        else if (num > 0.250 && num <= 0.3125)
            dResult = 0.3125;
        else if (num > 0.3125 && num <= 0.3750)
            dResult = 0.3750;
        else if (num > 0.3750 && num <= 0.4375)
            dResult = .4375;
        else if (num > 0.4375 && num <= 0.50)
            dResult = 0.50;
        else if (num > 0.50 && num <= 0.5625)
            dResult = 0.5625;
        else if (num > 0.5625 && num <= 0.625)
            dResult = 0.625;
        else if (num > 0.625 && num <= 0.6875)
            dResult = 0.6875;
        else if (num > 0.6875 && num <= 0.749)
            dResult = 0.75;
        else if (num > 0.749 && num <= 0.8125)
            dResult = 0.8125;
        else if (num > 0.8125 && num <= 0.875)
            dResult = 0.875;
        else if (num > 0.875 && num <= .9375)
            dResult = 0.9375;
        else
            dResult = 1.0;
        
        return dResult;
    }
        
    double roundDecimal(double num){
//  {.0000, .0625, .1250, .1875,   .2500, .3125,  .3750,  .4375, .5000,  .56250, .6250,  .6870,    .7500,  .8125,   .875,  .9375};
//  {   "","1/16", "1/8", "3/16",  "1/4", "5/16", "3/8",  "7/16", "1/2", "9/16", "5/8",  "11/16",  "3/4",  "13/16", "7/8", "15/16"};        
//        Double num32[] = {.0000, 0.03125, .0625, 0.09375, .1250, 0.15625, .1875, 0.21875, .2500, 0.28125, .3125, 0.34375, .3750, 0.40625, .4375, 0.46875, 
//                        0.5000, 0.53125, 0.56250, 0.59375, 0.6250, 0.65625, 0.6875, 0.71875, .7500, 0.78125, .8125, 0.84375, 0.8750, 0.90625, 0.9375, 0.96875};
//        String sValue32[] = {"","1/32","1/16","3/32","1/8","5/32","3/16","7/32","1/4","9/32","5/16","11/32","3/8","13/32","7/16","15/32",
//                        "1/2","17/32","9/16","19/32","5/8","21/32","11/16","23/32","3/4","25/32","13/16","27/32","7/8","29/32","15/16","31/32"};

        double dResult = 0.0;
        if (num >= 0 && num <= 0.01)
            dResult = 0.0;
        else if (num > .01 && num <= 0.03125)
            dResult = .03125;
        else if (num > 0.03125 && num <= 0.0625)
            dResult = 0.0625;
        else if (num > 0.0625 && num <= 0.09375)
            dResult = 0.09375;
        else if (num > 0.09375 && num <= 0.1250)
            dResult = 0.1250;
        else if (num > 0.1250 && num <= 0.15625)
            dResult = 0.15625;
        else if (num > 0.15625 && num <= 0.1875)
            dResult = 0.1875;
        else if (num > 0.1875 && num <= 0.21875)
            dResult = 0.21875;
        else if (num > 0.21875 && num <= 0.2500)
            dResult = 0.2500;
        else if (num > 0.2500 && num <= 0.28125)
            dResult = 0.28125;
        else if (num > 0.28125 && num <= 0.3125)
            dResult = 0.3125;
        else if (num > 0.3125 && num <= 0.34375)
            dResult = 0.34375;
        else if (num > 0.34375 && num <= 0.3750)
            dResult = 0.3750;
        else if (num > 0.3750 && num <= 0.40625)
            dResult = 0.40625;
        else if (num > 0.40625 && num <= 0.4375)
            dResult = 0.4375;
        else if (num > 0.4375 && num <= 0.46875)
            dResult = 0.46875;
        else if (num > 0.46875 && num < 0.5000)
            dResult = 0.5000;
        else if (num > 0.5000 && num < 0.53125)
            dResult = 0.53125;
        else if (num > 0.53125 && num < 0.56250)
            dResult = 0.56250;
        else if (num > 0.56250 && num < 0.59375)
            dResult = 0.59375;
        else if (num > 0.59375 && num < 0.6250)
            dResult = 0.6250;
        else if (num > 0.6250 && num < 0.65625)
            dResult = 0.65625;
        else if (num > 0.65625 && num < 0.6875)
            dResult = 0.6875;
        else if (num > 0.6875 && num < 0.71875)
            dResult = 0.71875;
        else if (num > 0.71875 && num < 0.7500)
            dResult = 0.7500;
        else if (num > 0.7500 && num < 0.78125)
            dResult = 0.78125;
        else if (num > 0.78125 && num < 0.8125)
            dResult = 0.8125;
        else if (num > 0.8125 && num < 0.84375)
            dResult = 0.84375;
        else if (num > 0.84375 && num < 0.8750)
            dResult = 0.8750;
        else if (num > 0.8750 && num < 0.90625)
            dResult = 0.90625;
        else if (num > 0.90625 && num < 0.9375)
            dResult = 0.9375;
        else if (num > 0.9375 && num < 0.96875)
            dResult = 0.96875;
        else if (num > 0.96875)
            dResult = 1.0;
                    
        return dResult;
        
    }
    private String numToStr(Double dValue, boolean bHighPrecision){
        String target="";
        String temp = String.format("%.4f", dValue);
        String result[] = new String[2];
        int index = temp.lastIndexOf(".");
        result[1] = temp.substring(index);
        result[0] = temp.substring(0,temp.length()-(result[1].length()));
        Double dWhole = Double.parseDouble(result[0]);
        
        double dNum = Double.parseDouble(result[1]);
        //if (dNum > 0 && dNum < 0.9375)
            //dNum += .0625;
        
        double dRounded = 0.0;
        if (bHighPrecision)
            dRounded = roundDecimal(dNum); // 1/32
        else
            dRounded = roundDouble(dNum);  // 1/16
        
        if (dRounded == 1.0){
            dWhole++;
            result[0] = String.valueOf(dWhole);
            dRounded = 0.0;
        }
        
        
        target = result[0];
        target += " ";
        if (bHighPrecision)
            target += (String) fractionMap32.get(dRounded);
        else
            target += (String) fractionMap.get(dRounded);
        return target;
    }
    
    private String decimalToFraction(Double dValue){
        String target="";
        String temp = String.format("%.4f", dValue);
        String result[] = new String[2];
        int index = temp.lastIndexOf(".");
        result[1] = temp.substring(index);
        result[0] = temp.substring(0,temp.length()-(result[1].length()));
        double dNum = Double.parseDouble(result[1]);
        //if (dNum > 0 && dNum < 0.9375)
            //dNum += .0625;
        //double dRounded = roundDouble(dNum);
        
        target = result[0];
        target += " ";
        target += (String) decimalToFraction.get(dNum);
        return target;
    }
    
    public double stringToDouble(String sValue){
        double dValue =0;
        double dDecimalValue = 0;
        int iPos = sValue.indexOf(" ");
        if (iPos == -1)
            return Double.parseDouble(sValue);
        String sLeft = sValue.substring(0, iPos);
        String sRight = sValue.substring(iPos+1, sValue.length());
        dValue = Double.parseDouble(sLeft);
        if (sRight.length() > 0){
            dDecimalValue = (double) stringToDoubleMap.get(sRight);
            dDecimalValue -= 0.1 ;
            dValue += dDecimalValue;
        }
        return dValue;
    }
    
    private void resequence() {
        String id = jCustIDFld.getText();
        
        int rowcount = tableModel.getRowCount();
        // Object row[][] = new Object[rowcount][tableColCount];
        
        for (int i=0; i < rowcount ; i++){
            tableModel.setValueAt(i+1, i, 0);
        }
//        clearTable(tableModel);
    }
    
//3/4 Lframe,1" Lframe,1" Zframe",2" Zframe,2 1/2" Zframe,3" Zframe
    public void populateFrameSizeList(String mountOpt){
        String frameOMList [] = {"1 1/4\"","1\" Lframe","2\"","2 1/2\""};
        String frameIMList [] = {"3/4 Lframe","1\" Lframe","1\"Zframe","2\" Zframe","2 1/2\" Zframe","3\" Zframe"};
        
        DefaultListModel model = new DefaultListModel();
        // ListModel model = jFrameSizeOutLst.getModel();
        if (mountOpt == "O"){
            for (int i=0; i < frameOMList.length; i++){
               model.addElement(frameOMList[i]);
            }
//            jFrameSizeLst.setModel(model);
        }
        else if (mountOpt == "I"){
            for (int i=0; i < frameIMList.length; i++){
                model.addElement(frameIMList[i]);
            }
        }
        jFrameSizeLst.setModel(model);
    }
    
    public String getFrameSizeKey(){
        String size = "";
        int index = 0;
        ListModel model = jFrameSizeLst.getModel();
        index = jFrameSizeLst.getSelectedIndex();
        if (index == -1){
            JOptionPane.showMessageDialog(null, "A frame size has not been selected.  Please select a frame size.","Error",ERROR_MESSAGE);
            return "";
        }
        else
            return (String) model.getElementAt(index);
    }
    
    public String getColor() {
        
        ListModel model = jColorComboBox.getModel();
        int index = jColorComboBox.getSelectedIndex();
        if (index >= 0)
            return (String) model.getElementAt(index);
        else
            return "";
    }
    
    private String getPanels(){
        String panels = "";
        String sWidth = jWidthFld.getText();
        if (sWidth == null || sWidth.length() == 0){
            return "";
        }
        double width = Double.valueOf(jWidthFld.getText());
        int panelSel = getPanelSelection();
        if (panelSel == 0){
           if (width > 0.0 && width <= 35.0){
               panels = "L";
           }
           else if (width > 35 && width < 71 ){
               panels = "LR";
           }
           else {
               panels = "2L2R";
           }           
        }
        else if (panelSel == 1){
            panels = "L";
        }
        else if (panelSel == 2){
            panels = "LR";
        }
        else if (panelSel == 4){
            panels = "2L2R";
        }
        return panels;
    }
            
    public void resetFields(){
        jWidthFld.setText("");
        jLengthFld.setText("");
        jRailWidthFld.setText("");
        jInstructionFld.setText("");
        jLouverLenFld.setText("");
        jLouverCountFld.setText("");
        jStileLenFld.setText("");
        jBitedFld.setText("");
        jRabitedFld.setText("");
        jHingeFld.setText("");
        jFinishedWidthFld.setText("");
        jFinishedLengthFld.setText("");
        jSillWidthFld.setText("");
        buttonGrpDivSplit.clearSelection();
        //jRadioLouverChoice3.setSelected(true);
        buttonGrpPanel.clearSelection();
        //jRadioRailLen4.setSelected(true); 
        jRadioFrameSillBase.setSelected(false);
        //buttonGroup6.clearSelection();
    }
    private Object [] getTableSelection(){
        Object [] selection = null;
        int index = jDataTable.getSelectedRow();
        int count = tableModel.getColumnCount() ;
        if (index < 0 || count == 0)
            return null;
        Object cols [] = new Object[count];
        
        for (int i=0; i < count; i++){
            cols[i] = tableModel.getValueAt(index, i);
        }
        return cols;
    }
    
    String getWidth(String width){
        double dWidth = Double.parseDouble(width) - 1;
        width = String.valueOf(dWidth);
        return width;
    }
    
    public void selectTableItem(Object cols[]){
        if (cols != null){
            double dWidth = stringToDouble(cols[2].toString());
            dWidth--;
            double dLength = stringToDouble(cols[3].toString());
            dLength--;
            jWidthFld.setText(String.valueOf(dWidth));
            jLengthFld.setText(String.valueOf(dLength));
            jHingeFld.setText(cols[4].toString());
            jRailWidthFld.setText(cols[5].toString());
            jLouverLenFld.setText(cols[6].toString());
            jLouverCountFld.setText(cols[9].toString());
            jStileLenFld.setText(cols[10].toString());
            jBitedFld.setText(cols[11].toString());
            jRabitedFld.setText(cols[12].toString());
            jInstructionFld.setText(cols[17].toString());
            jItemNumber.setText(cols[18].toString());
            jAddBtn.setEnabled(false);   
            jUpdatetBtn.setEnabled(false);
        }
    }
        
    public void insertItem(){
        int index = jDataTable.getSelectedRow();
        if (index > -1){
            setRowData(index,INSERT);
            resequence();
        }
        else
            JOptionPane.showMessageDialog(null,"Please select a location in the list to insert.", "Insertion Error", ERROR_MESSAGE);        
    }
    public void autoSetDecimal(JTextField fldName){
        String sValue = fldName.getText();
        
        int index = sValue.indexOf(".");
        if (index == -1)
            sValue += ".0";
        else {
            String sFraction = sValue.substring(index+1);
            if (sFraction == "")
                sValue += ".0";
        }
        fldName.setText(sValue);                  
    }

    public int getPanelSelection(){
        if (jRadioPanelOne.isSelected()){
            return 1;
        }
        else if (jRadioPanelTwo.isSelected()){
            return 2;
        }
        else if (jRadioPanelFour.isSelected()){
            return 4;
        }
        return 0;
    }
    
    private String getMountingOption(){
            String mountOpt = "";
            
            if (jRadioInside.isSelected())
                mountOpt = "IM";
            else if (jRadioOutside.isSelected())
                mountOpt = "OM";
            else if (jRadioDeadPan.isSelected())
                mountOpt = "DP";
            return mountOpt;
    }
    
    private int getPanelOption(){
        int panel = 0;
        if (jRadioPanelOne.isSelected())
            panel = 1;
        else if (jRadioPanelTwo.isSelected())
            panel = 2;
        else if (jRadioPanelFour.isSelected())
            panel = 4;
        return panel;
    }
    
    private String getLouverOption(){
        String louverOpt="";
        if (jRadioLouverChoice2.isSelected())
            louverOpt = "2 1/2";
        else if (jRadioLouverChoice3.isSelected())
            louverOpt = "3 1/2";
        else if (jRadioLouverChoice4.isSelected())
            louverOpt = "4 1/2";
        
        return louverOpt;
    }
    
    private String getRailString(){
        String rail = jRailWidthFld.getText();
        if (jRadioRailLen4.isSelected())
            rail += " x 4 1/2";
        else if (jRadioRailLen5.isSelected())
            rail += " x 5 1/2";
        
        return rail;
    }
    
    String getDiv50_50(int louverCount, String panel){
        String sResult = "" ;
        int iDiv = louverCount % 2;
        int count1 = 0;
        if (iDiv == 0){ // even
            count1 = (louverCount)/2 - 1;
        }
        else { // odd
            count1 = (louverCount)/2;
        }
        int count2 = (louverCount)/2;
        sResult = String.valueOf(count1);
        sResult += " x ";
        sResult += panel;
        sResult += " | ";
        sResult += String.valueOf(count2) + " x " + panel;
                
        return sResult;
    }
    
    String getDiv30_70(int louverCount, String panel){
        String sResult = "";
        int iDiv = louverCount % 3;
        int count1 = louverCount / 3;
        int count2 = 0;
        if (iDiv == 0){
            count2 = ((louverCount / 3) * 2) - 1;
        }
        else if (iDiv == 1){
            count2 = (louverCount / 3) * 2;
        }
        else if (iDiv == 2){
            count2 = ((louverCount / 3) * 2) + 1;
        }
        
        sResult = String.valueOf(count1);
        sResult += " x ";
        sResult += panel;
        sResult += " | ";
        sResult += String.valueOf(count2) + " x " + panel;
        
        return sResult;
    }
    
    String getSplit50_50(int louverCount, String panel){
         String sResult = "" ;
        int iDiv = louverCount % 2;
        int count1 = count1 = (louverCount)/2;
        int count2 = 0;
        if (iDiv == 0){ // even
            count2 = (louverCount)/2;
        }
        else { // odd
            count2 = (louverCount)/2 +1;
        }
        sResult = "(" + String.valueOf(count1);
        sResult += " x ";
        sResult += panel + ") (";
        sResult += String.valueOf(count2) + " x " + panel + ")";
                
        return sResult;       
    }
    
    public String getLouverCount(int count, int panel){
        //String louverCount = jLouverCountFld.getText();
        //int count = Integer.parseInt(louverCount);
        String louverFmtString = "";
        //int panel = getPanelOption();
        int option = 0;
        int selIndex = jDivSplitList.getSelectedIndex();
        if (jRadioDivider.isSelected())
            option = 1;
        else if (jRadioSplit.isSelected())
            option = 2;
        
        switch (option){
            case 1:  // Divider option
                if (selIndex == 0){ // 50/50
                    louverFmtString = getDiv50_50(count, String.valueOf(panel));
                }
                else if (selIndex == 1){ // 30/70
                    louverFmtString = getDiv30_70(count, String.valueOf(panel));
                }
                break;
            case 2: // Split option
                if (selIndex == 0){ // 50/50
                    louverFmtString = getSplit50_50(count, String.valueOf(panel));
                }
                else if (selIndex == 1){ // 30/70
                    
                }
                break;
            default: // no divider or split
                louverFmtString += String.valueOf(count);
                louverFmtString += " x ";
                louverFmtString += String.valueOf(panel);
        }
        return louverFmtString;
    }
    
    private int getFrameCount(){
        int count = 4;
        if (jRadioFrameSillBase.isSelected())
            count = 3;
        else if (jRadioFrametoFrame.isSelected() || jRadioFrameNormal.isSelected()){
            if (jRadioThreeSides.isSelected())
                count = 3;
            else if (jRadioFourSides.isSelected())
                count = 4;
        }
        return count;
    }
    
    private long getOrderCount(String orderid) throws SQLException{
        long count = 0;
        
        conn = getConnection();
        Statement stmt = conn.createStatement();
        String sql = "Select Count(*) from " + tableName 
                + " where " + ORDERID 
                + " = " + orderid;
        ResultSet rs = stmt.executeQuery(sql);
        rs.first();
        Object item = rs.getObject(1);
        count = (long) item;
        return count;
        
    }
    
    public void removeRow(){
        DefaultTableModel model = (DefaultTableModel) jDataTable.getModel();
        int selectedIndex = jDataTable.getSelectedRow();
        model.removeRow(selectedIndex);
        resequence();
    }
    
synchronized    
    public boolean deleteOrder() throws SQLException{
        Object [] field = getTableSelection();
       conn = getConnection();
       
        if (field != null){
            int custID = Integer.parseInt(jCustIDFld.getText());
            int itemnbr = Integer.parseInt(jItemNumber.getText());
            if (conn != null)
            {
                Statement stmt = conn.createStatement();
                String sql = "DELETE FROM " + orderTable + " where "
                        + col_order_cust_id
                        + "=" + custID
                        + " and "
                        + col_order_item_id
                        + "=" + itemnbr;
                stmt.execute(sql); 
            }
            populateOrderByID(String.valueOf(custID));
        } 
        if (conn != null){
            conn.close();
            conn = null;  
            }
        return true;
    }
 
synchronized    
    public boolean deleteOrder(String customerID, String itemID) throws SQLException{
        //Object [] field = getTableSelection();
       conn = getConnection();
       
       //if (customerID.length() > 0)
        //int custID = Integer.parseInt(customerID);
        //int itemnbr = Integer.parseInt(itemID);
        if (conn != null)
        {
            
            Statement stmt = conn.createStatement();
            String sql = "DELETE FROM " + orderTable + " where "
                    + col_order_cust_id
                    + "=" + customerID
                    + " and "
                    + col_order_item_id
                    + "=" + itemID;
            stmt.execute(sql); 
        }
        if (conn != null){
            conn.close();
            conn = null;  
            }
        return true;
    }

synchronized    
    public boolean deleteOrder(String customerID) throws SQLException{
        //Object [] field = getTableSelection();
       conn = getConnection();
       
       //if (customerID.length() > 0)
        //int custID = Integer.parseInt(customerID);
        //int itemnbr = Integer.parseInt(itemID);
        if (conn != null)
        {
            
            Statement stmt = conn.createStatement();
            String sql = "DELETE FROM " + orderTable + " where "
                    + col_order_cust_id
                    + "=" + customerID;
            stmt.execute(sql); 
        }
        if (conn != null){
            conn.close();
            conn = null;  
            }
        return true;
    }
    
 synchronized   private void updateOrder() throws SQLException {
          conn = getConnection();
        if (conn != null){
            int custid= Integer.parseInt(jCustIDFld.getText());
            String width= jWidthFld.getText();
            String length= jLengthFld.getText();
            String instr = jInstructionFld.getText();
            int itemnbr = Integer.parseInt(jItemNumber.getText());
            
            if (jCustIDFld.getText().length() == 0 || width.length() == 0 || length.length() == 0)
            {
                JOptionPane.showMessageDialog(null,"Required field is empty.");
            }
            else {
                String sql = "UPDATE " + tableName + " "
                        + "set width = " + width  + ", "
                        + "length = " + length + ", "
                        + col_order_instruction + "='" + instr + "'"
                        + " where " 
                        + col_order_cust_id 
                        + "=" + custid
                        + " and "
                        + col_order_item_id
                        + "=" +itemnbr;
                Statement stmt = conn.createStatement();
                boolean bResult = stmt.execute(sql);
                populateOrderByID(String.valueOf(custid));
            }
            if (conn != null){
                conn.close();
                conn = null;  
            }
        }
    }

 synchronized   
    public boolean saveOrder(boolean bInserted) throws SQLException{
        boolean bResult = false;
        conn = getConnection();
        if (conn != null){
            // String orderid= jCustIDFld.getText();
            int bited = 0;
            int rabited = 0;
            String sBited = "";
            String sRabited = "";
            
            String custID= jCustIDFld.getText();
            String width= jWidthFld.getText();
            double dWidth = Double.parseDouble(width);
            width = numToStr(dWidth+1.0625,false);           // convert to fraction
            String length= jLengthFld.getText();
            double dLength = Double.parseDouble(length);
            length = String.valueOf(dLength+1.0);
            String mountOpt = getMountingOption();
            String railString = getRailString();
            int panelOpt = getPanelOption();
            int railQty = panelOpt * 2;
            String louverLen = jLouverLenFld.getText();
            String louverOpt = getLouverOption();
            // String louverCount = getLouverCount();
            String louverCount = jLouverCountFld.getText();
            String stileLen = (jStileLenFld.getText());
            if ((sBited = jBitedFld.getText()).length() > 0)
                bited = Integer.parseInt(jBitedFld.getText());
            if((sRabited = jRabitedFld.getText()).length() > 0)
                rabited = Integer.parseInt(sRabited);
            String hinge = jHingeFld.getText();
            int frameCount = getFrameCount();
            String frameSize = getFrameSizeKey();
            String instruction = jInstructionFld.getText();
            if (custID.length() == 0 || width.length() == 0 || length.length() == 0)
            {
                JOptionPane.showMessageDialog(null,"Please enter required field.");
            }
            else {
                String sql = "Insert INTO " + orderTable + " values(" 
                        + custID + ","
                        + "'" + mountOpt + "',"
                        + "'" + width + "',"
                        + "'" + length + "',"
                        + panelOpt + ","
                        + "'" + railString + "',"
                        + railQty + ","
                        + "'" + louverLen + "',"
                        + "'" + louverOpt + "',"
                        + "'" + louverCount + "',"
                        + "'" + stileLen + "',"
                        + bited + ","
                        + rabited + ","
                        + "'" + hinge + "',"
                        + frameCount + ","
                        + "'" + frameSize + "',"
                        + "'" + instruction + "'" + ","
                        + "null"
                        + ")";
                Statement stmt = conn.createStatement();
                bResult = stmt.execute(sql);
            }
            
            populateOrderByID(custID, bInserted);
            if (conn != null){
                conn.close();
                conn = null;
            }
        }
        return bResult;
    }

 synchronized   
    public boolean saveOrder() throws SQLException{
        boolean bResult = false;
        conn = getConnection();
        if (conn != null){

            String custID= jCustIDFld.getText();

            
            if (custID.length() == 0)
            {
                JOptionPane.showMessageDialog(null,"The invoice number is required.");
            }
            else {
                deleteAll(custID);
                int rowCount = jDataTable.getRowCount();
                int colCount = jDataTable.getColumnCount();
                
                Object data [] = new Object[colCount];
                for (int i=0; i < rowCount; i++){
                    data = getRowData(i);
                    String sql = "Insert INTO " + orderTable + " values(" 
                            + custID + ","
                            + "'" + data[1].toString() + "',"
                            + "'" + data[2].toString() + "',"
                            + "'" + data[3].toString() + "',"
                            + data[4].toString() + ","
                            + "'" + data[5].toString() + "',"
                            + data[6].toString() + ","
                            + "'" + data[7].toString() + "',"
                            + "'" + data[8].toString() + "',"
                            + "'" + data[9].toString() + "',"
                            + "'" + data[10].toString() + "',"
                            + data[11].toString() + ","
                            + data[12].toString() + ","
                            + "'" + data[13].toString() + "',"
                            + "'" + data[14].toString() + "',"
                            + data[15].toString() + ","
                            + "'" + data[16].toString() + "'" + ","
                            + "'" + data[17].toString() + "'" + ","
                            + "null"                            
                            + ")";
                    Statement stmt = conn.createStatement();
                    bResult = stmt.execute(sql);                
                }
                bSaveRequired = false; // Done saving - turn off flag.
            }
            
            if (conn != null){
                conn.close();
                conn = null;
            }
        }
        return bResult;
    }
    
    public boolean deleteAll(String custID){
        boolean bResult= true;
        try {
            // DefaultTableModel model = (DefaultTableModel) jDataTable.getModel();
            // int rowCount = jDataTable.getRowCount();
            //for (int i=0; i < rowCount; i++){
            while (isItemExist(custID)){
                //String itemID = (model.getValueAt(i, 17)).toString();
                if (custID.length() > 0)
                    try {
                        deleteOrder(custID);
                    } catch (SQLException ex) {
                        Logger.getLogger(ShuttersFrame.class.getName()).log(Level.SEVERE, null, ex);
                        JOptionPane.showMessageDialog(null, ex.getMessage());
                    }
                
            }
        } catch (SQLException ex) {            
            Logger.getLogger(ShuttersFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
        return bResult;
    }
    
    
    public boolean isItemExist(String custID, String itemID) throws SQLException{
        boolean bResult = false;
        if (conn == null){
            conn = getConnection();
        }
        Statement stmt = conn.createStatement();
        String sql = "Select " + col_order_item_id + " from " + orderTable + " where " + col_order_cust_id +
                " = " + custID + " AND " + col_order_item_id + " = " + itemID;
        
        ResultSet rs = stmt.executeQuery(sql);
        if (rs.next()){
            Object item = rs.getObject(1);
            if (item.toString() == itemID)
                bResult = true;            
        }
        else
            bResult = false;
        
        return bResult;
    }
    
    public boolean isItemExist(String custID) throws SQLException{
        boolean bResult = false;
        if (conn == null){
            conn = getConnection();
        }
        Statement stmt = conn.createStatement();
        String sql = "Select " + col_order_item_id + " from " + orderTable + " where " + col_order_cust_id +
                " = " + custID;
        
        ResultSet rs = stmt.executeQuery(sql);
        if (rs.next()){
            Object item = rs.getObject(1);
            if (item.toString().length() > 0)
                bResult = true;            
        }

        else
            bResult = false;
        
        return bResult;
    }
    
    public Object [] getRowData(int index){
        //int rowCount = jDataTable.getRowCount();
        int colCount = jDataTable.getColumnCount();
        DefaultTableModel model = (DefaultTableModel) jDataTable.getModel();
        Object row [] = new Object[colCount];
        for (int i=1; i < colCount; i++){  // Do not get the line number
            row[i] = model.getValueAt(index, i);
        }
        return row;
   }
    
    public void setRowData(int index, int type){
        String sBited="";
        String sRabited="";
        int colCount = jDataTable.getColumnCount();
        DefaultTableModel model = (DefaultTableModel) jDataTable.getModel();
        Object row [] = new Object[colCount];
        String data [] = new String[colCount];
        // data[0] = jCustIDFld.getText();
        String mountOpt = getMountingOption();
        data[1] = mountOpt;
        String width = jWidthFld.getText();
        double dWidth = Double.parseDouble(width);
        int panelOpt = getPanelOption();
        String sFrameSzKey = getFrameSizeKey();
        // Inside Mount
        if (mountOpt == "IM"){
            dWidth = getIMWidth(panelOpt,dWidth);
            
//            if (sFrameSzKey == "3/4 Lframe"){
//                if (panelOpt == 2)
//                    dWidth = subtract(dWidth,0.2);
//                else if (panelOpt == 4)
//                    dWidth = subtract(dWidth,0.3);
//            }
//            else
//                dWidth -= 2;
        }
        else {
            dWidth++;
        }
        width = decimalToFraction(dWidth);
        // width = numToStr(dWidth+1.0625);           // convert to fraction
        data[2] = width;
        String length= jLengthFld.getText();
        double dLength = Double.parseDouble(length);
        if (mountOpt == "IM"){
            dLength = getIMLength(dLength);
        }
        else {
            if (jRadioFrameNormal.isSelected()){
                if (jRadioFourSides.isSelected())
                    dLength++;
                else
                    dLength = add(dLength,.4);
            }
            else if (jRadioFrameSillBase.isSelected()){
                dLength = add(dLength,.4);
            }
        }
        length = decimalToFraction(dLength);
        data[3] = length;
        data[4] = String.valueOf(panelOpt);
        String railString = getRailString();
        data[5] = railString;
        int railQty = panelOpt * 2;
        data[6] = String.valueOf(railQty);
        String louverLen = jLouverLenFld.getText();
        data[7] = louverLen;
        String louverOpt = getLouverOption();
        data[8] = louverOpt;
        // String louverCount = getLouverCount();
        String louverCount = jLouverCountFld.getText();
        data[9] = louverCount;
        String stileLen = (jStileLenFld.getText());
        data[10] = stileLen;
        sBited = jBitedFld.getText();
        data[11] = sBited;
        sRabited = jRabitedFld.getText();
        if (sRabited == null || sRabited.length() == 0)
            sRabited = "0";
        data[12] = sRabited;
        String hinge = jHingeFld.getText();
        data[13] = hinge;
        String color = getColor();
        data[14] = color;
        int frameCount = getFrameCount();
        data[15] = String.valueOf(frameCount);
        String frameSize = getFrameSizeKey();
        if (frameSize == null || frameSize.length() == 0)
            return;
        data[16] = frameSize;
        String instruction = jInstructionFld.getText();        
        data[17] = instruction;
        data[18] = "";
        if (index > -1){
            if (type == INSERT)
                model.insertRow(index, data);
            else if (type == UPDATE){
                for (int i=0; i < data.length; i++)
                    model.setValueAt(data[i], index, i);
            }
        }
        else
            model.addRow(data);
        
        bSaveRequired = true;
    }
    
    private void resizeTableColumn(){
        DefaultTableCellRenderer centerRdr = new DefaultTableCellRenderer();
//        DefaultTableCellRenderer boldRdr = new DefaultTableCellHeaderRenderer();
//        Font fontBold = new Font();
        //fontBold
//        boldRdr.setFont(new Font("Arial BOLD", Font.BOLD, 13));
        
        centerRdr.setHorizontalAlignment(DefaultTableCellRenderer.CENTER);
        int colCount = jDataTable.getColumnCount();
        //TableColumn finishedCol = jDataTable.getColumn("Finished Size");
        //jDataTable.removeColumn(finishedCol);
        jDataTable.setRowSelectionAllowed(true);
        TableColumn tblCol[] = new TableColumn[colCount];
        String colName = "";
        for (int i = 1; i < colCount -2 ; i++){
            colName = jDataTable.getColumnName(i);
            tblCol[i] = jDataTable.getColumn(colName);
            tblCol[i].setCellRenderer(centerRdr);
        }
        int width_small = 60;
        int width_med = 70;
        int width_big = 80;
        int width_bigger = 100;
        int w100 = 100;
        int w120 = 120;
        int w150 = 150;
        int w160 = 160;
        TableColumn lineCol = jDataTable.getColumn("Line");
        TableColumn mountCol = jDataTable.getColumn("Mount");
        TableColumn IDCol = jDataTable.getColumn("ID");
        TableColumn widthCol = jDataTable.getColumn("Width");
        TableColumn lengthCol = jDataTable.getColumn("Length");
        TableColumn panelCol = jDataTable.getColumn("Panel");
        TableColumn railCol = jDataTable.getColumn("Rail");
        TableColumn railQtyCol = jDataTable.getColumn("Rail Qty.");
        TableColumn louverLenCol = jDataTable.getColumn("Louver Len.");
        TableColumn louversizeCol = jDataTable.getColumn("Louver Sz.");
        TableColumn louverQtyCol = jDataTable.getColumn("Louver Qty.");
        TableColumn stileLenCol = jDataTable.getColumn("Stile Len.");
        TableColumn bitedCol = jDataTable.getColumn("Bited");
        TableColumn rabitedCol = jDataTable.getColumn("Rabited");
        TableColumn hingeCol = jDataTable.getColumn("Hinge");
        TableColumn frameCountCol = jDataTable.getColumn("# of Frame");
        TableColumn frameSizeCol = jDataTable.getColumn("Frame Size");
        TableColumn colorCol = jDataTable.getColumn("Color");
        TableColumn instructionCol = jDataTable.getColumn("Special Instructions");
        JTableHeader tblHdr = jDataTable.getTableHeader();
        Dimension dim = tblHdr.getSize();
        MultiLineHeaderRenderer renderer = new MultiLineHeaderRenderer();
        double width = dim.getWidth();
        dim.setSize(width, 60.0);
        tblHdr.setSize(dim);
        tblHdr.setPreferredSize(dim);
        //tblHdr.setAlignmentY(TOP_ALIGNMENT);
        tblHdr.setDefaultRenderer(renderer);
        
        // jDataTable.setAlignmentY(CENTER_ALIGNMENT);// AlignmentX(CENTER_ALIGNMENT);
        
        //rect.setBounds(0,0, (int) rectWidth,40);
        //tblHdr.setBounds(rect);
        IDCol.setMaxWidth(0);
        IDCol.setPreferredWidth(0);
        lineCol.setMaxWidth(width_small);
        mountCol.setMaxWidth(width_small);
        railQtyCol.setMaxWidth(width_small);
        panelCol.setMaxWidth(width_small);
        hingeCol.setMaxWidth(width_small);
        bitedCol.setMaxWidth(width_small);
        stileLenCol.setPreferredWidth(width_bigger);
        rabitedCol.setMaxWidth(width_big);
        frameSizeCol.setPreferredWidth(width_bigger);
        railCol.setPreferredWidth(w160);
        louverLenCol.setPreferredWidth(width_bigger);
        colorCol.setPreferredWidth(w120);
        
        //frameCountCol.setHeaderRenderer(renderer);
        frameCountCol.setHeaderValue("Frame" + "\n" + "Count");
        frameCountCol.setPreferredWidth(width_med);
        railQtyCol.setHeaderValue("Rail\nQty.");
        
        louverLenCol.setHeaderValue("Louver\nLength");
        
        louversizeCol.setHeaderValue("Louver\nSize");
        instructionCol.setMaxWidth(280);
        instructionCol.setPreferredWidth(280);
        instructionCol.setHeaderValue("Special\nInstructions");
        
        frameSizeCol.setHeaderValue("Frame\nSize");
        // set multiLineCol
        TextAreaRenderer txtAreaRender = new TextAreaRenderer();
        txtAreaRender.setBorder(null);
        
        louverQtyCol.setPreferredWidth(w160);

        
        
    }

    private void insertItem(ResultSet rs, int selected) throws SQLException{
        //resizeTableColumn();
        tableModel = (DefaultTableModel) jDataTable.getModel();
            // clearTable(tableModel);
            ResultSetMetaData metaData = rs.getMetaData();
            int colCount = metaData.getColumnCount();
            tableColCount = colCount;
            Object row[] = new Object[colCount];
            int line = 1;
            rs.last();
            for (int i=0, j=1; j <= colCount ; i++, j++){
                if (i == 0)
                    row[i] = line;
                else
                    row[i] = (rs.getObject(j));
            }
            tableModel.insertRow(selected,row);
            line++;
            //tableModel.addColumn("Finished Window");
    }

    
    public void moveUp(){
        tableModel = (DefaultTableModel) jDataTable.getModel();
        int selectedIndex = jDataTable.getSelectedRow();
        if (selectedIndex > -1){
            int line = (int) tableModel.getValueAt(selectedIndex, 0);
            if (selectedIndex > 0){
                tableModel.setValueAt(line-1, selectedIndex, 0);
                tableModel.setValueAt(line, selectedIndex-1, 0);
                tableModel.moveRow(selectedIndex, selectedIndex, selectedIndex-1);
                jDataTable.setRowSelectionInterval(selectedIndex-1, selectedIndex-1);
                bSaveRequired = true;
            }            
        }
    }
    
    public void moveDown(){
        tableModel = (DefaultTableModel) jDataTable.getModel();
        int itemCount = tableModel.getRowCount();
        int selectedIndex = jDataTable.getSelectedRow();
        if (selectedIndex > -1){
            int line = (int) tableModel.getValueAt(selectedIndex, 0);
            if (selectedIndex < itemCount - 1){
                tableModel.setValueAt(line+1, selectedIndex, 0);
                tableModel.setValueAt(line, selectedIndex+1, 0);
                tableModel.moveRow(selectedIndex, selectedIndex, selectedIndex+1);
                jDataTable.setRowSelectionInterval(selectedIndex+1, selectedIndex+1);
                bSaveRequired = true;
            }            
        }
    }
    
    public void clearTable(DefaultTableModel tableModel){
        int rowCount = tableModel.getRowCount();
        for (int i= rowCount-1; i >= 0; i-- ){
            tableModel.removeRow(i);
        }
    }
    
    public void populateDataTable(ResultSet rs, boolean bInserted) throws SQLException{
        //resizeTableColumn();
        tableModel = (DefaultTableModel) jDataTable.getModel();
        int selectedIndex = jDataTable.getSelectedRow();
        if (bInserted && selectedIndex == -1){
            JOptionPane.showMessageDialog(null,"Please select a row from the list before insert.");
        }
            if (!bInserted)
                clearTable(tableModel);
            ResultSetMetaData metaData = rs.getMetaData();
            int colCount = metaData.getColumnCount();
            tableColCount = colCount;
            Object row[] = new Object[colCount];
            int line = 1;
            while (rs.next()){
                for (int i=0, j=1; j <= colCount ; i++, j++){
                    if (i == 0)
                        row[i] = line;
                    else
                        row[i] = (rs.getObject(j));
                }
                if (bInserted){
                    if (!rs.isLast())
                        tableModel.addRow(row);
                    else {
                        tableModel.insertRow(selectedIndex, row);
                    }
                        
                }
                else
                    tableModel.addRow(row);
                line++;
             }
            if (bInserted)
                resequence();
            //tableModel.addColumn("Finished Window");
    }
    
    public String getCustIDByName(String custName) throws SQLException{
        int custID = 0;
        conn = getConnection();
        
        if (conn != null){
            String sql = "Select " + col_cust_id + " from " + customerTable + " where " + col_cust_name + "='" + custName + "'";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            rs.next();
            custID =  (int) rs.getObject(1);
        }
        return String.valueOf(custID);
    }
    
    public String getCustNameByID(String custID) throws SQLException{
        String name = "";
        
        conn = getConnection();
        
        if (conn != null){
            String sql = "Select " + col_cust_name + " from " + customerTable + " where " + col_cust_id + "=" + custID;
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            rs.next();
            name =  (String) rs.getObject(1);
        }
        return name;
    }
    
    private void populateCustomerField(){
        try {
            String custName = jNameFld.getText();
            String custID = getCustIDByName(custName);
            jCustIDFld.setText(custID);
        } catch (SQLException ex) {
            Logger.getLogger(ShuttersFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void populateOrderByID(String id) throws SQLException{
        populateOrderByID(id,false);
    }
    public void populateOrderByID(String id, boolean bInserted) throws SQLException{
        //int id = Integer.getInteger(orderid);
         
        conn = getConnection();
        if (conn != null){
//            String sql = "Select "
//                    + ITEMNBR + ", "
//                    + WIDTH + ", " + LENGTH + " from " + tableName + " where orderid='" + orderid + "'" 
//                    + "ORDER BY " + ITEMNBR + " ASC";
            
//            String sql_cust = "Select *"
//                    + " from " + customerTable + " where " + col_cust_id + "='" + id + "'" ;
            
            String sql = "Select *"
                    + " from " + orderTable + " where cust_id='" + id + "'" ;
                    // + "ORDER BY " + ITEMNBR + " ASC";
            
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
//            rs.first();
//            float width = rs.getFloat(WIDTH);
//            float length = rs.getFloat(LENGTH);
//            this.jWidthFld.setText(Float.toString(width));
//            this.jLengthFld.setText(Float.toString(length));
//            rs.refreshRow();
            populateDataTable(rs, bInserted);
            conn.close();
            conn = null;
        }
        
    }
    
    private void startup() throws Exception {
        //To change body of generated methods, choose Tools | Templates.
        jWidthFld.requestFocus();
        jWidthFld.setText("");
        jRadioThreeSides.setEnabled(true);
        jRadioFourSides.setEnabled(true);
        jAddBtn.setEnabled(false);
        jUpdatetBtn.setEnabled(false);
        enableSillFields(false);
        populateFrameSizeList("O");
        getUser();
        initMap();
        resizeTableColumn();
        String sqrFt = getSquareFootage();
        jSqrFtFld.setText(sqrFt);
        populateColorList();
       
        MaskFormatter decFormat = new MaskFormatter("####.#");
        decFormat.setValidCharacters("0123456789");        
        //initDataTable();
    }
    
    public void disableFinishedFields() {
            jFinishedWidthFld.setEnabled(false);
            jFinishedWidthLabel.setEnabled(false);
            jFinishedLengthFld.setEnabled(false);
            jFinishedLengthLabel.setEnabled(false);
    }
    
    public void enableFinishedFields(){
            jFinishedWidthFld.setEnabled(true);
            jFinishedWidthLabel.setEnabled(true);
            jFinishedLengthFld.setEnabled(true);
            jFinishedLengthLabel.setEnabled(true);        
    }
  
    public void enableSillFields(boolean bEnabled){
        jSillLabel.setEnabled(bEnabled);
        jSillWidthFld.setEnabled(bEnabled);
    }
    public void enableStandardFields(){
            jWidthFld.setEnabled(true);
            jLengthFld.setEnabled(true);
    }
    
    public void disableStandardFields(){
            jWidthFld.setEnabled(false);
            jLengthFld.setEnabled(false);
    }
    
    private String getUser() throws Exception{
        String user = "";
        String password = "";
        Node nodeItem;
        DocumentBuilderFactory dbfactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder db;
        try {
            db = dbfactory.newDocumentBuilder();
            File file = new File("auth.xml");
            Document doc = db.parse(file);
            NodeList nodeList = doc.getElementsByTagName("AUTHENTICACTION");
            if (nodeList != null && nodeList.getLength() > 0){
                nodeItem = nodeList.item(0);
                Element elem = (Element)nodeItem;
                user = elem.getElementsByTagName("USERNAME").item(0).getTextContent();
                password = elem.getElementsByTagName("PASSWORD").item(0).getTextContent();
                this.username = user;
                this.password = password;
            }        
        } 
        catch (ParserConfigurationException ex) {
            Logger.getLogger(ShuttersFrame.class.getName()).log(Level.SEVERE, null, ex);
            String message = "Failed login: " + ex.getMessage();
            JOptionPane.showMessageDialog(null,message);
        }
 
                
        return user;
    }
 
    void populateColorList() throws IOException, SAXException{
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DefaultComboBoxModel colorComboModel = new DefaultComboBoxModel();
        ComboBoxModel cbm = jColorComboBox.getModel();
        try {
            DocumentBuilder db = dbFactory.newDocumentBuilder();
            File file = new File("Custom.xml");
            Document doc = db.parse(file);
            NodeList nodeList = doc.getElementsByTagName("COLOR");
            if (nodeList != null){
                for (int i=0; i < nodeList.getLength(); i++){
                    Node nodeItem = nodeList.item(i);
                    Element elem = (Element) nodeItem;
                    String color = elem.getElementsByTagName("Name").item(0).getTextContent();
                    colorComboModel.addElement(color);
                    
                }
                jColorComboBox.setModel(colorComboModel);
            }
        } 
        catch (ParserConfigurationException ex) {
            Logger.getLogger(ShuttersFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private Connection getConnection() throws SQLException {

    Connection conn = null;
    Properties connectionProps = new Properties();
    connectionProps.put("user", this.username);
    connectionProps.put("password", this.password);
     if (this.dbms.equals("mysql")) {
        conn = DriverManager.getConnection(
                   "jdbc:" + this.dbms + "://" +
                   this.serverName +
                   ":" + this.portNumber + "/" + dbName,
                   connectionProps);
    } else if (this.dbms.equals("derby")) {
        conn = DriverManager.getConnection(
                   "jdbc:" + this.dbms + ":" +
                   this.dbName +
                   ";create=true",
                   connectionProps);
    }
    System.out.println("Connected to database");
    return conn;
}
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new ShuttersFrame().setVisible(true);
                } catch (Exception ex) {
                    Logger.getLogger(ShuttersFrame.class.getName()).log(Level.SEVERE, null, ex);
                }
                
            }
            
        });
        
 
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private java.awt.Button button1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.ButtonGroup buttonGroup7;
    private javax.swing.ButtonGroup buttonGrpDivSplit;
    private javax.swing.ButtonGroup buttonGrpFrameSize;
    private javax.swing.ButtonGroup buttonGrpLouverSize;
    private javax.swing.ButtonGroup buttonGrpMeasType;
    private javax.swing.ButtonGroup buttonGrpMount;
    private javax.swing.ButtonGroup buttonGrpPanel;
    private javax.swing.ButtonGroup buttonGrpRailLength;
    private javax.swing.ButtonGroup buttonGrpWindowType;
    private javax.swing.JButton jAddBtn;
    private javax.swing.JTextField jBitedFld;
    private javax.swing.JButton jBtnExit;
    private javax.swing.JButton jCalcBtn;
    private javax.swing.JComboBox jColorComboBox;
    private javax.swing.JTextField jCustIDFld;
    private javax.swing.JTable jDataTable;
    private javax.swing.JButton jDeleteBtn;
    private javax.swing.JDialog jDialog1;
    private javax.swing.JList jDivSplitList;
    private javax.swing.JTextField jFinishedLengthFld;
    private javax.swing.JLabel jFinishedLengthLabel;
    private javax.swing.JTextField jFinishedWidthFld;
    private javax.swing.JLabel jFinishedWidthLabel;
    private javax.swing.JList jFrameSizeLst;
    private javax.swing.JPanel jFrameSizePanel;
    private javax.swing.JTextField jHingeFld;
    private javax.swing.JButton jIMoveUpBtn;
    private javax.swing.JTextField jInstructionFld;
    private javax.swing.JTextField jItemNumber;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JTextField jLengthFld;
    private javax.swing.JLabel jLengthLabel;
    private javax.swing.JTextField jLouverCountFld;
    private javax.swing.JLabel jLouverCountLbl;
    private javax.swing.JTextField jLouverLenFld;
    private javax.swing.JPanel jMeasTypePan;
    private javax.swing.JButton jMoveDownBtn;
    private javax.swing.JTextField jNameFld;
    private javax.swing.JButton jNewBtn;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JPanel jPanelPanel;
    private javax.swing.JButton jPrintInvoiceBtn;
    private javax.swing.JTextField jRabitedFld;
    private javax.swing.JRadioButton jRadioDeadPan;
    private javax.swing.JRadioButton jRadioDivider;
    private javax.swing.JRadioButton jRadioFourSides;
    private javax.swing.JRadioButton jRadioFrameNormal;
    private javax.swing.JRadioButton jRadioFrameSillBase;
    private javax.swing.JRadioButton jRadioFrametoFrame;
    private javax.swing.JRadioButton jRadioInside;
    private javax.swing.JRadioButton jRadioLouverChoice2;
    private javax.swing.JRadioButton jRadioLouverChoice3;
    private javax.swing.JRadioButton jRadioLouverChoice4;
    private javax.swing.JRadioButton jRadioOutside;
    private javax.swing.JRadioButton jRadioPanelFour;
    private javax.swing.JRadioButton jRadioPanelOne;
    private javax.swing.JRadioButton jRadioPanelTwo;
    private javax.swing.JRadioButton jRadioRailLen4;
    private javax.swing.JRadioButton jRadioRailLen5;
    private javax.swing.JRadioButton jRadioShop;
    private javax.swing.JRadioButton jRadioSplit;
    private javax.swing.JRadioButton jRadioStandard;
    private javax.swing.JRadioButton jRadioThreeSides;
    private javax.swing.JRadioButton jRadioWindowFtoF;
    private javax.swing.JRadioButton jRadioWindowStd;
    private javax.swing.JTextField jRailWidthFld;
    private javax.swing.JButton jSaveBtn;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JLabel jSillLabel;
    private javax.swing.JTextField jSillWidthFld;
    private javax.swing.JTextField jSqrFtFld;
    private javax.swing.JTextField jStileLenFld;
    private javax.swing.JButton jUpdatetBtn;
    private javax.swing.JTextField jWidthFld;
    private javax.swing.JLabel jWidthLabel;
    // End of variables declaration//GEN-END:variables


}
