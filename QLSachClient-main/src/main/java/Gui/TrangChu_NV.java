/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Gui;

import java.awt.event.ActionEvent;
import java.net.Socket;
import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

/**
 *
 * @author LENOVO
 */
public class TrangChu_NV extends javax.swing.JFrame {
    private Socket socket;
    /**
     * Creates new form TrangChu
     */
    public TrangChu_NV(Socket socket) {
        initComponents();
        this.socket = socket;
        trangNen.removeAll();
        TrangNen tn = new TrangNen();
        trangNen.setLayout(this.getLayout());
        trangNen.add(tn);
        trangNen.revalidate();
        setupKeyboardShortcuts_TrangChuQL();
    }
    public void phanQuyenNhanVien(){
        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new JPanel();
        jPanel3 = new JPanel();
        jLabel2 = new javax.swing.JLabel();
//        jPanel6 = new JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jPanel10 = new JPanel();
        jLabel8 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jPanel4 = new JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jPanel8 = new JPanel();
        jLabel6 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jPanel13 = new JPanel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
//        jPanel11 = new JPanel();
        jLabel9 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        trangNen = new JPanel();
        jPanel2 = new JPanel();
        jLabel19 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(255, 255, 153));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setIcon(new javax.swing.ImageIcon("src/main/java/Img/hinh-nen.jpg")); // NOI18N

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel2)
        );

        jPanel1.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

//        jPanel6.setBackground(new java.awt.Color(204, 204, 204));
//        jPanel6.setForeground(new java.awt.Color(204, 204, 204));
//        jPanel6.addMouseListener(new java.awt.event.MouseAdapter() {
//            public void mouseClicked(java.awt.event.MouseEvent evt) {
//                jPanel6MouseClicked(evt);
//            }
//        });
//        jPanel6.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel4.setIcon(new javax.swing.ImageIcon("src/main/java/Img/book.png")); // NOI18N
        jLabel4.setText("Quản lý sản phẩm");
//        jPanel6.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 160, 30));
//
//        jLabel10.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
//        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
//        jLabel10.setText("F2");
//        jPanel6.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 10, 30, 30));
//
//        jPanel1.add(jPanel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 250, 250, 50));

        jPanel10.setBackground(new java.awt.Color(204, 204, 204));
        jPanel10.setForeground(new java.awt.Color(204, 204, 204));
        jPanel10.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel10MouseClicked(evt);
            }
        });
        jPanel10.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel8.setIcon(new javax.swing.ImageIcon("src/main/java/Img/BanHang_20px.png")); // NOI18N
        jLabel8.setText("Quản lý bán hàng");
        jPanel10.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 170, 30));

        jLabel14.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setText("F3");
        jPanel10.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 10, 30, 30));

        jPanel1.add(jPanel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 330, 250, 50));

        jPanel4.setBackground(new java.awt.Color(204, 204, 204));
        jPanel4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel4MouseClicked(evt);
            }
        });
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel3.setIcon(new javax.swing.ImageIcon("src/main/java/Img/home2.png")); // NOI18N
        jLabel3.setText("Trang chủ");
        jPanel4.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 170, 30));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("F1");
        jPanel4.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 10, 30, 30));

        jPanel1.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 170, 250, 50));

        jLabel18.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel18.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel18.setIcon(new javax.swing.ImageIcon("src/main/java/Img/Login-icon-16.png")); // NOI18N
        jLabel18.setText(" Đăng xuất");
        jLabel18.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel18MouseClicked(evt);
            }
        });
        jPanel1.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 650, 250, 30));

        jPanel8.setBackground(new java.awt.Color(204, 204, 204));
        jPanel8.setForeground(new java.awt.Color(204, 204, 204));
        jPanel8.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel8MouseClicked(evt);
            }
        });
        jPanel8.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel6.setIcon(new javax.swing.ImageIcon("src/main/java/Img/khach-hang.png")); // NOI18N
        jLabel6.setText("Quản lý khách hàng");
        jPanel8.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 170, 30));

        jLabel12.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setText("F5");
        jPanel8.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 10, 30, 30));

        jPanel1.add(jPanel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 490, -1, 50));

        jPanel13.setBackground(new java.awt.Color(204, 204, 204));
        jPanel13.setForeground(new java.awt.Color(204, 204, 204));
        jPanel13.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel13MouseClicked(evt);
            }
        });
        jPanel13.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel20.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel20.setIcon(new javax.swing.ImageIcon("src/main/java/Img/bill.png")); // NOI18N
        jLabel20.setText("Quản lý hóa đơn");
        jPanel13.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 170, 30));

        jLabel21.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(255, 255, 255));
        jLabel21.setText("F4");
        jPanel13.add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 10, 30, 30));

        jPanel1.add(jPanel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 410, 250, 50));

