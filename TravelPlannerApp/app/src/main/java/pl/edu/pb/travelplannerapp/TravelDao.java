package pl.edu.pb.travelplannerapp;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface TravelDao {

    @Insert(onConflict= OnConflictStrategy.REPLACE)
    void insert(Travel travel);

    @Update
    public void update(Travel travel);

    @Delete
    public void delete(Travel travel);

    @Query("DELETE FROM travel")
    public void deleteALl();

    @Query("SELECT * FROM travel ORDER BY name")
    public LiveData<List<Travel>> findAll();

    @Query("SELECT * FROM travel WHERE name LIKE :name")
    public List<Travel> findTravelWithName(String name);
}
