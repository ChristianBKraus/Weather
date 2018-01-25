package jupiterpa.weather.domain.model;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface DaylightRepo extends MongoRepository<Daylight,Long>{
	public List<Daylight> findAll();
}
