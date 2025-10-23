package ClasesDAO;

public class DAOFactory {

    private static DAOFactory instancia;

    private DAOFactory() {}

    public static DAOFactory getInstancia() {
        if (instancia == null) {
            instancia = new DAOFactory();
        }
        return instancia;
    }

    public HuespedDAO getHuespedDAO() {
        return HuespedDAOImp.getInstancia();
    }

    public UsuarioDAO getUsuarioDAO() {
        return UsuarioDAOImp.getInstancia();
    }

}
