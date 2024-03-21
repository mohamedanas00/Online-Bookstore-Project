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
        responseString.append("RequestsResponse{\n");
        responseString.append("status='").append(getStatus()).append("',\n");
        responseString.append("message='").append(getMessage()).append("',\n");
        for (Request req : requests) {
            responseString.append(req).append(",\n");
        }
        responseString.append("]\n");
        responseString.append("}");
        return responseString.toString();
    }

    
    


}
