package jupiterpa.weather.infrastructure.aop;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Filter {
	@Bean
	public FilterRegistrationBean registerFilter() {
		FilterRegistrationBean reg = new FilterRegistrationBean();
		reg.setFilter(someFilter());
		reg.addUrlPatterns("*");
		reg.addInitParameter(CorrelationID.CORRELATION_ID, "Anonymous");
		reg.setName("CorrelationFilter");
		reg.setOrder(1);
		return reg;
	}
	
	private javax.servlet.Filter someFilter() {
		return new CorrelationFilter();
	}
	
	public class CorrelationFilter implements javax.servlet.Filter {
	    private final Marker TECHNICAL = MarkerFactory.getMarker("TECHNICAL");
		private final Logger logger = LoggerFactory.getLogger(this.getClass());

		@Override
		public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
				throws IOException, ServletException {
			HttpServletRequest request = (HttpServletRequest) req; 

			String id = request.getHeader(CorrelationID.CORRELATION_ID);
			if (id == "" || id == null) {
				id = request.getRequestURI();
			}
			MDC.put(CorrelationID.CORRELATION_ID, id );
			CorrelationID.set(id);
			logger.info(TECHNICAL,"Correlation ID: {}", id);
			
			chain.doFilter(request, res);
		}

		@Override
		public void init(FilterConfig arg0) throws ServletException { }
		@Override
		public void destroy() { }		
	}
}
