package org.unl.music.base.controller.dao.dao_models;
import org.unl.music.base.controller.Utiles;
import org.unl.music.base.controller.dao.AdapterDao;
import org.unl.music.base.controller.data_struct.list.LinkedList;
import org.unl.music.base.models.Cancion;

import java.util.HashMap;

public class DaoCancion extends AdapterDao<Cancion> {
    private Cancion obj;


    public DaoCancion() {
        super(Cancion.class);
    }

    public Cancion getObj() {
        if (obj == null)
            this.obj = new Cancion();
        return this.obj;
    }

    public void setObj(Cancion obj) {
        this.obj = obj;
    }

    public Boolean save() {
        try {
            obj.setId(listAll().getLength() + 1);
            this.persist(obj);
            return true;
        } catch (Exception e) {

            return false;

        }
    }

    public Boolean update(Integer pos) {
        try {
            this.update(obj, pos);
            return true;
        } catch (Exception e) {

            return false;

        }
    }

    public Boolean updateId(Integer id) {
        try {
            this.update(obj, id);
            return true;
        } catch (Exception e) {

            return false;

        }
    }

    public LinkedList<HashMap<String, String>> all(LinkedList<Cancion> canciones) throws Exception {
        LinkedList<HashMap<String, String>> lista = new LinkedList<>();
        if (!canciones.isEmpty()) {
            Cancion[] arreglo = canciones.toArray();
            for (int i = 0; i < arreglo.length; i++) {

                lista.add(toDict(arreglo[i]));
            }
        }
        return lista;
    }

    private HashMap<String, String> toDict(Cancion arreglo) throws Exception {
        DaoGenero da = new DaoGenero();
        DaoAlbum db = new DaoAlbum();
        HashMap<String, String> aux = new HashMap<>();
        aux.put("id", arreglo.getId().toString());
        aux.put("nombre", arreglo.getNombre());
        aux.put("duracion", arreglo.getDuracion().toString());
        aux.put("url", arreglo.getUrl());
        aux.put("tipo", arreglo.getTipo().toString());
        aux.put("genero",da.get(arreglo.getId_genero()-1).getNombre());
        aux.put("album", db.get(arreglo.getId_album()-1).getNombre());

        return aux;
    }


    private int partition(Cancion arr[], int begin, int end, Integer type) {
        // hashmap //clave - valor
        // Calendar cd = Calendar.getInstance();

        Cancion pivot = arr[end];
        int i = (begin - 1);
        if (type == Utiles.ASCEDENTE) {
            for (int j = begin; j < end; j++) {
                if (arr[j].getNombre().toLowerCase().compareTo(pivot.getNombre().toLowerCase()) < 0) {
                    // if (arr[j] <= pivot) {
                    i++;
                    Cancion swapTemp = arr[i];
                    arr[i] = arr[j];
                    arr[j] = swapTemp;
                }
            }
        } else {
            for (int j = begin; j < end; j++) {
                if (arr[j].getNombre().toLowerCase().compareTo(pivot.getNombre().toLowerCase()) > 0) {
                    // if (arr[j] <= pivot) {
                    i++;
                    Cancion swapTemp = arr[i];
                    arr[i] = arr[j];
                    arr[j] = swapTemp;
                }
            }
        }
        Cancion swapTemp = arr[i + 1];
        arr[i + 1] = arr[end];
        arr[end] = swapTemp;

        return i + 1;
    }

    private void quickSort(Cancion arr[], int begin, int end, Integer type) {
        if (begin < end) {
            int partitionIndex = partition(arr, begin, end, type);

            quickSort(arr, begin, partitionIndex - 1, type);
            quickSort(arr, partitionIndex + 1, end, type);
        }
    }

    public LinkedList<HashMap<String, String>> orderName(Integer type) throws Exception {
        LinkedList<Cancion> lista = new LinkedList<>();
        if (!listAll().isEmpty()) {

            Cancion arr[] = listAll().toArray();
            quickSort(arr, 0, arr.length - 1, type);
            lista.toList(arr);
        }
        return all(lista);
    }


