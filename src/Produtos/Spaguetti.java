package Produtos;

import java.io.Serializable;

public class Spaguetti extends Produto implements Serializable {
    private static final long serialVersionUID = 1L;
    private static final double MAX_PRODUCAO = 1600.0;


    Spaguetti(double preco_kilograma){
        super(preco_kilograma);
    }
    @Override
    public double getMaxProducaoSemanal() { return MAX_PRODUCAO; }

    @Override
    public String getForma() { return "SPAGUETTI"; }


}
