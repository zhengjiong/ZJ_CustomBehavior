package com.zj.example.custombehavior.behavior;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

import com.zj.example.custombehavior.R;

/**
 * Created by zhengjiong
 * date: 2017/6/11 13:31
 */

public class Demo14_Behavior extends CoordinatorLayout.Behavior {
    private Context mContex;

    public Demo14_Behavior(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContex = context;
    }

    /**
     * 可以重写这个方法对子View 进行重新布局
     * <p>
     * 首先是整个布局，Header 固定在顶部，列表在Header 的下方，CoordinatorLayout
     * 是一个FrameLayout,不能提供这样的布局，我们需要重写onLayoutChild 来让列表位于Header下面
     *
     * @param parent
     * @param child           代表behavior绑定的view: Recyclerview
     * @param layoutDirection
     * @return
     */
    @Override
    public boolean onLayoutChild(CoordinatorLayout parent, View child, int layoutDirection) {
        if (child instanceof RecyclerView) {
            System.out.println("onLayoutChild RecyclerView");
            //这里如果返回true就代表自己来处理onLayout, 所以必须手动layout
            child.layout(0, 0, parent.getWidth(), parent.getHeight());
            child.setTranslationY(getHeaderHeight());

            return true;
        }
        return super.onLayoutChild(parent, child, layoutDirection);
    }


