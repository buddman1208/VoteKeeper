package mercy.killing.votekeeper.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
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
import mercy.killing.votekeeper.utils.DataManager;
import mercy.killing.votekeeper.utils.NetworkInterface;

public class GroupInfoActivity extends AppCompatActivity {

    ListView groupInfoListView;
    NetworkInterface service;
    DataManager manager;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_info);
        setDefault();
        setAppbarLayout();
    }

    private void setAppbarLayout() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.colorPrimary));
        getSupportActionBar().setTitle("그룹 정보 보기");
        getSupportActionBar().setElevation(5);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Drawable drawable = getResources().getDrawable(R.drawable.abc_ic_ab_back_material);
        drawable.setColorFilter(getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(drawable);
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
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.group_info_newVote:
//                startActivity(new Intent(getApplicationContext(), ));
                // 새로운 투표처리
                break;
            case R.id.group_info_editinfo:
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
            TextView type = (TextView) view.findViewById(R.id.group_info_state);
            TextView textView = (TextView) view.findViewById(R.id.group_info_title);
            TextView content = (TextView) view.findViewById(R.id.group_info_content);
            type.setText("진행중인 투표");
            textView.setText(arr.get(position).first);
            content.setText(arr.get(position).second.toLocaleString());
            return view;
        }
    }

}
