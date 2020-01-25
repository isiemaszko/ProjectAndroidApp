package pl.edu.pb.travelplannerapp;

import android.app.Application;

import androidx.annotation.Nullable;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class PlannerViewModel extends AndroidViewModel {
    private PlannerRepository travelRepository;
    private LiveData<List<Planner>> travels;
    private int id;

    public PlannerViewModel(@Nullable Application application){
        super(application);
        travelRepository=new PlannerRepository(application);
        travels=travelRepository.findAllPlans();
    }

    public LiveData<List<Planner>> findAll(){

        return travels;
    }

    public void insert(Planner travel){
        travelRepository.insert(travel);
    }

    public void update(Planner travel){
        travelRepository.update(travel);
    }

    public void delete(Planner travel){
        travelRepository.delete(travel);
    }
}
