package com.ot.springdbutils.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.SQLException;

@Component
public class TxUtil {

    @Autowired
    private ConnectionUtil connectionUtil;

    public void openTx() {
        try {
            connectionUtil.getConnection().setAutoCommit(false);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void commit() {
        try {
            connectionUtil.getConnection().commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void rollback() {
        try {
            connectionUtil.getConnection().rollback();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void close() {
        try {
            connectionUtil.getConnection().close();
            connectionUtil.remove();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
