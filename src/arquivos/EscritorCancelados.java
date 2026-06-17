package arquivos;

import checkout.Pedido;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class EscritorCancelados {

    public void escrever(List<Pedido> pedidosCancelados, String caminhoArquivo, int semana) throws IOException {

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(caminhoArquivo, true))) {
            bw.write("=== PEDIDOS CANCELADOS - SEMANA " + semana + " ===");
            bw.newLine();

            if (pedidosCancelados.isEmpty()) {
                bw.write("Nenhum pedido cancelado nesta semana.");
                bw.newLine();
            } else {
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