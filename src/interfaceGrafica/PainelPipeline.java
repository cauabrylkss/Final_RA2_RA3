package interfaceGrafica;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.Path2D;
import java.util.*;
import java.util.List;


public class PainelPipeline extends JPanel {

    private static final int MARGIN        = 20;  // margem externa (px)
    private static final int HEADER_HEIGHT = 38;  // altura do cabeçalho "Semana N"
    private static final int ROW_HEIGHT    = 72;  // altura de cada linha (pedido)
    private static final int WEEK_WIDTH    = 160; // largura de cada coluna (semana)
    private static final int BLOCK_W       = 46;  // largura do bloco P/F/E
    private static final int BLOCK_H       = 38;  // altura do bloco P/F/E


    private static final Color BLOCK_COLOR  = new Color(70, 107, 176);
    private static final Color ARROW_COLOR  = new Color(90, 130, 200);
    private static final Color STRIPE_COLOR = new Color(100, 180, 255, 22);


    /**
     * Mapeia ID do pedido → linha de exibição (1-based).
     * Usa LinkedHashMap para manter ordem de inserção.
     */
    private final Map<String, Integer> rowByOrder = new LinkedHashMap<>();

    /** Lista de todos os blocos adicionados até o momento. */
    private final List<BlockData> allBlocks = new ArrayList<>();

    /** Semana máxima já registrada. */
    private int maxWeek = 0;


    public PainelPipeline() {
        setBackground(Color.WHITE);
        setPreferredSize(new Dimension(800, 300));
    }


    /**
     * Adiciona os dados de uma semana e atualiza o painel.
     * Pode ser chamado repetidamente; dados anteriores são mantidos.
     *
     * @param semana      número da semana (começa em 1)
     * @param pedidos     IDs dos pedidos no estágio "P" (Pedido)
     * @param fabricacoes IDs dos pedidos no estágio "F" (Fabricação)
     * @param entregas    IDs dos pedidos no estágio "E" (Entregue)
     */
    public void addWeekData(int semana, List<String> pedidos, List<String> fabricacoes, List<String> entregas) {

        if (!SwingUtilities.isEventDispatchThread()) {
            SwingUtilities.invokeLater(() -> addWeekData(semana, pedidos, fabricacoes, entregas));
            return;
        }

        maxWeek = Math.max(maxWeek, semana);
        registrarBlocos(semana, "P", pedidos);
        registrarBlocos(semana, "F", fabricacoes);
        registrarBlocos(semana, "E", entregas);
        atualizarTamanhoPreferido();
        revalidate();
        repaint();
    }


    private void registrarBlocos(int semana, String estagio, List<String> ids) {
        for (String id : ids) {
            // computeIfAbsent: só atribui linha se o pedido ainda não tiver uma
            rowByOrder.computeIfAbsent(id, k -> rowByOrder.size() + 1);
            allBlocks.add(new BlockData(id, estagio, semana));
        }
    }

    /** Recalcula o tamanho preferido com base na quantidade de semanas e pedidos. */
    private void atualizarTamanhoPreferido() {
        int w = MARGIN * 2 + WEEK_WIDTH * maxWeek;
        int h = MARGIN * 2 + HEADER_HEIGHT + ROW_HEIGHT * rowByOrder.size() + MARGIN;
        setPreferredSize(new Dimension(w, h));
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,      RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        desenharListrasFundo(g2);
        desenharCabecalhos(g2);
        desenharSeparadores(g2);
        desenharSetas(g2);
        desenharBlocos(g2);

        g2.dispose();
    }

    /** Listras alternadas de fundo para facilitar a leitura das linhas. */
    private void desenharListrasFundo(Graphics2D g2) {
        int totalWidth = WEEK_WIDTH * maxWeek;
        for (int r = 0; r < rowByOrder.size(); r++) {
            if (r % 2 == 0) {
                g2.setColor(STRIPE_COLOR);
                int y = MARGIN + HEADER_HEIGHT + r * ROW_HEIGHT;
                g2.fillRect(MARGIN, y, totalWidth, ROW_HEIGHT);
            }
        }
    }

    /** Cabeçalhos pretos "Semana N" alinhados com cada coluna. */
    private void desenharCabecalhos(Graphics2D g2) {
        g2.setFont(new Font("Segoe UI", Font.BOLD, 13));
        FontMetrics fm = g2.getFontMetrics();

        for (int w = 1; w <= maxWeek; w++) {
            int x = MARGIN + (w - 1) * WEEK_WIDTH;

            // Fundo preto arredondado
            g2.setColor(Color.BLACK);
            g2.fillRoundRect(x + 4, MARGIN, WEEK_WIDTH - 8, HEADER_HEIGHT, 6, 6);

            // Texto branco centralizado
            g2.setColor(Color.WHITE);
            String label = "Semana " + w;
            int tx = x + 4 + (WEEK_WIDTH - 8 - fm.stringWidth(label)) / 2;
            int ty = MARGIN + (HEADER_HEIGHT + fm.getAscent() - fm.getDescent()) / 2;
            g2.drawString(label, tx, ty);
        }
    }

    /** Linhas tracejadas vermelhas separando as colunas de semana. */
    private void desenharSeparadores(Graphics2D g2) {
        float[] dash = {6f, 4f};
        g2.setStroke(new BasicStroke(1f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER,
                10f, dash, 0f));
        g2.setColor(new Color(210, 50, 50, 170));

        int bottom = MARGIN + HEADER_HEIGHT + ROW_HEIGHT * rowByOrder.size() + MARGIN;
        for (int w = 0; w <= maxWeek; w++) {
            g2.drawLine(MARGIN + w * WEEK_WIDTH, MARGIN,
                    MARGIN + w * WEEK_WIDTH, bottom);
        }
        g2.setStroke(new BasicStroke(1.5f)); // restaura traço sólido
    }

