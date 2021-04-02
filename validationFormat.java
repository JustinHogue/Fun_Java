import java.util.*;

/**
 * La classe validationFormat fournit deux fonctions, une pour vérifier si un numéro de téléphone est
 * un numéro de téléphone canadien valide et l'autre pour vérifier si un numéro de RAMQ est un numéro de
 * RAMQ valide au Québec.
 */

class validationFormat 
{
    public static boolean validerTelephone(String telephone) {
        if (telephone.length() != 12) {
            /* La longueur du numéro de téléphone est non valide. */
            return false;
        }
        ArrayList<String> indicateursRegionaux = new ArrayList<String>( /* Indicateurs régionaux de Wikipédia */
            Arrays.asList("403", "780", "604", "236", "250","873", "306", "709","778", "902",
            "204", "506", "902", "905", "519", "289", "705", "613","867", "867", "800", "966",
            "807", "416", "647", "438", "514", "450", "579", "418", "581", "819")
            );
        if (!indicateursRegionaux.contains(telephone.substring(0, 3))) {
            /* L'indicateur régional n'est pas valide. */
            return false;
        }
        String numeroLocal = telephone.substring(telephone.length() - 9); /* Seulement le numéro local. */
        for(int i = 0; i < numeroLocal.length(); i++)
        {
            char c = numeroLocal.charAt(i);
            if (i != 0 && i != 4 && !Character.isDigit(c)) {
                /* Il faut que ce soit tous des chiffres sauf le trait d'union et l'espace. */
                return false;
            }
            if (i == 4 && c != '-') {
                /* Il n'y a pas de trait d'union séparant les deux numéros locaux. */
                return false;
            }
            if (i == 0 && c != ' ') {
                /* Il n'y a pas d'espace entre l'indicateur régional et le numéro local. */
                return false;
            }
        }
        return true; /* Si toutes les conditions sont respectées, c'est que le numéro de téléphone est valide. */
    }

    public static boolean validerRAMQ(String numeroRAMQ, String nom, String prenom, byte jourNaissance, 
    byte moisNaissance, int anneeNaissance, char sexe) {
        if (numeroRAMQ.length() != 14) { // On s'assure que le numéro de RAMQ à la taille valide.
            return false;
        }

        // Il nous faut absolument une lettre au début du prénom et trois au début du nom
        if (!Character.isLetter(prenom.charAt(0)) || !Character.isLetter(nom.charAt(0)) || !Character.isLetter(nom.charAt(1)) || !Character.isLetter(nom.charAt(2))) {
            return false;
        }

        String nomComplet = prenom + nom;
        for (char ch: nomComplet.toCharArray()) {
            if (!(Character.isLetter(ch) || ch == ' ' || ch == '-')) { // Il ne doit pas y avoir de chiffres ou de caractères spéciaux dans les noms/prénoms
                return false;
            }
        }

        if (jourNaissance < 1 || jourNaissance > 31 || moisNaissance < 1 || moisNaissance > 12 || anneeNaissance < 1910) { // On valide la date de naissance
            return false;
        }

        if (sexe == 'f' || sexe == 'F') { // On additionne 50 pour indiquer le sexe féminin.
            moisNaissance += 50;
        }
        else if (sexe != 'm' && sexe != 'M') { // On s'assure que si ce n'est pas une femme, que ce soit un homme.
            return false;
        }

        /* Si le mois, la date ou l'année (2 derniers chiffres) est en-dessous de 10, on doit rajouter un 0 devant le chiffre. On construit ensuite des Strings à partir de la date de naissance. */
        String jourNaissanceEnString = (jourNaissance < 10) ? "0" + String.valueOf(jourNaissance) : String.valueOf(jourNaissance);
        String moisNaissanceEnString = (moisNaissance < 10) ? "0" + String.valueOf(moisNaissance) : String.valueOf(moisNaissance);
        String anneeNaissanceEnString = (anneeNaissance % 100 < 10) ? "0" + String.valueOf(anneeNaissance % 100) : String.valueOf(anneeNaissance % 100);

        // On construit le numéro de RAMQ à partir des paramètres puis on vérifie s'il est bien égal au numéro de la personne.
        String numeroRAMQFromParameters = nom.substring(0, 3) + prenom.charAt(0) + " " + anneeNaissanceEnString + moisNaissanceEnString + " " + jourNaissanceEnString;
        return numeroRAMQFromParameters.toUpperCase().equals(numeroRAMQ.toUpperCase().substring(0, 12)); /* Si toutes les conditions sont respectées, c'est que le numéro de RAMQ est valide. */
    }

    public static void main(String[] args) {
        String telephoneValide = "418 666-6666"; // Exemple de numéro valide.
        System.out.println(validerTelephone(telephoneValide));

        String numeroRAMQ = "POIG 9758 0316";
        String nom = "Poitras";
        String prenom = "Ginette";
        byte jourNaissance = 3;
        byte moisNaissance = 8;
        int anneeNaissance = 1997;
        char sexe = 'F';
        System.out.println(validerRAMQ(numeroRAMQ,nom,prenom,jourNaissance,moisNaissance,anneeNaissance,sexe));
    }
}