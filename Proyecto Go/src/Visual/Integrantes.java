package Visual;
/* @author Belen*/
import java.io.Serializable;

public class Integrantes {
    //atributos
   
    private String nick,nombre,codigodeamistad;
    private int codigo,contacto;
    private Object team;
    
    //constructores
     public Integrantes() {
    }
    
     public Integrantes(int codigo, String nick, String nombre, String codigodeamistad, int contacto, Object team) {
        this.nick = nick;
        this.nombre = nombre;
        this.codigodeamistad = codigodeamistad;
        this.codigo= codigo;
        this.contacto = contacto;
        this.team = team;
    }
    
    //getters y setters

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCodigodeamistad() {
        return codigodeamistad;
    }

    public void setCodigodeamistad(String codigodeamistad) {
        this.codigodeamistad = codigodeamistad;
    }
     public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }


    public int getContacto() {
        return contacto;
    }

    public void setContacto(int contacto) {
        this.contacto = contacto;
    }

    public Object getTeam() {
        return team;
    }

    public void setTeam(Object team) {
        this.team = team;
    }

}
