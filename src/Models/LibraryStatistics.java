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
        return "LibraryStatistics ["+'\n'+" borrowedBooksCount=" + borrowedBooksCount + '\n'+" availableBooksCount="
                + availableBooksCount + '\n'+" acceptedRequestsCount=" + acceptedRequestsCount + '\n'+" rejectedRequestsCount="
                + rejectedRequestsCount +'\n'+ " pendingRequestsCount=" + pendingRequestsCount + "]";
    }

    
    


}
