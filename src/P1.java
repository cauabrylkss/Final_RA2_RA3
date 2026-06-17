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

        // FLUXO

        // P1
        //├── LeitorClientes   → clientes.csv
        //├── LeitorPedidos    → pedidos_semana1.csv.csv (mín. 10 pedidos) 4 chamadas em p1 assim: new LeitorPedidos(clientes, "pedidos_semana1.csv", "registros_semana1.csv").exportar();
        //├── LeitorPedidos    → pedidos_semana2.csv.csv (mín. 10 pedidos)
        //├── LeitorPedidos    → pedidos_semana3.csv.csv (mín. 10 pedidos)
        //├── LeitorPedidos    → pedidos_semana4.csv.csv (mín. 10 pedidos)
        //└── serializa tudo   → dados.dat
        //
        //P2
        //├── desserializa dados.dat
        //├── simula semana 1 → EscritorFabricacao, EscritorCancelados
        //├── simula semana 2 → EscritorFabricacao, EscritorCancelados, EscritorEntregas
        //├── simula semana 3 → idem
        //├── simula semana 4 → idem
        //└── EscritorLog     → ao longo de tudo

        LeitorClientes leitorClientes = new LeitorClientes("src/clientes.csv");
        ArrayList<Cliente> clientes = leitorClientes.getLista_clientes();
        ArrayList<ArrayList<Pedido>> pedidosPorSemana = new ArrayList<>();


        try {
            LeitorPedidos lp1 = new LeitorPedidos(clientes, "src/pedidos_semana1.csv");
            lp1.exportar();

            LeitorPedidos lp2 = new LeitorPedidos(clientes, "src/pedidos_semana2.csv");
            lp2.exportar();

            LeitorPedidos lp3 = new LeitorPedidos(clientes, "src/pedidos_semana3.csv");
            lp3.exportar();

            LeitorPedidos lp4 = new LeitorPedidos(clientes, "src/pedidos_semana4.csv");
            lp4.exportar();

            pedidosPorSemana.add(lp1.getListaPedidos());
            pedidosPorSemana.add(lp2.getListaPedidos());
            pedidosPorSemana.add(lp3.getListaPedidos());
            pedidosPorSemana.add(lp4.getListaPedidos());
        } catch (ArquivoInvalidoException e) {
            System.out.println("Arquivos de pedidos são inválidos");
        }




        // serializa clientes e pedidos juntos
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("dados.dat"))) {
            oos.writeObject(clientes);
            oos.writeObject(pedidosPorSemana);
            System.out.println("Serialização concluída.");
        } catch (IOException e) {
            System.out.println("Erro ao serializar: " + e.getMessage());
        }
    }
}