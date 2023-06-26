package com.deepcode.farm_backend;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.deepcode.dao.CategoryDb;
import com.deepcode.model.Category;
import com.deepcode.request.CategoryId;
import com.deepcode.response.ResponseModel;

/**
 * Root resource (exposed at "myresource" path)
 */
@Path("/category")
public class CategoryResource {

    /**
     * Method handling HTTP GET requests. The returned object will be sent
     * to the client as "text/plain" media type.
     *
     * @return String that will be returned as a text/plain response.
     */
    @POST
    @Path("add")
    @Produces(MediaType.APPLICATION_JSON + ";charset=UTF-8")
    
    public Response insert(Category category) throws Exception {
    	
    	System.out.println("Category:"+ category.toString());
    	category = CategoryDb.getInstance().add(category);
    	return Response.ok(category).build();
    	}
    
    @POST
    @Path("update")
    @Produces(MediaType.APPLICATION_JSON + ";charset=UTF-8")
    
    public Response update(Category category) throws Exception {
    	category = CategoryDb.getInstance().update(category);
    	return Response.ok(category).build();
    	}
    
    @POST
    @Path("delete")
    @Produces(MediaType.APPLICATION_JSON + ";charset=UTF-8")
    
    public Response delete(CategoryId categoryid) throws Exception {
    	Boolean success = CategoryDb.getInstance().delete(categoryid.getId());
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
    public Response getById(CategoryId categoryid) throws Exception {
    	
    	Category category = CategoryDb.getInstance().getById(categoryid.getId());
		return Response.ok(category).build();
    }
    
    
    
    @POST
    @Path("getAll")
    @Produces(MediaType.APPLICATION_JSON + ";charset=UTF-8")
    public Response getAll() throws Exception{
    	List<Category> list = new ArrayList<Category>();
    		list = CategoryDb.getInstance().getAll();
    		
    	return Response.ok(list).build();
    }
    
    
    
}
