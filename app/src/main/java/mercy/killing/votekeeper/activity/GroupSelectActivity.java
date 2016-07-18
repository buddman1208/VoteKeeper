package mercy.killing.votekeeper.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;

import mercy.killing.votekeeper.R;

public class GroupSelectActivity extends AppCompatActivity {

    Toolbar toolbar;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_select);
        setAppbarLayout();
        setDefault();
    }

    private void setDefault() {
        listView = (ListView) findViewById(R.id.group_select_listview);
        ArrayList<String> arr = new ArrayList<>();
        arr.add("아 새벽이군요 좋은 새벽이에요");
        arr.add("아 새벽이군요 좋은 새벽이에요");
        arr.add("아 새벽이군요 좋은 새벽이에요");
        arr.add("아 새벽이군요 좋은 새벽이에요");
        listView.setAdapter(new ListViewAdapter(getApplicationContext(), arr));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                // 여기서 각 그룹 클릭되었을대 액션 정의
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