    public LinkedList<HashMap<String, String>> orderUrl(Integer type) throws Exception {
        LinkedList<Cancion> lista = new LinkedList<>();
        if (!listAll().isEmpty()) {

            Cancion arr[] = listAll().toArray();
            quickSort2(arr, 0, arr.length - 1, type);
            lista.toList(arr);
        }
        return all(lista);
    }

    private int partition2(Cancion arr[], int begin, int end, Integer type) {
        // hashmap //clave - valor
        // Calendar cd = Calendar.getInstance();

        Cancion pivot = arr[end];
        int i = (begin - 1);
        if (type == Utiles.ASCEDENTE) {
            for (int j = begin; j < end; j++) {
                if (arr[j].getUrl().toLowerCase().compareTo(pivot.getUrl().toLowerCase()) < 0) {
                    // if (arr[j] <= pivot) {
                    i++;
                    Cancion swapTemp = arr[i];
                    arr[i] = arr[j];
                    arr[j] = swapTemp;
                }
            }
        } else {
            for (int j = begin; j < end; j++) {
                if (arr[j].getUrl().toLowerCase().compareTo(pivot.getUrl().toLowerCase()) > 0) {
                    // if (arr[j] <= pivot) {
                    i++;
                    Cancion swapTemp = arr[i];
                    arr[i] = arr[j];
                    arr[j] = swapTemp;
                }
            }
        }
        Cancion swapTemp = arr[i + 1];
        arr[i + 1] = arr[end];
        arr[end] = swapTemp;

        return i + 1;
    }

    private void quickSort2(Cancion arr[], int begin, int end, Integer type) {
        if (begin < end) {
            int partitionIndex = partition2(arr, begin, end, type);

            quickSort2(arr, begin, partitionIndex - 1, type);
            quickSort2(arr, partitionIndex + 1, end, type);
        }
    }


    public LinkedList<HashMap<String, String>> orderDuracion(Integer type) throws Exception {
        LinkedList<Cancion> lista = new LinkedList<>();
        if (!listAll().isEmpty()) {

            Cancion arr[] = listAll().toArray();
            quickSort3(arr, 0, arr.length - 1, type);
            lista.toList(arr);
        }
        return all(lista);
    }

    private int partition3(Cancion arr[], int begin, int end, Integer type) {
        // hashmap //clave - valor
        // Calendar cd = Calendar.getInstance();

        Cancion pivot = arr[end];
        int i = (begin - 1);
        if (type == Utiles.ASCEDENTE) {
            for (int j = begin; j < end; j++) {
                if (arr[j].getDuracion()< pivot.getDuracion()) {
                    // if (arr[j] <= pivot) {
                    i++;
                    Cancion swapTemp = arr[i];
                    arr[i] = arr[j];
                    arr[j] = swapTemp;
                }
            }
        } else {
            for (int j = begin; j < end; j++) {
                if (arr[j].getDuracion()> pivot.getDuracion())  {
                    // if (arr[j] <= pivot) {
                    i++;
                    Cancion swapTemp = arr[i];
                    arr[i] = arr[j];
                    arr[j] = swapTemp;
                }
            }
        }
        Cancion swapTemp = arr[i + 1];
        arr[i + 1] = arr[end];
        arr[end] = swapTemp;

        return i + 1;
    }

    private void quickSort3(Cancion arr[], int begin, int end, Integer type) {
        if (begin < end) {
            int partitionIndex = partition3(arr, begin, end, type);

            quickSort3(arr, begin, partitionIndex - 1, type);
            quickSort3(arr, partitionIndex + 1, end, type);
        }
    }


    public LinkedList<HashMap<String, String>> orderTipo(Integer type) throws Exception {
        LinkedList<Cancion> lista = new LinkedList<>();
        if (!listAll().isEmpty()) {

            Cancion arr[] = listAll().toArray();
            quickSort4(arr, 0, arr.length - 1, type);
            lista.toList(arr);
        }
        return all(lista);
    }

