package wst.client;

import wst.entity.Shop;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.client.ClientConfig;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class WebServiceClient {
    private static BufferedReader in;
    private static final String URL = "http://localhost:8080/app/shops";

    public static void main(String[] args) throws IOException {
        Client client = ClientBuilder.newClient( new ClientConfig() );

        in = new BufferedReader(new InputStreamReader(System.in));

        while (true) {
            printList(read(client));
        }
    }

    public static List<Shop> read(Client client) {
        String name = getColumn("Name: ");
        String city = getColumn("City: ");
        String address = getColumn("Address: ");
        Boolean isActive = getBooleanColumn("isActive (y/n): ");
        String type = getColumn("Type: ");

        WebTarget webTarget = client.target(URL);

        webTarget = webTarget.queryParam("name", name);
        webTarget = webTarget.queryParam("city", city);
        webTarget = webTarget.queryParam("address", address);
        webTarget = webTarget.queryParam("isActive", isActive);
        webTarget = webTarget.queryParam("type", type);

        Response response = webTarget.request(MediaType.APPLICATION_JSON_TYPE).get();

        if (response.getStatus() != Response.Status.OK.getStatusCode()) {
            throw new IllegalStateException("Request failed\n" + response.getStatus() + "\n" + response.getEntity());
        }

        List<Shop> shops = response.readEntity(new GenericType<List<Shop>>(){});

        return shops;
    }

    private static void printList(List<Shop> stations) {
        for (Shop shop : stations) {
            System.out.println(shop);
        }
    }

    private static String checkNull(String s) {
        return s.length() == 0 ? null : s;
    }

    private static Boolean checkBool(String s) {
        if (s.length() == 0) return null;
        return s.equals("y") ? Boolean.TRUE : Boolean.FALSE;
    }

    private static String getColumn(String msg) {
        System.out.print(msg);
        try {
            return checkNull(in.readLine());
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static Boolean getBooleanColumn(String msg) {
        System.out.print(msg);
        try {
            return checkBool(in.readLine());
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    private static int getIntColumn(String msg) {
        System.out.print(msg);
        try {
            return Integer.parseInt(in.readLine());
        } catch (IOException e) {
            e.printStackTrace();
            return -1;
        }
    }
}