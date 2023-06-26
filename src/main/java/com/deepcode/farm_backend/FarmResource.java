package com.deepcode.farm_backend;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.deepcode.dao.FarmDb;
import com.deepcode.model.Farm;
import com.deepcode.request.FarmId;
import com.deepcode.response.ResponseModel;

/**
 * Root resource (exposed at "myresource" path)
 */
@Path("/farm")
public class FarmResource {

	/**
	 * Method handling HTTP GET requests. The returned object will be sent to the
	 * client as "text/plain" media type.
	 *
	 * @return String that will be returned as a text/plain response.
	 */
	@POST
	@Path("add")
	@Produces(MediaType.APPLICATION_JSON + ";charset=UTF-8")

	public Response insert(Farm farm) throws Exception {

		System.out.println("Da vao ham add Farm: "+ farm.toString());
		farm = FarmDb.getInstance().add(farm);
		return Response.ok(farm).build();
	}

	@POST
	@Path("update")
	@Produces(MediaType.APPLICATION_JSON + ";charset=UTF-8")

	public Response update(Farm farm) throws Exception {
		farm = FarmDb.getInstance().update(farm);
		return Response.ok(farm).build();
	}

	@POST
	@Path("delete")
	@Produces(MediaType.APPLICATION_JSON + ";charset=UTF-8")

	public Response delete(FarmId farmId) throws Exception {
		Boolean success = FarmDb.getInstance().delete(farmId.getId());
		ResponseModel res = new ResponseModel();

		if (success) {
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
	public Response getById(FarmId farmId) throws Exception {

		Farm farm = FarmDb.getInstance().getById(farmId.getId());
		return Response.ok(farm).build();
	}

	@POST
	@Path("getAll")
	@Produces(MediaType.APPLICATION_JSON + ";charset=UTF-8")
	public Response getAll() throws Exception {
		List<Farm> list = new ArrayList<Farm>();
		list = FarmDb.getInstance().getAll();

		return Response.ok(list).build();
	}

}