    /**
     * 一定要按照自己的需求返回true，该方法决定了当前控件是否能接收到其内部View(非并非是直接子View)滑动时的参数；
     * 假设你只涉及到纵向滑动，这里可以根据nestedScrollAxes这个参数，进行纵向判断。
     * <p>
     * 当coordinatorLayout 的子View试图开始嵌套滑动的时候被调用。当返回值为true的时候表明
     * coordinatorLayout 充当nested scroll parent 处理这次滑动，需要注意的是只有当返回值为true
     * 的时候，Behavior 才能收到后面的一些nested scroll 事件回调（如：onNestedPreScroll、onNestedScroll等）
     * 这个方法有个重要的参数nestedScrollAxes，表明处理的滑动的方向。
     *
     * @param coordinatorLayout
     * @param child
     * @param directTargetChild
     * @param target
     * @param nestedScrollAxes
     * @return
     */
    @Override
    public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, View child, View directTargetChild, View target, int nestedScrollAxes) {
        //这里的返回值表明这次滑动我们要不要关心，我们要关心什么样的滑动？当然是y轴方向上的
        boolean result = (nestedScrollAxes & ViewCompat.SCROLL_AXIS_VERTICAL) == ViewCompat.SCROLL_AXIS_VERTICAL;
        System.out.println("onStartNestedScroll ---> " + result);
        return result;
        //return (nestedScrollAxes & ViewCompat.SCROLL_AXIS_VERTICAL) != 0;
    }

    /**
     * 此方法的作用自己的理解是, 在child滑动前拦截child的滑动,然后自己处理, 如果处理不完再交给onNestedScroll继续处理,
     * 如果处理完onNestedScroll将不会再继续处理
     * <p>
     * 该方法的会传入内部View移动的dx,dy，如果你需要消耗一定的dx,dy，就通过最后一个参数consumed进行指定，
     * 例如我要消耗一半的dy，就可以写consumed[1]=dy/2
     * <p>
     * 每次滑动前，Child 先询问 Parent 是否需要滑动，即dispatchNestedPreScroll()，
     * 这就会回调到 Parent 的onNestedPreScroll()，Parent 可以在这个回调中“劫持”掉 Child 的滑动，也就是先于 Child 滑动。
     * <p>
     * 嵌套滚动发生之前被调用
     * 在nested scroll child 消费掉自己的滚动距离之前，嵌套滚动每次被nested scroll child
     * 更新都会调用onNestedPreScroll。注意有个重要的参数consumed，可以修改这个数组表示你消费
     * 了多少距离。假设用户滑动了100px,child 只需要移动90px 的位移，你需要把 consumed［1］的值改成90，
     * 这样coordinatorLayout就能知道只处理剩下的10px的滚动。
     * <p>
     * onNestedPreScroll方法里面收到子view的滑动信息，然后做出相应的处理把处理完后的结果通过consumed传给子view。
     * 同样的道理，如果父view需要在子view滑动后处理相关事件的话可以在子view的事件处理完成之后调用dispatchNestedScroll
     * 然后父view会在onNestedScroll收到回调。最后，滑动结束，调用onStopNestedScroll()表示本次处理结束。
     *
     * @param coordinatorLayout
     * @param child
     * @param target
     * @param dx
     * @param dy
     * @param consumed
     */
    @Override
    public void onNestedPreScroll(CoordinatorLayout coordinatorLayout, View child, View target, int dx, int dy, int[] consumed) {
        super.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed);
        System.out.println("onNestedPreScroll dy=" + dy + " ,child.getTranslationY()=" + child.getTranslationY() + " ,consumed[1]=" + consumed[1]);


        //重要! 注意: 这里可以打开if测试, 这样就再也不会调用onNestedScroll方法, Recyclerview也不可以滑动
        /*if (true) {
            consumed[1] = dy;//把滑动的距离全部消耗掉,这样就不会回调onNestedScroll方法
            return;
        }*/

        /**
         * 只有在上滑的时候, 并且recyclerview还没有到顶部的时候,
         * 才拦截滚动事件并且让coordinatorLayout消耗滚动事件(consumed[1] = dy),
         * child.setTranslationY(transY)并不是消耗滚动时间,consumed[1]才是消耗
         * 滚动事件, setTranslationY是让recyclerview往上移动,但是真正消耗滚动
         * 事件是consumed[1] = dy, 可以打开上面的if(true)测试效果!
         */
        if (dy > 0) {
            //上滑
            float transY = child.getTranslationY() - dy;
            if (child.getTranslationY() > 0 && child.getTranslationY() >= dy) {
                child.setTranslationY(transY);
                consumed[1] = dy;//把滑动的距离全部消耗掉,这样就不会回调onNestedScroll方法
            } else if (child.getTranslationY() > 0 && child.getTranslationY() < dy) {
                child.setTranslationY(0);
                consumed[1] = (int) (dy - child.getTranslationY());
            }
        } else if (dy < 0) {
            /**
             * 下滑在onNestedScroll中处理, 这里不处理,因为这里判断recyclerview是否还可以滑动太麻烦(后面的demo将试验在这里处理)
             * 在onNestedScroll直接可以用dxUnconsumed来判断是否可以滑动
             */

        }

    }

    /**
     * Header 有一个alpha的变化，
     * 本例子没有实现，其实也很简单，只要重写onDependentViewChanged方法，
     * 在里面根据滑动距离算出alpha 变化的值就可以了
     *
     * @param coordinatorLayout
     * @param child
     * @param target
     * @param dxConsumed
     * @param dyConsumed
     * @param dxUnconsumed
     * @param dyUnconsumed      代表划不动的时候, 子view未消耗的y轴距离
     */
    @Override
    public void onNestedScroll(CoordinatorLayout coordinatorLayout, View child, View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
        super.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed);
        System.out.println("onNestedScroll dyConsumed=" + dyConsumed + " ,dyUnconsumed=" + dyUnconsumed);

        /**
         * dyUnconsumed代表划不动的时候, 子view未消耗的y轴距离
         * 当上滑的时候, 滑到底部后继续滑动, dyUnconsumed为一个大于0的整数
         * 当下滑的时候,滑动到顶部后继续滑动, dyUnconsumed为一个小于0的整数
         */
        if (dyUnconsumed >= 0) {
            //滑到底部的继续滑动, 不处理!
            return;
        }

        if (child.getTranslationY() + Math.abs(dyUnconsumed) >= getHeaderHeight()) {
            //如果超过最大值,则直接设置为最初的TranslationY距离
            child.setTranslationY(getHeaderHeight());
        } else {
            //设置translationY为滑动的距离
            child.setTranslationY(child.getTranslationY() + Math.abs(dyUnconsumed));
        }
    }

    @Override
    public boolean onNestedFling(CoordinatorLayout coordinatorLayout, View child, View target, float velocityX, float velocityY, boolean consumed) {
        System.out.println("onNestedFling -> velocityY=" + velocityY + " ,consumed=" + consumed);
        //return super.onNestedFling(coordinatorLayout, child, target, velocityX, velocityY, consumed);
        return true;
    }

    /**
     * 用户松开手指并且会发生惯性动作之前调用，参数提供了速度信息，可以根据这些速度信息
     * 决定最终状态，比如滚动Header，是让Header处于展开状态还是折叠状态。返回true 表
     * 示消费了fling, onNestedFling将不会被调用
     *
     * @param coordinatorLayout
     * @param child
     * @param target
     * @param velocityX x 方向的速度
     * @param velocityY y 方向的速度
     * @return 返回true 表示消费了fling, onNestedFling将不会被调用
     */
    @Override
    public boolean onNestedPreFling(CoordinatorLayout coordinatorLayout, View child, View target, float velocityX, float velocityY) {
        System.out.println("onNestedPreFling -> velocityY=" + velocityY);
        //return super.onNestedPreFling(coordinatorLayout, child, target, velocityX, velocityY);

        //返回true 表示消费了fling, onNestedFling将不会被调用
        return true;
    }

    public float getHeaderHeight() {
        return mContex.getResources().getDimensionPixelSize(R.dimen.banner_height);
    }
}
