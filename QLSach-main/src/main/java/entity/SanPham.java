package entity;

import java.io.Serializable;
import java.lang.reflect.Type;

import com.google.gson.InstanceCreator;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter @Getter
@Entity
@Table(name = "sanpham")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "loaiSP", discriminatorType = DiscriminatorType.STRING)
public abstract class SanPham implements Serializable{
	private static final long serialVersionUID = 3501484191432628748L;
	@Id
	private String maSP;
	@Column(columnDefinition = "nvarchar(255)")
	private String tenSP;
//	@ManyToOne(fetch = FetchType.LAZY)
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "maNCC")
	private NhaCungCap nhaCungCap;
	private int soLuongTK;
//	private String loaiSP;
	private double donGiaBan;
	private String hinhAnh;
	
	

	public SanPham() {
		super();
	}

//	public SanPham(String tenSP, NhaCungCap nhaCungCap, int soLuongTK, String loaiSP, double donGiaBan) {
//		super();
//		this.tenSP = tenSP;
//		this.nhaCungCap = nhaCungCap;
//		this.soLuongTK = soLuongTK;
//		this.loaiSP = loaiSP;
//		this.donGiaBan = donGiaBan;
//	}


	public SanPham(String tenSP, NhaCungCap nhaCungCap, int soLuongTK, double donGiaBan, String hinhAnh) {
		this.tenSP = tenSP;
		this.nhaCungCap = nhaCungCap;
		this.soLuongTK = soLuongTK;
		this.donGiaBan = donGiaBan;
		this.hinhAnh = hinhAnh;
	}

	public SanPham(String maSP, String tenSP, NhaCungCap nhaCungCap, int soLuongTK, double donGiaBan) {
		this.maSP = maSP;
		this.tenSP = tenSP;
		this.nhaCungCap = nhaCungCap;
		this.soLuongTK = soLuongTK;
		this.donGiaBan = donGiaBan;
	}

	/**
	 * @param maSP
	 * @param tenSP
	 * @param nhaCungCap
	 * @param soLuongTK
	 * @param donGiaBan
	 * @param hinhAnh
	 */
	public SanPham(String maSP, String tenSP, NhaCungCap nhaCungCap, int soLuongTK, double donGiaBan, String hinhAnh) {
		super();
		this.maSP = maSP;
		this.tenSP = tenSP;
		this.nhaCungCap = nhaCungCap;
		this.soLuongTK = soLuongTK;
		this.donGiaBan = donGiaBan;
		this.hinhAnh = hinhAnh;
	}

//	public SanPham(String maSP, String tenSP, NhaCungCap nhaCungCap, int soLuongTK, String loaiSP, double donGiaBan) {
//		super();
//		this.maSP = maSP;
//		this.tenSP = tenSP;
//		this.nhaCungCap = nhaCungCap;
//		this.soLuongTK = soLuongTK;
//		this.loaiSP = loaiSP;
//		this.donGiaBan = donGiaBan;
//	}

	public String getMaSP() {
		return maSP;
	}

	public void setMaSP(String maSP) {
		this.maSP = maSP;
	}

	public String getTenSP() {
		return tenSP;
	}

	public void setTenSP(String tenSP) {
		this.tenSP = tenSP;
	}

	public NhaCungCap getNhaCungCap() {
		return nhaCungCap;
	}

	public void setNhaCungCap(NhaCungCap nhaCungCap) {
		this.nhaCungCap = nhaCungCap;
	}

	public int getSoLuongTK() {
		return soLuongTK;
	}

	public void setSoLuongTK(int soLuongTK) {
		this.soLuongTK = soLuongTK;
	}

//	public String getLoaiSP() {
//		return loai;
//	}
//
//	public void setLoaiSP(String loaiSP) {
//		this.loaiSP = loaiSP;
//	}

	public double getDonGiaBan() {
		return donGiaBan;
	}

	public void setDonGiaBan(double donGiaBan) {
		this.donGiaBan = donGiaBan;
	}
	
	public String getHinhAnh() {
		return hinhAnh;
	}

	public void setHinhAnh(String hinhAnh) {
		this.hinhAnh = hinhAnh;
	}
}
