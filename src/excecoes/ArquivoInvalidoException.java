package excecoes;

public class ArquivoInvalidoException extends Exception {
    private final String nomeArquivo;
    private final int numeroLinha;

    public ArquivoInvalidoException(String nomeArquivo, int numeroLinha, String motivo) {
        super("Arquivo inválido: " + nomeArquivo + " na linha " + numeroLinha + " — " + motivo);
        this.nomeArquivo = nomeArquivo;
        this.numeroLinha = numeroLinha;
    }

    public String getNomeArquivo() { return nomeArquivo; }
    public int getNumeroLinha() { return numeroLinha; }
}