package Produtos;

import java.io.Serializable;

public class Talharim extends Produto implements Serializable {
    private static final long serialVersionUID = 1L;

    private static final double MAX_PRODUCAO = 1000.0;

    public Talharim(){
        super(16.0);
    }
    @Override
    public double getMaxProducaoSemanal() { return MAX_PRODUCAO; }

    @Override
    public String getForma() { return "TALHARIM"; }


}
