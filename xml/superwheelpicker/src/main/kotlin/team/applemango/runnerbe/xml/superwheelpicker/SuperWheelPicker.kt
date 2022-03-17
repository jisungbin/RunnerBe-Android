package team.applemango.runnerbe.xml.superwheelpicker

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Typeface
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.VelocityTracker
import android.view.View
import android.view.ViewConfiguration
import android.view.ViewGroup
import android.view.animation.DecelerateInterpolator
import android.widget.OverScroller
import androidx.annotation.ColorInt
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

class SuperWheelPicker @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : View(context, attrs, defStyleAttr) {

    private companion object {
        const val TOP_AND_BOTTOM_FADING_EDGE_STRENGTH = 0.9f
        const val SNAP_SCROLL_DURATION = 300
        const val SELECTOR_MAX_FLING_VELOCITY_ADJUSTMENT = 4
        const val DEFAULT_ITEM_COUNT = 3
        const val DEFAULT_TEXT_SIZE = 80
        val DEFAULT_LAYOUT_PARAMS = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
    }

    private val configuration = ViewConfiguration.get(context)
    private var onValueChangeListener: OnValueChangeListener? = null
    private var onScrollListener: OnScrollListener? = null
    private var onTextRenderListener: OnTextRenderListener? = null

    private var selectorItemCount = DEFAULT_ITEM_COUNT + 2
    private var selectorVisibleItemCount = DEFAULT_ITEM_COUNT
    private var minIndex = Int.MIN_VALUE
    private var maxIndex = Int.MAX_VALUE

    private var wheelMiddleItemIndex = (selectorVisibleItemCount - 1) / 2
    private var wheelVisibleItemMiddleIndex = (selectorVisibleItemCount - 1) / 2
    private var selectorItemIndices = ArrayList<Int>(selectorItemCount)
    private var selectorItemValidStatus = ArrayList<Boolean>(selectorItemCount)
    private var curSelectedItemIndex = 0
    private var wrapSelectorWheelPreferred = false

    private var textPaint = Paint().apply {
        isAntiAlias = true
        style = Paint.Style.FILL_AND_STROKE
        typeface = Typeface.DEFAULT
    }
    private var selectedTextColor = Color.BLACK
    private var unSelectedTextColor = Color.LTGRAY
    private var textSize = DEFAULT_TEXT_SIZE
    private var textAlign = Paint.Align.CENTER

    private var overScroller = OverScroller(context, DecelerateInterpolator(2.5f))
    private var velocityTracker: VelocityTracker? = null
    private val touchSlop = configuration.scaledTouchSlop
    private val maximumVelocity =
        configuration.scaledMaximumFlingVelocity / SELECTOR_MAX_FLING_VELOCITY_ADJUSTMENT
    private val minimumVelocity = configuration.scaledMinimumFlingVelocity

    private var lastY = 0f
    private var isDragging = false
    private var currentFirstItemOffset = 0
    private var initialFirstItemOffset = Int.MIN_VALUE
    private var textGapHeight = 0
    private var itemHeight = 0
    private var textHeight = 0
    private var previousScrollerY = 0
    private var fadingEdgeEnabled = true
    private var selectedTextScale = 0.3f
    private var scrollState = OnScrollListener.SCROLL_STATE_IDLE

    init {
        updatePainter()
        initializeSelectorWheelIndices()
    }

    private fun updatePainter() {
        textPaint = textPaint.apply {
            textSize = this@SuperWheelPicker.textSize.toFloat()
            textAlign = this@SuperWheelPicker.textAlign
        }
    }

    fun setTextRenderListener(onTextRenderListener: OnTextRenderListener) {
        this.onTextRenderListener = onTextRenderListener
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)

        if (changed) {
            initializeSelectorWheel()
            initializeFadingEdges()
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val lp = layoutParams ?: DEFAULT_LAYOUT_PARAMS

        var width = calculateSize(suggestedMinimumWidth, lp.width, widthMeasureSpec)
        var height = calculateSize(suggestedMinimumHeight, lp.height, heightMeasureSpec)

        width += paddingLeft + paddingRight
        height += paddingTop + paddingBottom

        setMeasuredDimension(width, height)
    }

    override fun getSuggestedMinimumWidth(): Int {
        var suggested = super.getSuggestedMinimumHeight()
        if (selectorVisibleItemCount > 0) {
            suggested = max(suggested, computeMaximumWidth())
        }
        return suggested
    }

