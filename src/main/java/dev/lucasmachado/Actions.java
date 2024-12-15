package dev.lucasmachado;

import java.awt.*;
import java.awt.event.InputEvent;
import java.util.logging.Logger;

import static dev.lucasmachado.Constants.inventorySize;
import static dev.lucasmachado.mouse.MouseAction.click;

public class Actions {
    private static Robot robot;
    private static Locations locations;
    private Boolean stopRequested = Boolean.FALSE;
    private Integer inventories = 0;
    private Integer actions = 0;

    public Integer getInventories() {
        return inventories;
    }

    public Integer getActions() {
        return actions;
    }

    public Actions() {
        try {
            robot = new Robot();
            locations = new Locations();
        } catch (AWTException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void oneTickKarambwan() throws InterruptedException {
        for (int i = 1; i < 1000000; i++) {
            if (stopRequested) {
                break;
            }
            Thread.sleep(700);
            openBank();
            Thread.sleep(700);
            depositItens();
            Thread.sleep(700);
            getKarambwan();
            Thread.sleep(700);
            closeBank();
            Thread.sleep(700);

            inventories++;

            for (int j = 1; j <= inventorySize; j++) {
                if (stopRequested) {
                    break;
                }
                Thread.sleep(475);
                cook();
                actions++;
            }
        }
    }


    public void getKarambwan() throws InterruptedException {
        click(locations.karambwanBanked, "getting Karambwan");
    }

    public static void openBank() throws InterruptedException {
        click(locations.bank, "bank ");
    }

    public static void depositItens() throws InterruptedException {
        click(locations.depositBank, "deposit");
    }

    public static void closeBank() throws InterruptedException {
        Thread.sleep(500);
        click(locations.closeBank, "close bank");
    }

    public static void cook() throws InterruptedException {
        click(locations.lastInventory, "inventory");
        click(locations.fire, "fire");
    }

    public void requestStop() {
        this.stopRequested = true;
    }
}
