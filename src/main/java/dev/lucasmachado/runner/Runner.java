package dev.lucasmachado.runner;

import dev.lucasmachado.actions.Action;
import dev.lucasmachado.terminal.Terminal;

import java.time.Instant;

public class Runner {

    public static void start(Action action) throws InterruptedException {
        Terminal terminal = new Terminal(Instant.now(), action);
        terminal.start();
        action.setCoordinates();
        new Thread(() -> {
            try {
                action.run();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

}