    private int partition4(Cancion arr[], int begin, int end, Integer type) {
        // hashmap //clave - valor
        // Calendar cd = Calendar.getInstance();

        Cancion pivot = arr[end];
        int i = (begin - 1);
        if (type == Utiles.ASCEDENTE) {
            for (int j = begin; j < end; j++) {
                if (arr[j].getTipo().toString().toLowerCase().compareTo(pivot.getTipo().toString().toLowerCase()) < 0) {
                    // if (arr[j] <= pivot) {
                    i++;
                    Cancion swapTemp = arr[i];
                    arr[i] = arr[j];
                    arr[j] = swapTemp;
                }
            }
        } else {
            for (int j = begin; j < end; j++) {
                if (arr[j].getTipo().toString().toLowerCase().compareTo(pivot.getTipo().toString().toLowerCase()) > 0)  {
                    // if (arr[j] <= pivot) {
                    i++;
                    Cancion swapTemp = arr[i];
                    arr[i] = arr[j];
                    arr[j] = swapTemp;
                }
            }
        }
        Cancion swapTemp = arr[i + 1];
        arr[i + 1] = arr[end];
        arr[end] = swapTemp;

        return i + 1;
    }

    private void quickSort4(Cancion arr[], int begin, int end, Integer type) {
        if (begin < end) {
            int partitionIndex = partition4(arr, begin, end, type);

            quickSort4(arr, begin, partitionIndex - 1, type);
            quickSort4(arr, partitionIndex + 1, end, type);
        }
    }


    public LinkedList<HashMap<String, String>> orderGenero(Integer type) throws Exception {
    LinkedList<Cancion> lista = new LinkedList<>();
    if (!listAll().isEmpty()) {

        Cancion arr[] = listAll().toArray();
        quickSort5(arr, 0, arr.length - 1, type);
        lista.toList(arr);
    }
    return all(lista);
}

    private int partition5(Cancion arr[], int begin, int end, Integer type) throws Exception {
        // hashmap //clave - valor
        // Calendar cd = Calendar.getInstance();
        DaoGenero daoGenero = new DaoGenero();
        Cancion pivot = arr[end];
        int i = (begin - 1);
        if (type == Utiles.ASCEDENTE) {
            for (int j = begin; j < end; j++) {
                if (daoGenero.get(arr[j].getId_genero()).getNombre().toLowerCase().compareTo(daoGenero.get(pivot.getId_genero()).getNombre().toLowerCase()) < 0) {
                    // if (arr[j] <= pivot) {
                    i++;
                    Cancion swapTemp = arr[i];
                    arr[i] = arr[j];
                    arr[j] = swapTemp;
                }
            }
        } else { //aux.put("genero",da.get(arreglo.getId_genero()).getNombre());
            for (int j = begin; j < end; j++) {
                if (daoGenero.get(arr[j].getId_genero()).getNombre().toLowerCase().compareTo(daoGenero.get(pivot.getId_genero()).getNombre().toLowerCase()) > 0)  {
                    // if (arr[j] <= pivot) {
                    i++;
                    Cancion swapTemp = arr[i];
                    arr[i] = arr[j];
                    arr[j] = swapTemp;
                }
            }
        }
        Cancion swapTemp = arr[i + 1];
        arr[i + 1] = arr[end];
        arr[end] = swapTemp;

        return i + 1;
    }

    private void quickSort5(Cancion arr[], int begin, int end, Integer type) throws Exception {
        if (begin < end) {
            int partitionIndex = partition5(arr, begin, end, type);

            quickSort5(arr, begin, partitionIndex - 1, type);
            quickSort5(arr, partitionIndex + 1, end, type);
        }
    }


    public LinkedList<HashMap<String, String>> orderAlbum(Integer type) throws Exception {
        LinkedList<Cancion> lista = new LinkedList<>();
        if (!listAll().isEmpty()) {

            Cancion arr[] = listAll().toArray();
            quickSort6(arr, 0, arr.length - 1, type);
            lista.toList(arr);
        }
        return all(lista);
    }

