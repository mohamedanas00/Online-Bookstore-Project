package server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;

import Models.GlobalResponse;
import server.BSL.AuthBSl;

public class ClientHandler implements Runnable {
    private Socket socket;
    private BufferedWriter writer;

    public ClientHandler(Socket socket) {
        this.socket = socket;
    }

    public BufferedWriter getWriter() {
        return writer;
    }

    @Override
    public void run() {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            AuthBSl authBSl =new AuthBSl();
            String req;
            while ((req= reader.readLine()) != null && !req.equals("5")) {
                //*Do Req */
                switch (req) {
                    case "1":
                        String username =reader.readLine();
                        String password = reader.readLine();
                        //*"anas1001", "anos2002" */
                        GlobalResponse res = authBSl.login(username, password);
                        System.out.println(res.getMessage());
                        objectOutputStream.writeObject(res);
                        break;
                    default:
                        break;
                }
            }

            reader.close();
            writer.close();
            socket.close();
        } catch (Exception e) {
            // TODO: handle exception
        }

    }
}
