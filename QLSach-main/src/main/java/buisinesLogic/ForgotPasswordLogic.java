package buisinesLogic;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import dao.LoginDao;
import dao.NhanVienDAO;
import dao.TaiKhoanDAO;
import entity.TaiKhoan;
import jakarta.persistence.EntityManager;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ForgotPasswordLogic {
    private Socket client;
    private EntityManager em;
    private TaiKhoanDAO taiKhoanDAO;
    private NhanVienDAO nhanVienDAO;

    public ForgotPasswordLogic(Socket client, EntityManager em) {
        this.client = client;
        this.em = em;
    }
    public boolean checkMail(String mail) {

        nhanVienDAO = new NhanVienDAO(em);
        if(nhanVienDAO.getNhanVienByGmail(mail) != null)
            return true;
        return false;

    }
    public boolean changePassword(String email, String password) {
        taiKhoanDAO = new TaiKhoanDAO(em);

        TaiKhoan taiKhoan = taiKhoanDAO.timTaiKhoanByEmail(email);
        if (taiKhoan != null) {
            taiKhoanDAO.doiMatKhauTaiKhoan(taiKhoan, password);
            return true;
        }
        return false;
    }
    public void handleForgotPassword(Scanner sc) {
        try {
            // Receive request from client, get value from json request
            String request = sc.nextLine();
            System.out.println("Request: " + request);

            JsonObject jsonObject = new JsonParser().parse(request).getAsJsonObject();
            boolean isCheckMail = jsonObject.get("isCheckMail").getAsBoolean();
            String email = jsonObject.get("email").getAsString();
            System.out.println("Email: " + email);
            System.out.println("isCheckMail: " + isCheckMail);
            if (isCheckMail) {
                boolean checkMail = checkMail(email);
                if (checkMail) {
                    System.out.println("Check mail success");
                    client.getOutputStream().write(1);
                } else {
                    System.out.println("Check mail failed");
                    client.getOutputStream().write(0);
                }
            } else {
                String password = jsonObject.get("password").getAsString();
                boolean changePassword = changePassword(email, password);
                if (changePassword) {
                    System.out.println("Change password success");
                    client.getOutputStream().write(1);
                } else {
                    System.out.println("Change password failed");
                    client.getOutputStream().write(0);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
