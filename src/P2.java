import checkout.Pedido;
import usuarios.Cliente;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;

public class P2 {
    public static void main(String[] args) throws IOException, ClassNotFoundException {

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("dados.dat"))) {
            ArrayList<Cliente> clientes = (ArrayList<Cliente>) ois.readObject();
            ArrayList<ArrayList<Pedido>> pedidosPorSemana = (ArrayList<ArrayList<Pedido>>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Erro ao desserializar: " + e.getMessage());
        }
    }
}
