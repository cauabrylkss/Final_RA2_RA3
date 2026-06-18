package Produtos;

import java.io.Serializable;

public abstract class Produto implements Serializable {
    // Será usada na serialização, todas as classes tem
    private static final long serialVersionUID = 1L;

    protected double preco_kilograma;

    Produto (double preco_kilograma){
        this.preco_kilograma = preco_kilograma;
    }

    public double getPrecoPorKg() { return preco_kilograma; }

    // metodos abstratos
    public abstract double getMaxProducaoSemanal();
    public abstract String getForma();

    // Utilizado para criacao de Logs com atributos de todas as classes, sem ter que dar um get em cada elemento por vez dentro das classes criadoras de logs
    public String toString() {
        return getForma() + " | R$ " + String.format("%.2f", preco_kilograma) + "/kg";
    }
}
