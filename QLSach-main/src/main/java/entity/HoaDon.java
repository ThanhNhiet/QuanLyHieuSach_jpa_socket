package entity;

import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

//import dao.ChiTietHoaDonDAO;
//import dao.HoaDonDAO;
import constant.Constants;
import dao.ChiTietHoaDonDAO;
import dao.HoaDonDAO;
import jakarta.persistence.*;

@Entity
@Table(name = "hoadon")
public class HoaDon implements Serializable{
	
    private static final long serialVersionUID = 601992993010787781L;
	@Id
    private String maHD;
    private Date ngayLapHD;
    @ManyToOne
    @JoinColumn(name = "maNV")
    private NhanVien nhanVien;
    @ManyToOne
    @JoinColumn(name = "maKH")
    private KhachHang khachHang;

//    private String auto_IDPHoaDon() {
//        EntityManager em = Persistence.createEntityManagerFactory(Constants.DatabaseType).createEntityManager();
//        //auto gen id hóa đơn dạng HDXXXXXX
//        HoaDonDAO phieuNhap_DAO = new HoaDonDAO(em);
//        String idPrefix = "HD";
//        int length = phieuNhap_DAO.getAllHoaDon().size();
//        String finalId = idPrefix + String.format("%05d", length + 1);
//        return finalId;
//
//    }
//
//    public HoaDon() {
//        this.maHD = auto_IDPHoaDon();
//    }
    
    public HoaDon() {}
    
    

    public HoaDon(String maHD) {
		super();
		this.maHD = maHD;
	}



	public HoaDon(HoaDon hd) {
        this.ngayLapHD = hd.ngayLapHD;
        this.nhanVien = hd.nhanVien;
        this.khachHang = hd.khachHang;
    }

    public HoaDon(Date ngayLapHD, NhanVien nhanVien, KhachHang khachHang) {
        super();
        this.ngayLapHD = ngayLapHD;
        this.nhanVien = nhanVien;
        this.khachHang = khachHang;
    }

    public HoaDon(String maHD, Date ngayLapHD, NhanVien nhanVien, KhachHang khachHang) {
        super();
        this.maHD = maHD;
        this.ngayLapHD = ngayLapHD;
        this.nhanVien = nhanVien;
        this.khachHang = khachHang;
    }

    public String getMaHD() {
        return maHD;
    }

    public void setMaHD(String maHD) {
        this.maHD = maHD;
    }

    public Date getNgayLapHD() {
        return ngayLapHD;
    }

    public void setNgayLapHD(Date ngayLapHD) {
        this.ngayLapHD = ngayLapHD;
    }

    public NhanVien getNhanVien() {
        return nhanVien;
    }

    public void setNhanVien(NhanVien nhanVien) {
        this.nhanVien = nhanVien;
    }

    public KhachHang getKhachHang() {
        return khachHang;
    }

    public void setKhachHang(KhachHang khachHang) {
        this.khachHang = khachHang;
    }

    public double tongTien() {
        EntityManager em = Persistence.createEntityManagerFactory(Constants.DatabaseType).createEntityManager();
        double tongTien = 0;
        ChiTietHoaDonDAO cthd_DAO = new ChiTietHoaDonDAO(em);
        List<ChiTietHoaDon> listChiTietHoaDon = cthd_DAO.getAllCTHDByHoaDon(this);

        for (ChiTietHoaDon cthd : listChiTietHoaDon) {
            tongTien += cthd.thanhTien();
        }

        return tongTien;
    }

	@Override
	public String toString() {
		return "HoaDon [maHD=" + maHD + ", ngayLapHD=" + ngayLapHD + ", nhanVien=" + nhanVien.getMaNhanVien() + ", khachHang="
				+ khachHang.getMaKhachHang() + "]";
	}



	@Override
	public int hashCode() {
		return Objects.hash(maHD);
	}



	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		HoaDon other = (HoaDon) obj;
		return Objects.equals(maHD, other.maHD);
	}
    
    
}
