package parser;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import mapping.MappingRules;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Parse un fichier XSD avec DOM et retourne la liste des classes Java à générer
 * (une par xs:element racine contenant un xs:complexType), avec leurs champs.
 * Cette structure est consommée par Mouhamed Diouf (Générateur de classes Java).
 * Auteur :  Mohamed Wade (Prési)
 */
public class XSDParser {

    private static final String XSD_NS = "http://www.w3.org/2001/XMLSchema";

    public List<ClassDef> parse(String xsdFilePath) throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(new File(xsdFilePath));
        doc.getDocumentElement().normalize();

        List<ClassDef> classes = new ArrayList<>();
        Element schemaRoot = doc.getDocumentElement();

        for (Element el : directChildElements(schemaRoot)) {
            if (!isXsdTag(el, "element")) continue;

            String name = el.getAttribute("name");
            if (name.isEmpty()) continue; // pas un élément nommé au niveau racine

            Element complexType = getDirectChild(el, "complexType");
            if (complexType == null) continue; // élément simple à la racine, pas de classe à générer

            ClassDef classDef = new ClassDef(MappingRules.toClassName(name));
            classDef.setFields(parseSequenceFields(complexType));
            classes.add(classDef);
        }

        return classes;
    }

    private List<FieldDef> parseSequenceFields(Element complexType) {
        List<FieldDef> fields = new ArrayList<>();
        Element sequence = getDirectChild(complexType, "sequence");
        if (sequence == null) return fields;

        for (Element el : directChildElements(sequence)) {
            if (!isXsdTag(el, "element")) continue;
            fields.add(parseFieldElement(el));
        }
        return fields;
    }

    private FieldDef parseFieldElement(Element el) {
        String maxOccurs = el.getAttribute("maxOccurs");
        boolean isList = MappingRules.isList(maxOccurs.isEmpty() ? null : maxOccurs);

        String ref = el.getAttribute("ref");
        if (!ref.isEmpty()) {
            // xs:element ref="livre" -> référence vers une autre classe générée
            FieldDef field = new FieldDef(ref, MappingRules.toClassName(ref));
            field.setList(isList);
            field.setReference(true);
            return field;
        }

        String name = el.getAttribute("name");
        String type = el.getAttribute("type");

        if (!type.isEmpty()) {
            // xs:element simple (name + type, ex: xs:string)
            FieldDef field = new FieldDef(name, MappingRules.getJavaType(type));
            field.setList(isList);
            field.setReference(false);
            return field;
        }

        // xs:element avec complexType imbriqué (sans ref, sans type) : traité comme référence
        FieldDef field = new FieldDef(name, MappingRules.toClassName(name));
        field.setList(isList);
        field.setReference(true);
        return field;
    }

    private Element getDirectChild(Element parent, String localName) {
        for (Element el : directChildElements(parent)) {
            if (isXsdTag(el, localName)) return el;
        }
        return null;
    }

    private List<Element> directChildElements(Element parent) {
        List<Element> result = new ArrayList<>();
        NodeList children = parent.getChildNodes();
        for (int i = 0; i < children.getLength(); i++) {
            Node node = children.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                result.add((Element) node);
            }
        }
        return result;
    }

    private boolean isXsdTag(Element el, String localName) {
        return XSD_NS.equals(el.getNamespaceURI()) && localName.equals(el.getLocalName());
    }

    // Test rapide : parse bibliotheque.xsd et affiche les classes détectées
    public static void main(String[] args) throws Exception {
        String path = args.length > 0 ? args[0] : "bibliotheque.xsd";
        XSDParser parser = new XSDParser();
        List<ClassDef> classes = parser.parse(path);
        for (ClassDef c : classes) {
            System.out.println(c);
        }
    }
}
