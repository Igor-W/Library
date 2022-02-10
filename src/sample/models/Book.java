package sample.models;

public class Book {
    private int id;
    private String name;
    private String description;
    private String author;
    private String language;
    private String domain;
    private String url;
    private int authorId;
    private int domainId;
    private int languageId;

    public Book( int id,String name, String description, String author, String language, String domain, String url) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.author = author;
        this.language = language;
        this.domain = domain;
        this.url = url;
    }

    public Book(String name, String description, int authorId, int domainId, int languageId,String url) {
        this.name = name;
        this.description = description;
        this.authorId = authorId;
        this.domainId = domainId;
        this.languageId = languageId;
        this.url = url;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }



    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public int getAuthorId() {
        return authorId;
    }

    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }

    public int getDomainId() {
        return domainId;
    }

    public void setDomainId(int domainId) {
        this.domainId = domainId;
    }

    public int getLanguageId() {
        return languageId;
    }

    public void setLanguageId(int languageId) {
        this.languageId = languageId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}

