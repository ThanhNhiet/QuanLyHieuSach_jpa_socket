/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import entity.TaiKhoan;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

/**
 *
 * @author Admin
 */
public class TaiKhoanDAO {
    private EntityManager em;
    private EntityTransaction et;
    public TaiKhoanDAO(EntityManager em) {
        this.em = em;
        this.et = em.getTransaction();
    }

    public TaiKhoan timTaiKhoanByEmail(String email) {
        return em.createQuery("SELECT tk FROM TaiKhoan tk JOIN tk.tenDangNhap nv WHERE nv.email = :email", TaiKhoan.class)
                .setParameter("email", email)
                .getSingleResult();
    }

    /**
     * Đổi mật khẩu tài khoản
     *
     * @param taiKhoan
     */
    public void doiMatKhauTaiKhoan(TaiKhoan taiKhoan, String maKhau) {
        try {
            et.begin();
            taiKhoan.setMatKhau(maKhau);
            em.merge(taiKhoan);
            et.commit();
        } catch (Exception e) {
            e.printStackTrace();
            et.rollback();
        }
    }

 public int addTaiKhoan(TaiKhoan taiKhoan) {
        try {
            et.begin();
            em.persist(taiKhoan);
            et.commit();
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            et.rollback();
        }
        return -1;
    }
    public boolean cvTaiKhoan(String tk) {
        return em.createQuery("SELECT tk FROM TaiKhoan tk WHERE tk.tenDangNhap.maNhanVien = :tk AND tk.phanQuyen = 'QL'", TaiKhoan.class)
                .setParameter("tk", tk)
                .getResultList().size() > 0;
    }
}


