package dev.lucasmachado.terminal;

import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.NativeHookException;
import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener;
import dev.lucasmachado.actions.Action;
import lombok.Setter;

import javax.swing.*;
import java.awt.*;
import java.time.Duration;
import java.time.Instant;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Terminal extends JFrame implements NativeKeyListener {

    private final JTextArea textArea;
    private final Instant startTime;
    private static final Integer initializeTimer = 5;
    @Setter
    private String status;
    private final Action action;
    private boolean running = true;

    private final Logger logger = Logger.getLogger(Terminal.class.getName());


    public void start() throws InterruptedException {
        startTimer();
        registerHook();
        setStatus("Running");
        new Thread(this::updateLoop).start();
    }

    private void startTimer() throws InterruptedException {
        for (int i = initializeTimer; i > 0; i--) {
            this.updateTerminal("Starting in " + i + " seconds.");
            Thread.sleep(1000);
        }
        logger.info("Started.");
    }

    public Terminal(Instant startTime,
                    Action action) {
        this.startTime = startTime;
        this.action = action;
        setTitle("AutoClicker Status");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        add(new JScrollPane(textArea));
        setVisible(true);
        status = "Initializing";
    }

    public void updateTerminal(String message) {
        String sb = message + "\n" +
                "Status: " + status + "\n";
        textArea.setText(sb);
    }

    public void updateLoop() {
        while (running) {
            try {
                updateTerminal("Running... Time elapsed: " + getTimeElapsed() + " seconds.");
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    private long getTimeElapsed() {
        return Duration.between(startTime, Instant.now()).getSeconds();
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
            setStatus("Paused");
            running = false;
            this.action.requestStop();
            logger.info(e.getKeyCode() + " pressed! requesting stop.");
        } else if (e.getKeyCode() == NativeKeyEvent.VC_R) {
            setStatus("Restarting");
            this.action.requestStop();
            running = true;
            logger.info(e.getKeyCode() + " pressed! requesting restart.");
        }
    }
}
