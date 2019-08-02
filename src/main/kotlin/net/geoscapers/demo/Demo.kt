package net.geoscapers.demo

import processing.core.PApplet
import processing.core.PConstants

/**
 *
 */
class Demo : PApplet() {

    var productionVersion = false

    var startTime: Long = 0


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
        startTime = millis().toLong()
        noCursor()
        colorMode(PConstants.HSB, 1f)
    }

    override fun draw() {
        clear()
        background(0.5f)


        delay(10)
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


}