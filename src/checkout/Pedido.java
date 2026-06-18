package checkout;

import Produtos.Produto;
import usuarios.Cliente;

import java.io.Serializable;

public class Pedido implements Serializable {

    // Será usada na serialização, todas as classes tem
    private static final long serialVersionUID = 1L;

    private int id;
    private static int contador_id = 0;
    private String cnpjCliente;
    private Produto produto;
    private double quantidade;
    private Cliente cliente;

    public Pedido(Cliente cliente, Produto produto, double quantidade){
        this.id = ++contador_id;
        this.cliente = cliente;
        this.cnpjCliente = cliente.getCnpj();
        this.produto = produto;
        this.quantidade = quantidade;
    }


    // Getters
    public int     getId()      { return id; }
    public String  getCnpjCliente() { return cnpjCliente; }
    public Produto getProduto()     { return produto; }
    public double  getQuantidade()  { return quantidade; }
    public double getValorItem() {
        return produto.getPrecoPorKg() * quantidade;
    }
    public Cliente getCliente(){return this.cliente;}


    // Utilizado para criacao de Logs com atributos de todas as classes, sem ter que dar um get em cada elemento por vez dentro das classes criadoras de logs
    @Override
    public String toString() {
        return "\t- PEDIDO #" + this.id
                + " | CNPJ: " + cnpjCliente
                + " | TIPO: " + produto.getForma()
                + " | PESO: " + quantidade + " KG"
                + " | PRECO/KG: " + String.format("%.2f", produto.getPrecoPorKg())
                + " | TOTAL: R$ " + String.format("%.2f", getValorItem());
    }
}
