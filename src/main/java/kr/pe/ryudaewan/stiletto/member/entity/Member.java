package kr.pe.ryudaewan.stiletto.member.entity;

import javax.persistence.*;
import java.util.Objects;
import java.util.StringJoiner;

@Entity
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50)
    private String screenName;

    @Column(length = 1024)
    private String email;

    @Column(length = 1024)
    private String password;

    public Member() {
    }

    public Member(String screenName, String email, String password) {
        this.setEmail(email);
        this.setPassword(password);
        this.setScreenName(screenName);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Member member = (Member) o;
        return Objects.equals(id, member.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public Long getId() {
        return id;
    }

    public String getScreenName() {
        return screenName;
    }

    public void setScreenName(String screenName) {

        this.screenName = screenName;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Member.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("screenName='" + screenName + "'")
                .add("email='" + email + "'")
                .add("password='*********'")
                .toString();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
