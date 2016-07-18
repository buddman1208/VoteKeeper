package mercy.killing.votekeeper.activity;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
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

import java.util.ArrayList;

import mercy.killing.votekeeper.R;

public class GroupAddActivity extends AppCompatActivity {

    EditText groupName;
    Toolbar toolbar;
    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_add);
        setAppbarLayout();
        setDefault();
    }

    private void setDefault() {
        groupName = (EditText) findViewById(R.id.group_add_groupname_input);
        listView = (ListView) findViewById(R.id.group_add_memberlist);
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("THIS_IS_DUMMY_DATA");
        arrayList.add("WOW");
        arrayList.add("Much DOGE");
        listView.setAdapter(new ListViewAdapter(getApplicationContext(), arrayList));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                // 여기서 클릭했을때 멤버 삭제 가능하게 처리
            }
        });
    }

    private void setAppbarLayout() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.colorPrimary));
        getSupportActionBar().setTitle("그룹 관리");
        getSupportActionBar().setElevation(5);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.group_add_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.group_add_new_member:
                // 새로운 멤버 추가 액션
                break;
            case R.id.group_add_groupFuckOff:
                // 그룹 폭파 액션
                break;

        }
        return super.onOptionsItemSelected(item);
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
