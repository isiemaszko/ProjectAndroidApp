package pl.edu.pb.travelplannerapp;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Planner.class}, version = 2,exportSchema = false)
public abstract class PlannerDatabase extends RoomDatabase {
    public abstract PlannerDao plannerDao();

    private static volatile PlannerDatabase INSTANCE;
    public static final int NUMBER_OF_THREADS=4;
    static final ExecutorService databaseWriteExecutor= Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    static PlannerDatabase getDatabase(final Context context){
        if(INSTANCE==null){
            synchronized (PlannerDatabase.class){
                if(INSTANCE==null){
                    INSTANCE= Room.databaseBuilder(context.getApplicationContext(),
                            PlannerDatabase.class, "plan_db")
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
                PlannerDao dao=INSTANCE.plannerDao();
                //    dao.deleteALl();//usuwanie wszytskiego

                Planner plan=new Planner(3,"3","12/12/2012","12:30");
                dao.insert(plan);
                Planner plan2=new Planner(3,"3","12/12/2012","12:30");
                dao.insert(plan2);
                Planner plan3=new Planner(2,"2","12/12/2012","12:30");
                dao.insert(plan3);
                Planner plan4=new Planner(2,"2","12/12/2012","12:30");
                dao.insert(plan4);
                Planner plan5=new Planner(1,"1","12/12/2012","12:30");
                dao.insert(plan5);
                Planner plan6=new Planner(1,"1","12/12/2012","12:30");
                dao.insert(plan6);

            });
        }
    };

}
