package buisinesLogic;

import com.google.gson.Gson;
import dao.NhanVienDAO;
import entity.NhanVien;
import jakarta.persistence.EntityManager;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class StaffLogic {
	private Socket socket;
	private EntityManager em;
	private NhanVienDAO nhanVienDAO;

	public StaffLogic(Socket socket, EntityManager em) {
		this.socket = socket;
		this.em = em;
		nhanVienDAO = new NhanVienDAO(em);
	}

	public void handleClientRequest(Scanner scanner) {
		String action = scanner.nextLine();
		switch (action) {
		case "getById":
			getById(scanner);
			break;
		case "getAll":
			getAll();
			break;
		case "add":
			add(scanner);
			break;
		case "update":
			update(scanner);
			break;
		case "generateId":
			generateId();
			break;
		case "getAllNVByChucVu":
			getAllNVByChucVu();
			break;
		default:
			System.out.println("Unknown action: " + action);
		}
	}

	public void getById(Scanner scanner) {
		String id = scanner.nextLine();
		NhanVien nhanVien = nhanVienDAO.getNhanVienByID(id);
		try {
			PrintWriter printWriter = new PrintWriter(socket.getOutputStream());
			printWriter.println(new Gson().toJson(nhanVien));
			printWriter.flush();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

	}

	public void getAll() {
		try {
			PrintWriter printWriter = new PrintWriter(socket.getOutputStream());
			printWriter.println(new Gson().toJson(nhanVienDAO.getAllNhanVien()));
			printWriter.flush();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public void add(Scanner scanner) {
		String json = scanner.nextLine();
		NhanVien nhanVien = new Gson().fromJson(json, NhanVien.class);
		nhanVien.setMaNhanVien(nhanVienDAO.generateID());
		// check email exists
		if (nhanVienDAO.getNhanVienByGmail(nhanVien.getEmail()) != null) {
			try {
				PrintWriter printWriter = new PrintWriter(socket.getOutputStream());
				printWriter.println("Email exists");
				printWriter.flush();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
			return;
		}
		// check phone exists
		if (nhanVienDAO.getNhanVienBySdt(nhanVien.getSoDienThoai()) != null) {
			try {
				PrintWriter printWriter = new PrintWriter(socket.getOutputStream());
				printWriter.println("Phone exists");
				printWriter.flush();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
			return;
		}
		nhanVienDAO.addNhanVien(nhanVien);
		try {
			PrintWriter printWriter = new PrintWriter(socket.getOutputStream());
			printWriter.println("Add success");
			printWriter.flush();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public void update(Scanner scanner) {
		String json = scanner.nextLine();
		NhanVien nhanVien = new Gson().fromJson(json, NhanVien.class);
		nhanVienDAO.updateNhanVien(nhanVien);
		try {
			PrintWriter printWriter = new PrintWriter(socket.getOutputStream());
			printWriter.println("Update success");
			printWriter.flush();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public void generateId() {
		try {
			PrintWriter printWriter = new PrintWriter(socket.getOutputStream());
			printWriter.println(nhanVienDAO.generateID());
			printWriter.flush();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public void getAllNVByChucVu() {
//		for (NhanVien nv : nhanVienDAO.getAllNhanVienByChucVu()) {
//			System.out.println(nv.getTenNhanVien());
//		}
		try {
			PrintWriter printWriter = new PrintWriter(socket.getOutputStream());
			printWriter.println(new Gson().toJson(nhanVienDAO.getAllNhanVienByChucVu()));
			printWriter.flush();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
