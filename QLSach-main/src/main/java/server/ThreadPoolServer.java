package server;

import jakarta.persistence.EntityManager;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPoolServer {
    private boolean isRunning = true;
    private ServerSocket serverSocket = null;
    private final ExecutorService executorService = Executors.newFixedThreadPool(10);

    public synchronized void createServerSocket(int port, EntityManager em) {
        try {
            serverSocket = new ServerSocket(port);
            while (isRunning) {
                executorService.execute(new ProgressRunnable(serverSocket.accept(), em));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public synchronized void stop() throws IOException {
        isRunning = false;
        serverSocket.close();
        executorService.shutdown();
    }
}
