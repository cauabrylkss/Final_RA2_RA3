package checkout;

import Produtos.Produto;
import usuarios.Cliente;

import java.io.Serializable;

public class Pedido implements Serializable {

    private static final long serialVersionUID = 1L;
    protected int id;
    protected int contador_id;
    protected String cnpjCliente;
    private Produto produto;
    private double quantidade;
    private Cliente cliente;
    protected StatusPedido statusPedido;

    public Pedido(Cliente cliente, Produto produto, double quantidade){
        this.id = ++contador_id;
        this.cliente = cliente;
        this.cnpjCliente = cliente.getCnpj();
        this.produto = produto;
        this.quantidade = quantidade;
        this.statusPedido = StatusPedido.PEDIDO;
    }
    public void setStatus(StatusPedido status)   { this.statusPedido = status; }

    public int     getId()      { return id; }
    public String  getCnpjCliente() { return cnpjCliente; }
    public Produto getProduto()     { return produto; }
    public double  getQuantidade()  { return quantidade; }
    public StatusPedido getStatus()              { return statusPedido; }

    public double getValorItem() {
        return produto.getPrecoPorKg() * quantidade;
    }

    public Cliente getCliente(){return this.cliente;}

    @Override
    public String toString() {
        return "Pedido #" + this.id + "CNPJ: "
                + cnpjCliente + produto.getForma()
                + quantidade + " kg"
                + "Preco/Kg: " + String.format("%.2f", produto.getPrecoPorKg())
                + "Total: R$ " + String.format("%.2f", getValorItem());
    }
}
