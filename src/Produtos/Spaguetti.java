package Produtos;

import java.io.Serializable;

public class Spaguetti extends Produto implements Serializable {
    private static final long serialVersionUID = 1L;
    public static final double MAX_PRODUCAO = 1600.0;


    public Spaguetti(){
        super(12.0);
    }
    @Override
    public double getMaxProducaoSemanal() { return MAX_PRODUCAO; }

    @Override
    public String getForma() { return "SPAGUETTI"; }


}
