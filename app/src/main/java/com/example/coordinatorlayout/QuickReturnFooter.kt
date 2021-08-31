package com.example.coordinatorlayout

import android.animation.Animator
import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewPropertyAnimator
import android.view.animation.Interpolator
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.ViewCompat
import androidx.interpolator.view.animation.FastOutSlowInInterpolator

class QuickReturnFooter(context: Context?, attrs: AttributeSet?) :
    CoordinatorLayout.Behavior<View?>(context, attrs) {
    private var dyDirectionSum = 0
    private var isShowing = false
    private var isHiding = false
    override fun onStartNestedScroll(
        coordinatorLayout: CoordinatorLayout,
        child: View,
        directTargetChild: View,
        target: View,
        axes: Int,
        type: Int
    ): Boolean {
        return axes == ViewCompat.SCROLL_AXIS_VERTICAL
    }

    override fun onNestedPreScroll(
        coordinatorLayout: CoordinatorLayout,
        child: View,
        target: View,
        dx: Int,
        dy: Int,
        consumed: IntArray,
        type: Int
    ) {
        // 스크롤이 반대방향으로 전환
        if (dy > 0 && dyDirectionSum < 0 || dy < 0 && dyDirectionSum > 0) {
            child.animate().cancel()
            dyDirectionSum = 0
        }
        dyDirectionSum += dy
        if (dyDirectionSum > child.getHeight()) {
            hideView(child)
        } else if (dyDirectionSum < -child.getHeight()) {
            showView(child)
        }
    }

    private fun hideView(view: View) {
        if (isHiding || view.visibility !== View.VISIBLE) {
            return
        }
        val animator: ViewPropertyAnimator = view.animate()
            .translationY(view.height.toFloat())
            .setInterpolator(INTERPOLATOR)
            .setDuration(ANIMATION_DURATION)
        animator.setListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animator: Animator) {
                isHiding = true
            }

            override fun onAnimationEnd(animator: Animator) {
                isHiding = false
                view.setVisibility(View.INVISIBLE)
            }

            override fun onAnimationCancel(animator: Animator) {
                // 취소되면 다시 보여줌
                isHiding = false
                showView(view)
            }

            override fun onAnimationRepeat(animator: Animator) {
                // no-op
            }
        })
        animator.start()
    }

    private fun showView(view: View) {
        if (isShowing || view.getVisibility() === View.VISIBLE) {
            return
        }
        val animator: ViewPropertyAnimator = view.animate()
            .translationY(0f)
            .setInterpolator(INTERPOLATOR)
            .setDuration(ANIMATION_DURATION)
        animator.setListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animator: Animator) {
                isShowing = true
                view.setVisibility(View.VISIBLE)
            }

            override fun onAnimationEnd(animator: Animator) {
                isShowing = false
            }

            override fun onAnimationCancel(animator: Animator) {
                // 취소되면 다시 숨김
                isShowing = false
                hideView(view)
            }

            override fun onAnimationRepeat(animator: Animator) {
                // no-op
            }
        })
        animator.start()
    }

    companion object {
        private val INTERPOLATOR: Interpolator = FastOutSlowInInterpolator()
        private const val ANIMATION_DURATION: Long = 200
    }
}