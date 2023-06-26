package com.deepcode.farm_backend;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.deepcode.dao.ProductGroupDb;
import com.deepcode.model.ProductGroup;
import com.deepcode.request.ProductGroupId;
import com.deepcode.response.ResponseModel;

/**
 * Root resource (exposed at "myresource" path)
 */
@Path("/productgroup")
public class ProductGroupResource {

    /**
     * Method handling HTTP GET requests. The returned object will be sent
     * to the client as "text/plain" media type.
     *
     * @return String that will be returned as a text/plain response.
     */
    @POST
    @Path("add")
    @Produces(MediaType.APPLICATION_JSON + ";charset=UTF-8")
    
    public Response insert(ProductGroup productGroup) throws Exception {
    	
    	productGroup = ProductGroupDb.getInstance().add(productGroup);
    	return Response.ok(productGroup).build();
    	}
    
    @POST
    @Path("update")
    @Produces(MediaType.APPLICATION_JSON + ";charset=UTF-8")
    
    public Response update(ProductGroup productGroup) throws Exception {
    	productGroup = ProductGroupDb.getInstance().update(productGroup);
    	return Response.ok(productGroup).build();
    	}
    
    @POST
    @Path("delete")
    @Produces(MediaType.APPLICATION_JSON + ";charset=UTF-8")
    
    public Response delete(ProductGroupId id) throws Exception {
    	Boolean success = ProductGroupDb.getInstance().delete(id.getId());
    	ResponseModel res = new ResponseModel();
    	
    	if(success) {
    		res.setCode(200);
    		res.setMessage("Xoa thanh cong");
    		res.setSuccess(true);
    	} else {
    		res.setCode(-1);
    		res.setMessage("Xoa that bai");
    		res.setSuccess(false);
    	}
    	
    	return Response.ok(res).build();
    	}
    
    @POST
    @Path("getbyid")
    @Produces(MediaType.APPLICATION_JSON + ";charset=UTF-8")
    public Response getById(ProductGroupId id) throws Exception {
    	
    	ProductGroup productGroup = ProductGroupDb.getInstance().getById(id.getId());
		return Response.ok(productGroup).build();
    }
    
    
    
    @POST
    @Path("getAll")
    @Produces(MediaType.APPLICATION_JSON + ";charset=UTF-8")
    public Response getAll() throws Exception{
    	List<ProductGroup> list = new ArrayList<ProductGroup>();
    		list = ProductGroupDb.getInstance().getAll();
    		
    	return Response.ok(list).build();
    }
    
    
    
}
