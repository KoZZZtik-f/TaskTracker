package tasktracker.converter;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import tasktracker.model.Status;
import tasktracker.model.Task;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;

public class TaskAdapter extends TypeAdapter<Task> {

    @Override
    public void write(JsonWriter out, Task task) throws IOException {
        out.beginObject();
        out.name("name").value(task.getName());
        out.name("id").value(task.getId());
        out.name("description").value(task.getDescription().orElse(null));
        out.name("status").value(task.getStatus().name());
        out.name("duration").value(task.getDuration().toMinutes());
        out.name("startTime").value(task.getStartTime().map(LocalDateTime::toString).orElse(null));
        out.endObject();
    }

    @Override
    public Task read(JsonReader in) throws IOException {
        return null;
    }
}
