package dev.lucasmachado.actions;

import lombok.Getter;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

import static dev.lucasmachado.mouse.MouseAction.click;
import static dev.lucasmachado.mouse.MouseAction.getMouseLocation;

public class FletchDarts {
    private Boolean stopRequested = Boolean.FALSE;
    public void requestStop() {
        this.stopRequested = true;
    }

    public void requestRestart() {
        this.stopRequested = false;
    }

    private final Coordinates coordinates = new Coordinates();

    public FletchDarts() throws InterruptedException {
    }

    public void run() throws InterruptedException {
        while(!stopRequested) {
            Thread.sleep(80);
            click(coordinates.getPoints().get("feather"), "feather");
            Thread.sleep(80);
            click(coordinates.getPoints().get("dart"), "dart");
        }
    }

    public class Coordinates {

        @Getter
        Map<String, Point> points = new HashMap<>();

        public Coordinates() throws InterruptedException {
            setDart();
            setFeather();
        }

        public void setFeather() throws InterruptedException {
            points.put("feather", getMouseLocation("feather at inventory."));
        }
        public void setDart() throws InterruptedException {
            points.put("dart", getMouseLocation("dart at inventory."));
        }

    }


}
