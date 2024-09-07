package buisinesLogic;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import dao.LoginDao;
import dao.TaiKhoanDAO;
import jakarta.persistence.EntityManager;
import server.ProgressRunnable;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class LoginLogic {
    private Socket client;
    private LoginDao loginDao;
    private EntityManager em;
    public LoginLogic(EntityManager em, Socket client) {
        this.em = em;
        this.client = client;
        loginDao = new LoginDao(em);
    }
    public boolean handleLogin(String username, String password) {
        return loginDao.checkLogin(username, password);
    }
    public boolean hanndleRole(String username) {
        TaiKhoanDAO taiKhoanDAO = new TaiKhoanDAO(em);
        return taiKhoanDAO.cvTaiKhoan(username);
    }
    public void checkLogin(Scanner sc) {
        // read json request
        try {
            // Receive request from client
            String request = sc.nextLine();
            System.out.println("Info Login: " + request);

            JsonObject jsonObject = new JsonParser().parse(request).getAsJsonObject();
            String username = jsonObject.get("username").getAsString();
            String password = jsonObject.get("password").getAsString();

            boolean loginSuccess = handleLogin(username, password);

            // Send response to client
            PrintWriter pw = new PrintWriter(client.getOutputStream(), true);
            if (loginSuccess) {
                System.out.println("Login success");

                boolean isManager = hanndleRole(username);

//                Map<String, Boolean> responseMap = new HashMap<>();
//                responseMap.put("login", true);
//                responseMap.put("role", isManager);
//
//                Gson gson = new Gson();
//                String jsonResponse = gson.toJson(responseMap);
//                System.out.println(jsonResponse);
                String jsonResponse = "{\"login\":true,\"role\":" + isManager + "}";
                pw.println(jsonResponse);
                System.out.println(jsonResponse);
            } else {
                System.out.println("Login failed");
                Map<String, Boolean> responseMap = new HashMap<>();
                responseMap.put("login", false);
                Gson gson = new Gson();
                String jsonResponse = gson.toJson(responseMap);
                pw.println(jsonResponse);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
