package com.dumbpug.sfc.components.paintarea;

import com.dumbpug.sfc.palette.Colour;
import com.dumbpug.sfc.utility.Position;
import java.util.ArrayList;

/**
 * Represents a paintable target.
 */
public interface IPaintableTarget {
    int getSize();
    void setPixel(int x, int y, Colour colour);
    void setPixels(ArrayList<Position<Integer>> positions, Colour colour);
    Colour getPixel(int x, int y);
}
