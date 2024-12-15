package dev.lucasmachado;

import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.NativeHookException;
import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener;
import dev.lucasmachado.actions.FletchDarts;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AutoClicker extends JFrame implements NativeKeyListener {
    private static final Integer initializeTimer = 5;
    private static final FletchDarts fletchDarts;

    static {
        try {
            fletchDarts = new FletchDarts();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private Terminal terminal;
    private Instant startTime;
    private boolean running = true;
    private final Logger logger = Logger.getLogger(AutoClicker.class.getName());
    private final JTextArea textArea;
    private String status;

    public AutoClicker() throws IOException {
        setTitle("AutoClicker Status");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        add(new JScrollPane(textArea));
        setVisible(true);
        terminal = TerminalBuilder.builder().system(true).build();
        status = "Initializing";
    }

    public static void main(String[] args) throws InterruptedException, IOException {
        AutoClicker autoClicker = new AutoClicker();
        autoClicker.start();
    }

    public void timer() throws InterruptedException {
        for (int i = initializeTimer; i > 0; i--) {
            updateTerminal("Starting in " + i + " seconds.");
            Thread.sleep(1000);
        }
        logger.info("Started.");
    }

    public void start() throws InterruptedException {
        timer();
        registerHook();
        startTime = Instant.now();
        new Thread(this::updateLoop).start();
        status = "Running";
        new Thread(() -> {
            try {
                fletchDarts.run();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

    public void registerHook() {
        try {
            GlobalScreen.registerNativeHook();
            Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
            logger.setLevel(Level.OFF);
            GlobalScreen.addNativeKeyListener(this);
        } catch (NativeHookException ex) {
            logger.log(Level.WARNING, "There was a problem registering the native hook.", ex);
            System.exit(1);
        }
    }

    public void nativeKeyReleased(NativeKeyEvent e) {
        if (e.getKeyCode() == NativeKeyEvent.VC_SPACE) {
            try {
                GlobalScreen.unregisterNativeHook();
            } catch (NativeHookException ex) {
                ex.printStackTrace();
            }
        } else if (e.getKeyCode() == NativeKeyEvent.VC_E) {
            status = "Paused";
            fletchDarts.requestStop();
            running = false;
            logger.info(e.getKeyCode() + " pressed! requesting stop.");
        } else if (e.getKeyCode() == NativeKeyEvent.VC_R) {
            status = "Restarting";
            fletchDarts.requestRestart();
            running = true;
            logger.info(e.getKeyCode() + " pressed! requesting restart.");
        }
    }

    private void updateLoop() {
        while (running) {
            try {
                updateTerminal("Running... Time elapsed: " + getTimeElapsed() + " seconds.");
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    private void updateTerminal(String message) {
        StringBuilder sb = new StringBuilder();
        sb.append(message).append("\n");
        sb.append("Status: ").append(status).append("\n");
        textArea.setText(sb.toString());
    }

    private long getTimeElapsed() {
        return Duration.between(startTime, Instant.now()).getSeconds();
    }
}
