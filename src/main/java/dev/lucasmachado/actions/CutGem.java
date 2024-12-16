package dev.lucasmachado.actions;

import static dev.lucasmachado.mouse.MouseAction.click;
import static dev.lucasmachado.mouse.MouseAction.getMouseLocation;

public class CutGem extends Action {

    @Override
    public void setCoordinates() throws InterruptedException {
        coordinates.put("bank", getMouseLocation("bank."));
        coordinates.put("close bank", getMouseLocation("close bank"));
        coordinates.put("battlestaff", getMouseLocation("battlestaff"));
        coordinates.put("orb", getMouseLocation("orb"));
        coordinates.put("make", getMouseLocation("make"));
    }

    @Override
    public void run() throws InterruptedException {
        while (!this.stopRequested) {
            Thread.sleep(2500);
            click(this.getCoordinates().get("bank"), "bank");
            Thread.sleep(2500);
            click(this.getCoordinates().get("close bank"), "close bank");
            Thread.sleep(2500);
            click(this.getCoordinates().get("battlestaff"), "battlestaff");
            Thread.sleep(80);
            click(this.getCoordinates().get("orb"), "orb");
            Thread.sleep(2500);
            click(this.getCoordinates().get("make"), "make");
            Thread.sleep(20000);
        }
    }

}
