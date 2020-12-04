package com.dumbpug.sfc;

import java.io.File;

public class SFCContextDetails {
    private SFCContext context;
    private File target;
    private boolean isFullScreen;

    /**
     *
     * @param context
     * @param isFullScreen
     */
    public SFCContextDetails(SFCContext context, File target, boolean isFullScreen) {
        this.context      = context;
        this.target       = target;
        this.isFullScreen = isFullScreen;
    }

    public SFCContext getContext() {
        return context;
    }

    public File getTarget() {
        return target;
    }

    public boolean isFullScreen() {
        return isFullScreen;
    }
}
