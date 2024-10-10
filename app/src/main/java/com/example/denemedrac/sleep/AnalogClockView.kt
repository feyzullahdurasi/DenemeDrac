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
        strokeWidth = 8f // Çerçevenin kalınlığını artırıyoruz
        style = Paint.Style.STROKE
        color = Color.WHITE
    }

    private val redPaint = Paint().apply {
        isAntiAlias = true
        strokeWidth = 4f
        style = Paint.Style.STROKE
        color = Color.RED
    }

    private val numberPaint = Paint().apply {
        isAntiAlias = true
        color = Color.WHITE
        textSize = 50f // Rakamlar için büyük font boyutu
        textAlign = Paint.Align.CENTER
    }

    private val calendar = Calendar.getInstance()

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        // Her çizimde calendar objesini güncelle
        calendar.timeInMillis = System.currentTimeMillis()

        val centerX = width / 2f
        val centerY = height / 2f
        val radius = Math.min(centerX, centerY)

        // Saatin çerçevesini çiz
        canvas.drawCircle(centerX, centerY, radius, paint)

        // Saatin rakamlarını çiz
        drawNumerals(canvas, centerX, centerY, radius)

        // Anlık zamanı al
        val hour = calendar.get(Calendar.HOUR)
        val minute = calendar.get(Calendar.MINUTE)
        val second = calendar.get(Calendar.SECOND)

        // Saat göstergesini çiz
        drawHand(canvas, hour + minute / 60.0, 12, centerX, centerY, radius * 0.5, paint)

        // Dakika göstergesini çiz
        drawHand(canvas, minute + second / 60.0, 60, centerX, centerY, radius * 0.7, paint)

        // Saniye göstergesini çiz
        drawHand(canvas, second.toDouble(), 60, centerX, centerY, radius * 0.9, redPaint)

        // Her saniye saatin güncellenmesi için view'i yeniden çiz
        postInvalidateDelayed(1000)
    }

    // Saatin 12, 3, 6, 9 rakamlarını ve diğer çizgilerini ekliyoruz
    private fun drawNumerals(canvas: Canvas, centerX: Float, centerY: Float, radius: Float) {
        val numbers = arrayOf("12", "3", "6", "9")
        val angleStep = 90 // Rakamlar için 90 derece aralık
        for (i in numbers.indices) {
            val angle = Math.toRadians((i * angleStep - 90).toDouble())
            val x = centerX + Math.cos(angle) * (radius - 60)
            val y = centerY + Math.sin(angle) * (radius - 60) + numberPaint.textSize / 3
            canvas.drawText(numbers[i], x.toFloat(), y.toFloat(), numberPaint)
        }

        // 12 saat çizgisini ekleme
        for (i in 0 until 12) {
            val angle = Math.toRadians(i * 30.0 - 90) // 30 derece aralıklar
            val isMajorLine = (i % 3 == 0) // 3, 6, 9 ve 12 yönleri için uzun çizgi

            // Çizginin uzunluğunu ayarlıyoruz
            val lineStart = if (isMajorLine) radius - 30 else radius - 20
            val startX = centerX + Math.cos(angle) * lineStart
            val startY = centerY + Math.sin(angle) * lineStart
            val stopX = centerX + Math.cos(angle) * radius
            val stopY = centerY + Math.sin(angle) * radius

            // Çizgiyi çiz
            canvas.drawLine(startX.toFloat(), startY.toFloat(), stopX.toFloat(), stopY.toFloat(), paint)
        }
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
