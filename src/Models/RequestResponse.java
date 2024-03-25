package Models;

import java.util.List;

public class RequestResponse extends GlobalResponse{
        private List<Request> requests;

    public RequestResponse(int status, String message,List<Request> requests) {
        super(status, message);
        this.requests = requests;
    }

    @Override
    public String toString() {
        StringBuilder responseString = new StringBuilder();
        responseString.append("------------------------------\n");

        responseString.append("RequestsResponse:{\n");
        responseString.append("  Status: '").append(getStatus()).append("',\n");
        responseString.append("  Message: '").append(getMessage()).append("',\n");
        responseString.append("  Requests: [\n");
        for (Request req : requests) {
            responseString.append(req).append("\n");
        }
        responseString.append("  ]\n");
        responseString.append("}");
        responseString.append("\n------------------------------");
        return responseString.toString();
    }
    
    public List<Request> getRequests() {
        return requests;
    }

    
    


}
