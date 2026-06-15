package checkout;
import usuarios.Cliente;

import java.util.HashMap;
import java.util.Map;

public class Entrega {
    //parametros clientes (Cliente cliente)
    Cliente cliente;
    Map<Integer,Pedido> dicionarioPedidos;
    protected double valorTotal;

    /*protected String nome;
    protected String CNPJ;
    protected String endereco;
    double desconto;*/

    //___________________

    //LISTA CLIENTE
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

// Onde for chamar esse construtor, deve ser passado os atributos relacionados à classe cliente
// EX:
//
// Cliente cliente1 = new Cliente(cnpj, nome, etc..)
// Entrega entrega1 = new Entrega(cliente1.cnpj, etc...)
