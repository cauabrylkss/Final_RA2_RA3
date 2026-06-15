package checkout;
public class Pedido {

    protected int id;
    protected String cnpj;
    protected int preco;
    protected String forma_macarrao;
    protected float quantidade;
    protected StatusPedido statusPedido;

    Pedido(String cnpj, int preco, String forma_macarrao, float quantidade){
        this.cnpj = cnpj;
        this.preco = preco;
        this.forma_macarrao = forma_macarrao;
        this.quantidade = quantidade;
        this.statusPedido = StatusPedido.PEDIDO;
    }

    public void iniciarFabricacao(){}

    public void iniciarEntrega(){}

}
