package szalaimihaly.hu.worshipsearcher;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Mihaly on 2015.04.24..
 */
public class WorshipAdapter extends BaseAdapter {

    public final ArrayList<Worship> worshipList;

    public WorshipAdapter(final Context context, final ArrayList<Worship> worshipList) {
        this.worshipList = worshipList;
    }

    @Override
    public int getCount() {
        return worshipList.size();
    }

    @Override
    public Object getItem(int position) {
        return worshipList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Worship worship = worshipList.get(position);
        MainActivity.getDbl().open();
        Church church = MainActivity.getDbl().getChurchById(worship.getChurchid());
        String week = MainActivity.getDbl().getWeekById(worship.getWeekid());
        MainActivity.getDbl().close();
        LayoutInflater inflater = (LayoutInflater) parent.getContext().
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View viewitem = inflater.inflate(R.layout.worship,null);
        TextView textViewTermin = (TextView) viewitem.findViewById(R.id.termin);
        textViewTermin.setText(worship.getTermin());
        TextView textViewComment = (TextView) viewitem.findViewById(R.id.comment);
        textViewComment.setText(worship.getComment());
        TextView textViewCity = (TextView) viewitem.findViewById(R.id.churchcomment2);
        textViewCity.setText(church.getCity());
        TextView textViewHetMegjegyzes = (TextView) viewitem.findViewById(R.id.weekcommnet);
        textViewHetMegjegyzes.setText(week);
        return viewitem;
    }
}
