package cn.myzju.jzbook.entity;

import java.text.DecimalFormat;


/**
 * @author clovef
 * @date 2018/11/20
 */
public class Item {
    private long tid;
    private int type;
    private String text1;
    private String text2;
    public Item(){}

    public Item(long tid,int type,String text1,String text2){this.tid=tid;this.type=type;this.text1=text1;this.text2=text2;}

    public String getText1() {
        return text1;
    }

    public void setText1(String text1) {
        this.text1 = text1;
    }

    public String getText2() {
        return text2;
    }

    public void setText2(String text2) {
        this.text2 = text2;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public long getTid() {
        return tid;
    }

    public void setTid(long tid) {
        this.tid = tid;
    }

    public static Item tailyToItem(Taily taily){
        int type=taily.getType();
        long tid=taily.getTid();
        DecimalFormat df = new DecimalFormat("#.00");
        String text1=df.format(taily.getValue());
        String stime=String.valueOf(taily.getStime());
        String text2=String.valueOf(stime.substring(0,4)+"-"+stime.substring(4,6)+"-"+stime.substring(6,8));
        Item item=new Item(tid,type,text1,text2);
        return item;
    }
}

