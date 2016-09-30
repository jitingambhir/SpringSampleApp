package io.spring.start.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Company.
 */
@Entity
@Table(name = "company")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "company")
public class Company implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "company_users",
               joinColumns = @JoinColumn(name="companies_id", referencedColumnName="ID"),
               inverseJoinColumns = @JoinColumn(name="users_id", referencedColumnName="ID"))
    private Set<User> users = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Company name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<User> getUsers() {
        return users;
    }

    public Company users(Set<User> users) {
        this.users = users;
        return this;
    }

    public Company addUser(User user) {
        users.add(user);
        return this;
    }

    public Company removeUser(User user) {
        users.remove(user);
        return this;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Company company = (Company) o;
        if(company.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, company.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Company{" +
            "id=" + id +
            ", name='" + name + "'" +
            '}';
    }
}
