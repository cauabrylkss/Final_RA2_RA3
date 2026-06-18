package usuarios;

import java.io.Serializable;

public abstract class Cliente implements Serializable {
    // Será usada na serialização, todas as classes tem
    private static final long serialVersionUID = 1L;

    protected String nome;
    protected String CNPJ;
    protected String endereco;

    
    Cliente (String nome, String CNPJ, String endereco){
        this.nome = nome;
        this.CNPJ = CNPJ;
        this.endereco = endereco;
    }

    public String getCnpj()     { return CNPJ; }

    // metodos abstratos
    public abstract double aplicarDesconto(double valorBruto);
    public abstract String getTipo();

    // Utilizado para criacao de Logs com atributos de todas as classes, sem ter que dar um get em cada elemento por vez dentro das classes criadoras de logs
    @Override
    public String toString() {
        return getTipo() + ":" + nome + " | CNPJ: " + CNPJ + " | Endereço: " + endereco;
    }
}
