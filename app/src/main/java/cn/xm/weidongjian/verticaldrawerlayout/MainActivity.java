package cn.xm.weidongjian.verticaldrawerlayout;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ScrollView;


public class MainActivity extends AppCompatActivity implements View.OnClickListener, DragListener {
    String[] listItems = {"item 1", "item 2 ", "list", "android", "item 3", "foobar", "bar",};
    private DragLayout mDragLayout;
    private ScrollView mScrollView;
    private int mContentHeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mScrollView = (ScrollView) findViewById(R.id.scrollView);
        ListView listView = (ListView) findViewById(R.id.contentView);
        listView.setAdapter(new ArrayAdapter(this, android.R.layout.simple_list_item_1, listItems));

        mDragLayout = (DragLayout) findViewById(R.id.drawLayout);
        findViewById(R.id.open).setOnClickListener(this);
        findViewById(R.id.close).setOnClickListener(this);
        mDragLayout.setDragListener(this);
        mScrollView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                mScrollView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                mContentHeight = mScrollView.getHeight();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.open:
//                mDragLayout.smoothToPosition(300);
                startActivity(new Intent(this, ExerciseActivity.class));
                break;
            case R.id.close:
                mDragLayout.smoothToPosition(600);
                break;
        }
    }

    @Override
    public void setSize(int height) {
        if (mContentHeight > 0) {
            ViewGroup.LayoutParams params = mScrollView.getLayoutParams();
            params.height = mContentHeight - height;
            mScrollView.setLayoutParams(params);
        }
    }
}
