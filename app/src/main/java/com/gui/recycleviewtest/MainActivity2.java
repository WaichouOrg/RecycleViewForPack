package com.gui.recycleviewtest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gui.adapter.LGRecycleViewAdapter;
import com.gui.adapter.LGViewHolder;
import com.gui.bean.Bean1;
import com.gui.bean.Bean2;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 *
 */
public class MainActivity2 extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    @Bind(R.id.id_recycle_view)
    RecyclerView recyclerView;

    private RecyclerView.LayoutManager layoutManager;
    private MainAdapter mainAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        List<Object> datas = new ArrayList<>();
        datas.add(new Bean1("bean 1 ----0"));
        datas.add(new Bean1("bean 1 ----1"));

        datas.add(new Bean2("bean 2 ----0"));
        datas.add(new Bean2("bean 2 ----1"));

        datas.add(new Bean1("bean 1 ----2"));

        datas.add(new Bean2("bean 2 ----2"));

        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        mainAdapter = new MainAdapter(datas);
        mainAdapter.setOnItemClickListener(R.id.root, new LGRecycleViewAdapter.ItemClickListener() {
            @Override
            public void onItemClicked(View view, int position) {
                Log.d(TAG,"root clicked..." + position);
            }
        });
        mainAdapter.setOnItemClickListener(R.id.icon, new LGRecycleViewAdapter.ItemClickListener() {
            @Override
            public void onItemClicked(View view, int position) {
                Log.d(TAG,"icon clicked..." + position);
            }
        });

        recyclerView.setAdapter(mainAdapter);
        recyclerView.setLayoutManager(layoutManager);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mainAdapter.destroyAdapter();
    }

    private static class MainAdapter extends LGRecycleViewAdapter<Object> {

        public MainAdapter(List<Object> dataList) {
            super(dataList);
        }

        //根据标签确定要显示的布局layout
        @Override
        public int getLayoutId(int viewType) {//当前position位置对应的类型int值来对应加载对应不同的布局layout
            if(viewType == 1)
                return R.layout.item_view_main1;
            return R.layout.item_view_main2;//默认加载布局layout
        }

        //支持不同viewType视图 ,形象说法贴标签
        @Override
        public int getItemViewType(int position) {
            Object model = getItem(position);//通过item来确定当前position位置对应的类型int值
            if (model instanceof  Bean1){
                return 1;
            }
            return 2;
//            if(position % 2 == 0)
//                return  1;
//            return  2;
////            return super.getItemViewType(position);
        }

        /**
         * //需要通过当前item所要展示的layout决定当前数据源要赋值到哪个layout上面
         * @param holder
         * @param s
         * @param position
         */
        @Override
        public void convert(LGViewHolder holder, Object s, final int position) {

            if (s instanceof Bean1){
                Bean1 bean1 = (Bean1) s;
                TextView textView = (TextView) holder.getView(R.id.id_text);
                textView.setText("bean 1 -->>" + bean1.getBean1Name());
            }else if (s instanceof Bean2){
                Bean2 bean2 = (Bean2) s;
                TextView textView = (TextView) holder.getView(R.id.id_text);
                textView.setText("bean 2 -->>" + bean2.getBean2Name());
            }
        }
    }

    private static class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder>{

        private List<String> dataList;

        private ItemClickListener itemClickListener;

        public MyAdapter(List<String> dataList){
            this.dataList = dataList;
        }

        public interface ItemClickListener {
            void onItemClicked(View view, int position);
        }

        //设置点击回调接口
        public void setItemClickListener(ItemClickListener itemClickListener) {
            this.itemClickListener = itemClickListener;
        }

        //生成ViewHolder
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            int itemViewId = -1;
            if(viewType == 1){
                itemViewId = R.layout.item_view_main1;
            }else if(viewType == 2){
                itemViewId = R.layout.item_view_main2;
            }
            View itemView = LayoutInflater.from(parent.getContext()).inflate(itemViewId, parent, false);
            return new ViewHolder(itemView);
        }

        private String getItem(int position){
            return dataList.get(position);
        }

        //更新列表Item视图(根据需要绑定click事件)
        @Override
        public void onBindViewHolder(ViewHolder holder, final int position) {
            String str = getItem(position);
//            holder.icon.setImageDrawable(xxx);
            holder.name.setText(str);
            holder.root.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(itemClickListener != null)
                        itemClickListener.onItemClicked(v,position);
                }
            });
        }

        @Override
        public int getItemCount() {
            return dataList.size();
        }
        //ViewHolder保存每个item视图
        public class ViewHolder extends RecyclerView.ViewHolder{
            private ImageView icon;
            private TextView name;
            private View root;
            public ViewHolder(View itemView) {
                super(itemView);
                icon = (ImageView)itemView.findViewById(R.id.icon);
                name = (TextView)itemView.findViewById(R.id.id_text);
                root = itemView.findViewById(R.id.root);
            }
        }
    }
}
