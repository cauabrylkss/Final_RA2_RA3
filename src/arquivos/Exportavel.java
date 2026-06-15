package arquivos;

import Excecoes.ArquivoInvalidoException;

interface Exportavel {
    void exportar(String caminho) throws ArquivoInvalidoException;
}