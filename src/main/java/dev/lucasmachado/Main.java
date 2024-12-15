package dev.lucasmachado;

import dev.lucasmachado.actions.FletchDarts;
import dev.lucasmachado.runner.Runner;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws InterruptedException, IOException {
        Runner.start(new FletchDarts());
    }

}
