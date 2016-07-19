package mercy.killing.votekeeper.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.melnykov.fab.FloatingActionButton;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import mercy.killing.votekeeper.R;
import mercy.killing.votekeeper.models.Group;
import mercy.killing.votekeeper.utils.DataManager;
import mercy.killing.votekeeper.utils.NetworkHelper;
import mercy.killing.votekeeper.utils.NetworkInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GroupSelectActivity extends AppCompatActivity {

    Toolbar toolbar;
    Call<List<Group>> groupList;
    DataManager manager;
    NetworkInterface service;
    ArrayList<String> groupids = new ArrayList<>();
    ListView listView;
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        manager = new DataManager();
        manager.initializeManager(this);
        service = NetworkHelper.getNetworkInstance();
        groupList = service.getGroupList(manager.getString(DataManager.USER_ID));
        setContentView(R.layout.activity_group_select);
        setAppbarLayout();
        setDefault();
    }

    private void setDefault() {
        final ArrayList<String> arrayList = new ArrayList<>();
        groupList.enqueue(new Callback<List<Group>>() {
            @Override
            public void onResponse(Call<List<Group>> call, Response<List<Group>> response) {
                switch (response.code()){
                    case 200:
                        for(Group g : response.body()){
                            arrayList.add(g.getGroupname());
                            groupids.add(g.getGroupId());
                        }
                        break;
                    case 400:
                        Toast.makeText(GroupSelectActivity.this, "가입된 그룹이 존재하지 않습니다", Toast.LENGTH_SHORT).show();
                        break;
                }
            }

            @Override
            public void onFailure(Call<List<Group>> call, Throwable t) {
                Log.e("asdf", t.getMessage());
            }
        });
        listView = (ListView) findViewById(R.id.group_select_listview);
        fab = (FloatingActionButton) findViewById(R.id.group_select_fab);
        listView.setAdapter(new ListViewAdapter(getApplicationContext(), arrayList));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                // 여기서 각 그룹 클릭되었을대 액션 정의
                startActivity(new Intent(getApplicationContext(), GroupInfoActivity.class).putExtra("groupId", groupids.get(i)));
            }
        });
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), GroupAddActivity.class));
            }
        });
    }

    private void setAppbarLayout() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.colorPrimary));
        getSupportActionBar().setTitle("그룹 선택");
        getSupportActionBar().setElevation(5);
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
