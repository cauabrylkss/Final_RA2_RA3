package usuarios;

public class Restaurante extends Cliente{

    Restaurante (String nome, String CNPJ, String endereco){
        super(nome, CNPJ, endereco);
    }

    @Override
    public double aplicarDesconto(double valorBruto) {
        return valorBruto;
    }

    @Override
    public String getTipo() {
        return "RESTAURANTE";
    }


}
