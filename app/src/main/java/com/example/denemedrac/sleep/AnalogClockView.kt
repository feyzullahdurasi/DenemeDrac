package com.example.denemedrac.sleep

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import java.util.*

class AnalogClockView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : View(context, attrs) {

    private val paint = Paint().apply {
        isAntiAlias = true
        strokeWidth = 4f
        style = Paint.Style.STROKE
        color = Color.WHITE
    }

    private val redPaint = Paint().apply {
        isAntiAlias = true
        strokeWidth = 2f
        style = Paint.Style.STROKE
        color = Color.RED
    }

    private val calendar = Calendar.getInstance()

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val centerX = width / 2f
        val centerY = height / 2f
        val radius = Math.min(centerX, centerY) - 20

        // Draw the clock circle
        canvas.drawCircle(centerX, centerY, radius, paint)

        // Get current time
        val hour = calendar.get(Calendar.HOUR)
        val minute = calendar.get(Calendar.MINUTE)
        val second = calendar.get(Calendar.SECOND)

        // Draw hour hand
        drawHand(canvas, hour + minute / 60.0, 12, centerX, centerY, radius * 0.5, paint)

        // Draw minute hand
        drawHand(canvas, minute + second / 60.0, 60, centerX, centerY, radius * 0.7, paint)

        // Draw second hand
        drawHand(canvas, second.toDouble(), 60, centerX, centerY, radius * 0.9, redPaint)

        // Invalidate view for the next tick
        postInvalidateDelayed(1000)
    }

    private fun drawHand(
        canvas: Canvas?,
        value: Double,
        max: Int,
        centerX: Float,
        centerY: Float,
        length: Double,
        paint: Paint
    ) {
        val angle = Math.toRadians(value / max * 360.0 - 90)
        val endX = centerX + Math.cos(angle) * length
        val endY = centerY + Math.sin(angle) * length
        canvas?.drawLine(centerX, centerY, endX.toFloat(), endY.toFloat(), paint)
    }
}
