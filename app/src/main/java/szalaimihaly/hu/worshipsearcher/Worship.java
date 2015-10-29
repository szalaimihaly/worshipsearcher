package szalaimihaly.hu.worshipsearcher;

/**
 * Created by Mihaly on 2015.04.24..
 */
public class Worship {
    private int worshipid;
    private int churchid;
    private String termin;
    private int weekid;
    private String comment;
    private String date;


    public Worship(int worshipid, int churchid, String termin, int weekid, String comment) {
        this.worshipid=worshipid;
        this.churchid = churchid;
        this.termin = termin;
        this.weekid = weekid;
        this.comment = comment;
    }

    /*
    public Worship(int churchid, String termin, int weekid, String comment, String date) {
        this.churchid = churchid;
        this.termin = termin;
        this.weekid = weekid;
        this.comment = comment;
        this.date = date;
    }
    */

    @Override
    public String toString() {
        return "Worship{" +
                "worshipid=" + worshipid +
                ", churchid=" + churchid +
                ", termin='" + termin + '\'' +
                ", weekid=" + weekid +
                ", comment='" + comment + '\'' +
                ", date='" + date + '\'' +
                '}';
    }

    public int getChurchid() {
        return churchid;
    }

    public int getWorshipid() {
        return worshipid;
    }

    public String getTermin() {
        return termin;
    }

    public int getWeekid() {
        return weekid;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment){
        this.comment=comment;
    }

    public String getDate() {
        return date;
    }

    public void setTermin(String termin){
        this.termin=termin;
    }



}
