package com.tercerotest.controller.dao;

import com.tercerotest.controller.dao.implement.AdapterDao;
import com.tercerotest.controller.tda.list.LinkedList;
import com.tercerotest.models.Proyecto;
import com.tercerotest.models.enumerator.TipoEnergia;

public class ProyectoDao extends AdapterDao<Proyecto> {
    private Proyecto proyecto;
    private LinkedList listAll;

    // Constructor que inicializa el DAO con la clase Proyecto
    public ProyectoDao() {
        super(Proyecto.class);
    }

    // Obtiene un proyecto. Si no existe, lo inicializa
    public Proyecto getProyecto() {
        if (proyecto == null) {
            proyecto = new Proyecto();
        }
        return this.proyecto;
    }

    // Asigna un objeto Proyecto al atributo interno
    public void setProyecto(Proyecto proyecto) {
        this.proyecto = proyecto;
    }

    // Retorna la lista de todos los proyectos, inicializándola si es nula
    public LinkedList getListAll() {
        if (listAll == null) {
            this.listAll = listAll();
        }
        return listAll;
    }

    // Guarda el proyecto actual en la lista y actualiza la lista interna
    public Boolean save() throws Exception {
        Integer id = getListAll().getSize() + 1; // Genera un nuevo ID basado en el tamaño de la lista
        proyecto.setId(id);
        this.persist(this.proyecto); // Persiste el proyecto
        this.listAll = listAll(); // Actualiza la lista interna
        return true;
    }

    // Actualiza un proyecto existente basado en su ID
    public Boolean update() throws Exception {
        this.merge(getProyecto(), getProyecto().getId() - 1);
        this.listAll = listAll(); // Actualiza la lista interna
        return true;
    }

    // Devuelve el enumerador TipoEnergia basado en un string
    public TipoEnergia getTipoEnergia(String tipo) {
        return TipoEnergia.valueOf(tipo);
    }

    // Devuelve todos los valores posibles del enumerador TipoEnergia
    public TipoEnergia[] getTipos() {
        return TipoEnergia.values();
    }

    public LinkedList order(Integer type_order, String atributo) {
        LinkedList listita = listAll();
        if (!listAll().isEmpty()) {
            Proyecto[] lista = (Proyecto[]) listita.toArray();
            listita.reset();
            for (int i = 1; i < lista.length; i++) {
                Proyecto aux = lista[i]; // valor a ordenar
                int j = i - 1; // índice anterior
                while (j >= 0 && (verify(lista[j], aux, type_order, atributo))) {
                    lista[j + 1] = lista[j--]; // desplaza elementos hacia la derecha
                }
                lista[j + 1] = aux; // inserta el valor en su posición correcta
            }

            listita.toList(lista);
        }
        return listita;
    }

    private Boolean verify(Proyecto a, Proyecto b, Integer type_order, String atributo) {
        if (type_order == 1) {
            if (atributo.equalsIgnoreCase("nombre")) {
                return a.getNombre().compareTo(b.getNombre()) > 0;
            } else if (atributo.equalsIgnoreCase("inversionista")) {
                return a.getInversionista().compareTo(b.getInversionista()) > 0;
            } else if (atributo.equalsIgnoreCase("id")) {
                return a.getId() > b.getId();
            }
        } else if (type_order == 0){
            if (atributo.equalsIgnoreCase("nombre")) {
                return a.getNombre().compareTo(b.getNombre()) < 0;
            } else if (atributo.equalsIgnoreCase("inversionista")) {
                return a.getInversionista().compareTo(b.getInversionista()) < 0;
            } else if (atributo.equalsIgnoreCase("id")) {
                return a.getId() < b.getId();
            }
        }
        return false;
    }

