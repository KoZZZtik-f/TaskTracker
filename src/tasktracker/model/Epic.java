package tasktracker.model;

import java.util.ArrayList;
import java.util.List;

public class Epic extends Task {

    List<Subtask> subtasks;


    public Epic(String name, String description, Status status) {
        super(name, description, status);

        subtasks = new ArrayList<>();
        updateEpicStatus();
    }

    public List<Subtask> getSubtasks() {
        return subtasks;
    }
    
    private void updateEpicStatus() {
        Status epicStatus = Status.NEW; // NEW всегда будет заменяться
        boolean allSubtasksDone = true;

        if (subtasks.isEmpty()) {
            allSubtasksDone = false;
            epicStatus = Status.NEW;
        } else {
            for (Subtask subtask : subtasks) {
                if (allSubtasksDone && subtask.getStatus() != Status.DONE) {
                    allSubtasksDone = false;
                }
                if (subtask.getStatus() == Status.IN_PROGRESS) {
                    epicStatus = Status.IN_PROGRESS;
                    allSubtasksDone = false;
                    break;
                }
                if (!allSubtasksDone && subtask.getStatus() == Status.DONE) {
                    epicStatus = Status.IN_PROGRESS;
                    break;
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
        updateEpicStatus();
    }

    public void deleteSubtask(int id) {
        for (int i = 0; i < subtasks.size(); i++) {
            var subtask = subtasks.get(i);
            if (subtask.getId() == id) {
                subtasks.remove(i);
            }
        }
        updateEpicStatus();
    }
}
