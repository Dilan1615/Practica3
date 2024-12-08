package com.tercerotest.rest;

import java.util.HashMap;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.google.gson.Gson;

import com.tercerotest.controller.dao.services.ProyectoServices;
import com.tercerotest.controller.tda.list.LinkedList;
import com.tercerotest.models.Proyecto;

@Path("proyecto")
public class ProyectoApi {
    @Path("/list")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllProyectos() {
        HashMap<String, Object> map = new HashMap<>();
        ProyectoServices ps = new ProyectoServices();
        map.put("msg", "OK");
        map.put("data", ps.listAll().toArray());
        if (ps.listAll().isEmpty()) {
            map.put("data", new Object[] {});
        }

        return Response.ok(map).build();
    }
    

    @Path("/get/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPerson(@PathParam("id") Integer id) {
        HashMap<String, Object> map = new HashMap<>();
        ProyectoServices ps = new ProyectoServices();
        try {
            ps.setProyecto(ps.get(id));
        } catch (Exception e) {

        }
        map.put("msg", "OK");
        map.put("data", ps.getProyecto());
        if (ps.getProyecto().getId() == null) {
            map.put("data", "No ningun proyecto con ese identificador");
            return Response.status(Status.BAD_REQUEST).entity(map).build();
        }
        return Response.ok(map).build();
    }

    @Path("/listType")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getType() {
        HashMap<String, Object> map = new HashMap<>();
        ProyectoServices ps = new ProyectoServices();
        map.put("msg", "OK");
        map.put("data", ps.getTipos());
        return Response.ok(map).build();
    }

    @Path("/save")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response save(HashMap<String, Object> map) {
        HashMap<String, Object> res = new HashMap<>();
        Gson g = new Gson();
        String a = g.toJson(map);
        System.out.println("******* " + a);
        try {
            // Validación de datos
            if (map.get("nombre") == null ||
                    map.get("inversionista") == null || map.get("inversion") == null ||
                    map.get("tiempoVida") == null || map.get("inicio") == null ||
                    map.get("fin") == null || map.get("tipo") == null) {
                res.put("msg", "Error");
                res.put("data", "Datos incompletos");
                return Response.status(Status.BAD_REQUEST).entity(res).build();
            }

            ProyectoServices ps = new ProyectoServices();
            ps.getProyecto().setNombre(map.get("nombre").toString());
            ps.getProyecto().setInversionista(map.get("inversionista").toString());
            ps.getProyecto().setInversion(Float.parseFloat(map.get("inversion").toString()));
            ps.getProyecto().setTiempoVida(map.get("tiempoVida").toString());
            ps.getProyecto().setInicio(map.get("inicio").toString());
            ps.getProyecto().setFin(map.get("fin").toString());

            ps.getProyecto().setTipo(ps.getTipoEnergia(map.get("tipo").toString()));

            ps.save();
            res.put("msg", "OK");
            res.put("data", "Proyecto registrado correctamente");
            return Response.ok(res).build();
        } catch (Exception e) {
            System.out.println("Error en save data " + e.toString());
            res.put("msg", "Error");
            res.put("data", e.toString());
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(res).build();
        }
    }

    @Path("/update")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response update(HashMap<String, Object> map) {

        HashMap<String, Object> res = new HashMap<>();
        try {
            ProyectoServices ps = new ProyectoServices();
            ps.setProyecto(ps.get(Integer.parseInt(map.get(("id")).toString())));
            ps.getProyecto().setNombre(map.get("nombre").toString());
            ps.getProyecto().setInversionista(map.get("inversionista").toString());
            ps.getProyecto().setInversion(Float.parseFloat(map.get("inversion").toString()));
            ps.getProyecto().setTiempoVida(map.get("tiempoVida").toString());
            ps.getProyecto().setInicio(map.get("inicio").toString());
            ps.getProyecto().setFin(map.get("fin").toString());

            ps.getProyecto().setTipo(ps.getTipoEnergia(map.get("tipo").toString()));
            ps.update();
            res.put("msg", "OK");
            res.put("data", "Proyecto actualizzado correctamente");
            return Response.ok(res).build();

        } catch (Exception e) {
            System.out.println("Error en sav data " + e.toString());
            res.put("msg", "Error");
            // res.put("msg", "ERROR");
            res.put("data", e.toString());
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(res).build();
        }

    }

