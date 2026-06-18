package arquivos;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class EscritorLog {
    private final ArrayList<LogEvento> eventos;
    private final String arquivo;

    public EscritorLog(String arquivo) {
        this.eventos = new ArrayList<>();
        this.arquivo = arquivo;
    }

    public void registrar(TipoEvento tipo, int semana, String descricao) {
        eventos.add(new LogEvento(tipo, semana, descricao));
    }

    public void escrever() throws IOException {

        // escreve o evento com todos os metadados necessários
        BufferedWriter bw = new BufferedWriter(new FileWriter(arquivo));
        bw.write("timestamp,evento,semana,descricao");
        bw.newLine();

        for (LogEvento evento : eventos) {
            bw.write(evento.toCSV());
            bw.newLine();
        }

        bw.close();
    }
}