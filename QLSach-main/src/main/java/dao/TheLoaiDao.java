package dao;

import entity.TheLoai;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import java.util.ArrayList;

public class TheLoaiDao {
    private EntityManager em;
    private EntityTransaction et;
	public TheLoaiDao(EntityManager em) {
        this.em = em;
        this.et = em.getTransaction();
	}
	public ArrayList<TheLoai> getAllTheLoai(){
		return (ArrayList<TheLoai>) em.createQuery("SELECT t FROM TheLoai t", TheLoai.class).getResultList();
	}
	public TheLoai getTheoMaTheLoai(String id) {
		return em.find(TheLoai.class, id);
	}
	public TheLoai getTheLoaiByName(String name) {
        return em.createQuery("SELECT t FROM TheLoai t WHERE t.tenTheLoai = :name", TheLoai.class).setParameter("name", name).getSingleResult();
    }
}

