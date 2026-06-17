package arquivos;

import checkout.Pedido;
import utilitarios.GeradorCaminhos;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class EscritorFabricacao {
    ArrayList<Pedido> pedidosCancelados;

    public void escrever(List<Pedido> pedidosFabricar, String caminhoArquivo, int semana, ArrayList<Pedido> pedidosCancelados) throws IOException {
        this.pedidosCancelados = pedidosCancelados;
        EscritorCancelados escritorCancelados = new EscritorCancelados();
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(caminhoArquivo, true))) {
            bw.write("=== PROGRAMAÇÃO DE FABRICAÇÃO - SEMANA " + semana + " ===");
            bw.newLine();

            double totalSpaguetti = 0.0;
            double totalCanelone = 0.0;
            double totalTalharim = 0.0;

            for (Pedido p : pedidosFabricar) {

                String forma = p.getProduto().getForma().toLowerCase();
                if (forma.equals("spaguetti")) {
                    if (totalSpaguetti + p.getQuantidade() > p.getProduto().getMaxProducaoSemanal()){
                        pedidosCancelados.add(p);
                        escritorCancelados.escrever(pedidosCancelados, GeradorCaminhos.gerarCaminho("log_cancelados.txt", semana), semana);
                    }else {
                        totalSpaguetti += p.getQuantidade();
                    }
                }
                else if (forma.equals("canelone")) {
                    if (totalCanelone + p.getQuantidade() > p.getProduto().getMaxProducaoSemanal()){
                        pedidosCancelados.add(p);
                        escritorCancelados.escrever(pedidosCancelados, GeradorCaminhos.gerarCaminho("log_cancelados.txt", semana), semana);
                    }else {
                        totalCanelone += p.getQuantidade();
                    }
                }
                else {
                    if (totalTalharim + p.getQuantidade() > p.getProduto().getMaxProducaoSemanal()){
                        pedidosCancelados.add(p);
                        escritorCancelados.escrever(pedidosCancelados, GeradorCaminhos.gerarCaminho("log_cancelados.txt", semana), semana);
                    } else {
                        totalTalharim += p.getQuantidade();

                    }
                }

                if (totalCanelone + p.getQuantidade() > p.getProduto().getMaxProducaoSemanal()){
                    pedidosCancelados.add(p);
                    escritorCancelados.escrever(pedidosCancelados, GeradorCaminhos.gerarCaminho("log_cancelados.txt", semana), semana);
                }

                // Registro individual para fabricação
                bw.write(p.getId() + ";" + p.getProduto().getForma() + ";" + p.getQuantidade() + "kg");
                bw.newLine();
            }

            bw.write("--- RESUMO DA PRODUÇÃO ---");
            bw.newLine();
            bw.write("Total Spaguetti: " + totalSpaguetti + " kg / 2000.0 kg");
            bw.newLine();
            bw.write("Total Canelone: " + totalCanelone + " kg / 1600.0 kg");
            bw.newLine();
            bw.write("Total Talharim: " + totalTalharim + " kg");
            bw.newLine();
            bw.newLine();
        }
    }
}