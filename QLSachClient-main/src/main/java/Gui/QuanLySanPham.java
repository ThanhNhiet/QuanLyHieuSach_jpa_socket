/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package Gui;

import java.awt.PopupMenu;
import java.net.Socket;

/**
 *
 * @author Admin
 */
public class QuanLySanPham extends javax.swing.JPanel {
	private Socket socket;
    /**
     * Creates new form nenTC
     */
    public QuanLySanPham(Socket socket) {
    	this.socket = socket;
        initComponents();
        jPanel2.removeAll();
        Tab_Sach tab_Sach = new Tab_Sach(socket);
        jPanel2.removeAll();
        jPanel2.add(tab_Sach);
        jPanel2.revalidate();
        jPanel2.repaint();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenu2 = new javax.swing.JMenu();
        jMenu3 = new javax.swing.JMenu();
        jMenuBar2 = new javax.swing.JMenuBar();
        jMenu4 = new javax.swing.JMenu();
        jMenu5 = new javax.swing.JMenu();
        jMenuBar3 = new javax.swing.JMenuBar();
        jMenu6 = new javax.swing.JMenu();
        jMenu7 = new javax.swing.JMenu();
        jMenu8 = new javax.swing.JMenu();
        jMenuBar4 = new javax.swing.JMenuBar();
        jMenu9 = new javax.swing.JMenu();
        jMenu10 = new javax.swing.JMenu();
        jMenuBar5 = new javax.swing.JMenuBar();
        jMenu11 = new javax.swing.JMenu();
        jMenu12 = new javax.swing.JMenu();
        jMenuBar6 = new javax.swing.JMenuBar();
        jMenu13 = new javax.swing.JMenu();
        jMenu14 = new javax.swing.JMenu();
        popupMenu1 = new java.awt.PopupMenu();
        jPopupMenu1 = new javax.swing.JPopupMenu();
        jMenuItem2 = new javax.swing.JMenuItem();
        jInternalFrame1 = new javax.swing.JInternalFrame();
        jPanel2 = new javax.swing.JPanel();
        jMenuBar7 = new javax.swing.JMenuBar();
        jMenu17 = new javax.swing.JMenu();
        jMenu15 = new javax.swing.JMenu();

        jMenuItem1.setText("jMenuItem1");

        jMenu1.setText("File");
        jMenuBar1.add(jMenu1);

        jMenu2.setText("Edit");
        jMenuBar1.add(jMenu2);

        jMenu3.setText("jMenu3");

        jMenu4.setText("File");
        jMenuBar2.add(jMenu4);

        jMenu5.setText("Edit");
        jMenuBar2.add(jMenu5);

        jMenu6.setText("File");
        jMenuBar3.add(jMenu6);

        jMenu7.setText("Edit");
        jMenuBar3.add(jMenu7);

        jMenu8.setText("jMenu8");

        jMenu9.setText("File");
        jMenuBar4.add(jMenu9);

        jMenu10.setText("Edit");
        jMenuBar4.add(jMenu10);

        jMenu11.setText("File");
        jMenuBar5.add(jMenu11);

        jMenu12.setText("Edit");
        jMenuBar5.add(jMenu12);

        jMenu13.setText("File");
        jMenuBar6.add(jMenu13);

        jMenu14.setText("Edit");
        jMenuBar6.add(jMenu14);

        popupMenu1.setLabel("popupMenu1");

        jMenuItem2.setText("jMenuItem2");

        setBackground(new java.awt.Color(255, 255, 255));
        setPreferredSize(new java.awt.Dimension(1040, 550));
        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jInternalFrame1.setVisible(true);
        jInternalFrame1.getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        jInternalFrame1.getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1260, 520));

        jMenuBar7.setBackground(new java.awt.Color(0, 0, 0));

        jMenu17.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/book.png"))); // NOI18N
        jMenu17.setText("Sách");
        jMenu17.addMenuListener(new javax.swing.event.MenuListener() {
            public void menuCanceled(javax.swing.event.MenuEvent evt) {
            }
            public void menuDeselected(javax.swing.event.MenuEvent evt) {
            }
            public void menuSelected(javax.swing.event.MenuEvent evt) {
                jMenu17MenuSelected(evt);
            }
        });
        jMenuBar7.add(jMenu17);

        jMenu15.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/pen24.png"))); // NOI18N
        jMenu15.setText("Văn phòng phẩm");
        jMenu15.addMenuListener(new javax.swing.event.MenuListener() {
            public void menuCanceled(javax.swing.event.MenuEvent evt) {
            }
            public void menuDeselected(javax.swing.event.MenuEvent evt) {
            }
            public void menuSelected(javax.swing.event.MenuEvent evt) {
                jMenu15MenuSelected(evt);
            }
        });
        jMenuBar7.add(jMenu15);

        jInternalFrame1.setJMenuBar(jMenuBar7);

        add(jInternalFrame1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1300, 650));
    }// </editor-fold>//GEN-END:initComponents


    private void jMenu17MenuSelected(javax.swing.event.MenuEvent evt) {//GEN-FIRST:event_jMenu17MenuSelected
        // TODO add your handling code here:
        Tab_Sach tab_Sach = new Tab_Sach(socket);
        jPanel2.removeAll();
        jPanel2.add(tab_Sach);
        jPanel2.revalidate();
        jPanel2.repaint();
    }//GEN-LAST:event_jMenu17MenuSelected

    private void jMenu15MenuSelected(javax.swing.event.MenuEvent evt) {//GEN-FIRST:event_jMenu15MenuSelected
        // TODO add your handling code here:
        Tab_VPP tab_VPP = new Tab_VPP(socket);
        jPanel2.removeAll();
        jPanel2.add(tab_VPP);
        jPanel2.revalidate();
        jPanel2.repaint();
    }//GEN-LAST:event_jMenu15MenuSelected


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JInternalFrame jInternalFrame1;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu10;
    private javax.swing.JMenu jMenu11;
    private javax.swing.JMenu jMenu12;
    private javax.swing.JMenu jMenu13;
    private javax.swing.JMenu jMenu14;
    private javax.swing.JMenu jMenu15;
    private javax.swing.JMenu jMenu17;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenu jMenu5;
    private javax.swing.JMenu jMenu6;
    private javax.swing.JMenu jMenu7;
    private javax.swing.JMenu jMenu8;
    private javax.swing.JMenu jMenu9;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuBar jMenuBar2;
    private javax.swing.JMenuBar jMenuBar3;
    private javax.swing.JMenuBar jMenuBar4;
    private javax.swing.JMenuBar jMenuBar5;
    private javax.swing.JMenuBar jMenuBar6;
    private javax.swing.JMenuBar jMenuBar7;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPopupMenu jPopupMenu1;
    private java.awt.PopupMenu popupMenu1;
    // End of variables declaration//GEN-END:variables

}
