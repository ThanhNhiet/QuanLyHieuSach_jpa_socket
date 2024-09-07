package buisinesLogic;

import com.google.gson.Gson;
import dao.NhaCungCapDAO;
import dao.NhanVienDAO;
import entity.NhaCungCap;
import jakarta.persistence.EntityManager;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class SupplierLogic {
    private Socket socket;
    private EntityManager em;
    private NhaCungCapDAO nhaCungCapDAO;
    public SupplierLogic(Socket socket, EntityManager em) {
        this.socket = socket;
        this.em = em;
        nhaCungCapDAO = new NhaCungCapDAO(em);
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
            default:
                System.out.println("Unknown action: " + action);
        }
    }
    public void getById(Scanner scanner) {
        String id = scanner.nextLine();
        NhaCungCap nhaCungCap = nhaCungCapDAO.getNhaCungCap(id);
        try {
            PrintWriter printWriter = new PrintWriter(socket.getOutputStream());
            printWriter.println(new Gson().toJson(nhaCungCap));
            printWriter.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void getAll() {
        try {
            PrintWriter printWriter = new PrintWriter(socket.getOutputStream());
            printWriter.println(new Gson().toJson(nhaCungCapDAO.getAllNhaCungCap()));
            printWriter.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void add(Scanner scanner) {
        String json = scanner.nextLine();
        NhaCungCap nhaCungCap = new Gson().fromJson(json, NhaCungCap.class);
        nhaCungCap.setMaNCC(nhaCungCapDAO.generateId());
        // check email exists
        if (NhaCungCapDAO.getNhaCungCapByGmail(nhaCungCap.getEmail()) != null) {
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
        if (NhaCungCapDAO.getNhaCungCapBySdt(nhaCungCap.getSoDienThoai()) != null) {
            try {
                PrintWriter printWriter = new PrintWriter(socket.getOutputStream());
                printWriter.println("Phone exists");
                printWriter.flush();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return;
        }
        nhaCungCapDAO.addNhaCungCap(nhaCungCap);
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
        NhaCungCap nhaCungCap = new Gson().fromJson(json, NhaCungCap.class);
        nhaCungCapDAO.updateNhaCungCap(nhaCungCap);
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
            printWriter.println(nhaCungCapDAO.generateId());
            printWriter.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
