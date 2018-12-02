# sprinkles_ao
基于sprinkles修改的一个SQLite OR-Mapping工具，让其适应Android SDK 26以上版本。

原作者：[@emilsjolander](https://github.com/emilsjolander)

## 前言
Google 在2017年就发布了Android 8.0，即Android SDK 26

Android 8.0 更改了 ContentResolver.notifyChange() 和 registerContentObserver(Uri, boolean, ContentObserver) 在针对 Android 8.0 的应用中的行为方式。现在，这些 API 需要在所有 URI 中为颁发机构定义一个有效的 ContentProvider。使用相关权限定义一个有效的 ContentProvider 可帮助您的应用防范来自恶意应用的内容变更，并防止将可能的私密数据泄露给恶意应用。

这也导致了原项目在Android SDK 26以上版本会报以下错误：

```
Failed to find provider null for user 0; expected to find a valid ContentProvider for this authority
```

解决的方法也很简单，在此就不再做说明，可自行对比本项目与原项目的差异。

## sprinkles_ao的优点
创建JavaBean类时就可以通过@Key @Table的方式关联表，无需编写其它如XML的关联文件。

相比Android官方SQLite，它无需多次传入Content属性以创建数据库。

Model类中继承的save()和delete()方法简单好用，添加删除十分方便。

## 初始化

#### 声明provide
在项目的 AndroidManifest.xml 文件中声明provide：
```
<provider android:authorities="cn.myzju.lib.sprinkles.DatabaseContentProvider" 
          android:name="cn.myzju.lib.sprinkles.DatabaseContentProvider"/>
```

#### 创建数据库
你可以在一个MyApplication extends Application类的OnCreate()方法中进行初始化
```
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
                db.execSQL("create table XXX();");
                db.execSQL("create table XXX();");
                //在此处执行你的建表语句
            }

            @Override
            protected void onPostMigrate() {
                // do nothing
            }
        });
	}

}
```

#### 创建类
想要方便地调用sprinkles_ao来实现类与数据库的无缝连接，你需要创建相应的继承于cn.myzju.lib.sprinkles.Model的类

```
@Table("Students")//对应表标识
public class Student extends Model {

  @Key
	@AutoIncrement  //自增主键标识
	@Column("sno")
	private long sno; //注意sqlite中自增字段是一个支持存储long型的INTEGER

	@Column("name")
	public String name;

	@Column("rex")
	public String rex;

	public long getSno() {
		return sno;
	}

}
```

## 使用

#### 增
可以使用sava()方法向表中插入数据
```
Student s=new Student();
s.name="小明";
s.rex="女";
s.save();
```

对声明了自增主键的类，sprinkles_ao会自动填充数值。但类中的自增字段类型必须为long，不然会报错。
`save()`方法会返回一个boolean值

#### 删
```
Student s=new Student();
s.sno=1;
s.name="小明";
s.rex="女";
s.delete();
```

删除时，尽可能是一个完整的类，一般地先通过查找再进行删除。

#### 查
预制了查询单个、多个、全部的方法，可以通过下面的方式调用。
```
Student one=Query.one(Student.class,"select XXX from students where sno=1").get();

CursorList<Student> many=Query.many(Student.class,"select XXX from students").get();
Student s1=many.get(0);
many.close();

CursorList<Student> all=Query.all(Student.class).get();
List<Student> alls=all.asList();
all.close();
```

CursorList<T>中有get(int pos)、close()、asList()、size()等方法。

在使用get(int pos)方法/foreach遍历前，请使用size()方法判断大于0，以防止空指针错误。
