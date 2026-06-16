import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import arquivos.LeitorClientes;
import arquivos.LeitorPedidos;

import checkout.Pedido;

public class P1{
    public static void main(String[] args) throws IOException {
        

        LeitorClientes leitorClientes = new LeitorClientes();
        leitorClientes.registrarClientes();

        LeitorPedidos leitorPedidos = new LeitorPedidos(leitorClientes.getLista_clientes());
        leitorPedidos.lerPedido();

        ArrayList<Pedido> pedidos = leitorPedidos.getListaPedidos();


        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("dados.dat"));
        oos.writeObject(pedidos);
        oos.close();
    }
}