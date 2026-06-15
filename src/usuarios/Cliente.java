package usuarios;

import java.io.Serializable;

public abstract class Cliente implements Serializable {
    private static final long serialVersionUID = 1L;

    protected String nome;
    protected String CNPJ;
    protected String endereco;

    
    Cliente (String nome, String CNPJ, String endereco){
        this.nome = nome;
        this.CNPJ = CNPJ;
        this.endereco = endereco;
    }

    public String getNome()     { return nome; }
    public String getCnpj()     { return CNPJ; }
    public String getEndereco() { return endereco; }

    public abstract double aplicarDesconto(double valorBruto);
    public abstract String getTipo();

    @Override
    public String toString() {
        return getTipo() + ":" + nome + " | CNPJ: " + CNPJ + " | Endereço: " + endereco;
    }
}