    /** Setas horizontais ligando os blocos consecutivos do mesmo pedido. */
    private void desenharSetas(Graphics2D g2) {
        // Agrupa blocos por ID de pedido
        Map<String, List<BlockData>> porPedido = new LinkedHashMap<>();
        for (BlockData b : allBlocks) {
            porPedido.computeIfAbsent(b.orderId, k -> new ArrayList<>()).add(b);
        }

        g2.setColor(ARROW_COLOR);
        g2.setStroke(new BasicStroke(1.5f));

        for (List<BlockData> lista : porPedido.values()) {
            lista.sort(Comparator.comparingInt(b -> b.week));
            for (int i = 0; i < lista.size() - 1; i++) {
                Point de   = centroBloco(lista.get(i));
                Point para = centroBloco(lista.get(i + 1));

                // Da borda direita de 'de' até a borda esquerda de 'para'
                int x1 = de.x   + BLOCK_W / 2;
                int x2 = para.x - BLOCK_W / 2;
                g2.drawLine(x1, de.y, x2, para.y);
                desenharPontaFlecha(g2, x1, de.y, x2, para.y);
            }
        }
    }

    /** Desenha uma ponta de flecha sólida no ponto (x2, y2) apontando de (x1,y1). */
    private void desenharPontaFlecha(Graphics2D g2, int x1, int y1, int x2, int y2) {
        double angulo  = Math.atan2(y2 - y1, x2 - x1);
        int    tamanho = 9;
        Path2D flecha  = new Path2D.Float();
        flecha.moveTo(x2, y2);
        flecha.lineTo(x2 - tamanho * Math.cos(angulo - 0.38),
                y2 - tamanho * Math.sin(angulo - 0.38));
        flecha.lineTo(x2 - tamanho * Math.cos(angulo + 0.38),
                y2 - tamanho * Math.sin(angulo + 0.38));
        flecha.closePath();
        g2.fill(flecha);
    }

    /** Desenha todos os blocos (P, F, E) nas posições corretas. */
    private void desenharBlocos(Graphics2D g2) {
        g2.setFont(new Font("Segoe UI", Font.BOLD, 15));
        FontMetrics fm = g2.getFontMetrics();

        for (BlockData b : allBlocks) {
            Point c  = centroBloco(b);
            int   bx = c.x - BLOCK_W / 2;
            int   by = c.y - BLOCK_H / 2;

            // Sombra suave
            g2.setColor(new Color(0, 0, 0, 35));
            g2.fillRoundRect(bx + 2, by + 3, BLOCK_W, BLOCK_H, 10, 10);

            // Corpo do bloco
            g2.setColor(BLOCK_COLOR);
            g2.fillRoundRect(bx, by, BLOCK_W, BLOCK_H, 10, 10);

            // Rótulo centralizado
            g2.setColor(Color.WHITE);
            int tx = bx + (BLOCK_W - fm.stringWidth(b.stage)) / 2;
            int ty = by + (BLOCK_H + fm.getAscent() - fm.getDescent()) / 2;
            g2.drawString(b.stage, tx, ty);
        }
    }

    /**
     * Calcula o pixel central de um bloco com base no ID do pedido e na semana.
     * Coluna = semana; Linha = linha atribuída ao pedido.
     */
    private Point centroBloco(BlockData b) {
        int linha = rowByOrder.get(b.orderId); // 1-based
        int cx    = MARGIN + (b.week  - 1) * WEEK_WIDTH + WEEK_WIDTH / 2;
        int cy    = MARGIN + HEADER_HEIGHT + (linha - 1) * ROW_HEIGHT + ROW_HEIGHT / 2;
        return new Point(cx, cy);
    }

    // ── Classe interna de dados ───────────────────────────────────────────

    /** Representa um bloco (P, F ou E) de um pedido em uma semana específica. */
    private static final class BlockData {
        final String orderId; // ID do pedido (determina a linha)
        final String stage;   // "P", "F" ou "E"
        final int    week;    // semana em que aparece (determina a coluna)

        BlockData(String orderId, String stage, int week) {
            this.orderId = orderId;
            this.stage   = stage;
            this.week    = week;
        }
    }

    // ── Demonstração ──────────────────────────────────────────────────────

    /**
     * Exemplo idêntico à imagem de referência:
     * 4 pedidos entrando em semanas consecutivas e evoluindo P→F→E.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Pipeline de Pedidos");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            PainelPipeline painel = new PainelPipeline();
            JScrollPane scroll = new JScrollPane(painel);
            scroll.getHorizontalScrollBar().setUnitIncrement(16);
            scroll.getVerticalScrollBar().setUnitIncrement(16);

            frame.add(scroll);
            frame.setSize(1050, 420);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);

            // ── Simula o for loop de negócio ──────────────────────────────
            painel.addWeekData(1,
                    List.of("PED-001"), List.of(), List.of());

            painel.addWeekData(2,
                    List.of("PED-002"), List.of("PED-001"), List.of());

            painel.addWeekData(3,
                    List.of("PED-003"), List.of("PED-002"), List.of("PED-001"));

            painel.addWeekData(4,
                    List.of("PED-004"), List.of("PED-003"), List.of("PED-002"));

            painel.addWeekData(5,
                    List.of(), List.of("PED-004"), List.of("PED-003"));

            painel.addWeekData(6,
                    List.of(), List.of(), List.of("PED-004"));
        });
    }
}