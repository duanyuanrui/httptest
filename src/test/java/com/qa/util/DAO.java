package com.qa.util;

import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DAO {
    // 插入，更新，删除操作都可以包含其中
    public void update(String sql, Object... args) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = DBUtil.getConnection();
            preparedStatement = connection.prepareStatement(sql);

            for (int i = 0; i < args.length; i++) {
                preparedStatement.setObject(i + 1, args[i]);
            }
            preparedStatement.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(connection, preparedStatement, null);
        }
    }



    /**
     * 处理结果集，得到含有Map的一个List，其中一个Map对象对应一条记录
     *
     * @param resultSet
     * @return
     * @throws SQLException
     */
    public List<Map<String, Object>> handleResultSetToMapList(ResultSet resultSet) throws SQLException {
        List<Map<String, Object>> values = new ArrayList<>();

        List<String> columnLabels = getColumnLabels(resultSet);
        Map<String, Object> map = null;

        // 处理ResultSet，使用while循环
        while (resultSet.next()) {
            map = new HashMap<>();

            for (String columnLabel : columnLabels) {
                Object value = resultSet.getObject(columnLabel); // 获得列的值

                map.put(columnLabel, value); // 将列的别名与列的值放入map集合中
            }
            values.add(map); // 将map集合放入List中
        }
        return values;
    }

    /**
     * 获取结果集的ColumnLabel对应的List
     *
     * @param rs
     * @return
     * @throws SQLException
     */
    public List<String> getColumnLabels(ResultSet rs) throws SQLException {
        List<String> labels = new ArrayList<>();
        ResultSetMetaData rsmd = rs.getMetaData();
        for (int i = 0; i < rsmd.getColumnCount(); i++) {
            labels.add(rsmd.getColumnLabel(i + 1));
        }
        return labels;
    }

    // 返回某条记录的某个字段的值或一个统计的值
    public <E> E getForValue(String sql, Object ...args) {

        // 1.得到结果集：该结果集只有一行，一列
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = DBUtil.getConnection();
            preparedStatement = connection.prepareStatement(sql);

            for (int i = 0; i < args.length; i++) {
                preparedStatement.setObject(i + 1, args[i]);
            }

            resultSet = preparedStatement.executeQuery();

            if(resultSet.next()) {
                return (E) resultSet.getObject(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(connection, preparedStatement, resultSet);
        }
        return null;
}
}
