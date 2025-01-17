/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package Gui;

import dao.ChiTietHoaDonDAO;
import dao.HoaDonDAO;
import dao.KhachHangDAO;
import dao.NhanVienDAO;
import entity.HoaDon;
import entity.KhachHang;
import entity.NhanVien;
import entity.Sach;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.net.Socket;
import java.sql.Date;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Scanner;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.InstanceCreator;
import com.google.gson.reflect.TypeToken;

/**
 *
 * @author LENOVO
 */
public class Tab_HoaDon extends javax.swing.JPanel {
	private Socket socket;
	PrintWriter out;
	Scanner sc;

	/**
	 * Creates new form Tab_S
	 * 
	 * /** Creates new form QuanLyThongKe
	 */
	public Tab_HoaDon(Socket socket) {
		this.socket = socket;
		try {
			out = new PrintWriter(socket.getOutputStream(), true);
			sc = new Scanner(socket.getInputStream());
		} catch (Exception e) {
			e.printStackTrace();
		}
		initComponents();
		loadTblHoaDon();
	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */
	@SuppressWarnings("unchecked")
	public void clearTableHoaDon() {
		DefaultTableModel dtm = (DefaultTableModel) jTableDSHD.getModel();
		dtm.setRowCount(0);
	}

	// load table phiếu nhập
	public void loadTblHoaDon() {
		clearTableHoaDon();
		double tongTien = 0;
		DefaultTableModel dtm = (DefaultTableModel) jTableDSHD.getModel();
		try {
			out.println("QLHD");
			out.println("getAll");
			String listObject = sc.nextLine();
			List<HoaDon> dshd = new Gson().fromJson(listObject, new TypeToken<List<HoaDon>>() {
			}.getType());
			String listObjectTT = sc.nextLine();
			double[] listTongTien = new Gson().fromJson(listObjectTT, new TypeToken<double[]>() {
			}.getType());
			int index = 0;
			for (HoaDon hoaDon : dshd) {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				String date = sdf.format(hoaDon.getNgayLapHD());
				Object[] rowData = { hoaDon.getMaHD(), date, hoaDon.getNhanVien().getTenNhanVien(),
						hoaDon.getKhachHang().getTenKhachHang(), listTongTien[index]};
				dtm.addRow(rowData);
				tongTien = listTongTien[index] + tongTien;
				index++;

			}
			jLabelTongHD.setText(NumberFormat.getInstance().format(index));
			jLabelTongDT.setText(NumberFormat.getInstance().format(tongTien));
		} catch (Exception e) {
			e.printStackTrace();
		}
		out.flush();
	}

//    private void tableHoaDonTheoTime() {
//        clearTableHoaDon();
//
//        String tenNhanVien = jComboBoxNV.getSelectedItem().toString();
//        if (tenNhanVien.equals("Tất cả")) {
//            tenNhanVien = "";
//        }
//
//        String tenKhachHang = jComboBoxKH.getSelectedItem().toString();
//        if (tenKhachHang.equals("Tất cả")) {
//            tenKhachHang = "";
//        }
//
//        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
//        String from = formatter.format(jDateChooserFrom.getDate());
//        String to = formatter.format(jDateChooserTo.getDate());
//        Date tuNgay = Date.valueOf(from);
//        Date denNgay = Date.valueOf(to);
//
//        ArrayList<HoaDon> listHoaDon = hoaDon_DAO.getAllHoaDon(tenKhachHang, tenNhanVien, tuNgay, denNgay);
//
//        int tongHoaDon = listHoaDon.size();
//        double tongThanhTien = 0;
//
//        DefaultTableModel dtm = (DefaultTableModel) jTableDSHD.getModel();
//        for (HoaDon hd : listHoaDon) {
//            double tienHoaDon = hd.tongTien();
//            double thue = tienHoaDon * 0.2;
//            double thanhTien = tienHoaDon - thue;
//            tongThanhTien += thanhTien;
//            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//            String date = sdf.format(hd.getNgayLapHD());
//            Object[] rowData = {hd.getMaHD(), date, hd.getNhanVien().getTenNhanVien(), hd.getKhachHang().getTenKhachHang(), NumberFormat.getInstance().format(tienHoaDon), NumberFormat.getInstance().format(thue), NumberFormat.getInstance().format(thanhTien)};
//            dtm.addRow(rowData);
//        }
//
//        jLabelTongDT.setText(NumberFormat.getInstance().format(tongThanhTien));
//        jLabelTongHD.setText(NumberFormat.getInstance().format(tongHoaDon));

//    private void khoiTaoGiaTri() {
//        ArrayList<NhanVien> listNhanVien = nhanVien_DAO.getAllNhanVien();
//
//        for (NhanVien nv : listNhanVien) {
//            jComboBoxNV.addItem(nv.getTenNhanVien());
//        }
//
//        ArrayList<KhachHang> listKhachHang = khachHang_DAO.getAllKhachHang();
//        for (KhachHang kh : listKhachHang) {
//            jComboBoxKH.addItem(kh.getTenKhachHang());
//        }
//
//        jComboBoxNV.setSelectedIndex(0);
//        jComboBoxKH.setSelectedIndex(0);
//        jDateChooserFrom.setDate(Calendar.getInstance().getTime());
//        jDateChooserTo.setDate(Calendar.getInstance().getTime());
//    }
	// <editor-fold defaultstate="collapsed" desc="Generated
	// Code">//GEN-BEGIN:initComponents
	private void initComponents() {

		jPanel1 = new javax.swing.JPanel();
		jPanel3 = new javax.swing.JPanel();
		jLabelTongDT = new javax.swing.JLabel();
		jLabel2 = new javax.swing.JLabel();
		jPanel4 = new javax.swing.JPanel();
		jLabel1 = new javax.swing.JLabel();
		jLabelTongHD = new javax.swing.JLabel();
		jDateChooserTo = new com.toedter.calendar.JDateChooser();
		jDateChooserFrom = new com.toedter.calendar.JDateChooser();
		jLabelTo = new javax.swing.JLabel();
		jLabelFrom = new javax.swing.JLabel();
		jLabelTenKH = new javax.swing.JLabel();
		jLabelTenNV = new javax.swing.JLabel();
		jComboBoxKH = new javax.swing.JComboBox<>();
		jComboBoxNV = new javax.swing.JComboBox<>();
		btnThongKe = new javax.swing.JButton();
		jCheckBoxAll = new javax.swing.JCheckBox();
		jPanel2 = new javax.swing.JPanel();
		jPanelDSHD = new javax.swing.JPanel();
		jScrollPane1 = new javax.swing.JScrollPane();
		jTableDSHD = new javax.swing.JTable();

		setBackground(new java.awt.Color(255, 255, 255));
		setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

		jPanel1.setBackground(new java.awt.Color(255, 255, 255));
		jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

		jPanel3.setBackground(new java.awt.Color(255, 153, 0));

		jLabelTongDT.setFont(new java.awt.Font("Segoe UI", 0, 48)); // NOI18N
		jLabelTongDT.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		jLabelTongDT.setText("0");

		jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		jLabel2.setText("TỔNG DOANH THU");

		javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
		jPanel3.setLayout(jPanel3Layout);
		jPanel3Layout.setHorizontalGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
						.addGap(0, 0, Short.MAX_VALUE)
						.addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
								.addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 290, Short.MAX_VALUE)
								.addComponent(jLabelTongDT, javax.swing.GroupLayout.DEFAULT_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))));
		jPanel3Layout.setVerticalGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(jPanel3Layout.createSequentialGroup()
						.addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 56,
								javax.swing.GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
						.addComponent(jLabelTongDT, javax.swing.GroupLayout.DEFAULT_SIZE, 118, Short.MAX_VALUE)));

		jPanel1.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 20, 290, 180));

		jPanel4.setBackground(new java.awt.Color(255, 255, 204));

		jLabel1.setBackground(new java.awt.Color(102, 102, 255));
		jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		jLabel1.setText("TỔNG SỐ HOÁ ĐƠN");

		jLabelTongHD.setFont(new java.awt.Font("Segoe UI", 0, 48)); // NOI18N
		jLabelTongHD.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		jLabelTongHD.setText("0");

		javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
		jPanel4.setLayout(jPanel4Layout);
		jPanel4Layout.setHorizontalGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE,
						290, Short.MAX_VALUE)
				.addGroup(jPanel4Layout.createSequentialGroup().addContainerGap().addComponent(jLabelTongHD,
						javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
		jPanel4Layout.setVerticalGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(jPanel4Layout.createSequentialGroup()
						.addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 56,
								javax.swing.GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
						.addComponent(jLabelTongHD, javax.swing.GroupLayout.DEFAULT_SIZE, 118, Short.MAX_VALUE)));

		jPanel1.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 20, 290, 180));

		jDateChooserTo.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
			public void propertyChange(java.beans.PropertyChangeEvent evt) {
				jDateChooserToPropertyChange(evt);
			}
		});
		jPanel1.add(jDateChooserTo, new org.netbeans.lib.awtextra.AbsoluteConstraints(1060, 20, 200, 40));

		jDateChooserFrom.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
			public void propertyChange(java.beans.PropertyChangeEvent evt) {
				jDateChooserFromPropertyChange(evt);
			}
		});
		jPanel1.add(jDateChooserFrom, new org.netbeans.lib.awtextra.AbsoluteConstraints(780, 20, 200, 40));

		jLabelTo.setText("Đến ngày");
		jPanel1.add(jLabelTo, new org.netbeans.lib.awtextra.AbsoluteConstraints(990, 20, 60, 40));

		jLabelFrom.setText("Từ ngày");
		jPanel1.add(jLabelFrom, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 20, 60, 40));

		jLabelTenKH.setText("Tên khách hàng");
		jPanel1.add(jLabelTenKH, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 140, 100, 40));

		jLabelTenNV.setText("Tên nhân viên");
		jPanel1.add(jLabelTenNV, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 80, 100, 40));

		jComboBoxKH.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Tất cả" }));
		jPanel1.add(jComboBoxKH, new org.netbeans.lib.awtextra.AbsoluteConstraints(820, 140, 440, 40));

		jComboBoxNV.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Tất cả" }));
		jPanel1.add(jComboBoxNV, new org.netbeans.lib.awtextra.AbsoluteConstraints(820, 80, 440, 40));

		btnThongKe.setBackground(new java.awt.Color(255, 153, 0));
		btnThongKe.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
		btnThongKe.setForeground(new java.awt.Color(255, 255, 255));
		btnThongKe.setText("THỐNG KÊ");
		btnThongKe.setBorder(null);
		btnThongKe.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnThongKeActionPerformed(evt);
			}
		});
		jPanel1.add(btnThongKe, new org.netbeans.lib.awtextra.AbsoluteConstraints(1070, 190, 190, 40));

		jCheckBoxAll.setText("Tất cả");
		jCheckBoxAll.addItemListener(new java.awt.event.ItemListener() {
			public void itemStateChanged(java.awt.event.ItemEvent evt) {
				jCheckBoxAllItemStateChanged(evt);
			}
		});
		jPanel1.add(jCheckBoxAll, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 190, 150, -1));

		add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1290, 240));

		jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

		jPanelDSHD.setBorder(javax.swing.BorderFactory.createTitledBorder("THÔNG TIN HOÁ ĐƠN"));

		jTableDSHD.setModel(new javax.swing.table.DefaultTableModel(new Object[][] {

		}, new String[] { "Mã hoá đơn", "Ngày lập hoá đơn", "Nhân viên", "Khách hàng", "Tiền hoá đơn"}));
		jTableDSHD.setRowHeight(30);
		jScrollPane1.setViewportView(jTableDSHD);

		javax.swing.GroupLayout jPanelDSHDLayout = new javax.swing.GroupLayout(jPanelDSHD);
		jPanelDSHD.setLayout(jPanelDSHDLayout);
		jPanelDSHDLayout
				.setHorizontalGroup(
						jPanelDSHDLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
								.addGroup(jPanelDSHDLayout.createSequentialGroup().addContainerGap()
										.addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1233,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addContainerGap(41, Short.MAX_VALUE)));
		jPanelDSHDLayout.setVerticalGroup(jPanelDSHDLayout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(jPanelDSHDLayout.createSequentialGroup().addComponent(jScrollPane1,
						javax.swing.GroupLayout.PREFERRED_SIZE, 312, javax.swing.GroupLayout.PREFERRED_SIZE)
						.addGap(0, 75, Short.MAX_VALUE)));

		jPanel2.add(jPanelDSHD, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1290, 410));
		jPanelDSHD.getAccessibleContext().setAccessibleName("Thông tin hoá đơn");

		add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 240, 1290, 410));

		loadCbxNV();
		loadCbxKH();
	}// </editor-fold>//GEN-END:initComponents

	private void jDateChooserFromPropertyChange(java.beans.PropertyChangeEvent evt) {// GEN-FIRST:event_jDateChooserFromPropertyChange
		// TODO add your handling code here:
//        if (!isTuNgayValid())
//            return;
	}// GEN-LAST:event_jDateChooserFromPropertyChange

	private void jDateChooserToPropertyChange(java.beans.PropertyChangeEvent evt) {// GEN-FIRST:event_jDateChooserToPropertyChange
		// TODO add your handling code here:
//        if (!isDenNgayValid())
//            return;
	}// GEN-LAST:event_jDateChooserToPropertyChange

	private void btnThongKeActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnThongKeActionPerformed
		if (jDateChooserFrom.getDate() == null || jDateChooserTo.getDate() == null) {
			JOptionPane.showMessageDialog(this, "Vui lòng chọn ngày");
			return;
		}

		else if (jDateChooserFrom.getDate().after(jDateChooserTo.getDate())) {
			JOptionPane.showMessageDialog(this, "Ngày bắt đầu phải trước ngày kết thúc");
			return;
		}

		clearTableHoaDon();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		out.println("QLHD");
		out.println("btnTimKiem");
		try {
			String from = formatter.format(jDateChooserFrom.getDate());
			out.println(from);
			String to = formatter.format(jDateChooserTo.getDate());
			out.println(to);

			String nv = jComboBoxNV.getSelectedItem().toString();
			String kh = jComboBoxKH.getSelectedItem().toString();

			DefaultTableModel dtml = (DefaultTableModel) jTableDSHD.getModel();
			String listObject = sc.nextLine();
			List<HoaDon> listHoaDon = new Gson().fromJson(listObject, new TypeToken<List<HoaDon>>() {
			}.getType());
			String listObjectTT = sc.nextLine();
			double[] listTongTien = new Gson().fromJson(listObjectTT, new TypeToken<double[]>() {
			}.getType());

			int index = 0;
			double tongTien = 0;

			if (jComboBoxNV.getSelectedItem().toString().equals("Tất cả")
					&& jComboBoxKH.getSelectedItem().toString().equals("Tất cả")) {
				for (HoaDon hoaDon : listHoaDon) {
					Object[] rowdata = { hoaDon.getMaHD(), hoaDon.getNgayLapHD(), hoaDon.getNhanVien().getTenNhanVien(),
							hoaDon.getKhachHang().getTenKhachHang(), listTongTien[index] };
					dtml.addRow(rowdata);
					tongTien = listTongTien[index] + tongTien;
					index++;
					
					//Lấy số lượng hàng của table
                    int rowCount = jTableDSHD.getRowCount();
                    jLabelTongHD.setText(NumberFormat.getInstance().format(rowCount));
				}
			}

			else if (jComboBoxNV.getSelectedItem().toString().equals("Tất cả")
					&& !jComboBoxKH.getSelectedItem().toString().equals("Tất cả")) {
				for (HoaDon hoaDon : listHoaDon) {
					if (hoaDon.getKhachHang().getTenKhachHang().contains(kh)) {
						Object[] rowdata = { hoaDon.getMaHD(), hoaDon.getNgayLapHD(),
								hoaDon.getNhanVien().getTenNhanVien(), hoaDon.getKhachHang().getTenKhachHang(),
								listTongTien[index] };
						dtml.addRow(rowdata);
						tongTien = listTongTien[index] + tongTien;
					}
					index++;
					
					//Lấy số lượng hàng của table
                    int rowCount = jTableDSHD.getRowCount();
                    jLabelTongHD.setText(NumberFormat.getInstance().format(rowCount));
				}
			}

			else if (!jComboBoxNV.getSelectedItem().toString().equals("Tất cả")
					&& jComboBoxKH.getSelectedItem().toString().equals("Tất cả")) {
				for (HoaDon hoaDon : listHoaDon) {
					if (hoaDon.getNhanVien().getTenNhanVien().contains(nv)) {
						Object[] rowdata = { hoaDon.getMaHD(), hoaDon.getNgayLapHD(),
								hoaDon.getNhanVien().getTenNhanVien(), hoaDon.getKhachHang().getTenKhachHang(),
								listTongTien[index] };
						dtml.addRow(rowdata);
						tongTien = listTongTien[index] + tongTien;
					}
					index++;

					// Lấy số lượng hàng của table
					int rowCount = jTableDSHD.getRowCount();
					jLabelTongHD.setText(NumberFormat.getInstance().format(rowCount));
				}
			}

			else if (!jComboBoxNV.getSelectedItem().toString().equals("Tất cả")
					&& !jComboBoxKH.getSelectedItem().toString().equals("Tất cả")) {
				for (HoaDon hoaDon : listHoaDon) {
					if (hoaDon.getNhanVien().getTenNhanVien().contains(nv)
							&& hoaDon.getKhachHang().getTenKhachHang().contains(kh)) {
						Object[] rowdata = { hoaDon.getMaHD(), hoaDon.getNgayLapHD(),
								hoaDon.getNhanVien().getTenNhanVien(), hoaDon.getKhachHang().getTenKhachHang(),
								listTongTien[index] };
						dtml.addRow(rowdata);
						tongTien = listTongTien[index] + tongTien;
					}
					index++;

					// Lấy số lượng hàng của table
					int rowCount = jTableDSHD.getRowCount();
					jLabelTongHD.setText(NumberFormat.getInstance().format(rowCount));
				}
			}

			jLabelTongDT.setText(NumberFormat.getInstance().format(tongTien));
			
			jCheckBoxAll.setSelected(false);
		} catch (Exception e) {
			e.printStackTrace();
		}
		out.flush();
	}// GEN-LAST:event_btnThongKeActionPerformed

	private void jCheckBoxAllItemStateChanged(java.awt.event.ItemEvent evt) {// GEN-FIRST:event_jCheckBoxAllItemStateChanged
		if (jCheckBoxAll.isSelected()) {
			loadTblHoaDon();
		}
	}// GEN-LAST:event_jCheckBoxAllItemStateChanged

	private boolean isTuNgayValid() {
		long milis = System.currentTimeMillis();
		Date currentDate = new Date(milis);
		if (jDateChooserFrom.getDate().getTime() - currentDate.getTime() > 0) {
			JOptionPane.showMessageDialog(null, "Ngày phải bé hơn hoặc bằng ngày hiện tại");
			return false;
		}

		return true;
	}

