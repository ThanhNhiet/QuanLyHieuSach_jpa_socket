package dao;

import entity.ChiTietHoaDon;
import entity.HoaDon;
import entity.SanPham;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import java.util.ArrayList;
import java.util.List;

public class ChiTietHoaDonDAO {
    private EntityManager em;
    private EntityTransaction et;
//    private SanPhamDAO sanPham_DAO = new SanPhamDAO(em);
//    private HoaDonDAO hoaDon_DAO = new HoaDonDAO(em);
//    private NhanVienDAO nhanVien_DAO = new NhanVienDAO(em);
//    private KhachHangDAO khachHang_DAO = new KhachHangDAO(em);
    
    public ChiTietHoaDonDAO(EntityManager em) {
        this.em = em;
        this.et = em.getTransaction();
    }
    
    public List<ChiTietHoaDon> getAllCTHDByHoaDon(HoaDon hoaDon){
        // get by maHoaDon
        return em.createQuery("SELECT cthd FROM ChiTietHoaDon cthd WHERE cthd.hoaDon = :hoaDon", ChiTietHoaDon.class)
                .setParameter("hoaDon", hoaDon)
                .getResultList();
    }

    public List<ChiTietHoaDon> getCTHDById(String id){
        return em.createQuery("SELECT cthd FROM ChiTietHoaDon cthd WHERE cthd.hoaDon.maHD = :id", ChiTietHoaDon.class)
                .setParameter("id", id)
                .getResultList();
    }
    
    public int addChiTietHoaDon(ChiTietHoaDon cthd){
        try {
            et.begin();
            em.persist(cthd);
            et.commit();
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            et.rollback();
        }
        return 0;
    }
//    
//    public int capNhatSoLuong(SanPham sanPham){
//        try {
//            et.begin();
//            em.merge(sanPham);
//            et.commit();
//            return 1;
//        } catch (Exception e) {
//            e.printStackTrace();
//            et.rollback();
//        }
//        return 0;
//    }
}
