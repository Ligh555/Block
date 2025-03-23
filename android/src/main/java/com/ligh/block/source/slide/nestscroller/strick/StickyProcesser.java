package com.ligh.block.source.slide.nestscroller.strick;

import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * 吸顶/吸底效果处理核心类
 * 功能：
 * 1. 管理吸顶视图的定位与层级
 * 2. 处理吸底视图的固定定位
 * 3. 支持虚拟高度偏移量调整
 * 4. 提供吸顶状态变化回调
 */
public class StickyProcesser {

    // region 常量定义

    // 默认Z轴高度（用于防止按钮等视图压盖吸顶视图）
    private static final int DEFAULT_STICKY_TRANSLATE_Z = 6;

    // endregion

    // region 成员变量

    // 存储所有需要吸顶的子视图（按布局顺序存储）
    protected List<View> mStickyViews = new ArrayList<>();

    // 当前吸底视图（多个吸底视图时仅取第一个）
    protected View mStickBottomView;

    // 当前处于吸顶状态的视图
    private View mCurrentStickyView;

    // 是否已完成吸顶视图收集的标记
    private boolean hasCollectedStickView;

    // 吸顶视图的Z轴高度（控制视图层级）
    private int mStickViewTranslateZ = DEFAULT_STICKY_TRANSLATE_Z;

    // 父容器视图（HXVerticalScrollView实例）
    private ViewGroup mParentView;

    // 吸顶状态变化监听器
    protected OnStickyStatusChangeListener onStickyStatusChangeListener;

    // 虚拟吸顶高度偏移量（用于特殊位置调整）
    private int mVirtualStickHeight = 0;

    // endregion

    // region 构造方法

    /**
     * 初始化处理器
     * @param mParentView 父容器视图（必须是HXVerticalScrollView或其子类）
     */
    public StickyProcesser(ViewGroup mParentView) {
        this.mParentView = mParentView;
    }

    // endregion

    // region 核心方法

    /**
     * 更新吸顶视图状态（需在滚动时调用）
     * 算法逻辑：
     * 1. 根据当前滚动位置确定需要吸顶的视图
     * 2. 处理多个吸顶视图的过渡效果
     * 3. 更新视图的定位和层级
     */
    public void sticky() {
        // 没有吸顶视图时直接返回
        int size = mStickyViews.size();
        if (size == 0) {
            mCurrentStickyView = null;
            return;
        }

        // 计算实际滚动偏移量（包含父容器的paddingTop）
        int offset = mParentView.getScrollY() + mParentView.getPaddingTop();

        View stickyView = null;    // 当前需要吸顶的视图
        View nextStickView = null; // 下一个可能进入吸顶的视图

        // 从后往前遍历吸顶视图列表（确保优先匹配后面的视图）
        for (int i = size - 1; i >= 0; i--) {
            View view = mStickyViews.get(i);
            // 判断视图顶部位置是否进入吸顶区域（考虑虚拟高度偏移）
            if (view.getTop() - mVirtualStickHeight <= offset) {
                stickyView = view;
                // 获取下一个视图用于过渡计算
                if (i < size - 1) {
                    nextStickView = mStickyViews.get(i + 1);
                }
                break;
            }
        }

        // 没有找到符合条件的吸顶视图
        if (stickyView == null) {
            notifyStickChanged(null, mCurrentStickyView); // 触发状态变更回调
            mCurrentStickyView = null;
            // 重置第一个吸顶视图的位置
            if (!mStickyViews.isEmpty()) {
                mStickyViews.get(0).setTranslationY(0);
            }
            return;
        }

        // 处理吸顶过渡效果
        if (nextStickView != null) {
            // 重置视图位置
            stickyView.setTranslationY(0);
            nextStickView.setTranslationY(0);
            nextStickView.setTranslationZ(0);

            // 计算过渡偏移量（限制不超过0）
            offset = Math.min(0, nextStickView.getTop() - stickyView.getHeight() - offset);
        } else {
            offset = 0; // 最后一个吸顶视图不需要过渡
        }

        // 触发状态变更回调
        notifyStickChanged(stickyView, mCurrentStickyView);
        mCurrentStickyView = stickyView;

        // 应用吸顶定位
        stickyChild(stickyView, offset);
    }