//
	private boolean isDenNgayValid() {
		long milis = System.currentTimeMillis();
		Date currentDate = new Date(milis);
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String from = formatter.format(jDateChooserFrom.getDate());
		String to = formatter.format(jDateChooserTo.getDate());
		Date tuNgay = Date.valueOf(from);
		Date denNgay = Date.valueOf(to);

		if (denNgay.getTime() - currentDate.getTime() > 0) {
			JOptionPane.showMessageDialog(null, "Ngày phải bé hơn hoặc bằng ngày hiện tại");
			return false;
		}

		if (tuNgay.getTime() - denNgay.getTime() > 0) {
			JOptionPane.showMessageDialog(null, "Đến ngày phải có giá trị nhỏ hơn từ ngày");

			return false;
		}
		return true;
	}

	private void loadCbxNV() {
		out.println("staff");
		out.println("getAllNVByChucVu");
		String listObject = sc.nextLine();
		List<NhanVien> dsNV = new Gson().fromJson(listObject, new TypeToken<List<NhanVien>>() {
		}.getType());

		DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();

		// Add "Tất cả" to the model
		model.addElement("Tất cả");

		// Add each NhanVien's name to the model
		for (NhanVien nv : dsNV) {
			model.addElement(nv.getTenNhanVien());
		}

		// Set the model to the JComboBox
		jComboBoxNV.setModel(model);
	}

	private void loadCbxKH() {
		out.println("customer");
		out.println("get10KH_highestOrderPrice");
		String listObject = sc.nextLine();
		List<KhachHang> dsKH = new Gson().fromJson(listObject, new TypeToken<List<KhachHang>>() {
		}.getType());

		DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();

		// Add "Tất cả" to the model
		model.addElement("Tất cả");

		// Add each NhanVien's name to the model
		for (KhachHang kh : dsKH) {
			model.addElement(kh.getTenKhachHang());
		}

		// Set the model to the JComboBox
		jComboBoxKH.setModel(model);
	}

	// Variables declaration - do not modify//GEN-BEGIN:variables
	private javax.swing.JButton btnThongKe;
	private javax.swing.JCheckBox jCheckBoxAll;
	private javax.swing.JComboBox<String> jComboBoxKH;
	private javax.swing.JComboBox<String> jComboBoxNV;
	private com.toedter.calendar.JDateChooser jDateChooserFrom;
	private com.toedter.calendar.JDateChooser jDateChooserTo;
	private javax.swing.JLabel jLabel1;
	private javax.swing.JLabel jLabel2;
	private javax.swing.JLabel jLabelFrom;
	private javax.swing.JLabel jLabelTenKH;
	private javax.swing.JLabel jLabelTenNV;
	private javax.swing.JLabel jLabelTo;
	private javax.swing.JLabel jLabelTongDT;
	private javax.swing.JLabel jLabelTongHD;
	private javax.swing.JPanel jPanel1;
	private javax.swing.JPanel jPanel2;
	private javax.swing.JPanel jPanel3;
	private javax.swing.JPanel jPanel4;
	private javax.swing.JPanel jPanelDSHD;
	private javax.swing.JScrollPane jScrollPane1;
	private javax.swing.JTable jTableDSHD;
	// End of variables declaration//GEN-END:variables
}
