/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import entity.PhieuNhap;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author trana
 */
public class PhieuNhapDAO {
	private EntityManager em;
	private EntityTransaction et;

	public PhieuNhapDAO(EntityManager em) {
		this.em = em;
		this.et = em.getTransaction();
	}

	public List<PhieuNhap> getAllPhieuNhap() {
		return em.createQuery("SELECT p FROM PhieuNhap p", PhieuNhap.class).getResultList();
	}

	public PhieuNhap getPhieuNhapById(String id) {
		return em.find(PhieuNhap.class, id);
	}

	public ArrayList<PhieuNhap> getPhieuNhapByDate(Date ngayBatDau, Date ngayKetThuc) {
		return (ArrayList<PhieuNhap>) em
				.createQuery("SELECT p FROM PhieuNhap p WHERE p.ngayNhap BETWEEN :ngayBatDau AND :ngayKetThuc",
						PhieuNhap.class)
				.setParameter("ngayBatDau", ngayBatDau).setParameter("ngayKetThuc", ngayKetThuc).getResultList();
	}

	public int addPhieuNhap(PhieuNhap phieuNhap) {
		try {
			et.begin();
			em.persist(phieuNhap);
			et.commit();
			return 1;
		} catch (Exception e) {
			e.printStackTrace();
			et.rollback();
		}
		return -1;
	}

	public String auto_IDPhieuNhap() {
		String idPrefix = "PN";
		int length = getAllPhieuNhap().size();
		String finalId = idPrefix + String.format("%03d", length + 1);
		return finalId;
	}
}
