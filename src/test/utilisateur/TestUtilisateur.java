package utilisateur;

public class TestUtilisateur {

    public static void main(String[] args) {

        Utilisateur u1 = new Utilisateur(
                "Adrien",
                "J",
                "adrien@mail.com",
                "mdp"
        );

        Utilisateur u2 = new Utilisateur(
                "Mark",
                "M",
                "mark@mail.com",
                "1234"
        );

        Utilisateur u3 = new Utilisateur(
                "Sara",
                "S",
                "sara@mail.com",
                "pass"
        );

        u1.insert();
        u2.insert();
        u3.insert();

        Utilisateur user1 = new Utilisateur(
                "",
                "",
                "adrien@mail.com",
                "mdp"
        );

        Utilisateur user2 = new Utilisateur(
                "",
                "",
                "inconnu@mail.com",
                "abcd"
        );

        if (user1.estInscrit()) {
            System.out.println("user1 : utilisateur trouvé");
        } else {
            System.out.println("user1 : utilisateur non trouvé");
        }

        if (user2.estInscrit()) {
            System.out.println("user2 : utilisateur trouvé");
        } else {
            System.out.println("user2 : utilisateur non trouvé");
        }
    }
}