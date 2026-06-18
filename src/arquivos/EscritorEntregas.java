package arquivos;

import checkout.Entrega;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class EscritorEntregas {

    public void escrever(ArrayList<Entrega> entregas, String caminhoArquivo, int semana) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(caminhoArquivo, true))) {
            bw.write("=== PROGRAMAÇÃO DE ENTREGAS - SEMANA " + semana + " ===");
            bw.newLine();


            // caso esteja vazia avisa que não há entregas
            if (entregas.isEmpty()) {
                bw.write("Nenhuma entrega programada para esta semana.");
                bw.newLine();
            } else {

                // escreve todas entregas e separa por "-------"
                for (Entrega entrega : entregas) {

                    bw.write(entrega.toString());
                    bw.newLine();
                    bw.write("--------------------------------------------------");
                    bw.newLine();
                }
            }
            bw.newLine();
        }
    }
}