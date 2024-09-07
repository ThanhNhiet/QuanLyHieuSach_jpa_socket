/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package Gui;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.InstanceCreator;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.log.Level;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.sql.Date;
import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.sl.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.lang.*;
import java.lang.reflect.Type;
import java.net.Socket;

import entity.ChiTietHoaDon;
import entity.HoaDon;
import entity.KhachHang;
import entity.Sach;
import entity.SanPham;
import entity.VPP;

import java.awt.Desktop;
import java.awt.Font;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

/**
 *
 * @author Admin
 */
public class QuanLyHoaDon extends javax.swing.JPanel {
	DefaultTableModel dtm = null;
	private Socket socket;
	PrintWriter out;
	Scanner sc;
	public static File fontFile = new File("src/main/java/Font/vuArial.ttf");
	int xacNhan;

	public QuanLyHoaDon(Socket socket) {
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

	public void clearTableHoaDon() {
		dtm = (DefaultTableModel) jTable_DanhSachHoaDon.getModel();
		dtm.setRowCount(0);
	}

	// load table hóa đơn
	public void loadTblHoaDon() {
		clearTableHoaDon();
		dtm = (DefaultTableModel) jTable_DanhSachHoaDon.getModel();

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
						hoaDon.getKhachHang().getTenKhachHang(), listTongTien[index] };
				dtm.addRow(rowData);
				index++;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// clear table sản phẩm
	public void clearTableChiTietHoaDon() {
		dtm = (DefaultTableModel) jTable_ChiTietHoaDon.getModel();
		dtm.setRowCount(0);
	}

	// load table sản phẩm
	public void loadTblChiTietHoaDon(String id) {
		clearTableChiTietHoaDon();
		try {
			out.println("QLHD");
			out.println("getCTHD_id");
			out.println(id);
			dtm = (DefaultTableModel) jTable_ChiTietHoaDon.getModel();

			String listObjectSach = sc.nextLine();			
			// Tạo một InstanceCreator mới cho lớp SanPham
			InstanceCreator<SanPham> sachInstanceCreator = new InstanceCreator<SanPham>() {
				@Override
				public SanPham createInstance(Type type) {
					return new Sach();
				}
			};
			// Đăng ký InstanceCreator cho lớp SanPham khi tạo Gson
			Gson gsonSach = new GsonBuilder().registerTypeAdapter(SanPham.class, sachInstanceCreator).create();
			List<ChiTietHoaDon> listCTHDSach = gsonSach.fromJson(listObjectSach, new TypeToken<List<ChiTietHoaDon>>() {
			}.getType());
			
			String listObjectVPP = sc.nextLine();
			InstanceCreator<SanPham> vppInstanceCreator = new InstanceCreator<SanPham>() {
				@Override
				public SanPham createInstance(Type type) {
					return new VPP();
				}
			};
			Gson gsonVPP = new GsonBuilder().registerTypeAdapter(SanPham.class, vppInstanceCreator).create();
			List<ChiTietHoaDon> listCTHDVPP = gsonVPP.fromJson(listObjectVPP, new TypeToken<List<ChiTietHoaDon>>() {
			}.getType());
			
			for (ChiTietHoaDon chiTietHoaDon : listCTHDSach) {
				if (chiTietHoaDon.getSanPham() instanceof Sach) {
					Object[] rowData = { chiTietHoaDon.getSanPham().getMaSP(), chiTietHoaDon.getSanPham().getTenSP(),
							"Sách", chiTietHoaDon.getSoLuong(), chiTietHoaDon.getSanPham().getDonGiaBan(),
							chiTietHoaDon.thanhTien() };
					dtm.addRow(rowData);
				} 
			}
			for (ChiTietHoaDon chiTietHoaDon : listCTHDVPP) {
				if (chiTietHoaDon.getSanPham() instanceof VPP) {
					Object[] rowData = { chiTietHoaDon.getSanPham().getMaSP(), chiTietHoaDon.getSanPham().getTenSP(),
							"VPP", chiTietHoaDon.getSoLuong(), chiTietHoaDon.getSanPham().getDonGiaBan(),
							chiTietHoaDon.thanhTien() };
					dtm.addRow(rowData);
				} 
			}
			
		}catch( Exception e){
			e.printStackTrace();
		}
	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */
	
	// <editor-fold defaultstate="collapsed" desc="Generated
	// Code">//GEN-BEGIN:initComponents
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
		btnXuatDanhSachHoaDon = new javax.swing.JButton();
		jPanelThongTinTimKiem = new javax.swing.JPanel();
		jButtonAll = new javax.swing.JButton();
		jLabelFrom = new javax.swing.JLabel();
		jDateChooserFrom = new com.toedter.calendar.JDateChooser();
		jLabelTo = new javax.swing.JLabel();
		jDateChooserTo = new com.toedter.calendar.JDateChooser();
		jLabelTimKiem = new javax.swing.JLabel();
		jTextFieldTimKiem = new javax.swing.JTextField();
		btnTimKiem = new javax.swing.JButton();
		jPanelThongTinHoaDon = new javax.swing.JPanel();
		jScrollPaneHoaDon = new javax.swing.JScrollPane();
		jTable_DanhSachHoaDon = new javax.swing.JTable();
		jPanelChiTietHoaDon = new javax.swing.JPanel();
		jScrollPaneChiTietHoaDon = new javax.swing.JScrollPane();
		jTable_ChiTietHoaDon = new javax.swing.JTable();
		jLabel1 = new javax.swing.JLabel();
		btnXuatChiTietHoaDon = new javax.swing.JButton();

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

		setPreferredSize(new java.awt.Dimension(1300, 650));
		setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

		btnXuatDanhSachHoaDon.setBackground(new java.awt.Color(153, 255, 204));
		btnXuatDanhSachHoaDon.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
		btnXuatDanhSachHoaDon.setForeground(new java.awt.Color(102, 153, 255));
		btnXuatDanhSachHoaDon
				.setIcon(new javax.swing.ImageIcon("src/main/java/Img/icons8_ms_excel_30px.png")); // NOI18N
		btnXuatDanhSachHoaDon.setText("XUẤT DANH SÁCH HÓA ĐƠN");
		btnXuatDanhSachHoaDon.setBorder(null);
		btnXuatDanhSachHoaDon.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnXuatDanhSachHoaDonActionPerformed(evt);
			}
		});
		add(btnXuatDanhSachHoaDon, new org.netbeans.lib.awtextra.AbsoluteConstraints(920, 110, 270, 40));

		jPanelThongTinTimKiem.setBackground(new java.awt.Color(255, 255, 255));
		jPanelThongTinTimKiem.setBorder(javax.swing.BorderFactory.createTitledBorder("THÔNG TIN TÌM KIẾM"));
		jPanelThongTinTimKiem.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

		jButtonAll.setText("ALL");
		jButtonAll.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jButtonAllActionPerformed(evt);
			}
		});
		jPanelThongTinTimKiem.add(jButtonAll, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 30, 60, 40));

		jLabelFrom.setText("TỪ NGÀY:");
		jPanelThongTinTimKiem.add(jLabelFrom, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 30, 70, 40));