    private int partition6(Cancion arr[], int begin, int end, Integer type) throws Exception {
        // hashmap //clave - valor
        // Calendar cd = Calendar.getInstance();
        DaoAlbum daoAlbum = new DaoAlbum();
        Cancion pivot = arr[end];
        int i = (begin - 1);
        if (type == Utiles.ASCEDENTE) {
            for (int j = begin; j < end; j++) {
                if (daoAlbum.get(arr[j].getId_album()).getNombre().toLowerCase().compareTo(daoAlbum.get(pivot.getId_album()).getNombre().toLowerCase()) < 0) {
                    // if (arr[j] <= pivot) {
                    i++;
                    Cancion swapTemp = arr[i];
                    arr[i] = arr[j];
                    arr[j] = swapTemp;
                }
            }
        } else { //aux.put("genero",da.get(arreglo.getId_genero()).getNombre());
            for (int j = begin; j < end; j++) {
                if (daoAlbum.get(arr[j].getId_album()).getNombre().toLowerCase().compareTo(daoAlbum.get(pivot.getId_album()).getNombre().toLowerCase()) > 0)  {
                    // if (arr[j] <= pivot) {
                    i++;
                    Cancion swapTemp = arr[i];
                    arr[i] = arr[j];
                    arr[j] = swapTemp;
                }
            }
        }
        Cancion swapTemp = arr[i + 1];
        arr[i + 1] = arr[end];
        arr[end] = swapTemp;

        return i + 1;
    }

    private void quickSort6(Cancion arr[], int begin, int end, Integer type) throws Exception {
        if (begin < end) {
            int partitionIndex = partition6(arr, begin, end, type);

            quickSort6(arr, begin, partitionIndex - 1, type);
            quickSort6(arr, partitionIndex + 1, end, type);
        }
    }


    //BUSQUEDA BINARIA
    public LinkedList<HashMap<String, String>> search(String attribute, String text, Integer type) throws Exception {
        LinkedList<HashMap<String, String>> lista = all(listAll());
        LinkedList<HashMap<String, String>> resp = new LinkedList<>();

        if (!lista.isEmpty()) {
            HashMap<String, String>[] arr = lista.toArray();
            System.out.println(attribute+" "+text+" ** *** * * ** * * * *");
            switch (type) {
                case 1:
                    System.out.println(attribute+" "+text+" UNO");
                    for (HashMap m : arr) {
                        if (m.get(attribute).toString().toLowerCase().startsWith(text.toLowerCase())) {
                            resp.add(m);
                        }

                    }
                    break;
                case 2:
                    System.out.println(attribute+" "+text+" DOS");
                    for (HashMap m : arr) {
                        if (m.get(attribute).toString().toLowerCase().endsWith(text.toLowerCase())) {
                            resp.add(m);
                        }

                    }
                    break;
                default:
                    System.out.println(attribute+" "+text+" TRES");
                    for (HashMap m : arr) {
                        System.out.println("***** "+m.get(attribute)+"   "+attribute);
                        if (m.get(attribute).toString().toLowerCase().contains(text.toLowerCase())) {
                            resp.add(m);
                        }
//                        System.out.println("llego aqui?3 "+m.get(attribute));
                    }
                    break;
            }
        }
        return resp;
    }

//ORDENAMIENTO PARA LA BUSQUEDA

    private int partition7(HashMap<String, String>  arr[], int begin, int end, Integer type, String attribute) {
        // hashmap //clave - valor
        // Calendar cd = Calendar.getInstance();

        HashMap<String, String> pivot = arr[end];
        int i = (begin - 1);
        if (type == Utiles.ASCEDENTE) {
            for (int j = begin; j < end; j++) {
                if (arr[j].get(attribute).toString().toLowerCase()
                        .compareTo(pivot.get(attribute).toString().toLowerCase()) < 0) {
                    // if (arr[j] <= pivot) {
                    i++;
                    HashMap<String, String> swapTemp = arr[i];
                    arr[i] = arr[j];
                    arr[j] = swapTemp;
                }
            }
        } else {
            for (int j = begin; j < end; j++) {
                if (arr[j].get(attribute).toString().toLowerCase()
                        .compareTo(pivot.get(attribute).toString().toLowerCase()) > 0) {
                    // if (arr[j] <= pivot) {
                    i++;
                    HashMap<String, String> swapTemp = arr[i];
                    arr[i] = arr[j];
                    arr[j] = swapTemp;
                }
            }
        }
        HashMap<String, String> swapTemp = arr[i + 1];
        arr[i + 1] = arr[end];
        arr[end] = swapTemp;

        return i + 1;
    }

