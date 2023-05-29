package boucherie.common.commonressource.Network;
import java.io.*;
import java.net.Socket;
public class SocketObject {
    ObjectOutputStream out;
    ObjectInputStream in;
    public SocketObject(Socket socket) throws IOException {
        OutputStream basicOut = socket.getOutputStream();
        InputStream basicIn = socket.getInputStream();
        this.out = new ObjectOutputStream(basicOut);
        this.in = new ObjectInputStream(basicIn);
    }
    public void write(Object object) throws IOException {
        this.out.reset();
        this.out.writeObject(object);
        this.out.flush();
    }

    public <T> T read() throws IOException, ClassNotFoundException {
        return (T) this.in.readObject();
    }
}

