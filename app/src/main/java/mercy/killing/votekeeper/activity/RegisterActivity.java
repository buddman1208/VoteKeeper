package mercy.killing.votekeeper.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import mercy.killing.votekeeper.R;
import mercy.killing.votekeeper.models.User;
import mercy.killing.votekeeper.utils.NetworkHelper;
import mercy.killing.votekeeper.utils.NetworkInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    Button register;
    Call<User> registerUser;
    NetworkInterface service;
    EditText name, id, pw;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        service= NetworkHelper.getNetworkInstance();
        setDefault();
    }

    private void setDefault() {
        register = (Button) findViewById(R.id.register_login_button);
        name = (EditText) findViewById(R.id.register_name_input);
        id = (EditText) findViewById(R.id.register_id_input);
        pw = (EditText) findViewById(R.id.register_password_input);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(pwIsCorrect()){
                    registerUser = service.userRegister(id.getText().toString().trim(), pw.getText().toString().trim(), name.getText().toString().trim());
                    registerUser.enqueue(new Callback<User>() {
                        @Override
                        public void onResponse(Call<User> call, Response<User> response) {
                            switch (response.code()){
                                case 200:
                                    Toast.makeText(RegisterActivity.this, response.body().getName()+"님 가입이 완료되었습니다.\n로그인해주세요", Toast.LENGTH_SHORT).show();
                                    break;
                                case 409:
                                    Toast.makeText(RegisterActivity.this, "중복된 아이디입니다", Toast.LENGTH_SHORT).show();
                                    break;

                            }
                        }

                        @Override
                        public void onFailure(Call<User> call, Throwable t) {
                            Toast.makeText(RegisterActivity.this, "로그인 실패", Toast.LENGTH_SHORT).show();
                            Log.e("asdf", t.getMessage());
                        }
                    });

                } else Toast.makeText(RegisterActivity.this, "비밀번호가 올바르지 않습니다.", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private boolean pwIsCorrect(){
        return id.getText().toString().trim().equals(pw.getText().toString().trim());
    }
}
