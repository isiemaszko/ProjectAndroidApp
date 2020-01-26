package pl.edu.pb.travelplannerapp;
        import android.app.Application;
        import androidx.annotation.Nullable;
        import androidx.lifecycle.AndroidViewModel;
        import androidx.lifecycle.LiveData;
        import java.util.List;
public class PlanTravelViewModel extends AndroidViewModel {
    private PlanTravelRepository travelRepository;
    private LiveData<List<PlanTravel>> travels;
    private int id;
    public PlanTravelViewModel(@Nullable Application application){
        super(application);
        travelRepository=new PlanTravelRepository(application);
        travels=travelRepository.findAllPlans();
    }
    public LiveData<List<PlanTravel>> findAll(){
        return travels;
    }
    public void insert(PlanTravel travel){
        travelRepository.insert(travel);
    }
    public void update(PlanTravel travel){
        travelRepository.update(travel);
    }
    public void delete(PlanTravel travel){
        travelRepository.delete(travel);
    }
}

