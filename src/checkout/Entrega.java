package checkout;
import usuarios.Cliente;
import usuarios.Supermercado;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;


public class Entrega implements Serializable {
    // Será usada na serialização, todas as classes tem
    private static final long serialVersionUID = 1L;

    private Cliente cliente;
    private int id;
    private static int contador_id = 0;
    // Parte da classe que garante que uma entrega de um mesmo cliente possa receber mais de um pedido, guarda o id do pedido e o objeto do pedido
    private Map<Integer,Pedido> dicionarioPedidos = new HashMap<>();
    private double valorTotal;


    public Entrega(Cliente cliente, Pedido pedido) {
        this.dicionarioPedidos.put(pedido.getId(), pedido);
        this.id = ++contador_id;
        this.cliente = cliente;
        this.valorTotal = 0.0;
    }

    //getters (getId() sem uso por enquanto, manter mesmo assim)
    public int getId() {return id;}
    public Map<Integer, Pedido> getDicionarioPedidos() {
        return this.dicionarioPedidos;
    }

    //add pedido no checkout
    public void adicionarPedido(Pedido novoPedido) {
        this.dicionarioPedidos.put(novoPedido.getId(), novoPedido);
    }

    // Calcula o valor e aplica o desconto caso o cliente tenha desconto
    private double calcular_valor_total(){
        double soma =0.0;

        for(Pedido pedido : dicionarioPedidos.values()){
            soma += cliente.aplicarDesconto(pedido.getValorItem());
        }
        this.valorTotal = soma;
        return soma;
    }
    // getters úteis, sem uso, manter mesmo assim para crescimento futuro
    public Cliente getCliente() {return this.cliente;}
    public double getValorTotal(){return this.valorTotal;}

    // Utilizado para criacao de Logs com atributos de todas as classes, sem ter que dar um get em cada elemento por vez dentro das classes criadoras de logs
    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append(cliente.toString()).append("\n");
        sb.append("Itens: \n");
        for (Pedido p : dicionarioPedidos.values()){
            sb.append("\t").append(p.toString()).append("\n");
        }
        sb.append("Valor Total: R$ ").append(String.format("%.2f", calcular_valor_total())).append("\n");
        return sb.toString();
    }
}

