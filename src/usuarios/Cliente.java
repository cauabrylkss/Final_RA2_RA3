package usuarios;
public abstract class Cliente {
    protected String nome;
    protected String CNPJ;
    protected String endereco;

    protected double desconto;
    
    Cliente (String nome, String CNPJ, String endereco){
        this.nome = nome;
        this.CNPJ = CNPJ;
        this.endereco = endereco;
        this.desconto = 0;
    }

    Cliente (String nome, String CNPJ, String endereco, double desconto){
        this.nome = nome;
        this.CNPJ = CNPJ;
        this.endereco = endereco;
        this.desconto = desconto;
    }

    public double aplicar_desconto(double valor){
        if (this.desconto != 0){
            return valor * this.desconto;
        }
        return valor;
    }
}
