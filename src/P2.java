import checkout.Pedido;
import checkout.Entrega;
import usuarios.Cliente;
import arquivos.EscritorCancelados;
import arquivos.EscritorFabricacao;
import arquivos.EscritorEntregas;
import utilitarios.GeradorCaminhos;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;


public class P2 {

    public static void main(String[] args) {

        ArrayList<Cliente> clientes = null;
        ArrayList<ArrayList<Pedido>> pedidosPorSemana = null;


        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("dados.dat"))) {
            clientes = (ArrayList<Cliente>) ois.readObject();
            pedidosPorSemana = (ArrayList<ArrayList<Pedido>>) ois.readObject();
            System.out.println("Dados carregados com sucesso!");

        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Erro ao desserializar: " + e.getMessage());
            return;
        }

        EscritorCancelados escritorCancelados = new EscritorCancelados();
        EscritorFabricacao escritorFabricacao = new EscritorFabricacao();
        EscritorEntregas escritorEntregas = new EscritorEntregas();

        ArrayList<Pedido> pedidosFabricar = new ArrayList<>();
        ArrayList<Pedido> pedidosCancelados = new ArrayList<>();
        ArrayList<Entrega> entregasDaSemana = new ArrayList<>();

        // Lógica de simulação das semanas
        for (int i = 0; i < pedidosPorSemana.size(); i++) {
            int numeroSemana = i + 1;
            ArrayList<Pedido> pedidosDestaSemana = pedidosPorSemana.get(i);





            try {
                escritorFabricacao.escrever(pedidosFabricar, GeradorCaminhos.gerarCaminho("log_fabricacao.txt", numeroSemana), numeroSemana, pedidosCancelados);

                if (numeroSemana >= 3) {
                    escritorEntregas.escrever(entregasDaSemana, GeradorCaminhos.gerarCaminho("log_entregas.txt", numeroSemana), numeroSemana);
                }
            } catch (IOException e) {
                System.out.println("Erro ao escrever log da semana " + numeroSemana + ": " + e.getMessage());
            }

            // INTERFACE GRÁFICA

            // FIM INTERFACE


            // ATUALIZAÇÃO DA SEMANA

            entregasDaSemana.clear();

            for (Pedido p : pedidosFabricar){
                Entrega entregaPedido = new Entrega(p.getCliente(), p);
                entregasDaSemana.add(entregaPedido);
            }
            pedidosFabricar.clear();

            pedidosFabricar.addAll(pedidosDestaSemana);
            pedidosDestaSemana.clear();

        }
    }
}