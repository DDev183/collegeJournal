package page.danya.DAO;

public class ProfileDAO {

    private String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public ProfileDAO(String username) {
        this.username = username;
    }

    public ProfileDAO() {
    }
}
