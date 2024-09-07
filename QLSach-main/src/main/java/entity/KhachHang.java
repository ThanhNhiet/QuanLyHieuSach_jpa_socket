package entity;

import java.io.Serializable;
import java.util.Objects;

//import dao.KhachHangDAO;
import constant.Constants;
import dao.KhachHangDAO;
import jakarta.persistence.*;

@Entity @Table(name = "khachhang")
public class KhachHang implements Serializable{
	
	private static final long serialVersionUID = 4834250152476100158L;
	@Id
	private String maKhachHang;
	@Column(columnDefinition = "nvarchar(255)")
	private String tenKhachHang;
	private String email;
	private String soDienThoai;
	private boolean gioiTinh;
	
	public KhachHang() {
		super();
	}

	public KhachHang(String tenKhachHang, String email, String soDienThoai, boolean gioiTinh) {
		super();
		this.tenKhachHang = tenKhachHang;
		this.email = email;
		this.soDienThoai = soDienThoai;
		this.gioiTinh = gioiTinh;
	}

	public KhachHang(String maKhachHang, String tenKhachHang, String email, String soDienThoai, boolean gioiTinh) {
		super();
		this.maKhachHang = maKhachHang;
		this.tenKhachHang = tenKhachHang;
		this.email = email;
		this.soDienThoai = soDienThoai;
		this.gioiTinh = gioiTinh;
	}
//	public String auto_ID(){
//		EntityManager em = Persistence.createEntityManagerFactory(Constants.DatabaseType).createEntityManager();
//		KhachHangDAO KH_DAO = new KhachHangDAO(em);
//	        String idPrefix = "KH";
//	        int length = KH_DAO.getAllKhachHang().size();
//	        String finalId = idPrefix + String.format("%04d", length + 1);
//	        return finalId;
//	    }

	public String getMaKhachHang() {
		return maKhachHang;
	}

	public void setMaKhachHang(String maKhachHang) {
		this.maKhachHang = maKhachHang;
	}

	public String getTenKhachHang() {
		return tenKhachHang;
	}

	public void setTenKhachHang(String tenKhachHang) {
		this.tenKhachHang = tenKhachHang;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSoDienThoai() {
		return soDienThoai;
	}

	public void setSoDienThoai(String soDienThoai) {
		this.soDienThoai = soDienThoai;
	}

	public boolean isGioiTinh() {
		return gioiTinh;
	}

	public void setGioiTinh(boolean gioiTinh) {
		this.gioiTinh = gioiTinh;
	}

	@Override
	public int hashCode() {
		return Objects.hash(maKhachHang);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		KhachHang other = (KhachHang) obj;
		return Objects.equals(maKhachHang, other.maKhachHang);
	}
	
	
}
