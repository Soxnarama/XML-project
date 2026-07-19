# Règles de correspondance XSD → Java

## 1. xs:element simple
- XSD : `<xs:element name="titre" type="xs:string"/>`
- Java : attribut privé + getter + setter
```java
private String titre;
public String getTitre() { return titre; }
public void setTitre(String titre) { this.titre = titre; }
```

## 2. xs:element complexType
- XSD : `<xs:element name="livre"><xs:complexType>...</xs:complexType></xs:element>`
- Java : une classe Java
```java
public class Livre { ... }
```

## 3. xs:sequence
- XSD : `<xs:sequence><xs:element ref="titre"/><xs:element ref="auteur"/></xs:sequence>`
- Java : ordre des attributs dans la classe

## 4. maxOccurs="unbounded"
- XSD : `<xs:element ref="livre" maxOccurs="unbounded"/>`
- Java : `List<Livre>`
```java
private List<Livre> livres = new ArrayList<>();
public List<Livre> getLivres() { return livres; }
```

## 5. xs:string
- XSD : `type="xs:string"`
- Java : `String`

## 6. ref="element"
- XSD : `<xs:element ref="livre"/>`
- Java : référence à une autre classe Java
```java
private Livre livre;
```