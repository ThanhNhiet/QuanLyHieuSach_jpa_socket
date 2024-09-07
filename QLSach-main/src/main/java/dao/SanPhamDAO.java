package dao;

import entity.SanPham;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Query;

import java.util.ArrayList;
import java.util.List;

public class SanPhamDAO {
	private EntityManager em;
	private EntityTransaction et;

	public SanPhamDAO(EntityManager em) {
		this.em = em;
		this.et = em.getTransaction();
	}

	public List<SanPham> getAllListSanPham() {
		return em.createQuery("SELECT sp FROM SanPham sp", SanPham.class).getResultList();
	}

	public int capNhatSoLuong(SanPham sanPham) {
		try {
			et.begin();
			em.merge(sanPham);
			et.commit();
			return 1;
		} catch (Exception e) {
			e.printStackTrace();
			et.rollback();
		}
		return -1;
	}

	public int capNhatGiaBan(SanPham sanPham) {
		try {
			et.begin();
			em.merge(sanPham);
			et.commit();
			return 1;
		} catch (Exception e) {
			e.printStackTrace();
			et.rollback();
		}
		return -1;
	}

	public SanPham getSanPhamById(String id) {
		return em.find(SanPham.class, id);
	}

	public List<SanPham> top10SanPhamBanChay() {
//        ArrayList<SanPham>listSanPham = new ArrayList<>();
//        ConnectDB.getInstance();
//        Connection conn = ConnectDB.getConnection();
//        SanPhamDAO sp_DAO = new SanPhamDAO();
//        String sql = "SELECT TOP 10     SanPham.maSP, sum(ChiTietHoaDon.soLuong) as tongSoLuong \n" +
//                        "FROM        ChiTietHoaDon INNER JOIN\n" +
//                        "                  HoaDon ON ChiTietHoaDon.maHoaDon = HoaDon.maHoaDon INNER JOIN\n" +
//                        "                  sanPham ON ChiTietHoaDon.maSP = sanPham.maSP\n" +
//                        "\n" +
//                        "group by sanPham.maSP, sanPham.tenSP\n" +
//                        "order by tongSoLuong desc";
//        try {
//            Statement stmt = conn.createStatement();
//            ResultSet rs = stmt.executeQuery(sql);
//            while(rs.next()){
//                SanPham sp = sp_DAO.getSanPhamById(rs.getString(1));
//                sp.setSoLuongTK(rs.getInt(2));
//                listSanPham.add(sp);
//
//            }
//            return listSanPham;
//        } catch (SQLException ex) {
//            Logger.getLogger(SanPhamDAO.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        return null;

		String sql = "SELECT TOP 10 sp.* " + "FROM sanpham sp " + "JOIN chitiethoadon cthd ON sp.maSP = cthd.maSP "
				+ "GROUP BY sp.loaiSP, sp.maSP, sp.donGiaBan, sp.hinhAnh, sp.soLuongTK, sp.tenSP, sp.chatLieu, sp.mauSac, sp.xuatXu, sp.tacGia, sp.maNCC, sp.maNXB, sp.maTheLoai "
				+ "ORDER BY SUM(cthd.soLuong) DESC";

		List<SanPham> resultList = em.createNativeQuery(sql, SanPham.class).getResultList();

		return resultList;
	}

	public List<SanPham> top10SanPhamBanCham() {
//        ArrayList<SanPham>listSanPham = new ArrayList<>();
//        ConnectDB.getInstance();
//        Connection conn = ConnectDB.getConnection();
//        SanPhamDAO sp_DAO = new SanPhamDAO();
//        String sql = "SELECT TOP 10     SanPham.maSP, sum(ChiTietHoaDon.soLuong) as tongSoLuong \n" +
//                        "FROM        ChiTietHoaDon INNER JOIN\n" +
//                        "                  HoaDon ON ChiTietHoaDon.maHoaDon = HoaDon.maHoaDon INNER JOIN\n" +
//                        "                  sanPham ON ChiTietHoaDon.maSP = sanPham.maSP\n" +
//                        "\n" +
//                        "group by sanPham.maSP, sanPham.tenSP\n" +
//                        "order by tongSoLuong asc";
//        try {
//            Statement stmt = conn.createStatement();
//            ResultSet rs = stmt.executeQuery(sql);
//            while(rs.next()){
//                SanPham sp = sp_DAO.getSanPhamById(rs.getString(1));
//                sp.setSoLuongTK(rs.getInt(2));
//                listSanPham.add(sp);
//
//            }
//            return listSanPham;
//        } catch (SQLException ex) {
//            Logger.getLogger(SanPhamDAO.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        return null;
		String sql = "SELECT TOP 10 sp.* " + "FROM sanpham sp " + "JOIN chitiethoadon cthd ON sp.maSP = cthd.maSP "
				+ "GROUP BY sp.loaiSP, sp.maSP, sp.donGiaBan, sp.hinhAnh, sp.soLuongTK, sp.tenSP, sp.chatLieu, sp.mauSac, sp.xuatXu, sp.tacGia, sp.maNCC, sp.maNXB, sp.maTheLoai "
				+ "ORDER BY SUM(cthd.soLuong) ASC";

		List<SanPham> resultList = em.createNativeQuery(sql, SanPham.class).getResultList();

		return resultList;
	}
}
