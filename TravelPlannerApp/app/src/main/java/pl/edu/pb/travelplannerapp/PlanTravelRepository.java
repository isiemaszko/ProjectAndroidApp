package pl.edu.pb.travelplannerapp;
        import android.app.Application;
        import androidx.lifecycle.LiveData;
        import java.util.List;
public class PlanTravelRepository {

    private PlanTravelDao plannerDao;
    private LiveData<List<PlanTravel>> plans;

    PlanTravelRepository(Application application){
        PlanTravelDatabase database= PlanTravelDatabase.getDatabase(application);
        plannerDao=database.plannerDao();
        plans=plannerDao.findAll();
    }
    LiveData<List<PlanTravel>> findAllPlans(){
        return plans;
    }
    void insert (PlanTravel travel){
        PlanTravelDatabase.databaseWriteExecutor.execute(()->{
            plannerDao.insert(travel);
        });
    }
    void update(PlanTravel travel){
        PlanTravelDatabase.databaseWriteExecutor.execute(()->{
            plannerDao.update(travel);
        });
    }
    void delete(PlanTravel travel){
        PlanTravelDatabase.databaseWriteExecutor.execute(()->{
            plannerDao.delete(travel);
        });
    }
}