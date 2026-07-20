package generated;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import parser.ClassDef;
import parser.FieldDef;
import parser.XSDParser;

public class CodeGenerator {

    public static void main(String[] args) throws Exception {
        String xsdPath = args.length > 0 ? args[0] : "bibliotheque.xsd";
        List<ClassDef> classes = new XSDParser().parse(xsdPath);

        new CodeGenerator().generate(classes);
        System.out.println("Classes générées depuis " + xsdPath + " : " + classes.size());
    }

    public void generate(List<ClassDef> classes) throws IOException {
        Path outputDir = Path.of("src/main/java/generated");
        Files.createDirectories(outputDir);

        for (ClassDef c : classes) {
            String code = generateClass(c);
            Files.writeString(outputDir.resolve(c.getClassName() + ".java"), code);
        }
    }

    private String generateClass(ClassDef c) {

        StringBuilder sb = new StringBuilder();

        sb.append("package generated;\n\n")
          .append("import java.util.ArrayList;\n")
          .append("import java.util.List;\n\n")
          .append("public class ")
          .append(c.getClassName())
          .append(" {\n\n");

        for (FieldDef f : c.getFields()) {

            if (f.isList()) {

                sb.append("    private List<")
                  .append(f.getJavaType())
                  .append("> ")
                  .append(f.getName())
                  .append(" = new ArrayList<>();\n");

            } else {

                sb.append("    private ")
                  .append(f.getJavaType())
                  .append(" ")
                  .append(f.getName())
                  .append(";\n");
            }
        }

        sb.append("\n");

        for (FieldDef f : c.getFields()) {
            generateGetterSetter(sb, f);
        }

        sb.append("}");

        return sb.toString();
    }

    private void generateGetterSetter(StringBuilder sb, FieldDef f) {

        String n = f.getName();
        String cap = n.substring(0, 1).toUpperCase() + n.substring(1);

        String type = f.isList()
                ? "List<" + f.getJavaType() + ">"
                : f.getJavaType();

        sb.append("\n");

        sb.append("    public ")
          .append(type)
          .append(" get")
          .append(cap)
          .append("(){\n");

        sb.append("        return ")
          .append(n)
          .append(";\n");

        sb.append("    }\n\n");

        sb.append("    public void set")
          .append(cap)
          .append("(")
          .append(type)
          .append(" ")
          .append(n)
          .append("){\n");

        sb.append("        this.")
          .append(n)
          .append(" = ")
          .append(n)
          .append(";\n");

        sb.append("    }\n");
    }
}