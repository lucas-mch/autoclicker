package dev.lucasmachado.mouse;

import java.awt.*;
import java.awt.event.InputEvent;

public class MouseAction {
    private static final Robot robot;

    static {
        try {
            robot = new Robot();
        } catch (AWTException e) {
            throw new RuntimeException(e);
        }
    }

    public static void click(Point coordinate, String action) throws InterruptedException {
        robot.mouseMove(coordinate.x, coordinate.y);
        Thread.sleep(150);
        robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
    }

    public static Point getMouseLocation(String msg) throws InterruptedException {
        System.out.println("Por favor, defina a posição " + msg);
        Thread.sleep(5000);
        Point mouseLocation = MouseInfo.getPointerInfo().getLocation();
        System.out.println("posição do " + msg + " definida em: [x: " + mouseLocation.x + "] , [y: " + mouseLocation.y + "]");
        return mouseLocation;
    }

}
