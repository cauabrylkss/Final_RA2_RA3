import checkout.Pedido;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.List;

public class P2 {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream("dados.dat"));
        List<Pedido> pedidos = (List<Pedido>) ois.readObject();
        ois.close();
    }
}
