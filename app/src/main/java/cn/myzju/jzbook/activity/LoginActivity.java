package cn.myzju.jzbook.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.dd.processbutton.iml.ActionProcessButton;

import cn.myzju.jzbook.R;
import cn.myzju.jzbook.entity.Session;
import cn.myzju.jzbook.entity.User;
import cn.myzju.jzbook.utils.ProgressGenerator;
import cn.myzju.lib.sprinkles.CursorList;

import static cn.myzju.jzbook.db.DbManager.getSession;
import static cn.myzju.jzbook.db.DbManager.getUser;

public class LoginActivity extends AppCompatActivity implements ProgressGenerator.OnCompleteListener{

    public static final String EXTRAS_ENDLESS_MODE = "EXTRAS_ENDLESS_MODE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        int timenow=(int)(System.currentTimeMillis()/1000);
        CursorList<Session> sessions=getSession();
        if (sessions.size()>0){
            Session session=sessions.get(0);
            int ltime=session.getLtime();
            if (timenow-ltime<(60*60*24*7)){
                Intent intent=new Intent(LoginActivity.this, MainActivity.class); startActivity(intent);
                LoginActivity.this.finish();
            }else{
                session.delete();
            }
        }
        sessions.close();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final EditText editEmail = (EditText) findViewById(R.id.username);
        final EditText editPassword = (EditText) findViewById(R.id.password);

        final ProgressGenerator progressGenerator = new ProgressGenerator(this);
        final ActionProcessButton btnSignIn = (ActionProcessButton) findViewById(R.id.login);

        Bundle extras = getIntent().getExtras();
        if(extras != null && extras.getBoolean(EXTRAS_ENDLESS_MODE)) {
            btnSignIn.setMode(ActionProcessButton.Mode.ENDLESS);
        } else {
            btnSignIn.setMode(ActionProcessButton.Mode.PROGRESS);
        }
        editEmail.setEnabled(true);
        editPassword.setEnabled(true);
        btnSignIn.setClickable(true);
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editEmail.getText()==null||"".equals(editEmail.getText().toString())){
                    Toast.makeText(LoginActivity.this, "请输入邮箱", Toast.LENGTH_LONG).show();
                    return;
                } else if (editPassword.getText()==null||"".equals(editPassword.getText().toString())){
                    Toast.makeText(LoginActivity.this, "请输入密码", Toast.LENGTH_LONG).show();
                    return;
                } else {
                    CursorList<User> users=getUser(editEmail.getText().toString());
                    if (users.size()>0){
                        User user=users.get(0);
                        users.close();
                        String uname=user.getUsername();
                        String pwd=user.getPwd();
                        if (!pwd.equals(editPassword.getText().toString())){
                            Toast.makeText(LoginActivity.this, "密码错误！", Toast.LENGTH_LONG).show();
                            return;
                        }
                        long uid=user.getUid();
                        Session s=new Session();
                        s.setUid(uid);
                        s.setUsername(editEmail.getText().toString());
                        s.setPwd(editPassword.getText().toString());
                        s.save();
                        editEmail.setEnabled(false);
                        editPassword.setEnabled(false);
                        btnSignIn.setClickable(false);
                    }else{
                        users.close();
                        User user1=new User();
                        user1.setUsername(editEmail.getText().toString());
                        user1.setPwd(editPassword.getText().toString());
                        user1.save();
                        Toast.makeText(LoginActivity.this, "注册成功，请重新登录！", Toast.LENGTH_LONG).show();
                        return;
                    }
                }
                progressGenerator.start(btnSignIn);
            }
        });
    }

    @Override
    public void onComplete() {
        Intent intent=new Intent(LoginActivity.this, MainActivity.class); startActivity(intent);
        LoginActivity.this.finish();
    }
}
