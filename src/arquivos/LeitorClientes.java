package arquivos;

import checkout.Pedido;
import usuarios.Cliente;
import usuarios.Restaurante;
import usuarios.Supermercado;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;

public class LeitorClientes {
    protected ArrayList<Cliente> lista_clientes = new ArrayList<>();

    public LeitorClientes(String caminho) throws IOException {
        registrarClientes(caminho);
    }

    public void registrarClientes(String caminho) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(caminho));
        String linha;

        Cliente cliente;

        while ((linha = br.readLine()) != null) {
            String[] campos = linha.split(",");

            if (Objects.equals(campos[3], "restaurante")) {
                cliente = new Restaurante(campos[1], campos[0], campos[2]);
            } else {
                cliente = new Supermercado(campos[1], campos[0], campos[2]);
            }
            this.lista_clientes.add(cliente);

        }
    }

    public ArrayList<Cliente> getLista_clientes() {
        return lista_clientes;
    }
}
