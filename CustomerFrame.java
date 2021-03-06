
import java.sql.Connection;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * 
 * @author ttran
 */
// javax.swing.JFrame
public class CustomerFrame extends  javax.swing.JFrame{

    public static int customerID;
    static final String tableName="orderdetails";
    final String customerTable="customer";
    ShuttersFrame shutterFrm = null;
    /**
     * Creates new form Customer
     */
    public CustomerFrame() {
        try {
            initComponents();
            ShuttersFrame shutterFrm = new ShuttersFrame();
        } catch (Exception ex) {
            Logger.getLogger(CustomerFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jInvoiceFld = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jNameFld = new javax.swing.JTextField();
        jFindBtn = new javax.swing.JButton();
        jOrderBtn = new javax.swing.JButton();
        jExitBtn = new javax.swing.JButton();
        jAddBtn = new javax.swing.JButton();

        setTitle("Pacific Wholesale Shutters and Blinds");
        setIconImage(getIconImage());

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel1.setText("Invoice Number:");

        jInvoiceFld.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel3.setText("Customer Name:");

        jNameFld.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N

        jFindBtn.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jFindBtn.setText("Find");
        jFindBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jFindBtnActionPerformed(evt);
            }
        });

        jOrderBtn.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jOrderBtn.setText("Order Details");
        jOrderBtn.setEnabled(false);
        jOrderBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jOrderBtnActionPerformed(evt);
            }
        });

        jExitBtn.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jExitBtn.setText("Exit");
        jExitBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jExitBtnActionPerformed(evt);
            }
        });

        jAddBtn.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jAddBtn.setText("Add");
        jAddBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jAddBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(129, 129, 129)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 123, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jOrderBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jExitBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(layout.createSequentialGroup()
                            .addGap(11, 11, 11)
                            .addComponent(jInvoiceFld, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addComponent(jAddBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGap(18, 18, 18)
                            .addComponent(jFindBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(jNameFld, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 373, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(355, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(64, 64, 64)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jFindBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(16, 16, 16))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jAddBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jInvoiceFld)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jNameFld, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(80, 80, 80)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jOrderBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jExitBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(145, Short.MAX_VALUE))
        );

        jExitBtn.getAccessibleContext().setAccessibleName("");
        jExitBtn.getAccessibleContext().setAccessibleParent(null);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jExitBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jExitBtnActionPerformed
        System.exit(0);
    }//GEN-LAST:event_jExitBtnActionPerformed

    public int getCustomerID(){
        int custID = this.customerID;
        return custID;
    }
    
    public void setCustomerID(int custID){
        this.customerID = custID;
    }
    
    private void jOrderBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jOrderBtnActionPerformed
        try {
//            if (shutterFrm == null)
//             shutterFrm= new ShuttersFrame();
            launchOrderDetails();
            int custID = Integer.valueOf(jInvoiceFld.getText());
            String custName = jNameFld.getText();
            shutterFrm.setCustID(custID);
            shutterFrm.setCustName(custName);
            shutterFrm.populateOrderByID(String.valueOf(custID));
        } catch (Exception ex) {
            Logger.getLogger(CustomerFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jOrderBtnActionPerformed

    public boolean addCustomer(int custID, String custName){
        boolean bResult = true;
        Connection conn = null;
        MySqlConnection mySqlConn = new MySqlConnection();
        try {
            mySqlConn.getUser();
            conn = mySqlConn.getConnection();
            if (conn != null){
                Statement stmt = conn.createStatement();
                String sql = "INSERT INTO " + customerTable + " values("
                            + custID + ","
                            + "'" + custName + "'" + ")";
                stmt.execute(sql);
            }
        } catch (Exception ex) {
            Logger.getLogger(CustomerFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
        return bResult;
    }
    public String findCustomerByID(){
        String custName = "";
        String custID = jInvoiceFld.getText();
        try {
            
            if (shutterFrm == null)
                shutterFrm = new ShuttersFrame();
            custName = shutterFrm.getCustNameByID(custID);
        } catch (Exception ex) {
            Logger.getLogger(CustomerFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
        return custName;
    }
    
    private void jFindBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jFindBtnActionPerformed
        String name = findCustomerByID();
        if (name.length() > 0){
            jNameFld.setText(name);
            jOrderBtn.setEnabled(true);
        }
    }//GEN-LAST:event_jFindBtnActionPerformed

    private void jAddBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jAddBtnActionPerformed
        int custID = Integer.parseInt(jInvoiceFld.getText());
        String custName = jNameFld.getText();
        boolean bResult = addCustomer(custID, custName);
        if (bResult){
            jOrderBtn.setEnabled(bResult);
        }
    }//GEN-LAST:event_jAddBtnActionPerformed

    public void launchOrderDetails(){
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    if (shutterFrm != null)
                    {
                        shutterFrm.setVisible(true);
                    }
                } catch (Exception ex) {
                    Logger.getLogger(ShuttersFrame.class.getName()).log(Level.SEVERE, null, ex);
                }
                
            }    
        });
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
            java.util.logging.Logger.getLogger(CustomerFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(CustomerFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(CustomerFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CustomerFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                CustomerFrame custFrm = new CustomerFrame();
                        custFrm.setVisible(true);
                                //= new CustomerFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jAddBtn;
    private javax.swing.JButton jExitBtn;
    private javax.swing.JButton jFindBtn;
    private javax.swing.JTextField jInvoiceFld;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JTextField jNameFld;
    private javax.swing.JButton jOrderBtn;
    // End of variables declaration//GEN-END:variables
}
