package pl.edu.pb.travelplannerapp;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class TravelRepository {
  private TravelDao travelDao;
  private LiveData<List<Travel>> travels;

  TravelRepository(Application application){
    TravelDatabase database=TravelDatabase.getDatabase(application);
    travelDao=database.travelDao();
    travels=travelDao.findAll();
  }

  LiveData<List<Travel>> findAllBooks(){
    return travels;
  }

  void insert (Travel travel){
    TravelDatabase.databaseWriteExecutor.execute(()->{
      travelDao.insert(travel);
    });
  }

  void update(Travel travel){
    TravelDatabase.databaseWriteExecutor.execute(()->{
      travelDao.update(travel);
    });
  }

  void delete(Travel travel){
    TravelDatabase.databaseWriteExecutor.execute(()->{
      travelDao.delete(travel);
    });
  }
}
