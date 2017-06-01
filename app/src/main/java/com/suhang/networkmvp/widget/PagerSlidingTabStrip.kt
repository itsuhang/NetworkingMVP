package com.suhang.networkmvp.widget

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Paint.Style
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Parcel
import android.os.Parcelable
import android.support.v4.view.ViewPager
import android.support.v4.view.ViewPager.OnPageChangeListener
import android.util.AttributeSet
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.view.ViewTreeObserver.OnGlobalLayoutListener
import android.widget.*
import com.suhang.networkmvp.R
import com.suhang.networkmvp.constants.dip2px
import java.util.*


/**
 * 类似TabLayout,功能进行扩展
 * Created by sh on 2016/4/27 .
 */
class PagerSlidingTabStrip @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyle: Int = 0) : HorizontalScrollView(context, attrs, defStyle) {

    private val bitmap: Bitmap? = null
    private val drawable: Drawable = resources.getDrawable(R.drawable.arrow_down_tab)

    interface IconTabProvider {
        fun getPageIconResId(position: Int): Int
    }

    private val defaultTabLayoutParams: LinearLayout.LayoutParams
    private val expandedTabLayoutParams: LinearLayout.LayoutParams

    private val pageListener = PageListener()
    var delegatePageListener: OnPageChangeListener? = null

    private val tabsContainer: LinearLayout
    private var pager: ViewPager? = null

    private var tabCount: Int = 0

    private var currentPosition = 0
    private var currentPositionOffset = 0f

    private val rectPaint: Paint
    private val dividerPaint: Paint

    private var indicatorColor = 0xFF666666.toInt()
    private var underlineColor = 0x1A000000
    private var dividerColor = 0x1A000000

    private var shouldExpand = false
    var isTextAllCaps = true
        private set

    private var scrollOffset = 52
    private var indicatorHeight = 8
    private var underlineHeight = 2
    private var dividerPadding = 12
    private var tabPadding = 24
    private var dividerWidth = 1

    private var tabTextSize = 12
    private var tabTextColorNormal = 0xFF666666.toInt()
    private var tabTextColorSelected = 0xFF666666.toInt()
    private var tabTypeface: Typeface? = null
    private var tabTypefaceStyle = Typeface.NORMAL
    private var tabIndicatorPadding = 0

    private var lastScrollX = 0

    var tabBackground = R.drawable.background_tab

    private var locale: Locale? = null

    private var isShowArrow = false

    init {
        isFillViewport = true
        setWillNotDraw(false)
        tabsContainer = LinearLayout(context)
        tabsContainer.orientation = LinearLayout.HORIZONTAL
        tabsContainer.layoutParams = FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT)
        addView(tabsContainer)

        val dm = resources.displayMetrics

