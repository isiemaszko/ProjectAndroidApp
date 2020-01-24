package pl.edu.pb.travelplannerapp;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class PlannerRepository {
    private PlannerDao plannerDao;
    private LiveData<List<Planner>> plans;

    PlannerRepository(Application application){
        PlannerDatabase database= PlannerDatabase.getDatabase(application);
        plannerDao=database.plannerDao();
        plans=plannerDao.findAll();
    }

    LiveData<List<Planner>> findAllPlans(){
        return plans;
    }

    void insert (Planner travel){
        PlannerDatabase.databaseWriteExecutor.execute(()->{
            plannerDao.insert(travel);
        });
    }

    void update(Planner travel){
        PlannerDatabase.databaseWriteExecutor.execute(()->{
            plannerDao.update(travel);
        });
    }

    void delete(Planner travel){
        PlannerDatabase.databaseWriteExecutor.execute(()->{
            plannerDao.delete(travel);
        });
    }
}
