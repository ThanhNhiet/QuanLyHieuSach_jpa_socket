package entity;

import constant.Constants;
import dao.VppDao;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;

@Entity
@DiscriminatorValue("VPP")
public class VPP extends SanPham {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1282965267097119153L;
	@Column(columnDefinition = "nvarchar(255)")
	private String xuatXu;
	@Column(columnDefinition = "nvarchar(255)")
	private String mauSac;
	@Column(columnDefinition = "nvarchar(255)")
	private String chatLieu;
	
	public VPP() {
		super();	
	}

	/**
	 * @param maSP
	 * @param tenSP
	 * @param nhaCungCap
	 * @param soLuongTK
	 * @param donGiaBan
	 * @param xuatXu
	 * @param mauSac
	 * @param chatLieu
	 */
	

	/**
	 * @param maSP
	 * @param tenSP
	 * @param nhaCungCap
	 * @param soLuongTK
	 * @param loaiSP
	 * @param donGiaBan
	 * @param xuatXu
	 * @param mauSac
	 * @param chatLieu
	 */
	



	/**
	 * @param tenSP
	 * @param nhaCungCap
	 * @param soLuongTK
	 * @param loaiSP
	 * @param donGiaBan
	 * @param xuatXu
	 * @param mauSac
	 * @param chatLieu
	 */
	

	

	/**
//	 * @param maSP
//	 * @param tenSP
//	 * @param nhaCungCap
//	 * @param soLuongTK
//	 * @param donGiaBan
//	 * @param hinhAnh
//	 * @param xuatXu
//	 * @param mauSac
//	 * @param chatLieu
	 */

	public VPP(String maSP, String tenSP, NhaCungCap nhaCungCap, int soLuongTK, double donGiaBan, String hinhAnh,
			String xuatXu, String mauSac, String chatLieu) {
		super(maSP, tenSP, nhaCungCap, soLuongTK, donGiaBan, hinhAnh);
		this.xuatXu = xuatXu;
		this.mauSac = mauSac;
		this.chatLieu = chatLieu;
	}
	public String getMauSac() {
		return mauSac;
	}
	public void setMauSac(String mauSac) {
		this.mauSac = mauSac;
	}

	public String getChatLieu() {
		return chatLieu;
	}

	public void setChatLieu(String chatLieu) {
		this.chatLieu = chatLieu;
	}
	public String getXuatXu() {
		return xuatXu;
	}
	public void setXuatXu(String xuatXu) {
		this.xuatXu = xuatXu;
	}
}
