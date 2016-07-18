package mercy.killing.votekeeper.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import mercy.killing.votekeeper.R;

public class AuthActivity extends AppCompatActivity {

    Button login;
    EditText id, pw;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
        setDefault();
    }

    private void setDefault() {
        login = (Button) findViewById(R.id.auth_login_button);
        id = (EditText) findViewById(R.id.auth_id_input);
        pw = (EditText) findViewById(R.id.auth_password_input);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 서버 로그인 처리

            }
        });
    }
}
