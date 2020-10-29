package com.brakkits.data;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

/**
 * Ali Cooper
 * brakkits
 * CST-452
 * 10/18/2019
 * Generic Interface for Data Access Objects
 */
public interface DAOInterface<Model> {

    /**
     * finds a model by its id
     * @param id int
     * @return Optional<Model>
     */
    Optional<Model> findById(int id);

    /**
     * finds a Model by its String typed property
     * @param prop String
     * @return Optional<Model>
     */
    Optional<Model> findByStringProperty(String prop);

    /**
     * finds a Model by its int typed property
     * @param prop int
     * @return Optional<Model>
     */
    Optional<Model> findByIntProperty(String name, int prop);

    /**
     * finds a model by id using arrays of properties that get
     * used in a where clause
     * @param id int
     * @param lhsProps String[]
     * @param rhsProps String[]
     * @return Optional<Model>
     */
    Optional<Model> findByIdWhere(int id, String[] lhsProps, String[] rhsProps);

    /**
     * finds a model collection by id
     * @param id int
     * @return Optional< List<Model> >
     */
    Optional<List<Model>> findCollectionById(int id);

    /**
     * finds a model collection by its string property
     * @param prop String
     * @return Optional<List<Model>>
     */
    Optional<List<Model>> findCollectionByStringProperty(String prop);

    /**
     * finds a model collection by its int property
     * @param prop int
     * @return Optional<List<Model>>
     */
    Optional<List<Model>> findCollectionByIntProperty(int prop);

    /**
     * finds a model collection by arrays of its properties and
     * constraints in the form of strings
     * @param id int
     * @param lhsProps String[]
     * @param rhsProps String[]
     * @return Optional<List<Model>>
     */
    Optional<Model> findCollectionByIdWhere(int id, String[] lhsProps, String[] rhsProps);

    /**
     * returns an average of a given property by id
     * @param id int
     * @return Optional<BigDecimal>
     */
    Optional<BigDecimal> findAverageById(int id);

    /**
     * returns an average of a given property by a string value
     * @param prop String
     * @return Optional<BigDecimal>
     */
    Optional<BigDecimal> findAverageByProperty(String prop);
}
