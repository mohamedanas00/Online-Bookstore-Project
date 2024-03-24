package Models;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Request implements Serializable{
   private int id;
   private int borrowerId;
   private int lenderId;
   private int bookId;
   private String lenderUsername;
   private String borrowerUsername;
   private String status;



    public Request(ResultSet resultSet) throws SQLException{
        id= resultSet.getInt("id");
        borrowerId=resultSet.getInt("borrower_id");
        lenderId=resultSet.getInt("lender_id");
        bookId=resultSet.getInt("book_id");
        status=resultSet.getString("status");
        lenderUsername = resultSet.getString("lender_username");
        borrowerUsername = resultSet.getString("borrower_username");
    }

    
    @Override
    public String toString() {
        return "Request [id=" + id + ", borrowerId=" + borrowerId + ", lenderId=" + lenderId + ", bookId=" + bookId
                + ", lenderUsername=" + lenderUsername + ", borrowerUsername=" + borrowerUsername + ", status=" + status
                + "]";
    }


    public String getLenderUsername() {
        return lenderUsername;
    }


    public String getBorrowerUsername() {
        return borrowerUsername;
    }


    public void setStatus(String status) {
        this.status = status;
    }
    
    

    public String getStatus() {
        return status;
    }
 
}