//        jPanel11.setBackground(new java.awt.Color(204, 204, 204));
//        jPanel11.setForeground(new java.awt.Color(204, 204, 204));
//        jPanel11.addMouseListener(new java.awt.event.MouseAdapter() {
//            public void mouseClicked(java.awt.event.MouseEvent evt) {
//                jPanel11MouseClicked(evt);
//            }
//        });
//        jPanel11.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
//
//        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
//        jLabel9.setIcon(new javax.swing.ImageIcon("src/main/java/Img/thong-ke.png")); // NOI18N
//        jLabel9.setText("Quản lý thống kê");
//        jPanel11.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 170, 30));
//
//        jLabel15.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
//        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
//        jLabel15.setText("F6");
//        jPanel11.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 10, 30, 30));
//
//        jPanel1.add(jPanel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 570, 250, 50));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 250, 700));

        trangNen.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        getContentPane().add(trangNen, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 50, 1300, 650));

        jPanel2.setBackground(new java.awt.Color(255, 255, 153));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel19.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel19.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel19.setText("PHẦN MỀM QUẢN LÝ HIỆU SÁCH LUCKY");
        jPanel2.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1300, 50));

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 0, 1300, 50));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jPanel4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel4MouseClicked
        // TODO add your handling code here:
        trangNen.removeAll();
        TrangNen tn = new TrangNen();
        trangNen.setLayout(this.getLayout());
        trangNen.add(tn);
        trangNen.revalidate();
    }//GEN-LAST:event_jPanel4MouseClicked

//    private void jPanel6MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel6MouseClicked
//        // TODO add your handling code here:
//        trangNen.removeAll();
//        QuanLySanPham QuanLySanPham = new QuanLySanPham();
//        trangNen.setLayout(this.getLayout());
//        trangNen.add(QuanLySanPham);
//        trangNen.revalidate();
//    }//GEN-LAST:event_jPanel6MouseClicked

    private void jPanel8MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel8MouseClicked
        // TODO add your handling code here:
        trangNen.removeAll();
        QuanLyKhachHang QuanLyKhachHang = new QuanLyKhachHang(socket);
        trangNen.setLayout(this.getLayout());
        trangNen.add(QuanLyKhachHang);
        trangNen.revalidate();
    }//GEN-LAST:event_jPanel8MouseClicked

    private void jPanel10MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel10MouseClicked
        // TODO add your handling code here:        
        trangNen.removeAll();
        QuanLyBanHang QuanLyBanHang = new QuanLyBanHang(socket);
        trangNen.setLayout(this.getLayout());
        trangNen.add(QuanLyBanHang);
        trangNen.revalidate();
    }//GEN-LAST:event_jPanel10MouseClicked

    private void jPanel13MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel13MouseClicked
        // TODO add your handling code here:
        trangNen.removeAll();
        QuanLyHoaDon QuanLyHoaDon = new QuanLyHoaDon(socket);
        trangNen.setLayout(this.getLayout());
        trangNen.add(QuanLyHoaDon);
        trangNen.revalidate();
    }//GEN-LAST:event_jPanel13MouseClicked

    private void jLabel18MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel18MouseClicked
        // TODO add your handling code here:
        Login lg = new Login(socket);
        lg.setVisible(true);
        lg.setLocationRelativeTo(null);
        dispose();
    }//GEN-LAST:event_jLabel18MouseClicked

