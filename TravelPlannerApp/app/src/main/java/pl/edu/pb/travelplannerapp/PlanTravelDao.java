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
public interface PlanTravelDao {
    @Insert(onConflict= OnConflictStrategy.REPLACE)
    void insert(PlanTravel travel);
    @Update
    public void update(PlanTravel plan);
    @Delete
    public void delete(PlanTravel plan);
    @Query("DELETE FROM planTravel")
    public void deleteALl();
    @Query("SELECT * FROM planTravel  ORDER BY name")
    public LiveData<List<PlanTravel>> findAll();
    @Query("SELECT * FROM planTravel WHERE name LIKE :name")
    public List<PlanTravel> findTravelWithName(String name);
}