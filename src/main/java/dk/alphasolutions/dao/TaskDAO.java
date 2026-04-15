package dk.alphasolutions.dao;

import dk.alphasolutions.model.Task;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class TaskDAO {

    private final DataSource dataSource;

    public TaskDAO(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void create(Task task) {
        String sql = "INSERT INTO task (project_id, name, description, estimated_hours, start_date, end_date) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, task.getProjectId());
            stmt.setString(2, task.getName());
            stmt.setString(3, task.getDescription());
            stmt.setDouble(4, task.getEstimatedHours());
            stmt.setString(5, task.getStartDate());
            stmt.setString(6, task.getEndDate());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Task> getByProjectId(int projectId) {
        List<Task> tasks = new ArrayList<>();
        String sql = "SELECT * FROM task WHERE project_id=?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, projectId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                tasks.add(new Task(
                        rs.getInt("id"),
                        rs.getInt("project_id"),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getDouble("estimated_hours"),
                        rs.getString("start_date"),
                        rs.getString("end_date")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tasks;
    }

    public Task getById(int id) {
        String sql = "SELECT * FROM task WHERE id=?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Task(
                        rs.getInt("id"),
                        rs.getInt("project_id"),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getDouble("estimated_hours"),
                        rs.getString("start_date"),
                        rs.getString("end_date")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void update(Task task) {
        String sql = "UPDATE task SET name=?, description=?, estimated_hours=?, start_date=?, end_date=? WHERE id=?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, task.getName());
            stmt.setString(2, task.getDescription());
            stmt.setDouble(3, task.getEstimatedHours());
            stmt.setString(4, task.getStartDate());
            stmt.setString(5, task.getEndDate());
            stmt.setInt(6, task.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(int id) {
        String sql = "DELETE FROM task WHERE id=?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}