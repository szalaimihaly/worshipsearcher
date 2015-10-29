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
        int churhcid = bundle.getInt("churchid");
        MainActivity.getDbl().open();
        ArrayList<Worship> worshipList = MainActivity.getDbl().getWorshipByChurch(churhcid);
        Worship worship = worshipList.get(0);
        Church selectedChurch = MainActivity.getDbl().getChurchById(churhcid);
        String week = MainActivity.getDbl().getWeekById(worship.getWeekid());
        MainActivity.getDbl().close();
        DateListener dateListener = new DateListener();
        for(Worship w: worshipList){
            w.setDate(dateListener.searchForGoodDay(w.getWeekid(),1));
        }
        Collections.sort(worshipList);
        setContentView(R.layout.simpleworship);
        TextView textViewDate = (TextView) findViewById(R.id.date);
        TextView textViewCity = (TextView) findViewById(R.id.churchCity2);
        TextView textViewWeekkComment = (TextView) findViewById(R.id.weekcommnet2);
        TextView textViewComment = (TextView) findViewById(R.id.worshipcomment);
        textViewDate.setText(worship.getDate() + " " + worship.getTermin());
        textViewCity.setText(selectedChurch.getCity() + " " + selectedChurch.getAddress());
        textViewWeekkComment.setText(week);
        textViewComment.setText(worship.getComment());
    }
}