//        jDateChooserFrom.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
//            public void propertyChange(java.beans.PropertyChangeEvent evt) {
//                jDateChooserFromPropertyChange(evt);
//            }
//        });
		jPanelThongTinTimKiem.add(jDateChooserFrom,
				new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 30, 260, 40));

		jLabelTo.setText("ĐẾN NGÀY:");
		jPanelThongTinTimKiem.add(jLabelTo, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 30, 70, 40));

//        jDateChooserTo.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
//            public void propertyChange(java.beans.PropertyChangeEvent evt) {
//                jDateChooserToPropertyChange(evt);
//            }
//        });
		jPanelThongTinTimKiem.add(jDateChooserTo, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 30, 260, 40));

		jLabelTimKiem.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
		jLabelTimKiem.setForeground(new java.awt.Color(255, 153, 0));
		jLabelTimKiem.setText("Tìm kiếm:");
		jPanelThongTinTimKiem.add(jLabelTimKiem, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 80, 90, 40));

		jTextFieldTimKiem.setFont(new java.awt.Font("Segoe UI", 2, 12)); // NOI18N
		jPanelThongTinTimKiem.add(jTextFieldTimKiem,
				new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 80, 410, 40));

		btnTimKiem.setBackground(new java.awt.Color(255, 153, 0));
		btnTimKiem.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
		btnTimKiem.setForeground(new java.awt.Color(255, 255, 255));
		btnTimKiem.setText("TÌM KIẾM");
		btnTimKiem.setBorder(null);
		btnTimKiem.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnTimKiemActionPerformed(evt);
			}
		});
		jPanelThongTinTimKiem.add(btnTimKiem, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 80, 190, 40));

		add(jPanelThongTinTimKiem, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 50, 910, 140));

		jPanelThongTinHoaDon.setBorder(javax.swing.BorderFactory.createTitledBorder("THÔNG TIN HÓA ĐƠN"));
		jPanelThongTinHoaDon.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

		jTable_DanhSachHoaDon.setModel(new javax.swing.table.DefaultTableModel(new Object[][] {

		}, new String[] { "MÃ HOÁ ĐƠN", "NGÀY LẬP HOÁ ĐƠN", "NHÂN VIÊN", "KHÁCH HÀNG", "TỔNG TIỀN" }));
		jTable_DanhSachHoaDon.setRowHeight(30);
		jTable_DanhSachHoaDon.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mousePressed(java.awt.event.MouseEvent evt) {
				jTable_DanhSachHoaDonMousePressed(evt);
			}
		});
		jScrollPaneHoaDon.setViewportView(jTable_DanhSachHoaDon);

		jPanelThongTinHoaDon.add(jScrollPaneHoaDon,
				new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, 1230, 150));

		add(jPanelThongTinHoaDon, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 200, 1270, 190));

		jPanelChiTietHoaDon.setBorder(javax.swing.BorderFactory.createTitledBorder("CHI TIẾT HÓA ĐƠN"));
		jPanelChiTietHoaDon.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

		jTable_ChiTietHoaDon.setModel(new javax.swing.table.DefaultTableModel(new Object[][] {

		}, new String[] { "MÃ SP", "TÊN SP", "LOẠI SẢN PHẨM", "SỐ LƯỢNG", "ĐƠN GIÁ", "THÀNH TIỀN" }));
		jTable_ChiTietHoaDon.setRowHeight(30);
		jScrollPaneChiTietHoaDon.setViewportView(jTable_ChiTietHoaDon);

		jPanelChiTietHoaDon.add(jScrollPaneChiTietHoaDon,
				new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, 1230, 210));

		add(jPanelChiTietHoaDon, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 400, 1270, 240));

		jLabel1.setBackground(new java.awt.Color(255, 255, 255));
		jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
		jLabel1.setForeground(new java.awt.Color(255, 51, 0));
		jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		jLabel1.setText("QUẢN LÝ HÓA ĐƠN");
		add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1300, 40));

		btnXuatChiTietHoaDon.setBackground(new java.awt.Color(153, 255, 204));
		btnXuatChiTietHoaDon.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
		btnXuatChiTietHoaDon.setForeground(new java.awt.Color(102, 153, 255));
		btnXuatChiTietHoaDon.setIcon(new javax.swing.ImageIcon("src/main/java/Img/pdf-icon.png")); // NOI18N
		btnXuatChiTietHoaDon.setText("XUẤT CHI TIẾT HÓA ĐƠN");
		btnXuatChiTietHoaDon.setBorder(null);
		btnXuatChiTietHoaDon.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnXuatChiTietHoaDonActionPerformed(evt);
			}
		});
		add(btnXuatChiTietHoaDon, new org.netbeans.lib.awtextra.AbsoluteConstraints(920, 60, 270, 40));
	}// </editor-fold>//GEN-END:initComponents

	private static String[] Columns = { "MÃ HĐ", "NGÀY LẬP HĐ", "TÊN NHÂN VIÊN", "TÊN KHÁCH HÀNG", "TỔNG TIỀN" };

	private void jButtonAllActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButtonAllActionPerformed
		// TODO add your handling code here
		clearTableChiTietHoaDon();
		loadTblHoaDon();
	}// GEN-LAST:event_jButtonAllActionPerformed

	private void btnTimKiemActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnTimKiemActionPerformed
		// TODO add your handling code here:
		if (jDateChooserFrom.getDate() == null || jDateChooserTo.getDate() == null) {
			JOptionPane.showMessageDialog(this, "Vui lòng chọn ngày");
			return;
		}
		
		else if (jDateChooserFrom.getDate().after(jDateChooserTo.getDate())) {
			JOptionPane.showMessageDialog(this, "Ngày bắt đầu phải trước ngày kết thúc");
			return;
		}

		clearTableHoaDon();
		clearTableChiTietHoaDon();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		out.println("QLHD");
		out.println("btnTimKiem");
		try {
			String from = formatter.format(jDateChooserFrom.getDate());
			out.println(from);
			String to = formatter.format(jDateChooserTo.getDate());
			out.println(to);

			String key = jTextFieldTimKiem.getText().trim();

			DefaultTableModel dtml = (DefaultTableModel) jTable_DanhSachHoaDon.getModel();
			String listObject = sc.nextLine();
			List<HoaDon> listHoaDon = new Gson().fromJson(listObject, new TypeToken<List<HoaDon>>() {
			}.getType());
			String listObjectTT = sc.nextLine();
			double[] listTongTien = new Gson().fromJson(listObjectTT, new TypeToken<double[]>() {
			}.getType());
			int index = 0;
			for (HoaDon hoaDon : listHoaDon) {
				if (hoaDon.getNhanVien().getTenNhanVien().contains(key)
						|| hoaDon.getKhachHang().getTenKhachHang().contains(key)) {
					Object[] rowdata = { hoaDon.getMaHD(), hoaDon.getNgayLapHD(), hoaDon.getNhanVien().getTenNhanVien(),
							hoaDon.getKhachHang().getTenKhachHang(), listTongTien[index] };
					dtml.addRow(rowdata);
				}
				index++;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		out.flush();
	}// GEN-LAST:event_btnTimKiemActionPerformed

	private void btnXuatDanhSachHoaDonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnXuatDanhSachHoaDonActionPerformed
		// TODO add your handling code here:
		XSSFWorkbook excelJTableExport = new XSSFWorkbook();
		XSSFSheet excelSheet = excelJTableExport.createSheet("Danh sách hoá đơn");
		BufferedOutputStream excelBos = null;
		try {

			// Chọn nơi lưu
			JFileChooser excelFileChooser = new JFileChooser();
			// Tiêu đề ô save
			excelFileChooser.setDialogTitle("Save As ..");
			// Định dạng chỉ xls, xlsx, xlsm files
			FileNameExtensionFilter fnef = new FileNameExtensionFilter("Files", "xls", "xlsx", "xlsm");
			excelFileChooser.setFileFilter(fnef);
			int chooser = excelFileChooser.showSaveDialog(null);
			XSSFCell excelCell = null;
			// Kiểm tra nút save nhấn chưa
			if (chooser == JFileChooser.APPROVE_OPTION) {
				// Nếu click thì đẩy dữ liệ
				// Tạo tiêu đề
				XSSFRow excelRow = excelSheet.createRow(0);
				for (int j = 0; j < jTable_DanhSachHoaDon.getColumnCount(); j++) {
					excelCell = excelRow.createCell(j);
					excelCell.setCellValue(Columns[j]);
				}
				// Vòng lặp qua cột và hàng của bảng jtable_DanhSachHoaDon để lấy giá trị
				for (int i = 1; i <= jTable_DanhSachHoaDon.getRowCount(); i++) {
					excelRow = excelSheet.createRow(i);
					for (int j = 0; j < jTable_DanhSachHoaDon.getColumnCount(); j++) {
						excelCell = excelRow.createCell(j);
						excelCell.setCellValue(jTable_DanhSachHoaDon.getValueAt(i - 1, j).toString());
					}
				}
				FileOutputStream excelFos = new FileOutputStream(excelFileChooser.getSelectedFile() + ".xlsx");
				excelBos = new BufferedOutputStream(excelFos);
				excelJTableExport.write(excelBos);
				JOptionPane.showMessageDialog(null, "Xuất danh sách hoá đơn thành công");
			}

		} catch (FileNotFoundException ex) {
			JOptionPane.showMessageDialog(null, ex);
		} catch (IOException ex) {
			JOptionPane.showMessageDialog(null, ex);
		} finally {
			try {
				if (excelBos != null) {
					excelBos.close();
				}
				if (excelJTableExport != null) {
					excelJTableExport.close();
				}
			} catch (IOException ex) {
				JOptionPane.showMessageDialog(null, ex);
			}
		}
	}// GEN-LAST:event_btnXuatDanhSachHoaDonActionPerformed

	private void btnXuatChiTietHoaDonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnXuatChiTietHoaDonActionPerformed
		try {
			xacNhan = JOptionPane.showConfirmDialog(this, "Bạn có muốn xem file", "Thông báo",
					JOptionPane.YES_NO_OPTION);
			xuatFilePDF();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}// GEN-LAST:event_btnXuatChiTietHoaDonActionPerformed

	private void jTable_DanhSachHoaDonMousePressed(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_jTable_DanhSachHoaDonMousePressed
		// TODO add your handling code here:
		int row = jTable_DanhSachHoaDon.getSelectedRow();

		DefaultTableModel dtm = (DefaultTableModel) jTable_DanhSachHoaDon.getModel();
		String id = dtm.getValueAt(row, 0).toString().trim();

		loadTblChiTietHoaDon(id);
	}// GEN-LAST:event_jTable_DanhSachHoaDonMousePressed

