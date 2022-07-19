package com.shenyutao.anotationdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.shenyutao.annotations.BindView;
import com.shenyutao.annotations_android.InjectUtils;
import com.shenyutao.annotations_android.OnClick;
import com.shenyutao.open_api.ShzyButterKnife;

import java.util.ArrayList;
import java.util.List;

/**
 * @author shzy
 */
public class MainActivity extends AppCompatActivity {
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.text_view)
    TextView textView;

    @BindView(R.id.button)
    Button button;

    @BindView(R.id.second_button)
    Button secondButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ShzyButterKnife.bind(this);
        InjectUtils.inject(this);
        textView.setText("Sadasdasdas");
        button.setText("666");
        secondButton.setText("Shzy");

        List<String> list = new ArrayList<>();
        ((List) list).add(this);
    }

    @OnClick(R.id.button)
    public void click(View view){
        Toast.makeText(this,"666",Toast.LENGTH_SHORT).show();
    }
}