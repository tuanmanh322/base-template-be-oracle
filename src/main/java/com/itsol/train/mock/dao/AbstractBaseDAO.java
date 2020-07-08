package com.itsol.train.mock.dao;

import com.itsol.train.mock.dto.BaseSearchDto;
import com.itsol.train.mock.util.SqlUtil;
import org.hibernate.Session;
import org.hibernate.query.NativeQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public abstract class AbstractBaseDAO {
    @Autowired
    private EntityManager entityManager;
    @Autowired
    private SqlUtil sqlUtil;
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    protected SqlUtil getSqlUtil() {
        return sqlUtil;
    }

    protected String getSqlQueryById(String module, String queryId){
        return sqlUtil.getSqlQueryById(module, queryId);
    }

    protected long countTotalRecords(String sql, Map<String, Object> parameters){
        sql = sql.replaceAll("(?i)from", "FROM");
        sql = "SELECT COUNT(1) " + sql.substring(sql.indexOf("FROM"));
        return namedParameterJdbcTemplate.queryForObject(sql, parameters, BigDecimal.class).longValue();
    }

    protected long countTotalRecords1(String sql, Map<String, Object> parameters){
        sql = sql.replaceAll("(?i)from", "FROM");
        sql = "SELECT COUNT(1) " + sql.substring(sql.indexOf("FROM"));
        NativeQuery<BigDecimal> query = getSession().createNativeQuery(sql);
        setParameters(query, parameters);
        return query.getSingleResult().longValue();
    }

    protected long getOffset(BaseSearchDto searchDto){
        if(searchDto.getPage() == 0)
            return 0;
        return searchDto.getPage() * searchDto.getPageSize();
    }

    protected NamedParameterJdbcTemplate getNamedParameterJdbcTemplate() {
        return namedParameterJdbcTemplate;
    }

    protected <T> List<T> queryPaging(BaseSearchDto searchDto, String sql, Map<String, Object> parameters, Class<T> clazz){
        sql = getSqlPaging(searchDto, sql, parameters);
        return namedParameterJdbcTemplate.query(sql, parameters, BeanPropertyRowMapper.newInstance(clazz));
    }

    protected <T> List<T> queryPaging(BaseSearchDto searchDto, String sql, Map<String, Object> parameters, RowMapper<T> rowMapper){
        sql = getSqlPaging(searchDto, sql, parameters);
        return namedParameterJdbcTemplate.query(sql, parameters, rowMapper);
    }

    @SuppressWarnings("unchecked")
    protected <T> NativeQuery<T> getHibernateQuery(BaseSearchDto searchDto, String sql, Map<String, Object> parameters){
        NativeQuery<T> query = getSession().createNativeQuery(sql);
        query.setFirstResult((int) (getOffset(searchDto)));
        if(searchDto.getPage() == 0){
            query.setFirstResult(searchDto.getPageSize()  + 1);
        } else {
            query.setMaxResults(searchDto.getPageSize() * searchDto.getPage());
        }
        setParameters(query, parameters);
        return query;
    }

    private String getSqlPaging(BaseSearchDto searchDto, String sql, Map<String, Object> parameters){
        StringBuilder sqlBuilder = new StringBuilder("SELECT * FROM (\n");
        sql = sql.replaceAll("(?i)from", "FROM");
        sqlBuilder.append(sql.substring(0, sql.indexOf("FROM") - 1)).append("\n")
                .append("    , ROWNUM RN \n")
                .append(sql.substring(sql.indexOf("FROM")))
                .append(") \n")
        .append(" WHERE RN <= (:p_offset + :p_page_size) AND RN > :p_offset");
        parameters.put("p_offset", getOffset(searchDto));
        parameters.put("p_page_size", searchDto.getPageSize());
        return sqlBuilder.toString();
    }

    protected void setParameters(NativeQuery query, Map<String, Object> parameters){
        parameters.forEach(query::setParameter);
    }
    public Session getSession(){
        return entityManager.unwrap(Session.class);
    }
    protected JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }
}