    public LinkedList<Proyecto> orderQuickSort(Integer type_order, String attribute) {
        LinkedList<Proyecto> listita = listAll();
        if (!listita.isEmpty()) {
            try {
                listita.QuickSort(attribute, type_order);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return listita;
    }

    public LinkedList<Proyecto> orderMergeSort(Integer typeOrder, String attribute){
        LinkedList<Proyecto> listita = listAll();
        if (!listita.isEmpty()) {
            try {
                listita.mergeSort(attribute, typeOrder);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return listita;
    }

    public LinkedList<Proyecto> orderShellSort(Integer typeOrder, String attribute){
        LinkedList<Proyecto> listita = listAll();
        if (!listita.isEmpty()) {
            try {
                listita.shellSort(attribute, typeOrder);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return listita;
    }

    // Método de busqueda binaria

    public LinkedList<Proyecto> buscarNombreBinaria(String prfeix) {
        LinkedList<Proyecto> listita = listAll();
        LinkedList<Proyecto> reusltado = new LinkedList<>();

        if (!listita.isEmpty()) {
            listita = order(1,  "nombre");
            Proyecto[] array = listita.toArray();

            int start = 0;
            int end = array.length - 1;
            int mid = -1;

            while (start <= end) {
                mid = start + (end - start) / 2;
                int comparision = array[mid].getNombre().compareToIgnoreCase(prfeix);

                if (array[mid].getNombre().toLowerCase().startsWith(prfeix.toLowerCase())) {
                    break;
                } else if (comparision < 0) {
                    start = mid + 1;
                } else {
                    end = mid - 1;
                }
            }
            if (mid != -1 && array[mid].getNombre().toLowerCase().startsWith(prfeix.toLowerCase())) {
                int left = mid;
                while (left >= 0 && array[left].getNombre().toLowerCase().startsWith(prfeix.toLowerCase())) {
                    reusltado.add(array[left]);
                    left--;
                }

                int right = mid + 1;
                while (right < array.length
                        && array[right].getNombre().toLowerCase().startsWith(prfeix.toLowerCase())) {
                    reusltado.add(array[right]);
                    right++;
                }
            }
        }
        return reusltado;
    }

    public LinkedList<Proyecto> buscarInversorBinaria(String prefijo) {
        LinkedList<Proyecto> listita = listAll(); 
        LinkedList<Proyecto> resultados = new LinkedList<>(); 
            
        if (!listita.isEmpty()) {           
            listita = order(1, "inversionista");// ordernar a listita en ascendete por inversor
                      
            Proyecto[] arregloProyectos = listita.toArray();   // Convertir la lista a un arreglo 
    
            int inicio = 0;
            int fin = arregloProyectos.length - 1;
            int medio = -1; 
    
            
            while (inicio <= fin) {
                medio = inicio + (fin - inicio) / 2; 
                int comparacion = arregloProyectos[medio].getInversionista().compareToIgnoreCase(prefijo);
    
                if (arregloProyectos[medio].getInversionista().toLowerCase().startsWith(prefijo.toLowerCase())) {                    
                    break;
                } else if (comparacion < 0) {
                    inicio = medio + 1; 
                } else {
                    fin = medio - 1; 
                }
            }
                
            if (medio != -1 && arregloProyectos[medio].getInversionista().toLowerCase().startsWith(prefijo.toLowerCase())) {    
                int izquierda = medio;
                while (izquierda >= 0 && arregloProyectos[izquierda].getInversionista().toLowerCase().startsWith(prefijo.toLowerCase())) {
                    resultados.add(arregloProyectos[izquierda]);
                    izquierda--;
                }
                    
                int derecha = medio + 1;
                while (derecha < arregloProyectos.length
                        && arregloProyectos[derecha].getInversionista().toLowerCase().startsWith(prefijo.toLowerCase())) {
                    resultados.add(arregloProyectos[derecha]);
                    derecha++;
                }
            }
        }
    
        return resultados; 
    }
    
    
    public LinkedList<Proyecto> buscarNombreLineal(String attribute) {
        LinkedList<Proyecto> resultado = new LinkedList<>();
        LinkedList<Proyecto> listita = listAll();

        if (!listita.isEmpty()) {
            Proyecto[] array = listita.toArray();
            for (Proyecto proyecto : array) {
                if (proyecto.getNombre().toLowerCase().startsWith(attribute.toLowerCase())) {
                    resultado.add(proyecto);
                }
            }
        }
        return resultado;
    }

    public LinkedList<Proyecto> buscarInversionistaLineal(String attribute) {
        LinkedList<Proyecto> resultado = new LinkedList<>();
        LinkedList<Proyecto> listita = listAll();

        if (!listita.isEmpty()) {
            Proyecto[] array = listita.toArray();
            for (Proyecto proyecto : array) {
                if (proyecto.getInversionista().toLowerCase().startsWith(attribute.toLowerCase())) {
                    resultado.add(proyecto);
                }
            }
        }
        return resultado;
    }

}
