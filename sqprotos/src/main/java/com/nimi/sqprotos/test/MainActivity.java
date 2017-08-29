package com.nimi.sqprotos.test;

import android.os.Bundle;
import android.widget.TextView;

import com.nimi.sqprotos.R;
import com.nimi.sqprotos.base.SActivity;

public class MainActivity extends SActivity {
    TextView ss;


    @Override
    public void initData(Bundle savedInstanceState) {
        ss=$(R.id.ss);
        ss.setText("mains");
    }
    @Override
    public int getLayoutId() {
        return R.layout.test;
    }


}
