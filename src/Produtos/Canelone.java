package Produtos;
public class Canelone extends Produto{

    private static final double MAX_PRODUCAO = 2000.0;

    Canelone(double preco_kilograma){
        super(preco_kilograma);
    }
    @Override
    public double getMaxProducaoSemanal() { return MAX_PRODUCAO; }

    @Override
    public String getForma() { return "CANELONE"; }


}
