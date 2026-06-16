package arquivos;

import Produtos.Canelone;
import Produtos.Produto;
import Produtos.Spaguetti;
import Produtos.Talharim;
import checkout.Pedido;
import usuarios.Cliente;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;

public class LeitorPedidos {
    ArrayList<Cliente> lista_clientes;
    LeitorPedidos(ArrayList<Cliente> lista_clientes){
        this.lista_clientes = lista_clientes;
    }

    public void lerPedido() throws IOException {

        BufferedReader br = new BufferedReader(new FileReader("pedidos.csv"));
        String linha;

        Produto produto;

        while ((linha = br.readLine()) != null) {
            String[] campos = linha.split(",");
            Optional<Cliente> clienteEncontrado = lista_clientes.stream().filter(cliente -> cliente.getCnpj().equals(campos[0])).findFirst();

            if (clienteEncontrado.isPresent()){
                Cliente cliente = clienteEncontrado.get();
                double quantidade = Double.parseDouble(campos[2]);
                if (Objects.equals(campos[1], "spagetti")){
                    produto = new Spaguetti();
                } else if (Objects.equals(campos[1], "canelone")) {
                    produto = new Canelone();
                }else {
                    produto = new Talharim();
                }
                Pedido pedido = new Pedido(cliente.getCnpj(), produto, quantidade);
            }



        }
    }
}
