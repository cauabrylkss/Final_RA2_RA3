package Produtos;

import java.io.Serializable;

public class Canelone extends Produto implements Serializable {
    private static final long serialVersionUID = 1L;

    public static final double MAX_PRODUCAO = 2000.0;

    public Canelone(){
        super(45.0);
    }

    // sobreescrita dos métodos abstratos
    @Override
    public double getMaxProducaoSemanal() { return MAX_PRODUCAO; }
    @Override
    public String getForma() { return "CANELONE"; }


}
