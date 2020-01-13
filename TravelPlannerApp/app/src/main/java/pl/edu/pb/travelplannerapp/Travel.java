package pl.edu.pb.travelplannerapp;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Getter
@Setter
@Entity (tableName = "travel")
public class Travel {

  @PrimaryKey(autoGenerate = true)
  private int id;
  private String name;
  private String place;
  private String cal1;
  private String cal2;

  public void setId(int id) {
    this.id = id;
  }

  public int getId() {
    return id;
  }


  public Travel(String name, String place, String cal1, String cal2) {
    this.name=name;
    this.place=place;
    this.cal1=cal1;
    this.cal2=cal2;
  }

  public String getPlace() {
    return place;

  }

  public void setPlace(String place) {
    this.place = place;
  }

  public String getName(){
    return name;
  }
  public void setName(String name){
    this.name= name;
  }

  public String getCal1() {
    return cal1;
  }


  public void setCal1(String cel) {
    this.cal1 = cel;
  }

  public String getCal2() {
    return cal2;
  }


  public void setCal2(String cel) {
    this.cal2 = cel;
  }


}
