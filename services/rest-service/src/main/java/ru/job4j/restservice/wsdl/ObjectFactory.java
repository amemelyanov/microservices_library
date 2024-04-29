package ru.job4j.restservice.wsdl;

import jakarta.xml.bind.annotation.XmlRegistry;

/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the ru.job4j.restservice.wsdl package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {


    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: ru.job4j.restservice.wsdl
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link FindBookByIdRequest }
     * 
     */
    public FindBookByIdRequest createFindBookByIdRequest() {
        return new FindBookByIdRequest();
    }

    /**
     * Create an instance of {@link FindBookByIdResponse }
     * 
     */
    public FindBookByIdResponse createFindBookByIdResponse() {
        return new FindBookByIdResponse();
    }

    /**
     * Create an instance of {@link BookDto }
     * 
     */
    public BookDto createBookDto() {
        return new BookDto();
    }

    /**
     * Create an instance of {@link FindAllBooksRequest }
     * 
     */
    public FindAllBooksRequest createFindAllBooksRequest() {
        return new FindAllBooksRequest();
    }

    /**
     * Create an instance of {@link FindAllBooksResponse }
     * 
     */
    public FindAllBooksResponse createFindAllBooksResponse() {
        return new FindAllBooksResponse();
    }

    /**
     * Create an instance of {@link ServiceStatus }
     * 
     */
    public ServiceStatus createServiceStatus() {
        return new ServiceStatus();
    }

}
