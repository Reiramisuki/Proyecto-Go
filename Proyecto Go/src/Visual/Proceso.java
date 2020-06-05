package Visual;

import java.util.ArrayList;

/**
 *
 * @author Belen
 */
public class Proceso {
    private ArrayList<Object> a = new ArrayList<Object>();
    
    public Proceso(){}
    
    public Proceso(ArrayList<Object> a){
        this.a = a;
    }
    
    public void agregarRegistro(Integrantes p){
        this.a.add(p);
    }

    public void modificarRegistro(int i,Integrantes p){
        this.a.set(i, p);
    }
    
    public void eliminarRegistro(int i){
        this.a.remove(i);
    }
    
    public Integrantes obtenerRegistro(int i){
        return (Integrantes)a.get(i);
    }
    
    public int cantidadRegistro(){
        return this.a.size();
    }
    
    public int buscaCodigo(int codigo){
        for(int i = 0; i < cantidadRegistro(); i++){
            if(codigo == obtenerRegistro(i).getCodigo())return i;
        }
        return -1;
    }
    
   
    
}
