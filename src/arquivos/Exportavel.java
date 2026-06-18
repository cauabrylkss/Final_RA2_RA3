package arquivos;

import excecoes.ArquivoInvalidoException;

import java.io.IOException;


// interface de exportação
interface Exportavel {
    void exportar() throws IOException, ArquivoInvalidoException;
}