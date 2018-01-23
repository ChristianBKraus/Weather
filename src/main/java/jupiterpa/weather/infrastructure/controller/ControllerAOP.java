package jupiterpa.weather.infrastructure.controller;

import java.util.Arrays;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import jupiterpa.weather.infrastructure.client.HttpContext;

@Aspect
@Component
public class ControllerAOP
{
	
    private static final Marker TECHNICAL = MarkerFactory.getMarker("TECHNICAL");
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Around("execution(* jupiterpa.*.intf.controller.*.*(..))")
	public Object log(ProceedingJoinPoint joinPoint) throws Throwable {

        MDC.put("endpoint", joinPoint.getSignature().toString() );
        logger.info(TECHNICAL, " Service {} ({})", joinPoint.getSignature().toString(),  Arrays.toString(joinPoint.getArgs()));
        
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
        	User user = (User) auth.getPrincipal(); 
        	logger.info(TECHNICAL, " User {} ({})", user.getUsername(), user.getPassword());
        	
        	HttpContext.setUser(user);
        	MDC.put("user", user.getUsername());
        }
        
		try {
			Object result = joinPoint.proceed();
			if (result != null) {
				logger.info(TECHNICAL, " -> Result: {}", result.toString());
			} else {
				logger.info(TECHNICAL, " -> Done");
			}
			return result;
		} catch (Throwable e) {
			logger.error(TECHNICAL, "  => " + e ); 
			e.printStackTrace();
			throw e;
		}
	}
}