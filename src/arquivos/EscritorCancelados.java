package arquivos;

import checkout.Pedido;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class EscritorCancelados {

    public void escrever(ArrayList<Pedido> pedidosCancelados, String caminhoArquivo, int semana) throws IOException {

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(caminhoArquivo, true))) {
            bw.write("=== PEDIDOS CANCELADOS - SEMANA " + semana + " ===");
            bw.newLine();

            // caso esteja vazia avisa que não há cancelamentos
            if (pedidosCancelados.isEmpty()) {
                bw.write("Nenhum pedido cancelado nesta semana.");
                bw.newLine();
            } else {

                // escreve todos cancelamentos e separa por "-------"
                for (Pedido p : pedidosCancelados) {
                    bw.write("Pedido #" + p.getId() + " | CNPJ: " + p.getCnpjCliente() +
                            " | Produto: " + p.getProduto().getForma() +
                            " | Quantidade: " + p.getQuantidade() + " kg");
                    bw.newLine();
                }
            }
            bw.newLine();
        }
    }
}