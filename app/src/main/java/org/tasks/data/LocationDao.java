package org.tasks.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import io.reactivex.Single;
import java.util.List;
import org.tasks.filters.LocationFilters;

@Dao
public interface LocationDao {

  @Query(
      "SELECT * FROM geofences INNER JOIN places ON geofences.place = places.uid WHERE geofence_id = :id LIMIT 1")
  Location getGeofence(Long id);

  @Query(
      "SELECT * FROM geofences INNER JOIN places ON geofences.place = places.uid WHERE task = :taskId ORDER BY name ASC LIMIT 1")
  Location getGeofences(long taskId);

  @Query(
      "SELECT geofences.*, places.* FROM geofences INNER JOIN places ON geofences.place = places.uid INNER JOIN tasks ON tasks._id = geofences.task WHERE tasks._id = :taskId AND tasks.deleted = 0 AND tasks.completed = 0")
  List<Location> getActiveGeofences(long taskId);

  @Query(
      "SELECT geofences.*, places.* FROM geofences INNER JOIN places ON geofences.place = places.uid INNER JOIN tasks ON tasks._id = geofences.task WHERE tasks.deleted = 0 AND tasks.completed = 0")
  List<Location> getActiveGeofences();

  @Query("SELECT COUNT(*) FROM geofences")
  Single<Integer> geofenceCount();

  @Delete
  void delete(Geofence location);

  @Delete
  void delete(Place place);

  @Insert
  long insert(Geofence location);

  @Insert(onConflict = OnConflictStrategy.IGNORE)
  long insert(Place place);

  @Update
  void update(Place place);

  @Update
  void update(Geofence geofence);

  @Query("SELECT * FROM places WHERE uid = :uid LIMIT 1")
  Place getByUid(String uid);

  @Query("SELECT * FROM geofences WHERE task = :taskId")
  List<Geofence> getGeofencesForTask(long taskId);

  @Query("SELECT * FROM geofences WHERE place = :uid")
  List<Geofence> getGeofencesByPlace(String uid);

  @Query("SELECT * FROM places")
  List<Place> getPlaces();

  @Query("SELECT * FROM places WHERE place_id = :id")
  Place getPlace(long id);

  @Query("SELECT * FROM places WHERE uid = :uid")
  Place getPlace(String uid);

  @Query(
      "SELECT places.*, IFNULL(COUNT(geofence_id),0) AS count FROM places LEFT OUTER JOIN geofences ON geofences.place = places.uid GROUP BY uid ORDER BY COUNT(geofence_id) DESC")
  LiveData<List<PlaceUsage>> getPlaceUsage();

  @Query("SELECT * FROM places WHERE latitude LIKE :latitude AND longitude LIKE :longitude")
  Place findPlace(String latitude, String longitude);

  @Query("SELECT places.*, COUNT(tasks._id) AS count FROM places "
      + " LEFT JOIN geofences ON geofences.place = places.uid "
      + " LEFT JOIN tasks ON geofences.task = tasks._id AND tasks.completed = 0 AND tasks.deleted = 0 AND tasks.hideUntil < :now"
      + " GROUP BY places.uid"
      + " ORDER BY count DESC, name COLLATE NOCASE ASC")
  List<LocationFilters> getPlaceFilters(long now);
}
