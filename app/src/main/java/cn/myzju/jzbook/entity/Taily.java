package cn.myzju.jzbook.entity;

import cn.myzju.lib.sprinkles.Model;
import cn.myzju.lib.sprinkles.annotations.AutoIncrement;
import cn.myzju.lib.sprinkles.annotations.Column;
import cn.myzju.lib.sprinkles.annotations.Key;
import cn.myzju.lib.sprinkles.annotations.Table;

/**
 * 账单表
 * @author clovef
 */
@Table("taily")
public class Taily extends Model {
    @Key
    @AutoIncrement
    @Column("tid")
    private long tid;

    @Column("uid")
    private long uid;

    @Column("value")
    private double value;

    @Column("des")
    private String des;

    @Column("stime")
    private int stime;

    @Column("type")
    private int type;

    public Taily(){}

    public Taily(long uid,double value,String des,int stime,int type){this.des=des;this.stime=stime;this.uid=uid;this.type=type;this.value=value;}

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public int getStime() {
        return stime;
    }

    public void setStime(int stime) {
        this.stime = stime;
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

    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }
}
