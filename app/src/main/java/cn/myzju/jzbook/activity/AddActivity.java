package cn.myzju.jzbook.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.bruce.pickerview.popwindow.DatePickerPopWin;
import com.gigamole.navigationtabstrip.NavigationTabStrip;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.myzju.jzbook.R;
import cn.myzju.jzbook.entity.Session;
import cn.myzju.jzbook.entity.Taily;

import static cn.myzju.jzbook.db.DbManager.getSession;

public class AddActivity extends AppCompatActivity {
    private int time;
    private int flag=1;
    private GridView gridView;
    private int type=R.drawable.qian;
    private List<Map<String, Object>> dataList;
    private SimpleAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        gridView=(GridView)findViewById(R.id.gridview) ;
        inDate();
        String[] from={"img","text"};
        int[] to={R.id.img,R.id.text};
        adapter=new SimpleAdapter(this, dataList, R.layout.grid_item,from,to);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,long arg3) {
                type=Integer.valueOf(dataList.get(arg2).get("img").toString());
            }
        });

        final EditText editvalue=(EditText)findViewById(R.id.add_value);
        final EditText editdes=(EditText)findViewById(R.id.add_des);

        final TextView textView=(TextView)findViewById(R.id.add_time);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar=Calendar.getInstance();
                String date=calendar.get(Calendar.YEAR)+"-"+(calendar.get(Calendar.MONTH)+1)+"-"+calendar.get(Calendar.DAY_OF_MONTH);
                final DatePickerPopWin pickerPopWin = new DatePickerPopWin.Builder(AddActivity.this, new DatePickerPopWin.OnDatePickedListener() {
                    @Override
                    public void onDatePickCompleted(int year, int month, int day, String dateDesc) {
                        Toast.makeText(AddActivity.this, dateDesc, Toast.LENGTH_SHORT).show();
                    }
                }).textConfirm("确认") .textCancel("取消").btnTextSize(16).viewTextSize(25).colorCancel(Color.parseColor("#999999")) .colorConfirm(Color.parseColor("#009900")).minYear(1990).maxYear(2550).dateChose(date).build();
                pickerPopWin.showPopWin(AddActivity.this);

                pickerPopWin.confirmBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        textView.setText((pickerPopWin.yearLoopView.getSelectedItem()+1990)+"-"+(pickerPopWin.monthLoopView.getSelectedItem()+1)+"-"+(pickerPopWin.dayLoopView.getSelectedItem()+1));
                        time=(pickerPopWin.yearLoopView.getSelectedItem()+1990)*10000+(pickerPopWin.monthLoopView.getSelectedItem()+1)*100+(pickerPopWin.dayLoopView.getSelectedItem()+1);
                        pickerPopWin.dismissPopWin();
                    }
                });
            }

        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (editvalue.getText().toString().length()==0||editvalue.getText()==null){
                    Toast.makeText(AddActivity.this, "请输入金额", Toast.LENGTH_LONG).show();
                    return;
                }else if (time==0){
                    Toast.makeText(AddActivity.this, "请选择时间", Toast.LENGTH_LONG).show();
                    return;
                }
                Session session=getSession().get(0);

                if (session!=null){
                    long uid=session.getUid();
                    Taily taily = new Taily();
                    taily.setUid(uid);
                    taily.setValue(flag*Double.valueOf(editvalue.getText().toString()));
                    taily.setStime(time);
                    taily.setDes(editdes.getText().toString());
                    taily.setType(type);
                    taily.save();
                    Intent intent=new Intent(AddActivity.this, MainActivity.class); startActivity(intent);
                    AddActivity.this.finish();
                }else{
                    Toast.makeText(AddActivity.this, "添加失败", Toast.LENGTH_LONG).show();
                }
            }
        });

        NavigationTabStrip tabStrip=(NavigationTabStrip) findViewById(R.id.nav_tab);
        tabStrip.setTabIndex(0);
        tabStrip.setOnTabStripSelectedIndexListener(new NavigationTabStrip.OnTabStripSelectedIndexListener() {
            @Override
            public void onStartTabSelected(String title, int index) {
            }

            @Override
            public void onEndTabSelected(String title, int index) {
                if (index==0){
                    inDate();
                }else if(index==1){
                    outDate();
                }
                adapter=new SimpleAdapter(AddActivity.this, dataList, R.layout.grid_item,new String[]{"img","text"},new int[]{R.id.img,R.id.text});
                gridView.setAdapter(adapter);
                gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,long arg3) {
                        type=Integer.valueOf(dataList.get(arg2).get("img").toString());
                    }
                });
            }
        });


    }

    void outDate() {
        flag=-1;
        int icno[] = {R.drawable.feiji,R.drawable.duanxin,R.drawable.gouwu,R.drawable.jianshen,R.drawable.qian};
        String name[]={"旅行","通信","购物","健身","其它"};
        dataList = new ArrayList<Map<String, Object>>();
        for (int i = 0; i <icno.length; i++) {
            Map<String, Object> map=new HashMap<String, Object>();
            map.put("img", icno[i]);
            map.put("text",name[i]);
            dataList.add(map);
        }
    }

    void inDate() {
        flag=1;
        int icno[] = {R.drawable.huobiduihuan,R.drawable.qian,R.drawable.xiangqu};
        String name[]={"投资","工资","其它"};
        dataList = new ArrayList<Map<String, Object>>();
        for (int i = 0; i <icno.length; i++) {
            Map<String, Object> map=new HashMap<String, Object>();
            map.put("img", icno[i]);
            map.put("text",name[i]);
            dataList.add(map);
        }
    }
}
