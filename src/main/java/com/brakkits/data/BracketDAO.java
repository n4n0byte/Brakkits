package com.brakkits.data;

import com.brakkits.models.Bracket;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

/**
 * Ali Cooper
 * brakkits
 * CST-452
 * 10/18/2019
 *
 * DAO for Bracket Models
 **/
public class BracketDAO implements DAOInterface<Bracket> {
    /**
     * finds a model by its id
     *
     * @param id int
     * @return Optional<Model>
     */
    @Override
    public Optional<Bracket> findById(int id) {
        return Optional.empty();
    }

    /**
     * finds a Model by its String typed property
     *
     * @param prop String
     * @return Optional<Model>
     */
    @Override
    public Optional<Bracket> findByStringProperty(String prop) {
        return Optional.empty();
    }

    /**
     * finds a Model by its int typed property
     *
     * @param name
     * @param prop int
     * @return Optional<Model>
     */
    @Override
    public Optional<Bracket> findByIntProperty(String name, int prop) {
        return Optional.empty();
    }

    /**
     * finds a model by id using arrays of properties that get
     * used in a where clause
     *
     * @param id       int
     * @param lhsProps String[]
     * @param rhsProps String[]
     * @return Optional<Model>
     */
    @Override
    public Optional<Bracket> findByIdWhere(int id, String[] lhsProps, String[] rhsProps) {
        return Optional.empty();
    }

    /**
     * finds a model collection by id
     *
     * @param id int
     * @return Optional<Lis t < Model> >
     */
    @Override
    public Optional<List<Bracket>> findCollectionById(int id) {
        return Optional.empty();
    }

    /**
     * finds a model collection by its string property
     *
     * @param prop String
     * @return Optional<List < Model>>
     */
    @Override
    public Optional<List<Bracket>> findCollectionByStringProperty(String prop) {
        return Optional.empty();
    }

    /**
     * finds a model collection by its int property
     *
     * @param prop int
     * @return Optional<List < Model>>
     */
    @Override
    public Optional<List<Bracket>> findCollectionByIntProperty(int prop) {
        return Optional.empty();
    }

    /**
     * finds a model collection by arrays of its properties and
     * constraints in the form of strings
     *
     * @param id       int
     * @param lhsProps String[]
     * @param rhsProps String[]
     * @return Optional<List < Model>>
     */
    @Override
    public Optional<Bracket> findCollectionByIdWhere(int id, String[] lhsProps, String[] rhsProps) {
        return Optional.empty();
    }

    /**
     * returns an average of a given property by id
     *
     * @param id int
     * @return Optional<BigDecimal>
     */
    @Override
    public Optional<BigDecimal> findAverageById(int id) {
        return Optional.empty();
    }

    /**
     * returns an average of a given property by a string value
     *
     * @param prop String
     * @return Optional<BigDecimal>
     */
    @Override
    public Optional<BigDecimal> findAverageByProperty(String prop) {
        return Optional.empty();
    }
}
