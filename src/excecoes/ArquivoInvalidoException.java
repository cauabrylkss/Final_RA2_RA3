package excecoes;

public class ArquivoInvalidoException extends Exception {
    private final String nomeArquivo;
    private final int numeroLinha;

    public ArquivoInvalidoException(String nomeArquivo, int numeroLinha, String motivo) {
        // Declaração de erro na classe base EXCEPTION
        super("Arquivo inválido: " + nomeArquivo + " na linha " + numeroLinha + " — " + motivo);
        this.nomeArquivo = nomeArquivo;
        this.numeroLinha = numeroLinha;
    }

    // Getters para print de erro e auditoria personalizada das excessoes. Sem uso por enquanto, mas útil para construções futuras
    public String getNomeArquivo() { return nomeArquivo; }
    public int getNumeroLinha() { return numeroLinha; }
}