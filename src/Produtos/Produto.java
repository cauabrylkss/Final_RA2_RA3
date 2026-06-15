package Produtos;
public abstract class Produto {
    protected double preco_kilograma;

    Produto (double preco_kilograma){
        this.preco_kilograma = preco_kilograma;
    }
    protected double calculo_preco(double massa){
        return preco_kilograma * massa;
    }
}
