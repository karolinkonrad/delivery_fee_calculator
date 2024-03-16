package trial.task.delivery_fee_calculator.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import trial.task.delivery_fee_calculator.entity.Weather;
import trial.task.delivery_fee_calculator.repository.WeatherRepository;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@Service
public class WeatherService {
    @Autowired
    private WeatherRepository weatherRepository;

    /**
     * Request the most resent weather information in the given city.
     * @param name name of the city
     * @return Weather entity
     */
    public Weather getLatestWeatherByName(String name){
        return weatherRepository.findByNameStartingWithOrderByTimestampDesc(name).get(0);
    }

    /**
     * Save weather information.
     */
    public Weather saveWeather(Weather weather) {
        return weatherRepository.save(weather);
    }


    ArrayList<String> STATIONS = new ArrayList<>(List.of(new String[]{"Tallinn-Harku", "Tartu-Tõravere", "Pärnu"}));

    /**
     * Request and store weather data from the weather portal of the Estonian Environment Agency
     */
    //@EventListener(ApplicationReadyEvent.class)
    @Scheduled(cron = "${weather.request.frequency}")
    public void weatherRequest() throws XMLStreamException, IOException {
        URL url = new URL("https://www.ilmateenistus.ee/ilma_andmed/xml/observations.php");
        XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
        XMLEventReader reader = xmlInputFactory.createXMLEventReader(url.openStream());

        reader.nextEvent();
        XMLEvent nextEvent = reader.nextEvent();
        StartElement startElement = nextEvent.asStartElement();
        Attribute attribute = startElement.getAttributeByName(new QName("timestamp"));
        long timestamp = Long.parseLong(attribute.getValue());
        Weather weather = null;

        while (reader.hasNext()) {
            nextEvent = reader.nextEvent();

            if (nextEvent.isStartElement()) {
                startElement = nextEvent.asStartElement();

                if (startElement.getName().getLocalPart().equals("name")) {
                    nextEvent = reader.nextEvent();
                    String name = nextEvent.asCharacters().getData();
                    if (STATIONS.contains(name)) {
                        weather = new Weather(timestamp);
                        weather.setName(name.toLowerCase());
                    }
                }
                if (weather != null) {
                    switch (startElement.getName().getLocalPart()) {
                        case "wmocode":
                            nextEvent = reader.nextEvent();
                            weather.setWmocode(Integer.parseInt(nextEvent.asCharacters().getData()));
                            break;
                        case "airtemperature":
                            nextEvent = reader.nextEvent();
                            weather.setAirtemperature(Float.parseFloat(nextEvent.asCharacters().getData()));
                            break;
                        case "windspeed":
                            nextEvent = reader.nextEvent();
                            weather.setWindspeed(Float.parseFloat(nextEvent.asCharacters().getData()));
                            break;
                        case "phenomenon":
                            nextEvent = reader.nextEvent();
                            if (nextEvent.isStartElement())
                                weather.setPhenomenon(nextEvent.asCharacters().getData());
                            break;
                    }
                }

            }
            else if (nextEvent.isEndElement()) {
                EndElement endElement = nextEvent.asEndElement();
                if (endElement.getName().getLocalPart().equals("station") && weather != null) {
                    saveWeather(weather);
                    weather = null;
                }
            }
        }
    }

}
