package com.jbcodes.repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TradeDataRepository {

    Connection connection;

    public TradeDataRepository() throws SQLException{

        String url = "jdbc:postgresql://localhost:5432/postgres";
        String username = "justinbullock";
        String password = "secure";
        try{
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(url, username, password);
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public boolean get(Trade trade){
        String sql = "SELECT * FROM profitloss WHERE trade_id ="+trade.getId()+";";

        try{
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            if (resultSet.next()){

                resultSet.close();
                statement.close();
                return true;

            }

        }catch (Exception e){
            e.printStackTrace();
        }

        return false;
    }

    public List<Trade> get(){
        List<Trade> trades = new ArrayList<>();
        String sql = "SELECT * FROM profitloss LIMIT 5;";

        try{
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()){

                Trade trade = new Trade();

                trade.setId(resultSet.getInt(1));
                trade.setProfitLoss(resultSet.getDouble(2));
                trade.setShares(resultSet.getInt(3));
                trade.setDate(resultSet.getString(4));

                trades.add(trade);

                resultSet.close();
                statement.close();

            }

        }catch (Exception e){
            e.printStackTrace();
        }

        return trades;
    }

    public void update(Trade trade){
        try{

            if (get(trade)){
                String sql = "UPDATE profitloss SET trade_id = ?, profit_loss = ?, shares = ?, date = ? WHERE trade_id = ?;";
                PreparedStatement preparedStatement = connection.prepareStatement(sql);

                preparedStatement.setInt(1, trade.getId());
                preparedStatement.setDouble(2, trade.getProfitLoss());
                preparedStatement.setDouble(3, trade.getShares());
                preparedStatement.setString(4, trade.getDate());
                preparedStatement.setInt(5, trade.getId());

                System.out.println(preparedStatement.toString());

                preparedStatement.close();

                System.out.println("TradeRepository.update() called :trade has been updated");

            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void create(Trade trade){
        String sql = "INSERT INTO profitloss values (?,?,?,?)";

        try{

            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, trade.getId());
            preparedStatement.setDouble(2, trade.getProfitLoss());
            preparedStatement.setDouble(3, trade.getShares());
            preparedStatement.setString(4, trade.getDate());

            preparedStatement.executeUpdate();

            System.out.println("TradeRepository.create() called :trade has been created");

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void delete(Trade trade){
        try{

            if (get(trade)){
                String sql = "DELETE FROM profitloss WHERE trade_id = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(sql);

                preparedStatement.setInt(1, trade.getId());
                preparedStatement.executeUpdate();

                preparedStatement.close();

                System.out.println("TradeRepository.delete() called :trade has been deleted");

            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
