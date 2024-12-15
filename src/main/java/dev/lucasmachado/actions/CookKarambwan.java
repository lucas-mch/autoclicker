package dev.lucasmachado.actions;

import java.awt.*;

import static dev.lucasmachado.configs.Constants.INVENTORY_SIZE;
import static dev.lucasmachado.mouse.MouseAction.click;
import static dev.lucasmachado.mouse.MouseAction.getMouseLocation;

public class CookKarambwan extends Action {

    public void getKarambwan() throws InterruptedException {
        click(this.coordinates.get("karambwanBanked"), "getting Karambwan");
    }

    public void openBank() throws InterruptedException {
        click(this.coordinates.get("bank"), "bank ");
    }

    public void depositItens() throws InterruptedException {
        click(this.coordinates.get("depositBank"), "deposit");
    }

    public void closeBank() throws InterruptedException {
        Thread.sleep(500);
        click(this.coordinates.get("closeBank"), "close bank");
    }

    public void cook() throws InterruptedException {
        click(this.coordinates.get("lastInventory"), "inventory");
        click(this.coordinates.get("fire"), "fire");
    }

    @Override
    public void setCoordinates() throws InterruptedException {
        coordinates.put("lastInventory", getMouseLocation("lastInventory at inventory."));
        coordinates.put("fire", getMouseLocation("fire."));
        coordinates.put("closeBank", getMouseLocation("closeBank."));
        coordinates.put("depositBank", getMouseLocation("depositBank."));
        coordinates.put("bank", getMouseLocation("bank."));
        coordinates.put("karambwanBanked", getMouseLocation("karambwanBanked."));
    }

    @Override
    public void run() throws InterruptedException {
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

            for (int j = 1; j <= INVENTORY_SIZE; j++) {
                if (stopRequested) {
                    break;
                }
                Thread.sleep(475);
                cook();
            }
        }
    }


}
