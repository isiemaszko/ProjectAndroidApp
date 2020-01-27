package pl.edu.pb.travelplannerapp;
        import android.content.Context;
        import androidx.annotation.Nullable;
        import androidx.room.Database;
        import androidx.room.Room;
        import androidx.room.RoomDatabase;
        import androidx.sqlite.db.SupportSQLiteDatabase;
        import java.util.concurrent.ExecutorService;
        import java.util.concurrent.Executors;

@Database(entities = {PlanTravel.class}, version = 2,exportSchema = false)
public abstract class PlanTravelDatabase extends RoomDatabase {

    public abstract PlanTravelDao plannerDao();
    private static volatile PlanTravelDatabase INSTANCE;
    public static final int NUMBER_OF_THREADS=4;
    static final ExecutorService databaseWriteExecutor= Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    static PlanTravelDatabase getDatabase(final Context context){
        if(INSTANCE==null){
            synchronized (PlanTravelDatabase.class){
                if(INSTANCE==null){
                    INSTANCE= Room.databaseBuilder(context.getApplicationContext(),
                            PlanTravelDatabase.class, "plan_db")
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
                PlanTravelDao dao=INSTANCE.plannerDao();
                //    dao.deleteALl();//usuwanie wszytskiego
                PlanTravel plan=new PlanTravel(1,"stolica","12/12/2012","12:30",null);
                dao.insert(plan);
                PlanTravel plan2=new PlanTravel(2,"starówka","12/12/2012","12:30",null);
                dao.insert(plan2);
                PlanTravel plan3=new PlanTravel(3,"opalanie","12/12/2012","12:30",null);
                dao.insert(plan3);
                PlanTravel plan4=new PlanTravel(4,"koncerty","12/12/2012","12:30",null);
                dao.insert(plan4);
            });
        }
    };
}