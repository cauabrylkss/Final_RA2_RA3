import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import arquivos.LeitorClientes;
import arquivos.LeitorPedidos;

import checkout.Pedido;
import excecoes.ArquivoInvalidoException;
import usuarios.Cliente;

public class P1{
    public static void main(String[] args) throws IOException {

        // Lê os clientes e adiciona objetos ao sistema
        LeitorClientes leitorClientes = new LeitorClientes("src/clientes.csv");
        ArrayList<Cliente> clientes = leitorClientes.getLista_clientes();
        // Array de Array, serve para guardar N pedidos de N semanas
        ArrayList<ArrayList<Pedido>> pedidosPorSemana = new ArrayList<>();


        // Guarda todos os pedidos de todas as semana da simulação
        try {
            LeitorPedidos lp1 = new LeitorPedidos(clientes, "src/pedidos_semana1.csv");
            lp1.exportar();

            LeitorPedidos lp2 = new LeitorPedidos(clientes, "src/pedidos_semana2.csv");
            lp2.exportar();

            LeitorPedidos lp3 = new LeitorPedidos(clientes, "src/pedidos_semana3.csv");
            lp3.exportar();

            LeitorPedidos lp4 = new LeitorPedidos(clientes, "src/pedidos_semana4.csv");
            lp4.exportar();

            // adiciona 4 pedidos a 4 semana dentro da nossa matriz
            pedidosPorSemana.add(lp1.getListaPedidos());
            pedidosPorSemana.add(lp2.getListaPedidos());
            pedidosPorSemana.add(lp3.getListaPedidos());
            pedidosPorSemana.add(lp4.getListaPedidos());

        // Nossas funções arremessam nossa exception modificada, ela é pega aqui
        } catch (ArquivoInvalidoException e) {
            System.out.println("Arquivos de pedidos são inválidos");
        }




        // serializa clientes e pedidos juntos, tudo em binário
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("dados.dat"))) {
            oos.writeObject(clientes);
            oos.writeObject(pedidosPorSemana);
            System.out.println("Serialização concluída.");
        } catch (IOException e) {
            System.out.println("Erro ao serializar: " + e.getMessage());
        }
    }
}