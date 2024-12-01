package tasktracker.model;

import tasktracker.config.Config;

import java.util.Objects;
import java.util.Optional;

public class Task {
    private String name;
    private int id;
    private Optional<String> description;
    private Status status;
    private Type type;

    public Task(String name, String description, Status status) {
        this.name = name;
        this.status = status;

        if (description == null || description.isBlank()) {
            this.description = Optional.empty();
        } else {
            this.description = Optional.of(description);
        }
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

    public void setStatus(Status status) {
        this.status = status;
    }

    public void setId(int id) {
        this.id = id;
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
