package arquivos;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LogEvento implements Serializable {
    // Será usada na serialização, todas as classes tem
    private static final long serialVersionUID = 1L;

    private final LocalDateTime timestamp;
    private final TipoEvento tipo;
    private final int semana;
    private final String descricao;

    protected LogEvento(TipoEvento tipo, int semana, String descricao) {
        // captura o horário da chamada do registrador de logs
        this.timestamp = LocalDateTime.now();
        this.tipo = tipo;
        this.semana = semana;
        this.descricao = descricao;
    }

    // Escreve em CSV o evento
    protected String toCSV() {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        return timestamp.format(fmt) + "," + tipo + ",Semana " + semana + "," + descricao;
    }
}

