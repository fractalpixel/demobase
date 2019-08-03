package net.geoscapers.demo

import ddf.minim.Minim
import processing.core.PApplet
import processing.core.PConstants

/**
 *
 */
class Demo : PApplet() {

    var productionVersion = false

    var startTime: Long = 0

    val demoLength = 3f * 60f // Seconds

    lateinit var minim: Minim

    override fun settings() {

        /*
        // Check command line arguments
        for (arg in args) {
            if (arg.equals("-dev", ignoreCase = true)) productionVersion = false
        }
        */

        // Specify renderer to use
        val mode = PConstants.P3D

        // Set screen size
        if (productionVersion) {
            size(1920, 1080, mode)
            fullScreen()
        } else {
            size(1200, 700, mode)
        }
    }

    override fun setup() {
        minim = Minim(this)
        minim.loadFile("demo_music_here.mp3").play()

        frameRate(60f)

        noCursor()
        noStroke()
        colorMode(PConstants.HSB, 360f, 1f,1f, 1f)
        startTime = millis().toLong()
    }

    override fun draw() {
        clear()
        background(0.5f)


        // Finish it!
        if (getTimeSeconds() >= demoLength) exit()
    }


    /**
     * @return time in the demo since the start in seconds.
     */
    fun getTimeSeconds(): Float {
        return (millis() - startTime) / 1000f
    }

    /**
     * @return true if current demo time is in the specified range (excluding end value)
     */
    fun timeIsBetween(startSeconds: Float, endSeconds: Float): Boolean {
        val timeSeconds = getTimeSeconds()
        return timeSeconds >= startSeconds && timeSeconds < endSeconds
    }

    /**
     * @return true if current demo time is after the specified seconds since start.
     */
    fun timeIsAfter(startSeconds: Float): Boolean {
        return getTimeSeconds() >= startSeconds
    }

    /**
     * @return true if current demo time is before the specified seconds since start.
     */
    fun timeIsBefore(endSeconds: Float): Boolean {
        return getTimeSeconds() < endSeconds
    }

    /**
     * @return position in time between start and end seconds,
     * 0 at start and 1 at end, interpolating in between and beyond.
     */
    fun relativeTime(startSeconds: Float, endSeconds: Float): Float {
        return map(getTimeSeconds(), startSeconds, endSeconds, 0f, 1f)
    }

    /**
     * @return interpolate over time.
     */
    fun interpolateOverTime(startSeconds: Float, endSeconds: Float, startValue: Float, endValue: Float): Float {
        return map(getTimeSeconds(), startSeconds, endSeconds, startValue, endValue)
    }

    /**
     * @param t goes from 0 to 1 and controls the value produced
     * @param startValue value when t <= 0
     * @param midValue value after t >= startRampLength and while t <= 1 - endRampLength
     * @param endValue value when t >= 1
     * @param startRampLength length of transition from start to mid value
     * @param endRampLength length of transition from mid to end value.
     * @param startDelay t value before starting first ramp
     * @param endDelay delays the end
     * @return smoothly interpolated value
     */
    fun fadeInOut(
        t: Float,
        startValue: Float,
        midValue: Float,
        endValue: Float,
        startDelay: Float = 0.1f,
        startRampLength: Float = 0.25f,
        endRampLength: Float = 0.25f,
        endDelay: Float = 0.1f
    ): Float {
        return if (t <= startDelay) {
            startValue
        } else if (t > 1f - endDelay) {
            endValue
        } else if (t <= startDelay + startRampLength) {
            smoothInterpolate(startValue, midValue, relativePos(t, startDelay, startDelay + startRampLength))
        } else if (t > startDelay + startRampLength && t < 1f - endRampLength - endDelay) {
            midValue
        } else {
            smoothInterpolate(midValue, endValue, relativePos(t, 1f - endRampLength - endDelay, 1f - endDelay))
        }
    }

    fun smoothInterpolate(a: Float, b: Float, t: Float): Float {
        // Clamp
        if (t < 0f) return a
        if (t > 1f) return b

        //float smoothStepT = t * t * (3f - 2f * t);
        val t2 = (1f - cos(t * PConstants.PI)) / 2f
        return lerp(a, b, t2)
    }

    fun relativePos(value: Float, start: Float, end: Float): Float {
        if (end == start) return 0.5f

        val t = value - start
        return t / (end - start)
    }


}