package com.example.jewel.clothingrec;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private Button localBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        localBtn = (Button) findViewById(R.id.localRec);
//       建立事件监听器的第一种方法：使用匿名内部类
        localBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("你点击了本地搭配");
            }
        });
//        建立事件监听器的第二种方法：使用外部类
//        建立事件监听器的第三种方法：使用接口
    }
}
