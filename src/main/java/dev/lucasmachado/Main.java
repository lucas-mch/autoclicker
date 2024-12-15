package dev.lucasmachado;

import dev.lucasmachado.actions.FletchDarts;
import dev.lucasmachado.runner.Runner;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        Runner.start(new FletchDarts());
    }

}
