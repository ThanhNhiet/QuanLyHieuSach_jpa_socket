package dao;

import entity.NhanVien;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import java.util.ArrayList;
import java.util.List;

public class NhanVienDAO {
	private EntityManager em;
	private EntityTransaction et;

	public NhanVienDAO(EntityManager em) {
		this.em = em;
		this.et = em.getTransaction();
	}

	public ArrayList<NhanVien> getAllNhanVien() {
		return (ArrayList<NhanVien>) em.createQuery("SELECT nv FROM NhanVien nv", NhanVien.class).getResultList();
	}

	public boolean dieuKienQuenMatkhau(String gmail) {
		return em.createQuery("SELECT nv FROM NhanVien nv WHERE nv.email = :gmail", NhanVien.class)
				.setParameter("gmail", gmail).getResultList().size() > 0;
	}

	public NhanVien getNhanVienByID(String id) {
		return em.find(NhanVien.class, id);
	}

	// get all nhan vien theo chuc vu
	public ArrayList<NhanVien> getAllNhanVienByChucVu() {
		return (ArrayList<NhanVien>) em
				.createQuery("SELECT nv FROM NhanVien nv WHERE nv.chucVu = 'Nhân Viên'", NhanVien.class)
				.getResultList();
	}

	public NhanVien getNhanVienByName(String name) {
		return em.createQuery("SELECT nv FROM NhanVien nv WHERE nv.tenNhanVien = :name", NhanVien.class)
				.setParameter("name", name).getSingleResult();
	}

	public NhanVien getNhanVienBySdt(String sdt) {
		List<NhanVien> list = em.createQuery("SELECT nv FROM NhanVien nv WHERE nv.soDienThoai = :sdt", NhanVien.class)
				.setParameter("sdt", sdt).getResultList();
		if (!list.isEmpty())
			return list.get(0);
		return null;
	}

	public NhanVien getNhanVienByGmail(String gmail) {
		List<NhanVien> list = em.createQuery("SELECT nv FROM NhanVien nv WHERE nv.email = :gmail", NhanVien.class)
				.setParameter("gmail", gmail).getResultList();
		if (!list.isEmpty())
			return list.get(0);
		return null;
	}

	public int updateNhanVien(NhanVien nhanVien) {
		try {
			et.begin();
			em.merge(nhanVien);
			et.commit();
			return 1;
		} catch (Exception e) {
			e.printStackTrace();
			et.rollback();
		}
		return -1;
	}

	public String generateID() {
		return "NV" + String.format("%03d", getAllNhanVien().size() + 1);
	}
//    public int updateOTP(String gmail, String OTP, Timestamp expiredAt){
//        ConnectDB.getInstance();
//        Connection conn = ConnectDB.getConnection();
//        try {
//
//            String sql = "update nhanvien set OTP = ?, expriedAt = ? where email = ?";
//            PreparedStatement stmt = conn.prepareCall(sql);
//            stmt.setString(1, OTP);
//            stmt.setTimestamp(2, expiredAt);
//            stmt.setString(3, gmail);
//
//            return stmt.executeUpdate();
//        } catch (SQLException ex) {
//            Logger.getLogger(NhanVienDAO.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        return -1;
//    }

//    public int xoaNhanVien(String maNhanVien){
//        ConnectDB.getInstance();
//        Connection conn = ConnectDB.getConnection();
//        try {
//
//            String sql = "update nhanvien set isDeleted = 1 where maNhanVien = ?";
//            PreparedStatement stmt = conn.prepareCall(sql);
//            stmt.setString(1, maNhanVien);
//
////            if(taiKhoan_DAO.xoaTaiKhoan(maNhanVien) != -1);
//
//            return stmt.executeUpdate();
//        } catch (SQLException ex) {
//            Logger.getLogger(NhanVienDAO.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        return -1;
//
//    }

	public int addNhanVien(NhanVien nhanVien) {
		try {
			et.begin();
			em.persist(nhanVien);
			et.commit();
			return 1;
		} catch (Exception e) {
			e.printStackTrace();
			et.rollback();
		}
		return -1;
	}
}
