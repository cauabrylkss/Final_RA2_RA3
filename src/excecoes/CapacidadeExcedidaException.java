package excecoes;

public class CapacidadeExcedidaException extends Exception {
    private final String tipoProduto;
    private final double totalPedido;
    private final double limiteMaximo;
    double quantidadeRestante;

    public CapacidadeExcedidaException(String tipoProduto, double totalPedido, double limiteMaximo, double quantidadeRestante) {
        // Declaração de erro na classe base EXCEPTION
        super("Capacidade excedida para " + tipoProduto + ": pedido=" + totalPedido + "kg, limite=" + limiteMaximo + "kg");
        this.tipoProduto = tipoProduto;
        this.totalPedido = totalPedido;
        this.limiteMaximo = limiteMaximo;
        this.quantidadeRestante = quantidadeRestante;
    }

    // Getters para print de erro e auditoria personalizada das excessoes. Sem uso por enquanto, mas útil para construções futuras
    public String getTipoProduto() { return tipoProduto; }
    public double getTotalPedido() { return totalPedido; }
    public double getQuantidadeRestante() {return quantidadeRestante;}
    public double getLimiteMaximo() {return limiteMaximo;}

    public String getMessage() {
        return String.format("PEDIDO CANCELADO: %s | PEDIDO: %f | ESTOQUE: %f", this.tipoProduto, this.totalPedido, this.quantidadeRestante);
    }
}