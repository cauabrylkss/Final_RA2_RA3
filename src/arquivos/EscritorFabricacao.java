package arquivos;

import checkout.Pedido;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class EscritorFabricacao {

    public void escrever(List<Pedido> pedidosFabricar, String caminhoArquivo, int semana) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(caminhoArquivo, true))) {
            bw.write("=== PROGRAMAÇÃO DE FABRICAÇÃO - SEMANA " + semana + " ===");
            bw.newLine();

            double totalSpaguetti = 0.0;
            double totalCanelone = 0.0;
            double totalTalharim = 0.0;

            for (Pedido p : pedidosFabricar) {

                String forma = p.getProduto().getForma().toLowerCase();
                if (forma.equals("spaguetti")) totalSpaguetti += p.getQuantidade();
                else if (forma.equals("canelone")) totalCanelone += p.getQuantidade();
                else totalTalharim += p.getQuantidade();

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