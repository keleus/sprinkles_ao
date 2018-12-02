package cn.myzju.jzbook;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import cn.myzju.lib.sprinkles.Migration;
import cn.myzju.lib.sprinkles.Sprinkles;

/**
 * 用于数据库的初始化
 */
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        Sprinkles sprinkles = Sprinkles.init(getApplicationContext());

        sprinkles.addMigration(new Migration() {
            @Override
            protected void onPreMigrate() {
                // do nothing
            }

            @Override
            protected void doMigration(SQLiteDatabase db) {
                db.execSQL("CREATE TABLE user(uid INTEGER PRIMARY KEY autoincrement,username varchar(50) not null,pwd varchar(50) not null);");
                db.execSQL("CREATE TABLE taily(tid INTEGER PRIMARY KEY autoincrement,uid int not null,type int not null,value double not null,stime int not null,des text);");
                db.execSQL("CREATE TABLE session(uid int not null,username varchar(50) not null,pwd varchar(50) not null,ltime int default(strftime('%s','now')) not null)");
            }

            @Override
            protected void onPostMigrate() {
                // do nothing
            }
        });
    }

}
