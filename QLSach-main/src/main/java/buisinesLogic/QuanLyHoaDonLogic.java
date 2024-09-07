package buisinesLogic;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.google.gson.Gson;

import constant.Constants;
import dao.ChiTietHoaDonDAO;
import dao.HoaDonDAO;
import dao.SanPhamDAO;
import entity.ChiTietHoaDon;
import entity.HoaDon;
import entity.Sach;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;

public class QuanLyHoaDonLogic {
	private Socket socket;
	private EntityManager em;
	private HoaDonDAO hoaDonDAO;
	private ChiTietHoaDonDAO cthd_DAO;

	public QuanLyHoaDonLogic(EntityManager em, Socket socket) {
		this.em = em;
		this.socket = socket;
	}

	public void handleClientRequest(Scanner scanner) {
		String action = scanner.nextLine();
		switch (action) {
		case "getCTHD_id":
			getAllCTHDByID(scanner);
			break;
		case "getAll":
			loadAllDataHD();
			tinhTongTien();
			break;
		case "btnTimKiem":
			getDsHDByDate(scanner, scanner);
			break;
		case "btnXuatHoaDon":
			getHDByID_TT(scanner);
			break;
		default:
			System.out.println("Unknown action: " + action);
		}
	}

	public void loadAllDataHD() {
		hoaDonDAO = new HoaDonDAO(em);
		List<HoaDon> list = hoaDonDAO.getAllHoaDon();
		String response = new Gson().toJson(list);
		try {
			PrintWriter writer = new PrintWriter(socket.getOutputStream());
			writer.println(response);
			writer.flush();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public void tinhTongTien() {
		cthd_DAO = new ChiTietHoaDonDAO(em);
		hoaDonDAO = new HoaDonDAO(em);
		List<HoaDon> list = hoaDonDAO.getAllHoaDon();
		double[] listTongTien = new double[list.size()];
		int index = 0;

		for (HoaDon hd : list) {
			double tongTien = 0;

			List<ChiTietHoaDon> listCTHDByID = cthd_DAO.getCTHDById(hd.getMaHD());

			for (ChiTietHoaDon cthd : listCTHDByID) {
				tongTien += cthd.thanhTien();
			}

			listTongTien[index] = tongTien;
			index++;
		}

		String response = new Gson().toJson(listTongTien);
		try {
			PrintWriter writer = new PrintWriter(socket.getOutputStream());
			writer.println(response);
			writer.flush();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public void getAllCTHDByID(Scanner sc) {
		cthd_DAO = new ChiTietHoaDonDAO(em);
		String id = sc.nextLine();
		List<ChiTietHoaDon> listCTHD = cthd_DAO.getCTHDById(id);
		
		List<ChiTietHoaDon> listCTHD_Sach= new ArrayList<>();
		List<ChiTietHoaDon> listCTHD_VPP = new ArrayList<>();
		for(ChiTietHoaDon cthd : listCTHD) {
			if(cthd.getSanPham() instanceof Sach) {
				listCTHD_Sach.add(cthd);
			}else {
				listCTHD_VPP.add(cthd);
			}
		}
		
		String responseSach = new Gson().toJson(listCTHD_Sach);
		String responseVPP = new Gson().toJson(listCTHD_VPP);
		try {
			PrintWriter writer = new PrintWriter(socket.getOutputStream());
			writer.println(responseSach);
			writer.println(responseVPP);
			writer.flush();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public void getDsHDByDate(Scanner scF, Scanner scE) {
		hoaDonDAO = new HoaDonDAO(em);
		cthd_DAO = new ChiTietHoaDonDAO(em);
		String from = scF.nextLine();
		String to = scE.nextLine();
		Date ngayBatDau = Date.valueOf(from);
		Date ngayKetThuc = Date.valueOf(to);
		List<HoaDon> listHD = hoaDonDAO.getHoaDonByDate(ngayBatDau, ngayKetThuc);
		
		double[] listTongTien = new double[listHD.size()];
		int index = 0;
		for (HoaDon hd : listHD) {
			double tongTien = 0;
			List<ChiTietHoaDon> listCTHDByID = cthd_DAO.getCTHDById(hd.getMaHD());
			for (ChiTietHoaDon cthd : listCTHDByID) {
				tongTien += cthd.thanhTien();
			}
			listTongTien[index] = tongTien;
			index++;
		}
		
		String responseHD = new Gson().toJson(listHD);
		String responseTT = new Gson().toJson(listTongTien);
		try {
			PrintWriter writer = new PrintWriter(socket.getOutputStream());
			writer.println(responseHD);
			writer.println(responseTT);
			writer.flush();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public void getHDByID_TT(Scanner sc) {
		hoaDonDAO = new HoaDonDAO(em);
		cthd_DAO = new ChiTietHoaDonDAO(em);
		String id = sc.nextLine();
		HoaDon hd = hoaDonDAO.getHoaDonByID(id);

		List<ChiTietHoaDon> listCTHDByID = cthd_DAO.getCTHDById(id);
		
		String responseHD = new Gson().toJson(hd);
		String responseTT = new Gson().toJson(listCTHDByID);
		try {
			PrintWriter writer = new PrintWriter(socket.getOutputStream());
			writer.println(responseHD);
			writer.println(responseTT);
			writer.flush();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public double tinhTongTien1HD(HoaDon hd) {
		cthd_DAO = new ChiTietHoaDonDAO(em);

		double tongTien = 0;

		List<ChiTietHoaDon> listCTHDByID = cthd_DAO.getCTHDById(hd.getMaHD());

		for (ChiTietHoaDon cthd : listCTHDByID) {
			tongTien += cthd.thanhTien();
		}

		return tongTien;
	}

}
