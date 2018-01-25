package jupiterpa.weather.domain.model;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface WeatherRepo extends MongoRepository<Weather,Long>{
	public List<Weather> findAll();
}
