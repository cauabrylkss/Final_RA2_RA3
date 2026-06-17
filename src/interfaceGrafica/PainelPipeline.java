package interfaceGrafica;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Path2D;
import java.util.*;
import java.util.List;

public class PainelPipeline extends JPanel {

    private static final int MARGIN = 20;
    private static final int HEADER_HEIGHT = 38;
    private static final int ROW_HEIGHT = 72;
    private static final int WEEK_WIDTH = 160;
    private static final int BLOCK_W = 46;
    private static final int BLOCK_H = 38;

    private static final Color BLOCK_COLOR = new Color(70, 107, 176);
    private static final Color ARROW_COLOR = new Color(90, 130, 200);

    private final Map<String, Integer> rowByOrder = new LinkedHashMap<>();
    private final List<BlockData> allBlocks = new ArrayList<>();
    private int maxWeek = 0;

    public PainelPipeline() {
        setBackground(Color.WHITE);
        setPreferredSize(new Dimension(800, 300));
    }

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
            rowByOrder.computeIfAbsent(id, k -> rowByOrder.size() + 1);
            allBlocks.add(new BlockData(id, estagio, semana));
        }
    }

    private void atualizarTamanhoPreferido() {
        int w = MARGIN * 2 + WEEK_WIDTH * maxWeek;
        int h = MARGIN * 2 + HEADER_HEIGHT + ROW_HEIGHT * rowByOrder.size() + MARGIN;
        setPreferredSize(new Dimension(w, h));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        desenharCabecalhos(g2);
        desenharSeparadores(g2);
        desenharSetas(g2);
        desenharBlocos(g2);

        g2.dispose();
    }

    private void desenharCabecalhos(Graphics2D g2) {
        g2.setFont(new Font("Segoe UI", Font.BOLD, 13));
        FontMetrics fm = g2.getFontMetrics();

        for (int w = 1; w <= maxWeek; w++) {
            int x = MARGIN + (w - 1) * WEEK_WIDTH;

            g2.setColor(Color.BLACK);
            g2.fillRect(x + 4, MARGIN, WEEK_WIDTH - 8, HEADER_HEIGHT);

            g2.setColor(Color.WHITE);
            String label = "Semana " + w;
            int tx = x + 4 + (WEEK_WIDTH - 8 - fm.stringWidth(label)) / 2;
            int ty = MARGIN + (HEADER_HEIGHT + fm.getAscent() - fm.getDescent()) / 2;
            g2.drawString(label, tx, ty);
        }
    }

    private void desenharSeparadores(Graphics2D g2) {
        g2.setStroke(new BasicStroke(1f));
        g2.setColor(Color.BLACK);

        int bottom = MARGIN + HEADER_HEIGHT + ROW_HEIGHT * rowByOrder.size() + MARGIN;
        for (int w = 0; w <= maxWeek; w++) {
            g2.drawLine(MARGIN + w * WEEK_WIDTH, MARGIN, MARGIN + w * WEEK_WIDTH, bottom);
        }
    }

    private void desenharSetas(Graphics2D g2) {
        Map<String, List<BlockData>> porPedido = new LinkedHashMap<>();
        for (BlockData b : allBlocks) {
            porPedido.computeIfAbsent(b.orderId, k -> new ArrayList<>()).add(b);
        }

        g2.setColor(ARROW_COLOR);
        g2.setStroke(new BasicStroke(1.5f));

        for (List<BlockData> lista : porPedido.values()) {
            lista.sort(Comparator.comparingInt(b -> b.week));
            for (int i = 0; i < lista.size() - 1; i++) {
                Point de = centroBloco(lista.get(i));
                Point para = centroBloco(lista.get(i + 1));

                int x1 = de.x + BLOCK_W / 2;
                int x2 = para.x - BLOCK_W / 2;
                g2.drawLine(x1, de.y, x2, para.y);
                desenharPontaFlecha(g2, x1, de.y, x2, para.y);
            }
        }
    }

    private void desenharPontaFlecha(Graphics2D g2, int x1, int y1, int x2, int y2) {
        double angulo = Math.atan2(y2 - y1, x2 - x1);
        int tamanho = 9;
        Path2D flecha = new Path2D.Float();
        flecha.moveTo(x2, y2);
        flecha.lineTo(x2 - tamanho * Math.cos(angulo - 0.38), y2 - tamanho * Math.sin(angulo - 0.38));
        flecha.lineTo(x2 - tamanho * Math.cos(angulo + 0.38), y2 - tamanho * Math.sin(angulo + 0.38));
        flecha.closePath();
        g2.fill(flecha);
    }

    private void desenharBlocos(Graphics2D g2) {
        g2.setFont(new Font("Segoe UI", Font.BOLD, 15));
        FontMetrics fm = g2.getFontMetrics();

        for (BlockData b : allBlocks) {
            Point c = centroBloco(b);
            int bx = c.x - BLOCK_W / 2;
            int by = c.y - BLOCK_H / 2;

            g2.setColor(BLOCK_COLOR);
            g2.fillRect(bx, by, BLOCK_W, BLOCK_H);

            g2.setColor(Color.WHITE);
            int tx = bx + (BLOCK_W - fm.stringWidth(b.stage)) / 2;
            int ty = by + (BLOCK_H + fm.getAscent() - fm.getDescent()) / 2;
            g2.drawString(b.stage, tx, ty);
        }
    }

    private Point centroBloco(BlockData b) {
        int linha = rowByOrder.get(b.orderId);
        int cx = MARGIN + (b.week - 1) * WEEK_WIDTH + WEEK_WIDTH / 2;
        int cy = MARGIN + HEADER_HEIGHT + (linha - 1) * ROW_HEIGHT + ROW_HEIGHT / 2;
        return new Point(cx, cy);
    }

    private static final class BlockData {
        final String orderId;
        final String stage;
        final int week;

        BlockData(String orderId, String stage, int week) {
            this.orderId = orderId;
            this.stage = stage;
            this.week = week;
        }
    }
}