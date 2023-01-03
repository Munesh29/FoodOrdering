import java.io.Serializable;
import java.time.LocalTime;
public class HotelTime implements Serializable {
    private PartOfDay timeOfDay;
    private LocalTime from;
    private LocalTime to;
    HotelTime(PartOfDay timeOfDay, LocalTime from, LocalTime to ){
        this.timeOfDay=timeOfDay;
        this.from=from;
        this.to=to;
    }
    void setDay(PartOfDay timeOfDay){
        this.timeOfDay=timeOfDay;
    }
    void setFrom(LocalTime from){
        this.from=from;
    }
    void setTo(LocalTime to){
        this.to=to;
    }
    PartOfDay getDay(){
        return timeOfDay;
    }
    LocalTime getFrom(){
        return from;
    }
    LocalTime getTo(){
        return to;
    }
}
