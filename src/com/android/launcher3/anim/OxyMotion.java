/*
 * Copyright (C) 2026 The DerpFest Project
 * SPDX-License-Identifier: Apache-2.0
 */
package com.android.launcher3.anim;

import android.view.animation.Interpolator;
import android.view.animation.PathInterpolator;

/** Single place to tune the app launch / close motion. */
public final class OxyMotion {
    private OxyMotion() {}

    // Durations (ms). ValueAnimator multiplies these by Settings animator scale itself.
    public static final int OPEN_DURATION = 540;
    public static final int CLOSE_DURATION = 460;
    public static final int CONTENT_DURATION = 420;   // launcher workspace scale/blur
    public static final int ICON_FADE_DURATION = 180; // icon -> splash/app cross-fade

    // Spring feel
    public static final float OPEN_DAMPING = 0.82f;   // soft overshoot (~1.5%)
    public static final float CLOSE_DAMPING = 0.92f;  // almost no bounce on close
    public static final float OMEGA = 11f;
    public static final float MAX_OVERSHOOT = 1.012f;

    // Launcher content while an app is open
    public static final float LAUNCHER_SCALE_OPEN = 0.92f;
    public static final float LAUNCHER_ALPHA_OPEN = 0.0f;   // fully covered
    public static final float BLUR_DEPTH_OPEN = 1.0f;       // 0..1 of max_depth_blur_radius

    // App window corner radius while morphing from the icon (dp)
    public static final float WINDOW_RADIUS_START_DP = 22f;

    public static Interpolator open() {
        final Interpolator spring = new SpringInterpolator(OPEN_DAMPING, OMEGA);
        return t -> Math.max(0f, Math.min(spring.getInterpolation(t), MAX_OVERSHOOT));
    }

    public static Interpolator close() {
        return new SpringInterpolator(CLOSE_DAMPING, OMEGA);
    }

    /** Blur radius / scrim: starts gently, no pop to full blur. */
    public static Interpolator blurRamp() {
        return new PathInterpolator(0.4f, 0f, 0.2f, 1f);
    }

    /** For alpha / blur: monotonic, never overshoots. */
    public static Interpolator fade() {
        return new PathInterpolator(0.2f, 0f, 0f, 1f);
    }
}
