package dev.lucasmachado.actions.impl;

import dev.lucasmachado.actions.Action;

import java.awt.*;
import java.awt.event.InputEvent;

import static dev.lucasmachado.commons.Constants.inventorySize;

public class Cooking_Karambwan extends Action {
    private Integer inventories = 0;
    private Integer actions = 0;

    public Integer getInventories() {
        return inventories;
    }

    public Integer getActions() {
        return actions;
    }

    public static void click(Point coordinate, String action) throws InterruptedException {
        robot.mouseMove(coordinate.x, coordinate.y);
        Thread.sleep(150);
        robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
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

    @Override
    public void run() {
        try {
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
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
