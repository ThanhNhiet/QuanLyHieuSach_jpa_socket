package dao;

import entity.Sach;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import java.util.ArrayList;

public class SachDao {
	private EntityManager em;
	private EntityTransaction et;

	public SachDao(EntityManager em) {
		this.em = em;
		this.et = em.getTransaction();

	}

	public ArrayList<Sach> getAllSach() {
		return (ArrayList<Sach>) em.createQuery("SELECT s FROM Sach s", Sach.class).getResultList();
	}

	public Sach timSachTheoTenSach(String ten) {
		return em.createQuery("SELECT s FROM SanPham s WHERE s.tenSP = :ten", Sach.class).setParameter("ten", ten)
				.getSingleResult();
	}

	public Sach getSachByID(String id) {
		return em.find(Sach.class, id);
	}

	public int themSach(Sach s) {
		try {
			et.begin();
			em.persist(s);
			et.commit();
			return 1;
		} catch (Exception e) {
			e.printStackTrace();
			et.rollback();
		}
		return -1;
	}

	public int capNhat(Sach s) {
		try {
			et.begin();
			em.merge(s);
			et.commit();
			return 1;
		} catch (Exception e) {
			e.printStackTrace();
			et.rollback();
		}
		return -1;
	}

	public String auto_ID() {
		String idPrefix = "S";
		int length = getAllSach().size() + 1;
		String finalId = idPrefix + String.format("%04d", length);
		return finalId;
	}
}
