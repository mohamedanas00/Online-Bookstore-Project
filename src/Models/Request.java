package Models;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Request implements Serializable{
   private int id;
   private int borrowerId;
   private int lenderId;
   private int bookId;
   private String status;


    public Request(ResultSet resultSet) throws SQLException{
        id= resultSet.getInt("id");
        borrowerId=resultSet.getInt("borrower_id");
        lenderId=resultSet.getInt("lender_id");
        bookId=resultSet.getInt("book_id");
        status=resultSet.getString("status");
    }


    @Override
    public String toString() {
        return "Request [id=" + id + ", borrowerId=" + borrowerId + ", lenderId=" + lenderId + ", bookId=" + bookId
                + ", status=" + status + "]";
    }

    
}
