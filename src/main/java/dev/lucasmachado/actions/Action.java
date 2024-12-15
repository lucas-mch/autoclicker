package dev.lucasmachado.actions;

import lombok.Getter;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public abstract class Action {

    public abstract void setCoordinates() throws InterruptedException;

    public abstract void run() throws InterruptedException;

    protected Boolean stopRequested = Boolean.FALSE;

    public void requestStop() {
        this.stopRequested = true;
    }

    @Getter
    Map<String, Point> coordinates = new HashMap<>();

}
