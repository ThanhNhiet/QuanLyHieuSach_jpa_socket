/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

import jakarta.persistence.*;

import java.io.Serializable;

/**
 *
 * @author LENOVO
 */
@Entity @Table(name = "taikhoan")
public class TaiKhoan implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 5013107283532448504L;
	@Id
    @OneToOne
    @JoinColumn(name = "maNhanVien")
   private NhanVien tenDangNhap;
   private String matKhau;
   private String phanQuyen;

    public TaiKhoan(NhanVien tenDangNhap, String matKhau, String phanQuyen) {
        this.tenDangNhap = tenDangNhap;
        this.matKhau = matKhau;
        this.phanQuyen = phanQuyen;
    }

    public TaiKhoan(NhanVien tenDangNhap, String matKhau) {
        this.tenDangNhap = tenDangNhap;
        this.matKhau = matKhau;
    }

    public TaiKhoan(NhanVien tenDangNhap) {
        this.tenDangNhap = tenDangNhap;
    }
//        public taiKhoan(String tenDangNhap, String matKhau, String phanQuyen) {
//        this.tenDangNhap = tenDangNhap;
//        this.matKhau = matKhau;
//        this.phanQuyen = phanQuyen;
//    }
//
//    public taiKhoan(String tenDangNhap) {
//        this.tenDangNhap = tenDangNhap;
//    }
//
//    public taiKhoan(String tenDangNhap, String matKhau) {
//        this.tenDangNhap = tenDangNhap;
//        this.matKhau = matKhau;
//    }

    public TaiKhoan() {
    }

    public NhanVien getTenDangNhap() {
        return tenDangNhap;
    }

    public void setTenDangNhap(NhanVien tenDangNhap) {
        this.tenDangNhap = tenDangNhap;
    }

    public String getMatKhau() {
        return matKhau;
    }

    public void setMatKhau(String matKhau) {
        this.matKhau = matKhau;
    }

    public String getPhanQuyen() {
        return phanQuyen;
    }

    public void setPhanQuyen(String phanQuyen) {
        this.phanQuyen = phanQuyen;
    }
   
}
