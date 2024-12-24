package com.ligh.block.source.stocklist;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import androidx.core.widget.NestedScrollView;

public class HeightMatchNestedScrollView extends ViewGroup {

    public HeightMatchNestedScrollView(Context context) {
        super(context);
    }

    public HeightMatchNestedScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public HeightMatchNestedScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int parentNestedScrollViewHeight = findParentNestedScrollViewHeight();
        if (parentNestedScrollViewHeight > 0) {
            // Use the height of the found NestedScrollView
            heightMeasureSpec = MeasureSpec.makeMeasureSpec(parentNestedScrollViewHeight, MeasureSpec.EXACTLY);
        }

        // Measure children with updated height
        measureChildren(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec), MeasureSpec.getSize(heightMeasureSpec));
    }

    private int findParentNestedScrollViewHeight() {
        View parent = (View) getParent();
        while (parent != null) {
            if (parent instanceof NestedScrollView) {
                return parent.getMeasuredHeight();
            }
            parent = (View) parent.getParent();
        }
        return 0; // Default to 0 if no NestedScrollView is found
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        // Layout children normally
        int childTop = 0;
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            child.layout(l, childTop, r, childTop + child.getMeasuredHeight());
            childTop += child.getMeasuredHeight();
        }
    }
}
