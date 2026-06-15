package checkout;
import usuarios.Cliente;

import java.util.HashMap;
import java.util.Map;

public class Entrega {
    Cliente cliente;
    Map<Integer,Pedido> dicionarioPedidos;
    protected double valorTotal;


    public Entrega(Cliente cliente) {
        this.cliente = cliente;
        this.dicionarioPedidos = new HashMap<>();
        this.valorTotal = 0.0;
    }

    //[Numero do pedido,forma do macarrao,quantidade(kg),preco do kilo, valor do item]

    //add pedido no checkout
    public void adicionarPedido(Pedido novoPedido) {
        //dar um set pedido
        this.dicionarioPedidos.put(novoPedido.id, novoPedido);
    }

    public Map<Integer, Pedido> getDicionarioPedidos() {
        return this.dicionarioPedidos;
    }
    public Cliente getCliente() {
        return this.cliente;
    }

}

