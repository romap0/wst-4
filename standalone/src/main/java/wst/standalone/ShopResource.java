package wst.standalone;

import wst.dao.ShopDAO;
import wst.entity.Shop;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import java.util.List;

@Path("/shops")
@Produces({MediaType.APPLICATION_JSON})
public class ShopResource {
    @GET
    public List<Shop> read(
            @QueryParam("name") String name,
            @QueryParam("city") String city,
            @QueryParam("isActive") Boolean isActive,
            @QueryParam("type") String type,
            @QueryParam("address") String address) throws Exception {
        ShopDAO shopDAO = new ShopDAO();

        try {
            return shopDAO.read(name, city, address, isActive, type);
        } catch (Exception err) {
            System.out.println(err.getMessage());
            throw err;
        }
    }
}