    override fun getSuggestedMinimumHeight(): Int {
        var suggested = super.getSuggestedMinimumWidth()
        if (selectorVisibleItemCount > 0) {
            val fontMetricsInt = textPaint.fontMetricsInt
            val height = fontMetricsInt.descent - fontMetricsInt.ascent
            suggested = max(suggested, height * selectorVisibleItemCount)
        }
        return suggested
    }

    private fun computeMaximumWidth(): Int {
        textPaint.textSize = textSize * 1.3f
        val widthForMinIndex = textPaint.measureText(minIndex.toString()).toInt()
        val widthForMaxIndex = textPaint.measureText(maxIndex.toString()).toInt()
        textPaint.textSize = textSize * 1.0f
        return max(widthForMinIndex, widthForMaxIndex)
    }

    private fun calculateSize(suggestedSize: Int, paramSize: Int, measureSpec: Int): Int {
        val size = MeasureSpec.getSize(measureSpec)
        val mode = MeasureSpec.getMode(measureSpec)

        return when (MeasureSpec.getMode(mode)) {
            MeasureSpec.AT_MOST -> when (paramSize) {
                ViewGroup.LayoutParams.WRAP_CONTENT -> min(suggestedSize, size)
                ViewGroup.LayoutParams.MATCH_PARENT -> size
                else -> min(paramSize, size)
            }
            MeasureSpec.EXACTLY -> size
            MeasureSpec.UNSPECIFIED -> {
                if (
                    paramSize == ViewGroup.LayoutParams.WRAP_CONTENT ||
                    paramSize == ViewGroup.LayoutParams.MATCH_PARENT
                ) {
                    suggestedSize
                } else {
                    paramSize
                }
            }
            else -> 0
        }
    }

    private fun initializeSelectorWheel() {
        itemHeight = getItemHeight()
        textHeight = computeTextHeight()
        textGapHeight = getGapHeight()

        val visibleMiddleItemPos =
            itemHeight * wheelVisibleItemMiddleIndex + (itemHeight + textHeight) / 2
        initialFirstItemOffset = visibleMiddleItemPos - itemHeight * wheelMiddleItemIndex
        currentFirstItemOffset = initialFirstItemOffset
    }

    private fun initializeFadingEdges() {
        isVerticalFadingEdgeEnabled = fadingEdgeEnabled
        if (fadingEdgeEnabled) {
            setFadingEdgeLength((bottom - top - textSize) / 2)
        }
    }

    private fun initializeSelectorWheelIndices() {
        selectorItemIndices.clear()
        selectorItemValidStatus.clear()

        curSelectedItemIndex = if (minIndex <= 0) {
            0
        } else {
            minIndex
        }
        for (i in 0 until selectorItemCount) {
            var selectorIndex = curSelectedItemIndex + (i - wheelMiddleItemIndex)
            if (wrapSelectorWheelPreferred) {
                selectorIndex = getWrappedSelectorIndex(selectorIndex)
            }
            selectorItemIndices.add(selectorIndex)
            selectorItemValidStatus.add(true)
        }
    }

    override fun getBottomFadingEdgeStrength() = TOP_AND_BOTTOM_FADING_EDGE_STRENGTH

