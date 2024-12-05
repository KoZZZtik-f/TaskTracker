package test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import tasktracker.model.Epic;
import tasktracker.model.Status;
import tasktracker.model.Subtask;
import tasktracker.model.Task;
import tasktracker.taskmanager.TaskManager;

public abstract class TaskManagerTest<T extends TaskManager> {
    T taskManager;
    Task taskForTest;

    public TaskManagerTest(T taskManager) {
        this.taskManager = taskManager;
    }

    @BeforeEach
    public void beforeEach() {
        taskManager.clearAll();
        taskForTest = new Task("TaskForTest", "describe", Status.NEW);
    }

    // All the methods
    @Test
    public void shouldAddTask() {
        Object[] expected = new Task[10];

        for (int i = 0; i < 5; i++) {
            Task task = new Task("name" + i, "desc" + i, Status.NEW);
            taskManager.addTask(task);
            expected[i] = task;
        }
        for (int i = 0; i < 5; i++) {
            Task task = new Task("name" + i, "desc" + i, Status.NEW);
            taskManager.addTask(i + 10,task);
            expected[i+5] = task;
        }

        Object[] res = taskManager.getAllTasks().toArray();
        for (int i = 0; i < res.length; i++) {

        }

        Assertions.assertArrayEquals(expected, res);
    }

    // void deleteTask(int id);
    @Test
    public void shouldDeleteTask() {
        Task task = new Task("name", "desc", Status.NEW);
        Task task2 = new Task("name2", "desc2", Status.NEW);

        taskManager.addTask(task);
        taskManager.addTask(task2);
        taskManager.deleteTask(task.getId());

        Task[] expected = new Task[]{task2}; // empty
        Task[] res = taskManager.getAllTasks().toArray(new Task[0]);
        Assertions.assertArrayEquals(expected, res);
    }

    @Test
    public void shouldNotDeleteTaskWhenItsEmpty() {
        Task taskForDelete = new Task("name", "desc", Status.NEW);

        var oldTasks = taskManager.getAllTasks().toArray();
        taskManager.deleteTask(taskForDelete.getId());
        var newTasks = taskManager.getAllTasks().toArray();

        Assertions.assertArrayEquals(oldTasks, newTasks);
    }

    // void updateTask(int id, Task task);
    @Test
    public void shouldReturnUpdatedTaskWhenTaskManagerUpdatedIt() {
        Task taskForUpdate = new Task("ForUpd", "", Status.NEW);
        Task newTask = taskForTest;

        taskManager.addTask(taskForUpdate);
        taskManager.updateTask(taskForUpdate.getId(), newTask);

        Task[] expected = new Task[] {newTask};
        Task[] res = taskManager.getAllTasks().toArray(new Task[0]);

        Assertions.assertArrayEquals(expected, res);
    }

    // List<Integer> history()
    @Test
    public void shouldGetHistory() {
        Task task1 = new Task("name", "desc", Status.NEW);
        Task task2 = new Task("name", "desc", Status.NEW);
        Task task3 = new Task("name", "desc", Status.NEW);

        task1.setId(1);
        task2.setId(2);
        task3.setId(3);


        taskManager.addTask(task1);
        taskManager.addTask(task2);
        taskManager.addTask(task3);

        taskManager.getTask(2);
        taskManager.getTask(3);
        taskManager.getTask(1);

        Object[] expected = new Integer[] {1, 3, 2};
        Object[] res = taskManager.history().toArray();

        Assertions.assertArrayEquals(expected, res);
    }

    @Test
    public void shouldGetEmptyHistoryWhenTasksEmpty() {
        Object[] expected = new Integer[] {};
        Object[] res = taskManager.history().toArray();

        Assertions.assertArrayEquals(expected, res);
    }

}
