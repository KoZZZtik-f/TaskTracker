package tasktracker.controller;

import tasktracker.exceptions.AddTaskException;
import tasktracker.model.Epic;
import tasktracker.model.Task;

import java.util.*;

public class InMemoryTaskManager implements TaskManager {

    protected HistoryManager historyManager = Managers.getDefaultHistory();
    protected Map<Integer, Task> tasks;
    protected Set<Task> treeSetTasks;
    private int currentId;
//    private HashMap<Integer, Boolean> intervals;

    public InMemoryTaskManager() {
        tasks = new HashMap<>(); // Type Of Map - HashMap
        treeSetTasks = new TreeSet<>(new Comparator<Task>() {
            @Override
            public int compare(Task o1, Task o2) {
                if (o1.getStartTime().isEmpty() && o2.getStartTime().isEmpty()) {
                    return 1;
                } else if (o1.getStartTime().isPresent() && o2.getStartTime().isEmpty()) {
                    return 1;
                } else if (o1.getStartTime().isEmpty() && o2.getStartTime().isPresent()) {
                    return -1;
                } else if (o1.getStartTime().isPresent() && o2.getStartTime().isPresent()) {
                    return o1.getStartTime().get().isAfter(o2.getStartTime().get()) ? 1 : -1;
                }
                return 0;
            }
        });

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
    public List<Task> getPrioritizedTask() {
        return new ArrayList<>(treeSetTasks);
    }

    @Override
    public void addTask(int id, Task task) throws AddTaskException {
        if (tasks.containsKey(id)) {
            throw new AddTaskException("Задача с таким id уже существует");
        }
        task.setId(id);
        addTaskPutInMaps(task);
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
        addTaskPutInMaps(task);
    }

    private void addTaskPutInMaps(Task task) {
        tasks.put(task.getId(), task);
        treeSetTasks.add(task);
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
        historyManager.removeFromHistory(id);
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

    protected boolean doesTaskIntersect(Task task1, Task task2) {
        if (task1.getStartTime().isEmpty() || task2.getStartTime().isEmpty()) {
            return false;
        }
        if (task1.getId() == task2.getId()) {
            return false;
        }
        if (task1.getStartTime().get().isAfter(task2.getEndTime().get())
            || task1.getEndTime().get().isBefore(task2.getStartTime().get())) {
            return false;
        }
        return true;
    }

    protected int incCurrentId() {
        return ++currentId;
    }
}
