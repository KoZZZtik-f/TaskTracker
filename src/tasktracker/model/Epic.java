package tasktracker.model;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Epic extends Task {

    private List<Subtask> subtasks;
    private LocalDateTime endTime;


    public Epic(String name, String description, Status status) {
        super(name, description, status);

        subtasks = new ArrayList<>();
        updateEpicStatus();
    }

    public List<Subtask> getSubtasks() {
        return subtasks;
    }

    @Override
    public Optional<LocalDateTime> getEndTime() {
        return Optional.of(this.endTime);
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

    private void updateEpicEndTime() {
        Duration summaryDuration = Duration.ZERO;

        if (getStartTime().isPresent() && !getSubtasks().isEmpty()) {
            for (Subtask subtask : getSubtasks()) {
                summaryDuration = summaryDuration.plus(subtask.getDuration());
            }
            setEndTime(getStartTime().get().plus(summaryDuration));
        }
    }

    public void addSubtask(Subtask subtask) {
        subtasks.add(subtask);
        updateEpicStatus();
        updateEpicEndTime();
    }

    public void deleteSubtask(int id) {
        for (int i = 0; i < subtasks.size(); i++) {
            var subtask = subtasks.get(i);
            if (subtask.getId() == id) {
                subtasks.remove(i);
            }
        }
        updateEpicAtAll();
    }

    private void updateEpicAtAll() {
        updateEpicStatus();
        updateEpicEndTime();
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }
}
