package arquivos;

import Produtos.Canelone;
import Produtos.Produto;
import Produtos.Spaguetti;
import Produtos.Talharim;
import checkout.Pedido;
import usuarios.Cliente;

import java.io.*;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;

public class LeitorPedidos {
    ArrayList<Cliente> lista_clientes;
    ArrayList<Pedido> listaPedidos;
    String caminho;

    public LeitorPedidos(ArrayList<Cliente> lista_clientes, String caminho){
        this.lista_clientes = lista_clientes;
        this.listaPedidos = new ArrayList<>();
        this.caminho = caminho;
    }

    public void lerPedido() throws IOException {

        BufferedReader br = new BufferedReader(new FileReader(caminho));
        BufferedWriter bw = new BufferedWriter(new FileWriter(caminho));
        String linha;

        Produto produto;

        while ((linha = br.readLine()) != null) {
            String[] campos = linha.split(",");
            Optional<Cliente> clienteEncontrado = lista_clientes.stream().filter(cliente -> cliente.getCnpj().equals(campos[0])).findFirst();

            if (clienteEncontrado.isPresent()){
                Cliente cliente = clienteEncontrado.get();
                double quantidade = Double.parseDouble(campos[2]);
                if (Objects.equals(campos[1], "spaguetti")){
                    produto = new Spaguetti();
                } else if (Objects.equals(campos[1], "canelone")) {
                    produto = new Canelone();
                }else {
                    produto = new Talharim();
                }
                Pedido pedido = new Pedido(cliente.getCnpj(), produto, quantidade);

                listaPedidos.add(pedido);

                bw.write(pedido.getId() + "," + cliente.getCnpj() + "," + campos[1] + "," + quantidade);
                bw.newLine();
                

            }
        }
        br.close();
        bw.close();
    }
    public ArrayList<Pedido> getListaPedidos(){
        return listaPedidos;
    }
}
