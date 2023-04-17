package owner;

import database.ConnectionManager;

import javax.imageio.stream.IIOByteBuffer;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class OwnerRepository {
    private final ConnectionManager connectionManager;
    private Connection connection;
    private PreparedStatement statement;
    private ResultSet resultSet;

    public OwnerRepository(){
        this.connectionManager = new ConnectionManager();
    }

    /** Basic CRUD operation with database
     * and more
     * Usually, repositories are used by services and it's
     * common to give exceptions to service which uses it
     */

    public ArrayList<Owner> getAllOwners() throws SQLException {
        ArrayList<Owner> owners = new ArrayList<>();

        String query = "SELECT * FROM owners";
        Connection connection = this.connectionManager.getConnection();

        PreparedStatement statement = connection.prepareStatement(query);

        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()){
            /* you can use the constructor
            * new Owner(
                    resultSet.getInt("id"),
                    resultSet.getString("ownerName"),
                    resultSet.getInt("age"),
                    resultSet.getString("email"),
                    resultSet.getTimestamp("createdAt"),
                    resultSet.getTimestamp("updatedAt")
            );
            *
            * or can use setters
            * */
/*
            Owner owner = new Owner();
            owner.setId(resultSet.getInt("id"));
            owner.setOwnerName(resultSet.getString("ownerName"));
            owner.setAge(resultSet.getInt("age"));
            owner.setEmail(resultSet.getString("email"));
          x   owner.setCreatedAt(resultSet.getTimestamp("createdAt"));
            owner.setUpdatedAt(resultSet.getTimestamp("updatedAt"));
*/

            owners.add(this.createOwnerFromResultSet(resultSet));
        }


        /// close connection
        /*resultSet.close();
        statement.close();
        connection.close();*/
        this.connectionManager.closeConnections(connection, resultSet, statement);

        return owners;
    }

    public Owner getOwnerByEmail (String email) throws OwnerNotFoundException {
        try {
            String query = "SELECT * FROM owners WHERE email = ? LIMIT 1";
            // opening connection
            this.connection = connectionManager.getConnection();
            // creating statement
            this.statement = this.connection.prepareStatement(query);
            // set fields inside query
            this.statement.setString(1, email);
            // execute query to database to find owner
            this.resultSet = this.statement.executeQuery();
            // one check to see if there is a result
            if (resultSet.next()) return this.createOwnerFromResultSet(this.resultSet);

            /* this code is same as above
            if (resultSet.next()) {
                Owner owner = this.createOwnerFromResultSet(this.resultSet);
                return owner;
            } else {
                throw new OwnerNotFoundException();
            }*/

            // if getting here, there is no result
            // it is possible to throw exception inside try block to external class or method if
            // exception will be thrown from method signature and not handled in catch block below for example
            // throw new OwnerNotFoundException();

        } catch (SQLException exception) {
            exception.printStackTrace();
        } finally {
            // closing connection
            this.connectionManager.closeConnections(this.connection, this.resultSet, this.statement);
        }
        // if you will not throw this exception then need to return null or owner object since method
        // signature require to return something
        throw new OwnerNotFoundException("owner with email address "+ email + " not found!");
    }

    public ArrayList<Owner> filterOwnerByAge(int startAge, int endAge){
        ArrayList<Owner> owners = new ArrayList<>();
        try {
            String query = "SELECT * FROM owners WHERE age >= ? AND age <= ? ORDER BY age ASC";
            this.connection = this.connectionManager.getConnection();
            this.statement = this.connection.prepareStatement(query);
            this.statement.setInt(1, startAge);
            this.statement.setInt(2, endAge);
            this.resultSet = statement.executeQuery();

            while (this.resultSet.next()){
                owners.add(this.createOwnerFromResultSet(this.resultSet));
            }
        }catch (SQLException exception){
            exception.printStackTrace();
        } finally {
            this.connectionManager.closeConnections(this.connection, this.resultSet, this.statement);
        }

        return owners;
    }

    public Owner getOwnerById (Integer id) throws OwnerNotFoundException {
        try {
            String query = "SELECT * FROM owners WHERE id = ? LIMIT 1";
            this.connection = connectionManager.getConnection();
            this.statement = this.connection.prepareStatement(query);
            this.statement.setInt(1, id);
            this.resultSet = this.statement.executeQuery();
            if (resultSet.next()) return this.createOwnerFromResultSet(this.resultSet);
        } catch (SQLException exception) {
            exception.printStackTrace();
        } finally {
            this.connectionManager.closeConnections(this.connection, this.resultSet, this.statement);
        }
        throw new OwnerNotFoundException("owner with id: "+ id + " not found!");
    }

    public String createOwner(Owner owner) {
        try {
            String query = "INSERT INTO owners(ownerName, age, email) values(?, ?, ?)";

            this.connection = this.connectionManager.getConnection();
            this.statement = this.connection.prepareStatement(query);

            this.statement.setString(1, owner.getOwnerName());
            this.statement.setInt(2, owner.getAge());
            this.statement.setString(3, owner.getEmail());

            // we use executeUpdate() when we dont expect some resultset or query database for some results
            // in this case, we want to add new items or change item or remove item in database
            if (this.statement.executeUpdate() == 1) return "Owner created successfully";
            // line is same as above
            /*int result = this.statement.executeUpdate();
             * if(result == 1){
             *   return "Owner created successfully";
             * } else {
             *   return "Failed to create owner";
             * }
             * */
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.connectionManager.closeConnections(this.connection, null, this.statement);
        }

        return "Failed to create owner";
    }

    public String updateOwner(Owner owner) {
        try {
            String query = "UPDATE owners SET ownerName = ?, age = ?, email = ? WHERE id = ?";

            this.connection = this.connectionManager.getConnection();
            this.statement = this.connection.prepareStatement(query);

            this.statement.setString(1, owner.getOwnerName());
            this.statement.setInt(2, owner.getAge());
            this.statement.setString(3, owner.getEmail());
            this.statement.setInt(4, owner.getId());

            if (this.statement.executeUpdate() == 1) return "Owner updated successfully";
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.connectionManager.closeConnections(this.connection, null, this.statement);
        }

        return "Failed to update owner";
    }

    public String deleteOwner(Integer ownerId) {
        try {
            String query = "DELETE from owners WHERE id = ?";

            this.connection = this.connectionManager.getConnection();
            this.statement = this.connection.prepareStatement(query);

            this.statement.setInt(1, ownerId);


            if (this.statement.executeUpdate() == 1) return "Owner deleted successfully";
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.connectionManager.closeConnections(this.connection, null, this.statement);
        }

        return "Failed to delete owner";
    }

    private Owner createOwnerFromResultSet(ResultSet resultSet) throws SQLException {
        return new Owner(
                resultSet.getInt("id"),
                resultSet.getString("ownerName"),
                resultSet.getInt("age"),
                resultSet.getString("email"),
                resultSet.getTimestamp("createdAt"),
                resultSet.getTimestamp("updatedAt")
        );
    }

}
