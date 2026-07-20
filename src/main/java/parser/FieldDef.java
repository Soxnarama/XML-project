package parser;

/**
 * Représente un champ (attribut) qui sera généré dans une classe Java.
 * Correspond soit à un xs:element simple, soit à un xs:element ref="..."
 * (référence vers une autre classe générée).
 * Auteur :  Mohamed Wade (Prési)
 */
public class FieldDef {

    private final String name;
    private final String javaType;
    private boolean list;
    private boolean reference;

    public FieldDef(String name, String javaType) {
        this.name = name;
        this.javaType = javaType;
    }

    public String getName() {
        return name;
    }

    /**
     * Type Java du champ (ex: "String", "int", ou nom de classe pour une référence).
     * Le générateur doit l'envelopper dans List<...> si isList() est vrai.
     */
    public String getJavaType() {
        return javaType;
    }

    public boolean isList() {
        return list;
    }

    public void setList(boolean list) {
        this.list = list;
    }

    public boolean isReference() {
        return reference;
    }

    public void setReference(boolean reference) {
        this.reference = reference;
    }

    @Override
    public String toString() {
        String type = list ? "List<" + javaType + ">" : javaType;
        return "  - " + name + " : " + type + (reference ? " (référence)" : "");
    }
}
