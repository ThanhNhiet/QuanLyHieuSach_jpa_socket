package dao;

import entity.HoaDon;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.NoResultException;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class HoaDonDAO {
    private EntityManager em;
    private EntityTransaction et;
//    private NhanVienDAO nhanVien_DAO = new NhanVienDAO(em);
//    private KhachHangDAO khachHang_DAO = new KhachHangDAO(em);

    public HoaDonDAO(EntityManager em) {
        this.em = em;
        this.et = em.getTransaction();
    }

    public List<HoaDon> getAllHoaDon() {
        return em.createQuery("SELECT hd FROM HoaDon hd", HoaDon.class).getResultList();
    }

    public HoaDon getHoaDonByID(String maId) {
        try {
            return em.createQuery("SELECT hd FROM HoaDon hd WHERE hd.maHD LIKE :maId", HoaDon.class)
                     .setParameter("maId", "%" + maId + "%")
                     .getSingleResult();
        } catch (NoResultException e) {
            return null; // Trả về null nếu không tìm thấy kết quả
        }
    }
    
	public String auto_IDPHoaDon() {
		String idPrefix = "HD";
		int length = getAllHoaDon().size();
		String finalId = idPrefix + String.format("%05d", length + 1);
		return finalId;
	}
//    
//    public ArrayList<HoaDon> getAllHoaDon(String tenKhachHang, String tenNhanVien) {
//        return (ArrayList<HoaDon>) em.createQuery("SELECT hd FROM HoaDon hd WHERE hd.khachHang.tenKhachHang like :tenKhachHang and hd.nhanVien.tenNhanVien like :tenNhanVien", HoaDon.class)
//                .setParameter("tenKhachHang", "%" + tenKhachHang + "%")
//                .setParameter("tenNhanVien", "%" + tenNhanVien + "%")
//                .getResultList();
//    }
//    
//    public ArrayList<HoaDon> getAllHoaDon(String tenKhachHang, String tenNhanVien, Date tuNgay, Date denNgay) {
//        return (ArrayList<HoaDon>) em.createQuery("SELECT hd FROM HoaDon hd WHERE hd.khachHang.tenKhachHang like :tenKhachHang and hd.nhanVien.tenNhanVien like :tenNhanVien and hd.ngayLapHD between :tuNgay and :denNgay", HoaDon.class)
//                .setParameter("tenKhachHang", "%" + tenKhachHang + "%")
//                .setParameter("tenNhanVien", "%" + tenNhanVien + "%")
//                .setParameter("tuNgay", tuNgay)
//                .setParameter("denNgay", denNgay)
//                .getResultList();
//    }
//
    public List<HoaDon> getHoaDonByDate(Date ngayBatDau, Date ngayKetThuc) {
        return (ArrayList<HoaDon>) em.createQuery("SELECT hd FROM HoaDon hd WHERE hd.ngayLapHD between :ngayBatDau and :ngayKetThuc", HoaDon.class)
                .setParameter("ngayBatDau", ngayBatDau)
                .setParameter("ngayKetThuc", ngayKetThuc)
                .getResultList();
    }
//
//    public HoaDon getHoaDonById(String id) {
//        return em.find(HoaDon.class, id);
//    }
//    
    public int addHoaDon(HoaDon hoaDon) {
        try {
            et.begin();
            em.persist(hoaDon);
            et.commit();
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            et.rollback();
        }
        return 0;
    }
}
