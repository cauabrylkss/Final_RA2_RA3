package Produtos;
public abstract class Produto {
    protected double preco_kilograma;

    Produto (double preco_kilograma){
        this.preco_kilograma = preco_kilograma;
    }

    public double getPrecoPorKg() { return preco_kilograma; }

    public abstract double getMaxProducaoSemanal();
    public abstract String getForma();

    public String toString() {
        return getForma() + " | R$ " + String.format("%.2f", preco_kilograma) + "/kg";
    }
}
