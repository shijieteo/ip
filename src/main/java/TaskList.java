import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.IntStream;

public class TaskList extends ArrayList<Task> {

    TaskList() {
        super();
    }

    TaskList(Collection<Task> collection) {
        super(collection);
    }

    @Override
    public String toString(){
        String accumulatedString = IntStream.range(0, this.size()).boxed()
                .map(x -> String.format("\t%d. %s", x + 1, this.get(x)))
                .reduce("", (x,y) -> x + y + "\n");

        return "\t" + accumulatedString.trim();
    }
}
