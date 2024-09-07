/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package Gui;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.InstanceCreator;
import com.google.gson.reflect.TypeToken;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import entity.ChiTietHoaDon;
import entity.HoaDon;
import entity.KhachHang;
import entity.NhanVien;
import entity.Sach;
import entity.SanPham;
import entity.VPP;

import java.awt.Desktop;
import java.awt.Image;
import java.awt.PopupMenu;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.net.Socket;
import java.sql.Date;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import java.awt.Dimension;

/**
 *
 * @author Admin
 */
public class QuanLyBanHang extends javax.swing.JPanel {

	private Socket socket;
	PrintWriter out;
	Scanner sc;
	public static File fontFile = new File("src/main/java/Font/vuArial.ttf");
	int xacNhan;
	double tongTien = 0;

	/**
	 * Creates new form nenTC
	 */
	public QuanLyBanHang(Socket socket) {
		this.socket = socket;
		try {
			out = new PrintWriter(socket.getOutputStream(), true);
			sc = new Scanner(socket.getInputStream());
		} catch (Exception e) {
			e.printStackTrace();
		}

		initComponents();
		jButtonSach.setSelected(true);
		try {
			out.println("QLBH");
			out.println("getBasic");

			String txtFieldMaHD = sc.nextLine();
			jTextFieldMaHD.setText(txtFieldMaHD);

			out.println(Login.tenTaiKhoan);
			String object = sc.nextLine();
			NhanVien nv = new Gson().fromJson(object, new TypeToken<NhanVien>() {
			}.getType());

			jTextFieldMaNV.setText(nv.getMaNhanVien());
			jTextFieldTenNV.setText(nv.getTenNhanVien());
		} catch (Exception e) {
			e.printStackTrace();
		}

		long millis = System.currentTimeMillis();
		Date date = new Date(millis);
		jTextFieldNgayLap.setText(date.toString());

		loadTblSach();
	}

	// clear table sản phẩm
	public void clearTableSanPham() {
		DefaultTableModel dtm = (DefaultTableModel) jTable_DSSPHienCo.getModel();
		dtm.setRowCount(0);
	}

	// load table sách
	public void loadTblSach() {
		try {
			clearTableSanPham();
			out.println("QLBH");
			out.println("getAllSach");
			DefaultTableModel dtm = (DefaultTableModel) jTable_DSSPHienCo.getModel();

			String listObject = sc.nextLine();
			InstanceCreator<SanPham> sachInstanceCreator = new InstanceCreator<SanPham>() {
				@Override
				public SanPham createInstance(Type type) {
					return new Sach();
				}
			};
			Gson gsonSach = new GsonBuilder().registerTypeAdapter(SanPham.class, sachInstanceCreator).create();

			List<SanPham> listSanPham = gsonSach.fromJson(listObject, new TypeToken<List<SanPham>>() {
			}.getType());
			for (SanPham sanPham : listSanPham) {
				if (sanPham instanceof Sach) {
					Object[] rowData = { sanPham.getMaSP(), sanPham.getTenSP(), sanPham.getDonGiaBan(),
							sanPham.getSoLuongTK() };
					dtm.addRow(rowData);
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		out.flush();
	}

	// load table văn phòng phẩm
	public void loadTblVPP() {
		try {
			clearTableSanPham();
			out.println("QLBH");
			out.println("getAllVPP");
			DefaultTableModel dtm = (DefaultTableModel) jTable_DSSPHienCo.getModel();

			String listObject = sc.nextLine();
			InstanceCreator<SanPham> vppInstanceCreator = new InstanceCreator<SanPham>() {
				@Override
				public SanPham createInstance(Type type) {
					return new VPP();
				}
			};
			Gson gsonVPP = new GsonBuilder().registerTypeAdapter(SanPham.class, vppInstanceCreator).create();

			List<SanPham> listSanPham = gsonVPP.fromJson(listObject, new TypeToken<List<SanPham>>() {
			}.getType());
			for (SanPham sanPham : listSanPham) {
				if (sanPham instanceof VPP) {
					Object[] rowData = { sanPham.getMaSP(), sanPham.getTenSP(), sanPham.getDonGiaBan(),
							sanPham.getSoLuongTK() };
					dtm.addRow(rowData);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		out.flush();
	}

	// clear table sản phẩm
	public void clearTableSanPhamBan() {
		DefaultTableModel dtm = (DefaultTableModel) jTable_DSSPBan.getModel();
		dtm.setRowCount(0);
	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */
	@SuppressWarnings("unchecked")
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
		jLabelMaHD = new javax.swing.JLabel();
		jLabelMaNV = new javax.swing.JLabel();
		jTextFieldMaNV = new javax.swing.JTextField();
		jLabelTenNV = new javax.swing.JLabel();
		jPanel1 = new javax.swing.JPanel();
		jLabelMaKH = new javax.swing.JLabel();
		jLabelSoDienThoai = new javax.swing.JLabel();
		jTextFieldMaKH = new javax.swing.JTextField();
		btnThem = new javax.swing.JButton();
		btnXoaSP = new javax.swing.JButton();
		btnLamMoi = new javax.swing.JButton();
		jTextFieldSoDT = new javax.swing.JTextField();
		jTextFieldTenNV = new javax.swing.JTextField();
		jTextFieldTenKH = new javax.swing.JTextField();
		jLabelTenKH = new javax.swing.JLabel();
		jTextFieldMaHD = new javax.swing.JTextField();
		jLabelEmail = new javax.swing.JLabel();
		jTextFieldSoLuong = new javax.swing.JTextField();
		jButtonVPP = new javax.swing.JButton();
		jButtonSach = new javax.swing.JButton();
		jScrollPane1 = new javax.swing.JScrollPane();
		jTable_DSSPHienCo = new javax.swing.JTable();
		jLabelTimKiem = new javax.swing.JLabel();
		jTextFieldTimKiem = new javax.swing.JTextField();
		btnThanhToan = new javax.swing.JButton();
		jLabelSoLuong = new javax.swing.JLabel();
		jTextFieldEmail = new javax.swing.JTextField();
		jScrollPane2 = new javax.swing.JScrollPane();
		jTable_DSSPBan = new javax.swing.JTable();
		btnTimKiem = new javax.swing.JButton();
		jLabelTongTien = new javax.swing.JLabel();
		jTextFieldTongTien = new javax.swing.JTextField();
		jPanel2 = new javax.swing.JPanel();
		jLabel1 = new javax.swing.JLabel();
		btnTimSDT = new javax.swing.JButton();
		btnXoaAll = new javax.swing.JButton();
		jlabel_img = new javax.swing.JLabel();
		jLabelTienKD = new javax.swing.JLabel();
		jTextFieldKhachDua = new javax.swing.JTextField();
		jLabelTienTL = new javax.swing.JLabel();
		jTextFieldTraLai = new javax.swing.JTextField();
		jTextFieldSoLuongBan = new javax.swing.JTextField();
		jButtonTang = new javax.swing.JButton();
		jButtonGiam = new javax.swing.JButton();
		btnXacNhan = new javax.swing.JButton();
		jLabelNgayLap = new javax.swing.JLabel();
		jTextFieldNgayLap = new javax.swing.JTextField();

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
		setPreferredSize(new Dimension(1290, 650));
		setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

		jLabelMaHD.setText("MÃ HÓA ĐƠN:");
		add(jLabelMaHD, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, 90, 40));

		jLabelMaNV.setText("MÃ NHÂN VIÊN:");
		add(jLabelMaNV, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 100, 90, 40));

		jTextFieldMaNV.setEnabled(false);
		add(jTextFieldMaNV, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 100, 170, 40));

		jLabelTenNV.setText("TÊN NHÂN VIÊN:");
		add(jLabelTenNV, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 160, 90, 40));

		jPanel1.setBackground(new java.awt.Color(153, 255, 153));
		add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 50, 10, 290));

		jLabelMaKH.setText("MÃ KH:");
		add(jLabelMaKH, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 150, 120, 40));