    /**
     * 更新吸底视图位置（需在布局变化时调用）
     * 定位逻辑：
     * 1. 当视图底部超出可视区域时固定到屏幕底部
     * 2. 正常情况保持原始位置
     */
    public void stickyBottom() {
        if (mStickBottomView == null) {
            return;
        }

        // 计算可视区域底部位置
        int offset = mParentView.getScrollY() + mParentView.getPaddingTop();
        int visibleBottom = offset + mParentView.getMeasuredHeight() - mParentView.getPaddingBottom();

        int y; // 目标Y坐标
        if (visibleBottom < mStickBottomView.getBottom()) {
            // 需要吸底的情况：视图底部超出可视区域
            y = visibleBottom - mStickBottomView.getMeasuredHeight();
            mStickBottomView.setTranslationZ(mStickViewTranslateZ); // 提升层级
        } else {
            // 正常情况
            y = mStickBottomView.getTop();
            mStickBottomView.setTranslationZ(0); // 恢复层级
        }

        // 应用位置变化
        mStickBottomView.setY(y);
    }

    // endregion

    // region 辅助方法

    /**
     * 获取当前吸顶视图的高度
     * @return 当前吸顶视图的测量高度（不可见时返回0）
     */
    public int getCurrentStickyViewHeight() {
        if (mCurrentStickyView == null || mCurrentStickyView.getVisibility() == View.GONE) {
            return 0;
        }
        return mCurrentStickyView.getMeasuredHeight();
    }

    /**
     * 获取指定视图对应的吸顶高度
     * @param topView 当前顶部视图
     * @return 最近的上方吸顶视图高度
     */
    public int getStickyViewHeight(View topView) {
        if (topView == null || topView.getVisibility() == View.GONE) {
            return 0;
        }

        int size = mStickyViews.size();
        if (size == 0) {
            return 0;
        }

        // 根据顶部视图的位置查找最近的吸顶视图
        int offset = topView.getTop();
        for (int i = size - 1; i >= 0; i--) {
            View view = mStickyViews.get(i);
            if (view.getTop() <= offset) {
                return (view.getVisibility() == View.GONE) ? 0 : view.getMeasuredHeight();
            }
        }

        return getCurrentStickyViewHeight();
    }

    // endregion

    // region 私有方法

    /**
     * 应用吸顶效果到指定视图
     * @param childView 目标视图
     * @param offset 垂直偏移量
     */
    private void stickyChild(View childView, int offset) {
        // 计算最终Y坐标（考虑父容器滚动偏移和虚拟高度）
        float finalY = mParentView.getScrollY() + mParentView.getPaddingTop() + offset + mVirtualStickHeight;
        childView.setY(finalY);

        // 提升Z轴防止被其他视图覆盖
        childView.setTranslationZ(mStickViewTranslateZ);
    }

    /**
     * 触发吸顶状态变更回调
     */
    private void notifyStickChanged(View currentStickyView, View lastStickView) {
        if (onStickyStatusChangeListener != null && currentStickyView != lastStickView) {
            onStickyStatusChangeListener.onStickyChanged(currentStickyView, lastStickView);
        }
    }

    /**
     * 收集所有吸顶/吸底视图（仅执行一次）
     */
    public void setStickyChildren() {
        if (hasCollectedStickView) {
            return;
        }
        hasCollectedStickView = true;

        // 清空旧数据
        mStickyViews.clear();
        mStickBottomView = null;

        // 遍历所有子视图
        int childCount = mParentView.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View childView = mParentView.getChildAt(i);

            // 收集吸顶视图
            if (isStickyView(childView)) {
                mStickyViews.add(childView);
            }

            // 记录第一个吸底视图
            if (mStickBottomView == null && isStickyBottomView(childView)) {
                mStickBottomView = childView;
            }
        }
    }

    // endregion

    // region 属性判断

    /**
     * 判断视图是否需要吸顶
     */
    public boolean isStickyView(View childView) {
        if (childView == null) return false;

        if ( childView instanceof IStick){
            return ((IStick) childView).isStickTop();
        }
        return false;
    }

    /**
     * 判断视图是否需要吸底
     */
    private boolean isStickyBottomView(View childView) {
        if (childView == null) return false;

        if ( childView instanceof IStick){
            return ((IStick) childView).isStickBottom();
        }
        return false;
    }

    // endregion

    // region Getter/Setter

    public View getCurrentStickyView() {
        return mCurrentStickyView;
    }

    public void setStickViewTranslateZ(int stickViewTranslateZ) {
        this.mStickViewTranslateZ = stickViewTranslateZ;
    }

    public void setOnStickyStatusChangeListener(OnStickyStatusChangeListener onStickyStatusChangeListener) {
        this.onStickyStatusChangeListener = onStickyStatusChangeListener;
    }
    public void setVirtualStickHeight(int virtualStickHeight) {
        this.mVirtualStickHeight = virtualStickHeight;
    }
}
