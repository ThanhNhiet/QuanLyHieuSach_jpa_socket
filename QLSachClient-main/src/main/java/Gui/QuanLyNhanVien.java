/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package Gui;

import java.awt.HeadlessException;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

//import connectDB.ConnectDB;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import dao.HoaDonDAO;
import dao.NhanVienDAO;
import dao.TaiKhoanDAO;
import entity.HoaDon;
import entity.NhanVien;
//import entity.taiKhoan;

/**
 *
 * @author LENOVO
 */
public class QuanLyNhanVien extends javax.swing.JPanel {
    private Socket socket;
    private boolean isThemActive = false;
    private boolean isSuaActive = false;
//    private NhanVienDAO NV_DAO = new NhanVienDAO();
//    private TaiKhoanDAO TK_DAO = new TaiKhoanDAO();

    /**
     * Creates new form Tab_Sach
     *
     * @throws SQLException
     */
    public QuanLyNhanVien(Socket socket) {
        initComponents();
        this.socket = socket;
        loadtableNhanVien();

    }

    // clear table
    public void clearTableNhanVien() {
        DefaultTableModel dtm = (DefaultTableModel) jtable_NhanVien.getModel();
        dtm.setRowCount(0);
    }

    // load table
    public void loadtableNhanVien() {
        clearTableNhanVien();
//        NhanVienDAO nvdao = new NhanVienDAO();
        DefaultTableModel dtm = (DefaultTableModel) jtable_NhanVien.getModel();
        try {
            PrintWriter pw = new PrintWriter(socket.getOutputStream(), true);
            pw.println("staff");
            pw.println("getAll");

            Scanner sc = new Scanner(socket.getInputStream());
            String list = sc.nextLine();
            ArrayList<NhanVien> listNhanVien = new ArrayList<>();
            listNhanVien = new Gson().fromJson(list, new TypeToken<ArrayList<NhanVien>>() {
            }.getType());
            for (NhanVien nhanvien : listNhanVien) {
                String gioitinh = new String();
                if (nhanvien.isGioiTinh() == true) {
                    gioitinh = "Nam";
                } else {
                    gioitinh = "Nữ";
                }

                Object[] rowData = {nhanvien.getMaNhanVien(), nhanvien.getTenNhanVien(), nhanvien.getSoDienThoai(), gioitinh, nhanvien.getChucVu(), nhanvien.getEmail()};
                dtm.addRow(rowData);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
//        ArrayList<NhanVien> listNhanVien = NV_DAO.getAllNhanVien();
//        for (NhanVien nhanvien : listNhanVien) {
//            String gioitinh = new String();
//            if (nhanvien.isGioiTinh() == true) {
//                gioitinh = "Nam";
//            } else {
//                gioitinh = "Nữ";
//            }
//
//            Object[] rowData = {nhanvien.getMaNhanVien(), nhanvien.getTenNhanVien(), nhanvien.getSoDienThoai(), gioitinh, nhanvien.getChucVu(), nhanvien.getEmail()};
//            dtm.addRow(rowData);
//        }
    }
    // valid data

    private boolean validateData() {
        String sdt = jtextfield_SoDienThoai.getText().trim();
        String email = jtextfield_Email.getText().trim();
        String HovaTen = jtextfield_TenNhanVien.getText().trim();
        String manv = jtextfield_MaNhanVien.getText().trim();

        if (jtextfield_Email.getText().equals("") || jtextfield_MaNhanVien.getText().equals("") || jtextfield_SoDienThoai.getText().equals("")
                || jtextfield_TenNhanVien.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Vui lòng nhập đầy đủ thông tin");
            return false;
        }
        if (!(sdt.length() > 0 && sdt.matches("[0-9]{10}"))) {
            JOptionPane.showMessageDialog(jtextfield_SoDienThoai, "Số điện thoại yêu cầu phải đủ 10 số");
            jtextfield_SoDienThoai.requestFocus();
            return false;
        }
        if (!(email.length() > 0 && email.matches("[a-zA-Z0-9._%-]+(@){1}[a-zA-Z]+(.){1}[a-zA-Z]{2,4}"))) {
            JOptionPane.showMessageDialog(this, "Email sai định dạng");
            jtextfield_Email.requestFocus();
            return false;
        }
        return true;
    }

    private void themNhanVien() throws SQLException {
        if (!validateData()) {
            return;
        }
        String maNhanVien = jtextfield_MaNhanVien.getText();
        String hoVaTen = jtextfield_TenNhanVien.getText();
        String email = jtextfield_Email.getText();
        String sdt = jtextfield_SoDienThoai.getText();
        try {
            PrintWriter pw = new PrintWriter(socket.getOutputStream(), true);
            pw.println("staff");
            pw.println("add");

            boolean gioiTinh = Combobox_GioiTinh.getSelectedItem() == "Nam" ? true : false;
            String chucVu = combobox_ChucVu.getSelectedItem().toString();
            String json = "{\"maNhanVien\":\"" + maNhanVien + "\",\"tenNhanVien\":\"" + hoVaTen + "\",\"soDienThoai\":\"" + sdt + "\",\"gioiTinh\":" + gioiTinh + ",\"chucVu\":\"" + chucVu + "\",\"email\":\"" + email + "\"}";
            pw.println(json);

            Scanner sc = new Scanner(socket.getInputStream());
            String response = sc.nextLine();
            if (response.equals("Email exists")) {
                JOptionPane.showMessageDialog(null, "Email đã tồn tại trong hệ thống. Vui lòng sử dụng email khác");
                return;
            }
            if (response.equals("Phone exists")) {
                JOptionPane.showMessageDialog(null, "Số điện thoại đã tồn tại trong hệ thống. Vui lòng sử dụng sdt khác");
                return;
            }
            if (response.equals("Add success")) {
                JOptionPane.showMessageDialog(null, "Thêm thành công");
                loadtableNhanVien();
                clearInput();
                huyThaoTac();
                return;
            }
            JOptionPane.showMessageDialog(null, "Thêm thất bại");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
//        if (NV_DAO.getNhanVienByGmail(email) != null) {
//            JOptionPane.showMessageDialog(null, "Email đã tồn tại trong hệ thống. Vui lòng sử dụng email khác");
//            return;
//        }
//        if (NV_DAO.getNhanVienBySdt(sdt) != null) {
//            JOptionPane.showMessageDialog(null, "Số điện thoại đã tồn tại trong hệ thống. Vui lòng sử dụng sdt khác");
//            return;
//        }
//        boolean gioiTinh = Combobox_GioiTinh.getSelectedItem() == "Nam" ? true : false;
//        String chucVu = combobox_ChucVu.getSelectedItem().toString();
//        NhanVien nhanVien = new NhanVien(maNhanVien, hoVaTen, sdt, gioitinh, chucVu, email);
//        taiKhoan tk = new taiKhoan(nhanVien.getMaNhanVien(), "1111", chucVu);
//        if (TK_DAO.addTaiKhoan(tk) != -1) {
//            ConnectDB.getInstance().connect();
//            if (NV_DAO.addNhanVien(nhanVien) == -1) {
//                return;
//            }
//            loadtableNhanVien();
//            clearInput();
//            huyThaoTac();
//            JOptionPane.showMessageDialog(null, "Thêm thành công");
//            return;
//        } else {
//            JOptionPane.showMessageDialog(null, "Thêm thất bại");
//        }
    }

    public void clearTable() {
        DefaultTableModel dtm = (DefaultTableModel) jtable_NhanVien.getModel();
        dtm.setRowCount(0);
    }

    private void clearInput() {
        jtextfield_Email.setText("");
        jtextfield_SoDienThoai.setText("");
        jtextfield_TenNhanVien.setText("");
        combobox_ChucVu.setSelectedIndex(0);
        Combobox_GioiTinh.setSelectedIndex(0);
    }

    private void isInputActive(boolean check) {
        jtextfield_MaNhanVien.setEnabled(!check);
        jtextfield_SoDienThoai.setEnabled(check);
        jtextfield_Email.setEnabled(check);
        jtextfield_TenNhanVien.setEnabled(check);
        Combobox_GioiTinh.setEnabled(check);
        combobox_ChucVu.setEnabled(check);
    }

    private void isThemNhanVienClicked(boolean check) {
        isThemActive = check;
        isSuaActive = !check;
        if (isThemActive) {
//            NhanVien nhanVien = new NhanVien();
//            jtextfield_MaNhanVien.setText(nhanVien.auto_ID());
            try {
                PrintWriter pw = new PrintWriter(socket.getOutputStream(), true);
                pw.println("staff");
                pw.println("generateId");
                Scanner sc = new Scanner(socket.getInputStream());
                String id = sc.nextLine();
                jtextfield_MaNhanVien.setText(id);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            btn_Them.setText("Huỷ");
            btn_Sua.setEnabled(false);
            jtextfield_Timkiem.setEnabled(false);
        } else if (isSuaActive) {
            btn_Sua.setText("Huỷ");
            jtextfield_Timkiem.setEnabled(false);
            btn_Them.setEnabled(false);
        }
        btn_Luu.setEnabled(true);
        isInputActive(true);
    }

    private void btn_themActionPerformed(java.awt.event.ActionEvent evt) {
        if (btn_Them.getText().equals("THÊM")) {
            isThemActive = true;
            isThemNhanVienClicked(isThemActive);
            clearInput();
        } else if (btn_Them.getText().equals("Huỷ")) {
        	jtextfield_Timkiem.setEnabled(true);
            huyThaoTac();
        }
    }

    private void btn_timNhanVienActionPerformed(java.awt.event.ActionEvent evt) {
        TimKiem();

    }

    private void btn_capNhatActionPerformed(java.awt.event.ActionEvent evt) {
        if (jtable_NhanVien.getSelectedRow() == -1) {
            JOptionPane.showMessageDialog(null, "Vui lòng chọn dữ liệu để cập nhật");
            return;
        }

        if (btn_Sua.getText().equals("SỬA")) {
            isThemActive = false;
            isThemNhanVienClicked(isThemActive);
        } else if (btn_Sua.getText().equals("Huỷ")) {
        	jtextfield_Timkiem.setEnabled(true);
            huyThaoTac();
        }
    }

    private void btn_luuActionPerformed(java.awt.event.ActionEvent evt) throws SQLException {
        if (isThemActive) {
            themNhanVien();
            jtextfield_Timkiem.setEnabled(true);
        } else if (isSuaActive) {
            capNhatNhanVien();
            jtextfield_Timkiem.setEnabled(true);
        }
    }

    private void btn_LamMoiActionPerformed(java.awt.event.ActionEvent evt) {

        clearInput();
    }

    private void capNhatNhanVien() {
        if (!validateData()) {
            return;
        }
        try {
            PrintWriter pw = new PrintWriter(socket.getOutputStream(), true);
            pw.println("staff");
            pw.println("update");

            boolean gioiTinh = Combobox_GioiTinh.getSelectedItem() == "Nam" ? true : false;
            String chucVu = combobox_ChucVu.getSelectedItem().toString();
            String json = "{\"maNhanVien\":\"" + jtextfield_MaNhanVien.getText() + "\",\"tenNhanVien\":\"" + jtextfield_TenNhanVien.getText() + "\",\"soDienThoai\":\"" + jtextfield_SoDienThoai.getText() + "\",\"gioiTinh\":" + gioiTinh + ",\"chucVu\":\"" + chucVu + "\",\"email\":\"" + jtextfield_Email.getText() + "\"}";
            pw.println(json);

            Scanner sc = new Scanner(socket.getInputStream());
            String response = sc.nextLine();
            if (response.equals("Update success")) {
                JOptionPane.showMessageDialog(null, "Cập nhật thành công");
                loadtableNhanVien();
                clearInput();
                huyThaoTac();
                return;
            }
            JOptionPane.showMessageDialog(null, "Cập nhật thất bại");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
//        NhanVien nhanVien = NV_DAO.getNhanVienByID((String) jtable_NhanVien.getValueAt(jtable_NhanVien.getSelectedRow(), 0));
//        nhanVien.setEmail(jtextfield_Email.getText());
//        nhanVien.setTenNhanVien(jtextfield_TenNhanVien.getText());
//        nhanVien.setSoDienThoai(jtextfield_SoDienThoai.getText());
//        nhanVien.setMaNhanVien(jtextfield_MaNhanVien.getText());
//        nhanVien.setGioiTinh(Combobox_GioiTinh.getSelectedItem().toString() == "Nam" ? true : false);
//        nhanVien.setChucVu(combobox_ChucVu.getSelectedItem().toString());
//        if (NV_DAO.updateNhanVien(nhanVien) != -1) {
//            JOptionPane.showMessageDialog(null, "Cập nhật thành công");
//            loadtableNhanVien();
//            clearInput();
//            huyThaoTac();
//            return;
//        }
        JOptionPane.showMessageDialog(null, "Cập nhật thất bại");

    }

    private void tbl_danhSachNhanVienMousePressed(java.awt.event.MouseEvent evt) {
        String id = (String) jtable_NhanVien.getValueAt(jtable_NhanVien.getSelectedRow(), 0);
        try {
            PrintWriter pw = new PrintWriter(socket.getOutputStream(), true);
            pw.println("staff");
            pw.println("getById");
            pw.println(id);

            Scanner sc = new Scanner(socket.getInputStream());
            String nhanVien = sc.nextLine();
            if (nhanVien.equals("null")) {
                System.out.println("Không tìm thấy nhân viên");
                return;
            }
            NhanVien nv = new Gson().fromJson(nhanVien, NhanVien.class);
            thongTinNhanVien(nv);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
//        NhanVien nhanVien = NV_DAO.getNhanVienByID(id);
//        if (nhanVien == null) {
//            System.out.println("Không tìm thấy nhân viên");
//            return;
//        }
//        thongTinNhanVien(nhanVien);
    }

    public void TimKiem() {
        jtable_NhanVien.clearSelection();
        clearTable();
        DefaultTableModel dtm = (DefaultTableModel) jtable_NhanVien.getModel();
        String timKiem = "";
        if (jtextfield_Timkiem.getText().length() > 0) {
            timKiem = jtextfield_Timkiem.getText();
        }
        try {
            PrintWriter pw = new PrintWriter(socket.getOutputStream(), true);
            pw.println("staff");
            pw.println("getAll");

            Scanner sc = new Scanner(socket.getInputStream());
            String list = sc.nextLine();
            ArrayList<NhanVien> listNhanVien = new ArrayList<>();
            listNhanVien = new Gson().fromJson(list, new TypeToken<ArrayList<NhanVien>>() {
            }.getType());
            for (NhanVien nv : listNhanVien) {
                String gioitinh = new String();
                if (nv.isGioiTinh() == true) {
                    gioitinh = "Nam";
                } else {
                    gioitinh = "Nữ";
                }

                if (nv.getMaNhanVien().toLowerCase().contains(timKiem.toLowerCase())
                        || nv.getTenNhanVien().toLowerCase().contains(timKiem.toLowerCase())
                        || nv.getSoDienThoai().toLowerCase().contains(timKiem.toLowerCase())
                        || nv.getEmail().toLowerCase().contains(timKiem.toLowerCase())) {
                    Object[] rowData = {nv.getMaNhanVien(), nv.getTenNhanVien(), nv.getSoDienThoai(), gioitinh, nv.getChucVu(), nv.getEmail()};
                    dtm.addRow(rowData);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
//        ArrayList<NhanVien> listNhanVien = NV_DAO.getAllNhanVien();
//        for (NhanVien nv : listNhanVien) {
//            String gioitinh = new String();
//            if (nv.isGioiTinh() == true) {
//                gioitinh = "Nam";
//            } else {
//                gioitinh = "Nữ";
//            }
//
//            if (nv.getMaNhanVien().toLowerCase().contains(timKiem.toLowerCase())
//                    || nv.getTenNhanVien().toLowerCase().contains(timKiem.toLowerCase())
//                    || nv.getSoDienThoai().toLowerCase().contains(timKiem.toLowerCase())
//                    || nv.getEmail().toLowerCase().contains(timKiem.toLowerCase())) {
//                Object[] rowData = {nv.getMaNhanVien(), nv.getTenNhanVien(), nv.getSoDienThoai(), gioitinh, nv.getChucVu(), nv.getEmail()};
//                dtm.addRow(rowData);
//            }
//
//        }
    }

    private void huyThaoTac() {
        clearInput();
        isSuaActive = false;
        isThemActive = false;
        btn_Them.setText("THÊM");
        btn_Sua.setText("SỬA");
        btn_Them.setEnabled(true);
        btn_Sua.setEnabled(true);
        btn_Luu.setEnabled(false);
        jtextfield_MaNhanVien.setText("");

        isInputActive(false);
        jtable_NhanVien.clearSelection();

    }

    private void thongTinNhanVien(NhanVien nhanVien) {
        jtextfield_MaNhanVien.setText(nhanVien.getMaNhanVien());
        jtextfield_TenNhanVien.setText(nhanVien.getTenNhanVien());
        jtextfield_SoDienThoai.setText(nhanVien.getSoDienThoai());
        Combobox_GioiTinh.setSelectedItem(nhanVien.isGioiTinh() == true ? "Nam" : "Nữ");
        combobox_ChucVu.setSelectedItem(nhanVien.getChucVu());
        jtextfield_Email.setText(nhanVien.getEmail());
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel3.setBounds(539, 10, 80, 40);
        jLabel4 = new javax.swing.JLabel();
        jLabel4.setBounds(10, 110, 140, 40);
        jLabel5 = new javax.swing.JLabel();
        jLabel5.setBounds(539, 110, 140, 40);
        jLabel6 = new javax.swing.JLabel();
        jLabel6.setBounds(10, 60, 140, 40);
        jLabel7 = new javax.swing.JLabel();
        jLabel7.setBounds(539, 59, 100, 40);
        jtextfield_Timkiem = new javax.swing.JTextField();
        jtextfield_Timkiem.setBounds(160, 160, 305, 40);
        Combobox_GioiTinh = new javax.swing.JComboBox<>();
        Combobox_GioiTinh.setBounds(648, 12, 346, 40);
        jtextfield_SoDienThoai = new javax.swing.JTextField();
        jtextfield_SoDienThoai.setBounds(160, 110, 230, 40);
        jtextfield_Email = new javax.swing.JTextField();
        jtextfield_Email.setBounds(648, 112, 346, 40);
        btn_timKiem = new javax.swing.JButton();
        btn_timKiem.setBounds(485, 161, 122, 40);
        jLabel10 = new javax.swing.JLabel();
        jLabel10.setBounds(80, 160, 70, 40);
        jLabel9 = new javax.swing.JLabel();
        jLabel9.setBounds(10, 10, 140, 40);
        jtextfield_MaNhanVien = new javax.swing.JTextField();
        jtextfield_MaNhanVien.setBounds(160, 10, 230, 40);
        btn_Them = new javax.swing.JButton();
        btn_Them.setBounds(1046, 10, 147, 40);
        btn_Luu = new javax.swing.JButton();
        btn_Luu.setBounds(1046, 60, 147, 40);
        btn_Sua = new javax.swing.JButton();
        btn_Sua.setBounds(1046, 110, 147, 40);
        btn_LamMoi = new javax.swing.JButton();
        btn_LamMoi.setBounds(635, 163, 115, 40);
        combobox_ChucVu = new javax.swing.JComboBox<>();
        combobox_ChucVu.setBounds(649, 60, 345, 40);
        jtextfield_TenNhanVien = new javax.swing.JTextField();
        jtextfield_TenNhanVien.setBounds(160, 60, 230, 40);
        jScrollPane1 = new javax.swing.JScrollPane();
        jtable_NhanVien = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 255, 255));
        setPreferredSize(new java.awt.Dimension(1260, 530));
        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        jPanel1.setLayout(null);

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel3.setText("GIỚI TÍNH:");
        jPanel1.add(jLabel3);

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel4.setText("SỐ ĐIỆN THOẠI:");
        jPanel1.add(jLabel4);

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel5.setText("EMAIL:");
        jPanel1.add(jLabel5);

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel6.setText("TÊN NHÂN VIÊN:");
        jPanel1.add(jLabel6);

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel7.setText("CHỨC VỤ:");
        jPanel1.add(jLabel7);

        jtextfield_Timkiem.setFont(new java.awt.Font("Segoe UI", 2, 12)); // NOI18N
        jtextfield_Timkiem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField2ActionPerformed(evt);
            }
        });
        jPanel1.add(jtextfield_Timkiem);

        Combobox_GioiTinh.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Nam","Nữ" }));
        Combobox_GioiTinh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
               Combobox_GioiTinh.actionPerformed(evt);
            }
        });
        jPanel1.add(Combobox_GioiTinh);
        jPanel1.add(jtextfield_SoDienThoai);
        jPanel1.add(jtextfield_Email);

        btn_timKiem.setBackground(new java.awt.Color(255, 153, 0));
        btn_timKiem.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btn_timKiem.setForeground(new java.awt.Color(255, 255, 255));
        btn_timKiem.setText("TÌM KIẾM");
        btn_timKiem.setBorder(null);
        btn_timKiem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_timNhanVienActionPerformed(evt);
            }
        });
        jPanel1.add(btn_timKiem);

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 153, 0));
        jLabel10.setText("Tìm kiếm:");
        jPanel1.add(jLabel10);

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel9.setText("MÃ NHÂN VIÊN:");
        jPanel1.add(jLabel9);
        jPanel1.add(jtextfield_MaNhanVien);

        btn_Them.setBackground(new java.awt.Color(255, 102, 102));
        btn_Them.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btn_Them.setForeground(new java.awt.Color(255, 255, 255));
        btn_Them.setIcon(new ImageIcon("src/main/java/Img/Add-icon.png")); // NOI18N
        btn_Them.setText("THÊM");
        btn_Them.setBorder(null);
        btn_Them.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
              btn_themActionPerformed(evt);
            }
        });
        jtable_NhanVien.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tbl_danhSachNhanVienMousePressed(evt);
            }
        });
        jPanel1.add(btn_Them);

        btn_Luu.setBackground(new java.awt.Color(255, 51, 102));
        btn_Luu.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btn_Luu.setForeground(new java.awt.Color(255, 255, 255));
        btn_Luu.setIcon(new ImageIcon("src/main/java/Img/icons8_downloads_30px.png")); // NOI18N
        btn_Luu.setText("LƯU");
        btn_Luu.setBorder(null);
        btn_Luu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
               try {
				btn_luuActionPerformed(evt);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            }
        });
        jPanel1.add(btn_Luu);

        btn_Sua.setBackground(new java.awt.Color(153, 255, 204));
        btn_Sua.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btn_Sua.setForeground(new java.awt.Color(255, 255, 255));
        btn_Sua.setIcon(new ImageIcon("src/main/java/Img/icons8_maintenance_30px.png")); // NOI18N
        btn_Sua.setText("SỬA");
        btn_Sua.setBorder(null);
        btn_Sua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
              btn_capNhatActionPerformed(evt);
            }
        });
        jPanel1.add(btn_Sua);

        btn_LamMoi.setBackground(new java.awt.Color(102, 255, 102));
        btn_LamMoi.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btn_LamMoi.setForeground(new java.awt.Color(255, 255, 255));
        btn_LamMoi.setIcon(new ImageIcon("src/main/java/Img/Refresh-icon.png")); // NOI18N
        btn_LamMoi.setText("LÀM MỚI");
        btn_LamMoi.setBorder(null);
        btn_LamMoi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
           
               btn_LamMoiActionPerformed(evt);
            }
        });
        jPanel1.add(btn_LamMoi);

        combobox_ChucVu.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Nhân viên","Quản lý" }));
        combobox_ChucVu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox3ActionPerformed(evt);
            }
        });
        jPanel1.add(combobox_ChucVu);
        jPanel1.add(jtextfield_TenNhanVien);

        add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 50, 1260, 220));

        jScrollPane1.setBackground(new java.awt.Color(255, 255, 255));

        jtable_NhanVien.setModel(new DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "MÃ NHÂN VIÊN", "TÊN NHÂN VIÊN", "SỐ ĐIỆN THOẠI", "GIỚI TÍNH", "CHỨC VỤ", "EMAIL"
            }
        ));
        jScrollPane1.setViewportView(jtable_NhanVien);

        add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 270, 1260, 380));

        jLabel1.setBackground(new java.awt.Color(255, 255, 255));
        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 51, 0));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("QUẢN LÝ NHÂN VIÊN");
        add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1300, 40));
    }// </editor-fold>//GEN-END:initComponents

    private void btn3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btn3ActionPerformed

    private void btn6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn6ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btn6ActionPerformed

    private void btn4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btn4ActionPerformed

    private void btn1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btn1ActionPerformed

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox1ActionPerformed


    private void btn8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn8ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btn8ActionPerformed

    private void jComboBox3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox3ActionPerformed

    private void jTextField2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField2ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_timKiem;
    private javax.swing.JButton btn_Them;
    private javax.swing.JButton btn_LamMoi;
    private javax.swing.JButton btn_Luu;
    private javax.swing.JButton btn_Sua;
    private javax.swing.JComboBox<String> Combobox_GioiTinh;
    private javax.swing.JComboBox<String> combobox_ChucVu;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jtable_NhanVien;
    private javax.swing.JTextField jtextfield_Timkiem;
    private javax.swing.JTextField jtextfield_SoDienThoai;
    private javax.swing.JTextField jtextfield_MaNhanVien;
    private javax.swing.JTextField jtextfield_TenNhanVien;
    private javax.swing.JTextField jtextfield_Email;
    // End of variables declaration//GEN-END:variables
}
