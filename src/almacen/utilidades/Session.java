package almacen.utilidades;


public class Session {
    private final int rank;
    private final String nombre;

    private Session(final int rank, final String nombre) {
        this.rank = rank;
        this.nombre = nombre;
    }


    @SuppressWarnings("unused")
    public int getRank() {
        return rank;
    }

    public String getName() {
        return nombre;
    }

    static Session createSesion(final int rank, final String nombre){
        return new Session(rank, nombre);
    }
}