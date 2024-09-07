import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import constant.Constants;
import dao.NhanVienDAO;
import dao.TaiKhoanDAO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;
import server.ThreadPoolServer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) throws IOException {
        try (EntityManager em = Persistence.createEntityManagerFactory("QLS SQlSERVER").createEntityManager()) {
            ThreadPoolServer server = new ThreadPoolServer();
            server.createServerSocket(5000, em);
        }
    }
}
