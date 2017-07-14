package cn.xm.weidongjian.verticaldrawerlayout;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

/**
 * Created by Weidongjian on 2015/7/2.
 */
public class DragLayout extends LinearLayout {
    private ViewDragHelper dragHelper;
    private View mDragView, contentView;
    private int dragRange;//可拖拽的距离
    private int dragY;//拖拽的距离
    private boolean isDrag = false;
    private DragListener dragListener;

    public DragLayout(Context context) {
        super(context);
        init();
    }

    public DragLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public void setDragListener(DragListener dragListener) {
        this.dragListener = dragListener;
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public DragLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    public DragLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        dragHelper = ViewDragHelper.create(this, callback);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mDragView = findViewById(R.id.dragView);
        contentView = findViewById(R.id.contentView);
        mDragView.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                    case MotionEvent.ACTION_MOVE:
                        isDrag = true;
                        break;
                    case MotionEvent.ACTION_UP:
                        isDrag = false;
                        break;
                }
                return false;
            }
        });
    }

    private ViewDragHelper.Callback callback = new ViewDragHelper.Callback() {
        @Override
        public boolean tryCaptureView(View child, int pointerId) {
            return child == mDragView;
        }

        @Override
        public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
            contentView.layout(0, top + mDragView.getHeight(), getWidth(), top + mDragView.getHeight() + dragRange);
        }

        @Override
        public int clampViewPositionVertical(View child, int top, int dy) {
            int topBound = getHeight() - dragRange - mDragView.getHeight();
            int bottomBound = getHeight() - mDragView.getHeight();
            final int newHeight = Math.min(Math.max(topBound, top), bottomBound);
            dragY = dragRange - newHeight;
            if (dragListener != null) {
                dragListener.setSize(dragY);
            }
            return newHeight;
        }

        @Override
        public int getViewVerticalDragRange(View child) {
            return dragRange;
        }

        @Override
        public void onViewReleased(View releasedChild, float xvel, float yvel) {
            super.onViewReleased(releasedChild, xvel, yvel);
            //惯性滑动
//            if (yvel > 0) {
//                smoothToBottom();
//            } else if (yvel < 0) {
//                smoothToTop();
//            }
        }
    };

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        dragRange = contentView.getMeasuredHeight();
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        mDragView.layout(0, getHeight() - mDragView.getHeight() - dragY, getWidth(), getHeight() - dragY);
        contentView.layout(0, getHeight() - dragY, getWidth(), getHeight() + dragRange);
    }

    @Override
    public boolean onInterceptHoverEvent(MotionEvent event) {
        return dragHelper.shouldInterceptTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        dragHelper.processTouchEvent(event);
        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_UP:
                isDrag = false;
                break;
        }
        return isDrag;
    }

    public void smoothToPosition(int position) {
        if (dragHelper.smoothSlideViewTo(mDragView, getPaddingLeft(), getHeight() - position - mDragView.getHeight())) {
            ViewCompat.postInvalidateOnAnimation(this);
            if (dragListener != null) {
                dragListener.setSize(position);
            }
        }
    }

    public void smoothToTop() {
        if (dragHelper.smoothSlideViewTo(mDragView, getPaddingLeft(), getHeight() - dragRange - mDragView.getHeight())) {
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }

    public void smoothToBottom() {
        if (dragHelper.smoothSlideViewTo(mDragView, getPaddingLeft(), getHeight() - mDragView.getHeight())) {
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }

    @Override
    public void computeScroll() {
        if (dragHelper.continueSettling(true)) {
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }

}

