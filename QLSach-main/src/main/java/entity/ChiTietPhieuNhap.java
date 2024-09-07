/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

import jakarta.persistence.*;

/**
 *
 * @author trana
 */
@Entity @Table(name = "chitietphieunhap")
public class ChiTietPhieuNhap {
    @Id
    @ManyToOne
    @JoinColumn(name = "maSP")
    private SanPham sanPham;
    @Id
    @ManyToOne
    @JoinColumn(name = "maPhieuNhap")
    private PhieuNhap phieuNhap;
    private int soLuong;
    private double donGiaMua;

    public ChiTietPhieuNhap() {
    }

    public ChiTietPhieuNhap(SanPham sanPham, PhieuNhap phieuNhap, int soLuong, double donGiaMua) {
        this.sanPham = sanPham;
        this.phieuNhap = phieuNhap;
        this.soLuong = soLuong;
        this.donGiaMua = donGiaMua;
    }

    public SanPham getSanPham() {
        return sanPham;
    }

    public void setSanPham(SanPham sanPham) {
        this.sanPham = sanPham;
    }

    public PhieuNhap getPhieuNhap() {
        return phieuNhap;
    }

    public void setPhieuNhap(PhieuNhap phieuNhap) {
        this.phieuNhap = phieuNhap;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public double getDonGiaMua() {
        return donGiaMua;
    }

    public void setDonGiaMua(double donGiaMua) {
        this.donGiaMua = donGiaMua;
    }
    
    public double thanhTien() {
        double thanhTien = 0;
        thanhTien = donGiaMua * this.getSoLuong();

        return thanhTien;
    }
}
