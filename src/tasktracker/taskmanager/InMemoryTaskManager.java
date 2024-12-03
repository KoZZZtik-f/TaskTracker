package tasktracker.taskmanager;

import tasktracker.model.Epic;
import tasktracker.model.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryTaskManager implements TaskManager {

    HistoryManager historyManager = Managers.getDefaultHistory();
    Map<Integer, Task> tasks;
    private int currentId;

    public InMemoryTaskManager() {
        tasks = new HashMap<>();
    }

    @Override
    public List<Task> getAllTasks() {
        ArrayList<Task> allTasks = new ArrayList<>(tasks.values());
        return allTasks;
    }

    @Override
    public void deleteAllTasks() {
        tasks.clear();
    }

    @Override
    public Task getTask(int index) {
        var task = tasks.get(index);

        historyManager.addToHistory(task.getId());
        return task;
    }

    @Override
    public void addTask(int id, Task task) {
        task.setId(id);
        tasks.put(id, task);
    }

    @Override
    public void addTask(Task task) {
        try {
            tasks.put(task.getId(), task);
        } catch (Throwable throwable) {
            task.setId(incCurrentId());
        }
        tasks.put(task.getId(), task);
    }

    @Override
    public void clearAll() {
        tasks.clear();
        historyManager.clearHistory();
        currentId = 0;
    }

    @Override
    public void updateTask(int id, Task task) {
        task.setId(id);
        tasks.put(id, task);
    }

    @Override
    public void deleteTask(int id) {
        tasks.remove(id);
    }

    @Override
    public List<Task> getEpicSubtasks(Epic epic) {
        ArrayList<Task> epicSubtasks = new ArrayList<>(epic.getSubtasks());
        return epicSubtasks;
    }

    @Override
    public List<Integer> history() {
        return historyManager.getHistory();
    }

    private int incCurrentId() {
        return ++currentId;
    }
}
