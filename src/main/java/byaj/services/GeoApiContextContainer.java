package byaj.services;


import com.google.maps.GeoApiContext;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

/**
 * Created by raya on 7/18/17.
 */
@Service
public class GeoApiContextContainer {

        private static String apiKey="AIzaSyCk-6KT-rNBJlpw2BibE7P3XftP8RMb7Ac";

        private GeoApiContext context;


        public GeoApiContextContainer()
        {
            context=new GeoApiContext().setApiKey(apiKey);

        }

        public GeoApiContext getContext() {
            return context;
        }

        public void setContext(GeoApiContext context) {
            this.context = context;
        }
    }
