package com.example.jewel.clothingrec;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.jewel.clothingrec.tagsview.TagsActivity;
import com.example.jewel.clothingrec.tagsview.TagsForPantsActivity;

/**
 * Created by jewel on 2016/10/25.
 */
public class TagsForWhat extends Activity {
    private Button pantsBtn;
    private Button shirtBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.type_selection);
        pantsBtn = (Button) findViewById(R.id.pantsBtn);
        shirtBtn = (Button) findViewById(R.id.shirtBtn);
        pantsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(TagsForWhat.this,TagsForPantsActivity.class);
                startActivity(intent);
            }
        });
        shirtBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TagsForWhat.this,TagsActivity.class);
                startActivity(intent);
            }
        });
    }
}
