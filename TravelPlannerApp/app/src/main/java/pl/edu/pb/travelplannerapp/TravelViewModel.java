package pl.edu.pb.travelplannerapp;

import android.app.Application;

import androidx.annotation.Nullable;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class TravelViewModel extends AndroidViewModel {

  private TravelRepository travelRepository;
  private LiveData<List<Travel>> travels;

  public TravelViewModel(@Nullable Application application){
    super(application);
    travelRepository=new TravelRepository(application);
    travels=travelRepository.findAllBooks();
  }

  public LiveData<List<Travel>> findAll(){
    return travels;
  }

  public void insert(Travel travel){
    travelRepository.insert(travel);
  }

  public void update(Travel travel){
    travelRepository.update(travel);
  }

  public void delete(Travel travel){
    travelRepository.delete(travel);
  }

}
