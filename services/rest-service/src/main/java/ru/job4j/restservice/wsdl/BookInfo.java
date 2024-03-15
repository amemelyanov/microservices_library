
package ru.job4j.restservice.wsdl;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Java class for bookInfo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="bookInfo"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}long"/&gt;
 *         &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="description" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="author" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="pages" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="year" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="cover" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "bookInfo", propOrder = {
    "id",
    "name",
    "description",
    "author",
    "pages",
    "year",
    "cover"
})
public class BookInfo {

    protected long id;
    @XmlElement(required = true)
    protected String name;
    @XmlElement(required = true)
    protected String description;
    @XmlElement(required = true)
    protected String author;
    protected int pages;
    protected int year;
    @XmlElement(required = true)
    protected String cover;

    /**
     * Gets the value of the id property.
     * 
     */
    public long getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     */
    public void setId(long value) {
        this.id = value;
    }

    /**
     * Gets the value of the name property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setName(String value) {
        this.name = value;
    }

    /**
     * Gets the value of the description property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the value of the description property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescription(String value) {
        this.description = value;
    }

    /**
     * Gets the value of the author property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAuthor() {
        return author;
    }

    /**
     * Sets the value of the author property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAuthor(String value) {
        this.author = value;
    }

    /**
     * Gets the value of the pages property.
     * 
     */
    public int getPages() {
        return pages;
    }

    /**
     * Sets the value of the pages property.
     * 
     */
    public void setPages(int value) {
        this.pages = value;
    }

    /**
     * Gets the value of the year property.
     * 
     */
    public int getYear() {
        return year;
    }

    /**
     * Sets the value of the year property.
     * 
     */
    public void setYear(int value) {
        this.year = value;
    }

    /**
     * Gets the value of the cover property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCover() {
        return cover;
    }

    /**
     * Sets the value of the cover property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCover(String value) {
        this.cover = value;
    }

}