//    private void jPanel11MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel11MouseClicked
//        // TODO add your handling code here:
//        trangNen.removeAll();
//        QuanLyThongKe QuanLyThongKe = new QuanLyThongKe();
//        trangNen.setLayout(this.getLayout());
//        trangNen.add(QuanLyThongKe);
//        trangNen.revalidate();
//    }//GEN-LAST:event_jPanel11MouseClicked

     private void setupKeyboardShortcuts_TrangChuQL() {

        // F1 shortcut
        setupShortcut("F1", "performShortcutF1", () -> handleF1Shortcut());

        // F2 shortcut
        setupShortcut("F2", "performShortcutF2", () -> handleF2Shortcut());

        // F3 shortcut
        setupShortcut("F3", "performShortcutF3", () -> handleF3Shortcut());

        // F4 shortcut
        setupShortcut("F4", "performShortcutF4", () -> handleF4Shortcut());

        // F5 shortcut
        setupShortcut("F5", "performShortcutF5", () -> handleF5Shortcut());

        // F6 shortcut
        setupShortcut("F6", "performShortcutF6", () -> handleF6Shortcut());
    }

    private void setupShortcut(String keyStroke, String actionName, Runnable action) {
        KeyStroke ks = KeyStroke.getKeyStroke(keyStroke);
        InputMap inputMap = trangNen.getInputMap(JPanel.WHEN_IN_FOCUSED_WINDOW);
        ActionMap actionMap = trangNen.getActionMap();
        int condition = JComponent.WHEN_IN_FOCUSED_WINDOW;

        inputMap.put(ks, actionName);
        actionMap.put(actionName, new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                action.run();
            }
        });
    }

    private void handleF1Shortcut() {
        // TODO: Add handling code for F1 shortcut
        trangNen.removeAll();
        setLocationRelativeTo(null);
        setupTrangChuComponents();
        trangNen.revalidate();
        trangNen.repaint();
    }

    private void setupTrangChuComponents() {
        TrangNen tn = new TrangNen();
        trangNen.setLayout(this.getLayout());
        trangNen.add(tn);
        trangNen.revalidate();
    }


    private void handleF2Shortcut() {
        // TODO: Add handling code for F2 shortcut
//        trangNen.removeAll();
//        QuanLySanPham QuanLySanPham = new QuanLySanPham();
//        trangNen.add(QuanLySanPham);
//        setLocationRelativeTo(null);
//        trangNen.revalidate();
    }

    private void handleF3Shortcut() {
        // TODO: Add handling code for F2 shortcut
        trangNen.removeAll();
        QuanLyBanHang QuanLyBanHang = new QuanLyBanHang(socket);
        trangNen.add(QuanLyBanHang);
        setLocationRelativeTo(null);
        trangNen.revalidate();
    }

    private void handleF4Shortcut() {
        // TODO: Add handling code for F2 shortcut
        trangNen.removeAll();
        QuanLyHoaDon QuanLyHoaDon = new QuanLyHoaDon(socket);
        trangNen.add(QuanLyHoaDon);
        setLocationRelativeTo(null);
        trangNen.revalidate();
    }

    private void handleF5Shortcut() {
        // TODO: Add handling code for F2 shortcut
        trangNen.removeAll();
        QuanLyKhachHang QuanLyKhachHang = new QuanLyKhachHang(socket);
        trangNen.add(QuanLyKhachHang);
        setLocationRelativeTo(null);
        trangNen.revalidate();
    }

    private void handleF6Shortcut() {
        // TODO: Add handling code for F2 shortcut
//        trangNen.removeAll();
//        QuanLyThongKe QuanLyThongKe = new QuanLyThongKe();
//        trangNen.add(QuanLyThongKe);
//        setLocationRelativeTo(null);
//        trangNen.revalidate();
    }
    /**
     * @param args the command line arguments
     */
//    public static void main(String args[]) {
//        /* Set the Nimbus look and feel */
//        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
//        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
//         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
//         */
//        try {
//            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
//                if ("Nimbus".equals(info.getName())) {
//                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
//                    break;
//                }
//            }
//        } catch (ClassNotFoundException ex) {
//            java.util.logging.Logger.getLogger(TrangChu_QL.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (InstantiationException ex) {
//            java.util.logging.Logger.getLogger(TrangChu_QL.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (IllegalAccessException ex) {
//            java.util.logging.Logger.getLogger(TrangChu_QL.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
//            java.util.logging.Logger.getLogger(TrangChu_QL.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        }
//        //</editor-fold>
//        //</editor-fold>
//        //</editor-fold>
//        //</editor-fold>
//
//        /* Create and display the form */
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                TrangChu_QL tc_ql = new TrangChu_QL();
//                tc_ql.setVisible(true);
//                tc_ql.setTitle("Cửa hàng sách LucKy ");
//            }
//        });
//    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private JPanel jPanel1;
    private JPanel jPanel10;
//    private JPanel jPanel11;
    private JPanel jPanel13;
    private JPanel jPanel2;
    private JPanel jPanel3;
    private JPanel jPanel4;
//    private JPanel jPanel6;
    private JPanel jPanel8;
    private JPanel trangNen;
    // End of variables declaration//GEN-END:variables
}
