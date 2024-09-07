package dao;

import entity.VPP;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import java.util.ArrayList;

public class VppDao {
	private EntityManager em;
	private EntityTransaction et;
	public VppDao(EntityManager em) {
		this.em = em;
		this.et = em.getTransaction();
		
	}
	public ArrayList<VPP> getAllVPP(){
		return (ArrayList<VPP>) em.createQuery("SELECT v FROM VPP v", VPP.class).getResultList();
	}
	public VPP timVPPTheoTen(String ten) {
		return em.createQuery("SELECT v FROM VPP v WHERE v.tenSP = :ten", VPP.class)
				.setParameter("ten", ten)
				.getSingleResult();
	}
	public VPP getVPPByID(String id) {
		return em.find(VPP.class, id);
	}
	
	public int addVPP(VPP vpp) {
		try {
			et.begin();
			em.persist(vpp);
			et.commit();
			return 1;
		}catch (Exception e) {
			e.printStackTrace();
			et.rollback();
		}
		return -1;
	}
	public int updateVPP(VPP vpp) {
		try {
			et.begin();
			em.merge(vpp);
			et.commit();
			return 1;
		}catch (Exception e) {
			e.printStackTrace();
			et.rollback();
		}
		return -1;
	}
	
	public String auto_ID() {
		String idPrefix = "H";
		int length = getAllVPP().size() + 1;
		String finalId = idPrefix + String.format("%04d", length);
		return finalId;
	}
}

