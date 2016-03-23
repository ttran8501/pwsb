
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author ttran
 */
public class MySqlConnection {
        //SQL settings
    static final String dbms="mysql";
    static final String serverName="localhost";
    static final String portNumber="3306";
    static final String dbName="pwsb";
    static final String tableName="orderdetails";
    final String customerTable="customer";
    static final String orderTable="orderdetails";
    private String username;
    private String password;    
    static Connection conn = null;
    public MySqlConnection() {
    }
    
    public Connection getConnection() throws SQLException {

        //Connection conn = null;
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
    
    public String getUser() throws Exception{
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
}