    override fun getTopFadingEdgeStrength() = TOP_AND_BOTTOM_FADING_EDGE_STRENGTH

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        drawVertical(canvas)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        onTouchEventVertical(event)
        return true
    }

    private fun onTouchEventVertical(event: MotionEvent) {
        if (velocityTracker == null) {
            velocityTracker = VelocityTracker.obtain()
        }

        velocityTracker?.addMovement(event)

        when (event.actionMasked) {
            MotionEvent.ACTION_DOWN -> {
                if (!overScroller.isFinished) {
                    overScroller.forceFinished(true)
                }

                lastY = event.y
            }
            MotionEvent.ACTION_MOVE -> {
                var deltaY = event.y - lastY
                if (!isDragging && abs(deltaY) > touchSlop) {
                    parent?.requestDisallowInterceptTouchEvent(true)
                    if (deltaY > 0) {
                        deltaY -= touchSlop
                    } else {
                        deltaY += touchSlop
                    }
                    onScrollStateChange(OnScrollListener.SCROLL_STATE_TOUCH_SCROLL)
                    isDragging = true
                }

                if (isDragging) {
                    scrollBy(0, deltaY.toInt())
                    invalidate()
                    lastY = event.y
                }
            }
            MotionEvent.ACTION_UP -> {
                if (isDragging) {
                    isDragging = false
                    parent?.requestDisallowInterceptTouchEvent(false)

                    velocityTracker?.computeCurrentVelocity(1000, maximumVelocity.toFloat())
                    val velocity = velocityTracker?.yVelocity?.toInt()

                    if (abs(velocity!!) > minimumVelocity) {
                        previousScrollerY = 0
                        overScroller.fling(
                            scrollX, scrollY, 0, velocity, 0, 0, Integer.MIN_VALUE,
                            Integer.MAX_VALUE, 0, (getItemHeight() * 0.7).toInt()
                        )
                        invalidateOnAnimation()
                        onScrollStateChange(OnScrollListener.SCROLL_STATE_FLING)
                    }
                    recyclerVelocityTracker()
                } else {
                    val y = event.y.toInt()
                    handlerClickVertical(y)
                }
            }

            MotionEvent.ACTION_CANCEL -> {
                if (isDragging) {
                    isDragging = false
                }
                recyclerVelocityTracker()
            }
        }
    }

    private fun handlerClickVertical(y: Int) {
        val selectorIndexOffset = y / itemHeight - wheelVisibleItemMiddleIndex
        changeValueBySteps(selectorIndexOffset)
    }

    override fun scrollBy(x: Int, y: Int) {
        if (y == 0) return

        val gap = textGapHeight

        if (
            !wrapSelectorWheelPreferred && y > 0 &&
            selectorItemIndices[wheelMiddleItemIndex] <= minIndex
        ) {
            if (currentFirstItemOffset + y - initialFirstItemOffset < gap / 2) {
                currentFirstItemOffset += y
            } else {
                currentFirstItemOffset = initialFirstItemOffset + (gap / 2)
                if (!overScroller.isFinished && !isDragging) {
                    overScroller.abortAnimation()
                }
            }
            return
        }

        if (
            !wrapSelectorWheelPreferred && y < 0 &&
            selectorItemIndices[wheelMiddleItemIndex] >= maxIndex
        ) {
            if (currentFirstItemOffset + y - initialFirstItemOffset > -(gap / 2)) {
                currentFirstItemOffset += y
            } else {
                currentFirstItemOffset = initialFirstItemOffset - (gap / 2)
                if (!overScroller.isFinished && !isDragging) {
                    overScroller.abortAnimation()
                }
            }
            return
        }

        currentFirstItemOffset += y

        while (currentFirstItemOffset - initialFirstItemOffset < -gap) {
            currentFirstItemOffset += itemHeight
            increaseSelectorsIndex()
            if (
                !wrapSelectorWheelPreferred &&
                selectorItemIndices[wheelMiddleItemIndex] >= maxIndex
            ) {
                currentFirstItemOffset = initialFirstItemOffset
            }
        }

        while (currentFirstItemOffset - initialFirstItemOffset > gap) {
            currentFirstItemOffset -= itemHeight
            decreaseSelectorsIndex()
            if (
                !wrapSelectorWheelPreferred &&
                selectorItemIndices[wheelMiddleItemIndex] <= minIndex
            ) {
                currentFirstItemOffset = initialFirstItemOffset
            }
        }
        onSelectionChanged(selectorItemIndices[wheelMiddleItemIndex])
    }

    override fun computeScroll() {
        super.computeScroll()
        if (overScroller.computeScrollOffset()) {
            val x = overScroller.currX
            val y = overScroller.currY

            if (previousScrollerY == 0) {
                previousScrollerY = overScroller.startY
            }
            scrollBy(x, y - previousScrollerY)
            previousScrollerY = y
            invalidate()
        } else {
            if (!isDragging) {
                adjustItemVertical()
            }
        }
    }

    private fun adjustItemVertical() {
        previousScrollerY = 0
        var deltaY = initialFirstItemOffset - currentFirstItemOffset

        if (abs(deltaY) > itemHeight / 2) {
            deltaY += if (deltaY > 0) {
                -itemHeight
            } else {
                itemHeight
            }
        }

        if (deltaY != 0) {
            overScroller.startScroll(scrollX, scrollY, 0, deltaY, 800)
            invalidateOnAnimation()
        }

        onScrollStateChange(OnScrollListener.SCROLL_STATE_IDLE)
    }

    private fun recyclerVelocityTracker() {
        velocityTracker?.recycle()
        velocityTracker = null
    }

    private fun onScrollStateChange(scrollState: Int) {
        if (this.scrollState == scrollState) {
            return
        }
        this.scrollState = scrollState
        onScrollListener?.onScrollStateChange(scrollState)
    }

    private fun getItemHeight() = height / (selectorItemCount - 2)

    private fun getGapHeight() = getItemHeight() - computeTextHeight()

    private fun computeTextHeight(): Int {
        val metricsInt = textPaint.fontMetricsInt
        return abs(metricsInt.bottom + metricsInt.top)
    }

    private fun invalidateOnAnimation() {
        postInvalidateOnAnimation()
    }

    private fun drawVertical(canvas: Canvas) {
        if (selectorItemIndices.size == 0) return
        val itemHeight = getItemHeight()

        val x = when (textPaint.textAlign) {
            Paint.Align.LEFT -> paddingLeft.toFloat()
            Paint.Align.CENTER -> ((right - left) / 2).toFloat()
            Paint.Align.RIGHT -> (right - left).toFloat() - paddingRight.toFloat()
            else -> ((right - left) / 2).toFloat()
        }

        var y = currentFirstItemOffset.toFloat()
        var i = 0

        val topIndexDiffToMid = wheelVisibleItemMiddleIndex
        val bottomIndexDiffToMid = selectorVisibleItemCount - wheelVisibleItemMiddleIndex - 1
        val maxIndexDiffToMid = max(topIndexDiffToMid, bottomIndexDiffToMid)

        while (i < selectorItemIndices.size) {
            var scale = 1f
            val value = getValue(selectorItemIndices[i])

            val offsetToMiddle =
                abs(y - (initialFirstItemOffset + wheelMiddleItemIndex * itemHeight).toFloat())

            if (maxIndexDiffToMid != 0) {
                scale =
                    selectedTextScale * (itemHeight * maxIndexDiffToMid - offsetToMiddle) / (itemHeight * maxIndexDiffToMid) + 1
            }

            if (selectorItemValidStatus[i]) {
                if (offsetToMiddle < this.itemHeight / 2) {
                    textPaint.color = selectedTextColor
                } else {
                    textPaint.color = unSelectedTextColor
                }
            } else {
                textPaint.color = Color.LTGRAY
            }

            canvas.save()
            canvas.scale(scale, scale, x, y)
            canvas.drawText(
                onTextRenderListener?.onTextRender(value) ?: value.toString(),
                x,
                y,
                textPaint
            )
            canvas.restore()

            y += itemHeight
            i++
        }
    }

    private fun getPosition(position: Int): Int {
        return try {
            validatePosition(position)
        } catch (e: NumberFormatException) {
            0
        }
    }

    private fun increaseSelectorsIndex() {
        for (i in 0 until (selectorItemIndices.size - 1)) {
            selectorItemIndices[i] = selectorItemIndices[i + 1]
            selectorItemValidStatus[i] = selectorItemValidStatus[i + 1]
        }
        var nextScrollSelectorIndex = selectorItemIndices[selectorItemIndices.size - 2] + 1
        if (wrapSelectorWheelPreferred && nextScrollSelectorIndex > maxIndex) {
            nextScrollSelectorIndex = minIndex
        }
        selectorItemIndices[selectorItemIndices.size - 1] = nextScrollSelectorIndex
        selectorItemValidStatus[selectorItemIndices.size - 1] = true
    }

    private fun decreaseSelectorsIndex() {
        for (i in selectorItemIndices.size - 1 downTo 1) {
            selectorItemIndices[i] = selectorItemIndices[i - 1]
            selectorItemValidStatus[i] = selectorItemValidStatus[i - 1]
        }
        var nextScrollSelectorIndex = selectorItemIndices[1] - 1
        if (wrapSelectorWheelPreferred && nextScrollSelectorIndex < minIndex) {
            nextScrollSelectorIndex = maxIndex
        }
        selectorItemIndices[0] = nextScrollSelectorIndex
        selectorItemValidStatus[0] = true
    }

    private fun changeValueBySteps(steps: Int) {
        previousScrollerY = 0
        overScroller.startScroll(0, 0, 0, -itemHeight * steps, SNAP_SCROLL_DURATION)
        invalidate()
    }

    private fun onSelectionChanged(current: Int) {
        val previous = curSelectedItemIndex
        curSelectedItemIndex = current
        if (previous != current) {
            notifyChange(previous, current)
        }
    }

    private fun getWrappedSelectorIndex(selectorIndex: Int): Int {
        if (selectorIndex > maxIndex) {
            return minIndex + (selectorIndex - maxIndex) % (maxIndex - minIndex + 1) - 1
        } else if (selectorIndex < minIndex) {
            return maxIndex - (minIndex - selectorIndex) % (maxIndex - minIndex + 1) + 1
        }
        return selectorIndex
    }

    private fun notifyChange(previous: Int, current: Int) {
        onValueChangeListener?.onValueChange(getValue(previous), getValue(current))
    }

    private fun validatePosition(position: Int): Int {
        return if (!wrapSelectorWheelPreferred) {
            position
        } else {
            getWrappedSelectorIndex(position)
        }
    }

    fun scrollTo(position: Int) {
        if (curSelectedItemIndex == position)
            return

        curSelectedItemIndex = position
        selectorItemIndices.clear()
        for (i in 0 until selectorItemCount) {
            var selectorIndex = curSelectedItemIndex + (i - wheelMiddleItemIndex)
            if (wrapSelectorWheelPreferred) {
                selectorIndex = getWrappedSelectorIndex(selectorIndex)
            }
            selectorItemIndices.add(selectorIndex)
        }

        invalidate()
    }

    fun setOnValueChangedListener(onValueChangeListener: OnValueChangeListener) {
        this.onValueChangeListener = onValueChangeListener
    }

    fun setOnScrollListener(onScrollListener: OnScrollListener) {
        this.onScrollListener = onScrollListener
    }

    fun smoothScrollTo(position: Int) {
        val realPosition = validatePosition(position)
        changeValueBySteps(realPosition - curSelectedItemIndex)
    }

    fun smoothScrollToValue(value: Int) {
        smoothScrollTo(getPosition(value))
    }

    @Suppress("MemberVisibilityCanBePrivate")
    fun scrollToValue(value: Int) {
        scrollTo(getPosition(value))
    }

    fun setUnselectedTextColor(@ColorInt color: Int) {
        unSelectedTextColor = color
    }

    /**
     * Set a custom typeface object for the text
     *
     * @param typeface the custom typeface object
     */
    fun setTypeface(typeface: Typeface?) {
        if (typeface != null) {
            textPaint.typeface = typeface
        }
    }

    /**
     * Sets whether the selector wheel shown during flinging/scrolling should
     * wrap around the {@link NumberPicker#getMinValue()} and
     * {@link NumberPicker#getMaxValue()} values.
     * <p>
     * By default if the range (max - min) is more than the number of items shown
     * on the selector wheel the selector wheel wrapping is enabled.
     * </p>
     * <p>
     * <strong>Note:</strong> If the number of items, i.e. the range (
     * {@link #getMaxValue()} - {@link #getMinValue()}) is less than
     * the number of items shown on the selector wheel, the selector wheel will
     * not wrap. Hence, in such a case calling this method is a NOP.
     * </p>
     *
     * @param wrapSelectorWheel Whether to wrap.
     */
    fun setWrapSelectorWheel(wrap: Boolean) {
        wrapSelectorWheelPreferred = wrap
        invalidate()
    }

    /**
     * Gets whether the selector wheel wraps when reaching the min/max value.
     *
     * @return True if the selector wheel wraps.
     *
     * @see .getMinValue
     * @see .getMaxValue
     */
    fun getWrapSelectorWheel(): Boolean {
        return wrapSelectorWheelPreferred
    }

    fun setRange(range: IntRange) {
        minIndex = range.first
        maxIndex = range.last
    }

    /**
     * Set how many visible item show in the picker
     */
    fun setWheelItemCount(count: Int) {
        selectorItemCount = count + 2
        wheelMiddleItemIndex = (selectorItemCount - 1) / 2
        selectorVisibleItemCount = selectorItemCount - 2
        wheelVisibleItemMiddleIndex = (selectorVisibleItemCount - 1) / 2
        selectorItemIndices = ArrayList(selectorItemCount)
        selectorItemValidStatus = ArrayList(selectorItemCount)
        reset()
        invalidate()
    }

    /**
     * Set color for current selected item
     */
    fun setSelectedTextColor(@ColorInt color: Int) {
        selectedTextColor = color
        invalidate()
    }

    fun getValue(position: Int): Int {
        return if (!wrapSelectorWheelPreferred) {
            when {
                position > maxIndex -> 0
                position < minIndex -> 0
                else -> position
            }
        } else {
            getWrappedSelectorIndex(position)
        }
    }

    fun setValue(value: Int) {
        scrollToValue(value)
    }

    private fun reset() {
        initializeSelectorWheelIndices()
        initializeSelectorWheel()
        invalidate()
    }

    fun getCurrentItem(): Int {
        return getValue(curSelectedItemIndex)
    }
}

internal fun Int.clamp(min: Int, max: Int): Int {
    if (this < min) return min
    return if (this > max) max else this
}
