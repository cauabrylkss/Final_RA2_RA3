package utilitarios;

public class GeradorCaminhos {
    public static String gerarCaminho(String caminhoOriginal, int numeroSemana){
        return caminhoOriginal.replace(".txt", String.format("Semana%d", numeroSemana));
    }
}
