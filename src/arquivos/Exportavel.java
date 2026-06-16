package arquivos;

import excecoes.ArquivoInvalidoException;

interface Exportavel {
    void exportar(String caminho) throws ArquivoInvalidoException;
}