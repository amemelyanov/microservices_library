//
// This file was generated by the Eclipse Implementation of JAXB, v3.0.0 
// See https://eclipse-ee4j.github.io/jaxb-ri 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2024.03.21 at 02:23:52 PM MSK 
//


package ru.job4j.libraryservice.ws;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="bookDto" type="{http://libraryservice.job4j.ru/ws}bookDto"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "bookDto"
})
@XmlRootElement(name = "findBookByIdResponse")
public class FindBookByIdResponse {

    @XmlElement(required = true)
    protected BookDto bookDto;

    /**
     * Gets the value of the bookDto property.
     * 
     * @return
     *     possible object is
     *     {@link BookDto }
     *     
     */
    public BookDto getBookDto() {
        return bookDto;
    }

    /**
     * Sets the value of the bookDto property.
     * 
     * @param value
     *     allowed object is
     *     {@link BookDto }
     *     
     */
    public void setBookDto(BookDto value) {
        this.bookDto = value;
    }

}
