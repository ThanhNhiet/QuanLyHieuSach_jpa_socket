package dao;

import entity.NhaXuatBan;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import java.util.ArrayList;

public class NhaXuatBanDao {
	private EntityManager em;
	private EntityTransaction et;
	public NhaXuatBanDao(EntityManager em) {
		this.em = em;
		this.et = em.getTransaction();
		
	}
	public ArrayList<NhaXuatBan> getAllNhaXuatBan(){
		return (ArrayList<NhaXuatBan>) em.createQuery("SELECT nxb FROM NhaXuatBan nxb", NhaXuatBan.class).getResultList();
	}
	public NhaXuatBan getNhaXuatBan(String id) {
		return em.find(NhaXuatBan.class, id);
	}
	public NhaXuatBan getNhaXuatBanByName(String name) {
		return em.createQuery("SELECT nxb FROM NhaXuatBan nxb WHERE nxb.tenNXB = :name", NhaXuatBan.class)
				.setParameter("name", name)
				.getSingleResult();
	}
}
