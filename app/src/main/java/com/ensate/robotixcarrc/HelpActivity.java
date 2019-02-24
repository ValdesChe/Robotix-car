package com.ensate.robotixcarrc;

import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

public class HelpActivity extends AppCompatActivity {
    SharedPreferences mPrefs;

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
        mPrefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        Resources res = getResources();

        String commandTitleString = res.getString(R.string.commands_titles_string,
                res.getString(R.string.pref_title_pos_up),
                res.getString(R.string.pref_title_pos_down),
                res.getString(R.string.pref_title_pos_left),
                res.getString(R.string.pref_title_pos_right),
                res.getString(R.string.pref_title_pos_up_left),
                res.getString(R.string.pref_title_pos_up_right),
                res.getString(R.string.pref_title_pos_down_left),
                res.getString(R.string.pref_title_pos_down_right));

        String commandCharDataString = res.getString(R.string.command_char_data_string,
                mPrefs.getString(res.getString(R.string.key_pref_pos_up), res.getString(R.string.pref_default_pos_up)),
                mPrefs.getString(res.getString(R.string.key_pref_pos_down), res.getString(R.string.pref_default_pos_down)),
                mPrefs.getString(res.getString(R.string.key_pref_pos_left), res.getString(R.string.pref_default_pos_left)),
                mPrefs.getString(res.getString(R.string.key_pref_pos_right), res.getString(R.string.pref_default_pos_right)),
                mPrefs.getString(res.getString(R.string.key_pref_pos_up_left), res.getString(R.string.pref_default_pos_up_left)),
                mPrefs.getString(res.getString(R.string.key_pref_pos_up_right), res.getString(R.string.pref_default_pos_up_right)),
                mPrefs.getString(res.getString(R.string.key_pref_pos_down_left), res.getString(R.string.pref_default_pos_down_left)),
                mPrefs.getString(res.getString(R.string.key_pref_pos_down_right), res.getString(R.string.pref_default_pos_down_right)));

        TextView commandTitleTextView = findViewById(R.id.commandTitleTextView);
        TextView commandCharDataTextView = findViewById(R.id.commandCharDataTextView);
        commandTitleTextView.setText(commandTitleString);
        commandCharDataTextView.setText(commandCharDataString);

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
