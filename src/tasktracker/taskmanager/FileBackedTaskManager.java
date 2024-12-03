package tasktracker.taskmanager;

import tasktracker.config.Config;
import tasktracker.exceptions.InvalidFileException;
import tasktracker.model.*;
import tasktracker.util.Validator;
//import tasktracker.util.SaveHelper;

import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class FileBackedTaskManager extends InMemoryTaskManager implements TaskManager {

    public FileBackedTaskManager() {
        loadFromFile(new File(Config.DATA_FILE_PATH));
    }

    @Override
    public void addTask(int id, Task task) {
        super.addTask(id, task);
        save();
    }

    @Override
    public void addTask(Task task) {
        super.addTask(task);
        save();
    }

    public void loadTask(Task task) {
        tasks.put(task.getId(), task);
    }

    @Override
    public Task getTask(int index) {
        var task = tasks.get(index);

        historyManager.addToHistory(task.getId());
//        save();
        return task;
    }

    @Override
    public void deleteAllTasks() {
        super.deleteAllTasks();
        save();
    }

    @Override
    public void deleteTask(int id) {
        super.deleteTask(id);
        save();
    }

    @Override
    public void updateTask(int id, Task task) {
        super.updateTask(id, task);
        save();
    }

    private void save() {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("id,type,name,status,description,epic");
        if (!getAllTasks().isEmpty()) {
            for (Task task : getAllTasks()) {
                if (!(task instanceof Subtask)) {
                    stringBuilder.append(toString(task));
                }

                if (task instanceof Epic) {
                    Epic epic = (Epic) task;
                    for (Subtask subtask : epic.getSubtasks()) {
                        try {
                            stringBuilder.append(toString(getTask(subtask.getId())));
                        } catch (Throwable throwable) {
                            //nothing
                        }
                    }
                }

            }
        }

        stringBuilder.append("\n\n");
        stringBuilder.append(toString(history()));

        try (FileWriter fileWriter = new FileWriter(new File(Config.DATA_FILE_PATH))) {
            fileWriter.write(stringBuilder.toString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String toString(Task task) {
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
        stringBuilder.append("\n");

        return stringBuilder.toString();
    }

    private String toString(List<Integer> history) {
        StringBuilder stringBuilder = new StringBuilder();
        for (Integer integer : history) {
            stringBuilder.append(integer).append(',');
        }

        if (stringBuilder.length() != 0) {
            stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        }


        return stringBuilder.toString();
    }



    private Task fromString(String s) throws IllegalArgumentException{
        String[] parameters = s.split(",");

        int id = Integer.parseInt(parameters[0]);//
        var type = Type.valueOf(parameters[1].toUpperCase());//
        var name = parameters[2];//
        var status = Status.valueOf(parameters[3]);//
        var description = parameters[4] == "null" ? null : parameters[4];//
        Optional<Integer> subtaskEpicId;

        if (type == Type.SUBTASK) {
            subtaskEpicId = Optional.of(Integer.valueOf(parameters[5]));//
        } else {
            subtaskEpicId = Optional.empty();
        }


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

    private void loadFromFile(File file) {

        try (BufferedReader reader = new BufferedReader(new FileReader(Config.DATA_FILE_NAME))) {
            String line = reader.readLine();
            while (!line.isBlank() && !line.isEmpty()) {
                line = reader.readLine();
                if (line == null) {
                    break;
                }
                if (!line.isBlank()) {
                    var task = fromString(line);
                    addTask(task);
                }

            }

            var historyLine = reader.readLine();
            if (historyLine != null && !historyLine.isBlank()) {
                String[] historyIndexes = historyLine.split(",");
                for (String historyIndex : historyIndexes) {
                    historyManager.addToHistory(Integer.valueOf(historyIndex));
                }
            }


        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

//    private void addFromString(String s) {
//        String[] parameters = s.split(",");
//
//        int id = Integer.parseInt(parameters[0]);//
//        var type = Type.valueOf(parameters[1]);//
//        var name = parameters[2];//
//        var status = Status.valueOf(parameters[3]);//
//        var description = parameters[4] == "null" ? null : parameters[4];//
//        Optional<Integer> subtaskEpicId = Optional.ofNullable(Integer.valueOf(parameters[5]));//
//
//        switch (type) {
//            case TASK -> {
//                Task task = new Task(name, description, status);
//                task.setId(id);
//                addTask(task.getId(), task);
//            }
//
//            case EPIC -> {
//                Epic epic = new Epic(name, description, status);
//                epic.setId(id);
//                addTask(epic.getId(), epic);
//            }
//
//            case SUBTASK -> {
//                Epic epic = (Epic) getTask(subtaskEpicId.get());
//                var subtask = new Subtask(name, description, status, epic);
//
//                epic.addSubtask(subtask);
//                addTask(subtask.getId(), subtask);
//            }
//        }
//
//    }
}
