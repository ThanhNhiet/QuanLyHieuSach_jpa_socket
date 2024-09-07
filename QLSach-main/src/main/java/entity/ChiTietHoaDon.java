package entity;

import java.io.Serializable;

//import dao.ChiTietHoaDonDAO;
//import dao.SanPhamDAO;

import jakarta.persistence.*;

@Entity
@Table(name = "chitiethoadon")
public class ChiTietHoaDon implements Serializable{
    private static final long serialVersionUID = 4756319012794332621L;
	@Id
    @ManyToOne
    @JoinColumn(name = "maSP")
    private SanPham sanPham;
    @Id
    @ManyToOne
    @JoinColumn(name = "maHoaDon")
    private HoaDon hoaDon;
    private int soLuong;
    public ChiTietHoaDon() {
    }

    public ChiTietHoaDon(SanPham sanPham, HoaDon hoaDon, int soLuong) {
        this.sanPham = sanPham;
        this.hoaDon = hoaDon;
        this.soLuong = soLuong;
    }

    public SanPham getSanPham() {
        return sanPham;
    }

    public void setSanPham(SanPham sanPham) {
        this.sanPham = sanPham;
    }

    public HoaDon getHoaDon() {
        return hoaDon;
    }

    public void setHoaDon(HoaDon hoaDon) {
        this.hoaDon = hoaDon;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }
    
    @Override
	public String toString() {
		return "ChiTietHoaDon [sanPham=" + sanPham.getTenSP() + ", hoaDon=" + hoaDon + ", soLuong=" + soLuong + "]";
	}

	public double thanhTien() {
    	double thanhTien = 0;
    	thanhTien = this.getSanPham().getDonGiaBan() * this.getSoLuong();
        
        return thanhTien;
    }
    
}
