package server;

import buisinesLogic.*;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import dao.LoginDao;
import dao.TaiKhoanDAO;
import entity.ChiTietHoaDon;
import entity.HoaDon;
import entity.KhachHang;
import entity.NhanVien;
import entity.SanPham;
import jakarta.persistence.EntityManager;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class ProgressRunnable implements Runnable {
	private Socket client;
	private EntityManager em;

	public ProgressRunnable(Socket client, EntityManager em) {
		this.client = client;
		this.em = em;
	}

	@Override
	public void run() {
		try {
			Scanner scanner = new Scanner(client.getInputStream());
//			ObjectOutputStream out = new ObjectOutputStream(client.getOutputStream());
//			ObjectInputStream in = new ObjectInputStream(client.getInputStream());
			while (true) {
				if (!scanner.hasNextLine())
					break;
				String type = scanner.nextLine();
				System.out.println("Received request: " + type);
				switch (type) {
					case "login":
                        LoginLogic loginLogic = new LoginLogic(em, client);
                        loginLogic.checkLogin(scanner);
                        break;
                    case "forgotPassword":
                        ForgotPasswordLogic forgotPasswordLogic = new ForgotPasswordLogic(client, em);
                        forgotPasswordLogic.handleForgotPassword(scanner);
                        break;
                    case "customer":
                        CustomerLogic customerLogic = new CustomerLogic(client, em);
                        customerLogic.handleClientRequest(scanner);
                        break;
                    case "staff":
                        StaffLogic staffLogic = new StaffLogic(client, em);
                        staffLogic.handleClientRequest(scanner);
                        break;
                    case "supplier":
                        SupplierLogic supplierLogic = new SupplierLogic(client, em);
                        supplierLogic.handleClientRequest(scanner);
                        break;
                    case "QLHD":
                    	QuanLyHoaDonLogic qlhdLogic = new QuanLyHoaDonLogic(em, client);
                    	qlhdLogic.handleClientRequest(scanner);
                    	break;
                    case "QLBH":
                    	QuanLyBanHangLogic qlbhLogic = new QuanLyBanHangLogic(em, client);
                    	qlbhLogic.handleClientRequest(scanner);
                    	break;
                    case "QLSP":
						QuanLySanPhamLogic qlspLogic = new QuanLySanPhamLogic(em, client);
						qlspLogic.handleClientRequest(scanner);
						break;
                    case "QLPN":
                    	QuanLyPhieuNhapLogic qlpnLogic = new QuanLyPhieuNhapLogic(client, em);
                    	qlpnLogic.handleClientRequest(scanner);
                    	break;
				default:
					System.out.println("Unknown request: " + type);
				}
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

}
    

   