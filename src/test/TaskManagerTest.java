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

    // void addTask(int id, Task task);
    @Test
    public void shouldAddTaskById() {
        Task task1 = new Task("Test1", "desc1", Status.NEW);

        taskManager.addTask(10, task1);

        // Check 1
        ArrayList<Task> expected1 = new ArrayList<>(List.of(task1));
        ArrayList<Task> res1 = (ArrayList<Task>) taskManager.getAllTasks();
        Assertions.assertEquals(expected1.getFirst(), res1.getFirst());

        // Check 2
        int expectedId1 = taskManager.getTask(task1.getId()).getId();
        Assertions.assertEquals(expectedId1, task1.getId());
    }

    //  void addTask(Task task);
    @Test
    public void shouldAddTask() {
        Task task1 = new Task("Test1", "desc1", Status.NEW);

        taskManager.addTask(task1);

        // Check 1
        ArrayList<Task> expected1 = new ArrayList<>(List.of(task1));
        ArrayList<Task> res1 = (ArrayList<Task>) taskManager.getAllTasks();
        Assertions.assertEquals(expected1.getFirst(), res1.getFirst());

        // Check 2
        int expectedId1 = 1;
        Assertions.assertEquals(expectedId1, task1.getId());
    }

    // Task getTask(int id);
    @Test
    public void shouldGetAddedTask() {
        Task task1 = new Task("Test1", "desc1", Status.NEW);

        taskManager.addTask(task1);

        Task expected = task1;
        Task res = taskManager.getTask(task1.getId());
        Assertions.assertEquals(expected, res);
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
