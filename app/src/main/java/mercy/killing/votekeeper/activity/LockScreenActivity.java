package mercy.killing.votekeeper.activity;


import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;



import mercy.killing.votekeeper.R;

public class LockScreenActivity extends AppCompatActivity {
    TextView voteContent;
    Button voteFinishBtn;
    boolean isAbstainable = false;
    int maxSelectable = 1;
    boolean chkarr[] = new boolean[]{false, false, false, false, false};

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lock_screen);
        voteContent = (TextView) findViewById(R.id.vote_content);
        voteFinishBtn = (Button) findViewById(R.id.example_button);
        loadItems(new String[]{"항목1", "항목2", "항목3", "항목4", "항목5"});
        voteFinishBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i : getCheckedItems()) {
                    Log.e("asdf", i + "번째 아이템 체크됨");
                }
//                if (isFinishButtonEnabled()) {
//                    //투표 결과를 소켓으로 보낸다 얍
//                    finish();
//                }
            }
        });

        // 투표 정보를 받는다
        // isAbstainAble, maxSelectable 값을 갱신한다
        // 투표 항목들을 String 배열에 넣고 loadItem()을 호출한다
    }

    @Override
    protected void onPause() {
        super.onPause();
        // 서버에게 액티비티 재실행 요청을 보낸다
    }

    public void loadItems(String[] items) {
        ListView listView;
        listView = (ListView) findViewById(R.id.listView);
        ArrayList<String> are = new ArrayList<>();
        Collections.addAll(are, items);
        listView.setAdapter(new CustomAdapter(getApplicationContext(), are));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                CheckBox check = (CheckBox) view.findViewById(R.id.custom_listview_checkbox);
                check.setChecked(!check.isChecked());
                chkarr[i] = check.isChecked();
            }
        });
    }

    public boolean isFinishButtonEnabled() {
        //투표 완료를 누를 수 있는지(최소 1개의 항목을 선택했는지, 선택 가능 최대 항목 수를 초과하지 않았는지 등) 체크함
        boolean canEnable = false;
        for(boolean b : chkarr){
            if(b == true) {
                canEnable = true;
                break;
            }
        }
        return canEnable;
    }

    public void enableFinishButton() {
        //voteFinishBtn이 시각적으로 활성화/비활성화된 것처럼 보이게 함.
    }

    class CustomAdapter extends ArrayAdapter<String> {
        ArrayList<String> arr;
        LayoutInflater inflater;

        public CustomAdapter(Context context, ArrayList<String> arr) {
            super(context, 0, arr);
            inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            this.arr = arr;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = inflater.inflate(R.layout.custom_listview_layout, null);
            TextView textView = (TextView) view.findViewById(R.id.custom_listview_textView);
            CheckBox checkBox = (CheckBox) view.findViewById(R.id.custom_listview_checkbox);
            checkBox.setFocusable(false);
            textView.setText(arr.get(position));
            return view;
        }
    }

    public ArrayList<Integer> getCheckedItems() {
        ArrayList<Integer> arrayList = new ArrayList<>();
        for (int i = 0; i < chkarr.length; i++) {
            if (chkarr[i] == true) {
                arrayList.add(i);
            }
        }
        return arrayList;
    }
}
