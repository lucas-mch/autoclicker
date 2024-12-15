package dev.lucasmachado.actions;

import java.awt.*;
import java.util.logging.Logger;

import static dev.lucasmachado.mouse.MouseAction.getMouseLocation;

public class Locations {

    public Point lastInventory;
    public Point fire;
    public Point karambwanBanked;
    public Point depositBank;
    public Point bank;
    public Point closeBank;

    public Locations() throws InterruptedException {
        setLastInventory();
        setFire();
        setKarambwanBanked();
        setDepositBank();
        setBank();
        setCloseBank();
    }

    public void setLastInventory() throws InterruptedException {
        lastInventory = getMouseLocation("do último espaço do inventário");
    }

    public void setFire() throws InterruptedException {
        fire = getMouseLocation("da fogueira");
    }

    public void setKarambwanBanked() throws InterruptedException {
        karambwanBanked = getMouseLocation("do karambwan no banco.");
    }

    public void setDepositBank() throws InterruptedException {
        depositBank = getMouseLocation("do botão para depositar todos os itens no banco.");
    }

    public void setBank() throws InterruptedException {
        bank = getMouseLocation("do banco.");
    }

    public void setCloseBank() throws InterruptedException {
        closeBank = getMouseLocation("do botão que fecha o banco.");
    }



}