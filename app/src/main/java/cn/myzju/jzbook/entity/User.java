package cn.myzju.jzbook.entity;

import cn.myzju.lib.sprinkles.Model;
import cn.myzju.lib.sprinkles.annotations.AutoIncrement;
import cn.myzju.lib.sprinkles.annotations.Column;
import cn.myzju.lib.sprinkles.annotations.Key;
import cn.myzju.lib.sprinkles.annotations.Table;

/**
 * 用户表
 * @author clovef
 */
@Table("user")
public class User extends Model {
    @Key
    @AutoIncrement
    @Column("uid")
    private long uid;

    @Column("username")
    private String username;

    @Column("pwd")
    private String pwd;

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

    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }
}
