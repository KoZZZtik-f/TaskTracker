package tasktracker.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import tasktracker.converter.TaskAdapter;
import tasktracker.exceptions.AddTaskException;
import tasktracker.model.Task;

import java.util.Optional;

public class HTTPTaskManager extends FileBackedTaskManager implements TaskManager {

    String uriStr;
    KVTaskClient taskClient;
    Gson gson;

    public HTTPTaskManager(String uriStr) {
        this.uriStr = uriStr;
        taskClient = new KVTaskClient(uriStr);
        gson = new GsonBuilder()
                .registerTypeAdapter(Task.class, new TaskAdapter())
                .create();
    }

    @Override
    public void addTask(int id, Task task) {
        if (tasks.containsKey(id)) {
            throw new AddTaskException("Задача с таким id уже существует");
        }
        task.setId(id);

        POSTTask(task);
    }

    @Override
    public void addTask(Task task) {
        Optional<Task> intersectionTask = getPrioritizedTask().stream()
                .filter(o -> doesTaskIntersect(o, task))
                .findFirst();

        if (intersectionTask.isPresent()) {
            throw new AddTaskException("Пересечение по времени с задачей ID = " + intersectionTask.get().getId());
        }

        task.setId(incCurrentId());

        POSTTask(task);
    }

    @Override
    public Task getTask(int index) {
        return GETTASK(index);
    }

    @Override
    public void deleteTask(int id) {
        super.deleteTask(id);
    }

    @Override
    public void updateTask(int id, Task task) {
        super.updateTask(id, task);
    }

    @Override
    protected void save() {
        super.save();
        String jsonTasks = gson.toJson(super.getAllTasks());
        taskClient.put("tasks", jsonTasks);

        String jsonHistory = gson.toJson(super.history());
        taskClient.put("history", jsonHistory);
    }

    private void POSTTask(Task task) {
        String key = String.valueOf(task.getId());
        String json = gson.toJson(task);
        taskClient.put(key, json);
    }

    private Task GETTASK(int id) {
        String key = String.valueOf(id);
        String json = taskClient.load(key);

        System.out.println(json);
        Task task = gson.fromJson(json, Task.class);

        return task;
    }
}
