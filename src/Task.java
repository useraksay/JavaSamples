import java.util.Date;
import java.util.Map;
import java.util.TimerTask;
import java.util.UUID;

class Task extends TimerTask {
    private Map<Long, UUID> map;
    public Task(Map<Long, UUID> map){
        this.map = map;
    }
    public void run(){
        System.out.println("Run the timer :-");
        this.map.entrySet().stream().forEach(entry -> {
            Long key = entry.getKey();
            System.out.println("Duration " + ((new Date().getTime()) - (key + 5000)));
            if((new Date().getTime() > (key + 5000))){
                this.map.remove(key);
            }
        });
        System.out.println("Task class map size " + this.map.size());
    }
}