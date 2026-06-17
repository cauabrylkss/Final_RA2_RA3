package arquivos;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LogEvento implements Serializable {
    private static final long serialVersionUID = 1L;

    private final LocalDateTime timestamp;
    private final TipoEvento tipo;
    private final int semana;
    private final String descricao;

    protected LogEvento(TipoEvento tipo, int semana, String descricao) {
        this.timestamp = LocalDateTime.now();
        this.tipo = tipo;
        this.semana = semana;
        this.descricao = descricao;
    }

    protected String toCSV() {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        return timestamp.format(fmt) + "," + tipo + ",Semana " + semana + "," + descricao;
    }
}