//    private void jDateChooserFromPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_jDateChooserFromPropertyChange
//        // TODO add your handling code here:
//        if (!isTuNgayValid())
//            return;
//    }//GEN-LAST:event_jDateChooserFromPropertyChange

//    private void jDateChooserToPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_jDateChooserToPropertyChange
//        // TODO add your handling code here:
//        if (!isDenNgayValid())
//            return;
//    }//GEN-LAST:event_jDateChooserToPropertyChange

	private boolean isTuNgayValid() {
		long milis = System.currentTimeMillis();
		Date currentDate = new Date(milis);
		if (jDateChooserFrom.getDate().getTime() - currentDate.getTime() > 0) {
			JOptionPane.showMessageDialog(null, "Ngày phải bé hơn hoặc bằng ngày hiện tại");
			return false;
		}

		return true;
	}

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

	public void xuatFilePDF() {
		try {
			out.println("QLHD");
			out.println("btnXuatHoaDon");
			int row = jTable_DanhSachHoaDon.getSelectedRow();
			if (row == -1) {
				JOptionPane.showMessageDialog(null, "Vui lòng chọn hoá đơn cần xuất");
			}
			dtm = (DefaultTableModel) jTable_DanhSachHoaDon.getModel();
			String id = dtm.getValueAt(row, 0).toString();
			out.println(id);

			String object = sc.nextLine();
			HoaDon hd = new Gson().fromJson(object, new TypeToken<HoaDon>() {
			}.getType());

			InstanceCreator<SanPham> sanPhamInstanceCreator = new InstanceCreator<SanPham>() {
				@Override
				public SanPham createInstance(Type type) {
					return new Sach();
				}
			};

			// Đăng ký InstanceCreator cho lớp SanPham khi tạo Gson
			Gson gson = new GsonBuilder().registerTypeAdapter(SanPham.class, sanPhamInstanceCreator).create();
			String listCTHDS = sc.nextLine();
			List<ChiTietHoaDon> listCTHD = gson.fromJson(listCTHDS, new TypeToken<List<ChiTietHoaDon>>() {
			}.getType());

			String path = hd.getMaHD();
			path = "hoaDonPDF\\" + path + ".pdf";
			if (!path.matches("(.)+(\\.pdf)$")) {
				path += ".pdf";
			}

			Document doc = new Document();
			doc.setPageSize(PageSize._11X17);
			try {
				Locale localeVN = new Locale("vi", "VN");
				NumberFormat currencyVN = NumberFormat.getCurrencyInstance(localeVN);

				PdfWriter.getInstance(doc, new FileOutputStream(path));

				BaseFont bf = BaseFont.createFont(fontFile.getAbsolutePath(), BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
				com.itextpdf.text.Font font = new com.itextpdf.text.Font(bf, 15);
				com.itextpdf.text.Font fontTitle = new com.itextpdf.text.Font(bf, 25);
				com.itextpdf.text.Font fontDC = new com.itextpdf.text.Font(bf, 25);
				doc.open();

				doc.add(new Paragraph("Hiệu sách Lucky", fontTitle));
				doc.add(new Paragraph("Địa chỉ: Nguyễn Văn Bảo, Phường 4, Gò Vấp", fontDC));
				doc.add(new Paragraph("Mã hóa đơn: " + hd.getMaHD(), font));
				doc.add(new Paragraph("Tên khách hàng: " + hd.getKhachHang().getTenKhachHang(), font));
				doc.add(new Paragraph("Ngày Lập: " + hd.getNgayLapHD(), font));
				doc.add(new Paragraph("Tên nhân viên: " + hd.getNhanVien().getTenNhanVien(), font));
				doc.add(new Paragraph(" "));
				doc.add(new Paragraph(" "));

				PdfPTable tbl = new PdfPTable(4);

				PdfPCell cell0 = new PdfPCell();
				Phrase phr0 = new Phrase("Tên sản phẩm", font);
				cell0.setPhrase(phr0);

				PdfPCell cell1 = new PdfPCell();
				Phrase phr1 = new Phrase("Số lượng", font);
				cell1.setPhrase(phr1);

				PdfPCell cell2 = new PdfPCell();
				Phrase phr2 = new Phrase("Đơn giá", font);
				cell2.setPhrase(phr2);

				PdfPCell cell3 = new PdfPCell();
				Phrase phr3 = new Phrase("Thành tiền", font);
				cell3.setPhrase(phr3);

				tbl.addCell(cell0);
				tbl.addCell(cell1);
				tbl.addCell(cell2);
				tbl.addCell(cell3);
				double tongCong = 0;
				for (ChiTietHoaDon cthd : listCTHD) {
					String sp = cthd.getSanPham().getTenSP();
					int sl = cthd.getSoLuong();
					String slS = String.valueOf(sl);
					double donGia = cthd.getSanPham().getDonGiaBan();
					String donGiaS = currencyVN.format(donGia);
					double thanhTien = cthd.thanhTien();
					String thanhTienS = currencyVN.format(thanhTien);

					PdfPCell cSP = new PdfPCell();
					Phrase phrSP = new Phrase(sp, font);
					cSP.setPhrase(phrSP);

					PdfPCell cSL = new PdfPCell();
					Phrase phrSl = new Phrase(slS, font);
					cSL.setPhrase(phrSl);

					PdfPCell cDg = new PdfPCell();
					Phrase phrDg = new Phrase(donGiaS, font);
					cDg.setPhrase(phrDg);

					PdfPCell cTT = new PdfPCell();
					Phrase phrTT = new Phrase(thanhTienS, font);
					cTT.setPhrase(phrTT);

					tbl.addCell(cSP);
					tbl.addCell(cSL);
					tbl.addCell(cDg);
					tbl.addCell(cTT);

					tongCong = tongCong + thanhTien;
				}

				doc.add(tbl);
				doc.add(new Paragraph(" "));
				String tongCongS = currencyVN.format(tongCong);
				doc.add(new Paragraph("Tổng cộng: " + tongCongS, font));
				doc.close();
				if (xacNhan == JOptionPane.YES_OPTION) {
					Desktop.getDesktop().open(new File(path));
				} else {
					JOptionPane.showMessageDialog(this, "Xuất hóa đơn " + hd.getMaHD() + " Thành công");
				}
			} catch (FileNotFoundException ex) {
				ex.printStackTrace();
			} catch (DocumentException ex) {
				ex.printStackTrace();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		out.flush();
	}

	// Variables declaration - do not modify//GEN-BEGIN:variables
	private javax.swing.JButton btnTimKiem;
	private javax.swing.JButton btnXuatChiTietHoaDon;
	private javax.swing.JButton btnXuatDanhSachHoaDon;
	private javax.swing.JButton jButtonAll;
	private com.toedter.calendar.JDateChooser jDateChooserFrom;
	private com.toedter.calendar.JDateChooser jDateChooserTo;
	private javax.swing.JLabel jLabel1;
	private javax.swing.JLabel jLabelFrom;
	private javax.swing.JLabel jLabelTimKiem;
	private javax.swing.JLabel jLabelTo;
	private javax.swing.JMenu jMenu1;
	private javax.swing.JMenu jMenu10;
	private javax.swing.JMenu jMenu11;
	private javax.swing.JMenu jMenu12;
	private javax.swing.JMenu jMenu13;
	private javax.swing.JMenu jMenu14;
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
	private javax.swing.JMenuItem jMenuItem1;
	private javax.swing.JMenuItem jMenuItem2;
	private javax.swing.JPanel jPanelChiTietHoaDon;
	private javax.swing.JPanel jPanelThongTinHoaDon;
	private javax.swing.JPanel jPanelThongTinTimKiem;
	private javax.swing.JPopupMenu jPopupMenu1;
	private javax.swing.JScrollPane jScrollPaneChiTietHoaDon;
	private javax.swing.JScrollPane jScrollPaneHoaDon;
	private javax.swing.JTable jTable_ChiTietHoaDon;
	private javax.swing.JTable jTable_DanhSachHoaDon;
	private javax.swing.JTextField jTextFieldTimKiem;
	private java.awt.PopupMenu popupMenu1;
	// End of variables declaration//GEN-END:variables

}
