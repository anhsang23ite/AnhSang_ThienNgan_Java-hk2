package Model;

public class User {
    private int id;
    private String username;
    private String nickname;
    private String password;
    private String quyen;

    // Constructor
    public User(int id, String username, String nickname, String password, String quyen) {
        this.id = id;
        this.username = username;
        this.nickname = nickname;
        this.password = password;
        this.quyen = quyen;
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getNickname() {
        return nickname;
    }

    public String getPassword() {
        return password;
    }

    public String getQuyen() {
        return quyen;
    }
}
