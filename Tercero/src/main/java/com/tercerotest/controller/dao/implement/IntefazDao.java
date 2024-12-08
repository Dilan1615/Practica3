package com.tercerotest.controller.dao.implement;

import com.tercerotest.controller.tda.list.LinkedList;

public interface IntefazDao <T> {
    public void persist(T object) throws Exception;// agregar un nuevo tipo de dato a la lista
    public void merge(T object, Integer index) throws Exception; // modificar un tipo de dato de la lista el index es el id del objeto
    public LinkedList<T> listAll(); // duevle la losta de todos los objetos
    public T get(Integer id) throws Exception; // reucpera el id de todos los objectos
}
