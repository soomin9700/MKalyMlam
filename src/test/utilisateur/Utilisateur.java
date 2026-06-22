package utilisateur;

import java.util.Iterator;
import java.util.Vector;

public class Utilisateur {

    private String nom;
    private String prenom;
    private String email;
    private String motDePasse;

    /*
      A remplacer par findAll() via  Repository 
     */
    private static Vector utilisateurs = new Vector();

    public Utilisateur(String nom, String prenom, String email, String motDePasse) {
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.motDePasse = motDePasse;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMotDePasse() {
        return motDePasse;
    }

    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }

    public boolean estInscrit() {

        Iterator it = utilisateurs.iterator();

        while (it.hasNext()) {
            Utilisateur u = (Utilisateur) it.next();

            if (this.email.equals(u.getEmail()) &&
                this.motDePasse.equals(u.getMotDePasse())) {
                return true;
            }
        }

        return false;
    }

    public void insert() {

        utilisateurs.add(this);
    }
}