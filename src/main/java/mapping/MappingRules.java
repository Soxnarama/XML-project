package mapping;

import java.util.HashMap;
import java.util.Map;

/**
 * Classe représentant les règles de correspondance entre concepts XSD et concepts Java.
 * Auteur : Ramatoulaye (Rama)
 */
public class MappingRules {

    // Correspondance types XSD → types Java
    private static final Map<String, String> TYPE_MAPPING = new HashMap<>();

    static {
        TYPE_MAPPING.put("xs:string", "String");
        TYPE_MAPPING.put("xs:int", "int");
        TYPE_MAPPING.put("xs:integer", "Integer");
        TYPE_MAPPING.put("xs:boolean", "boolean");
        TYPE_MAPPING.put("xs:double", "double");
        TYPE_MAPPING.put("xs:float", "float");
    }

    /**
     * Retourne le type Java correspondant à un type XSD
     */
    public static String getJavaType(String xsdType) {
        return TYPE_MAPPING.getOrDefault(xsdType, "String");
    }

    /**
     * Indique si un élément XSD correspond à une List en Java
     * (quand maxOccurs="unbounded")
     */
    public static boolean isList(String maxOccurs) {
        return "unbounded".equals(maxOccurs) || 
               (maxOccurs != null && Integer.parseInt(maxOccurs) > 1);
    }

    /**
     * Indique si un élément XSD correspond à une classe Java
     * (quand il contient un complexType)
     */
    public static boolean isClass(boolean hasComplexType) {
        return hasComplexType;
    }

    /**
     * Génère le nom de la classe Java à partir du nom de l'élément XSD
     * ex: "livre" → "Livre"
     */
    public static String toClassName(String elementName) {
        if (elementName == null || elementName.isEmpty()) return elementName;
        return Character.toUpperCase(elementName.charAt(0)) + elementName.substring(1);
    }

    /**
     * Génère le nom du getter Java
     * ex: "titre" → "getTitre"
     */
    public static String toGetterName(String fieldName) {
        return "get" + toClassName(fieldName);
    }

    /**
     * Génère le nom du setter Java
     * ex: "titre" → "setTitre"
     */
    public static String toSetterName(String fieldName) {
        return "set" + toClassName(fieldName);
    }

    // Test rapide
    public static void main(String[] args) {
        System.out.println(getJavaType("xs:string"));   // String
        System.out.println(getJavaType("xs:int"));      // int
        System.out.println(isList("unbounded"));         // true
        System.out.println(toClassName("livre"));        // Livre
        System.out.println(toGetterName("titre"));       // getTitre
        System.out.println(toSetterName("auteur"));      // setAuteur
    }
}