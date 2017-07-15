package com.gx.android_leftandrightslide;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import com.gx.android_leftandrightslide.widget.CardConfig;
import com.gx.android_leftandrightslide.widget.CardItemTouchHelperCallback;
import com.gx.android_leftandrightslide.widget.CardLayoutManager;
import com.gx.android_leftandrightslide.widget.OnSwipeListener;

public class MainActivity extends AppCompatActivity {

    private List<Integer> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
    }

    private void initView() {
        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(new MyAdapter());
        CardItemTouchHelperCallback cardCallback = new CardItemTouchHelperCallback(recyclerView.getAdapter(), list);
        cardCallback.setOnSwipedListener(new OnSwipeListener<Integer>() {
            @Override
            public void onSwiping(RecyclerView.ViewHolder viewHolder, float ratio, int direction) {
                viewHolder.itemView.setAlpha(1 - Math.abs(ratio) * 0.2f);
            }
            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, Integer o, int direction) {
                viewHolder.itemView.setAlpha(1f);
                if(direction == CardConfig.SWIPED_LEFT){
                    // 向左滑动
                }else{
                    // 向右滑动
                }
            }
            @Override
            public void onSwipedClear() {
                // 最后一张也滑动结束，表示内容已空
                recyclerView.post(new Runnable() {
                    @Override
                    public void run() {
                        initData();
                        recyclerView.getAdapter().notifyDataSetChanged();
                    }
                });
            }
        });
        final ItemTouchHelper touchHelper = new ItemTouchHelper(cardCallback);
        final CardLayoutManager cardLayoutManager = new CardLayoutManager(recyclerView, touchHelper);
        recyclerView.setLayoutManager(cardLayoutManager);
        touchHelper.attachToRecyclerView(recyclerView);
    }

    private void initData() {
        list.add(R.drawable.image1);
        list.add(R.drawable.image2);
        list.add(R.drawable.image3);
        list.add(R.drawable.image4);
        list.add(R.drawable.image5);
        list.add(R.drawable.image6);
        list.add(R.drawable.image7);
        list.add(R.drawable.image8);
    }

    private class MyAdapter extends RecyclerView.Adapter {
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
            return new MyViewHolder(view);
        }
        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            ImageView avatarImageView = ((MyViewHolder) holder).avatarImageView;
            avatarImageView.setImageResource(list.get(position));
        }
        @Override
        public int getItemCount() {
            return list.size();
        }
        class MyViewHolder extends RecyclerView.ViewHolder {
            ImageView avatarImageView;
            MyViewHolder(View itemView) {
                super(itemView);
                avatarImageView = (ImageView) itemView.findViewById(R.id.iv_avatar);
            }
        }
    }
}
