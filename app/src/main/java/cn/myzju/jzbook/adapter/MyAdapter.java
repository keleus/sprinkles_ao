package cn.myzju.jzbook.adapter;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import cn.myzju.jzbook.R;
import cn.myzju.jzbook.activity.MainActivity;
import cn.myzju.jzbook.entity.Item;

import static cn.myzju.jzbook.db.DbManager.deleteTaily;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    private ArrayList<Item> mListItem;

    public class MyViewHolder extends ViewHolder{
        TextView tx;
        TextView tx2;
        ImageView imageView;
        public LinearLayout layout;
        public MyViewHolder(View view){
            super(view);
            imageView=(ImageView)view.findViewById(R.id.imagelogo);
            tx=(TextView)view.findViewById(R.id.text1);
            tx2=(TextView)view.findViewById(R.id.text2);
            layout= (LinearLayout) view.findViewById(R.id.item_recycler_ll);
        }
    }
    @Override
    public MyViewHolder onCreateViewHolder(final ViewGroup parent, int i) {
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.rec_item, parent, false);
        final MyViewHolder holder=new MyViewHolder(view);
        return holder;
    }
    public MyAdapter(ArrayList<Item> listItem){
        this.mListItem=listItem;
    }
    @Override
    public int getItemCount() {
        return mListItem.size();
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int i) {
        holder.imageView.setImageResource(mListItem.get(i).getType());
        holder.tx.setText(mListItem.get(i).getText1());
        holder.tx2.setText(mListItem.get(i).getText2());
        if (Double.valueOf(mListItem.get(i).getText1())<0){
            holder.tx.setTextColor(Color.RED);
        }
    }
    public void removeRecycle(int pos){
        deleteTaily(mListItem.get(pos).getTid());
        MainActivity.res();
        mListItem.remove(pos);
        notifyItemRemoved(pos);
    }
}
