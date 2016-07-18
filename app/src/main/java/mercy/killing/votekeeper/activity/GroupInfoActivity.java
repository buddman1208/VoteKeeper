package mercy.killing.votekeeper.activity;

import android.content.Context;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;

import mercy.killing.votekeeper.R;

public class GroupInfoActivity extends AppCompatActivity {

    ListView groupInfoListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_info);
        setDefault();
    }

    private void setDefault() {
        groupInfoListView = (ListView) findViewById(R.id.group_info_listView);
        ArrayList<Pair<String, Date>> arr = new ArrayList<>();
        arr.add(Pair.create("asf", new Date(System.currentTimeMillis())));
        arr.add(Pair.create("asf", new Date(System.currentTimeMillis())));
        arr.add(Pair.create("asf", new Date(System.currentTimeMillis())));
        arr.add(Pair.create("asf", new Date(System.currentTimeMillis())));
        groupInfoListView.setAdapter(new ListViewAdapter(getApplicationContext(), arr));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.group_info_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.group_add_newVote:
                // 새로운 투표처리
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    class ListViewAdapter extends ArrayAdapter<Pair<String, Date>> {
        ArrayList<Pair<String, Date>> arr;
        private LayoutInflater inflater;

        public ListViewAdapter(Context c, ArrayList<Pair<String, Date>> arr) {
            super(c, 0, arr);
            this.arr = arr;
            inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = inflater.inflate(R.layout.group_info_listview_content, null);
            TextView textView = (TextView) view.findViewById(R.id.mypage_listview_text);
            TextView content = (TextView) view.findViewById(R.id.group_info_content);
            textView.setText(arr.get(position).first);
            content.setText(arr.get(position).second.toLocaleString());
            return view;
        }
    }
}
