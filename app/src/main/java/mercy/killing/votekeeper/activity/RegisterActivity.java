package mercy.killing.votekeeper.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import mercy.killing.votekeeper.R;

public class RegisterActivity extends AppCompatActivity {

    Button register;
    EditText name, id, pw;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
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
                    // 비밀번호 두개가 같으면 처리

                } else Toast.makeText(RegisterActivity.this, "비밀번호가 올바르지 않습니다.", Toast.LENGTH_SHORT).show();
            }
        });
    }
    boolean pwIsCorrect(){
        return id.getText().toString().trim().equals(pw.getText().toString().trim());
    }
}
