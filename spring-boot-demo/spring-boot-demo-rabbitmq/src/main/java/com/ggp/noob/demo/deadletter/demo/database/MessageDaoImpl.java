package com.ggp.noob.demo.deadletter.demo.database;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

/**
 * @Author Created by gongguanpeng on 2022/3/13 20:35
 */
@Repository
public class MessageDaoImpl implements MessageDao {
    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public int insert(MessageDO messageDO) {
        String sql = "insert into message(exchange,queue,`key`,message)values(:exchange,:queue,:key,:message)";
        MapSqlParameterSource param = new MapSqlParameterSource();
        param.addValue("exchange",messageDO.getExchange());
        param.addValue("queue",messageDO.getQueue());
        param.addValue("key",messageDO.getKey());
        param.addValue("message",messageDO.getMessage());
        return jdbcTemplate.update(sql,param);
    }
}
