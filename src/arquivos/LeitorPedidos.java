package arquivos;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class LeitorPedidos {
    public void lerPedido() throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("pedidos.csv"));
        String linha;

        while ((linha = br.readLine()) != null) {
            String[] campos = linha.split(",");


        }

    }
}
