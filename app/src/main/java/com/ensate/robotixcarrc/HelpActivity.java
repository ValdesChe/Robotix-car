package com.ensate.robotixcarrc;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class HelpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        updateHelpTable();
    }

    private void updateHelpTable() {

        TextView moveUpTextView = findViewById(R.id.cMoveUpTextView);
        moveUpTextView.setText(String.valueOf(Constants.MOVE_UP_CMD));

        TextView moveDownTextView = findViewById(R.id.cMoveDownTextView);
        moveDownTextView.setText(String.valueOf(Constants.MOVE_DOWN_CMD));

        TextView moveUpLeftView = findViewById(R.id.cMoveUpLeftView);
        moveUpLeftView.setText(String.valueOf(Constants.MOVE_LEFT_CMD));

        TextView moveRightTextView = findViewById(R.id.cMoveRightTextView);
        moveRightTextView.setText(String.valueOf(Constants.MOVE_RIGHT_CMD));


    }
}
