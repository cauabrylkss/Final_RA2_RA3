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


        // deserialização
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("dados.dat"))) {
            clientes = (ArrayList<Cliente>) ois.readObject();
            pedidosPorSemana = (ArrayList<ArrayList<Pedido>>) ois.readObject();
            System.out.println("Dados carregados com sucesso!");

        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Erro ao desserializar: " + e.getMessage());
            return;
        }

        // declaração de Objetos
        EscritorFabricacao escritorFabricacao = new EscritorFabricacao();
        EscritorEntregas escritorEntregas = new EscritorEntregas();

        // Listas de Pedidos (regularão o funcionamento dos fluzos da semana)
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

            // escrita dos logs com timestamp
            logGeral.registrar(TipoEvento.INICIO_PEDIDOS, numeroSemana, "Início do tratamento dos pedidos");

            // pega os pedidos dessa semana na matriz
            ArrayList<Pedido> pedidosDestaSemana = pedidosPorSemana.get(i);

            try {
                // escrita dos logs com timestamp
                logGeral.registrar(TipoEvento.INICIO_FABRICACAO, numeroSemana, "Início da produção");

                // escreve os pedidos à fabricar da semana
                escritorFabricacao.escrever(pedidosFabricar, GeradorCaminhos.gerarCaminho("log_fabricacao.txt", numeroSemana), numeroSemana, pedidosCancelados);

                // escrita dos logs com timestamp
                logGeral.registrar(TipoEvento.FIM_FABRICACAO, numeroSemana, "Fim da produção");

                // a partir do terceiro dia que temos entregas
                if (numeroSemana >= 3) {

                    // escrita dos logs com timestamp
                    logGeral.registrar(TipoEvento.INICIO_ENTREGAS, numeroSemana, "Início das entregas");

                    // escreve os pedidos à entregar da semana
                    escritorEntregas.escrever(entregasDaSemana, GeradorCaminhos.gerarCaminho("log_entregas.txt", numeroSemana), numeroSemana);

                    // escreve os pedidos à fabricar da semana
                    logGeral.registrar(TipoEvento.FIM_ENTREGAS, numeroSemana, "Fim das entregas");
                }
            } catch (IOException | CapacidadeExcedidaException e) {
                System.out.println("Erro ao escrever log da semana " + numeroSemana + ": " + e.getMessage());
            }

            // INTERFACE GRÁFICA
            List<String> idPedidosFabricar = new ArrayList<>();
            List<String> idPedidosFeitos = new ArrayList<>();
            List<String> idEntregas = new ArrayList<>();

            // adiciona o id dos pedidos da semana
            for (Pedido p : pedidosDestaSemana){
                idPedidosFeitos.add(String.valueOf(p.getId()));
            }

            // adiciona o id dos pedidos que vao para a fabrica
            for (Pedido p : pedidosFabricar){
                idPedidosFabricar.add(String.valueOf(p.getId()));
            }

            // adiciona o id dos pedidos entregues
            for (Pedido p : pedidosEntregues){
                idEntregas.add(String.valueOf(p.getId()));
            }


            // Itera sobre todas as entregas programadas para a semana atual
            for (Entrega e : entregasDaSemana) {

                // Percorre as chaves do dicionário de pedidos dentro de cada entrega
                for (Object chave : e.getDicionarioPedidos().keySet()) {

                    // Verifica se a chave foi armazenada inteira como um objeto Pedido
                    if (chave instanceof Pedido) {
                        // Faz o cast, extrai o ID do pedido e salva na lista de controle do gráfico
                        idEntregas.add(String.valueOf(((Pedido) chave).getId()));
                    } else {
                        // Se a chave já for diretamente o ID numérico, apenas converte para texto
                        idEntregas.add(String.valueOf(chave));
                    }
                }
            }

            // Atualiza a interface gráfica com os IDs dos pedidos em cada estágio da semana atual
            painel.addWeekData(numeroSemana, idPedidosFeitos, idPedidosFabricar, idEntregas);

            // FIM INTERFACE


            // ATUALIZAÇÃO DA SEMANA

            // Limpa as listas de controle para que os dados da semana atual não vazem para a próxima
            entregasDaSemana.clear();
            pedidosEntregues.clear();
            pedidosCancelados.clear();

            // Cria um mapa temporário para agrupar os pedidos de um mesmo cliente no mesmo caminhão de entrega
            Map<String, Entrega> entregasPorCliente = new HashMap<>();

            // Transforma os pedidos recém-fabricados em entregas
            for (Pedido p : pedidosFabricar) {
                String cnpj = p.getCnpjCliente();

                // Se o cliente já tem uma entrega criada nesta semana, apenas adiciona o novo pedido a ela
                if (entregasPorCliente.containsKey(cnpj)) {
                    entregasPorCliente.get(cnpj).adicionarPedido(p);
                } else {
                    // Se é o primeiro pedido do cliente, cria uma nova entrega e registra no mapa e na lista da semana
                    Entrega novaEntrega = new Entrega(p.getCliente(), p);
                    entregasPorCliente.put(cnpj, novaEntrega);
                    entregasDaSemana.add(novaEntrega);
                }

                // Salva o pedido na lista auxiliar para garantir que o bloco "E" apareça no gráfico
                pedidosEntregues.add(p);
            }

            // Avança o pipeline: os pedidos que entraram nesta semana serão fabricados na próxima
            pedidosFabricar.clear();
            pedidosFabricar.addAll(pedidosDestaSemana);
            pedidosDestaSemana.clear();

            // Registra no CSV geral o fim do ciclo de processamento desta semana
            logGeral.registrar(TipoEvento.FIM_PEDIDOS, numeroSemana, "Fim do tratamento dos pedidos");
        }

        try {
            // escrita do log
            logGeral.escrever();
        } catch (IOException e) {
            System.out.println("Erro ao salvar log geral: " + e.getMessage());
        }
    }
}