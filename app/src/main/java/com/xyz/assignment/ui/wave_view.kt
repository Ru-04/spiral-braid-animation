package com.example.waveanimation

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat

class WaveView(context: Context, attrs: AttributeSet) : View(context, attrs) {

    private val paint1 = Paint().apply {
        color = ContextCompat.getColor(context, android.R.color.holo_blue_bright)
        style = Paint.Style.STROKE
        strokeWidth = 5f
        isAntiAlias = true
    }

    private val paint2 = Paint().apply {
        color = ContextCompat.getColor(context, android.R.color.holo_green_light)
        style = Paint.Style.STROKE
        strokeWidth = 5f
        isAntiAlias = true
    }

    private val paint3 = Paint().apply {
        color = ContextCompat.getColor(context, android.R.color.holo_orange_light)
        style = Paint.Style.STROKE
        strokeWidth = 5f
        isAntiAlias = true
    }

    private var wavePhase = 0f

    init {
        startAnimation()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val width = width.toFloat()
        val height = height.toFloat()
        val amplitude = height / 4
        val frequency = 2 * Math.PI / width

        drawWave(canvas, paint1, width, height, amplitude, frequency, wavePhase)
        drawWave(canvas, paint2, width, height, amplitude, frequency, wavePhase + 2)
        drawWave(canvas, paint3, width, height, amplitude, frequency, wavePhase + 4)
    }

    private fun drawWave(
        canvas: Canvas,
        paint: Paint,
        width: Float,
        height: Float,
        amplitude: Float,
        frequency: Double,
        phase: Float
    ) {
        val path = android.graphics.Path()
        path.moveTo(0f, height / 2)

        for (x in 0..width.toInt()) {
            val y = (amplitude * Math.sin(frequency * x + phase)).toFloat() + height / 2
            path.lineTo(x.toFloat(), y)
        }

        canvas.drawPath(path, paint)
    }

    private fun startAnimation() {
        val animator = ValueAnimator.ofFloat(0f, (2 * Math.PI).toFloat()).apply {
            duration = 2000
            repeatCount = ValueAnimator.INFINITE
            addUpdateListener { animation ->
                wavePhase = animation.animatedValue as Float
                invalidate()
            }
        }
        animator.start()
    }
}
