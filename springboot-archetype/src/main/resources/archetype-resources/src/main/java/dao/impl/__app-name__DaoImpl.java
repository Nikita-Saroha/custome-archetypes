package ${package}.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import ${package}.dao.${app-name}Dao;
import ${package}.model.Names;

@Repository
public class ${app-name}DaoImpl implements ${app-name}Dao {
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	@Override
	public List<Names> findAll() {
		return jdbcTemplate.query("select * from names", new NamesRowMapper());
	}
	
	@Override
	public Names save(Names name) {
		final String sql = "insert into names(id,name) values(?,?)";
		 Object[] params = new Object[]{name.getId() , name.getName()};
		 int rowsInserted = jdbcTemplate.update(sql , params);
		 if(rowsInserted == 1)
			 return name;
		 else 
			 return null;
	}
	
	public Names findOne(Integer id) {
		return jdbcTemplate.queryForObject(
	            "select * from names where id=?",
	            new Object[]{id}, new NamesRowMapper());
	}

	public void delete(Integer id) {
		jdbcTemplate.execute("delete from names where id="+id);
	}
	
	class NamesRowMapper implements RowMapper<Names>
	{
	    @Override
	    public Names mapRow(ResultSet rs, int rowNum) throws SQLException {
	    	Names name = new Names();
	    	name.setId(rs.getInt("id"));
	        name.setName(rs.getString("name"));
	        return name;
	    }
	}



}
