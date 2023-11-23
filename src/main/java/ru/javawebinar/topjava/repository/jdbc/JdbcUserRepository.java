package ru.javawebinar.topjava.repository.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Repository
@Transactional(readOnly = true)
public class JdbcUserRepository implements UserRepository {

    private static final BeanPropertyRowMapper<User> ROW_MAPPER = BeanPropertyRowMapper.newInstance(User.class);

    private final JdbcTemplate jdbcTemplate;

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private final SimpleJdbcInsert insertUser;

    @Autowired
    public JdbcUserRepository(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.insertUser = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("users")
                .usingGeneratedKeyColumns("id");

        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    @Transactional
    public User save(User user) {
        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource(user);

        if (user.isNew()) {
            Number newKey = insertUser.executeAndReturnKey(parameterSource);
            user.setId(newKey.intValue());

            jdbcTemplate.batchUpdate(
                    "INSERT INTO user_role (user_id, role) VALUES (?, ?)",
                    user.getRoles(),
                    10,
                    (PreparedStatement ps, Role role) -> {
                        ps.setLong(1, (Integer) newKey);
                        ps.setString(2, role.name());
                    });
        } else if (namedParameterJdbcTemplate.update("""
                   UPDATE users SET name=:name, email=:email, password=:password,
                   registered=:registered, enabled=:enabled, calories_per_day=:caloriesPerDay WHERE id=:id
                """, parameterSource) > 0) {
            jdbcTemplate.batchUpdate("""
                        MERGE INTO user_role AS ur USING (VALUES(?,?))
                        ON (ur.user_id=? AND ur.role=?)
                        WHEN MATCHED THEN DO NOTHING
                        WHEN NOT MATCHED THEN INSERT VALUES(?,?)""",
                    user.getRoles(),
                    10,
                    (PreparedStatement ps, Role role) -> {
                        ps.setLong(1, user.getId());
                        ps.setString(2, role.name());
                    });
        } else {
            return null;
        }
        return user;
    }

    @Override
    @Transactional
    public boolean delete(int id) {
        return jdbcTemplate.update("DELETE FROM users WHERE id=?", id) != 0;
    }

    @Override
    public User get(int id) {
        List<User> users = jdbcTemplate.query("SELECT * FROM users LEFT JOIN user_role ur ON users.id = ur.user_id WHERE id=?",
                this::createUserList, id);
        return DataAccessUtils.singleResult(users);
    }

    @Override
    public User getByEmail(String email) {
//        return jdbcTemplate.queryForObject("SELECT * FROM users WHERE email=?", ROW_MAPPER, email);
        List<User> users = jdbcTemplate.query("SELECT * FROM users LEFT JOIN user_role ur ON users.id = ur.user_id WHERE email=?", this::createUserList, email);
        return DataAccessUtils.singleResult(users);
    }

    @Override
    public List<User> getAll() {
        return jdbcTemplate.query(
                "SELECT * FROM users LEFT JOIN user_role ur ON users.id = ur.user_id ORDER BY name, email", this::createUserList);
    }

    private List<User> createUserList(ResultSet rs) throws SQLException {
        Map<Integer, User> users = new LinkedHashMap<>();
        while (rs.next()) {
            int id = rs.getInt("id");
            String role = rs.getString("role");
            if (users.get(id) != null) {
                if (role != null) {
                    users.get(id).getRoles().add(Role.valueOf(rs.getString("role")));
                }
            } else {
                User user = ROW_MAPPER.mapRow(rs, rs.getRow());
                user.setRoles(role != null ?
                        Collections.singleton(Role.valueOf(rs.getString("role"))) :
                        Collections.emptyList());
                users.put(id, user);
            }
        }
        return new ArrayList<>(users.values());
    }
}
