package com.ensate.robotixcarrc;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

public class HelpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.action_help);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);


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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: {
                onBackPressed();
                break;
            }

        }
        return super.onOptionsItemSelected(item);
    }
}
