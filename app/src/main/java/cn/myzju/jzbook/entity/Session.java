package cn.myzju.jzbook.entity;

import cn.myzju.lib.sprinkles.Model;
import cn.myzju.lib.sprinkles.annotations.Column;
import cn.myzju.lib.sprinkles.annotations.Key;
import cn.myzju.lib.sprinkles.annotations.Table;

@Table("session")
public class Session extends Model {
    @Key
    @Column("uid")
    private long uid;

    @Column("username")
    private String username;

    @Column("pwd")
    private String pwd;

    @Column("ltime")
    private int ltime;

    public Session() {
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public int getLtime() {
        return ltime;
    }

    public void setLtime(int ltime) {
        this.ltime = ltime;
    }

    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }
}
