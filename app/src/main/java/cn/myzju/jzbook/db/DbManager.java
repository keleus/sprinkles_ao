package cn.myzju.jzbook.db;

import cn.myzju.jzbook.entity.Session;
import cn.myzju.jzbook.entity.Taily;
import cn.myzju.jzbook.entity.User;
import cn.myzju.lib.sprinkles.CursorList;
import cn.myzju.lib.sprinkles.Query;

/**
 * 使用第三方库来调用db
 */
public class DbManager {

    public static CursorList<Session> getSession(){
         return Query.all(Session.class).get();
    }
    public static void cleanSession(){
        Query.all(Session.class).get().get(0).delete();
    }
    public static CursorList<User> getUsers(){
        Session session=getSession().get(0);
        return Query.many(User.class,"select * from user where uid="+session.getUid()).get();
    }
    public static CursorList<User> getUser(String username){
        return Query.many(User.class,"select * from user where username like '"+username+"'").get();
    }
    public static CursorList<Taily> getTaily(){
        Session session=getSession().get(0);
        return Query.many(Taily.class,"select * from taily where uid="+session.getUid()).get();
    }
    public static void deleteTaily(long tid){
        Query.one(Taily.class,"select * from taily where tid="+tid).get().delete();
    }
    public static double sumTaily(){
        CursorList<Taily> tailies=getTaily();
        double sum=0;
        if (tailies.size()>0) {
            for (Taily taily : tailies) {
                sum = sum + taily.getValue();
            }
        }
        tailies.close();
        return sum;
    }
}
