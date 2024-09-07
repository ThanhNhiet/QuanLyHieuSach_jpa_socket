package entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.io.Serializable;
import java.util.Objects;
@Entity @Table(name = "nhaxuatban")
public class NhaXuatBan implements Serializable{
	private static final long serialVersionUID = 1181055418446045921L;
	@Id
	private String maNXB;
	@Column(columnDefinition = "nvarchar(255)")
	private String tenNXB;
	@Column(columnDefinition = "nvarchar(255)")
	private String diaChi;
	private String soDienThoai;
	public NhaXuatBan(String maNXB, String tenNXB, String diaChi, String soDienThoai) {
		super();
		this.maNXB = maNXB;
		this.tenNXB = tenNXB;
		this.diaChi = diaChi;
		this.soDienThoai = soDienThoai;
	}
	public NhaXuatBan() {
		super();
	}
	
	/**
	 * @param maNXB
	 */
	public NhaXuatBan(String maNXB) {
		super();
		this.maNXB=maNXB;
	}
	
	public String getMaNXB() {
		return maNXB;
	}
	public void setMaNXB(String maNXB) {
		this.maNXB = maNXB;
	}
	public String getTenNXB() {
		return tenNXB;
	}
	public void setTenNXB(String tenNXB) {
		this.tenNXB = tenNXB;
	}
	public String getDiaChi() {
		return diaChi;
	}
	public void setDiaChi(String diaChi) {
		this.diaChi = diaChi;
	}
	public String getSoDienThoai() {
		return soDienThoai;
	}
	public void setSoDienThoai(String soDienThoai) {
		this.soDienThoai = soDienThoai;
	}
	@Override
	public String toString() {
		return "NhaXuatBan [maNXB=" + maNXB + ", tenNXB=" + tenNXB + ", diaChi=" + diaChi + ", soDienThoai="
				+ soDienThoai + "]";
	}
	@Override
	public int hashCode() {
		return Objects.hash(diaChi, maNXB, soDienThoai, tenNXB);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		NhaXuatBan other = (NhaXuatBan) obj;
		return Objects.equals(diaChi, other.diaChi) && Objects.equals(maNXB, other.maNXB)
				&& Objects.equals(soDienThoai, other.soDienThoai) && Objects.equals(tenNXB, other.tenNXB);
	}
	
}
