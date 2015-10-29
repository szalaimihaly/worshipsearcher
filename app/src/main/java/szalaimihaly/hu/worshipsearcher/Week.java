package szalaimihaly.hu.worshipsearcher;

/**
 * Created by Mihaly on 2015.04.24..
 */
public class Week {
    private int weekid;
    private String type;
    public Week(int id, String type) {
        this.weekid = id;
        this.type = type;
    }
    @Override
    public String toString() {
        return "Week [id=" + weekid + ", type=" + type + "]";
    }
    public int getWeekid() {
        return weekid;
    }
    public String getType() {
        return type;
    }




}
