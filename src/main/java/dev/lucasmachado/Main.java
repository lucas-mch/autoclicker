package dev.lucasmachado;

import dev.lucasmachado.actions.impl.Cooking_Karambwan;
import dev.lucasmachado.runner.Runner;

import java.io.IOException;

public class Main {

    private static Runner runner;

    static {
        try {
            runner = new Runner();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Main() throws IOException {
    }

    public static void main(String[] args) throws InterruptedException {
        runner.start(new Cooking_Karambwan());
    }
}