        scrollOffset = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, scrollOffset.toFloat(), dm).toInt()
        indicatorHeight = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, indicatorHeight.toFloat(), dm).toInt()
        underlineHeight = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, underlineHeight.toFloat(), dm).toInt()
        dividerPadding = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dividerPadding.toFloat(), dm).toInt()
        tabPadding = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, tabPadding.toFloat(), dm).toInt()
        dividerWidth = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dividerWidth.toFloat(), dm).toInt()
        tabTextSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, tabTextSize.toFloat(), dm).toInt()
        var a = context.obtainStyledAttributes(attrs, ATTRS)
        a.recycle()

        // get custom attrs

        a = context.obtainStyledAttributes(attrs, R.styleable.PagerSlidingTabStrip)

        indicatorColor = a.getColor(R.styleable.PagerSlidingTabStrip_pstsIndicatorColor, indicatorColor)
        underlineColor = a.getColor(R.styleable.PagerSlidingTabStrip_pstsUnderlineColor, underlineColor)
        dividerColor = a.getColor(R.styleable.PagerSlidingTabStrip_pstsDividerColor, dividerColor)
        indicatorHeight = a.getDimensionPixelSize(R.styleable.PagerSlidingTabStrip_pstsIndicatorHeight, indicatorHeight)
        underlineHeight = a.getDimensionPixelSize(R.styleable.PagerSlidingTabStrip_pstsUnderlineHeight, underlineHeight)
        dividerPadding = a.getDimensionPixelSize(R.styleable.PagerSlidingTabStrip_pstsDividerPadding, dividerPadding)
        tabPadding = a.getDimensionPixelSize(R.styleable.PagerSlidingTabStrip_pstsTabPaddingLeftRight, tabPadding)
        tabBackground = a.getResourceId(R.styleable.PagerSlidingTabStrip_pstsTabBackground, tabBackground)
        shouldExpand = a.getBoolean(R.styleable.PagerSlidingTabStrip_pstsShouldExpand, shouldExpand)
        scrollOffset = a.getDimensionPixelSize(R.styleable.PagerSlidingTabStrip_pstsScrollOffset, scrollOffset)
        isTextAllCaps = a.getBoolean(R.styleable.PagerSlidingTabStrip_pstsTextAllCaps, isTextAllCaps)
        tabTextSize = a.getDimensionPixelSize(R.styleable.PagerSlidingTabStrip_pstsTextSize, tabTextSize)
        tabIndicatorPadding = a.getDimensionPixelSize(R.styleable.PagerSlidingTabStrip_pstsIndicatiorPadding, tabIndicatorPadding)
        tabTextColorNormal = a.getColor(R.styleable.PagerSlidingTabStrip_pstsTextColorNormal, tabTextColorNormal)
        tabTextColorSelected = a.getColor(R.styleable.PagerSlidingTabStrip_pstsTextColorSelected, tabTextColorSelected)
        a.recycle()

        rectPaint = Paint()
        rectPaint.isAntiAlias = true
        rectPaint.style = Style.FILL

        dividerPaint = Paint()
        dividerPaint.isAntiAlias = true
        dividerPaint.strokeWidth = dividerWidth.toFloat()

        defaultTabLayoutParams = LinearLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.MATCH_PARENT)
        expandedTabLayoutParams = LinearLayout.LayoutParams(0, FrameLayout.LayoutParams.MATCH_PARENT, 1.0f)

        if (locale == null) {
            locale = resources.configuration.locale
        }
    }

    fun setViewPager(pager: ViewPager) {
        this.pager = pager

        if (pager.adapter == null) {
            throw IllegalStateException("ViewPager does not have adapter instance.")
        }

        pager.addOnPageChangeListener(pageListener)

        notifyDataSetChanged()
    }

    fun setOnPageChangeListener(listener: OnPageChangeListener) {
        this.delegatePageListener = listener
    }

    fun notifyDataSetChanged() {

        tabsContainer.removeAllViews()

        tabCount = pager!!.adapter.count

        for (i in 0..tabCount - 1) {

            if (pager!!.adapter is IconTabProvider) {
                addIconTab(i, (pager!!.adapter as IconTabProvider).getPageIconResId(i))
            } else {
                addTextTab(i, pager!!.adapter.getPageTitle(i).toString())
            }

        }

        updateTabStyles()

        viewTreeObserver.addOnGlobalLayoutListener(object : OnGlobalLayoutListener {

            @SuppressLint("NewApi")
            override fun onGlobalLayout() {

                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                    viewTreeObserver.removeGlobalOnLayoutListener(this)
                } else {
                    viewTreeObserver.removeOnGlobalLayoutListener(this)
                }

                currentPosition = pager!!.currentItem
                scrollToChild(currentPosition, 0)
            }
        })

    }

    private fun addTextTab(position: Int, title: String) {

        val tab = TextView(context)
        tab.text = title
        tab.gravity = Gravity.CENTER
        tab.setSingleLine()

        addTab(position, tab)
    }

    private fun addIconTab(position: Int, resId: Int) {

        val tab = ImageButton(context)
        tab.setImageResource(resId)

        addTab(position, tab)

    }

    private fun addTab(position: Int, tab: View) {
        tab.isFocusable = true
        tab.setOnClickListener { pager!!.currentItem = position }

        tab.setPadding(tabPadding, 0, tabPadding, 0)
        tabsContainer.addView(tab, position, if (shouldExpand) expandedTabLayoutParams else defaultTabLayoutParams)
    }

    private fun updateTabStyles() {
        for (i in 0..tabCount - 1) {

            val v = tabsContainer.getChildAt(i)

            v.setBackgroundResource(tabBackground)

            if (v is TextView) {

                val tab = v
                tab.setTextSize(TypedValue.COMPLEX_UNIT_PX, tabTextSize.toFloat())
                tab.setTypeface(tabTypeface, tabTypefaceStyle)
                if (currentPosition == i) {
                    tab.setTextColor(tabTextColorSelected)
                } else {
                    tab.setTextColor(tabTextColorNormal)
                }
                if (isTextAllCaps) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
                        tab.setAllCaps(true)
                    } else {
                        tab.text = tab.text.toString().toUpperCase(locale!!)
                    }
                }
            }
        }

    }

    private fun scrollToChild(position: Int, offset: Int) {

        if (tabCount == 0) {
            return
        }

        var newScrollX = tabsContainer.getChildAt(position).left + offset

        if (position > 0 || offset > 0) {
            newScrollX -= scrollOffset
        }

        if (newScrollX != lastScrollX) {
            lastScrollX = newScrollX
            scrollTo(newScrollX, 0)
        }

    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        if (isInEditMode || tabCount == 0) {
            return
        }

        val height = height

        rectPaint.color = indicatorColor

        val currentTab = tabsContainer.getChildAt(currentPosition)
        var lineLeft = currentTab.left.toFloat()
        var lineRight = currentTab.right.toFloat()
        val arrowLeft = currentTab.left + (currentTab.right - currentTab.left) / 2 - dip2px(20f) / 2
        if (currentPositionOffset > 0f && currentPosition < tabCount - 1) {

            val nextTab = tabsContainer.getChildAt(currentPosition)
            val nextTabLeft = nextTab.left.toFloat()
            val nextTabRight = nextTab.right.toFloat()
            lineLeft = currentPositionOffset * nextTabLeft + (1f - currentPositionOffset) * lineLeft
            lineRight = currentPositionOffset * nextTabRight + (1f - currentPositionOffset) * lineRight
        }
        canvas.drawRect(lineLeft + tabIndicatorPadding, (height - indicatorHeight).toFloat(), lineRight - tabIndicatorPadding, height.toFloat(), rectPaint)
        if (isShowArrow) {
            drawable.setBounds(arrowLeft, height - dip2px(5f) - dip2px(5f), (arrowLeft + dip2px(20f)), height - dip2px(5f))
            drawable.draw(canvas)
        }
        rectPaint.color = underlineColor
        canvas.drawRect(0f, (height - underlineHeight).toFloat(), tabsContainer.width.toFloat(), height.toFloat(), rectPaint)

        dividerPaint.color = dividerColor
        for (i in 0..tabCount - 1 - 1) {
            val tab = tabsContainer.getChildAt(i)
            canvas.drawLine(tab.right.toFloat(), dividerPadding.toFloat(), tab.right.toFloat(), (height - dividerPadding).toFloat(), dividerPaint)
        }
    }

    private inner class PageListener : OnPageChangeListener {

        override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            currentPositionOffset = positionOffset
            scrollToChild(position, (positionOffset * tabsContainer.getChildAt(position).width).toInt())
            invalidate()

            if (delegatePageListener != null) {
                delegatePageListener!!.onPageScrolled(position, positionOffset, positionOffsetPixels)
            }
        }

        override fun onPageScrollStateChanged(state: Int) {
            if (state == ViewPager.SCROLL_STATE_IDLE) {
                scrollToChild(pager!!.currentItem, 0)
            }

            if (delegatePageListener != null) {
                delegatePageListener!!.onPageScrollStateChanged(state)
            }
        }

        override fun onPageSelected(position: Int) {
            currentPosition = position
            updateTabStyles()
            if (delegatePageListener != null) {
                delegatePageListener!!.onPageSelected(position)
            }
        }

    }

    fun setIsShowArrow(isShowArrow: Boolean) {
        this.isShowArrow = isShowArrow
    }

    fun setIndicatorColor(indicatorColor: Int) {
        this.indicatorColor = indicatorColor
        invalidate()
    }

    fun setIndicatorColorResource(resId: Int) {
        this.indicatorColor = resources.getColor(resId)
        invalidate()
    }

    fun getIndicatorColor(): Int {
        return this.indicatorColor
    }

    fun setIndicatorHeight(indicatorLineHeightPx: Int) {
        this.indicatorHeight = indicatorLineHeightPx
        invalidate()
    }

    fun getIndicatorHeight(): Int {
        return indicatorHeight
    }

    fun setUnderlineColor(underlineColor: Int) {
        this.underlineColor = underlineColor
        invalidate()
    }

    fun setUnderlineColorResource(resId: Int) {
        this.underlineColor = resources.getColor(resId)
        invalidate()
    }

    fun getUnderlineColor(): Int {
        return underlineColor
    }

    fun setDividerColor(dividerColor: Int) {
        this.dividerColor = dividerColor
        invalidate()
    }

    fun setDividerColorResource(resId: Int) {
        this.dividerColor = resources.getColor(resId)
        invalidate()
    }

    fun getDividerColor(): Int {
        return dividerColor
    }

    fun setUnderlineHeight(underlineHeightPx: Int) {
        this.underlineHeight = underlineHeightPx
        invalidate()
    }

    fun getUnderlineHeight(): Int {
        return underlineHeight
    }

    fun setDividerPadding(dividerPaddingPx: Int) {
        this.dividerPadding = dividerPaddingPx
        invalidate()
    }

    fun getDividerPadding(): Int {
        return dividerPadding
    }

    fun setScrollOffset(scrollOffsetPx: Int) {
        this.scrollOffset = scrollOffsetPx
        invalidate()
    }

    fun getScrollOffset(): Int {
        return scrollOffset
    }

    fun setShouldExpand(shouldExpand: Boolean) {
        this.shouldExpand = shouldExpand
        requestLayout()
    }

    fun getShouldExpand(): Boolean {
        return shouldExpand
    }

    fun setAllCaps(textAllCaps: Boolean) {
        this.isTextAllCaps = textAllCaps
    }

    var textSize: Int
        get() = tabTextSize
        set(textSizePx) {
            this.tabTextSize = textSizePx
            updateTabStyles()
        }

    fun setTextColorResource(resId: Int) {
        this.tabTextColorNormal = resources.getColor(resId)
        updateTabStyles()
    }

    var textColor: Int
        get() = tabTextColorNormal
        set(textColor) {
            this.tabTextColorNormal = textColor
            updateTabStyles()
        }

    fun setTypeface(typeface: Typeface, style: Int) {
        this.tabTypeface = typeface
        this.tabTypefaceStyle = style
        updateTabStyles()
    }

    var tabPaddingLeftRight: Int
        get() = tabPadding
        set(paddingPx) {
            this.tabPadding = paddingPx
            updateTabStyles()
        }

    public override fun onRestoreInstanceState(state: Parcelable) {
        val savedState = state as SavedState
        super.onRestoreInstanceState(savedState.superState)
        currentPosition = savedState.currentPosition
        requestLayout()
    }

    public override fun onSaveInstanceState(): Parcelable {
        val superState = super.onSaveInstanceState()
        val savedState = SavedState(superState)
        savedState.currentPosition = currentPosition
        return savedState
    }

    internal class SavedState : View.BaseSavedState {
        var currentPosition: Int = 0

        constructor(superState: Parcelable) : super(superState) {}

        private constructor(`in`: Parcel) : super(`in`) {
            currentPosition = `in`.readInt()
        }

        override fun writeToParcel(dest: Parcel, flags: Int) {
            super.writeToParcel(dest, flags)
            dest.writeInt(currentPosition)
        }

        companion object {

            val CREATOR: Parcelable.Creator<SavedState> = object : Parcelable.Creator<SavedState> {
                override fun createFromParcel(`in`: Parcel): SavedState {
                    return SavedState(`in`)
                }

                override fun newArray(size: Int): Array<SavedState?> {
                    return arrayOfNulls(size)
                }
            }
        }
    }

    companion object {

        private val ATTRS = intArrayOf(android.R.attr.textSize, android.R.attr.textColor)
    }

}
