package mercy.killing.votekeeper.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;

import mercy.killing.votekeeper.R;
import mercy.killing.votekeeper.models.User;
import mercy.killing.votekeeper.utils.DataManager;
import mercy.killing.votekeeper.utils.NetworkHelper;
import mercy.killing.votekeeper.utils.NetworkInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AuthActivity extends AppCompatActivity {

    Button login, register;
    EditText id, pw;
    DataManager manager;
    Call<User> loginUser;
    NetworkInterface service;

    MaterialDialog builder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
        builder = new MaterialDialog.Builder(this)
                .title("데이터를 로드합니다..")
                .progress(true, 0)
                .cancelable(false)
                .show();
        manager = new DataManager();
        manager.initializeManager(this);
        service = NetworkHelper.getNetworkInstance();
        checkUser();
    }

    private void checkUser() {
        if (manager.hasActiveUser()) {
            loginUser = service.userLogin(manager.getString(DataManager.USER_ID), manager.getString(DataManager.USER_PW));
            loginUser.enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    builder.dismiss();
                    switch (response.code()) {
                        case 200:
                            Toast.makeText(AuthActivity.this, response.body().getName() + " 님 환영합니다", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), GroupSelectActivity.class));
                            manager.saveUser(response.body());
                            finish();
                            break;
                        case 401:
                            Toast.makeText(AuthActivity.this, "로그인해주세요", Toast.LENGTH_SHORT).show();
                            break;
                    }
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    Log.e("asdf", t.getMessage());
                    builder.dismiss();
                }
            });
        } else {
            setDefault();
            builder.dismiss();
        }
    }

    private void setDefault() {
        login = (Button) findViewById(R.id.auth_login_button);
        register = (Button) findViewById(R.id.auth_start_register);
        id = (EditText) findViewById(R.id.auth_id_input);
        pw = (EditText) findViewById(R.id.auth_password_input);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 서버 로그인 처리
                loginUser = service.userLogin(id.getText().toString().trim(), pw.getText().toString().trim());
                loginUser.enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        switch (response.code()) {
                            case 200:
                                Toast.makeText(AuthActivity.this, response.body().getName() + " 님 환영합니다", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(), GroupSelectActivity.class));
                                finish();
                                break;
                            case 401:
                                Toast.makeText(AuthActivity.this, "아이디나 비밀번호가 잘못되었습니다", Toast.LENGTH_SHORT).show();
                                break;
                        }
                    }
                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        Log.e("asdf", t.getMessage());
                    }
                });
                register.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
                    }
                });
            }
        });
    }
}
