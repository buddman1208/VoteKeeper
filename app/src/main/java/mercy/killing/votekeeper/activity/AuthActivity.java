package mercy.killing.votekeeper.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import mercy.killing.votekeeper.R;

public class AuthActivity extends AppCompatActivity {

    Button login, register;
    EditText id, pw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
        setDefault();
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

            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
            }
        });
    }
}
