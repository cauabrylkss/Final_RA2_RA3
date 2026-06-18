package usuarios;

import java.io.Serializable;

public class Supermercado extends Cliente implements Serializable {
    private static final long serialVersionUID = 1L;
    private static final double DESCONTO = 0.10;

    public Supermercado(String nome, String CNPJ, String endereco) {
        super(nome, CNPJ, endereco);
    }

    // sobreescrita dos métodos abstratos
    @Override
    public double aplicarDesconto(double valorBruto) {
        return valorBruto - (valorBruto * DESCONTO);
    }
    @Override
    public String getTipo() {
        return "SUPERMERCADO";
    }
}
