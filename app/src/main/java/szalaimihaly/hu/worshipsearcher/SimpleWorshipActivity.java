package szalaimihaly.hu.worshipsearcher;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Mihaly on 2015.10.29..
 */
public class SimpleWorshipActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        String date = bundle.getString("date");
        String churchLoc = bundle.getString("churchLoc");
        String week = bundle.getString("week");
        String comment = bundle.getString("comment");
        MainActivity.getDbl().open();

        setContentView(R.layout.simpleworship);
        TextView textViewDate = (TextView) findViewById(R.id.date);
        TextView textViewCity = (TextView) findViewById(R.id.churchCity2);
        TextView textViewWeekkComment = (TextView) findViewById(R.id.weekcommnet2);
        TextView textViewComment = (TextView) findViewById(R.id.worshipcomment);
        textViewDate.setText(date);
        textViewCity.setText(churchLoc);
        textViewWeekkComment.setText(week);
        textViewComment.setText(comment);
    }
}
