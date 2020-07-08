/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Form;


import java.awt.Dimension;
import java.awt.Toolkit;
import java.sql.SQLException;


public class SelectSever extends javax.swing.JFrame {
public static boolean a;
    public SelectSever() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenHeight = screenSize.height;
        int screenWidth = screenSize.width;
        setUndecorated(true);
        setResizable(false);
        setLocation((screenWidth - 730) / 2, (screenHeight - 460) / 2);
        initComponents();

    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        NAME_GAME = new javax.swing.JLabel();
        bntLocal = new javax.swing.JButton();
        bntOnline = new javax.swing.JButton();
        background = new javax.swing.JLabel();

        jLabel1.setText("jLabel1");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("    X");
        jLabel4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jLabel4MousePressed(evt);
            }
        });
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 4, 40, 30));

        jLabel2.setFont(new java.awt.Font("Verdana", 1, 36)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("   Sever");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 100, 170, 50));

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel3.setText("  Vui lòng chọn sever");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 150, 160, -1));

        NAME_GAME.setFont(new java.awt.Font("Arial", 3, 24)); // NOI18N
        NAME_GAME.setForeground(new java.awt.Color(255, 255, 255));
        NAME_GAME.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        NAME_GAME.setText("Diệt Trừ Virut CORONA");
        jPanel1.add(NAME_GAME, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 60, 290, 100));

        bntLocal.setBackground(new java.awt.Color(0, 185, 144));
        bntLocal.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        bntLocal.setText("Local");
        bntLocal.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                bntLocalMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                bntLocalMousePressed(evt);
            }
        });
        jPanel1.add(bntLocal, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 243, 90, 30));

        bntOnline.setBackground(new java.awt.Color(0, 185, 144));
        bntOnline.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        bntOnline.setText("Cloud");
        bntOnline.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                bntOnlineMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                bntOnlineMousePressed(evt);
            }
        });
        bntOnline.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bntOnlineActionPerformed(evt);
            }
        });
        jPanel1.add(bntOnline, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 243, 90, 30));

        background.setIcon(new javax.swing.ImageIcon("D:\\HK2\\ADF-II\\BT\\AppQtri\\src\\Image\\LoginForm.png")); // NOI18N
        jPanel1.add(background, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 730, 460));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void bntOnlineActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bntOnlineActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_bntOnlineActionPerformed

    private void bntLocalMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bntLocalMouseClicked

    }//GEN-LAST:event_bntLocalMouseClicked

    private void bntOnlineMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bntOnlineMouseClicked

    }//GEN-LAST:event_bntOnlineMouseClicked

    private void bntLocalMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bntLocalMousePressed
        // TODO add your handling code here:

        java.awt.EventQueue.invokeLater(() -> {
            new LoginForm().setVisible(true);
        });
        setVisible(false);
        a=true;
    }//GEN-LAST:event_bntLocalMousePressed

    private void bntOnlineMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bntOnlineMousePressed

        java.awt.EventQueue.invokeLater(() -> {
            new LoginForm().setVisible(true);
        });
        setVisible(false);
          a=false;
    }//GEN-LAST:event_bntOnlineMousePressed

    private void jLabel4MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel4MousePressed
        
        System.exit(0);
    }//GEN-LAST:event_jLabel4MousePressed

  
    

    public static void main(String args[]) throws SQLException {

        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(SelectSever.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        java.awt.EventQueue.invokeLater(() -> {
            new SelectSever().setVisible(true);
        });

    }
   

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel NAME_GAME;
    private javax.swing.JLabel background;
    private javax.swing.JButton bntLocal;
    private javax.swing.JButton bntOnline;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables
}
