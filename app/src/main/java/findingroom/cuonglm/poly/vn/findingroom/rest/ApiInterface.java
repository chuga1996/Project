package findingroom.cuonglm.poly.vn.findingroom.rest;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiInterface {
    @GET("")
    Call getContacts();

}
