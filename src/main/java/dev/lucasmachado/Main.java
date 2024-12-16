package dev.lucasmachado;

import dev.lucasmachado.actions.CutGem;
import dev.lucasmachado.runner.Runner;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        Runner.start(new CutGem());
    }
}
