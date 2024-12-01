package tasktracker.model;

import java.util.ArrayList;
import java.util.List;

public class Epic extends Task {

    List<Subtask> subtasks;


    public Epic(String name, String description, Status status) {
        super(name, description, status);

        subtasks = new ArrayList<>();
    }

    public List<Subtask> getSubtasks() {
        return subtasks;
    }
    
    private void updateEpicStatus() {
        Status epicStatus = null; // null всегда будет заменяться
        boolean allSubtasksDone = true;

        if (subtasks.isEmpty()) {
            epicStatus = Status.NEW;
        } else {
            for (Subtask subtask : subtasks) {
                if (subtask.getStatus() == Status.IN_PROGRESS) {
                    epicStatus = Status.IN_PROGRESS;
                    break;
                } else if (allSubtasksDone && subtask.getStatus() != Status.DONE) {
                    allSubtasksDone = false;
                }
            }
        }
        if (allSubtasksDone) {
            epicStatus = Status.DONE;
        }

        setStatus(epicStatus);
    }

    public void addSubtask(Subtask subtask) {
        subtasks.add(subtask);
    }
}
