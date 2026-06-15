package checkout;
import usuarios.Cliente;
import usuarios.Supermercado;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

//entrega pedido-> logica e usuario

public class Entrega implements Serializable {
    //versão do Serializable
    private static final long serialVersionUID = 1L;

    Cliente cliente;
    Supermercado supermercado;
    Map<Integer,Pedido> dicionarioPedidos;
    protected double valorTotal;


    public Entrega(Cliente cliente) {
        this.cliente = cliente;
        this.dicionarioPedidos = new HashMap<>();
        this.valorTotal = 0.0;
    }

    public Map<Integer, Pedido> getDicionarioPedidos() {
        return this.dicionarioPedidos;
    }

    //[Numero do pedido,forma do macarrao,quantidade(kg),preco do kilo, valor do item]

    //add pedido no checkout
    public void adicionarPedido(Pedido novoPedido) {
        this.dicionarioPedidos.put(novoPedido.getId(), novoPedido);
    }

    public void calcular_valor_total(){
        double soma =0.0;

        for(Pedido pedido : dicionarioPedidos.values()){
            soma += cliente.aplicarDesconto(pedido.getValorItem());
            this.valorTotal = soma;

        }

    }

    public Cliente getCliente() {return this.cliente;}
    public double getValorTotal(){return this.valorTotal;}


}