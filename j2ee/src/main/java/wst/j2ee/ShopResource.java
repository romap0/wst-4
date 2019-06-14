package wst.j2ee;


import wst.dao.ShopDAO;
import wst.entity.Shop;

import javax.annotation.Resource;
import javax.enterprise.context.RequestScoped;
import javax.sql.DataSource;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@RequestScoped
@Path("/shops")
@Produces({MediaType.APPLICATION_JSON})
public class ShopResource {
    @Resource(lookup = "jdbc/postgres")
    private DataSource dataSource;

    @GET
    public List<Shop> read(@QueryParam("name") String name, @QueryParam("city") String city,
                              @QueryParam("isActive") Boolean isActive, @QueryParam("type") String type,
                              @QueryParam("address") String address) throws Exception {
        ShopDAO shopDAO = new ShopDAO(getConnection());
        List<Shop> shops = shopDAO.read(name, city, address, isActive, type);
        
        return shops;
    }

    private Connection getConnection() {
        Connection result = null;
        try {
            result = dataSource.getConnection();
        } catch (SQLException ex) {
            Logger.getLogger(ShopResource.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
}
