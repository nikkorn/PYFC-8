package com.dumbpug.sfc.utility;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.ScreenUtils;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * Taker of application screenshots.
 */
public class Screenshot {

    /**
     * Get an application screenshot as a buffered image.
     * @return An application screenshot as a buffered image.
     */
    public static BufferedImage capture() {
        byte[] pixels = ScreenUtils.getFrameBufferPixels(0, 0, Gdx.graphics.getBackBufferWidth(), Gdx.graphics.getBackBufferHeight(), true);

        // this loop makes sure the whole screenshot is opaque and looks exactly like what the user is seeing
        for(int i = 4; i < pixels.length; i += 4) {
            pixels[i - 1] = (byte) 255;
        }

        BufferedImage screenshot = new BufferedImage(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), BufferedImage.TYPE_4BYTE_ABGR);
        screenshot.getRaster().setDataElements(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), pixels);

        return screenshot;
    }

    /**
     * Take a screenshot and write it to disk.
     * @param directory The directory to save the screenshot to.
     * @param name The name of the screenshot.
     */
    public static void write(File directory, String name) {
        try {
            ImageIO.write(Screenshot.capture(), "png", new File(directory.getAbsolutePath() + "/" + name + ".png"));
        } catch (IOException e) {}
    }
}
