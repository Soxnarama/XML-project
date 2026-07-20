package parser;

import java.util.ArrayList;
import java.util.List;

/**
 * Représente une classe Java à générer, issue d'un xs:element portant un xs:complexType.
 * Auteur :  Mohamed Wade (Prési)
 */
public class ClassDef {

    private final String className;
    private final List<FieldDef> fields = new ArrayList<>();

    public ClassDef(String className) {
        this.className = className;
    }

    public String getClassName() {
        return className;
    }

    public List<FieldDef> getFields() {
        return fields;
    }

    public void setFields(List<FieldDef> fields) {
        this.fields.clear();
        this.fields.addAll(fields);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("class " + className + " {\n");
        for (FieldDef f : fields) {
            sb.append(f).append("\n");
        }
        sb.append("}");
        return sb.toString();
    }
}
