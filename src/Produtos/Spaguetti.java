package Produtos;
public class Spaguetti extends Produto{
    private static final double MAX_PRODUCAO = 1600.0;


    Spaguetti(double preco_kilograma){
        super(preco_kilograma);
    }
    @Override
    public double getMaxProducaoSemanal() { return MAX_PRODUCAO; }

    @Override
    public String getForma() { return "SPAGUETTI"; }


}
