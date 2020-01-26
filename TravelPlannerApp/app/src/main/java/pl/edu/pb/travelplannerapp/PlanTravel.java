package pl.edu.pb.travelplannerapp;
import android.graphics.Bitmap;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import java.util.Date;

@Getter
@Setter
@Entity(tableName = "planTravel")
public class PlanTravel {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private int idTravel;
    private String name;
    private String date;
    private String time;
    private String bitmap;

    public PlanTravel(int idTravel, String name, String date, String time, String bitmap){
        this.date=date;
        this.time=time;
        this.name=name;
        this.idTravel=idTravel;
        this.bitmap=bitmap;
    }
    public int getId() {
        return id;
    }
    public void setIdTravel(int idTravel) {
        this.idTravel = idTravel;
    }
    public int getIdTravel() {
        return idTravel;
    }
    public String getDate() {
        return date;
    }
    public String getName() {
        return name;
    }
    public void setDate(String date) {
        this.date = date;
    }
    public void setId(int id) {
        this.id = id;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getTime() {
        return time;
    }
    public void setTime(String time) {
        this.time = time;
    }
    public String getBitmap() {
        return bitmap;
    }
    public void setBitmap(String bitmap) {
        this.bitmap = bitmap;
    }
}