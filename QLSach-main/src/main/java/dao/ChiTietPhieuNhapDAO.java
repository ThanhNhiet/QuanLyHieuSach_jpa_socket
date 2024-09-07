/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import entity.ChiTietPhieuNhap;
import entity.PhieuNhap;
import entity.SanPham;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import java.util.ArrayList;

/**
 *
 * @author trana
 */
public class ChiTietPhieuNhapDAO {
    private EntityManager em;
    private EntityTransaction et;

    public ChiTietPhieuNhapDAO(EntityManager em) {
        this.em = em;
        this.et = em.getTransaction();
    }
    
    public ArrayList<ChiTietPhieuNhap> getAllCTHDByHoaDon(PhieuNhap phieuNhap) {
        return (ArrayList<ChiTietPhieuNhap>) em.createQuery("SELECT ctpn FROM ChiTietPhieuNhap ctpn WHERE ctpn.phieuNhap = :phieuNhap", ChiTietPhieuNhap.class)
                .setParameter("phieuNhap", phieuNhap)
                .getResultList();
    }
    
    public ArrayList<ChiTietPhieuNhap> getCTPNById(String id){
    return (ArrayList<ChiTietPhieuNhap>) em.createQuery("SELECT ctpn FROM ChiTietPhieuNhap ctpn WHERE ctpn.phieuNhap.maPhieuNhap = :id", ChiTietPhieuNhap.class)
                    .setParameter("id", id)
                    .getResultList();
    }
    
    public int addChiTietPhieuNhap(ChiTietPhieuNhap ctpn){
        try {
            et.begin();
            em.persist(ctpn);
            et.commit();
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            et.rollback();
        }
        return -1;
    }
    
    public int capNhatSoLuong(SanPham sanPham){
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
}
