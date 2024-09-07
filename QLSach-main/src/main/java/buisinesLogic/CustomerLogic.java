package buisinesLogic;

import com.google.gson.Gson;
import dao.KhachHangDAO;
import entity.KhachHang;
import jakarta.persistence.EntityManager;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;
import java.util.Scanner;

public class CustomerLogic {
    private Socket socket;
    private EntityManager em;
    private KhachHangDAO khachHangDAO;
    public CustomerLogic(Socket socket, EntityManager em) {
        this.socket = socket;
        this.em = em;
        khachHangDAO = new KhachHangDAO(em);
    }
    public void handleClientRequest(Scanner scanner) {
        String action = scanner.nextLine();
        switch (action) {
            case "getById":
                getById(scanner);
                break;
            case "getAll":
                System.out.println("Get all");
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
            case "get10KH_highestOrderPrice":
            	get10KH_highestOrderPrice();
            	break;
            default:
                System.out.println("Unknown action: " + action);
        }
    }
    public void getById(Scanner scanner) {
        String id = scanner.nextLine();
        KhachHang khachHang = khachHangDAO.getKhachHangById(id);
        try {
            PrintWriter writer = new PrintWriter(socket.getOutputStream());
            writer.println(new Gson().toJson(khachHang));
            writer.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void getAll() {
        List<KhachHang> list = khachHangDAO.getAllKhachHang();
        // send list to client
        String response = new Gson().toJson(list);

        try {
            PrintWriter writer = new PrintWriter(socket.getOutputStream());
            writer.println(response);
            writer.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void generateId() {
        String id = khachHangDAO.generateId();
        try {
            PrintWriter writer = new PrintWriter(socket.getOutputStream());
            writer.println(id);
            writer.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void add(Scanner scanner) {
        // read json from client
        String json = scanner.nextLine();
        // get email from json
        KhachHang khachHang = new Gson().fromJson(json, KhachHang.class);
        khachHang.setMaKhachHang(khachHangDAO.generateId());
        // check mail exists
        if (khachHangDAO.getKhachHangByEmail(khachHang.getEmail()) != null) {
            // send response to client
            try {
                PrintWriter writer = new PrintWriter(socket.getOutputStream());
                writer.println("Email exists");
                writer.flush();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return;
        }
        // check phone exists
        if (khachHangDAO.getKhachHangBySdt(khachHang.getSoDienThoai()) != null) {
            // send response to client
            try {
                PrintWriter writer = new PrintWriter(socket.getOutputStream());
                writer.println("Phone exists");
                writer.flush();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return;
        }
        khachHangDAO.addKhachHang(khachHang);
        try {
            PrintWriter writer = new PrintWriter(socket.getOutputStream());
            writer.println("Add success");
            writer.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void update(Scanner scanner) {
        String json = scanner.nextLine();
        KhachHang khachHang = new Gson().fromJson(json, KhachHang.class);
        khachHangDAO.updateKhachHang(khachHang);
        try {
            PrintWriter writer = new PrintWriter(socket.getOutputStream());
            writer.println("Update success");
            writer.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    
    public void get10KH_highestOrderPrice() {
    	try {
			PrintWriter printWriter = new PrintWriter(socket.getOutputStream());
			printWriter.println(new Gson().toJson(khachHangDAO.get10KH_highestOrderPrice()));
			printWriter.flush();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
    }
}
