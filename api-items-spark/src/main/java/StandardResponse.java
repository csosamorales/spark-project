import com.google.gson.JsonElement;

public class StandardResponse {

    private StatusResponse status;
    private String menssage;
    private JsonElement data;

    public StandardResponse(StatusResponse status){
        this.status = status;
    }

    public StandardResponse(StatusResponse status,String message){
        this.status = status;
        this.menssage = message;
    }

    public StandardResponse(StatusResponse status, JsonElement data){
        this.status = status;
        this.data = data;
    }

    public StatusResponse getStatus() {
        return status;
    }

    public void setStatus(StatusResponse status) {
        this.status = status;
    }

    public String getMenssage() {
        return menssage;
    }

    public void setMenssage(String menssage) {
        this.menssage = menssage;
    }

    public JsonElement getData() {
        return data;
    }

    public void setData(JsonElement data) {
        this.data = data;
    }
}
