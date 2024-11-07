package io.huyvu.notion.inventory;

import org.apache.ibatis.session.SqlSessionFactory;

import java.sql.Connection;

public class LocalRepository {
    private final SqlSessionFactory conn;


    public LocalRepository(SqlSessionFactory conn) {
        this.conn = conn;
    }


}
