package dev.lucasmachado.actions;

import dev.lucasmachado.positions.Locations;

import java.awt.*;

public abstract class Action implements IAction {
    protected static Robot robot;
    protected static Locations locations;
    protected Boolean stopRequested = Boolean.FALSE;

    public Action() {
        try {
            robot = new Robot();
            locations = new Locations();
        } catch (AWTException | InterruptedException e) {
            e.printStackTrace();
        }
    }

}
