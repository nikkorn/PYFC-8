package com.dumbpug.sfc.cli;

import com.dumbpug.sfc.utility.ConcurrentQueue;

/**
 * Represents a launcher of SFC.
 */
public interface ISFCLauncher {
    /**
     * Launch SFC.
     * @return A queue used to push messages to the SFC application.
     */
    ConcurrentQueue<String> launch();
}
