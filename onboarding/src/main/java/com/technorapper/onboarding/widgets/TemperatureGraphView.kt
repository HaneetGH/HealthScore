package com.technorapper.onboarding.widgets

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Typeface
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import com.technorapper.onboarding.R



class TemperatureGraphView : View {

    var prevTemp: Double? = null
    var currentTemp: Double? = null
    var nextTemp: Double? = null

    private var maxTemp: Double = .0
    private var minTemp: Double = .0

    private var dotPaint: Paint
    private var linePaint: Paint
    private var textPaint: Paint


    private val themedColor by lazy {

        //context.getThemeColor(android.R.attr.textColorPrimary)
        context.getThemeColor(R.color.white)
    }

    fun Context.getThemeColor(attr: Int): Int {
        val typedValue: TypedValue = TypedValue()
        this.theme.resolveAttribute(attr, typedValue, true)
        return typedValue.data
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
        canvas?.let {
            drawCurrentTempDot(it, dotPaint)
            drawCurrentTemperature(it, textPaint)
            drawLineToNext(it, linePaint)
            drawLineToPrev(it, linePaint)
        }
    }

    private fun initDotPaint(): Paint {
        val dotPaint = Paint()
        dotPaint.color = resources.getColor(R.color.white)
        dotPaint.flags = Paint.ANTI_ALIAS_FLAG
        return dotPaint
    }

    private fun initLinePaint(): Paint {
        val linePaint = Paint()
        linePaint.color = resources.getColor(R.color.white)
        linePaint.flags = Paint.ANTI_ALIAS_FLAG
        linePaint.style = Paint.Style.FILL_AND_STROKE
        linePaint.strokeWidth = dpToPx(1.0).toFloat()
        return linePaint

    }

    fun View.dpToPx(dp: Double): Double {
        val scale = context.resources.displayMetrics.density
        return dp * scale + 0.5f
    }

    private fun initTextPaint(): Paint {
        val textPaint = Paint()
        textPaint.color = resources.getColor(R.color.white)
        textPaint.flags = Paint.ANTI_ALIAS_FLAG
        textPaint.textSize = spToPx(14f).toFloat()
        return textPaint
    }


    private fun drawCurrentTempDot(canvas: Canvas, paint: Paint) {
        currentTemp?.let {
            canvas.drawCircle(
                measuredWidth.toFloat() / 2,
                dpToPx(calculateY(it)).toFloat(),
                dpToPx(4.0).toFloat(),
                paint
            )
        }
        /* canvas.drawCircle(
             measuredWidth.toFloat() / 2,
             dpToPx(calculateY(currentTemp!!)).toFloat(),
             20f,
             paint
         )*/
    }

    private fun drawCurrentTemperature(canvas: Canvas, paint: Paint) {
        currentTemp?.let {
            canvas.drawText(
                "${Math.round(currentTemp!!)}${getDegreesUnits(context)}",
                measuredWidth.toFloat() / 2 - dpToPx(9.0).toFloat(),
                dpToPx(calculateY(currentTemp!!) - 10).toFloat(),
                paint
            )
        }

    }

    fun getDegreesUnits(context: Context): String {
        val metric: Boolean = true
        return if (metric) "\u2103" else "\u2109"
    }

    fun View.spToPx(sp: Float): Int {
        val px = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_SP,
            sp,
            context.resources.displayMetrics
        ).toInt()
        return px
    }


    private fun drawLineToNext(canvas: Canvas, paint: Paint) {
        ifNotNull(currentTemp, nextTemp) { current, next ->
            canvas.drawLine(
                measuredWidth.toFloat() / 2, dpToPx(calculateY(current)).toFloat(),
                measuredWidth.toFloat() * 3 / 2, dpToPx(calculateY(next)).toFloat(),
                paint
            )
        }
    }

    inline fun <A, B, R> ifNotNull(a: A?, b: B?, code: (A, B) -> R) {
        if (a != null && b != null)
            code(a, b)
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