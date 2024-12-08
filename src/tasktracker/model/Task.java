package tasktracker.model;

import tasktracker.config.Config;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;
import java.util.Optional;

public class Task {
    private String name;
    private int id;
    private Optional<String> description;
    private Status status;
//    private Type type;
    private Duration duration;
    private Optional<LocalDateTime> startTime;

    public Task(String name, String description, Status status) {
        this.name = name;
        this.status = status;
        this.description = (description == null || description.isBlank()) ? Optional.empty() : Optional.of(description);
        this.startTime = Optional.empty();
        this.duration = Duration.ZERO;

    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public Optional<String> getDescription() {
        return description;
    }

    public Status getStatus() {
        return status;
    }

    public Optional<LocalDateTime> getStartTime() {
        return startTime;
    }

    public Duration getDuration() {
        return duration;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = Optional.of(description);
    }

//    public void setType(Type type) {
//        this.type = type;
//    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = Optional.of(startTime);
    }

    public Optional<LocalDateTime> getEndTime() {
        if (startTime.isPresent()) {
            return Optional.of(startTime.get().plus(duration));
        } else {
            return Optional.empty();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return id == task.id && Objects.equals(name, task.name) && Objects.equals(description, task.description) && status == task.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, id, description, status);
    }

//    @Override
//    public String toString() {
//        StringBuilder stringBuilder = new StringBuilder(this.getClass().toString().split("\\.")[2]
//                + " {" + "name = " + name);
//
//        if (description.isPresent()) {
//            if (description.get().length() <= Config.DESCRIPTION_LENGHT_TO_PRINT) {
//                stringBuilder.append(", description = ").append(description.get());
//            } else {
//                stringBuilder.append(", description.lenght = ").append(description.get().length());
//            }
//        }
//
//        stringBuilder.append(", id=").append(id).append(", status=").append(status).append("}");
//
//        return stringBuilder.toString();
//    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(id).append(",");
        stringBuilder.append(getClass().getSimpleName()).append(",");
        stringBuilder.append(name).append(",");
        stringBuilder.append(status).append(",");
        stringBuilder.append(description.orElse(null)).append(",");


        return stringBuilder.toString();
    }

}
