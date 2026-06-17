package excecoes;

public class CapacidadeExcedidaException extends Exception {
    private final String tipoProduto;
    private final double totalPedido;
    private final double limiteMaximo;
    double quantidadeRestante;

    public CapacidadeExcedidaException(String tipoProduto, double totalPedido, double limiteMaximo, double quantidadeRestante) {
        super("Capacidade excedida para " + tipoProduto + ": pedido=" + totalPedido + "kg, limite=" + limiteMaximo + "kg");
        this.tipoProduto = tipoProduto;
        this.totalPedido = totalPedido;
        this.limiteMaximo = limiteMaximo;
        this.quantidadeRestante = quantidadeRestante;
    }

    public String getTipoProduto() { return tipoProduto; }
    public double getTotalPedido() { return totalPedido; }
    public double getLimiteMaximo() { return limiteMaximo; }
    public double getQuantidadeRestante() {return quantidadeRestante;}

    public String getMessage() {
        return String.format("PEDIDO CANCELADO: %s | PEDIDO: %f | ESTOQUE: %f", getTipoProduto(), getTotalPedido(), getQuantidadeRestante());
    }
}