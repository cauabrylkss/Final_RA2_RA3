import arquivos.*;
import checkout.Pedido;
import checkout.Entrega;
import excecoes.CapacidadeExcedidaException;
import interfaceGrafica.PainelPipeline;
import usuarios.Cliente;
import utilitarios.GeradorCaminhos;

import javax.swing.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

        // Logs gerais
        EscritorLog logGeral = new EscritorLog("log_eventos_simulacao.csv");

        // Lógica de simulação das semanas
        for (int i = 0; i < pedidosPorSemana.size(); i++) {
            int numeroSemana = i + 1;

            logGeral.registrar(TipoEvento.INICIO, numeroSemana, "Início do tratamento dos pedidos");

            ArrayList<Pedido> pedidosDestaSemana = pedidosPorSemana.get(i);

            try {
                logGeral.registrar(TipoEvento.INICIO, numeroSemana, "Início da produção");
                escritorFabricacao.escrever(pedidosFabricar, GeradorCaminhos.gerarCaminho("log_fabricacao.txt", numeroSemana), numeroSemana, pedidosCancelados);
                logGeral.registrar(TipoEvento.FIM, numeroSemana, "Fim da produção");

                if (numeroSemana >= 3) {
                    logGeral.registrar(TipoEvento.INICIO, numeroSemana, "Início das entregas");
                    escritorEntregas.escrever(entregasDaSemana, GeradorCaminhos.gerarCaminho("log_entregas.txt", numeroSemana), numeroSemana);
                    logGeral.registrar(TipoEvento.FIM, numeroSemana, "Fim das entregas");
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
                    } else {
                        idEntregas.add(String.valueOf(chave));
                    }
                }
            }

            painel.addWeekData(numeroSemana, idPedidosFeitos, idPedidosFabricar, idEntregas);

            // FIM INTERFACE


            // ATUALIZAÇÃO DA SEMANA

            entregasDaSemana.clear();
            pedidosEntregues.clear();
            pedidosCancelados.clear();

            Map<String, Entrega> entregasPorCliente = new HashMap<>();

            for (Pedido p : pedidosFabricar) {
                String cnpj = p.getCnpjCliente();

                if (entregasPorCliente.containsKey(cnpj)) {
                    entregasPorCliente.get(cnpj).adicionarPedido(p);
                } else {
                    Entrega novaEntrega = new Entrega(p.getCliente(), p);
                    entregasPorCliente.put(cnpj, novaEntrega);
                    entregasDaSemana.add(novaEntrega);
                }

                pedidosEntregues.add(p); // Mantém o gráfico do PainelPipeline funcionando
            }

            pedidosFabricar.clear();
            pedidosFabricar.addAll(pedidosDestaSemana);
            pedidosDestaSemana.clear();

            logGeral.registrar(TipoEvento.FIM, numeroSemana, "Fim do tratamento dos pedidos");
        }

        try {
            logGeral.escrever();
        } catch (IOException e) {
            System.out.println("Erro ao salvar log geral: " + e.getMessage());
        }
    }
}