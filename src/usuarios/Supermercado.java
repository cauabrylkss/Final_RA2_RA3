package usuarios;
public class Supermercado extends Cliente {
    private static final double DESCONTO = 0.10;

    Supermercado(String nome, String CNPJ, String endereco) {
        super(nome, CNPJ, endereco);
    }

    @Override
    public double aplicarDesconto(double valorBruto) {
        return valorBruto - (valorBruto * DESCONTO);
    }

    @Override
    public String getTipo() {
        return " SUPERMERCADO ";
    }
}
