package tasktracker.controller;

import tasktracker.model.Epic;
import tasktracker.model.Task;

import java.util.List;

public interface TaskManager {

    // 2
    List<Task> getAllTasks(); // 2.1

    void deleteAllTasks(); // 2.2

    Task getTask(int id); // 2.3

    List<Task> getPrioritizedTask();

    void addTask(int id, Task task);

    void addTask(Task task);

    void clearAll();

    void updateTask(int id, Task task); // 2.5

    void deleteTask(int id); // 2.6

    // 3
    List<Task> getEpicSubtasks(Epic epic);

    //history
    List<Integer> history();

}