		jLabelSoDienThoai.setText("SĐT :");
		add(jLabelSoDienThoai, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 50, 90, 40));
		jLabelSoDienThoai.getAccessibleContext().setAccessibleName("SỐ ĐTI :");

		jTextFieldMaKH.setEnabled(false);
		add(jTextFieldMaKH, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 150, 170, 40));

		btnThem.setBackground(new java.awt.Color(255, 102, 102));
		btnThem.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
		btnThem.setForeground(new java.awt.Color(255, 255, 255));
		btnThem.setIcon(new javax.swing.ImageIcon("src/main/java/Img/Add-icon.png")); // NOI18N
		btnThem.setText("THÊM");
		btnThem.setBorder(null);
		btnThem.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnThemActionPerformed(evt);
			}
		});
		add(btnThem, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 300, 100, 40));

		btnXoaSP.setBackground(new java.awt.Color(255, 51, 102));
		btnXoaSP.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
		btnXoaSP.setForeground(new java.awt.Color(255, 255, 255));
		btnXoaSP.setIcon(new javax.swing.ImageIcon("src/main/java/Img/icons8_delete_30px_1.png")); // NOI18N
		btnXoaSP.setBorder(null);
		btnXoaSP.setLabel("XÓA SP");
		btnXoaSP.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnXoaSPActionPerformed(evt);
			}
		});
		add(btnXoaSP, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 540, 120, 40));

		btnLamMoi.setBackground(new java.awt.Color(102, 255, 102));
		btnLamMoi.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
		btnLamMoi.setForeground(new java.awt.Color(255, 255, 255));
		btnLamMoi.setIcon(new javax.swing.ImageIcon("src/main/java/Img/Refresh-icon.png")); // NOI18N
		btnLamMoi.setText("LÀM MỚI");
		btnLamMoi.setBorder(null);
		btnLamMoi.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnLamMoiActionPerformed(evt);
			}
		});
		add(btnLamMoi, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 300, 110, 40));
		add(jTextFieldSoDT, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 50, 170, 40));

		jTextFieldTenNV.setEnabled(false);
		add(jTextFieldTenNV, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 160, 170, 40));

		jTextFieldTenKH.setEnabled(false);
		add(jTextFieldTenKH, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 200, 170, 40));

		jLabelTenKH.setText("TÊN KH:");
		jLabelTenKH.setToolTipText("");
		add(jLabelTenKH, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 200, 120, 40));

		jTextFieldMaHD.setEnabled(false);
		add(jTextFieldMaHD, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 50, 170, 30));

		jLabelEmail.setText("EMAIL :");
		add(jLabelEmail, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 250, 90, 40));
		add(jTextFieldSoLuong, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 300, 140, 40));

		jButtonVPP.setText("Văn phòng phẩm");
		jButtonVPP.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jButtonVPPActionPerformed(evt);
			}
		});
		add(jButtonVPP, new org.netbeans.lib.awtextra.AbsoluteConstraints(780, 50, -1, -1));

		jButtonSach.setText("Sách");
		jButtonSach.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jButtonSachActionPerformed(evt);
			}
		});
		add(jButtonSach, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 50, 120, -1));

		jTable_DSSPHienCo
				.setModel(new javax.swing.table.DefaultTableModel(new Object[][] { { null, null, null, null } },
						new String[] { "Mã SP", "Tên SP", "Đơn giá", "Hiện còn" }));
		jTable_DSSPHienCo.setRowHeight(30);
		jTable_DSSPHienCo.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mousePressed(java.awt.event.MouseEvent evt) {
				jTable_DSSPHienCoMousePressed(evt);
			}
		});
		jScrollPane1.setViewportView(jTable_DSSPHienCo);
		if (jTable_DSSPHienCo.getColumnModel().getColumnCount() > 0) {
			jTable_DSSPHienCo.getColumnModel().getColumn(0).setMinWidth(1);
			jTable_DSSPHienCo.getColumnModel().getColumn(0).setPreferredWidth(1);
			jTable_DSSPHienCo.getColumnModel().getColumn(0).setMaxWidth(1);
			jTable_DSSPHienCo.getColumnModel().getColumn(1).setPreferredWidth(100);
			jTable_DSSPHienCo.getColumnModel().getColumn(2).setPreferredWidth(1);
			jTable_DSSPHienCo.getColumnModel().getColumn(3).setPreferredWidth(1);
		}

		add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 80, 340, 210));

		jLabelTimKiem.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
		jLabelTimKiem.setForeground(new java.awt.Color(255, 153, 0));
		jLabelTimKiem.setText("Tìm kiếm:");
		add(jLabelTimKiem, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 300, 70, 40));

		jTextFieldTimKiem.setFont(new java.awt.Font("Segoe UI", 2, 12)); // NOI18N
		jTextFieldTimKiem.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jTextFieldTimKiemActionPerformed(evt);
			}
		});
		add(jTextFieldTimKiem, new org.netbeans.lib.awtextra.AbsoluteConstraints(890, 300, 250, 40));

		btnThanhToan.setBackground(new java.awt.Color(255, 153, 0));
		btnThanhToan.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
		btnThanhToan.setForeground(new java.awt.Color(255, 255, 255));
		btnThanhToan.setText("THANH TOÁN");
		btnThanhToan.setBorder(null);
		btnThanhToan.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnThanhToanActionPerformed(evt);
			}
		});
		add(btnThanhToan, new org.netbeans.lib.awtextra.AbsoluteConstraints(1060, 590, 190, 40));

		jLabelSoLuong.setText("SỐ LƯỢNG:");
		add(jLabelSoLuong, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 300, 90, 40));

		jTextFieldEmail.setEnabled(false);
		add(jTextFieldEmail, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 250, 170, 40));

		jTable_DSSPBan.setModel(new javax.swing.table.DefaultTableModel(new Object[][] {

		}, new String[] { "MÃ SP", "TÊN SP", "LOẠI SP", "NHÀ CUNG CẤP", "SỐ LƯỢNG", "ĐƠN GIÁ", "THÀNH TIỀN" }));
		jTable_DSSPBan.setRowHeight(30);
		jTable_DSSPBan.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mousePressed(java.awt.event.MouseEvent evt) {
				jTable_DSSPBanMousePressed(evt);
			}
		});
		jScrollPane2.setViewportView(jTable_DSSPBan);
		if (jTable_DSSPBan.getColumnModel().getColumnCount() > 0) {
			jTable_DSSPBan.getColumnModel().getColumn(0).setPreferredWidth(1);
			jTable_DSSPBan.getColumnModel().getColumn(2).setPreferredWidth(1);
			jTable_DSSPBan.getColumnModel().getColumn(4).setPreferredWidth(1);
		}

		add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 380, 1250, 150));

		btnTimKiem.setBackground(new java.awt.Color(255, 153, 0));
		btnTimKiem.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
		btnTimKiem.setForeground(new java.awt.Color(255, 255, 255));
		btnTimKiem.setText("TÌM KIẾM");
		btnTimKiem.setBorder(null);
		btnTimKiem.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnTimKiemActionPerformed(evt);
			}
		});
		add(btnTimKiem, new org.netbeans.lib.awtextra.AbsoluteConstraints(1150, 300, 110, 40));

		jLabelTongTien.setText("TỔNG TIỀN:");
		add(jLabelTongTien, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 590, 100, 40));

		jTextFieldTongTien.setBorder(null);
		jTextFieldTongTien.setEnabled(false);
		add(jTextFieldTongTien, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 590, 210, 40));
		add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

		jLabel1.setBackground(new java.awt.Color(255, 255, 255));
		jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
		jLabel1.setForeground(new java.awt.Color(255, 51, 0));
		jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		jLabel1.setText("QUẢN LÝ BÁN HÀNG");
		add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1300, 40));

		btnTimSDT.setBackground(new java.awt.Color(255, 153, 0));
		btnTimSDT.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
		btnTimSDT.setForeground(new java.awt.Color(255, 255, 255));
		btnTimSDT.setText("TÌM SĐT");
		btnTimSDT.setActionCommand("TÌM");
		btnTimSDT.setBorder(null);
		btnTimSDT.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnTimSDTActionPerformed(evt);
			}
		});
		add(btnTimSDT, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 100, 170, 40));

		btnXoaAll.setBackground(new java.awt.Color(255, 51, 102));
		btnXoaAll.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
		btnXoaAll.setForeground(new java.awt.Color(255, 255, 255));
		btnXoaAll.setIcon(new javax.swing.ImageIcon("src/main/java/Img/icons8_delete_30px_1.png")); // NOI18N
		btnXoaAll.setBorder(null);
		btnXoaAll.setLabel("XÓA TẤT CẢ");
		btnXoaAll.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnXoaAllActionPerformed(evt);
			}
		});
		add(btnXoaAll, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 540, 140, 40));

		jlabel_img.setBackground(new java.awt.Color(204, 255, 255));
		jlabel_img.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		jlabel_img.setBorder(javax.swing.BorderFactory.createEtchedBorder(new java.awt.Color(255, 153, 0), null));
		add(jlabel_img, new org.netbeans.lib.awtextra.AbsoluteConstraints(940, 40, 320, 250));

		jLabelTienKD.setBackground(new java.awt.Color(204, 255, 51));
		jLabelTienKD.setText("TIỀN KHÁCH ĐƯA:");
		add(jLabelTienKD, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 590, 110, 40));

		jTextFieldKhachDua.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
		jTextFieldKhachDua.addKeyListener(new java.awt.event.KeyAdapter() {
			public void keyReleased(java.awt.event.KeyEvent evt) {
				jTextFieldKhachDuaKeyReleased(evt);
			}
		});
		add(jTextFieldKhachDua, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 590, 210, 40));

		jLabelTienTL.setBackground(new java.awt.Color(204, 255, 51));
		jLabelTienTL.setText("TIỀN TRẢ LẠI:");
		add(jLabelTienTL, new org.netbeans.lib.awtextra.AbsoluteConstraints(740, 590, 90, 40));

		jTextFieldTraLai.setBorder(null);
		jTextFieldTraLai.setEnabled(false);
		add(jTextFieldTraLai, new org.netbeans.lib.awtextra.AbsoluteConstraints(830, 590, 210, 40));
		add(jTextFieldSoLuongBan, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 540, 170, 40));

		jButtonTang.setText("+");
		jButtonTang.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jButtonTangActionPerformed(evt);
			}
		});
		add(jButtonTang, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 540, 50, 40));

		jButtonGiam.setText("-");
		jButtonGiam.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jButtonGiamActionPerformed(evt);
			}
		});
		add(jButtonGiam, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 540, 50, 40));

		btnXacNhan.setBackground(new java.awt.Color(255, 102, 102));
		btnXacNhan.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
		btnXacNhan.setForeground(new java.awt.Color(255, 255, 255));
		btnXacNhan.setIcon(new javax.swing.ImageIcon("src/main/java/Img/Add-icon.png")); // NOI18N
		btnXacNhan.setText("XÁC NHẬN");
		btnXacNhan.setBorder(null);
		btnXacNhan.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnXacNhanActionPerformed(evt);
			}
		});
		add(btnXacNhan, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 540, 130, 40));

		jLabelNgayLap.setText("NGÀY LẬP");
		add(jLabelNgayLap, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 210, 90, 40));

		jTextFieldNgayLap.setEnabled(false);
		add(jTextFieldNgayLap, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 210, 170, 40));
	}// </editor-fold>//GEN-END:initComponents

	private int tongsoluong = 0;

	private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnThemActionPerformed
		try {
			if (kiemTraHopLe()) {
				out.println("QLBH");
				out.println("getSP");
				int soLuong = Integer.parseInt(jTextFieldSoLuong.getText());
				int row = jTable_DSSPHienCo.getSelectedRow();

				out.println(jTable_DSSPHienCo.getValueAt(row, 0).toString());

				SanPham sp = null;
				if (jTable_DSSPHienCo.getValueAt(row, 0).toString().startsWith("S")) {
					String objectSach = sc.nextLine();
					InstanceCreator<SanPham> sachInstanceCreator = new InstanceCreator<SanPham>() {
						@Override
						public SanPham createInstance(Type type) {
							return new Sach();
						}
					};
					Gson gsonSach = new GsonBuilder().registerTypeAdapter(SanPham.class, sachInstanceCreator).create();
					sp = gsonSach.fromJson(objectSach, new TypeToken<SanPham>() {
					}.getType());
				} else {
					String objectVPP = sc.nextLine();
					InstanceCreator<SanPham> vppInstanceCreator = new InstanceCreator<SanPham>() {
						@Override
						public SanPham createInstance(Type type) {
							return new VPP();
						}
					};
					Gson gsonVPP = new GsonBuilder().registerTypeAdapter(SanPham.class, vppInstanceCreator).create();
					sp = gsonVPP.fromJson(objectVPP, new TypeToken<SanPham>() {
					}.getType());
				}
				DefaultTableModel dtmBan = (DefaultTableModel) jTable_DSSPBan.getModel();
				int tang = 0;
				int check = 0;
				int checkSL = 0;
				for (int i = 0; i < dtmBan.getRowCount(); i++) {
					if (sp.getMaSP().equals(jTable_DSSPBan.getValueAt(i, 0).toString())) {
						int slhc = Integer.parseInt(dtmBan.getValueAt(i, 4).toString());
						tongsoluong = slhc + soLuong;
						if (tongsoluong > sp.getSoLuongTK()) {
							JOptionPane.showMessageDialog(null,
									"Số lượng bán phải nhỏ hơn số lượng hiện có trong kho ở sản phẩm " + sp.getMaSP());
							checkSL = 1;
						} else {
							check = i;
							tang = 1;
							break;
						}

					}
				}

				for (int i = 0; i < dtmBan.getRowCount(); i++) {
					if (sp.getMaSP().equals(jTable_DSSPBan.getValueAt(i, 0).toString())) {
						int slhc = Integer.parseInt(dtmBan.getValueAt(i, 4).toString());
						tongsoluong = slhc + soLuong;
						if (tongsoluong > sp.getSoLuongTK()) {
							JOptionPane.showMessageDialog(null,
									"Số lượng bán phải nhỏ hơn số lượng hiện có trong kho ở sản phẩm " + sp.getMaSP());
							checkSL = 1;
						} else {
							check = i;
							tang = 1;
							break;
						}

					}
				}
				if (checkSL == 0) {
					if (tang == 0) {
						if (sp instanceof Sach) {
							Object[] rowData = { sp.getMaSP(), sp.getTenSP(), "Sách", sp.getNhaCungCap().getTenNCC(),
									soLuong, sp.getDonGiaBan(), soLuong * sp.getDonGiaBan() };
							dtmBan.addRow(rowData);
						} else {
							Object[] rowData = { sp.getMaSP(), sp.getTenSP(), "VPP", sp.getNhaCungCap().getTenNCC(),
									soLuong, sp.getDonGiaBan(), soLuong * sp.getDonGiaBan() };
							dtmBan.addRow(rowData);
						}
					} else {
						dtmBan.setValueAt(tongsoluong, check, 4);
						dtmBan.setValueAt(tongsoluong * sp.getDonGiaBan(), check, 6);
						tang = 0;
					}
				}
				tinhTongTien();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		out.flush();
	}// GEN-LAST:event_btnThemActionPerformed

	private void btnXoaSPActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnXoaSPActionPerformed
		// TODO add your handling code here:
		int row = jTable_DSSPBan.getSelectedRow();
		DefaultTableModel dtmBan = (DefaultTableModel) jTable_DSSPBan.getModel();
		if (row == -1) {
			JOptionPane.showMessageDialog(null, "Vui lòng chọn sản phẩm cần xoá");
		} else {
			dtmBan.removeRow(row);
		}
		tinhTongTien();
	}// GEN-LAST:event_btnXoaSPActionPerformed

	private void btnLamMoiActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnLamMoiActionPerformed
		// TODO add your handling code here:
		lamMoi();

	}// GEN-LAST:event_btnLamMoiActionPerformed

	private void jButtonVPPActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButtonVPPActionPerformed
		// TODO add your handling code here:
		loadTblVPP();
	}// GEN-LAST:event_jButtonVPPActionPerformed

	private void btnThanhToanActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnThanhToanActionPerformed
		// TODO add your handling code here:
		try {
			if (kiemTraHopLeThanhToan()) {
				out.println("QLBH");
				out.println("thanhToan");
				
				DefaultTableModel dtmDSSPBan = (DefaultTableModel) jTable_DSSPBan.getModel();
				
				//Kiem tra so luong ton kho
				int rowJTable_DSSPBan_check = jTable_DSSPBan.getRowCount();
				out.println(rowJTable_DSSPBan_check);
				for (int i = 0; i < jTable_DSSPBan.getRowCount(); i++) {
					String maSP = dtmDSSPBan.getValueAt(i, 0).toString();
					out.println(maSP);
					String spObject = sc.nextLine();
					SanPham sp = null;
					boolean errorOccurred = false;
					if (maSP.startsWith("S")) {
						InstanceCreator<SanPham> sachInstanceCreator = new InstanceCreator<SanPham>() {
							@Override
							public SanPham createInstance(Type type) {
								return new Sach();
							}
						};
						Gson gsonSach = new GsonBuilder().registerTypeAdapter(SanPham.class, sachInstanceCreator)
								.create();
						sp = gsonSach.fromJson(spObject, new TypeToken<SanPham>() {
						}.getType());
						if (sp.getSoLuongTK() <= Integer.parseInt(dtmDSSPBan.getValueAt(i, 4).toString())) {
							JOptionPane.showMessageDialog(null, "Số lượng tồn kho không đủ");
							errorOccurred = true;
						}
					} else {
						InstanceCreator<SanPham> vppInstanceCreator = new InstanceCreator<SanPham>() {
							@Override
							public SanPham createInstance(Type type) {
								return new VPP();
							}
						};
						Gson gsonVPP = new GsonBuilder().registerTypeAdapter(SanPham.class, vppInstanceCreator)
								.create();
						sp = gsonVPP.fromJson(spObject, new TypeToken<SanPham>() {
						}.getType());
						if (sp.getSoLuongTK() <= Integer.parseInt(dtmDSSPBan.getValueAt(i, 4).toString())) {
							JOptionPane.showMessageDialog(null, "Số lượng tồn kho không đủ");
							//Ngắt hành động
							errorOccurred = true;
						}
					}
					if (errorOccurred == true) {
                        return;
					}
				}
				
				String maHD = sc.nextLine();
				long milis = System.currentTimeMillis();
				Date now = new Date(milis);
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
				String day = formatter.format(now);
				Date ngayLap = Date.valueOf(day);

				out.println(jTextFieldMaNV.getText());
				String nvObject = sc.nextLine();
				NhanVien nv = new Gson().fromJson(nvObject, new TypeToken<NhanVien>() {
				}.getType());

				out.println(jTextFieldSoDT.getText());
				String khObject = sc.nextLine();
				KhachHang kh = new Gson().fromJson(khObject, new TypeToken<KhachHang>() {
				}.getType());

				HoaDon hd = new HoaDon(maHD, ngayLap, nv, kh);
				String responseHD = new Gson().toJson(hd);
				try {
					PrintWriter writer = new PrintWriter(socket.getOutputStream());
					writer.println(responseHD);
					writer.flush();
				} catch (IOException e) {
					throw new RuntimeException(e);
				}

				String rsAddHD = sc.nextLine();
				if (rsAddHD.equals("false")) {
					JOptionPane.showMessageDialog(null, "Đã có lỗi");
					return;
				}

				int rowJTable_DSSPBan = jTable_DSSPBan.getRowCount();
				out.println(rowJTable_DSSPBan);

				for (int i = 0; i < jTable_DSSPBan.getRowCount(); i++) {
					String maSP = dtmDSSPBan.getValueAt(i, 0).toString();
					out.println(maSP);

					String spObject = sc.nextLine();

					out.println(maSP);
					SanPham sp = null;
					if (maSP.startsWith("S")) {
						InstanceCreator<SanPham> sachInstanceCreator = new InstanceCreator<SanPham>() {
							@Override
							public SanPham createInstance(Type type) {
								return new Sach();
							}
						};
						Gson gsonSach = new GsonBuilder().registerTypeAdapter(SanPham.class, sachInstanceCreator)
								.create();
						sp = gsonSach.fromJson(spObject, new TypeToken<SanPham>() {
						}.getType());
					} else {
						InstanceCreator<SanPham> vppInstanceCreator = new InstanceCreator<SanPham>() {
							@Override
							public SanPham createInstance(Type type) {
								return new VPP();
							}
						};
						Gson gsonVPP = new GsonBuilder().registerTypeAdapter(SanPham.class, vppInstanceCreator)
								.create();
						sp = gsonVPP.fromJson(spObject, new TypeToken<SanPham>() {
						}.getType());
					}
					int soLuong = Integer.parseInt(dtmDSSPBan.getValueAt(i, 4).toString());

					ChiTietHoaDon cthd = new ChiTietHoaDon(sp, hd, soLuong);
					String responseCTHD = new Gson().toJson(cthd);
					try {
						PrintWriter writer = new PrintWriter(socket.getOutputStream());
						writer.println(responseCTHD);
						writer.flush();
					} catch (IOException e) {
						throw new RuntimeException(e);
					}
					String rsAddCTHD = sc.nextLine();
					if (rsAddCTHD.equals("false")) {
						JOptionPane.showMessageDialog(null, "Đã có lỗi");
						return;
					}

					sp.setSoLuongTK(sp.getSoLuongTK() - soLuong);
					out.println(maSP);
					String responseSP = new Gson().toJson(sp);
					try {
						PrintWriter writer = new PrintWriter(socket.getOutputStream());
						writer.println(responseSP);
						writer.flush();
					} catch (IOException e) {
						throw new RuntimeException(e);
					}
					String rsUpdateSP = sc.nextLine();
					if (rsUpdateSP.equals("false")) {
						JOptionPane.showMessageDialog(null, "Đã có lỗi");
						return;
					}
				}
				JOptionPane.showMessageDialog(null, "Thanh toán thành công");

				out.println(maHD);
				String cthdObject = sc.nextLine();
				InstanceCreator<SanPham> sachInstanceCreator = new InstanceCreator<SanPham>() {
					@Override
					public SanPham createInstance(Type type) {
						return new Sach();
					}
				};
				Gson gsonSach = new GsonBuilder().registerTypeAdapter(SanPham.class, sachInstanceCreator).create();
				List<ChiTietHoaDon> listCTHD = gsonSach.fromJson(cthdObject, new TypeToken<List<ChiTietHoaDon>>() {
				}.getType());
				try {
					xacNhan = JOptionPane.showConfirmDialog(this, "Bạn có muốn xem file", "Thông báo",
							JOptionPane.YES_NO_OPTION);
					xuatHoaDon(hd, listCTHD, kh, nv);
				} catch (Exception e) {
					e.printStackTrace();
				}
				clearTableSanPhamBan();

				String txtFieldMaHD = sc.nextLine();
				jTextFieldMaHD.setText(txtFieldMaHD);

				tongTien = 0;
				jTextFieldTongTien.setText("");

				lamMoi();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		out.flush();
	}// GEN-LAST:event_btnThanhToanActionPerformed

	private void btnTimKiemActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnTimKiemActionPerformed
		// TODO add your handling code here:
		try {
			out.println("QLBH");
			out.println("getAllSP");
			clearTableSanPham();
			String timKiem = jTextFieldTimKiem.getText().toString();
			DefaultTableModel dtm = (DefaultTableModel) jTable_DSSPHienCo.getModel();

			String listObject = sc.nextLine();
			InstanceCreator<SanPham> sachInstanceCreator = new InstanceCreator<SanPham>() {
				@Override
				public SanPham createInstance(Type type) {
					return new Sach();
				}
			};
			Gson gsonSach = new GsonBuilder().registerTypeAdapter(SanPham.class, sachInstanceCreator).create();

			List<SanPham> listSanPham = gsonSach.fromJson(listObject, new TypeToken<List<SanPham>>() {
			}.getType());

			for (SanPham sanPham : listSanPham) {
				if (sanPham.getTenSP().contains(timKiem)) {
					Object[] rowData = { sanPham.getMaSP(), sanPham.getTenSP(), sanPham.getDonGiaBan(),
							sanPham.getSoLuongTK() };
					dtm.addRow(rowData);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		out.flush();
	}// GEN-LAST:event_btnTimKiemActionPerformed

	private void jButtonSachActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButtonSachActionPerformed
		// TODO add your handling code here:
		loadTblSach();
	}// GEN-LAST:event_jButtonSachActionPerformed

	private void jTable_DSSPHienCoMousePressed(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_jTable_DSSPHienCoMousePressed
		// TODO add your handling code here:
		try {
			out.println("QLBH");
			out.println("getSP");
			int row = jTable_DSSPHienCo.getSelectedRow();
			DefaultTableModel dtm = (DefaultTableModel) jTable_DSSPHienCo.getModel();
			out.println(dtm.getValueAt(row, 0).toString());

			String object = sc.nextLine();
			InstanceCreator<SanPham> sachInstanceCreator = new InstanceCreator<SanPham>() {
				@Override
				public SanPham createInstance(Type type) {
					return new Sach();
				}
			};
			Gson gsonSach = new GsonBuilder().registerTypeAdapter(SanPham.class, sachInstanceCreator).create();

			SanPham sanPham = gsonSach.fromJson(object, new TypeToken<SanPham>() {
			}.getType());
			File file = new File("");
			String path = file.getAbsolutePath();

			jlabel_img.setIcon(ResizeImage(path + "/src/main/java/Img/SanPham/" + sanPham.getHinhAnh()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		out.flush();
	}// GEN-LAST:event_jTable_DSSPHienCoMousePressed

	private void btnTimSDTActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnTimSDTActionPerformed
		// TODO add your handling code here:
		try {
			out.println("QLBH");
			out.println("getKHBySDT");
			String sdt = jTextFieldSoDT.getText().toString();
			Pattern pattern = Pattern.compile("^[0-9\\-\\+]{9,15}$");
			Matcher matcher = pattern.matcher(sdt);

			if (matcher.matches()) {
				try {
					out.println(sdt);
					String object = sc.nextLine();
					KhachHang kh = new Gson().fromJson(object, new TypeToken<KhachHang>() {
					}.getType());
					jTextFieldMaKH.setText(kh.getMaKhachHang());
					jTextFieldTenKH.setText(kh.getTenKhachHang());
					jTextFieldEmail.setText(kh.getEmail());
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Không tồn tại khách hàng có số điện thoại này");
				}
			} else {
				JOptionPane.showMessageDialog(null,
						"Vui lòng nhập dúng định dạng số điện thoại (dãy số từ 10 đến 11 số, bắt đàu 0)");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		out.flush();
	}// GEN-LAST:event_btnTimSDTActionPerformed

	private void jTextFieldTimKiemActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jTextFieldTimKiemActionPerformed
		// TODO add your handling code here:
	}// GEN-LAST:event_jTextFieldTimKiemActionPerformed

	private void btnXoaAllActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnXoaAllActionPerformed
		// TODO add your handling code here:
		clearTableSanPhamBan();
		tongTien = 0;
		jTextFieldTongTien.setText("");
	}// GEN-LAST:event_btnXoaAllActionPerformed

	private void jTextFieldKhachDuaKeyReleased(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_jTextFieldKhachDuaKeyReleased
		// TODO add your handling code here:
		double tong = tongTien;
		double tienKH = Double.parseDouble(jTextFieldKhachDua.getText());
		double tienThua = tienKH - tong;
		jTextFieldTraLai.setText(NumberFormat.getInstance().format(tienThua));
	}// GEN-LAST:event_jTextFieldKhachDuaKeyReleased

	private void jButtonGiamActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButtonGiamActionPerformed
		// TODO add your handling code here:
		try {
			int row = jTable_DSSPBan.getSelectedRow();
			if (row == -1) {
				JOptionPane.showMessageDialog(null, "Vui lòng chọn sản phẩm cần thay đổi");
			}
			int giam = Integer.parseInt(jTextFieldSoLuongBan.getText()) - 1;
			jTextFieldSoLuongBan.setText(String.valueOf(giam));
		} catch (Exception e) {
		}

	}// GEN-LAST:event_jButtonGiamActionPerformed

	private void jButtonTangActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButtonTangActionPerformed
		// TODO add your handling code here:
		try {
			int row = jTable_DSSPBan.getSelectedRow();
			if (row == -1) {
				JOptionPane.showMessageDialog(null, "Vui lòng chọn sản phẩm cần thay đổi");
			}
			int tang = Integer.parseInt(jTextFieldSoLuongBan.getText()) + 1;
			jTextFieldSoLuongBan.setText(String.valueOf(tang));
		} catch (Exception e) {
		}

	}// GEN-LAST:event_jButtonTangActionPerformed

	private void jTable_DSSPBanMousePressed(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_jTable_DSSPBanMousePressed
		// TODO add your handling code here:
		int row = jTable_DSSPBan.getSelectedRow();
		DefaultTableModel dtmBan = (DefaultTableModel) jTable_DSSPBan.getModel();
		if (row == -1) {
			JOptionPane.showMessageDialog(null, "Vui lòng chọn sản phẩm cần thay đổi");
		}
		jTextFieldSoLuongBan.setText(dtmBan.getValueAt(row, 4).toString());
	}// GEN-LAST:event_jTable_DSSPBanMousePressed

	private void btnXacNhanActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnXacNhanActionPerformed
		// TODO add your handling code here:
		try {
			out.println("QLBH");
			out.println("getAllSP");
			int check = 0;
			int row = jTable_DSSPBan.getSelectedRow();
			DefaultTableModel dtmBan = (DefaultTableModel) jTable_DSSPBan.getModel();
			if (row == -1) {
				JOptionPane.showMessageDialog(null, "Vui lòng chọn sản phẩm cần thay đổi");
			} else {
				String listObject = sc.nextLine();
				InstanceCreator<SanPham> sachInstanceCreator = new InstanceCreator<SanPham>() {
					@Override
					public SanPham createInstance(Type type) {
						return new Sach();
					}
				};
				Gson gson = new GsonBuilder().registerTypeAdapter(SanPham.class, sachInstanceCreator).create();
				List<SanPham> listSanPham = gson.fromJson(listObject, new TypeToken<List<SanPham>>() {
				}.getType());

				int soLuong = Integer.parseInt(jTextFieldSoLuongBan.getText());
				if (soLuong < 1) {
					int option = JOptionPane.showConfirmDialog(null,
							"Số lượng mua nhỏ hơn 1 nên sản phẩm sẽ bị xoá. Bạn chắc chứ?");
					if (option == JOptionPane.YES_OPTION) {
						dtmBan.removeRow(row);
						JOptionPane.showMessageDialog(null, "Xoá thành công");
					} else
						check = 1;
				} else {
					for (SanPham sanPham : listSanPham) {
						if (dtmBan.getValueAt(row, 0).equals(sanPham.getMaSP())) {
							if (soLuong > sanPham.getSoLuongTK()) {
								JOptionPane.showMessageDialog(null, "Số lượng phải nhỏ hơn số lượng tồn kho");
								check = 1;
								break;
							}
						}
					}
				}
				if (check == 0) {
					JOptionPane.showMessageDialog(null, "Cập nhật thành công");
					dtmBan.setValueAt(soLuong, row, 4);
					double donGia = Double.parseDouble(dtmBan.getValueAt(row, 5).toString());
					int sl = Integer.parseInt(dtmBan.getValueAt(row, 4).toString());
					double thanhTien = sl * donGia;
					dtmBan.setValueAt(thanhTien, row, 6);
					tinhTongTien();
				}
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Số lượng thay đổi phải là 1 con số");
		}
		out.flush();
	}// GEN-LAST:event_btnXacNhanActionPerformed

	private boolean kiemTraHopLe() {
		int row = jTable_DSSPHienCo.getSelectedRow();
		DefaultTableModel dtm = (DefaultTableModel) jTable_DSSPHienCo.getModel();
		if (row == -1) {
			JOptionPane.showMessageDialog(null, "Vui lòng chọn sản phẩm cần nhập");
			return false;
		}

		if (jTextFieldSoLuong.getText().equals("")) {
			JOptionPane.showMessageDialog(null, "Vui lòng nhập số lượng sản phẩm");
			return false;
		}

		try {
			int soLuong = Integer.parseInt(jTextFieldSoLuong.getText());
			if (soLuong < 1) {
				JOptionPane.showMessageDialog(null, "Số lượng phải từ 1 trở lên");
				return false;
			}
			if (soLuong > Integer.parseInt(dtm.getValueAt(row, 3).toString())) {
				JOptionPane.showMessageDialog(null, "Số lượng phải nhỏ hơn số lượng tồn kho");
				return false;
			}

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Số lượng phải là 1 con số lớn hơn 0");
			return false;
		}
		return true;
	}

	private boolean kiemTraHopLeThanhToan() {
		if (jTextFieldMaKH.getText().toString().equals("")) {
			JOptionPane.showMessageDialog(null, "Chưa có thông tin khách hàng");
			return false;
		}

		if (jTable_DSSPBan.getRowCount() == 0) {
			JOptionPane.showMessageDialog(null, "Chưa có thông tin sản phẩm bán");
			return false;
		}

		if (jTextFieldKhachDua.getText().toString().equals("")) {
			JOptionPane.showMessageDialog(null, "Vui lòng nhập tiền khách đưa");
			return false;
		}

		if ((Double.parseDouble(jTextFieldKhachDua.getText().toString()) - tongTien) < 0) {
			JOptionPane.showMessageDialog(null, "Tiền khách đưa phải lớn hơn tổng tiền");
			return false;
		}

		return true;
	}

	private void tinhTongTien() {
		DefaultTableModel dtmBan = (DefaultTableModel) jTable_DSSPBan.getModel();
		double tien = 0;
		for (int i = 0; i < dtmBan.getRowCount(); i++) {
			tien += Double.parseDouble(dtmBan.getValueAt(i, 6).toString());
		}
		tongTien = tien;
		jTextFieldTongTien.setText(NumberFormat.getInstance().format(tongTien));
		jTextFieldSoLuong.setText("");
		jTextFieldSoLuongBan.setText("");
		jTable_DSSPHienCo.clearSelection();
		jTable_DSSPBan.clearSelection();
	}

	public ImageIcon ResizeImage(String imgPath) {
		ImageIcon myImage = new ImageIcon(imgPath);
		Image img = myImage.getImage();
		Image newImg = img.getScaledInstance(jlabel_img.getWidth(), jlabel_img.getHeight(), Image.SCALE_SMOOTH);
		ImageIcon image = new ImageIcon(newImg);

		return image;
	}

	private void lamMoi() {
		jTextFieldSoDT.setText("");
		jTextFieldSoLuong.setText("");
		jTextFieldMaKH.setText("");
		jTextFieldTenKH.setText("");
		jTextFieldEmail.setText("");
		jTextFieldTimKiem.setText("");
		jTable_DSSPHienCo.clearSelection();
		jTable_DSSPBan.clearSelection();
		loadTblSach();
	}

	private void xuatHoaDon(HoaDon hd, List<ChiTietHoaDon> listCTHD, KhachHang kh, NhanVien nv) {
		try {
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
				double tienNhan = Double.parseDouble(jTextFieldKhachDua.getText().toString());
				String tienNhanS = currencyVN.format(tienNhan);
				doc.add(new Paragraph("Khách đưa: " + tienNhanS, font));
				doc.add(new Paragraph("Tiền trả khách: " + jTextFieldTraLai.getText().toString(), font));
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
	}

	private void openHoaDon(String maHoaDon) {
		File file = new File("");
		String path = file.getAbsolutePath();
		File URL = new File("/" + path + "/" + maHoaDon + ".pdf");
		try {
			Desktop.getDesktop().open(URL);
		} catch (Exception e) {

		}
	}

	// Variables declaration - do not modify//GEN-BEGIN:variables
	private javax.swing.JButton btnLamMoi;
	private javax.swing.JButton btnThanhToan;
	private javax.swing.JButton btnThem;
	private javax.swing.JButton btnTimKiem;
	private javax.swing.JButton btnTimSDT;
	private javax.swing.JButton btnXacNhan;
	private javax.swing.JButton btnXoaAll;
	private javax.swing.JButton btnXoaSP;
	private javax.swing.JButton jButtonGiam;
	private javax.swing.JButton jButtonSach;
	private javax.swing.JButton jButtonTang;
	private javax.swing.JButton jButtonVPP;
	private javax.swing.JLabel jLabel1;
	private javax.swing.JLabel jLabelEmail;
	private javax.swing.JLabel jLabelMaHD;
	private javax.swing.JLabel jLabelMaKH;
	private javax.swing.JLabel jLabelMaNV;
	private javax.swing.JLabel jLabelNgayLap;
	private javax.swing.JLabel jLabelSoDienThoai;
	private javax.swing.JLabel jLabelSoLuong;
	private javax.swing.JLabel jLabelTenKH;
	private javax.swing.JLabel jLabelTenNV;
	private javax.swing.JLabel jLabelTienKD;
	private javax.swing.JLabel jLabelTienTL;
	private javax.swing.JLabel jLabelTimKiem;
	private javax.swing.JLabel jLabelTongTien;
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
	private javax.swing.JPanel jPanel1;
	private javax.swing.JPanel jPanel2;
	private javax.swing.JPopupMenu jPopupMenu1;
	private javax.swing.JScrollPane jScrollPane1;
	private javax.swing.JScrollPane jScrollPane2;
	private javax.swing.JTable jTable_DSSPBan;
	private javax.swing.JTable jTable_DSSPHienCo;
	private javax.swing.JTextField jTextFieldEmail;
	private javax.swing.JTextField jTextFieldKhachDua;
	private javax.swing.JTextField jTextFieldMaHD;
	private javax.swing.JTextField jTextFieldMaKH;
	private javax.swing.JTextField jTextFieldMaNV;
	private javax.swing.JTextField jTextFieldNgayLap;
	private javax.swing.JTextField jTextFieldSoDT;
	private javax.swing.JTextField jTextFieldSoLuong;
	private javax.swing.JTextField jTextFieldSoLuongBan;
	private javax.swing.JTextField jTextFieldTenKH;
	private javax.swing.JTextField jTextFieldTenNV;
	private javax.swing.JTextField jTextFieldTimKiem;
	private javax.swing.JTextField jTextFieldTongTien;
	private javax.swing.JTextField jTextFieldTraLai;
	private javax.swing.JLabel jlabel_img;
	private java.awt.PopupMenu popupMenu1;
	// End of variables declaration//GEN-END:variables
}
