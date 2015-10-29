package szalaimihaly.hu.worshipsearcher;

/**
 * Created by Mihaly on 2015.04.24..
 */


import android.app.ListActivity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;


import java.util.ArrayList;

public class WorshipListActivity extends ListActivity {

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        MainActivity.getDbl().open();
        ArrayList<Worship> worshipList =
                MainActivity.getDbl().getWorshipByChurch(bundle.getInt("churchid"));
        WorshipAdapter worshipAdapter =
                new WorshipAdapter(getApplicationContext(),worshipList);
        setListAdapter(worshipAdapter);
        MainActivity.getDbl().close();
        getListView().setBackgroundColor(Color.rgb(58, 139, 189));
    }

}
