package entity;


import java.io.Serializable;

import constant.Constants;
import dao.NhaCungCapDAO;
import jakarta.persistence.*;
//import org.apache.commons.compress.harmony.unpack200.bytecode.forms.ThisFieldRefForm;

//import dao.NhaCungCapDAO;
//import dao.NhanVienDAO;
@Entity @Table(name = "nhacungcap")
public class NhaCungCap implements Serializable{
	private static final long serialVersionUID = 3502783364597853840L;
	@Id
	private String maNCC;
	@Column(columnDefinition = "nvarchar(255)")
    private String tenNCC;
	@Column(columnDefinition = "nvarchar(255)")
    private String diaChi;
    private String SoDienThoai;
    private String email;
    
	public NhaCungCap() {
		super();
	}

	public NhaCungCap(String maNCC, String tenNCC, String diaChi,String SDT,String email) {
		super();
		this.maNCC = maNCC;
		this.tenNCC = tenNCC;
		this.diaChi = diaChi;
		this.SoDienThoai = SDT;
		this.email = email;
	}
//	public String auto_ID(){
//		EntityManager em = Persistence.createEntityManagerFactory(Constants.DatabaseType).createEntityManager();
//	       NhaCungCapDAO ncc= new NhaCungCapDAO(em);
//	        String idPrefix = "NCC";
//	        int length = ncc.getAllNhaCungCap().size();
//	        String finalId = idPrefix + String.format("%02d", length + 1);
//	        return finalId;
//	    }
	public NhaCungCap(String tenNCC, String diaChi) {
		super();
		this.tenNCC = tenNCC;
		this.diaChi = diaChi;
	}

	public String getMaNCC() {
		return maNCC;
	}

	public void setMaNCC(String maNCC) {
		this.maNCC = maNCC;
	}

	public String getTenNCC() {
		return tenNCC;
	}

	public void setTenNCC(String tenNCC) {
		this.tenNCC = tenNCC;
	}

	public String getDiaChi() {
		return diaChi;
	}

	public void setDiaChi(String diaChi) {
		this.diaChi = diaChi;
	}

	public String getSoDienThoai() {
		return SoDienThoai;
	}

	public void setSoDienThoai(String soDienThoai) {
		SoDienThoai = soDienThoai;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
    
    
}
