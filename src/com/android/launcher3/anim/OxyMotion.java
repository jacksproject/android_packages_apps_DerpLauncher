/*
 * Copyright (C) 2026 The DerpFest Project
 * SPDX-License-Identifier: Apache-2.0
 */
package com.android.launcher3.anim;

import android.view.animation.Interpolator;
import android.view.animation.PathInterpolator;

/**
 * ColorOS 17 (Living Flow) motion tuning - crisp 460ms launch, subtle depth blur, 120Hz smooth.
 */
public final class OxyMotion {
    private OxyMotion() {}

    public static final long OPEN_DURATION = 460L;
    public static final long RIPPLE_DURATION = 320L;
    public static final long CLOSE_DURATION = 400L;
    public static final int CONTENT_DURATION = 440;
    public static final int ICON_FADE_DURATION = 160;

    public static final long APP_LAUNCH_ALPHA_START_DELAY = 95L;
    public static final long APP_LAUNCH_ALPHA_DURATION = 160L;

    public static final float OPEN_DAMPING = 0.94f;
    public static final float OPEN_OMEGA = 8.4f;

    public static final float CLOSE_DAMPING = 0.95f;
    public static final float CLOSE_OMEGA = 8.8f;
    public static final float OMEGA = 8.4f;

    public static final float LAUNCHER_SCALE_OPEN = 0.94f;
    public static final float LAUNCHER_ALPHA_OPEN = 0.0f;
    public static final float BLUR_DEPTH_OPEN = 0.65f;
    public static final float WINDOW_RADIUS_START_DP = 22f;

    private static final Interpolator OPEN_INTERPOLATOR =
            new SpringInterpolator(OPEN_DAMPING, OPEN_OMEGA, 0.06f);

    private static final Interpolator OPEN_X_INTERPOLATOR =
            new SpringInterpolator(0.96f, 9.4f, 0.04f);

    private static final Interpolator CLOSE_INTERPOLATOR =
            new SpringInterpolator(CLOSE_DAMPING, CLOSE_OMEGA, 0.0f);

    private static final Interpolator BLUR_RAMP_INTERPOLATOR =
            new PathInterpolator(0.35f, 0.0f, 0.25f, 1.0f);

    private static final Interpolator WORKSPACE_SCALE_INTERPOLATOR =
            new PathInterpolator(0.22f, 0.80f, 0.18f, 1.0f);

    private static final Interpolator FADE_INTERPOLATOR =
            new PathInterpolator(0.28f, 0.08f, 0.20f, 1.0f);

    public static Interpolator open() {
        return OPEN_INTERPOLATOR;
    }

    public static Interpolator openX() {
        return OPEN_X_INTERPOLATOR;
    }

    public static Interpolator close() {
        return CLOSE_INTERPOLATOR;
    }

    public static Interpolator blurRamp() {
        return BLUR_RAMP_INTERPOLATOR;
    }

    public static Interpolator workspaceScale() {
        return WORKSPACE_SCALE_INTERPOLATOR;
    }

    public static Interpolator fade() {
        return FADE_INTERPOLATOR;
    }
}
