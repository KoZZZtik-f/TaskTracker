package tasktracker.taskmanager;

import tasktracker.exceptions.ManagerSaveException;
import tasktracker.model.*;

import java.util.Optional;

public class FileBackedTaskManager extends InMemoryTaskManager implements TaskManager {


    @Override
    public void addTask(int id, Task task) {
        super.addTask(id, task);

    }

    @Override
    public void deleteAllTasks() {
        super.deleteAllTasks();
    }

    @Override
    public void addTask(Task task) {
        super.addTask(task);
//        save();
    }

    @Override
    public void deleteTask(int id) {
        super.deleteTask(id);
    }

    @Override
    public void updateTask(int id, Task task) {
        super.updateTask(id, task);
    }

    private void save(Task task) {

    }

    private String taskToString(Task task) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(task.getId()).append(",");
        stringBuilder.append(task.getClass().getSimpleName()).append(",");
        stringBuilder.append(task.getName()).append(",");
        stringBuilder.append(task.getStatus()).append(",");
        stringBuilder.append(task.getDescription().orElse(null)).append(",");
        if (task instanceof Subtask) {
            var subtask = (Subtask) task;
            stringBuilder.append(subtask.getEpic().getId());
        }


        return stringBuilder.toString();
    }

    private Task fromString(String s) throws IllegalArgumentException{
        String[] parameters = s.split(",");

        int id = Integer.parseInt(parameters[0]);//
        var type = Type.valueOf(parameters[1]);//
        var name = parameters[2];//
        var status = Status.valueOf(parameters[3]);//
        var description = parameters[4] == "null" ? null : parameters[4];//
        Optional<Integer> subtaskEpicId = Optional.ofNullable(Integer.valueOf(parameters[5]));//

        switch (type) {
            case TASK -> {
                Task task = new Task(name, description, status);
                task.setId(id);
                return task;

            }

            case EPIC -> {
                Epic epic = new Epic(name, description, status);
                epic.setId(id);
                return epic;
            }

            case SUBTASK -> {
                Epic epic = (Epic) tasks.get(subtaskEpicId);
                var subtask = new Subtask(name, description, status, epic);

                epic.addSubtask(subtask);
                return subtask;
            }
        }
        throw new IllegalArgumentException();
    }

    private void addFromString(String s) {
        String[] parameters = s.split(",");

        int id = Integer.parseInt(parameters[0]);//
        var type = Type.valueOf(parameters[1]);//
        var name = parameters[2];//
        var status = Status.valueOf(parameters[3]);//
        var description = parameters[4] == "null" ? null : parameters[4];//
        Optional<Integer> subtaskEpicId = Optional.ofNullable(Integer.valueOf(parameters[5]));//

        switch (type) {
            case TASK -> {
                Task task = new Task(name, description, status);
                task.setId(id);
                addTask(task.getId(), task);
            }

            case EPIC -> {
                Epic epic = new Epic(name, description, status);
                epic.setId(id);
                addTask(epic.getId(), epic);
            }

            case SUBTASK -> {
                Epic epic = (Epic) getTask(subtaskEpicId.get());
                var subtask = new Subtask(name, description, status, epic);

                epic.addSubtask(subtask);
                addTask(subtask.getId(), subtask);
            }
        }

    }
}
