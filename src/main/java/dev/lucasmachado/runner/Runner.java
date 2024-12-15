package dev.lucasmachado.runner;

import dev.lucasmachado.actions.Action;
import dev.lucasmachado.actions.FletchDarts;
import dev.lucasmachado.terminal.Terminal;

import java.io.IOException;
import java.time.Instant;

public class Runner {

    private static Action action = null;

    public static void start(Action action) throws InterruptedException, IOException {
        Terminal terminal = new Terminal(Instant.now(), action);
        Runner.action = action;
        terminal.start();
        action.setCoordinates();
        new Thread(terminal::updateLoop).start();
        new Thread(() -> {
            try {
                action.run();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
