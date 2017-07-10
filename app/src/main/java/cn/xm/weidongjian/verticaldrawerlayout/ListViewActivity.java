package cn.xm.weidongjian.verticaldrawerlayout;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ArrayAdapter;
import android.widget.ListView;


public class ListViewActivity extends AppCompatActivity implements View.OnClickListener, DragListener {
    String[] listdatas = {"item 1", "item 2 ", "list", "android", "item 3", "foobar", "bar", "item 1", "item 2 ", "list", "android", "item 3", "foobar", "bar", "item 1", "item 2 ", "list", "android", "item 3", "foobar", "bar",};
    String[] listItems = {"item 1", "item 2 ", "list", "android", "item 3", "foobar", "bar",};
    private DragLayout mDragLayout;
    private ListView mListView;
    private int mContentHeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listview);
        mListView = (ListView) findViewById(R.id.listview);
        mListView.setAdapter(new ArrayAdapter(this, android.R.layout.simple_list_item_1, listdatas));
        ListView listView = (ListView) findViewById(R.id.contentView);
        listView.setAdapter(new ArrayAdapter(this, android.R.layout.simple_list_item_1, listItems));

        mDragLayout = (DragLayout) findViewById(R.id.drawLayout);
        findViewById(R.id.open).setOnClickListener(this);
        findViewById(R.id.close).setOnClickListener(this);
        mDragLayout.setDragListener(this);
        mListView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                mListView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                mContentHeight = mListView.getHeight();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.open:
                mDragLayout.smoothToPosition(300);
                break;
            case R.id.close:
                mDragLayout.smoothToPosition(600);
                break;
        }
    }

    @Override
    public void setSize(int height) {
        if (mContentHeight > 0) {
            ViewGroup.LayoutParams params = mListView.getLayoutParams();
            params.height = mContentHeight - height;
            mListView.setLayoutParams(params);
        }
    }
}
