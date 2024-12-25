package m.ify.bouiracomputerscience.Helpers

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.viewpager.widget.ViewPager

class NonSwipeableViewPager @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : ViewPager(context, attrs) {

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        // Prevent swipe gestures
        return false
    }

    override fun onTouchEvent(ev: MotionEvent?): Boolean {
        // Prevent touch events
        return false
    }
}
