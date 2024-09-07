package buisinesLogic;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.net.Socket;
import java.util.Scanner;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.InstanceCreator;
import com.google.gson.reflect.TypeToken;

import dao.ChiTietHoaDonDAO;
import dao.HoaDonDAO;
import dao.NhaCungCapDAO;
import dao.NhaXuatBanDao;
import dao.SachDao;
import dao.SanPhamDAO;
import dao.TheLoaiDao;
import dao.VppDao;
import entity.ChiTietHoaDon;
import entity.HoaDon;
import entity.NhaCungCap;
import entity.NhaXuatBan;
import entity.Sach;
import entity.SanPham;
import entity.TheLoai;
import entity.VPP;
import jakarta.persistence.EntityManager;

public class QuanLyDonViLogic {
	private Socket socket;
	private EntityManager em;
	private NhaCungCapDAO nhaCcDao;
	private NhaXuatBanDao nhaXbDao;
	private TheLoaiDao theLoaiDao;
	private ChiTietHoaDonDAO chiTietHoaDonDAO;
	private HoaDonDAO hoaDonDao;
	public void handleClientRequest(Scanner scanner) {
		String action = scanner.nextLine();
		switch (action) {
		case "add":
			getNhaCCbyName(scanner);
			getTheLoaibyName(scanner);
			getNhaXbByName(scanner);
			String sanPhamObject = scanner.nextLine();
			SanPham sp = new Gson().fromJson(sanPhamObject, new TypeToken<SanPham>() {
			}.getType());
			break;
		default:
			System.out.println("Unknown action: " + action);
		}
	}
	public QuanLyDonViLogic(Socket socket, EntityManager em) {
	super();
	this.socket = socket;
	this.em = em;
}
	public void getNhaCCbyName(Scanner sc) {
		nhaCcDao = new NhaCungCapDAO(em);
		String id = sc.nextLine();
		NhaCungCap s = nhaCcDao.getNhaCungCapByName(id);
		String response = new Gson().toJson(s);
		try {
			PrintWriter writer = new PrintWriter(socket.getOutputStream());
			writer.println(response);
			writer.flush();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	public void getNhaXbByName(Scanner sc) {
		nhaXbDao = new NhaXuatBanDao(em);
		String id = sc.nextLine();
		NhaXuatBan s = nhaXbDao.getNhaXuatBanByName(id);
		String response = new Gson().toJson(s);
		try {
			PrintWriter writer = new PrintWriter(socket.getOutputStream());
			writer.println(response);
			writer.flush();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	public void getTheLoaibyName(Scanner sc) {
		theLoaiDao = new TheLoaiDao(em);
		String id = sc.nextLine();
		TheLoai s = theLoaiDao.getTheLoaiByName(id);
		String response = new Gson().toJson(s);
		try {
			PrintWriter writer = new PrintWriter(socket.getOutputStream());
			writer.println(response);
			writer.flush();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	public void addHoaDon(HoaDon hd) {
		hoaDonDao = new HoaDonDAO(em);
		if (hoaDonDao.addHoaDon(hd) == 0) {
			try {
				PrintWriter writer = new PrintWriter(socket.getOutputStream());
				writer.println("false");
				writer.flush();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		} else {
			try {
				PrintWriter writer = new PrintWriter(socket.getOutputStream());
				writer.println("true");
				writer.flush();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
	}
	
}


