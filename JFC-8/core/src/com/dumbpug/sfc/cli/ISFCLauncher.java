package com.dumbpug.sfc.cli;

import com.dumbpug.sfc.SFC;

/**
 * Represents a launcher of SFC.
 */
public interface ISFCLauncher {
    /**
     * Launch SFC.
     * @return The SFC application instance.
     */
    SFC launch();
}
