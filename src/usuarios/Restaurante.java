package usuarios;

import java.io.Serializable;

public class Restaurante extends Cliente implements Serializable {
    private static final long serialVersionUID = 1L;

    public Restaurante(String nome, String CNPJ, String endereco){
        super(nome, CNPJ, endereco);
    }

    // sobreescrita dos métodos abstratos
    @Override
    public double aplicarDesconto(double valorBruto) {
        return valorBruto;
    }
    @Override
    public String getTipo() {
        return "RESTAURANTE";
    }


}
