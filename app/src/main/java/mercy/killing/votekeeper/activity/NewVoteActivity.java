package mercy.killing.votekeeper.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.melnykov.fab.FloatingActionButton;

import java.util.ArrayList;
import java.util.Date;

import mercy.killing.votekeeper.R;
import mercy.killing.votekeeper.models.Vote;
import mercy.killing.votekeeper.utils.DataManager;
import mercy.killing.votekeeper.utils.NetworkHelper;
import mercy.killing.votekeeper.utils.NetworkInterface;
import mercy.killing.votekeeper.utils.VoteKeeperHelperClass;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewVoteActivity extends AppCompatActivity {

    ListView listview;
    EditText title;
    ListViewAdapter adapter;
    Toolbar toolbar;
    Call<Vote> newVote;
    NetworkInterface service;
    TextView footer;
    DataManager manager;
    FloatingActionButton button;
    ArrayList<String> content = new ArrayList<>();
    Intent intent;
    MaterialDialog builder;
    String currentGroupID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_vote);
        intent = getIntent();
        currentGroupID = intent.getStringExtra("currentGroupID");
        setDefault();
        setAppbarLayout();
    }

    private void setDefault() {
        manager = new DataManager();
        manager.initializeManager(this);
        service = NetworkHelper.getNetworkInstance();
        button = (FloatingActionButton) findViewById(R.id.fab);
        listview = (ListView) findViewById(R.id.newvote_listview);
        title = (EditText) findViewById(R.id.newvote_sibjectinput);
        footer = new TextView(this);
        adapter = new ListViewAdapter(getApplicationContext(), content);
        listview.setAdapter(adapter);
        listview.addFooterView(footer);
        content = new ArrayList<>();
        footer.setText("새로운 항목 추가");
        final EditText editText = new EditText(this);
        editText.setPadding(10, 0, 10, 0);
        editText.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == content.size()) {
                    new MaterialDialog.Builder(NewVoteActivity.this)
                            .title("새로운 항목을 입력하세요")
                            .customView(editText, false)
                            .positiveText("확인")
                            .onPositive(new MaterialDialog.SingleButtonCallback() {
                                @Override
                                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                    String s = editText.getText().toString().trim();
                                    if (s.equals(""))
                                        Toast.makeText(NewVoteActivity.this, "빈칸넣지마ㅠㅠㅠㅠㅠ", Toast.LENGTH_SHORT).show();
                                    else {
                                        content.add(editText.getText().toString().trim());
                                        editText.setText("");
                                        setAdapter();
                                    }
                                }
                            })
                            .show();
                } else {
                    content.remove(i);
                    setAdapter();
                }
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                builder = new MaterialDialog.Builder(NewVoteActivity.this)
                        .title("데이터를 로드합니다..")
                        .content("잠시마안 기다려주세요오")
                        .progress(true, 0)
                        .cancelable(false)
                        .show();
                newVote = service.newVote(manager.getString(DataManager.USER_ID), currentGroupID, title.getText().toString().trim(),
                        new Date(System.currentTimeMillis()), VoteKeeperHelperClass.convertArraytoString(content), content.size(), false);
                newVote.enqueue(new Callback<Vote>() {
                    @Override
                    public void onResponse(Call<Vote> call, Response<Vote> response) {
                        builder.dismiss();
                        switch (response.code()) {
                            case 200:
                                Toast.makeText(NewVoteActivity.this, "등록에 성공했습니다", Toast.LENGTH_SHORT).show();
                                finish();
                                break;
                            case 400:
                                break;
                        }
                    }

                    @Override
                    public void onFailure(Call<Vote> call, Throwable t) {
                        Log.e("asdf", t.getMessage());
                    }
                });
            }
        });
    }

    private void setAdapter() {
        listview.setAdapter(new ListViewAdapter(getApplicationContext(), content));
    }

    private void setAppbarLayout() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.colorPrimary));
        getSupportActionBar().setTitle("투표 등록");
        getSupportActionBar().setElevation(5);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Drawable drawable = getResources().getDrawable(R.drawable.abc_ic_ab_back_material);
        drawable.setColorFilter(getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(drawable);
    }

    class ListViewAdapter extends ArrayAdapter<String> {
        ArrayList<String> arr;
        private LayoutInflater inflater;

        public ListViewAdapter(Context c, ArrayList<String> arr) {
            super(c, 0, arr);
            this.arr = arr;
            inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = inflater.inflate(R.layout.mypage_listview_content, null);
            TextView textView = (TextView) view.findViewById(R.id.mypage_listview_text);
            textView.setText(arr.get(position));
            return view;
        }
    }
}

