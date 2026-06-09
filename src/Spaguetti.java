public class Spaguetti extends Produto{
    private int producao_semanal;
    private double contador_producao;

    Spaguetti(double preco_kilograma, int producao_semanal){
        super(preco_kilograma);
        this.producao_semanal = producao_semanal;
    }

    public void adicionar_producao(double massa_produzida){
        if (this.contador_producao + massa_produzida > this.producao_semanal){
            System.out.printf("\nNão é possível adicionar à produção, temos mais %f", this.producao_semanal - this.contador_producao);
            //isso deve ser adicionado à um arquiovo, como descrito no pdf do professor
        }else{
            this.contador_producao += massa_produzida;
        }
    }

    public void zerar_producao(){
        this.contador_producao = 0;
    }
}
