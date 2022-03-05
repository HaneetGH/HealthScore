package com.technorapper.onboarding.widgets

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Typeface
import android.util.AttributeSet
import android.view.View
import com.technorapper.onboarding.R



class TemperatureGraphView2 : View {

    var prevTemp: Double? = null
    var currentTemp: Double? = null
    var nextTemp: Double? = null

    private var maxTemp: Double = .0
    private var minTemp: Double = .0

    private var dotPaint: Paint
    private var linePaint: Paint
    private var textPaint: Paint

    private val themedColor by lazy {

        context.getThemeColor(android.R.attr.textColorPrimary)
    }

    var isBold: Boolean = false
        set(value) {
            field = value
            if (isBold)
                textPaint.typeface = Typeface.DEFAULT_BOLD
            else textPaint.typeface = Typeface.DEFAULT
        }

    init {
        dotPaint = initDotPaint()
        linePaint = initLinePaint()
        textPaint = initTextPaint()
    }

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    constructor(
        context: Context?,
        attrs: AttributeSet?,
        defStyleAttr: Int,
        defStyleRes: Int
    ) : super(context, attrs, defStyleAttr, defStyleRes)

    override fun draw(canvas: Canvas?) {
        super.draw(canvas)


        // canvas?.drawText("20", 10f, 25f, textPaint)
        drawCurrentTemperature(canvas!!, textPaint)
        drawCurrentTempDot(canvas!!, dotPaint)
        drawLineToNext(canvas!!, linePaint)
        /*canvas?.let {
          // drawCurrentTempDot(it, dotPaint)
            drawCurrentTemperature(canvas, textPaint)
           // drawLineToNext(it, linePaint)
           // drawLineToPrev(it, linePaint)
        }*/


    }

    private fun initDotPaint(): Paint {
        val dotPaint = Paint()
        dotPaint.color = resources.getColor(R.color.white)// themedColor
        dotPaint.flags = Paint.ANTI_ALIAS_FLAG
        return dotPaint
    }

    private fun initLinePaint(): Paint {
        val linePaint = Paint()
        linePaint.color = resources.getColor(R.color.white)
        linePaint.flags = Paint.ANTI_ALIAS_FLAG
        linePaint.style = Paint.Style.FILL_AND_STROKE
        linePaint.strokeWidth = dpToPx(2.7).toFloat()
        return linePaint
    }

    private fun initTextPaint(): Paint {
        val textPaint = Paint()
        textPaint.color = resources.getColor(R.color.white)
        textPaint.flags = Paint.ANTI_ALIAS_FLAG
        textPaint.textSize = 20f //spToPx(14f).toFloat()
        return textPaint

        /*  val paint = Paint()
          paint.color = Color.WHITE
          paint.style = Paint.Style.FILL
         // canvas!!.drawPaint(paint)

          paint.color = Color.BLACK
          paint.textSize = 20f
          return paint*/
    }

    private fun drawCurrentTempDot(canvas: Canvas, paint: Paint) {
        canvas.drawCircle(
            measuredWidth.toFloat() / 2,
            22f,
            5f,
            paint
        )
    }

    private fun drawCurrentTemperature(canvas: Canvas, paint: Paint) {

        canvas.drawText(
            "8℃",
            10f,
            25f,
            paint
        )

    }

    private fun drawLineToNext(canvas: Canvas, paint: Paint) {
        ifNotNull(currentTemp, nextTemp) { current, next ->

        }
        canvas.drawLine(
            10f, 20f, 30f,
            40f,
            paint
        )
    }

    private fun drawLineToPrev(canvas: Canvas, paint: Paint) {
        ifNotNull(currentTemp, prevTemp) { current, prev ->
            canvas.drawLine(
                measuredWidth.toFloat() / 2, dpToPx(calculateY(current)).toFloat(),
                -measuredWidth.toFloat() / 2, dpToPx(calculateY(prev)).toFloat(),
                paint
            )
        }
    }

    /**
     * посчитать координату y, считая сверху
     * @return координата y в dp
     */
    private fun calculateY(temperature: Double): Double {
        return (maxTemp - temperature) * 5 + 30
    }

    private fun resizeSelfToTemperature() {
        val height = maxOf(70, calculateTempGraphHeight())
        layoutParams.height = dpToPx(height.toDouble()).toInt()
    }

    /**
     * высота графика в зависимости от разброса температуры
     * @return высота в dp
     */
    private fun calculateTempGraphHeight(): Int {
        return (maxTemp - minTemp).toInt() * 5 + 50
    }

    /**
     * установить разброс температуры
     */
    fun setMinMaxTemp(minTemp: Double, maxTemp: Double) {
        this.minTemp = minTemp
        this.maxTemp = maxTemp
        resizeSelfToTemperature()
    }
}