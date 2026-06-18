package arquivos;

import checkout.Pedido;
import excecoes.CapacidadeExcedidaException;
import utilitarios.GeradorCaminhos;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;


public class EscritorFabricacao {
    ArrayList<Pedido> pedidosCancelados;

    public void escrever(ArrayList<Pedido> pedidosFabricar, String caminhoArquivo, int semana, ArrayList<Pedido> pedidosCancelados) throws IOException, CapacidadeExcedidaException {
        this.pedidosCancelados = pedidosCancelados;
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(caminhoArquivo, true))) {
            bw.write("=== PROGRAMAÇÃO DE FABRICAÇÃO - SEMANA " + semana + " ===");
            bw.newLine();


            // utilizado para verificação de limite de produção na semana
            double totalSpaguetti = 0.0;
            double totalCanelone = 0.0;
            double totalTalharim = 0.0;

            // itera sobre todos pedidos à fabricar
            for (Pedido p : pedidosFabricar) {

                String forma = p.getProduto().getForma().toLowerCase();
                // esse if verifica as formas, verifica se o pedido excede a produção semanal e decide se deve ser produzido ou não
                if (forma.equals("spaguetti")) {
                    try {
                        if (totalSpaguetti + p.getQuantidade() > p.getProduto().getMaxProducaoSemanal()) {
                            pedidosCancelados.add(p);

                            // caso tenhamos essa excessão, arremessamos nossa excessão criada
                            throw new CapacidadeExcedidaException(p.getProduto().getForma(), p.getQuantidade() + totalSpaguetti,
                                    p.getProduto().getMaxProducaoSemanal(), p.getProduto().getMaxProducaoSemanal() - totalSpaguetti);
                        } else {
                            totalSpaguetti += p.getQuantidade();
                        }
                    } catch (CapacidadeExcedidaException e) {
                        System.out.println(e.getMessage());
                    }
                }
                else if (forma.equals("canelone")) {
                    try {
                        if (totalCanelone + p.getQuantidade() > p.getProduto().getMaxProducaoSemanal()) {
                            pedidosCancelados.add(p);

                            // caso tenhamos essa excessão, arremessamos nossa excessão criada
                            throw new CapacidadeExcedidaException(p.getProduto().getForma(), p.getQuantidade() + totalCanelone,
                                    p.getProduto().getMaxProducaoSemanal(), p.getProduto().getMaxProducaoSemanal() - totalCanelone);
                        } else {
                            totalCanelone += p.getQuantidade();
                        }
                    } catch (CapacidadeExcedidaException e) {
                        System.out.println(e.getMessage());
                    }
                }
                else {
                    try {
                        if (totalTalharim + p.getQuantidade() > p.getProduto().getMaxProducaoSemanal()) {
                            pedidosCancelados.add(p);

                            // caso tenhamos essa excessão, arremessamos nossa excessão criada
                            throw new CapacidadeExcedidaException(p.getProduto().getForma(), p.getQuantidade() + totalTalharim,
                                    p.getProduto().getMaxProducaoSemanal(), p.getProduto().getMaxProducaoSemanal() - totalTalharim);
                        } else {
                            totalTalharim += p.getQuantidade();

                        }
                    } catch (CapacidadeExcedidaException e) {
                        System.out.println(e.getMessage());
                    }
                }

                if (totalCanelone + p.getQuantidade() > p.getProduto().getMaxProducaoSemanal()){
                    pedidosCancelados.add(p);
                }

                // Registro individual para fabricação
                bw.write(p.getId() + ";" + p.getProduto().getForma() + ";" + p.getQuantidade() + "kg");
                bw.newLine();
            }

            // logs padronizados
            bw.write("--- RESUMO DA PRODUÇÃO ---");
            bw.newLine();
            bw.write("Total Spaguetti: " + totalSpaguetti + " kg / " + Produtos.Spaguetti.MAX_PRODUCAO + " kg");
            bw.newLine();
            bw.write("Total Canelone: " + totalCanelone + " kg / " + Produtos.Canelone.MAX_PRODUCAO + " kg");
            bw.newLine();
            bw.write("Total Talharim: " + totalTalharim + " kg / " + Produtos.Talharim.MAX_PRODUCAO + " kg");
            bw.newLine();
            bw.newLine();
        }

        // chama a função de escrita de pedidos cancelados caso precise
        if (!pedidosCancelados.isEmpty()) {
            EscritorCancelados escritorCancelados = new EscritorCancelados();
            escritorCancelados.escrever(pedidosCancelados, GeradorCaminhos.gerarCaminho("log_cancelados.txt", semana), semana);
        }
    }
}