//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.2 
// See <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2021.10.12 at 07:35:29 PM CEST 
//


package fr.dsirc.demo.jaxb;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for PensionType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PensionType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;attribute name="nir" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="pointsNumber" use="required" type="{http://www.w3.org/2001/XMLSchema}integer" /&gt;
 *       &lt;attribute name="codeFede" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="inst" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PensionType")
public class PensionType {

    @XmlAttribute(name = "nir", required = true)
    protected String nir;
    @XmlAttribute(name = "pointsNumber", required = true)
    protected BigInteger pointsNumber;
    @XmlAttribute(name = "codeFede", required = true)
    protected String codeFede;
    @XmlAttribute(name = "inst", required = true)
    protected String inst;

    /**
     * Gets the value of the nir property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNir() {
        return nir;
    }

    /**
     * Sets the value of the nir property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNir(String value) {
        this.nir = value;
    }

    /**
     * Gets the value of the pointsNumber property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getPointsNumber() {
        return pointsNumber;
    }

    /**
     * Sets the value of the pointsNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setPointsNumber(BigInteger value) {
        this.pointsNumber = value;
    }

    /**
     * Gets the value of the codeFede property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodeFede() {
        return codeFede;
    }

    /**
     * Sets the value of the codeFede property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodeFede(String value) {
        this.codeFede = value;
    }

    /**
     * Gets the value of the inst property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInst() {
        return inst;
    }

    /**
     * Sets the value of the inst property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInst(String value) {
        this.inst = value;
    }

}
