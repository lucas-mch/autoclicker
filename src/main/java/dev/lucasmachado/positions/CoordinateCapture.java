package dev.lucasmachado.positions;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class CoordinateCapture extends JFrame {
    private Point cursorPosition = new Point(0, 0);
    private int squareSize = 50; // Tamanho do quadrado

    public CoordinateCapture() {
        setUndecorated(true); // Remove bordas
        setBackground(new Color(0, 0, 0, 0)); // Fundo transparente
        setAlwaysOnTop(true); // Sempre no topo
        setExtendedState(JFrame.MAXIMIZED_BOTH); // Maximiza a janela para cobrir a tela toda
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Obtém as dimensões da tela
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        System.out.println("Dimensões da tela: " + screenSize);

        // Centraliza o cursor no meio da tela
        cursorPosition.setLocation(screenSize.width / 2, screenSize.height / 2);

        // Adiciona um listener de mouse para capturar a posição do cursor
        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                cursorPosition = e.getPoint();
                repaint(); // Re-desenha o quadrado
            }
        });

        // Adiciona um listener de teclado para capturar a tecla de gravação
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_SPACE) { // Tecla Espaço para gravar a posição
                    System.out.println("Coordenadas gravadas: " + cursorPosition);
                } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    System.exit(1);
                }
            }
        });

        setVisible(true); // Torna a janela visível

        // Move o cursor do mouse para o centro da tela
        try {
            Robot robot = new Robot();
            robot.mouseMove(screenSize.width / 2, screenSize.height / 2);
        } catch (AWTException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.RED);
        g2d.setStroke(new BasicStroke(2));
        int x = cursorPosition.x - (squareSize / 2);
        int y = cursorPosition.y - (squareSize / 2);
        g2d.drawRect(x, y, squareSize, squareSize);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new CoordinateCapture();
        });
    }
}
