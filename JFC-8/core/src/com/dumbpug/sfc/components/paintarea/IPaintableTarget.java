package com.dumbpug.sfc.components.paintarea;

import com.dumbpug.sfc.palette.Colour;

/**
 * Represents a paintable target.
 */
public interface IPaintableTarget {
    int getSize();
    void setPixel(int x, int y, Colour colour);
    Colour getPixel(int x, int y);
}
