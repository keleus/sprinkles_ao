package cn.myzju.jzbook.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.ArrayList;

import cn.myzju.jzbook.R;
import cn.myzju.jzbook.adapter.MyAdapter;
import cn.myzju.jzbook.entity.Item;
import cn.myzju.jzbook.entity.Taily;
import cn.myzju.jzbook.view.MyRecyclerView;
import cn.myzju.lib.sprinkles.CursorList;

import static cn.myzju.jzbook.db.DbManager.*;

public class IndexFragment extends Fragment {
    private ArrayList<Item> listItem=new ArrayList<>();
    double sum;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.linear_taily, container,false);
        MyRecyclerView recyclerView = (MyRecyclerView) view.findViewById(R.id.recy);
        DecimalFormat df = new DecimalFormat("#.00");
        CursorList<Taily> tailies=getTaily();
        if (tailies.size()>0) {
            for (Taily taily : tailies) {
                sum += taily.getValue();
                Item item = Item.tailyToItem(taily);
                listItem.add(item);
            }

            if (listItem != null && listItem.size() != 0) {
                MyAdapter myAdapter = new MyAdapter(listItem);
                recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
                recyclerView.setAdapter(myAdapter);
                TextView textView = (TextView) view.findViewById(R.id.sum);
                textView.setText(df.format(sum));
            }
        }
        return view;
    }
}
