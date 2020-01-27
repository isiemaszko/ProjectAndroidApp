package pl.edu.pb.travelplannerapp;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Travel.class}, version = 1,exportSchema = false)
public abstract class TravelDatabase extends RoomDatabase {
  public abstract TravelDao travelDao();

  private static volatile TravelDatabase INSTANCE;
  public static final int NUMBER_OF_THREADS=4;
  static final ExecutorService databaseWriteExecutor= Executors.newFixedThreadPool(NUMBER_OF_THREADS);

  static TravelDatabase getDatabase(final Context context){
    if(INSTANCE==null){
      synchronized (TravelDatabase.class){
        if(INSTANCE==null){
          INSTANCE= Room.databaseBuilder(context.getApplicationContext(),
                  TravelDatabase.class, "travel_db")
                  .addCallback(sRoomDatabaseCallback)
                  .build();
        }
      }
    }
    return INSTANCE;
  }

  private static RoomDatabase.Callback sRoomDatabaseCallback=new RoomDatabase.Callback(){
    @Override
    public void onCreate(@Nullable SupportSQLiteDatabase db){
      super.onCreate(db);
      databaseWriteExecutor.execute(()->{
        TravelDao dao=INSTANCE.travelDao();
    //    dao.deleteALl();//usuwanie wszytskiego

        Travel travel=new Travel("Wakacje we Włoszek","Włochy","12\07\2018","20\07\2018");
        dao.insert(travel);
        Travel travel1=new Travel("Majówka","Poznań","01\05\2019","03\05\2019");
        dao.insert(travel1);
        Travel travel2=new Travel("Weekend nad morzem","Gdańsk","12\09\2019","14\09\2019");
        dao.insert(travel2);
        Travel travel3=new Travel("Sylwester w stolicy","Warszawa","31\12\2019","01\01\2020");
        dao.insert(travel3);

      });
    }
  };

}
