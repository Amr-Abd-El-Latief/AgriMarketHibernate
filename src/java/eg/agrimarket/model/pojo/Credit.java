/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eg.agrimarket.model.pojo;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Israa
 */
@Entity
@Table(name = "credit")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Credit.findAll", query = "SELECT c FROM Credit c"),
    @NamedQuery(name = "Credit.findByNumber", query = "SELECT c FROM Credit c WHERE c.number = :number"),
    @NamedQuery(name = "Credit.findByBalance", query = "SELECT c FROM Credit c WHERE c.balance = :balance")})
public class Credit implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "number")
    private String number;
    @Basic(optional = false)
    @Column(name = "balance")
    private int balance;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "creditNumber")
    private User user;

    public Credit() {
    }

    public Credit(String number) {
        this.number = number;
    }

    public Credit(String number, int balance) {
        this.number = number;
        this.balance = balance;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (number != null ? number.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Credit)) {
            return false;
        }
        Credit other = (Credit) object;
        if ((this.number == null && other.number != null) || (this.number != null && !this.number.equals(other.number))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.pojo.Credit[ number=" + number + " ]";
    }
    
}
