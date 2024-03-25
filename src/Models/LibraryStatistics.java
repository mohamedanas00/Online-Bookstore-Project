package Models;

import java.io.Serializable;

public class LibraryStatistics implements Serializable {

    private int borrowedBooksCount ;
    private int availableBooksCount ;
    private int acceptedRequestsCount ;
    private int rejectedRequestsCount ;
    private int pendingRequestsCount ;

    public LibraryStatistics(int borrowedBooksCount, int availableBooksCount, int acceptedRequestsCount,
            int rejectedRequestsCount, int pendingRequestsCount) {
        this.borrowedBooksCount = borrowedBooksCount;
        this.availableBooksCount = availableBooksCount;
        this.acceptedRequestsCount = acceptedRequestsCount;
        this.rejectedRequestsCount = rejectedRequestsCount;
        this.pendingRequestsCount = pendingRequestsCount;
    }

    @Override
    public String toString() {
        StringBuilder statisticsString = new StringBuilder();
        statisticsString.append("LibraryStatistics {\n");
        statisticsString.append("  Borrowed Books Count: ").append(borrowedBooksCount).append("\n");
        statisticsString.append("  Available Books Count: ").append(availableBooksCount).append("\n");
        statisticsString.append("  Accepted Requests Count: ").append(acceptedRequestsCount).append("\n");
        statisticsString.append("  Rejected Requests Count: ").append(rejectedRequestsCount).append("\n");
        statisticsString.append("  Pending Requests Count: ").append(pendingRequestsCount).append("\n");
        statisticsString.append("   }");
        return statisticsString.toString();
    }
    

    
    


}
