package dk.alphasolutions.dao;

import dk.alphasolutions.model.TimeEntry;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class TimeEntryDAO {

    private final DataSource dataSource;

    public TimeEntryDAO(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void create(TimeEntry t) {
        String sql = "INSERT INTO time_entry (task_id, user_id, hours, date, comment) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, t.getTaskId());
            stmt.setInt(2, t.getUserId());
            stmt.setDouble(3, t.getHours());
            stmt.setString(4, t.getDate());
            stmt.setString(5, t.getComment());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<TimeEntry> getByTaskId(int taskId) {
        List<TimeEntry> list = new ArrayList<>();
        String sql = "SELECT * FROM time_entry WHERE task_id=?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, taskId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                list.add(new TimeEntry(
                        rs.getInt("id"),
                        rs.getInt("task_id"),
                        rs.getInt("user_id"),
                        rs.getDouble("hours"),
                        rs.getString("date"),
                        rs.getString("comment")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public void update(TimeEntry t) {
        String sql = "UPDATE time_entry SET hours=?, date=? WHERE id=?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDouble(1, t.getHours());
            stmt.setString(2, t.getDate());
            stmt.setInt(3, t.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(int id) {
        String sql = "DELETE FROM time_entry WHERE id=?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}