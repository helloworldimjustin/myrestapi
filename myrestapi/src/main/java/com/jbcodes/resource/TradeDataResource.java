package com.jbcodes.resource;

import com.jbcodes.repository.Trade;
import com.jbcodes.repository.TradeDataRepository;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.sql.SQLException;
import java.util.List;


/**
 * Root resource (exposed at "myresource" path)
 */
@Path("tradedataresource")
public class TradeDataResource {

    private TradeDataRepository repository;

    public TradeDataResource() {

        try {
            repository = new TradeDataRepository();
        }catch (SQLException e){
            e.printStackTrace();
        }

    }

    /**
     * Method handling HTTP GET requests. The returned object will be sent
     * to the client as "text/plain" media type.
     *
     * @return String that will be returned as a text/plain response.
     */
    @GET
    @Path("get")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public List<Trade> getData(){
        System.out.println("TradeRepository.get() called");
        return repository.get();
    }

    @POST
    @Path("post")
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
     public void postTrade(Trade trade){
        repository.create(trade);
        System.out.println("TradeRepository.putTrade() called");
        System.out.println(trade.toString());
    }

    @PUT
    @Path("put")
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public void updateTrade(Trade trade){
        repository.update(trade);
        System.out.println("updateTrade() called");
        System.out.println(trade.toString());
    }

    @DELETE
    @Path("delete")
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public void deleteTrade(Trade trade){
        repository.delete(trade);
        System.out.println("deleteTrade() called");
        System.out.println(trade.toString());

    }


}
