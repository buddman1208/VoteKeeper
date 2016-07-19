package mercy.killing.votekeeper.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.Network;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.google.gson.internal.bind.DateTypeAdapter;

import java.util.ArrayList;
import java.util.List;

import mercy.killing.votekeeper.R;
import mercy.killing.votekeeper.models.Group;
import mercy.killing.votekeeper.models.User;
import mercy.killing.votekeeper.utils.DataManager;
import mercy.killing.votekeeper.utils.NetworkHelper;
import mercy.killing.votekeeper.utils.NetworkInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GroupAddActivity extends AppCompatActivity {

    EditText groupName;
    Toolbar toolbar;
    ListView listView;
    MaterialDialog builder;
    Call<List<User>> getUserList;
    NetworkInterface service;
    DataManager manager;
    ListViewAdapter adapter;
    ArrayList<User> currentUserList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_add);
        setAppbarLayout();
        setDefault();
    }

    private void setDefault() {
        manager = new DataManager();
        manager.initializeManager(this);
        service = NetworkHelper.getNetworkInstance();
        groupName = (EditText) findViewById(R.id.group_add_groupname_input);
        listView = (ListView) findViewById(R.id.group_add_memberlist);
        listView.setAdapter(new ListViewAdapter(getApplicationContext(), currentUserList));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                currentUserList.remove(i);
                adapter.notifyDataSetChanged();
            }
        });
    }

    private void setAppbarLayout() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.colorPrimary));
        getSupportActionBar().setTitle("그룹 추가");
        getSupportActionBar().setElevation(5);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Drawable drawable = getResources().getDrawable(R.drawable.abc_ic_ab_back_material);
        drawable.setColorFilter(getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(drawable);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.group_add_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
            case R.id.group_add_new_member:
                // 새로운 멤버 추가 액션
                final ArrayList<String> users = new ArrayList<>();
                builder = new MaterialDialog.Builder(this)
                        .title("데이터를 로드합니다..")
                        .content("잠시마안 기다려주세요오")
                        .progress(true, 0)
                        .cancelable(false)
                        .show();
                getUserList = service.getUserList(manager.getString(DataManager.USER_ID));
                getUserList.enqueue(new Callback<List<User>>() {
                    @Override
                    public void onResponse(Call<List<User>> call, final Response<List<User>> response) {
                        builder.dismiss();
                        switch (response.code()){
                            case 200:
                                for(User user : response.body()) {
                                    users.add(user.getName());
                                }
                                new MaterialDialog.Builder(GroupAddActivity.this)
                                        .title("멤버 새로 초대")
                                        .items(users)
                                        .itemsCallbackSingleChoice(-1, new MaterialDialog.ListCallbackSingleChoice() {
                                            @Override
                                            public boolean onSelection(MaterialDialog dialog, View itemView, int which, CharSequence text) {
                                                currentUserList.add(response.body().get(which));
                                                adapter.notifyDataSetChanged();
                                                return false;
                                            }
                                        })
                                        .show();
                                break;
                            case 400:
                                Toast.makeText(GroupAddActivity.this, "No User", Toast.LENGTH_SHORT).show();
                                break;
                        }
                    }

                    @Override
                    public void onFailure(Call<List<User>> call, Throwable t) {
                        builder.dismiss();
                        Log.e("asdf", t.getMessage());
                    }
                });
                break;
            case R.id.group_add_groupFuckOff:
                finish();
                // 그룹 폭파 액션
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    class ListViewAdapter extends ArrayAdapter<User> {

        ArrayList<User> arr;
        private LayoutInflater inflater;

        public ListViewAdapter(Context c, ArrayList<User> arr) {
            super(c, 0, arr);
            this.arr = arr;
            inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = inflater.inflate(R.layout.mypage_listview_content, null);
            TextView textView = (TextView) view.findViewById(R.id.mypage_listview_text);
            textView.setText(arr.get(position).getName());
            return view;
        }
    }

}
