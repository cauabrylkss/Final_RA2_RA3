package arquivos;

import excecoes.ArquivoInvalidoException;

import java.io.IOException;

interface Exportavel {
    void exportar() throws IOException, ArquivoInvalidoException;
}