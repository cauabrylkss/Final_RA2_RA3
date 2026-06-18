package utilitarios;

public class GeradorCaminhos {

    // Utilitário utilizado para nomear arquivos das semana em sequencia
    public static String gerarCaminho(String caminhoOriginal, int numeroSemana){
        return caminhoOriginal.replace(".txt", String.format("Semana%d", numeroSemana));
    }
}