    @Path("/list/searchBinaryName/{texto}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response search_binary_nombre(@PathParam("texto") String texto) {
        HashMap<String, Object> map = new HashMap<>();
        ProyectoServices ps = new ProyectoServices();

        System.out.println("List size: " + ps.listAll().getSize());
        LinkedList<Proyecto> lista = ps.buscarNombreBinaria(texto);
        if (lista.isEmpty()) {
            map.put("msg", "No se encontraron resultados");
            map.put("data", new Object[] {});
            return Response.status(Status.BAD_REQUEST).entity(map).build();
        }

        map.put("msg", "OK");
        map.put("data", lista.toArray());
        return Response.ok(map).build();
    }

    @Path("/list/searchBinaryInversor/{texto}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response search_binary_inversionista(@PathParam("texto") String texto) {
        HashMap<String, Object> map = new HashMap<>();
        ProyectoServices ps = new ProyectoServices();

        System.out.println("List size: " + ps.listAll().getSize());
        LinkedList<Proyecto> lista = ps.buscarInversorBinaria(texto);
        if (lista.isEmpty()) {
            map.put("msg", "No se encontraron resultados");
            map.put("data", new Object[] {});
            return Response.status(Status.BAD_REQUEST).entity(map).build();
        }

        map.put("msg", "OK");
        map.put("data", lista.toArray());
        return Response.ok(map).build();
    }

    @Path("/list/searchLinealName/{texto}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response search_lineal_nombre(@PathParam("texto") String texto) {
        HashMap<String, Object> map = new HashMap<>();
        ProyectoServices ps = new ProyectoServices();

        LinkedList<Proyecto> lista = ps.buscarNombreLineal(texto);
        if (lista.isEmpty()) {
            map.put("msg", "No se encontraron resultados");
            map.put("data", new Object[] {});
            return Response.status(Status.BAD_REQUEST).entity(map).build();
        }

        map.put("msg", "OK");
        map.put("data", lista.toArray());
        return Response.ok(map).build();
    }

    @Path("/list/searchLinealInversor/{texto}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response search_lineal_inversinista(@PathParam("texto") String texto) {
        HashMap<String, Object> map = new HashMap<>();
        ProyectoServices ps = new ProyectoServices();

        LinkedList<Proyecto> lista = ps.buscarInversionistaLineal(texto);
        if (lista.isEmpty()) {
            map.put("msg", "No se encontraron resultados");
            map.put("data", new Object[] {});
            return Response.status(Status.BAD_REQUEST).entity(map).build();
        }

        map.put("msg", "OK");
        map.put("data", lista.toArray());
        return Response.ok(map).build();

    }

    @Path("/list/orderShell/{attribute}/{type}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response orderShellSort(@PathParam("attribute") String attribute, @PathParam("type") Integer type) {
        HashMap<String, Object> map = new HashMap<>();
        ProyectoServices ps = new ProyectoServices();
        map.put("msg", "OK");

        try {
            LinkedList<Proyecto> lista = ps.orderShellSort(type, attribute);
            map.put("data", lista.isEmpty() ? new Object[] {} : lista.toArray());
        } catch (Exception e) {
            map.put("msg", "Error: " + e.getMessage());
        }

        return Response.ok(map).build();
    }

    @Path("/list/orderMerge/{attribute}/{type}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response orderQuicMergeSort(@PathParam("attribute") String attribute, @PathParam("type") Integer type) {
        HashMap<String, Object> map = new HashMap<>();
        ProyectoServices ps = new ProyectoServices();
        map.put("msg", "OK");

        try {
            LinkedList<Proyecto> lista = ps.orderMergeSort(attribute, type);
            map.put("data", lista.isEmpty() ? new Object[] {} : lista.toArray());
        } catch (Exception e) {
            map.put("msg", "Error: " + e.getMessage());
        }

        return Response.ok(map).build();
    }
    @Path("/list/orderQuick/{attribute}/{type}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response orderQuickSort(@PathParam("attribute") String attribute, @PathParam("type") Integer type) {
        HashMap<String, Object> map = new HashMap<>();
        ProyectoServices ps = new ProyectoServices();
        map.put("msg", "OK");

        try {
            LinkedList<Proyecto> lista = ps.orderQuickSort(type, attribute);
            map.put("data", lista.isEmpty() ? new Object[] {} : lista.toArray());
        } catch (Exception e) {
            map.put("msg", "Error: " + e.getMessage());
        }

        return Response.ok(map).build();
    }
}
