import checkout.Pedido;
import checkout.Entrega;
import excecoes.CapacidadeExcedidaException;
import interfaceGrafica.PainelPipeline;
import usuarios.Cliente;
import arquivos.EscritorCancelados;
import arquivos.EscritorFabricacao;
import arquivos.EscritorEntregas;
import utilitarios.GeradorCaminhos;

import javax.swing.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;


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

        EscritorFabricacao escritorFabricacao = new EscritorFabricacao();
        EscritorEntregas escritorEntregas = new EscritorEntregas();

        ArrayList<Pedido> pedidosFabricar = new ArrayList<>();
        ArrayList<Pedido> pedidosCancelados = new ArrayList<>();
        ArrayList<Entrega> entregasDaSemana = new ArrayList<>();
        ArrayList<Pedido> pedidosEntregues = new ArrayList<>();

        // INICIALIZAÇÃO DA INTERFACE GRÁFICA
        PainelPipeline painel = new PainelPipeline();
        JScrollPane scroll   = new JScrollPane(painel);

        scroll.getHorizontalScrollBar().setUnitIncrement(16);
        scroll.getVerticalScrollBar().setUnitIncrement(16);

        // Cria e exibe a janela
        JFrame frame = new JFrame("Pipeline de Pedidos");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(scroll);
        frame.setSize(1050, 420);
        frame.setLocationRelativeTo(null); // centraliza na tela
        frame.setVisible(true);

        // Lógica de simulação das semanas
        for (int i = 0; i < pedidosPorSemana.size(); i++) {
            int numeroSemana = i + 1;
            ArrayList<Pedido> pedidosDestaSemana = pedidosPorSemana.get(i);



            try {
                escritorFabricacao.escrever(pedidosFabricar, GeradorCaminhos.gerarCaminho("log_fabricacao.txt", numeroSemana), numeroSemana, pedidosCancelados);

                if (numeroSemana >= 3) {
                    escritorEntregas.escrever(entregasDaSemana, GeradorCaminhos.gerarCaminho("log_entregas.txt", numeroSemana), numeroSemana);
                }
            } catch (IOException | CapacidadeExcedidaException e) {
                System.out.println("Erro ao escrever log da semana " + numeroSemana + ": " + e.getMessage());
            }

            // INTERFACE GRÁFICA
            List<String> idPedidosFabricar = new ArrayList<>();
            List<String> idPedidosFeitos = new ArrayList<>();
            List<String> idEntregas = new ArrayList<>();
            for (Pedido p : pedidosDestaSemana){
                idPedidosFeitos.add(String.valueOf(p.getId()));
            }
            for (Pedido p : pedidosFabricar){
                idPedidosFabricar.add(String.valueOf(p.getId()));
            }

            for (Pedido p : pedidosEntregues){
                idEntregas.add(String.valueOf(p.getId()));
            }

            for (Entrega e : entregasDaSemana) {

                for (Object chave : e.getDicionarioPedidos().keySet()) {


                    if (chave instanceof Pedido) {
                        idEntregas.add(String.valueOf(((Pedido) chave).getId()));
                    }

                    else {
                        idEntregas.add(String.valueOf(chave));
                    }
                }
            }

            painel.addWeekData(numeroSemana, idPedidosFeitos, idPedidosFabricar, idEntregas);

            // FIM INTERFACE


            // ATUALIZAÇÃO DA SEMANA

            entregasDaSemana.clear();
            pedidosEntregues.clear();

            for (Pedido p : pedidosFabricar){
                Entrega entregaPedido = new Entrega(p.getCliente(), p);
                entregasDaSemana.add(entregaPedido);
                pedidosEntregues.add(p);
            }
            pedidosFabricar.clear();

            pedidosFabricar.addAll(pedidosDestaSemana);
            pedidosDestaSemana.clear();
            pedidosCancelados.clear();

        }
    }
}