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
    public void onOpen(@Nullable SupportSQLiteDatabase db){
      super.onOpen(db);
      databaseWriteExecutor.execute(()->{
        TravelDao dao=INSTANCE.travelDao();

        Travel travel=new Travel("Wakacje Mikołajka","Mikołajki","00\00\00","00\00\00");
        dao.insert(travel);
        Travel travel1=new Travel("Naznaczona","Kristin Cast","00\00\00","00\00\00");
        dao.insert(travel1);
        Travel travel2=new Travel("Igrzyska Smierci TOM 1","Suzanne Collins","00\00\00","00\00\00");
        dao.insert(travel2);
        Travel travel3=new Travel("W pierścieniu ognia TOM 2","Suzanne Collins","00\00\00","00\00\00");
        dao.insert(travel3);

      });
    }
  };

}
