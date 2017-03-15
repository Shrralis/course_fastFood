package server;

import models.*;

import java.sql.*;

/**
 * Created by shrralis on 3/12/17.
 */
@SuppressWarnings("unchecked")
public class DatabaseWorker {
    private static DatabaseWorker iam = null;
    private Connection connection = null;

    private DatabaseWorker() {}

    public static boolean openConnection() {
        if (iam == null) {
            iam = new DatabaseWorker();
        } else {
            closeConnection();
            return openConnection();
        }

        final String sDatabaseName = "formula_one";
        final String sServerUser = "ТУТ_ВВЕДИ_ІМ'Я_КОРИСТУВАЧА_MYSQL_SERVER";
        final String sServerPassword = "ТУТ_ВВЕДИ_ПАРОЛЬ_КОРИСТУВАЧА_MYSQL_SERVER";

        try {
            if (iam.connection == null || iam.connection.isClosed()) {
                Class.forName("com.mysql.jdbc.Driver").newInstance();

                iam.connection = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/" + sDatabaseName + "?useUnicode=true&characterEncoding=UTF-8",
                        sServerUser,
                        sServerPassword
                );
            }
            return true;
        } catch (SQLException | ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void closeConnection() {
        try {
            if (iam != null) {
                if (iam.connection != null && !iam.connection.isClosed()) {
                    iam.connection.close();
                }
                iam = null;
            }
        } catch (SQLException ignored) {}
    }

    public static ServerResult processQuery(ServerQuery query) {
        if (query == null) {
            System.out.println("No query from the server!");
            return ServerResult.create(1, "No query");
        } else {
            String method = query.getMethodName();

            if (method.equalsIgnoreCase("disconnect")) {
                return ServerResult.create(0, "disconnect");
            }

            if (method.equalsIgnoreCase("get")) {
                return iam.get(query);
            }

            if (method.equalsIgnoreCase("add")) {
                return iam.add(query);
            }

            if (method.equalsIgnoreCase("delete")) {
                return iam.delete(query);
            }

            if (method.equalsIgnoreCase("edit")) {
                return iam.edit(query);
            }
        }
        return null;
    }

    private ServerResult get(ServerQuery query) {
        ServerResult result = null;

        try {
            String table = query.getTableName().toLowerCase();
            if (connection != null && !connection.isClosed()) {
                Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                        ResultSet.CONCUR_READ_ONLY);

                if (table.matches("^(companies)|(fast_foods)|(filations)|(personal)|(fillations_has_meals)" +
                        "|(fillations_has_drinks)|(meals)|(orders_has_meals)|(drinks)|(orders_has_drinks)|(orders)$")) {

                    if (table.equalsIgnoreCase("fillations_has_meals")) {
                        String sql;

                        if (query.getMySQLCondition().matches("(\\D|\\d)+meal(\\D|\\d)+")) {
                            sql = "SELECT f.* FROM `fillations_has_meals` fhm, `fillations` f" +
                                    query.getMySQLCondition() + " AND fhm.`fillation` = f.`id`;";
                            result = ServerResult.create(new List(statement.executeQuery(sql), Fillation.class, connection));
                        } else {
                            sql = "SELECT m.* FROM `fillations_has_meals` fhm, `meals` m" +
                                    query.getMySQLCondition() + " AND fhm.`meal` = m.`id`;";
                            result = ServerResult.create(new List(statement.executeQuery(sql), Meal.class));
                        }
                        return result;
                    }

                    if (table.equalsIgnoreCase("fillations_has_drinks")) {
                        String sql;

                        if (query.getMySQLCondition().matches("(\\D|\\d)+drink(\\D|\\d)+")) {
                            sql = "SELECT f.* FROM `fillations_has_drinks` fhd, `fillations` f" +
                                    query.getMySQLCondition() + " AND fhd.`fillation` = f.`id`;";
                            result = ServerResult.create(new List(statement.executeQuery(sql), Fillation.class, connection));
                        } else {
                            sql = "SELECT d.* FROM `fillations_has_drinks` fhd, `drinks` d" +
                                    query.getMySQLCondition() + " AND fhm.`drink` = d.`id`;";
                            result = ServerResult.create(new List(statement.executeQuery(sql), Drink.class));
                        }
                        return result;
                    }

                    if (table.equalsIgnoreCase("orders_has_meals")) {
                        String sql;

                        if (query.getMySQLCondition().matches("(\\D|\\d)+meal(\\D|\\d)+")) {
                            sql = "SELECT o.* FROM `orders_has_meals` ohm, `orders` o" +
                                    query.getMySQLCondition() + " AND ohm.`order` = o.`id`;";
                            result = ServerResult.create(new List(statement.executeQuery(sql), Order.class, connection));
                        } else {
                            sql = "SELECT m.* FROM `orders_has_meals` ohm, `meals` m" +
                                    query.getMySQLCondition() + " AND ohm.`meal` = m.`id`;";
                            result = ServerResult.create(new List(statement.executeQuery(sql), Meal.class));
                        }
                        return result;
                    }

                    if (table.equalsIgnoreCase("orders_has_drinks")) {
                        String sql;

                        if (query.getMySQLCondition().matches("(\\D|\\d)+drink(\\D|\\d)+")) {
                            sql = "SELECT f.* FROM `orders_has_drinks` ohd, `orders` o" +
                                    query.getMySQLCondition() + " AND ohd.`order` = o.`id`;";
                            result = ServerResult.create(new List(statement.executeQuery(sql), Order.class, connection));
                        } else {
                            sql = "SELECT d.* FROM `orders_has_drinks` ohd, `drinks` d" +
                                    query.getMySQLCondition() + " AND ohm.`drink` = d.`id`;";
                            result = ServerResult.create(new List(statement.executeQuery(sql), Drink.class));
                        }
                        return result;
                    }

                    ResultSet resultSet = statement.executeQuery("SELECT * FROM `" + table + "`" +
                            query.getMySQLCondition() + ";");

                    if (table.equalsIgnoreCase("companies")) {
                        result = ServerResult.create(new List(resultSet, Company.class, connection));
                    } else if (table.equalsIgnoreCase("fast_foods")) {
                        result = ServerResult.create(new List(resultSet, FastFood.class, connection));
                    } else if (table.equalsIgnoreCase("personal")) {
                        result = ServerResult.create(new List(resultSet, Personal.class));
                    } else if (table.equalsIgnoreCase("fillations")) {
                        result = ServerResult.create(new List(resultSet, Fillation.class));
                    } else if (table.equalsIgnoreCase("meals")) {
                        result = ServerResult.create(new List(resultSet, Meal.class));
                    } else if (table.equalsIgnoreCase("drinks")) {
                        result = ServerResult.create(new List(resultSet, Drink.class));
                    } else if (table.equalsIgnoreCase("orders")) {
                        result = ServerResult.create(new List(resultSet, Order.class));
                    }
                } else {
                    System.out.println("Unknown table (" + table + ") for get()");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    private ServerResult add(ServerQuery query) {
        ServerResult result = null;

        try {
            String table = query.getTableName().toLowerCase();

            if (connection != null && !connection.isClosed()) {
                Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                        ResultSet.CONCUR_READ_ONLY);

                if (table.matches("^(companies)|(fast_foods)|(filations)|(personal)|(fillations_has_meals)" +
                        "|(fillations_has_drinks)|(meals)|(orders_has_meals)|(drinks)|(orders_has_drinks)|(orders)$")) {
                    System.out.println(query.getInsertMysqlQuery());

                    int iResult = statement.executeUpdate(query.getInsertMysqlQuery(), Statement.RETURN_GENERATED_KEYS);

                    if (iResult >= 0) {
                        ResultSet rs = statement.getGeneratedKeys();
                        int id = 0;

                        if (rs.next()) {
                            id = rs.getInt(1);
                        }

                        if (table.matches("^((fillation)|(order))s_has_((meal)|(drink))s$")) {
                            result = ServerResult.create(0, "successfully added");
                        } else {
                            result = ServerResult.create(
                                    new List(statement.executeQuery("SELECT * FROM `" + table + "` WHERE `id` = " + id + ";"),
                                            query.getObjectToProcess().getClass(), connection)
                            );
                        }
                    } else {
                        result = ServerResult.create(1, "not added");
                    }
                }
            }
        } catch (SQLException | IllegalAccessException ignored) {}
        return result;
    }

    private ServerResult delete(ServerQuery query) {
        ServerResult result = null;

        try {
            String table = query.getTableName().toLowerCase();

            if (connection != null && !connection.isClosed()) {
                Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                        ResultSet.CONCUR_READ_ONLY);

                if (table.matches("^(companies)|(fast_foods)|(filations)|(personal)|(fillations_has_meals)" +
                        "|(fillations_has_drinks)|(meals)|(orders_has_meals)|(drinks)|(orders_has_drinks)|(orders)$")) {
                    int iResult = statement.executeUpdate("DELETE FROM `" + table + "` WHERE `id` = "
                            + query.getObjectToProcess().getId() + ";");

                    if (iResult >= 0) {
                        result = ServerResult.create(0, "deleted");
                    } else {
                        result = ServerResult.create(1, "not deleted");
                    }
                }
            }
        } catch (SQLException ignored) {}
        return result;
    }

    private ServerResult edit(ServerQuery query) {
        ServerResult result = null;

        try {
            String table = query.getTableName();

            if (connection != null && !connection.isClosed()) {
                Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                        ResultSet.CONCUR_READ_ONLY);

                if (table.matches("^(companies)|(fast_foods)|(filations)|(personal)|(fillations_has_meals)" +
                        "|(fillations_has_drinks)|(meals)|(orders_has_meals)|(drinks)|(orders_has_drinks)|(orders)$")) {
                    int iResult = statement.executeUpdate(query.getUpdateMysqlQuery(), Statement.RETURN_GENERATED_KEYS);

                    if (iResult >= 0) {
                        int id = query.getObjectToProcess().getId();

                        result = ServerResult.create(
                                new List(statement.executeQuery("SELECT * FROM `" + table + "` WHERE `id` = " + id + ";"),
                                        query.getObjectToProcess().getClass(), connection)
                        );
                        System.out.println("Size: " + result.getObjects().size());
                    } else {
                        result = ServerResult.create(1, "not updated");
                    }
                }
            }
        } catch (SQLException | IllegalAccessException ignored) {}
        return result;
    }
}
