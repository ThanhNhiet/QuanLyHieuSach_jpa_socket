/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;


import constant.Constants;
import entity.TaiKhoan;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

/**
 *
 * @author Admin
 */
public class LoginDao {
    private EntityManager em;

    public LoginDao(EntityManager em) {
        this.em = em;
    }

    /**
     *
     * @return
     */
//    public boolean checkLogin(String username, String matkhau) {
//        PreparedStatement stmt = null;
//        String sql = "Select * from [TaiKhoan] where tenDangNhap = ? and matKhau = ?";
//        taiKhoan taiKhoan = null;
//
//        try {
//            stmt = con.prepareStatement(sql);
//            stmt.setString(1, username);
//            stmt.setString(2, matkhau);
//
//            ResultSet rs = stmt.executeQuery();
//            while (rs.next()) {
//                taiKhoan = new taiKhoan(
//                        rs.getString(1),
//                        rs.getString(2));
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            // Close resources (stmt, rs, and con) here if necessary
//            // This should be done in a finally block to ensure resources are properly released.
//        }
//
//        return taiKhoan != null && taiKhoan.getTenDangNhap()!= null;
//
//    }
    public boolean checkLogin(String username, String matkhau) {
        return em.createQuery("SELECT tk FROM TaiKhoan tk WHERE tk.tenDangNhap.maNhanVien = :username AND tk.matKhau = :matkhau", TaiKhoan.class)
                .setParameter("username", username)
                .setParameter("matkhau", matkhau)
                .getResultList().size() > 0;
    }
}
