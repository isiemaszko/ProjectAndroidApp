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
public interface PlannerDao {

    @Insert(onConflict= OnConflictStrategy.REPLACE)
    void insert(Planner travel);

    @Update
    public void update(Planner plan);

    @Delete
    public void delete(Planner plan);

    @Query("DELETE FROM planner")
    public void deleteALl();

    @Query("SELECT * FROM planner  ORDER BY name")
    public LiveData<List<Planner>> findAll();

    @Query("SELECT * FROM planner WHERE name LIKE :name")
    public List<Planner> findTravelWithName(String name);
}
