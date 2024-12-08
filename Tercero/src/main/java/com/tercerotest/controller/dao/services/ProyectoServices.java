package com.tercerotest.controller.dao.services;

import com.tercerotest.controller.dao.ProyectoDao;
import com.tercerotest.controller.tda.list.LinkedList;
import com.tercerotest.models.Proyecto;
import com.tercerotest.models.enumerator.TipoEnergia;

public class ProyectoServices {

    private ProyectoDao obj;

    // Constructor que inicializa el servicio con un objeto ProyectoDao
    public ProyectoServices() {
        obj = new ProyectoDao();
    }

    // Guarda el proyecto actual a través del DAO
    public Boolean save() throws Exception {
        return obj.save();
    }

    // Actualiza el proyecto actual a través del DAO
    public Boolean update() throws Exception {
        return obj.update();
    }

    // Obtiene la lista de todos los proyectos
    public LinkedList listAll() {
        return obj.getListAll();
    }

    // Obtiene el proyecto actual
    public Proyecto getProyecto() {
        return obj.getProyecto();
    }

    // Asigna un proyecto actual
    public void setProyecto(Proyecto proyecto) {
        obj.setProyecto(proyecto);
    }

    // Obtiene un tipo de energía basado en un string
    public TipoEnergia getTipoEnergia(String tipo) {
        return obj.getTipoEnergia(tipo);
    }

    // Obtiene todos los tipos de energía disponibles
    public TipoEnergia[] getTipos() {
        return obj.getTipos();
    }

    // Obtiene un proyecto específico por su ID
    public Proyecto get(Integer id) throws Exception {
        return obj.get(id);
    }


    // Ordena la lista de proyectos utilizando QuickSort (implementación del DAO)
    public LinkedList<Proyecto> orderQuickSort(Integer type_order, String attribute) throws Exception {
        return obj.listAll().QuickSort(attribute, type_order);
    }

    public LinkedList<Proyecto> orderMergeSort(String attribute, Integer type_order) throws Exception {
        return obj.listAll().mergeSort(attribute, type_order);
    }        

    public LinkedList<Proyecto> orderShellSort(Integer type_order, String attribute) throws Exception {
        return obj.listAll().shellSort(attribute, type_order);
    }        

    public LinkedList<Proyecto> buscarNombreBinaria(String texto) {
        return obj.buscarNombreBinaria(texto);
    }

    public LinkedList<Proyecto> buscarInversorBinaria(String texto) {
        return obj.buscarInversorBinaria(texto);
    }
    public LinkedList<Proyecto> buscarNombreLineal(String attribute) {
        return obj.buscarNombreLineal(attribute);
    }

    public LinkedList<Proyecto> buscarInversionistaLineal(String attribute) {
        return obj.buscarInversionistaLineal(attribute);
    }

}
