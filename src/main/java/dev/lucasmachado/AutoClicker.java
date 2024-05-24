package dev.lucasmachado;

import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.NativeHookException;
import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener;
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
    private static final Integer intializeTimer = 15;
    private static final Actions actions = new Actions();
    private Terminal terminal;
    private Instant startTime;
    private boolean running = true;
    Logger logger = Logger.getLogger(AutoClicker.class.getName());
    private JTextArea textArea;

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
        for (int i = intializeTimer; i > 0; i--) {
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
        actions.oneTickKarambwan();

    }

    public void registerHook() {
        try {
            GlobalScreen.registerNativeHook();
            Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
            logger.setLevel(Level.OFF);
            GlobalScreen.addNativeKeyListener(this);
        } catch (NativeHookException ex) {
            logger.log(Level.WARNING, "There was a problem registering the native hook.");
            System.err.println(ex.getMessage());
            ex.printStackTrace();
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
            status = "Stopping";
            actions.requestStop();
            running = false;
            logger.info(e.getKeyCode() + " pressed! requesting stop.");
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
        sb.append("Inventories: ").append(actions.getInventories()).append("\n");
        sb.append("Actions: ").append(actions.getActions()).append("\n");
        textArea.setText(sb.toString());
    }

    private long getTimeElapsed() {
        return Duration.between(startTime, Instant.now()).getSeconds();
    }

}