package jupiterpa.weather.controller;

import java.util.Arrays;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class WeatherAOP
{
	
    private static final Marker TECHNICAL = MarkerFactory.getMarker("TECHNICAL");
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Around("execution(* jupiterpa.weather.controller.WeatherController.*(..))")
	public Object log(ProceedingJoinPoint joinPoint) throws Throwable {

        logger.info(TECHNICAL, " {} ({})", joinPoint.getSignature().toString(),  Arrays.toString(joinPoint.getArgs()));

		try {
			Object result = joinPoint.proceed();
			logger.info(TECHNICAL, " -> Result: {}", result.toString());
			return result;
		} catch (Throwable e) {
			logger.error(TECHNICAL, "  => " + e ); 
			e.printStackTrace();
			throw e;
		}
	}
}