/*
 * Copyright (C) 2026 The DerpFest Project
 * SPDX-License-Identifier: Apache-2.0
 */
package com.android.launcher3.anim;

import android.view.animation.Interpolator;

public final class SpringInterpolator implements Interpolator {
    private static final int LUT_SIZE = 512;
    private static final float STEP = 1.0f / (LUT_SIZE - 1);
    private static final boolean ALLOW_OVERSHOOT = false;

    private final float[] mValues = new float[LUT_SIZE];

    public SpringInterpolator(float dampingRatio, float omega) {
        this(dampingRatio, omega, 0.05f);
    }

    public SpringInterpolator(float dampingRatio, float omega, float softStartRatio) {
        final float zeta = Math.max(0.5f, Math.min(dampingRatio, 0.999f));
        final float omegaD = omega * (float) Math.sqrt(1.0f - zeta * zeta);
        final float ratio = (zeta * omega) / omegaD;

        final double endEnv = Math.exp(-zeta * omega);
        final double endOsc = Math.cos(omegaD) + ratio * Math.sin(omegaD);
        final float rawEnd = (float) (1.0 - endEnv * endOsc);
        final float invNorm = (rawEnd > 0.0001f) ? (1.0f / rawEnd) : 1.0f;

        mValues[0] = 0.0f;
        for (int i = 1; i < LUT_SIZE - 1; i++) {
            float t = i * STEP;
            double env = Math.exp(-zeta * omega * t);
            double osc = Math.cos(omegaD * t) + ratio * Math.sin(omegaD * t);
            float val = (float) (1.0 - env * osc) * invNorm;

            if (softStartRatio > 0f && t < softStartRatio) {
                float u = t / softStartRatio;
                float easeIn = u * u * (3.0f - 2.0f * u);
                val *= (0.40f + 0.60f * easeIn);
            }

            if (t > 0.82f) {
                float u = (t - 0.82f) / 0.18f;
                float quinticTail = u * u * u * (u * (u * 6.0f - 15.0f) + 10.0f);
                val = val + (1.0f - val) * quinticTail;
            }

            float maxClamp = ALLOW_OVERSHOOT ? 1.035f : 1.0f;
            mValues[i] = Math.max(0.0f, Math.min(maxClamp, val));
        }
        mValues[LUT_SIZE - 1] = 1.0f;
    }

    @Override
    public float getInterpolation(float t) {
        if (t <= 0.0f) return 0.0f;
        if (t >= 1.0f) return 1.0f;

        final float pos = t * (LUT_SIZE - 1);
        final int idx = (int) pos;
        if (idx >= LUT_SIZE - 1) return 1.0f;

        final float frac = pos - idx;
        return mValues[idx] + frac * (mValues[idx + 1] - mValues[idx]);
    }
}
