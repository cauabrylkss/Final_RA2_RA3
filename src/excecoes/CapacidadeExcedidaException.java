package excecoes;

public class CapacidadeExcedidaException extends Exception {
    private final String tipoProduto;
    private final double totalPedido;
    private final double limiteMaximo;

    public CapacidadeExcedidaException(String tipoProduto, double totalPedido, double limiteMaximo) {
        super("Capacidade excedida para " + tipoProduto + ": pedido=" + totalPedido + "kg, limite=" + limiteMaximo + "kg");
        this.tipoProduto = tipoProduto;
        this.totalPedido = totalPedido;
        this.limiteMaximo = limiteMaximo;
    }

    public String getTipoProduto() { return tipoProduto; }
    public double getTotalPedido() { return totalPedido; }
    public double getLimiteMaximo() { return limiteMaximo; }
}