    private void quickSort7(HashMap<String, String> arr[], int begin, int end, Integer type, String attribute) {
        if (begin < end) {
            int partitionIndex = partition7(arr, begin, end, type, attribute);

            quickSort7(arr, begin, partitionIndex - 1, type, attribute);
            quickSort7(arr, partitionIndex + 1, end, type, attribute);
        }
    }

    public LinkedList<HashMap<String, String>> orderQ(Integer type, String attribute) throws Exception {
        LinkedList<HashMap<String, String>>  lista = new LinkedList<>();
        if (!listAll().isEmpty()) {

            HashMap<String, String> arr[] = all(listAll()).toArray();
            quickSort7(arr, 0, arr.length - 1, type, attribute);
            lista.toList(arr);
        }
        return lista;
    }




//BUSQUEDA LINEAL BINARIA

    public LinkedList<HashMap<String, String>> searchBinary(String attribute, String text, Integer type) throws Exception {
        LinkedList<HashMap<String, String>> lista = all(listAll());
        LinkedList<HashMap<String, String>> resp = new LinkedList<>();

        if (!lista.isEmpty()) {
            lista = orderQ(Utiles.ASCEDENTE, attribute);
            HashMap<String, String>[] arr = lista.toArray();
            Integer n =binaryLineal(arr,attribute, text , type);
            System.out.println("LA N de la mitad "+ n);
            switch (type) {
                case 1:

                    if (n > 0) {
                        for (int i = n; i< arr.length; i++) {
                            if (arr[i].get(attribute).toString().toLowerCase().startsWith(text.toLowerCase())){
                                resp.add(arr[i]);
                            }
                        }
                    } else if (n < 0) {
                        n *=-1;
                        for (int i = 0;  i < n; i++){
                            if (arr[i].get(attribute).toString().toLowerCase().startsWith(text.toLowerCase())){
                                resp.add(arr[i]);
                            }
                        }
                    }else {
                        for (int i = 0; i< arr.length;i++){
                            if (arr[i].get(attribute).toString().toLowerCase().contains(text.toLowerCase())){
                                resp.add(arr[i]);
                            }
                        }
                    }
                    break;
                case 2:
                    if (n > 0) {
                        for (int i = n; i< arr.length;i++) {
                            if (arr[i].get(attribute).toString().toLowerCase().endsWith(text.toLowerCase())){
                                resp.add(arr[i]);
                            }
                        }
                    } else if (n < 0 ) {
                        n *= -1;
                        for (int i = 0; i < n; i++){
                            if (arr[i].get(attribute).toString().toLowerCase().endsWith(text.toLowerCase())){
                                resp.add(arr[i]);
                            }
                        }
                    }else{
                        for (int i = 0; i< arr.length;i++){
                            if (arr[i].get(attribute).toString().toLowerCase().contains(text.toLowerCase())){
                                resp.add(arr[i]);
                            }
                        }
                    }
                    break;
                default:
                    for (int i = 0; i< arr.length; i++){
                        if (arr[i].get(attribute).toString().toLowerCase().contains(text.toLowerCase())){
                            resp.add(arr[i]);
                        }
                    }
                    break;
            }
        }
        return resp;
    }



    private Integer binaryLineal(HashMap<String, String>[] array, String attribute, String text, Integer type ){
        Integer half = 0;
        if(!(array.length == 0) && !text.isEmpty()){
            half = array.length/2;
            int aux = 0;
            System.out.println(text.trim().toLowerCase().charAt(0)+" *** ** ** "+half+" "+array[half]
                    .get(attribute).toString().trim().toLowerCase().charAt(0));
            if (text.trim().toLowerCase().charAt(0) > array[half].get(attribute)
                    .toString().trim().toLowerCase().charAt(0)){
                aux = 1;
            } else if (text.trim().toLowerCase().charAt(0) < array[half]
                    .get(attribute).toString().trim().toLowerCase().charAt(0)) {
                aux = -1;
            }half =half*aux;
        }
        return half;
    }


}


