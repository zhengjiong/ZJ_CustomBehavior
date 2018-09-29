package com.zj.example.custombehavior.demo17

import android.animation.Animator
import android.animation.ValueAnimator
import android.content.Context
import android.support.design.widget.AppBarLayout
import android.support.design.widget.CoordinatorLayout
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import com.zj.example.custombehavior.R
import kotlin.properties.Delegates

/**
 *
 * Company: 上加下信息技术成都有限公司
 * CreateTime:2018/9/27  20:16
 * @author 郑炯
 * @version 1.0
 */

class Behavior17 : AppBarLayout.Behavior {
    var mTotalDy = 0
    var mScale = 1F
    var isAnimate = false
    var imgHeight: Int by Delegates.notNull()
    var appbarHeight: Int by Delegates.notNull()

    lateinit var img: ImageView
    lateinit var imgBottom: View
    lateinit var appbar: AppBarLayout

    constructor() {}

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    override fun onLayoutChild(parent: CoordinatorLayout, abl: AppBarLayout, layoutDirection: Int): Boolean {
        img = parent.findViewById(R.id.img)
        appbar = abl
        imgBottom = parent.findViewById(R.id.img_bottom)
        imgHeight = img.measuredHeight
        appbarHeight = abl.measuredHeight
        return super.onLayoutChild(parent, abl, layoutDirection)
    }

    //开始嵌套滚动, 这里返回true，才会接受到后续滑动事件。
    override fun onStartNestedScroll(parent: CoordinatorLayout, child: AppBarLayout, directTargetChild: View, target: View, nestedScrollAxes: Int, type: Int): Boolean {
        /*if (target.id == R.id.nestedScrollView) {
            return true
        }*/
        isAnimate = true
        val onStartNestedScroll = super.onStartNestedScroll(parent, child, directTargetChild, target, nestedScrollAxes, type)
        println("onStartNestedScroll = $onStartNestedScroll")
        return onStartNestedScroll
    }

    /**
     * 即将开始嵌套滚动，每次滑动前，Child 先询问 Parent 是否需要滑动，即dispatchNestedPreScroll()，
     * 这就回调到 Parent 的onNestedPreScroll()，Parent 可以在这个回调中“劫持”掉 Child 的滑动，
     * 也就是先于 Child 滑动。
     */
    override fun onNestedPreScroll(coordinatorLayout: CoordinatorLayout, child: AppBarLayout, target: View, dx: Int, dy: Int, consumed: IntArray, type: Int) {
        //println("onNestedPreScroll  child=$child  target=$target  dy=$dy")
        if (anim != null && anim!!.isRunning) {
            //如果正在还原的过程中, 不scale图片, 不然画面会突然抖动
            return
        }


        /**
         * dy < 0,代表下滑, child就是appbar, child.bottom >= child.height用于判断appbar是否在顶部没有被滑动,如果不加会在滑动的过程中缩放img!
         */
        if (dy < 0 && child.bottom >= child.height) {
            scaleImg(child, target, dy)
            //consumed[1] = dy / 5//不除以5的话速度会很快
            return
        } else if (dy > 0 && mScale != 1F) {
            scaleImg(child, target, dy)
            //这里必须用把滑动距离都消耗完,不然最下面的ScrollView会先滑动!
            consumed[1] = dy
            return
        }

        super.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed, type)
    }

    override fun onNestedFling(coordinatorLayout: CoordinatorLayout, child: AppBarLayout, target: View, velocityX: Float, velocityY: Float, consumed: Boolean): Boolean {
        println("onNestedFling target=$target  velocityY=$velocityY  consumed=$consumed")
        if (velocityY > 100) {
            //isAnimate = false
        }
        return super.onNestedFling(coordinatorLayout, child, target, velocityX, velocityY, consumed)
    }

    override fun onStopNestedScroll(coordinatorLayout: CoordinatorLayout, abl: AppBarLayout, target: View, type: Int) {
        println("onStopNestedScroll")
        recoveryImg()
        super.onStopNestedScroll(coordinatorLayout, abl, target, type)
    }

    fun scaleImg(appbar: AppBarLayout, target: View, dy: Int) {
        //下滑的时候,dy是负值
        mTotalDy += -dy
        println("scale mTotalDy=$mTotalDy")
        mScale = Math.max(mTotalDy / imgHeight.toFloat() + 1, 1F)
        img.scaleX = mScale
        img.scaleY = mScale

        val mLastAppBarBottom = appbarHeight + imgHeight / 2 * (mScale - 1)

        //appbar需要设置clipChildren才行
        appbar.bottom = mLastAppBarBottom.toInt()

        target.scrollY = 0

        imgBottom.top = (mLastAppBarBottom - imgBottom.height).toInt()
        imgBottom.bottom = mLastAppBarBottom.toInt()
    }

    var anim: ValueAnimator? = null
    fun recoveryImg() {
        //必须判断mScale == 1F,不然会每次把mTotalDy都设置为0, 因为每次都会重新new一个新的ValueAnimator, 所以isRecovering为false的时候, 动画可能还没有真正结束
        if (mScale == 1F) {
            return
        }

        if (anim != null && anim!!.isRunning) {
            anim!!.cancel()
        }
        anim = ValueAnimator.ofFloat(mScale, 1F).setDuration(200)
        mTotalDy = 0
        mScale = 1F
        /*if (!isAnimate) {
            img.scaleX = 1F
            img.scaleY = 1F
            isRecovering = false
            return
        }*/
        anim!!.addUpdateListener { animation ->
            val value = animation.animatedValue as Float
            img.scaleX = value
            img.scaleY = value

            val mLastAppBarBottom = appbarHeight + imgHeight / 2 * (value - 1)
            appbar.bottom = mLastAppBarBottom.toInt()
            imgBottom.top = (mLastAppBarBottom - imgBottom.height).toInt()
            imgBottom.bottom = mLastAppBarBottom.toInt()
        }
        anim!!.addListener(object : Animator.AnimatorListener {
            override fun onAnimationRepeat(animation: Animator?) {

            }

            override fun onAnimationEnd(animation: Animator?) {
                mTotalDy = 0
            }

            override fun onAnimationCancel(animation: Animator?) {

            }

            override fun onAnimationStart(animation: Animator?) {

            }
        })
        anim!!.start()
    }
}