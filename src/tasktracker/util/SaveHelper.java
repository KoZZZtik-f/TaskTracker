//package tasktracker.util;
//
//import tasktracker.config.Config;
//import tasktracker.model.*;
//
//import java.io.File;
//import java.io.FileWriter;
//import java.io.IOException;
//import java.util.Optional;
//
//public class SaveHelper {
//    public static void saveTask(Task task) {
//        try (FileWriter fileWriter = new FileWriter(Config.DATA_FILE_NAME, true)) {
//            fileWriter.write(taskToString(task, true));
//
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//
//
//    private static String taskToString(Task task) {
//        StringBuilder stringBuilder = new StringBuilder();
//        stringBuilder.append(task.getId()).append(",");
//        stringBuilder.append(task.getClass().getSimpleName()).append(",");
//        stringBuilder.append(task.getName()).append(",");
//        stringBuilder.append(task.getStatus()).append(",");
//        stringBuilder.append(task.getDescription().orElse(null)).append(",");
//        if (task instanceof Subtask) {
//            var subtask = (Subtask) task;
//            stringBuilder.append(subtask.getEpic().getId());
//        }
//
//
//        return stringBuilder.toString();
//    }
//
//    private static String taskToString(Task task, boolean nextStroke) {
//        StringBuilder stringBuilder = new StringBuilder();
//        stringBuilder.append(task.getId()).append(",");
//        stringBuilder.append(task.getClass().getSimpleName()).append(",");
//        stringBuilder.append(task.getName()).append(",");
//        stringBuilder.append(task.getStatus()).append(",");
//        stringBuilder.append(task.getDescription().orElse(null)).append(",");
//        if (task instanceof Subtask) {
//            var subtask = (Subtask) task;
//            stringBuilder.append(subtask.getEpic().getId());
//        }
//        if (nextStroke) {
//            stringBuilder.append("\n");
//        }
//
//        return stringBuilder.toString();
//    }
//
//    private static Task fromString(String s) throws IllegalArgumentException{
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
//                return task;
//
//            }
//
//            case EPIC -> {
//                Epic epic = new Epic(name, description, status);
//                epic.setId(id);
//                return epic;
//            }
//
//            case SUBTASK -> {
//                Epic epic = (Epic) tasks.get(subtaskEpicId);
//                var subtask = new Subtask(name, description, status, epic);
//
//                epic.addSubtask(subtask);
//                return subtask;
//            }
//        }
//        throw new IllegalArgumentException();
//    }
//
////    private static void addFromString(String s) {
////        String[] parameters = s.split(",");
////
////        int id = Integer.parseInt(parameters[0]);//
////        var type = Type.valueOf(parameters[1]);//
////        var name = parameters[2];//
////        var status = Status.valueOf(parameters[3]);//
////        var description = parameters[4] == "null" ? null : parameters[4];//
////        Optional<Integer> subtaskEpicId = Optional.ofNullable(Integer.valueOf(parameters[5]));//
////
////        switch (type) {
////            case TASK -> {
////                Task task = new Task(name, description, status);
////                task.setId(id);
////                addTask(task.getId(), task);
////            }
////
////            case EPIC -> {
////                Epic epic = new Epic(name, description, status);
////                epic.setId(id);
////                addTask(epic.getId(), epic);
////            }
////
////            case SUBTASK -> {
////                Epic epic = (Epic) getTask(subtaskEpicId.get());
////                var subtask = new Subtask(name, description, status, epic);
////
////                epic.addSubtask(subtask);
////                addTask(subtask.getId(), subtask);
////            }
////        }
////
////    }
//}
