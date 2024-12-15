package dev.lucasmachado.actions;

import static dev.lucasmachado.mouse.MouseAction.click;
import static dev.lucasmachado.mouse.MouseAction.getMouseLocation;


public class FletchDarts extends Action {

    @Override
    public void setCoordinates() throws InterruptedException {
        coordinates.put("feather", getMouseLocation("feather at inventory."));
        coordinates.put("dart", getMouseLocation("dart at inventory."));
    }

    @Override
    public void run() throws InterruptedException {
        while (!this.stopRequested) {
            Thread.sleep(80);
            click(this.getCoordinates().get("feather"), "feather");
            Thread.sleep(80);
            click(this.getCoordinates().get("dart"), "dart");
        }
    }

}
